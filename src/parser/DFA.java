package parser;

import domain.Production;
import domain.Symbol;
import domain.Terminal;
import javafx.util.Pair;

import java.util.*;
public class DFA
{
	List<Set<LR1Item>> states;
	Map<Pair<Set<LR1Item>, Symbol>, Integer> transition;
	List<Production> productions;
	
	public DFA(List<Production> productions, Set<Terminal> terminals)
	{
		this.productions = productions;
		states = new LinkedList<>();
		Set<LR1Item> state = new HashSet<>();
		LR1Item temp = new LR1Item();
		temp.production = productions.get(0);//把第一个生成式放进去
		temp.dotPosition = 0;
		temp.lookaheads = new HashSet<>();
		temp.lookaheads.add(new Terminal("end"));
		state.add(temp);
		
		buildDFA(state);
		for (Terminal terminal : terminals)
		{
			Set<LR1Item> next = go(state, terminal);
		}
		
	}
	private void buildDFA(Set<LR1Item> state)
	{
		//找出这个节点的子节点
		for (LR1Item item : state)
		{
			if (item.dotPosition < item.production.right.size() - 1)
			{
				Symbol change = item.production.right.get(item.dotPosition);//收到这个输入之后
				Pair<Set<LR1Item>, Symbol> key = new Pair<>(state, change);
				Set<LR1Item> newState = new HashSet<>();//跳转到这个新状态
				//完善这个新状态的信息
				for (LR1Item i : state)
				{
					if (i.production.right.get(item.dotPosition).equals(change))
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
				for (Set<LR1Item> finishedState : states)
					if (finishedState.contains(newState.iterator().next()))
					{
						checked = true;
						transition.put(new Pair<>(state, change), states.indexOf(finishedState));
						break;
					}
				if (checked)
					continue;
				//没有被生成过,对这个状态求闭包
				closure(newState);
				//把这个状态加到DFA上
				states.add(newState);
				//填状态转换表
				transition.put(new Pair<>(state, change), states.size() - 1);
				//递归遍历子节点的子节点
				buildDFA(newState);
			}
		}
	}
	private int linkState(Set<LR1Item> state)
	{
		return 0;
	}
	private Set<LR1Item> go(Set<LR1Item> state, Terminal terminal)
	{
		return null;
	}
	private void closure(Set<LR1Item> state)
	{
		if (state.size() < 1)
			throw new IllegalArgumentException("做为函数closure参数传入的必须是一个已经恰当初始化的状态");
		LR1Item item = state.iterator().next();
		
	}
	public int getNext(Set<LR1Item> state, Symbol input)
	{
		return transition.get(new Pair<>(state, input));
	}
}
