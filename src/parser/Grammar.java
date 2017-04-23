package parser;

import domain.*;
import javafx.util.Pair;

import java.io.FileReader;
import java.util.*;
public class Grammar
{
	List<Production> productions;
	List<Variable> variables;
	List<Terminal> terminals;
	HashMap<Pair<Variable,Integer>,Integer> go;
	HashMap<Pair<Terminal,Integer>,String> action;
	
	public Grammar()
	{
		productions = new ArrayList<>();
		variables = new LinkedList<>();
		terminals = new LinkedList<>();
		// TODO: 2017/3/27 编写C的文法产生式
		Variable s = new Variable("S`",false);
		Variable S = new Variable("S",false);
		Variable L = new Variable("L",false);
		Variable R = new Variable("R",false);
		variables.addAll(Arrays.asList(s,S,L,R));
		Terminal eq = new Terminal("=");
		Terminal mu = new Terminal("*");
		Terminal id = new Terminal("i");
		terminals.addAll(Arrays.asList(eq,mu,id));
		Production p0 = new Production(s,S);
		Production p1 = new Production(S,L,eq,R);
		Production p2 = new Production(S,R);
		Production p3 = new Production(L,mu,R);
		Production p4 = new Production(L,id);
		Production p5 = new Production(R,L);
		productions.addAll(Arrays.asList(p0, p1, p2, p3, p4, p5));
		System.out.println(productions);
		DFA dfa = new DFA(productions, variables, terminals);
		List<Set<LR1Item>> states = dfa.states;
		Map<Pair<Set<LR1Item>, Symbol>, Integer> transition = dfa.transition;
		for(int i = 0;i<states.size();i++)
		{
			Set<LR1Item> state = states.get(i);
			for (LR1Item item : state)//处理规约
			{
				if(item.dotPosition == item.production.right.size())
					for(Terminal terminal:item.lookaheads)
						action.put(new Pair<>(terminal,i),"r"+productions.indexOf(item.production));
			}
			for(Terminal terminal : terminals)//处理移进
			{
				Pair<Set<LR1Item>, Symbol> transKey = new Pair<>(state,terminal);
				if(transition.containsKey(transKey))
				{
					Pair<Terminal, Integer> actionKey = new Pair<>(terminal,i);
					action.put(actionKey,"S"+transition.get(transKey));
				}
			}
			for (Variable variable : variables)//处理goto表
			{
				Pair<Set<LR1Item>, Symbol> transKey = new Pair<>(state,variable);
				if(transition.containsKey(transKey))
				{
					Pair<Variable, Integer> actionKey = new Pair<>(variable,i);
					go.put(actionKey,transition.get(transKey));
				}
			}
		}
	}
	public List<Production> getProductions()
	{
		return productions;
	}
	
	public String getAction(int state, Terminal input)
	{
		return action.get(new Pair<>(input,state));
	}
	public Integer getGoto(int state, Variable input)
	{
		return go.get(new Pair<>(input,state));
	}
	
}