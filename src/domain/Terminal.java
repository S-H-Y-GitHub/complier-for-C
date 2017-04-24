package domain;
public class Terminal extends Symbol {
	public Terminal(){
		super();
	}
	public Terminal(String s)
	{
		super(s);
	}
	@Override
	public boolean equals(Object obj)
	{
		return (this == obj) || ((obj instanceof Terminal)
				&& s.equals(((Terminal)obj).s));
	}
	@Override
	public int hashCode()
	{
		return super.hashCode()*2;
	}
}
