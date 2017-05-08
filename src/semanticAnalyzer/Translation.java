package semanticAnalyzer;

import javafx.util.Pair;
import model.Terminal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
public class Translation
{
	/**
	 * 最终的三地址码序列
	 */
	public LinkedList<String> interCode;
	/**
	 * 符号表
	 */
	public HashMap<String, String> symbols;
	/**
	 * 表达式的规约栈
	 */
	public Stack<Pair<Terminal, String>> stack;
	/**
	 * if结构的回填栈
	 */
	public Stack<Integer> if_bp;
	/**
	 * for结构的回填栈
	 */
	public Stack<Integer> for_l1_bp;
	public Stack<Integer> for_l2_bp;
	public Stack<Integer> for_l3_bp;
	public Stack<Integer> for_l4_bp;
	private List<Pair<Terminal, String>> laResult;
	public Translation(List<Pair<Terminal, String>> laResult)
	{
		interCode = new LinkedList<>();
		symbols = new HashMap<>();
		stack = new Stack<>();
		if_bp = new Stack<>();
		this.laResult = laResult;
		for_l1_bp = new Stack<>();
		for_l2_bp = new Stack<>();
		for_l3_bp = new Stack<>();
		for_l4_bp = new Stack<>();
	}
	
	public void M1(Integer i)
	{
		String value = laResult.get(i - 3).getValue();
		interCode.add("call " + value);
	}
	public void M2(Integer i)
	{
		String value = stack.pop().getValue();
		interCode.add("return " + value);
	}
	public void M3(Integer i) throws Exception
	{
		if (symbols.containsKey(laResult.get(i).getValue()))
			throw new Exception("变量" + laResult.get(i).getValue() + "已被声明");
		symbols.put(laResult.get(i).getValue(), laResult.get(i - 1).getValue());
	}
	public void M4(Integer i) throws Exception
	{
		if (symbols.containsKey(laResult.get(i - 3).getValue()))
			throw new Exception("变量" + laResult.get(i - 3).getValue() + "已被声明");
		symbols.put(laResult.get(i - 3).getValue(), laResult.get(i - 4).getValue()
				+ "[" + laResult.get(i - 1).getValue() + "]");
	}
	public void M5(Integer i) throws Exception
	{
		Pair<Terminal, String> p1 = stack.pop();
		Pair<Terminal, String> op = stack.pop();
		Pair<Terminal, String> p2 = stack.pop();
		String p1type;
		if (p1.getKey().s.equals("标识符"))
			p1type = symbols.get(p1.getValue());
		else if (p1.getKey().s.equals("数字"))
			p1type = "int";
		else
			p1type = "char";
		String temp = "_v" + symbols.size();
		symbols.put(temp, p1type);
		stack.push(new Pair<>(new Terminal("标识符"), temp));
		interCode.add(temp + " := " + p2.getValue() + " " + op.getValue() + " " + p1.getValue());
	}
	public void M6(Integer i) throws Exception
	{
		if (laResult.get(i).getKey().s.equals("标识符") && !symbols.containsKey(laResult.get(i).getValue()))
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
		String temp = "_b" + symbols.size();
		symbols.put(temp, "bool");
		stack.push(new Pair<>(new Terminal("标识符"), temp));
		interCode.add(temp + " := " + p2.getValue() + " " + op.getValue() + " " + p1.getValue());
	}
	public void M7(Integer i) throws Exception
	{
		String bool = stack.pop().getValue();
		interCode.add("if " + bool + " goto " + (interCode.size() + 2));
		interCode.add("goto ");
		if_bp.push(interCode.size() - 1);
	}
	public void M8(Integer i) throws Exception
	{
		interCode.add("goto ");
		int index = if_bp.pop();
		String s = interCode.get(index);
		s = s + interCode.size();
		interCode.set(index, s);
		if_bp.push(interCode.size() - 1);
	}
	public void $ELSE(Integer i) throws Exception
	{
		int index = if_bp.pop();
		String s = interCode.get(index);
		s = s + interCode.size();
		interCode.set(index, s);
	}
	public void $ASSI(Integer i) throws Exception
	{
		String right = stack.pop().getValue();
		String left = stack.pop().getValue();
		interCode.add(left + " := " + right);
	}
	public void M9(Integer i) throws Exception
	{
		interCode.add("if " + stack.pop().getValue() + " goto ");
		for_l1_bp.push(interCode.size() - 1);
		interCode.add("goto ");
		for_l2_bp.push(interCode.size() - 1);
		for_l4_bp.push(interCode.size());
	}
	public void M10(Integer i) throws Exception
	{
		interCode.add("goto " + for_l3_bp.pop());
		int todo = for_l1_bp.pop();
		String s = interCode.get(todo);
		s = s + interCode.size();
		interCode.set(todo, s);
	}
	public void $FOR(Integer i) throws Exception
	{
		interCode.add("goto " + for_l4_bp.pop());
		int todo = for_l2_bp.pop();
		String s = interCode.get(todo);
		s = s + interCode.size();
		interCode.set(todo, s);
	}
	public void M11(Integer i) throws Exception
	{
		for_l3_bp.push(interCode.size());
	}
	public void M12(Integer i) throws Exception
	{
		if (laResult.get(i - 3).getKey().s.equals("标识符") && !symbols.containsKey(laResult.get(i - 3).getValue()))
			throw new Exception("引用了未定义的变量");
		Integer index = Integer.valueOf(laResult.get(i - 1).getValue());
		if (index > Integer.valueOf(symbols.get(laResult.get(i - 3).getValue()).replaceAll("\\D", "")))
			throw new Exception("数组访问越界");
		stack.push(new Pair<>(new Terminal("标识符"),
				("[" + laResult.get(i - 3).getValue() + " + " + index + "]")));
	}
	public void M14(Integer i) throws Exception
	{
	
	}
	public void M15(Integer i) throws Exception
	{
	
	}
	
	public void printCode()
	{
		for (int i = 0; i < interCode.size(); i++)
		{
			String code = interCode.get(i);
			System.out.println(i + "\t\t" + code);
		}
	}
}
