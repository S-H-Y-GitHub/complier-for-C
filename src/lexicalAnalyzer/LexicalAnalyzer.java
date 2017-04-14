package lexicalAnalyzer;


import domain.Terminal;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
public class LexicalAnalyzer
{
	
	public List<Pair<Terminal, String>> getSymbols(String filename)
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
						symbols.add(new Pair<>(new Terminal(buffer.substring(i, i + 1)),buffer.substring(i, i + 1)));
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
							symbols.add(new Pair<>(new Terminal(buffer.substring(i, i + 2)),buffer.substring(i, i + 2)));
							i = i + 2;
						}
						else
						{
							symbols.add(new Pair<>(new Terminal(buffer.substring(i, i + 1)),buffer.substring(i, i + 1)));
							i++;
						}
					}
					//数字
					else if (buffer.substring(i, i + 1).matches("\\d"))
					{
						int j = 1;
						for (; buffer.substring(i + j, i + j + 1).matches("\\d"); j++) ;
						symbols.add(new Pair<>(new Terminal("数字"),buffer.substring(i, i + j)));
						i = i + j;
					}
					//字符串常量
					else if (buffer.substring(i, i + 1).matches("\""))
					{
						int j = 1;
						for (; !buffer.substring(i + j, i + j + 1).matches("\""); j++) ;
						symbols.add(new Pair<>(new Terminal("字符串"),buffer.substring(i, i + j + 1)));
						i = i + j + 1;
					}
					//字符常量
					else if (buffer.substring(i, i + 1).matches("\'"))
					{
						int j = 1;
						for (; !buffer.substring(i + j, i + j + 1).matches("\'"); j++) ;
						symbols.add(new Pair<>(new Terminal("字符"),buffer.substring(i, i + j + 1)));
						i = i + j + 1;
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
							symbols.add(new Pair<>(new Terminal(word.toUpperCase()),word));
						
						else
							symbols.add(new Pair<>(new Terminal("标识符"),word));
						i = i + j;
					}
				}
			}
			symbols.add(new Pair<>(new Terminal("end"),null));
			return symbols;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
