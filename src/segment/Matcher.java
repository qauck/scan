package segment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
 * Created on 2004-4-2
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
/**
 * @author qianzhiqiang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public final class Matcher
{
	private static final double[] THETA;
	private static final int ROU = 1300;
	private static final int ANGEL = 90;
	
	private static final double EPS_SLOP = 0.1;
	private static final int EPS_GRAY = 1021;
	
	private static final int[] LINEAR_H = {-1, -1, -1, 2, 2, 2, -1, -1, -1}; 
	private static final int[] LINEAR_V = {-1, 2, -1, -1, 2, -1, -1, 2, -1}; 

	private static final int[] SOBEL_H = {-1, -2, -1, 0, 0, 0, 1, 2, 1}; 
	private static final int[] SOBEL_V = {-1, 0, 1, -2, 0, 2, -1, 0, 1}; 
	
	private static final int[] HOLT_1 = {0, 1, -1, 1, 1, -1, -1, -1, 0};
	private static final int[] HOLT_2 = {-1, 1, 0, -1, 1, 1, 0, -1, -1};
	private static final int[] HOLT_3 = {0, -1, -1, -1, 1, 1, -1, 1, 0};
	private static final int[] HOLT_4 = {-1, -1, 0, 1, 1, -1, 0, 1, -1};
	
	static
	{
		double[] theta = new double[90/ANGEL + 1];
		
		for (int i=0; i<=90/ANGEL; i++)
		{
			theta[i] = ANGEL * i * Math.PI / 180 ;
		}
		
		THETA = theta;
	}
	
	public Element[] linearH(Element[] lattice, int width)
	{
		Element[] rt = new Element[lattice.length];
		
		for (int i=0; i<lattice.length; i++)
		{
			Element e = (Element)lattice[i].clone();
			
			e.value2 = vector9product(get9Neighbour(lattice, i, width), LINEAR_H);
			e.value = (e.value2 & 0xFF) |
			((e.value2 & 0xFF) << 8) |
			((e.value2 & 0xFF) << 16);
			
			rt[i] = e;
		}
		
		return rt;
	}
	
	public Element[] linearV(Element[] lattice, int width)
	{
		Element[] rt = new Element[lattice.length];
		
		for (int i=0; i<lattice.length; i++)
		{
			Element e = (Element)lattice[i].clone();
			
			e.value2 = vector9product(get9Neighbour(lattice, i, width), LINEAR_V);
			e.value = (e.value2 & 0xFF) |
			((e.value2 & 0xFF) << 8) |
			((e.value2 & 0xFF) << 16);
			
			rt[i] = e;
		}
		
		return rt;
	}
	
	public Element[] linearC(Element[] lattice, int width)
	{
		Element[] rt = new Element[lattice.length];
		
		for (int i=0; i<lattice.length; i++)
		{
			Element e = (Element)lattice[i].clone();
			
			Element[] v9 = get9Neighbour(lattice, i, width);
			
			int hv = vector9product(v9, LINEAR_H);
			int vv = vector9product(v9, LINEAR_V);

			e.value2 = hv | vv;
			e.value = (e.value2 & 0xFF) |
			((e.value2 & 0xFF) << 8) |
			((e.value2 & 0xFF) << 16);
			
			rt[i] = e;
		}
		
		return rt;
	}
	
	public Element[] sobelH(Element[] lattice, int width)
	{
		Element[] rt = new Element[lattice.length];
		
		for (int i=0; i<lattice.length; i++)
		{
			Element e = (Element)lattice[i].clone();
			
			e.value2 = vector9product(get9Neighbour(lattice, i, width), SOBEL_H);
			e.value = (e.value2 & 0xFF) |
			((e.value2 & 0xFF) << 8) |
			((e.value2 & 0xFF) << 16);
			
			rt[i] = e;
		}
		
		return rt;
	}
	
	public Element[] sobelV(Element[] lattice, int width)
	{
		Element[] rt = new Element[lattice.length];
		
		for (int i=0; i<lattice.length; i++)
		{
			Element e = (Element)lattice[i].clone();
			
			e.value2 = vector9product(get9Neighbour(lattice, i, width), SOBEL_V);
			e.value = (e.value2 & 0xFF) |
			((e.value2 & 0xFF) << 8) |
			((e.value2 & 0xFF) << 16);
			
			rt[i] = e;
		}
		
		return rt;
	}
	
	public Element[] sobelC(Element[] lattice, int width)
	{
		Element[] rt = new Element[lattice.length];
		
		for (int i=0; i<lattice.length; i++)
		{
			Element e = (Element)lattice[i].clone();
			
			Element[] v9 = get9Neighbour(lattice, i, width);
			
			int hv = vector9product(v9, SOBEL_H);
			int vv = vector9product(v9, SOBEL_V);
			
			e.value2 = hv | vv;
			e.value = (e.value2 & 0xFF) |
			((e.value2 & 0xFF) << 8) |
			((e.value2 & 0xFF) << 16);
			
			rt[i] = e;
		}
		
		return rt;
	}
	
	public Element[] connect(Element[] lattice, int width)
	{
		for (int i=0; i<lattice.length; i++)
		{
			if (lattice[i].value2 == 0)
			{
				Element[] v9 = get9Neighbour(lattice, i, width);

				int hv = vector9product(v9, SOBEL_H);
				int vv = vector9product(v9, SOBEL_V);
				
				int dg = Math.abs(hv) + Math.abs(vv);
				
				double alf;
				
				if (vv==0)
				{
					alf = 0;
				}
				else
				{
					alf = Math.atan((double)vv/hv);
				}
				
				for (int j=0; j<v9.length; j++)
				{
					if (v9[j].value2 != 0)
					{
						int si = get9NeighbourIndex(i, j, width);
						
						if (si >=0 && si<lattice.length)
						{
							Element[] sv9 = get9Neighbour(lattice, si, width);
							
							int shv = vector9product(sv9, SOBEL_H);
							int svv = vector9product(sv9, SOBEL_V);
							
							double salf;
							
							if (svv==0)
							{
								salf = 0;
							}
							else
							{
								salf = Math.atan((double)svv/shv);
							}
							
							int deltaT = Math.abs(dg - Math.abs(shv) - Math.abs(svv));
							
							double deltaA = Math.abs(alf - salf);
							
							if (deltaA < EPS_SLOP && deltaT < EPS_GRAY)
							{
//								lattice[si].value2 = 0;
								lattice[si].value = -1;
							}
						}
					}
				}
			}
		}
		
		for (int i=0; i<lattice.length; i++)
		{
			if (lattice[i].value == -1)
			{
				lattice[i].value = 0;
				lattice[i].value2 = 0;
			}
		}
		
		return lattice;
	}
	
	public Element[][] houf(Element[] lattice, int precision)
	{
		ArrayList[][] accumu = new ArrayList[THETA.length][2*ROU+1];
		
		int rou;
		int arou;
		
		if (precision < 1)
		{
			precision = 1;
		}
		
		for (int i=0; i<THETA.length; i++)
		{
			for (int j=0; j<lattice.length; j++)
			{
				rou = (int)Math.round(lattice[j].x * Math.cos(THETA[i]) + lattice[j].y * Math.sin(THETA[i]));
				
				if (rou <= ROU && rou>= -ROU)
				{
					arou = rou + ROU;

					if (accumu[i][arou] == null)
					{
						accumu[i][arou] = new ArrayList();
					}
					
					accumu[i][arou].add(lattice[j]);
				}
			}
		}
		
		ArrayList ret = new ArrayList();

		for (int i=0; i<THETA.length; i++)
		{
			for (int j=0; j<2*ROU+1; j++)
			{
				if (accumu[i][j] != null && accumu[i][j].size() > precision)
				{
					Element[] eles = (Element[])accumu[i][j].toArray(new Element[0]);
					Arrays.sort(eles);

					ret.add(eles);
				}
			}
		}
		
		return (Element[][])ret.toArray(new Element[0][0]);
	}
	
	public Element[][] aggregate(Element[][] sortedData, int distance)
	{
		ArrayList al = new ArrayList();

		for (int i=0; i<sortedData.length; i++)
		{
			ArrayList as = new ArrayList();

			for (int j=0; j<sortedData[i].length; j++)
			{
				if (j==0)
				{
					as.add(sortedData[i][0]);
					
				}
				else
				{
					if (distance(sortedData[i][j], sortedData[i][j-1]) < distance)
					{
						as.add(sortedData[i][j]);
					}
					else
					{
						al.add((Element[])as.toArray(new Element[0]));
						
						as = new ArrayList();
						
						as.add(sortedData[i][j]);
					}
				}
				
			}

			al.add((Element[])as.toArray(new Element[0]));

		}
		
		return (Element[][])al.toArray(new Element[0][0]);
	}
	
	public Element[][] refine(Element[][] sortedData)
	{
		ArrayList al = new ArrayList();
		
		for (int i=0; i<sortedData.length; i++)
		{
			if (sortedData[i].length > 1)
			{
				Element[] f = new Element[] {sortedData[i][0], sortedData[i][sortedData[i].length-1]};
				
				al.add(f);
			}
		}
		
		return (Element[][])al.toArray(new Element[0][0]);
	}
	
	public Element[][] filter(Element[][] refinedData, int distance)
	{
		ArrayList al = new ArrayList();
		
		for (int i=0; i<refinedData.length; i++)
		{
			if (refinedData[i].length > 1)
			{
				if (distance(refinedData[i][0], refinedData[i][1]) > distance)
				{
					al.add(refinedData[i]);
				}
			}
		}
		
		return (Element[][])al.toArray(new Element[0][0]);
	}
	
	public Element[][] combine(Element[][] refinedData, int precision)
	{
		Element[][] data = refinedData;
		
		ArrayList med = new ArrayList();
		
		boolean merged = false;
		boolean match = false;
		
		for (int i=0; i<data.length; i++)
		{
			Element[] line = data[i];

			merged = false;
			
			for (int j=0; j<med.size(); j++)
			{
				ArrayList list = (ArrayList)med.get(j);
				
				Element[] lead = (Element[])list.get(0);
				
				match = false;
				
				if (isNeighbour(line[0], lead[0], precision) && isNeighbour(line[1], lead[1], precision))
				{
					match = true;
					
					list.add(line);
					
					merged = true;
					
					break;
				}
				
				if (!match && list.size()>1)
				{
					Element[] tail = (Element[])list.get(list.size()-1);
					
					if (isNeighbour(line[0], tail[0], precision) && isNeighbour(line[1], tail[1], precision))
					{
						list.add(line);
						
						merged = true;
						
						break;
					}
				}
			}
			
			if (!merged)
			{
				ArrayList lg  = new ArrayList();
				
				lg.add(line);
				
				med.add(lg);
			}
		}
		
		ArrayList rt = new ArrayList();
		
		for (int i=0; i<med.size(); i++)
		{
			ArrayList tmp = (ArrayList)med.get(i);
			
			Element[][] ltmp = (Element[][])tmp.toArray(new Element[0][0]);
			
			Arrays.sort(ltmp, new Comparator()
			{
				public int compare(Object o1, Object o2)
				{
					Element[] a = (Element[])o1;
					Element[] b = (Element[])o2;

					if (a[0].x == a[1].x)
					{
						//vertical;
						return (a[0].x<b[0].x)?-1:((a[0].x==b[0].x)?0:1);
					}
					else
					{
						//horizontal;
						return (a[0].y<b[0].y)?-1:((a[0].y==b[0].y)?0:1);
					}
				}
			});
			
			rt.add(ltmp[ltmp.length/2]);
		}
		
		return (Element[][])rt.toArray(new Element[0][0]);
	}
	
	public Element[][] combine2(Element[][] refinedData, int precision)
	{
		Element[][] data = refinedData;
		
		ArrayList med = new ArrayList();
		
		boolean merged = false;
		boolean match = false;
		
		for (int i=0; i<data.length; i++)
		{
			Element[] line = data[i];

			merged = false;
			
			for (int j=0; j<med.size(); j++)
			{
				ArrayList list = (ArrayList)med.get(j);
				
				Element[] lead = (Element[])list.get(0);
				
				match = false;
				
				if (isNeighbour(line[0], lead[0], precision) && isNeighbour(line[1], lead[1], precision))
				{
					match = true;
					
					list.add(line);
					
					merged = true;
					
					break;
				}
				
				if (!match && list.size()>1)
				{
					Element[] tail = (Element[])list.get(list.size()-1);
					
					if (isNeighbour(line[0], tail[0], precision) && isNeighbour(line[1], tail[1], precision))
					{
						list.add(line);
						
						merged = true;
						
						break;
					}
				}
			}
			
			if (!merged)
			{
				ArrayList lg  = new ArrayList();
				
				lg.add(line);
				
				med.add(lg);
			}
		}
		
		ArrayList rt = new ArrayList();
		
		for (int i=0; i<med.size(); i++)
		{
			ArrayList tmp = (ArrayList)med.get(i);
			
			Element[][] ltmp = (Element[][])tmp.toArray(new Element[0][0]);
			
			Arrays.sort(ltmp, new Comparator()
			{
				public int compare(Object o1, Object o2)
				{
					Element[] a = (Element[])o1;
					Element[] b = (Element[])o2;

					if (a[0].x == a[1].x)
					{
						//vertical;
						return (a[0].x<b[0].x)?-1:((a[0].x==b[0].x)?0:1);
					}
					else
					{
						//horizontal;
						return (a[0].y<b[0].y)?-1:((a[0].y==b[0].y)?0:1);
					}
				}
			});
			
			rt.add(ltmp[0]);
				
			if (ltmp.length>1)
			{
				rt.add(ltmp[ltmp.length-1]);
			}
		}
		
		return (Element[][])rt.toArray(new Element[0][0]);
	}
	
	public Element[] zhangSuenThinning(Element[] data, int width)
	{
		int height = data.length / width;
		
		int tag[] = new int[data.length];
		
		boolean changed = true;
		
		while (changed)
		{
			changed = false;
			
			//sub-iterator1
			for (int i=0; i<data.length; i++)
			{
				if (data[i].value2 > 0)
				{
					continue;
				}
				
				Element[] v8n = get8Neighbour(data, i, width);
				int[] rt = get8NeighbourConnectivity(v8n);
				
				if (rt[0] == 1 && (rt[1]>=2 && rt[1]<=6))
				{
					if ((v8n[2].value2>0 || v8n[4].value2>0 || v8n[6].value2>0) &&
							(v8n[0].value2>0 || v8n[4].value2>0 || v8n[6].value2>0))
					{
						tag[i] = 1;
						
						changed = true;
					}
				}
			}

			for (int i=0; i<data.length; i++)
			{
				if (tag[i] == 1)
				{
					data[i].value2 = 255;
					data[i].value = 0x00ffffff;
				}
			}
			
			Arrays.fill(tag, 0);
			
			//sub-iterator2
			for (int i=0; i<data.length; i++)
			{
				if (data[i].value2 > 0)
				{
					continue;
				}
				
				Element[] v8n = get8Neighbour(data, i, width);
				int[] rt = get8NeighbourConnectivity(v8n);
				
				if (rt[0] == 1 && (rt[1]>=2 && rt[1]<=6))
				{
					if ((v8n[0].value2>0 || v8n[2].value2>0 || v8n[4].value2>0) &&
						(v8n[0].value2>0 || v8n[2].value2>0 || v8n[6].value2>0))
					{
						tag[i] = 1;
						
						changed = true;
					}
				}
			}

			for (int i=0; i<data.length; i++)
			{
				if (tag[i] == 1)
				{
					data[i].value2 = 255;
					data[i].value = 0x00ffffff;
				}
			}
			
			Arrays.fill(tag, 0);
		}
		
		return data;
	}
	
	public Element[] zhangSuenHoltThinning(Element[] data, int width)
	{
		data = zhangSuenThinning(data, width);
		
		int[] tag = new int[data.length];
		
		for (int i=0; i<data.length; i++)
		{
			if (data[i].value2==0)
			{
				Element[] v9 = get9Neighbour(data, i, width);
				
				if (matchTemplate(v9, HOLT_1) ||
					matchTemplate(v9, HOLT_2) ||
					matchTemplate(v9, HOLT_3) ||
					matchTemplate(v9, HOLT_4))
				{
					data[i].value2 = 255;
					data[i].value = 0x00ffffff;
				}
			}
		}
		
		return data;
	}
	
	private boolean matchTemplate(Element[] src, int[] template)
	{
		for (int i=0; i<src.length; i++)
		{
			if (template[i] != -1)
			{
				if ((template[i]==0 && src[i].value2>0) ||
					(template[i]==1 && src[i].value2==0))
				{
					//match;
				}
				else
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * @param data
	 * @return format: [connectivity | blackCount]
	 */
	private int[] get8NeighbourConnectivity(Element[] data)
	{
		int ct = 0;
		int bt = 0;
		
		for (int i=0; i<7; i++)
		{
			if (data[i].value2==0 && data[i+1].value2>0)
			{
				ct++;
			}
			
			if (data[i].value2==0)
			{
				bt++;
			}
		}
		
		if (data[7].value2==0 && data[0].value2>0)
		{
			ct++;
		}
		
		if (data[7].value2==0)
		{
			bt++;
		}
		
		return new int[] {ct, bt};
	}
	
	private boolean isNeighbour(Element a, Element b, int precision)
	{
		if (Math.abs(a.x - b.x) <= precision && Math.abs(a.y - b.y) <= precision)
		{
			return true;
		}
		
		return false;
	}
	
	private int distance(Element a, Element b)
	{
		return (int)Math.round(Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y)));
	}
	
	private int slopAngle(Element a, Element b)
	{
		return (int)(Math.atan((a.y-b.y)*1.0/(a.x-b.x))*180/Math.PI);
	}
	
	private int get9NeighbourIndex(int depIndex, int neighbourIndex, int width)
	{
		if (neighbourIndex < 0 || neighbourIndex > 8)
		{
			return -1;
		}
		
		if (neighbourIndex < 3)
		{
			return (depIndex - width) - (1 - neighbourIndex);
		}
		else
			if (neighbourIndex < 6)
		{
			return depIndex - (4 - neighbourIndex);
		}
		else
		{
			return (depIndex + width) - (7 - neighbourIndex);
		}
	}
	
	private Element[] get9Neighbour(Element[] lattice, int idx, int width)
	{
		int h = lattice.length / width;
		int y = idx / width;
		int x = idx - y * width; 
		
		Element dummy = new Element(-1, -1);
		
		Element[] rt = new Element[9];
		Arrays.fill(rt, dummy);
		
		if (x == 0)
		{
			if (y == 0)
			{
				rt[4] = lattice[0];
				rt[5] = lattice[1];
				rt[7] = lattice[width];
				rt[8] = lattice[width+1];
			}
			else
				if (y == h-1)
			{
				rt[1] = lattice[x+(y-1)*width];
				rt[2] = lattice[x+(y-1)*width+1];
				rt[4] = lattice[x+y*width];
				rt[5] = lattice[x+y*width+1];
			}
			else
			{
				rt[1] = lattice[x+(y-1)*width];
				rt[2] = lattice[x+(y-1)*width+1];
				rt[4] = lattice[x+y*width];
				rt[5] = lattice[x+y*width+1];
				rt[7] = lattice[x+(y+1)*width];
				rt[8] = lattice[x+(y+1)*width+1];
			}
		}
		else
			if (x == width-1)
		{
			if (y == 0)
			{
				rt[3] = lattice[width-2];
				rt[4] = lattice[width-1];
				rt[6] = lattice[2*width-2];
				rt[7] = lattice[2*width-1];
			}
			else
				if (y == h-1)
			{
				rt[0] = lattice[x+(y-1)*width-1];
				rt[1] = lattice[x+(y-1)*width];
				rt[3] = lattice[x+y*width-1];
				rt[4] = lattice[x+y*width];
			}
			else
			{
				rt[0] = lattice[x+(y-1)*width-1];
				rt[1] = lattice[x+(y-1)*width];
				rt[3] = lattice[x+y*width-1];
				rt[4] = lattice[x+y*width];
				rt[6] = lattice[x+(y+1)*width-1];
				rt[7] = lattice[x+(y+1)*width];
			}
		}
		else
		{
			if (y == 0)
			{
				rt[3] = lattice[x-1];
				rt[4] = lattice[x];
				rt[5] = lattice[x+1];
				rt[6] = lattice[x+width-1];
				rt[7] = lattice[x+width];
				rt[8] = lattice[x+width+1];
			}
			else
				if (y == h-1)
			{
				rt[0] = lattice[x+(y-1)*width-1];
				rt[1] = lattice[x+(y-1)*width];
				rt[2] = lattice[x+(y-1)*width+1];
				rt[3] = lattice[x+y*width-1];
				rt[4] = lattice[x+y*width];
				rt[5] = lattice[x+y*width+1];
			}
			else
			{
				rt[0] = lattice[x+(y-1)*width-1];
				rt[1] = lattice[x+(y-1)*width];
				rt[2] = lattice[x+(y-1)*width+1];
				rt[3] = lattice[x+y*width-1];
				rt[4] = lattice[x+y*width];
				rt[5] = lattice[x+y*width+1];
				rt[6] = lattice[x+(y+1)*width-1];
				rt[7] = lattice[x+(y+1)*width];
				rt[8] = lattice[x+(y+1)*width+1];
			}
		}
		
		return rt;
	}
	
	static Element[] get8Neighbour(Element[] lattice, int idx, int width)
	{
		int h = lattice.length / width;
		int y = idx / width;
		int x = idx - y * width; 
		
		Element dummy = new Element(-1, -1);
		dummy.value2 = 255;
		dummy.value = 0xffffff;
		
		Element[] rt = new Element[8];
		Arrays.fill(rt, dummy);
		
		if (x == 0)
		{
			if (y == 0)
			{
				rt[0] = lattice[1];
				rt[1] = lattice[width+1];
				rt[2] = lattice[width];
			}
			else
				if (y == h-1)
			{
				rt[0] = lattice[x+y*width+1];
				rt[6] = lattice[x+(y-1)*width];
				rt[7] = lattice[x+(y-1)*width+1];
			}
			else
			{
				rt[0] = lattice[x+y*width+1];
				rt[1] = lattice[x+(y+1)*width+1];
				rt[2] = lattice[x+(y+1)*width];
				rt[6] = lattice[x+(y-1)*width];
				rt[7] = lattice[x+(y-1)*width+1];
			}
		}
		else
			if (x == width-1)
		{
			if (y == 0)
			{
				rt[2] = lattice[2*width-1];
				rt[3] = lattice[2*width-2];
				rt[4] = lattice[width-2];
			}
			else
				if (y == h-1)
			{
				rt[4] = lattice[x+y*width-1];
				rt[5] = lattice[x+(y-1)*width-1];
				rt[6] = lattice[x+(y-1)*width];
			}
			else
			{
				rt[2] = lattice[x+(y+1)*width];
				rt[3] = lattice[x+(y+1)*width-1];
				rt[4] = lattice[x+y*width-1];
				rt[5] = lattice[x+(y-1)*width-1];
				rt[6] = lattice[x+(y-1)*width];
			}
		}
		else
		{
			if (y == 0)
			{
				rt[0] = lattice[x+1];
				rt[1] = lattice[x+width+1];
				rt[2] = lattice[x+width];
				rt[3] = lattice[x+width-1];
				rt[4] = lattice[x-1];
			}
			else
				if (y == h-1)
			{
				rt[0] = lattice[x+y*width+1];
				rt[4] = lattice[x+y*width-1];
				rt[5] = lattice[x+(y-1)*width-1];
				rt[6] = lattice[x+(y-1)*width];
				rt[7] = lattice[x+(y-1)*width+1];
			}
			else
			{
				rt[0] = lattice[x+y*width+1];
				rt[1] = lattice[x+(y+1)*width+1];
				rt[2] = lattice[x+(y+1)*width];
				rt[3] = lattice[x+(y+1)*width-1];
				rt[4] = lattice[x+y*width-1];
				rt[5] = lattice[x+(y-1)*width-1];
				rt[6] = lattice[x+(y-1)*width];
				rt[7] = lattice[x+(y-1)*width+1];
			}
		}
		
		return rt;
	}
	
	private int vector9product(Element[] data, int[] template)
	{
		int sum = 0;
		
		for (int i=0; i<9; i++)
		{
			sum += data[i].value2 * template[i];
		}
		
		return sum;
	}
}
