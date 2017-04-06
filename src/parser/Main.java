package parser;

import domain.*;
import javafx.util.Pair;
import lexicalAnalyzer.LexicalAnalyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
public class Main
{
	public static void main(String[] args)
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
		Stack<Symbol> x = new Stack<>(); //符号栈
		s.push(0);
		
	}
	
	private static String  getAction(int state, Terminal input)
	{
		HashMap<Terminal,List<String>> action;
		action = new HashMap<>();
		// TODO: 2017/4/6 完成action表
		return action.get(input).get(state);
	}
	private static int getGoto(int state, Variable input)
	{
		HashMap<Variable,List<Integer>> go;
		go = new HashMap<>();
		// TODO: 2017/4/6 完成goto表
		return go.get(input).get(state);
	}
}
