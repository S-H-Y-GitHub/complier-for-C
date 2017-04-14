package parser;

import domain.Production;
import domain.Symbol;
import domain.Terminal;
import javafx.util.Pair;

import java.util.*;
public class DFA
{
	List<Set<LR1Item>> states;
	Map<Pair<Set, Symbol>, Integer> transition;
	
	public DFA(List<Production> productions,Set<Terminal> terminals)
	{
		states = new LinkedList<>();
		Set<LR1Item> state = new HashSet<>();
		LR1Item temp = new LR1Item();
		temp.production = productions.get(0);//把第一个生成式放进去
		temp.dotPosition = 0;
		temp.lookaheads = new HashSet<>();
		temp.lookaheads.add(new Terminal("end"));
		state.add(temp);
		
		buildDFA(state);
		for (Terminal terminal:terminals)
		{
			Set<LR1Item> next = go(state,terminal);
		}
		
	}
	private void buildDFA(Set<LR1Item> state)
	{
		closure(state);
		for (LR1Item item:state)
		{
			Symbol change = item.production.right.get(item.dotPosition);//todo check
			
		}
		states.add(state);
	}
	private Set<LR1Item> go(Set<LR1Item> state, Terminal terminal)
	{
		return null;
	}
	private void closure(Set<LR1Item> state)
	{
	
	}
	public int getNext(Set state, Symbol input)
	{
		return transition.get(new Pair<>(state, input));
	}
}
