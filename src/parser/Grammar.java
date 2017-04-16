package parser;

import domain.*;
import javafx.util.Pair;

import java.io.FileReader;
import java.util.*;
class Grammar
{
	List<Production> productions;
	List<Variable> variables;
	List<Terminal> terminals;
	HashMap<Variable, HashSet<Terminal>> first,follow;
	HashMap<Pair<Variable,Integer>,Integer> go;
	HashMap<Pair<Terminal,Integer>,String> action;
	
	public Grammar()
	{
		productions = new ArrayList<>();
		variables = new LinkedList<>();
		terminals = new LinkedList<>();
		// TODO: 2017/3/27 编写C的文法产生式
		
		
		first = new HashMap<>();
		// TODO: 2017/4/16 求first表
		DFA dfa = new DFA(productions, first);
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
	
	@Deprecated
	HashMap<Variable, Set<Terminal>> getFirst()
	{
		HashMap<Variable,Set<Terminal>> f = new HashMap<>();
		Boolean changed = true;
		for(Variable v:variables)
			f.put(v,new HashSet<>());
		while (changed)
		{
			changed = false;
			for(Production p : productions)
			{
				// TODO: 2017/3/27 what about epsilon?
				if(p.right.get(0) instanceof Terminal)
				{//是个终结符
					changed |= f.get(p.left).add((Terminal) p.right.get(0));
				}
				else if(p.right.get(0) instanceof Variable)
				{//是个变量
					changed |= f.get(p.left).addAll(f.get((Variable) p.right.get(0)));
				}
				else
					System.err.println("文法定义有误");
			}
		}
		return f;
	}
	@Deprecated
	HashMap<Variable, Set<Terminal>> getFollow()
	{
		HashMap<Variable,Set<Terminal>> f = new HashMap<>();
		for(Variable v:variables)
			f.put(v,new HashSet<>());
		
		return f;
	}
	
}