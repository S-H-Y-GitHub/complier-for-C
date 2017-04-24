package parser;

import domain.Production;
import domain.Symbol;
import domain.Terminal;
import domain.Variable;
import javafx.util.Pair;

import java.util.*;
public class DFA
{
	List<Set<LR1Item>> states;
	Map<Pair<Set<LR1Item>, Symbol>, Integer> transition;
	List<Production> productions;
	HashMap<Variable, HashSet<Terminal>> first;
	List<Variable> variables;
	List<Terminal> terminals;
	
	public DFA(List<Production> productions, List<Variable> variables, List<Terminal> terminals)
	{
		this.productions = productions;
		this.variables = variables;
		this.terminals = terminals;
		first = new HashMap<>();
		transition = new HashMap<>();
		
		states = new LinkedList<>();
		Set<LR1Item> state = new HashSet<>();
		LR1Item temp = new LR1Item();
		temp.production = productions.get(0);//把第一个生成式放进去
		temp.dotPosition = 0;
		temp.lookaheads = new HashSet<>();
		temp.lookaheads.add(new Terminal("end"));
		state.add(temp);
		closure(state);
		states.add(state);
		buildDFA(state);
	}
	public void buildDFA(Set<LR1Item> state)
	{
		//找出这个节点的子节点
		for (LR1Item item : state)
		{
			if (item.dotPosition < item.production.right.size())
			{
				Symbol change = item.production.right.get(item.dotPosition);//收到这个输入之后
				Set<LR1Item> newState = new HashSet<>();//跳转到这个新状态
				//完善这个新状态的信息
				for (LR1Item i : state)
				{
					if ((i.dotPosition < i.production.right.size())
							&&(i.production.right.get(i.dotPosition).equals(change)))
					{
						LR1Item temp = new LR1Item();
						temp.production = i.production;
						temp.lookaheads = i.lookaheads;
						temp.dotPosition = i.dotPosition + 1;
						newState.add(temp);
					}
				}
				//检查是否为已经生成过的状态节点
				Boolean checked = false;
				closure(newState);
				for (Set<LR1Item> finishedState : states)
					if (finishedState.equals(newState))
					{
						checked = true;
						transition.put(new Pair<>(state, change), states.indexOf(finishedState));
						break;
					}
				if (checked)
					continue;
				//把这个状态加到DFA上
				states.add(newState);
				//填状态转换表
				transition.put(new Pair<>(state, change), states.size() - 1);
				//递归遍历子节点的子节点
				buildDFA(newState);
			}
		}
	}
	
	public void closure(Set<LR1Item> state)
	{
		if (state.size() < 1)
			throw new IllegalArgumentException("做为函数closure参数传入的必须是一个已经恰当初始化的状态");
		Boolean changed = true;
		while (changed)
		{
			changed = false;
			for (int i = 0; i < state.size(); i++)
			{
				LR1Item item = (LR1Item) state.toArray()[i];//当前扫描到的LR(1)项目
				if (item.dotPosition == item.production.right.size())//如果这个项目已经是规约项目，就没得生成了
					continue;
				Symbol symbol = item.production.right.get(item.dotPosition);//拿来扩充的变量，就是圆点之后的语法变量
				for (Production p : productions)
				{
					if (p.left.equals(symbol))//找到一个需要加入状态中的产生式
					{
						LR1Item newItem = new LR1Item();
						if(p.right.size()==1&&p.right.get(0).s.equals(""))//若为生成空
							newItem.dotPosition = 1;
						else
							newItem.dotPosition = 0;
						newItem.production = p;
						if (item.dotPosition == item.production.right.size() - 1)
							newItem.lookaheads = item.lookaheads;
						else
							newItem.lookaheads = getFirst(item.production.right.get(item.dotPosition + 1));
						//将新生成的LR(1)项目插入状态中
						Boolean toAdd = false;
						for (LR1Item item1 : state)
						{
							if (item1.production.equals(newItem.production) && item1.dotPosition.equals(newItem.dotPosition))
							{
								toAdd = true;
								changed = item1.lookaheads.addAll(newItem.lookaheads);
								break;
							}
						}
						if (!toAdd)
						{
							changed = state.add(newItem);
							i = -1;
						}
					}
				}
			}
		}
	}
	public HashSet<Terminal> getFirst(Symbol symbol)
	{
		if (symbol instanceof Terminal)
		{
			HashSet<Terminal> result = new HashSet<>();
			result.add((Terminal) symbol);
			return result;
		}
		else if (first.get((Variable) symbol) != null)
		{
			return first.get(symbol);
		}
		else
		{
			Boolean changed = true;
			for (Variable v : variables)
				first.put(v, new HashSet<>());
			while (changed)
			{
				changed = false;
				for (Production p : productions)
				{
					if (p.left.nullable)
					{//可以为空
						changed = first.get(p.left).add(new Terminal(""));
					}
					for (Symbol s : p.right)
					{
						if (s instanceof Terminal)
						{
							changed = first.get(p.left).add((Terminal) s);
							break;
						}
						else if ((s instanceof Variable) && (((Variable) s).nullable))
						{
							HashSet<Terminal> temp = new HashSet<>(first.get((Variable) s));
							temp.remove(new Terminal(""));
							changed = first.get(p.left).addAll(temp);
						}
						else if (!((Variable) s).nullable)
						{
							changed = first.get(p.left).addAll(first.get((Variable) s));
							break;
						}
					}
					
				}
			}
			return first.get(symbol);
		}
	}
}
