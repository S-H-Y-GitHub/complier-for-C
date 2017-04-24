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
	
	@Override
	public boolean equals(Object obj)
	{
		Boolean result =(this == obj) || ((this.getClass()==obj.getClass()) && (s.equals(((Symbol)obj).s)));
		return result;
	}
}
