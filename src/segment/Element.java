/*
 * Created on 2004-4-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package segment;


public final class Element implements Comparable, Cloneable
{
	public int x;
	public int y;
	public int value;
	public int value2;
	
	public Element(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o)
	{
		Element e = (Element)o;
		
		if (this.y < e.y || (this.y == e.y && this.x < e.x))
		{
			return -1;
		}
		else
			if (this.y == e.y && this.x == e.x)
		{
			return 0;
			
		}
		else
		{
			return 1;
		}
	}
	
	public Object clone()
	{
		Element e = new Element(this.x, this.y);
		e.value = this.value;
		e.value2 = this.value2;
		
		return e;
	}
}