package parser;

import domain.Production;
import domain.Terminal;

import java.util.HashSet;
public class LR1Item
{
	public Production production;
	public HashSet<Terminal> lookaheads;
	public Integer dotPosition;//表示下一位置在生成式右边哪个位置的符号之前
	
	@Override
	public int hashCode()
	{
		return production.hashCode() + lookaheads.hashCode() + dotPosition;
	}
	@Override
	public boolean equals(Object obj)
	{
		return (obj == this) || ((obj instanceof LR1Item) && (dotPosition.equals(((LR1Item) obj).dotPosition))
				&& (lookaheads.equals(((LR1Item) obj).lookaheads)) && (production.equals(((LR1Item) obj).production)));
	}
}
