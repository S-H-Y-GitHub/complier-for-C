package parser;

import domain.Production;
import domain.Terminal;

import java.util.Set;
public class LR1Item
{
	public Production production;
	public Set<Terminal> lookaheads;
	public int dotPosition;//表示下一位置在生成式右边哪个位置的符号之前
}
