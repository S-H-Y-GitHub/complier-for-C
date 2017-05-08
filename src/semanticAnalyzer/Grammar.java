package semanticAnalyzer;

import javafx.util.Pair;
import model.Production;
import model.Symbol;
import model.Terminal;
import model.Variable;

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
			Variable M1 = new Variable("M1", true);
			Variable M2 = new Variable("M2", true);
			Variable M3 = new Variable("M3", true);
			Variable M4 = new Variable("M4", true);
			Variable M5 = new Variable("M5", true);
			Variable M6 = new Variable("M6", true);
			Variable M7 = new Variable("M7", true);
			Variable M8 = new Variable("M8", true);
			Variable M9 = new Variable("M9", true);
			Variable M10 = new Variable("M10", true);
			Variable M11 = new Variable("M11", true);
			Variable M12 = new Variable("M12", true);
			Variable M13 = new Variable("M13", true);
			Variable M14 = new Variable("M14", true);
			Variable M15 = new Variable("M15", true);
			Variable ASSI = new Variable("$ASSI", false);
			Variable DECL = new Variable("$DECL", false);
			Variable TYPE = new Variable("$TYPE", false);
			Variable FOR = new Variable("$FOR", false);
			Variable IF = new Variable("$IF", false);
			Variable ELSE = new Variable("$ELSE", true);
			Variable EXPS = new Variable("$EXPS", false);
			Variable OP = new Variable("$OP", false);
			Variable BOOL = new Variable("$BOOL", false);
			Variable JUG = new Variable("$JUG", false);
			variables.addAll(Arrays.asList(Sp, S, A, B, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12,
					M13, M14, M15, ASSI, DECL, TYPE, FOR, IF, ELSE, EXPS, OP, BOOL, JUG));
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
			Production ps = new Production(Sp, S);
			Production p0 = new Production(S, TYPE, id, ls, rs, lb, M1, A, rb);
			Production p1 = new Production(A, B, A);
			Production p2 = new Production(A, nul);
			Production p3 = new Production(B, ret, EXPS, sc, M2);
			Production p4 = new Production(B, IF);
			Production p5 = new Production(B, FOR);
			Production p6 = new Production(B, DECL, sc);
			Production p7 = new Production(B, ASSI, sc);
			Production p8 = new Production(ASSI, EXPS, is, EXPS);
			Production p9 = new Production(DECL, TYPE, id, M3);
			Production p10 = new Production(DECL, TYPE, id, lm, num, rm, M4);
			Production p11 = new Production(TYPE, in);
			Production p12 = new Production(TYPE, cha);
			Production p13 = new Production(FOR, fo, ls, ASSI, sc, M11, BOOL, sc, M9, ASSI, rs, lb, M10, A, rb);
			Production p14 = new Production(IF, f, ls, BOOL, rs, lb, M7, A, rb, M8, ELSE);
			Production p15 = new Production(ELSE, els, lb, A, rb);
			Production p16 = new Production(ELSE, nul);
			Production p17 = new Production(EXPS, EXPS, OP, EXPS, M5);
			Production p20 = new Production(EXPS, id, M6);
			Production p48 = new Production(EXPS, id, lm, num, rm, M12);
			Production p21 = new Production(EXPS, num, M6);
			Production p22 = new Production(EXPS, str, M6);
			Production p23 = new Production(EXPS, chr, M6);
			Production p24 = new Production(OP, add);
			Production p25 = new Production(OP, mius);
			Production p26 = new Production(OP, mult);
			Production p27 = new Production(OP, divi);
			Production p28 = new Production(BOOL, EXPS, JUG, EXPS);
			Production p29 = new Production(JUG, eq);
			Production p30 = new Production(JUG, gt);
			Production p31 = new Production(JUG, lt);
			Production p32 = new Production(JUG, nq);
			Production p33 = new Production(M1, nul);
			Production p34 = new Production(M2, nul);
			Production p35 = new Production(M3, nul);
			Production p36 = new Production(M4, nul);
			Production p37 = new Production(M5, nul);
			Production p38 = new Production(M6, nul);
			Production p39 = new Production(M7, nul);
			Production p40 = new Production(M8, nul);
			Production p41 = new Production(M9, nul);
			Production p42 = new Production(M10, nul);
			Production p43 = new Production(M11, nul);
			Production p44 = new Production(M12, nul);
			Production p45 = new Production(M13, nul);
			Production p46 = new Production(M14, nul);
			Production p47 = new Production(M15, nul);
			//Production p38 = new Production(M6, nul);
			
			productions.addAll(Arrays.asList(ps, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10,
					p11, p12, p13, p14, p15, p16, p17, p20,
					p21, p22, p23, p24, p25, p26, p27, p28, p29, p30,
					p31, p32, p33, p34, p35, p36, p37, p38, p39, p40, p41,
					p42, p43, p44, p45, p46, p47, p48));
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
				if (item.dotPosition == item.production.right.size())
					for (Terminal terminal : item.lookaheads)
					{
						if (productions.indexOf(item.production) != 0)
							action.put(new Pair<>(terminal, i), "r" + productions.indexOf(item.production));
						else
							action.put(new Pair<>(terminal, i), "acc");
					}
				
			}
			for (Terminal terminal : terminals)//处理移进
			{
				Pair<Set<LR1Item>, Symbol> transKey = new Pair<>(state, terminal);
				if (transition.containsKey(transKey))
				{
					Pair<Terminal, Integer> actionKey = new Pair<>(terminal, i);
					action.put(actionKey, "S" + transition.get(transKey));
				}
			}
			for (Variable variable : variables)//处理goto表
			{
				Pair<Set<LR1Item>, Symbol> transKey = new Pair<>(state, variable);
				if (transition.containsKey(transKey))
				{
					Pair<Variable, Integer> actionKey = new Pair<>(variable, i);
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
		return action.get(new Pair<>(input, state));
	}
	Integer getGoto(int state, Variable input)
	{
		return go.get(new Pair<>(input, state));
	}
	
}