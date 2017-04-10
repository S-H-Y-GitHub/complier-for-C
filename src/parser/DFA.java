package parser;

import domain.Production;
import domain.Symbol;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
public class DFA
{
	List<State> states;
	Map<Pair<State, Symbol>, State> transition;
	
	public DFA(List<Production> productions)
	{
	
	}
	public State getNext(State state, Symbol input)
	{
		return transition.get(new Pair<>(state, input));
	}
}
