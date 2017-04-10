import domain.Symbol;

import java.util.LinkedList;
/**
 * Created by ZZY on 2017/4/10.
 */
public class Test
{
	public static void main(String[] args)
	{
		LinkedList<String> s = new LinkedList<>();
		LinkedList<String> s1 = new LinkedList<>();
		LinkedList<String> s2 = new LinkedList<>();
		s2.add("a");
		System.out.println(s.hashCode());
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
	}
}
