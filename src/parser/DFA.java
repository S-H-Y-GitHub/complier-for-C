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
	
	public DFA(List<Production> productions, Set<Terminal> terminals)
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
				Set<LR1Item> newState = new HashSet<>();//跳转到这个新状态
				//完善这个新状态的信息
				LR1Item temp = new LR1Item();
				temp.production = item.production;
				temp.lookaheads = item.lookaheads;
				temp.dotPosition = item.dotPosition + 1;
				//检查是否为已经生成过的状态节点
				Boolean checked = false;
				for (Set<LR1Item> finishedState : states)
					if (finishedState.contains(temp))
					{
						checked = true;
						break;
					}
				if(checked)
					continue;
				newState.add(temp);
				//对这个节点求闭包
				closure(state);
				//把这个状态加到DFA上
				states.add(state);
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
	
	}
	public int getNext(Set state, Symbol input)
	{
		return transition.get(new Pair<>(state, input));
	}
}
