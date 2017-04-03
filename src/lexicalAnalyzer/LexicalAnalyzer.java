package lexicalAnalyzer;


import domain.Terminal;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
public class LexicalAnalyzer
{
	
	public List getSymbols(String filename)
	{
		String buffer;
		BufferedReader inputFile;
		List<Pair<Terminal, String>> symbols = new LinkedList<>();
		try
		{
			inputFile = new BufferedReader(new FileReader(filename));
			while ((buffer = inputFile.readLine()) != null)
			{
				buffer = buffer + ' ';
				int i = 0;
				while (i < buffer.length())
				{
					//处理空白字符
					if (buffer.substring(i, i + 1).matches("\\s"))
						i++;
						//处理单个的符号
					else if (buffer.substring(i, i + 1).matches("#|;|\\)|\\(|\\{|}|\\.|\\[|]|:|\\?"))
					{
						System.out.println("(\"" + buffer.substring(i, i + 1) + "\"\t" + buffer.substring(i, i + 1) + ")");
						i++;
					}
					//长度为二的符号
					else if (buffer.substring(i, i + 1).matches("!|<|>|=|-|\\+|\\*|/|%"))
					{
						if (buffer.charAt(i + 1) == '=' || buffer.substring(i, i + 2).matches("->")
								|| buffer.substring(i, i + 2).matches("\\+\\+") || buffer.substring(i, i + 2).matches("--")
								|| buffer.substring(i, i + 2).matches(">>") || buffer.substring(i, i + 2).matches("<<")
								|| buffer.substring(i, i + 2).matches("//") || buffer.substring(i, i + 2).matches("/\\*")
								|| buffer.substring(i, i + 2).matches("\\*/"))
						{
							System.out.println("(\"" + buffer.substring(i, i + 2) + "\"\t" + buffer.substring(i, i + 2) + ")");
							i = i + 2;
						}
						else
						{
							System.out.println("(\"" + buffer.substring(i, i + 1) + "\"\t" + buffer.substring(i, i + 1) + ")");
							i++;
						}
					}
					//数字
					else if (buffer.substring(i, i + 1).matches("\\d"))
					{
						int j = 1;
						for (; buffer.substring(i + j, i + j + 1).matches("\\d"); j++) ;
						System.out.println("(数字\t" + buffer.substring(i, i + j) + ")");
						i = i + j;
					}
					//字符串常量
					else if (buffer.substring(i, i + 1).matches("\""))
					{
						int j = 1;
						for (; !buffer.substring(i + j, i + j + 1).matches("\""); j++) ;
						System.out.println("(字符串\t" + buffer.substring(i, i + j + 1) + ")");
						i = i + j + 2;
					}
					//字符常量
					else if (buffer.substring(i, i + 1).matches("\'"))
					{
						int j = 1;
						for (; !buffer.substring(i + j, i + j + 1).matches("\'"); j++) ;
						System.out.println("(字符\t" + buffer.substring(i, i + j + 1) + ")");
						i = i + j + 2;
					}
					//标识符和关键字
					else if (buffer.substring(i, i + 1).matches("[A-Za-z]"))
					{
						int j = 1;
						for (; buffer.substring(i + j, i + j + 1).matches("\\w"); j++) ;
						String word = buffer.substring(i, i + j);
						if (word.matches("auto|break|case|char|const|continue|default|do|double|else" +
								"|enum|extern|float|for|goto|if|int|long|register|return|short|signed|sizeof" +
								"|static|struct|switch|typedef|union|unsigned|void|volatile|while"))
							System.out.println("(" + word.toUpperCase() + "\t" + word + ")");
						else
							System.out.println("(标识符\t" + word + ")");
						i = i + j;
					}
				}
			}
			return symbols;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
