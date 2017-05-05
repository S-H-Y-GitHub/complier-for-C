package semanticAnalyzer;

import domain.Terminal;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
public class Translation
{
	/**
	 * 最终的三地址码序列
	 */
	public LinkedList<String> interCode;
	/**
	 * 符号表
	 */
	public HashSet<Object> symbols;
	private List<Pair<Terminal, String>> laResult;
	public Translation(List<Pair<Terminal, String>> laResult)
	{
		interCode = new LinkedList<>();
		symbols = new HashSet<>();
		this.laResult = laResult;
	}
	
	public void M1(Integer i)
	{
		String value = laResult.get(i - 4).getValue();
		interCode.add("call " + value);
	}
	public void M2(Integer i)
	{
		String value = null;// TODO: 2017/5/5
		interCode.add("return " + value);
	}
	public void M3(Integer i)
	{
	
	}
	public void $EXPS(Integer i)
	{
	
	}
	public void M4(Integer i)
	{
	
	}
	public void $IF(Integer i)
	{
	
	}
	public void $BOOL(Integer i)
	{
	
	}
	public void $FOR(Integer i)
	{
	
	}
}
