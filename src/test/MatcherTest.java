/*
 * Created on 2004-4-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package test;

import junit.framework.TestCase;
import segment.Element;
import segment.Matcher;

/**
 * @author qianzhiqiang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MatcherTest extends TestCase
{
	Element[] lattice;
	Matcher matcher;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
		
		matcher = new Matcher();
		
		lattice = new Element[] {	new Element(20, 20),
									new Element(20, 10),
									new Element(20, 30),
									new Element(20, 40),
									new Element(20, 60),
									new Element(10, 20),
									new Element(22, 20),
									new Element(25, 20),
									new Element(60, 20),
									new Element(30, 20),
								};
	}
	
	public void testHouf()
	{
		Element[][] ret = matcher.houf(lattice, 3);
		
		printHouf(ret);
	}

	public static void printHouf(Element[][] ret)
	{
		for (int i=0; i<ret.length; i++)
		{
			System.out.println("LINE " + i + ": ");
			
			StringBuffer sb = new StringBuffer();
			
			for (int j=0; j<ret[i].length; j++)
			{
				sb.append("(x: " + ret[i][j].x + " , y: " + ret[i][j].y + ") ; ");
			}
				
			System.out.println(sb.toString());
		}
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
}
