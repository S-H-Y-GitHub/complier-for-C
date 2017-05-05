package parser;

import domain.Production;
import domain.Symbol;
import domain.Terminal;
import domain.Variable;
import javafx.util.Pair;

import java.util.*;
public class Grammar
{
	private List<Production> productions;
	private HashMap<Pair<Variable, Integer>, Integer> go;
	private HashMap<Pair<Terminal, Integer>, String> action;
	//public HashMap<Variable, HashSet<Terminal>> first;
	
	Grammar()
	{
		productions = new ArrayList<>();
		List<Variable> variables = new LinkedList<>();
		List<Terminal> terminals = new LinkedList<>();
		go = new HashMap<>();
		action = new HashMap<>();
		//一个简单的C语言文法产生式
		{
			Variable Sp = new Variable("$S`", false);
			Variable S = new Variable("$S", false);
			Variable A = new Variable("$A", true);
			Variable B = new Variable("$B", false);
			Variable ASSI = new Variable("$ASSI", false);
			Variable DECL = new Variable("$DECL", false);
			Variable TYPE = new Variable("$TYPE", false);
			Variable FOR = new Variable("$FOR", false);
			Variable IF = new Variable("$IF", false);
			Variable ELSE = new Variable("$ELSE", true);
			Variable EXPS = new Variable("$EXPS", false);
			Variable D = new Variable("$D", true);
			Variable C = new Variable("$C", false);
			Variable OP = new Variable("$OP", false);
			Variable BOOL = new Variable("$BOOL", false);
			Variable JUG = new Variable("$JUG", false);
			variables.addAll(Arrays.asList(Sp, S, A, B, ASSI, DECL, TYPE, FOR, IF, ELSE, EXPS, D, C, OP, BOOL, JUG));
			Terminal id = new Terminal("标识符");
			Terminal ret = new Terminal("RETURN");
			Terminal num = new Terminal("数字");
			Terminal ls = new Terminal("(");
			Terminal rs = new Terminal(")");
			Terminal lb = new Terminal("{");
			Terminal rb = new Terminal("}");
			Terminal nul = new Terminal("");
			Terminal sc = new Terminal(";");
			Terminal is = new Terminal("=");
			Terminal lm = new Terminal("[");
			Terminal rm = new Terminal("]");
			Terminal in = new Terminal("INT");
			Terminal cha = new Terminal("CHAR");
			Terminal fo = new Terminal("FOR");
			Terminal f = new Terminal("IF");
			Terminal els = new Terminal("ELSE");
			Terminal str = new Terminal("字符串");
			Terminal chr = new Terminal("字符");
			Terminal add = new Terminal("+");
			Terminal mius = new Terminal("-");
			Terminal mult = new Terminal("*");
			Terminal divi = new Terminal("/");
			Terminal eq = new Terminal("==");
			Terminal gt = new Terminal(">");
			Terminal lt = new Terminal("<");
			Terminal nq = new Terminal("!=");
			terminals.addAll(Arrays.asList(id, ret, num, ls, rs, lb, rb, nul, sc, is, lm, rm, in, cha, fo, f, els,
					str, chr, add, mius, mult, divi, eq, gt, lt, nq));
			Production ps = new Production(Sp,S);
			Production p0 = new Production(S, TYPE, id, ls, rs, lb, A, rb);
			Production p1 = new Production(A, B, A);
			Production p2 = new Production(A, nul);
			Production p3 = new Production(B, ret, EXPS, sc);
			Production p4 = new Production(B, IF);
			Production p5 = new Production(B, FOR);
			Production p6 = new Production(B, DECL, sc);
			Production p7 = new Production(B, ASSI, sc);
			Production p8 = new Production(ASSI, id, is, EXPS);
			Production p9 = new Production(DECL, TYPE, id);
			Production p10 = new Production(DECL, TYPE, id, lm, num, rm);
			Production p11 = new Production(TYPE, in);
			Production p12 = new Production(TYPE, cha);
			Production p13 = new Production(FOR, fo, ls, ASSI, sc, BOOL, sc, ASSI, rs, lb, A, rb);
			Production p14 = new Production(IF, f, ls, BOOL, rs, lb, A, rb, ELSE);
			Production p15 = new Production(ELSE, els, lb, A, rb);
			Production p16 = new Production(ELSE, nul);
			Production p17 = new Production(EXPS, C, D);
			Production p18 = new Production(D, OP, C, D);
			Production p19 = new Production(D, nul);
			Production p20 = new Production(C, id);
			Production p21 = new Production(C, num);
			Production p22 = new Production(C, str);
			Production p23 = new Production(C, chr);
			Production p24 = new Production(OP, add);
			Production p25 = new Production(OP, mius);
			Production p26 = new Production(OP, mult);
			Production p27 = new Production(OP, divi);
			Production p28 = new Production(BOOL, EXPS, JUG, EXPS);
			Production p29 = new Production(JUG, eq);
			Production p30 = new Production(JUG, gt);
			Production p31 = new Production(JUG, lt);
			Production p32 = new Production(JUG, nq);
			productions.addAll(Arrays.asList(ps,p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16,
					p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32));
		}
		DFA dfa = new DFA(productions, variables);
		List<Set<LR1Item>> states = dfa.states;
		Map<Pair<Set<LR1Item>, Symbol>, Integer> transition = dfa.transition;
		//first = dfa.first;
		for (int i = 0; i < states.size(); i++)
		{
			Set<LR1Item> state = states.get(i);
			for (LR1Item item : state)//处理规约
			{
				if(item.dotPosition == item.production.right.size())
					for(Terminal terminal:item.lookaheads)
					{
						if(productions.indexOf(item.production)!=0)
							action.put(new Pair<>(terminal,i),"r"+productions.indexOf(item.production));
						else
							action.put(new Pair<>(terminal,i),"acc");
					}
					
			}
			for(Terminal terminal : terminals)//处理移进
			{
				Pair<Set<LR1Item>, Symbol> transKey = new Pair<>(state,terminal);
				if(transition.containsKey(transKey))
				{
					Pair<Terminal, Integer> actionKey = new Pair<>(terminal,i);
					action.put(actionKey, "S" + transition.get(transKey));
				}
			}
			for (Variable variable : variables)//处理goto表
			{
				Pair<Set<LR1Item>, Symbol> transKey = new Pair<>(state,variable);
				if(transition.containsKey(transKey))
				{
					Pair<Variable, Integer> actionKey = new Pair<>(variable,i);
					go.put(actionKey, transition.get(transKey));
				}
			}
		}
	}
	List<Production> getProductions()
	{
		return productions;
	}
	
	String getAction(int state, Terminal input)
	{
		return action.get(new Pair<>(input,state));
	}
	Integer getGoto(int state, Variable input)
	{
		return go.get(new Pair<>(input,state));
	}
	
}