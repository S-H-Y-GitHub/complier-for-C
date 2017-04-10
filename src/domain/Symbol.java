package domain;
public class Symbol
{
	public Symbol() {}
	public Symbol(String s){
		this.s = s;
	}
	public String s;
	
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
}
