package model;
public class Symbol
{
	public String s;
	public Symbol() {}
	public Symbol(String s){
		this.s = s;
	}
	@Override
	public String toString()
	{
		return s;
	}
	
	@Override
	public int hashCode()
	{
		return s.hashCode()*2;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return (this == obj) || ((this.getClass() == obj.getClass()) && (s.equals(((Symbol) obj).s)));
	}
}
