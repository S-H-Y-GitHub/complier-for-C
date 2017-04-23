package domain;
public class Variable extends Symbol {
	public Boolean nullable;
	
	public Variable()
	{
		super();
	}
	public Variable(String s, Boolean nullable)
	{
		this.s = s;
		this.nullable=nullable;
	}
}
