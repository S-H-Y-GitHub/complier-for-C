package semanticAnalyzer;

import domain.Terminal;
import javafx.util.Pair;

import java.util.*;
public class Translation
{
	/**
	 * 最终的三地址码序列
	 */
	public LinkedList<String> interCode;
	/**
	 * 符号表
	 */
	public HashMap<String,String> symbols;
	private List<Pair<Terminal, String>> laResult;
	public Stack<Pair<Terminal, String>> stack;
	public ArrayList<Integer> todo;
	public Translation(List<Pair<Terminal, String>> laResult)
	{
		interCode = new LinkedList<>();
		symbols = new HashMap<>();
		stack = new Stack<>();
		todo = new ArrayList<>();
		this.laResult = laResult;
	}
	
	public void M1(Integer i)
	{
		String value = laResult.get(i - 3).getValue();
		interCode.add("call " + value);
	}
	public void M2(Integer i)
	{
		String value = laResult.get(i-1).getValue();
		//value = stack.peek();
		interCode.add("return " + value);
	}
	public void M3(Integer i) throws Exception
	{
		if(symbols.containsKey(laResult.get(i).getValue()))
			throw new Exception("变量"+laResult.get(i).getValue()+"已被声明");
		symbols.put(laResult.get(i).getValue() , laResult.get(i-1).getValue());
	}
	public void M4(Integer i) throws Exception
	{
		if(symbols.containsKey(laResult.get(i-3).getValue()))
			throw new Exception("变量"+laResult.get(i-3).getValue()+"已被声明");
		symbols.put(laResult.get(i-3).getValue(), laResult.get(i-4).getValue()
				+"[" + laResult.get(i-4).getValue() + "]");
	}
	public void M5(Integer i) throws Exception
	{
		Pair<Terminal, String> p1 = stack.pop();
		Pair<Terminal, String> op = stack.pop();
		Pair<Terminal, String> p2 = stack.pop();
		String p1type;
		if(p1.getKey().s.equals("标识符"))
			p1type = symbols.get(p1.getValue());
		else if(p1.getKey().s.equals("数字"))
			p1type = "int";
		else
			p1type = "char";
		String temp = "t"+symbols.size();
		symbols.put(temp,p1type);
		stack.push(new Pair<>(new Terminal("标识符"),temp));
		interCode.add(temp+":="+p1.getValue()+op.getValue()+p2.getValue());
	}
	public void M6(Integer i) throws Exception
	{
		if(laResult.get(i).getKey().s.equals("标识符") && !symbols.containsKey(laResult.get(i).getValue()))
			throw new Exception("引用了未定义的变量");
		stack.push(laResult.get(i));
	}
	public void $OP(Integer i)
	{
		stack.push(laResult.get(i));
	}
	public void $JUG(Integer i)
	{
		stack.push(laResult.get(i));
	}
	public void $BOOL(Integer i)
	{
		Pair<Terminal, String> p1 = stack.pop();
		Pair<Terminal, String> op = stack.pop();
		Pair<Terminal, String> p2 = stack.pop();
		String temp = "t"+symbols.size();
		symbols.put(temp,"bool");
		stack.push(new Pair<>(new Terminal("标识符"),temp));
		interCode.add(temp+":="+p1.getValue()+op.getValue()+p2.getValue());
	}
	public void $IF(Integer i)
	{
	
	}
	public void $FOR(Integer i)
	{
	
	}
}
