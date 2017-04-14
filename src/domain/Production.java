package domain;
import java.util.LinkedList;
public class Production
{
	public Variable left;
	public LinkedList<Symbol> right;
	
	@Override
	public String toString()
	{
		return left.s+" -> "+right.toString();
	}
	@Override
	public int hashCode()
	{
		return left.hashCode()+right.hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		return this == obj || obj instanceof Production && left.equals(((Production) obj).left) && right.equals(((Production) obj).right);
	}
}
