package codeGenerator;

import semanticAnalyzer.SemanticAnalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
public class Main
{
	static private HashMap<String, String> symbols;
	
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
		SemanticAnalyzer s = new SemanticAnalyzer(filename);
		LinkedList<String> interCode = s.getInterCode();
		symbols = s.getSymbols();
		LinkedList<String> result = new LinkedList<>();
		LinkedList<String> params = new LinkedList<>();
		if (interCode == null)
			return;
		File file = new File("result.asm");
		if (file.exists())
			if (!file.delete())
				throw new IOException("存在无法删除的result.asm文件或文件夹");
		if (!file.createNewFile())
			throw new IOException("result.asm创建失败");
		FileWriter output = new FileWriter(file);
		output.write(".586\n.model flat, stdcall\n.stack 4096\n" + "\n" +
				"option casemap : none\n\nincludelib msvcrt.lib\n\n" +
				"printf\t\t\tPROTO C : ptr byte, : vararg\n" + "scanf\t\t\tPROTO C : ptr byte, : vararg\n" +
				"gets\t\t\t\tPROTO C : ptr byte\n" + "getchar\t\tPROTO C\n" + "_getche\t\tPROTO C\n" +
				"ExitProcess\tPROTO, dwExitCode : DWORD; exit program\n" + "\n" + "\n" +
				"chr$ MACRO any_text : VARARG\n" + "LOCAL txtname\n" + ".data\n" + "IFDEF __UNICODE__\n" +
				"WSTR txtname, any_text\n" + "align 4\n" + ".code\n" +
				"EXITM <OFFSET txtname>\n" + "ENDIF\n" + "txtname db any_text, 0\n" + "align 4\n" +
				".code\n" + "EXITM <OFFSET txtname>\n" + "ENDM\n" + "\n" + ".data\n");
		for (String vname : symbols.keySet())
		{
			String type = symbols.get(vname);
			String asmCode = "\t";
			asmCode += vname + "\t";
			if (type.matches(".+\\[.+\\]"))
			{
				String t1 = type.split("\\[")[0];
				String t2 = type.split("\\[")[1];
				t2 = t2.replace("]", "");
				if (t1.matches("int"))
					asmCode += "sdword\t" + t2 + " dup(?)";
				else
					asmCode += "byte\t" + t2 + " dup(?)";
			}
			else
			{
				if (type.matches("int"))
					asmCode += "sdword\t?";
				else
					asmCode += "byte\t?";
			}
			output.write(asmCode + "\n");
		}
		output.write(".code\n");
		for (int i = 0; i < interCode.size(); i++)
		{
			String code = interCode.get(i);
			if (code.matches("call main"))
			{
				output.write("main PROC\n");
			}
			else if (code.matches("param .+"))
			{
				params.add(code.replace("param ", ""));
			}
			else if (code.matches("call.+"))
			{
				StringBuilder asmCode = new StringBuilder(code.replace("call ", "invoke\t"));
				for (String param : params)
				{
					if (param.matches("\".+\""))
						asmCode.append(", chr$(").append(param).append(")");
					else
						asmCode.append(", ").append(param);
				}
				result.add(i + ":\t\t" + asmCode);
			}
			else if (code.matches("return.+"))
			{
				String retValue = getAsmType(code.replace("return", "").trim());
				if (retValue.length() > 0)
					result.add(i + ":\t\tmov\t\teax," + retValue);
				result.add("\t\tret");
			}
			else if (code.matches("\\[.+\\].+:=.+"))
			{
				String left = code.split(":=")[0];
				String right = code.split(":=")[1].trim();
				left = left.replace("[", "").replace("]", "");
				String var = left.split("\\+")[0].trim();
				String offset = left.split("\\+")[1].trim();
				String type = symbols.get(var);
				right = getAsmType(right);
				if (type.contains("int"))
				{
					result.add(i + ":\t\tmov\t\teax,4");
					result.add("\t\timul\tecx,eax," + offset);
					result.add("\t\tmov\t\tdword ptr " + var + "[ecx]," + right);
				}
				else if (type.contains("char"))
				{
					result.add(i + ":\t\tmov\teax,1");
					result.add("\t\timul\tecx,eax," + offset);
					result.add("\t\tmov\t\tbyte ptr " + var + "[ecx]," + right);
				}
			}
			else if (code.matches("\\w+ := .+ [+\\-*/] .+"))
			{
				String left = getAsmType(code.split(" := ")[0]);
				String right = code.split(" := ")[1];
				String p1 = getAsmType(right.split(" ")[0]);
				String op = right.split(" ")[1];
				String p2 = getAsmType(right.split(" ")[2]);
				result.add(i + ":\t\tmov\t\teax," + p1);
				switch (op)
				{
					case "+":
						result.add("\t\tadd\t\teax," + p2);
						break;
					case "-":
						result.add("\t\tsub\t\teax," + p2);
						break;
					case "*":
						result.add("\t\timul\teax," + p2);
						break;
					case "/":
						result.add("\t\tidiv\teax," + p2);
				}
				result.add("\t\tmov\t\t" + left + ",eax");
			}
			else if (code.matches(".+ := .+"))
			{
				String left = code.split(":=")[0].trim();
				String right = code.split(":=")[1].trim();
				String type = symbols.get(left);
				if (type.contains("int"))
					result.add(i + ":\t\tmov\t\tdword ptr [" + left + "]," + right);
				else if (type.contains("char"))
					result.add(i + ":\t\tmov\t\tbyte ptr [" + left + "]," + right);
			}
			else if (code.matches("goto \\d+"))
			{
				String num = code.replace("goto ", "");
				result.add(i + ":\t\tjmp\t\t" + num);
			}
			else if (code.matches("if \\w+ goto \\d+"))
			{
				//处理布尔产生式
				String boolcode = interCode.get(i - 1);
				String right = boolcode.split(" := ")[1];
				String p1 = getAsmType(right.split(" ")[0]);
				String op = right.split(" ")[1];
				String p2 = getAsmType(right.split(" ")[2]);
				result.add((i - 1) + ":\t\tcmp\t\t" + p1 + "," + p2);
				//处理跳转语句
				String target = code.split(" ")[3];
				switch (op)
				{
					case "<":
						result.add(i + ":\t\tjl\t\t" + target);
						break;
					case ">":
						result.add(i + ":\t\tjg\t\t" + target);
						break;
					case "==":
						result.add(i + ":\t\tje\t\t" + target);
						break;
					case "!=":
						result.add(i + ":\t\tjne\t\t" + target);
				}
			}
		}
		for (String code : result)
		{
			System.out.println(code);
			output.write(code + "\n");
		}
		output.write("main\tendp\n\nend\tmain" + "\n");
		output.flush();
		output.close();
	}
	private static String getAsmType(String key)
	{
		String type = symbols.get(key);
		if (type != null && type.contains("int"))
			key = "dword ptr [" + key + "]";
		else if (type != null && type.contains("char"))
			key = "byte ptr [" + key + "]";
		return key;
	}
}
