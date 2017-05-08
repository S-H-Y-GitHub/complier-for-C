import model.Production;
import model.Terminal;
import parser.LR1Item;

import java.util.HashSet;

public class Test
{
	public static void main(String[] args)
	{
		Terminal a = new Terminal("a");
		Terminal b = new Terminal("b");
		HashSet<Terminal> c = new HashSet<>();
		HashSet<Terminal> d = new HashSet<>();
		c.add(a);
		c.add(b);
		d.add(a);
		d.add(b);
		Production p = new Production();
		LR1Item i1 = new LR1Item();
		i1.production = p;
		i1.dotPosition = 0;
		i1.lookaheads = c;
		LR1Item i2 = new LR1Item();
		i2.dotPosition = 0;
		i2.production = p;
		i2.lookaheads = d;
		System.out.println(i1.equals(i2));
		System.out.println(i1.hashCode());
		System.out.println(i2.hashCode());
	}
}
