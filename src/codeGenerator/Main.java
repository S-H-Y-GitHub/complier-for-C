package codeGenerator;

import semanticAnalyzer.SemanticAnalyzer;

import java.util.LinkedList;
import java.util.Scanner;
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
		SemanticAnalyzer s = new SemanticAnalyzer();
		LinkedList<String> interCode = s.getInterCode(filename);
		if (interCode == null)
			return;
		
	}
}
