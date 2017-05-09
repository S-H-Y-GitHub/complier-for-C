package semanticAnalyzer;

import javafx.util.Pair;
import lexicalAnalyzer.LexicalAnalyzer;
import model.Production;
import model.Symbol;
import model.Terminal;
import model.Variable;
import parser.Grammar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class SemanticAnalyzer
{
	private Translation translation;
	public SemanticAnalyzer(String filename) throws Exception
	{
		LexicalAnalyzer l = new LexicalAnalyzer();
		List<Pair<Terminal, String>> laResult = l.getSymbols(filename); //词法分析器的结果
		Stack<Integer> s = new Stack<>(); //状态栈
		Stack<Symbol> x = new Stack<>(); //符号栈
		Grammar grammar = new Grammar();
		s.push(0);
		x.push(new Terminal("end"));
		List<Production> productions = grammar.getProductions();
		translation = new Translation(laResult);
		String action;
		for (int i = 0; i < laResult.size(); i++)
		{
			Pair<Terminal, String> t = laResult.get(i);
			action = grammar.getAction(s.peek(), t.getKey());
			if (action == null)
				throw new Exception("不是规范的C语言程序或文法定义有误");
			else if (action.matches("S\\d+"))//移进
			{
				x.push(t.getKey());
				s.push(Integer.valueOf(action.replace("S", "")));
				//System.out.println("移进\t" + t.toString());
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
						//System.out.println("规约\t" + p.toString());
						i--;
						//调用语义分析挂钩程序
						try
						{
							translation.getClass().getMethod(p.left.s, Integer.class).invoke(translation, i);
						}
						catch (NoSuchMethodException ignored) {}
					}
					else
						throw new Exception("不是规范的C语言程序或文法定义有误");
				}
				else
					throw new Exception("不是规范的C语言程序或文法定义有误");
			}
			else if (action.equals("acc"))//接受
				return;
			else//出错
				throw new Exception("unknown error");
		}
	}
	public LinkedList<String> getInterCode() throws Exception
	{
		return translation.getInterCode();
	}
	public HashMap<String, String> getSymbols()
	{
		return translation.getSymbols();
	}
}
