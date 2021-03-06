package semanticAnalyzer;

import javafx.util.Pair;
import lexicalAnalyzer.LexicalAnalyzer;
import model.Production;
import model.Symbol;
import model.Terminal;
import model.Variable;
import parser.Grammar;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
public class Main
{
	public static void main(String[] args) throws Exception
	{
		String filename = "";
		if (args.length == 1)
			filename = args[0];
		else
		{
			Scanner scan = new Scanner(System.in);
			System.out.print("请输入C语言源程序的位置：");
			if (scan.hasNextLine())
				filename = scan.next();
		}
		LexicalAnalyzer l = new LexicalAnalyzer();
		List<Pair<Terminal, String>> laResult = l.getSymbols(filename); //词法分析器的结果
		Stack<Integer> s = new Stack<>(); //状态栈
		Stack<Symbol> x = new Stack<>(); //符号栈
		Grammar grammar = new Grammar();
		s.push(0);
		x.push(new Terminal("end"));
		List<Production> productions = grammar.getProductions();
		Translation translation = new Translation(laResult);
		String action;
		/*
		仅用于调试
		HashMap<Pair<Variable,Integer>,Integer> go = grammar.go;
		HashMap<Pair<Terminal,Integer>,String> action1 = grammar.action;
		Map<Pair<Set<LR1Item>, Symbol>, Integer> transition = grammar.transition;
		List<Set<LR1Item>> states = grammar.states;
		HashMap<Variable, HashSet<Terminal>> first = grammar.first;
		Arrays.asList(go,action1,transition,states,first);
		*/
		for (int i = 0; i < laResult.size(); i++)
		{
			Pair<Terminal, String> t = laResult.get(i);
			action = grammar.getAction(s.peek(), t.getKey());
			if (action == null)
			{
				System.err.println("不是规范的C语言程序或文法定义有误");
				return;
			}
			else if (action.matches("S\\d+"))//移进
			{
				x.push(t.getKey());
				s.push(Integer.valueOf(action.replace("S", "")));
				System.out.println("移进\t" + t.toString());
			}
			else if (action.matches("r\\d+"))//规约
			{
				Production p = productions.get(Integer.parseInt(action.replace("r", "")));
				LinkedList<Symbol> stackTop = new LinkedList<>();
				int count = p.right.get(0).s.equals("") ? 0 : p.right.size();
				for (; count > 0; count--)
					stackTop.push(x.pop());
				if (stackTop.equals(p.right) || (stackTop.size() == 0 && p.right.get(0).s.equals("")))
				{
					count = p.right.get(0).s.equals("") ? 0 : p.right.size();
					for (; count > 0; count--)
						s.pop();
					x.push(p.left);
					Integer newState = grammar.getGoto(s.peek(), (Variable) x.peek());
					if (newState != null)
					{
						s.push(newState);
						System.out.println("规约\t" + p.toString());
						i--;
						//调用语义分析挂钩程序
						try
						{
							translation.getClass().getMethod(p.left.s, Integer.class).invoke(translation, i);
						}
						catch (NoSuchMethodException ignored){}
						catch (Exception e)
						{
							System.err.println(((InvocationTargetException) e).getTargetException().getMessage());
							return;
						}
					}
					else
					{
						System.err.println("不是规范的C语言程序或文法定义有误");
						return;
					}
				}
				else
				{
					System.err.println("不是规范的C语言程序或文法定义有误");
					return;
				}
			}
			else if (action.equals("acc"))//接受
			{
				System.out.println("语法分析成功完成！");
				System.out.println("生成的三地址码：");
				translation.printCode();
				System.out.println("使用的符号表：");
				System.out.println(translation.getSymbols());
			}
			else//出错
			{
				System.err.println("unknown error");
				return;
			}
		}
	}
}
