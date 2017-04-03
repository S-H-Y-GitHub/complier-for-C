package parser;

import domain.*;

import java.util.*;
class Grammar
{
	List<Production> productions;
	List<Variable> V;
	List<Terminal> T;
	HashMap<Variable, List<Variable>> first,follow;
	
	Grammar()
	{
		// TODO: 2017/3/27 编写C的文法产生式
		productions = new LinkedList<>();
	}
	
	HashMap<Variable, Set<Terminal>> getFirst()
	{
		HashMap<Variable,Set<Terminal>> f = new HashMap<>();
		Boolean changed = true;
		for(Variable v:V)
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
	HashMap<Variable, Set<Terminal>> getFollow()
	{
		HashMap<Variable,Set<Terminal>> f = new HashMap<>();
		for(Variable v:V)
			f.put(v,new HashSet<>());
		
		return f;
	}
	
}



