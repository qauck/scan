package segment;

import java.util.ArrayList;
import java.util.Arrays;

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
public final class Analyzer
{
	private static final int[][][] SIMSUN_9_NUMBER_ORIGIN_FEATURE = {
			/*0*/
			{{1, 2, 1}, {1, 2, 1}},
			/*1*/
			{{1}, {2, 1}},
			/*2*/
			{{1, 2, 1}, {2, 3, 2}},
			/*3*/
			{{1, 2, 1, 2, 1}, {2, 3, 2}},
			/*4*/
			{{1, 2, 1}, {1, 2, 1, 2}},
			/*5*/
			{{1, 2, 1}, {2, 3, 2}},
			/*6*/
			{{1, 2, 1, 2, 1}, {1, 3, 1}},
			/*7*/
			{{1, 2, 1}, {1, 2, 1}},
			/*8*/
			{{1, 2, 1, 2, 1}, {2, 3, 2}},
			/*9*/
			{{1, 2, 1, 2, 1}, {1, 3, 1}},
	};
	
	private static final int[][][] SIMSUN_9_NUMBER_ZSHOLT_FEATURE = {
			/*0*/
			{{1, 2, 1}, {1, 2, 1}},
			/*1*/
			{{1}, {1}},
			/*2*/
			{{1, 2, 1}, {2, 3, 2}},
			/*3*/
			{{1, 2, 1, 2, 1}, {2, 3, 2}},
			/*4*/
			{{1, 2, 1}, {1, 2}},
			/*5*/
			{{1, 2, 1}, {2, 3, 2}},
			/*6*/
			{{1, 2, 1}, {2, 3, 1}},
			/*7*/
			{{1}, {1, 2, 1}},
			/*8*/
			{{1, 2, 1, 2, 1}, {2, 3, 2}},
			/*9*/
			{{1, 2, 1}, {1, 3, 2}},
	};
	
	public int[] splitHorizontal(Element[] data , int width)
	{
		int[] accumu = new int[width];
		
		int height = data.length / width;
		
		for (int i=0; i<width; i++)
		{
			for (int j=0; j<height; j++)
			{
				if (data[j*width+i].value2==0)
				{
					accumu[i] ++;
				}
			}
		}
		
		ArrayList al = new ArrayList();
		
		boolean headed = false;
		
		for (int i=0; i<accumu.length; i++)
		{
			if (accumu[i]>0 && !headed)
			{
				if (i>0 && accumu[i-1]==0)
				{
					al.add(new Integer(i-1));
				}
				else
				{
					al.add(new Integer(i));
				}
				
				headed = true;
			}
			else
				if (accumu[i]==0 && headed)
			{
				al.add(new Integer(i));
				
				headed = false;
			}
		}
		
		Integer[] rto = (Integer[])al.toArray(new Integer[0]);
		
		int[] rt = new int[rto.length];
		
		for (int i=0; i<rt.length; i++)
		{
			rt[i] = rto[i].intValue();
		}

		return rt;
	}
	
	public int[] splitVertical(Element[] data, int width)
	{
		int height = data.length / width;

		int[] accumu = new int[height];
		
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				if (data[i*width+j].value2==0)
				{
					accumu[i] ++;
				}
			}
		}
		
		ArrayList al = new ArrayList();
		
		boolean headed = false;
		
		for (int i=0; i<accumu.length; i++)
		{
			if (accumu[i]>0 && !headed)
			{
				if (i>0 && accumu[i-1]==0)
				{
					al.add(new Integer(i-1));
				}
				else
				{
					al.add(new Integer(i));
				}
				
				headed = true;
			}
			else
				if (accumu[i]==0 && headed)
			{
				al.add(new Integer(i));
				
				headed = false;
			}
		}
		
		Integer[] rto = (Integer[])al.toArray(new Integer[0]);
		
		int[] rt = new int[rto.length];
		
		for (int i=0; i<rt.length; i++)
		{
			rt[i] = rto[i].intValue();
		}

		return rt;
	}
	
	
	/**
	 * @param data
	 * @param width
	 * @return format: [h11|h12|v11|v12|....|v1n]
	 * 					[h21|h22|v21|v22|....|v2n]
	 * 					.......
	 * 					[hm1|hm2|vm1|vm2|....|vmn]
	 */
	public int[][] splitH2V(Element[] data, int width)
	{
		int[] hs = splitHorizontal(data, width);
		
		int[][] rt = new int[hs.length/2][];
		
		for (int i=0; i<hs.length/2; i++)
		{
			Element[] hcropData = cropDataH(data, hs[i*2], hs[i*2+1], width);
			
			int[] vs = splitVertical(hcropData, hs[i*2+1]-hs[i*2]+1);
			
			int[] srt = new int[vs.length + 2];
			
			srt[0] = hs[i*2];
			srt[1] = hs[i*2+1];
			
			System.arraycopy(vs, 0, srt, 2, vs.length);
			
			rt[i] = srt;
		}
		
		return rt;
	}
	
	/**
	 * @param data
	 * @param width
	 * @return format: [v11|v12|h11|h12|....|h1n]
	 * 					[v21|v22|h21|h22|....|h2n]
	 * 					.......
	 * 					[vm1|vm2|hm1|hm2|....|hmn]
	 */
	public int[][] splitV2H(Element[] data, int width)
	{
		int[] vs = splitVertical(data, width);
		
		int[][] rt = new int[vs.length/2][];
		
		for (int i=0; i<vs.length/2; i++)
		{
			Element[] vcropData = cropDataV(data, vs[i*2], vs[i*2+1], width);
			
			int[] hs = splitHorizontal(vcropData, width);
			
			int[] srt = new int[hs.length + 2];
			
			srt[0] = vs[i*2];
			srt[1] = vs[i*2+1];
			
			System.arraycopy(hs, 0, srt, 2, hs.length);
			
			rt[i] = srt;
		}
		
		return rt;
	}
	
	public int[][] centroid(Element[] data, int width, int splitMode, int[][] splitData)
	{
		if (splitData != null)
		{
			ArrayList al = new ArrayList();
			
			for (int i=0; i<splitData.length; i++)
			{
				int[] v1 = splitData[i];
				
				if (splitMode == 0)
				{
					//hv-mode
					int x1 = v1[0];
					int x2 = v1[1];
					
					for (int j=1; j<v1.length/2; j++)
					{
						Element[] sdata = cropData(data, x1, x2, v1[j*2], v1[j*2+1], width);
						
						int[] cd = calcCentroid(sdata, x2-x1+1); 
						
						cd[0] += x1;
						cd[1] += v1[j*2];
						
						al.add(cd);
					}
				}
				else
				{
					//vh-mode
					int y1 = v1[0];
					int y2 = v1[1];
					
					for (int j=1; j<v1.length/2; j++)
					{
						Element[] sdata = cropData(data, v1[j*2], v1[j*2+1], y1, y2, width);
						
						int[] cd = calcCentroid(sdata, v1[j*2+1]-v1[j*2]+1);
						
						cd[0] += v1[j*2];
						cd[1] += y1;
						
						al.add(cd);
					}
				}
			}
			
			return (int[][])al.toArray(new int[0][0]);
			
		}
		
		return new int[0][0];
	}
	
	public Element[] normalize(Element[] data, int width, int splitMode, int[][] splitData)
	{
		if (splitData != null)
		{
			for (int i=0; i<splitData.length; i++)
			{
				int[] v1 = splitData[i];
				
				if (splitMode == 0)
				{
					//hv-mode
					int x1 = v1[0];
					int x2 = v1[1];
					
					for (int j=1; j<v1.length/2; j++)
					{
						Element[] sdata = cropData(data, x1, x2, v1[j*2], v1[j*2+1], width);
						
						Element[] nData = normalizeData(sdata, x2-x1+1, 7, 10);
						
						for (int k=x1; k<=x2; k++)
						{
							for (int l=v1[j*2]; l<=v1[j*2+1]; l++)
							{
								if (k-x1<7 && l-v1[j*2]<10)
								{
									data[l*width+k].value = nData[(l-v1[j*2])*7+k-x1].value;
									data[l*width+k].value2 = nData[(l-v1[j*2])*7+k-x1].value2;
								}
								else
								{
									data[l*width+k].value = 0xffffff;
									data[l*width+k].value2 = 255;
								}
							}
						}
						
					}
				}
				else
				{
					//vh-mode
					int y1 = v1[0];
					int y2 = v1[1];
					
					for (int j=1; j<v1.length/2; j++)
					{
						Element[] sdata = cropData(data, v1[j*2], v1[j*2+1], y1, y2, width);

						Element[] nData = normalizeData(sdata, v1[j*2+1]-v1[j*2]+1, 7, 10);

						for (int k=v1[j*2]; k<=v1[j*2+1]; k++)
						{
							for (int l=y1; l<=y2; l++)
							{
								if (k-v1[j*2]<7 && l-y1<10)
								{
									data[l*width+k].value = nData[(l-y1)*7+k-v1[j*2]].value;
									data[l*width+k].value2 = nData[(l-y1)*7+k-v1[j*2]].value2;
								}
								else
								{
									data[l*width+k].value = 0xffffff;
									data[l*width+k].value2 = 255;
								}
							}
						}
					}
				}
			}
			
		}
		
		return data;
	}
	
	public String identifyNumber(Element[] data, int width, int splitMode, int[][] splitData)
	{
		if (splitData != null)
		{
			StringBuffer sb = new StringBuffer("");
			
			for (int i=0; i<splitData.length; i++)
			{
				int[] v1 = splitData[i];
				
				if (splitMode == 0)
				{
					//hv-mode
					int x1 = v1[0];
					int x2 = v1[1];
					
					for (int j=1; j<v1.length/2; j++)
					{
						Element[] sdata = cropData(data, x1, x2, v1[j*2], v1[j*2+1], width);
						
						int[] vf = calcVFeature(sdata, x2-x1+1); 
						int[] hf = calcHFeature(sdata, x2-x1+1);
						
						String s = matchFeature(vf, hf);
						
						sb.append(s);
					}
				}
				else
				{
					//vh-mode
					int y1 = v1[0];
					int y2 = v1[1];
					
					for (int j=1; j<v1.length/2; j++)
					{
						Element[] sdata = cropData(data, v1[j*2], v1[j*2+1], y1, y2, width);
						
						int[] vf = calcVFeature(sdata, v1[j*2+1]-v1[j*2]+1); 
						int[] hf = calcHFeature(sdata, v1[j*2+1]-v1[j*2]+1);
						
						String s = matchFeature(vf, hf);
						
						sb.append(s);
					}
				}
				
				sb.append("\r\n");
			}
			
			return sb.toString();
		}
		
		return "";
	}
	
	private Element[] normalizeData(Element[] data, int width, int destWidth, int destHeight)
	{
		int height = data.length/width;
		
		Element[] rData = new Element[destWidth*destHeight];
		
		for (int i=0; i<destWidth; i++)
		{
			for (int j=0; j<destHeight; j++)
			{
				int sx = (int)(((double)(i*width)) / destWidth + 0.5);
				int sy = (int)(((double)(j*height)) / destHeight + 0.5);
				
				Element dummy = new Element(i, j);

				if (sx < width && sy < height)
				{
					Element[] nbs = Matcher.get8Neighbour(data, sy*width+sx, width);

					dummy.value2 = (nbs[0].value2 + nbs[2].value2 + nbs[4].value2 + nbs[6].value2)/4;
									//+ nbs[1].value2 + nbs[3].value2 + nbs[5].value2 + nbs[7].value2)/8;
					dummy.value = (dummy.value2 & 0xFF) |
								((dummy.value2 & 0xFF) << 8) |
								((dummy.value2 & 0xFF) << 16);
					
//					dummy.value = data[sy*width+sx].value;
//					dummy.value2 = data[sy*width+sx].value2;
				}
				else
				{
					dummy.value = 0xffffff;
					dummy.value2 = 255;
				}

				rData[j*destWidth+i] = dummy;
			}
		}
		
		return rData;
	}
	
	private int[] calcCentroid(Element[] data, int width)
	{
		int height = data.length/width;
		
		int nc = 0;
		int xc = 0;
		int yc = 0;
		
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				if (data[i*width+j].value2==0)
				{
					nc++;
					
					xc += j;
					yc += i;
				}
			}
		}
		
		return new int[]{xc/nc, yc/nc};
	}
	
	private String matchFeature(int[] vFeature, int[] hFeature)
	{
		for (int i=0; i<SIMSUN_9_NUMBER_ZSHOLT_FEATURE.length; i++)
		{
			if (Arrays.equals(SIMSUN_9_NUMBER_ZSHOLT_FEATURE[i][0], vFeature) &&
				Arrays.equals(SIMSUN_9_NUMBER_ZSHOLT_FEATURE[i][1], hFeature))
			{
				return String.valueOf(i);
			}
		}
		
		return "?";
	}
	
	private int[] calcVFeature(Element[] data, int width)
	{
		int height = data.length/width;
		
		int[] tag = new int[height];
		
		boolean headed = false;
		
		for (int i=0; i<tag.length; i++)
		{
			headed = false;
			
			for (int j=0; j<width; j++)
			{
				if (data[i*width+j].value2==0 && !headed)
				{
					tag[i]++;
					
					headed = true;
				}
				else
					if (data[i*width+j].value2>0 && headed)
				{
					headed = false;
				}
			}
		}
		
		return shrinkFeature(tag);
	}
	
	private int[] calcHFeature(Element[] data, int width)
	{
		int height = data.length/width;
		
		int[] tag = new int[width];
		
		boolean headed = false;
		
		for (int i=0; i<tag.length; i++)
		{
			headed = false;
			
			for (int j=0; j<height; j++)
			{
				if (data[j*width+i].value2==0 && !headed)
				{
					tag[i]++;
					
					headed = true;
				}
				else
					if (data[j*width+i].value2>0 && headed)
				{
					headed = false;
				}
			}
		}
		
		return shrinkFeature(tag);
	}
	
	private int[] shrinkFeature(int[] tag)
	{
		ArrayList al = new ArrayList();
		
		int sc = -1;
		
		for (int i=0; i<tag.length; i++)
		{
			if (tag[i]==0 && al.size()==0)
			{
				continue;
			}
			else
				if (tag[i]==sc)
			{
				continue;
			}
			else
			{
				al.add(new Integer(tag[i]));
				
				sc = tag[i];
			}
		}
		
		Integer[] rto = (Integer[])al.toArray(new Integer[0]);

		ArrayList nal = new ArrayList();
		
		for (int i=rto.length-1; i>=0; i--)
		{
			if (rto[i].intValue()==0 && nal.size()==0)
			{
				continue;
			}
			
			nal.add(rto[i]);
		}
		
		rto = (Integer[])nal.toArray(new Integer[0]);
		
		int[] rt = new int[rto.length];
		
		for (int i=0; i<rto.length; i++)
		{
			rt[i] = rto[rto.length-1-i].intValue();
		}
		
		return rt;
	}
	
	private Element[] cropDataH(Element[] data, int start, int end, int width)
	{
		ArrayList al = new ArrayList();
		
		for (int i=0; i<data.length; i++)
		{
			int x = i - (i/width)*width;
			
			if (x>=start && x<=end)
			{
				al.add(data[i]);
			}
		}
		
		return (Element[])al.toArray(new Element[0]);
	}
	
	private Element[] cropDataV(Element[] data, int start, int end, int width)
	{
		ArrayList al = new ArrayList();
		
		for (int i=0; i<data.length; i++)
		{
			int y = i/width;
			
			if (y>=start && y<=end)
			{
				al.add(data[i]);
			}
		}
		
		return (Element[])al.toArray(new Element[0]);
	}
	
	private Element[] cropData(Element[] data, int xstart, int xend, int ystart, int yend, int width)
	{
		ArrayList al = new ArrayList();
		
		for (int i=0; i<data.length; i++)
		{
			int x = i - (i/width)*width;
			int y = i/width;
			
			if (x>=xstart && x<=xend && y>=ystart && y<=yend)
			{
				al.add(data[i]);
			}
		}
		
		return (Element[])al.toArray(new Element[0]);
	}
	
}
