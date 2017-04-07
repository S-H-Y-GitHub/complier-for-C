package parser;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import domain.*;
import javafx.util.Pair;
import lexicalAnalyzer.LexicalAnalyzer;

import java.util.*;
public class Main
{
	public static void main(String[] args) throws Exception
	{
//		Grammar grammar = new Grammar();
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
		Stack<Symbol> x = new Stack<>(); //符号栈，用空格做为结束字符
		s.push(0);
		x.push(new Terminal(" "));
		List<Production> productions = new ArrayList<>();// TODO: 2017/4/6 制作生成式
		String action;
		for(Pair<Terminal, String> t:laResult)
		{
			action = getAction(s.peek(), t.getKey());
			if(action == null)
			{
				System.err.println("不是规范的C语言程序或文法定义有误");
				return;
			}
			else if(action.matches("S\\d+"))//移进
			{
				x.push(t.getKey());
				s.push(Integer.valueOf(action.replace("S","")));
			}
			else if(action.matches("r\\d+"))//规约
			{
				Production p = productions.get(Integer.parseInt(action.replace("r", "")));
				LinkedList<Symbol> stackTop = new LinkedList<>();
				for (int count = p.right.size();count>=0;count--)
					stackTop.push(x.pop());
				if(stackTop.equals(p.right))
				{
					for (int count = p.right.size();count>=0;count--)
						s.pop();
					x.push(p.left);
					Integer newstate = getGoto(s.peek(), (Variable) x.peek());
					if(newstate!=-1)
						s.push(newstate);
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
			else if(action.equals("acc"))//接受
				System.out.println("语法分析成功完成！");
			else//出错
			{
				System.err.println("unknown error");
				return;
			}
		}
	}
	
	private static String  getAction(int state, Terminal input)
	{
		HashMap<Terminal,List<String>> action;
		action = new HashMap<>();
		// TODO: 2017/4/6 完成action表,null表示空
		return action.get(input).get(state);
	}
	private static int getGoto(int state, Variable input)
	{
		HashMap<Variable,List<Integer>> go;
		go = new HashMap<>();
		// TODO: 2017/4/6 完成goto表,-1表示空
		return go.get(input).get(state);
	}
}
