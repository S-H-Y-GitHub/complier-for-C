package parser;

import java.util.HashMap;
import java.util.List;
public class Grammar
{
	List<Production> productions;
	
	HashMap<String, List<String>> getFirst()
	{
		return null;
	}
	HashMap<String, List<String>> getFollow()
	{
		return null;
	}
	
}

class Production
{
	public V right;
	public List<S> left;
}
class S
{
	public String s;
}
class T extends S {}
class V extends S {}

