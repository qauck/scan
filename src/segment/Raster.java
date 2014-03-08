package segment;

import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/*
 * Created on 2004-4-2
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
/**
 * @author qianzhiqiang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public final class Raster
{
	private Image img;
	private int width;
	private int height;

	public Raster(String fileName)
	{
		img = new ImageIcon(fileName).getImage();
	}
	
	public Raster(Image img)
	{
		this.img = img;
	}

	public void loadImage(String fileName)
	{
		img = new ImageIcon(fileName).getImage();
	}

	public Element[] scanElements(ImageObserver observer)
	{
		int w = img.getWidth(observer);
		int h = img.getHeight(observer);

		width = w;
		height = h;

		int[] pixels = new int[w * h];
		int scan = w;
		int offset = 0;

		PixelGrabber pg = new PixelGrabber(img, 0, 0, w, h, pixels, offset,
				scan);

		try
		{
			pg.grabPixels();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		Element[] elems = new Element[w*h];

		for (int i = 0; i < w * h; i++)
		{
			Element ele = new Element(i % w, i / w);
			ele.value = pixels[i];

			ele.value2 = ((pixels[i] & 0x000000ff) * 299
					+ ((pixels[i] & 0x0000ff00) >> 8) * 587 
					+ ((pixels[i] & 0x00ff0000) >> 16) * 114) / 1000;
			
//			ele.value2 = ((pixels[i] & 0x000000ff) + ((pixels[i] & 0x0000ff00) >> 8) + ((pixels[i] & 0x00ff0000) >> 16)) / 3;

			elems[i] = ele;
		}

		return elems;
	}

	public Element[] greylize(Element[] data)
	{
		float[] f = new float[1];
		ColorSpace grayInst = ColorSpace.getInstance(ColorSpace.CS_GRAY);

		for (int i = 0; i < data.length; i++)
		{
			Element ele = data[i];

			ele.value = (ele.value2 & 0xFF) |
						((ele.value2 & 0xFF) << 8) |
						((ele.value2 & 0xFF) << 16);
		}

		return data;
	}

	public Element[] inverse(Element[] data)
	{
		float[] f = new float[1];

		ColorSpace grayInst = ColorSpace.getInstance(ColorSpace.CS_GRAY);

		for (int i = 0; i < data.length; i++)
		{
			Element ele = data[i];

			ele.value2 = 255 - data[i].value2;

			ele.value = (ele.value2 & 0xFF) |
						((ele.value2 & 0xFF) << 8) |
						((ele.value2 & 0xFF) << 16);
		}

		return data;
	}

	public Element[] bynalize(Element[] data, int threshold)
	{
		for (int i = 0; i < data.length; i++)
		{
			Element ele = data[i];

			if (ele.value2 < threshold)
			{
				ele.value2 = 0;
				ele.value = 0x0;
			}
			else
			{
				ele.value2 = 255;
				ele.value = 0x00ffffff;
			}
		}

		return data;
	}
	
	public Element[] extract(Element[] data)
	{
		ArrayList al = new ArrayList();
		
		for (int i=0; i<data.length; i++)
		{
			if (data[i].value2 == 0)
			{
				al.add(data[i]);
			}
		}
		
		return (Element[])al.toArray(new Element[0]);
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
