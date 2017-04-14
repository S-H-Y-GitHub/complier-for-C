import domain.Production;
import domain.Symbol;
import domain.Terminal;
import domain.Variable;
import javafx.util.Pair;
import parser.LR1Item;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
/**
 * Created by ZZY on 2017/4/10.
 */
public class Test
{
	public static void main(String[] args)
	{
		Set<LR1Item> a  = new HashSet<>();
		LR1Item temp = new LR1Item();
		temp.production = new Production();
		temp.production.left = new Variable("abc");
		temp.production.right = new LinkedList<>();
		temp.production.right.add(new Terminal("cde"));
		temp.lookaheads = new HashSet<>();
		temp.lookaheads.add(new Terminal("end"));
		temp.dotPosition = 0;
		a.add(temp);
		Set<LR1Item> b  = new HashSet<>();
		temp = new LR1Item();
		temp.production = new Production();
		temp.production.left = new Variable("abc");
		temp.production.right = new LinkedList<>();
		temp.production.right.add(new Terminal("cde"));
		temp.lookaheads = new HashSet<>();
		temp.lookaheads.add(new Terminal("end"));
		temp.dotPosition = 0;
		b.add(temp);
		Set<LR1Item> c  = new HashSet<>();
		temp = new LR1Item();
		temp.production = new Production();
		temp.production.left = new Variable("abc");
		temp.production.right = new LinkedList<>();
		temp.production.right.add(new Terminal("cde"));
		temp.lookaheads = new HashSet<>();
		temp.lookaheads.add(new Terminal("end1"));
		temp.dotPosition = 0;
		c.add(temp);
		System.out.println(a.equals(b));
		System.out.println(a.equals(c));
		System.out.println(b.equals(c));
	}
}
