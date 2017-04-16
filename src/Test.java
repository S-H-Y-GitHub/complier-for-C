import com.sun.corba.se.spi.orbutil.closure.Closure;
import domain.Production;
import domain.Symbol;
import domain.Terminal;
import domain.Variable;
import javafx.util.Pair;
import parser.LR1Item;

import java.util.HashMap;
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
		HashMap<Integer,Integer> a  = new HashMap<>();
		int c = a.get(0);
		Integer d = a.put(0, 0);
		System.out.println(d);
	}
}
