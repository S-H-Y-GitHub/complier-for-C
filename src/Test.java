import com.sun.corba.se.spi.orbutil.closure.Closure;
import domain.Production;
import domain.Symbol;
import domain.Terminal;
import domain.Variable;
import javafx.util.Pair;
import parser.Grammar;
import parser.LR1Item;

import java.util.*;
/**
 * Created by ZZY on 2017/4/10.
 */
public class Test
{
	public static void main(String[] args)
	{
		Grammar g = new Grammar();
		List<Production> p = g.getProductions();
		System.out.println(p);
	}
}
