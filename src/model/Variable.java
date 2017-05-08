package model;
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
	
	@Override
	public int hashCode()
	{
		return super.hashCode()+nullable.hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		return (this == obj) || ((obj instanceof Variable)
				&& s.equals(((Variable)obj).s) && nullable.equals(((Variable)obj).nullable));
	}
}
