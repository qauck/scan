/*
 * Created on 2004-4-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import segment.Element;

/**
 * @author qianzhiqiang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public final class DrawingPane extends JPanel
{
	private Element[][] data;
	private boolean fillMode = true;
	private int zoom = 3;
	
	private int width = 300;
	private int height = 300;
	private BufferedImage buffer;

	public DrawingPane()
	{
		super();
		
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		buffer.getGraphics().setColor(Color.WHITE);
		buffer.getGraphics().fillRect(0, 0, width, height);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), Color.WHITE, this);
	}
	
	public void setData(Element[][] data)
	{
		this.data = data;
		
		redraw();
	}
	
	public void drawGridH(int[] grid)
	{
		Graphics bg = buffer.getGraphics();

		int width = buffer.getWidth();
		int height = buffer.getHeight();
		
		bg.setColor(Color.MAGENTA);
		
		for (int i=0; i<grid.length; i++)
		{
			int ai = grid[i]*zoom + zoom/2;
			
			if (ai<width)
			{
				bg.drawLine(ai, 0, ai, height);
			}
		}
	}
	
	public void drawGridV(int[] grid)
	{
		Graphics bg = buffer.getGraphics();

		int width = buffer.getWidth();
		int height = buffer.getHeight();
		
		bg.setColor(Color.MAGENTA);
		
		for (int i=0; i<grid.length; i++)
		{
			int ai = grid[i]*zoom + zoom/2;
			
			if (ai<height)
			{
				bg.drawLine(0, ai, width, ai);
			}
		}
	}
	
	public void drawGridHV(int[][] grid)
	{
		Graphics bg = buffer.getGraphics();

		int width = buffer.getWidth();
		int height = buffer.getHeight();
		
		bg.setColor(Color.MAGENTA);
		
		for (int i=0; i<grid.length; i++)
		{
			int[] v1 = grid[i];
			
			int h1 = v1[0]*zoom + zoom/2;
			int h2 = v1[1]*zoom + zoom/2;
			
			for (int j=2; j<v1.length; j++)
			{
				int v = v1[j]*zoom + zoom/2;

				bg.drawLine(h1, v, h2, v);
				
				if (j % 2 == 1)
				{
					bg.drawLine(h1, v, h1, v1[j-1]*zoom + zoom/2);
					bg.drawLine(h2, v, h2, v1[j-1]*zoom + zoom/2);
				}
			}
			
		}
	}
	
	public void drawGridVH(int[][] grid)
	{
		Graphics bg = buffer.getGraphics();

		int width = buffer.getWidth();
		int height = buffer.getHeight();
		
		bg.setColor(Color.MAGENTA);
		
		for (int i=0; i<grid.length; i++)
		{
			int[] v1 = grid[i];
			
			int h1 = v1[0]*zoom + zoom/2;
			int h2 = v1[1]*zoom + zoom/2;
			
			for (int j=2; j<v1.length; j++)
			{
				int v = v1[j]*zoom + zoom/2;

				bg.drawLine(v, h1, v, h2);
				
				if (j % 2 == 1)
				{
					bg.drawLine(v, h1, v1[j-1]*zoom + zoom/2, h1);
					bg.drawLine(v, h2, v1[j-1]*zoom + zoom/2, h2);
				}
			}
			
		}
	}
	
	public void drawCentroid(int[][] cen)
	{
		Graphics bg = buffer.getGraphics();

		bg.setColor(Color.MAGENTA);
		
		for (int i=0; i<cen.length; i++)
		{
			int[] v1 = cen[i];
			
			if (fillMode)
			{
				bg.fillRect(v1[0] * zoom, v1[1] * zoom, zoom, zoom);
			}
			else
			{
				bg.drawRect(v1[0] * zoom, v1[1] * zoom, zoom-1, zoom-1);
			}
		}
	}
	
	public void redraw()
	{
		if (data != null)
		{
			Graphics bg = buffer.getGraphics();
			
			bg.setColor(Color.WHITE);
			bg.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
			
			for (int i=0; i<data.length; i++)
			{
				for (int j=0; j<data[i].length; j++)
				{
					bg.setColor(new Color(data[i][j].value));
					
					if (fillMode)
					{
						bg.fillRect(data[i][j].x * zoom, data[i][j].y * zoom, zoom, zoom);
					}
					else
					{
						bg.drawRect(data[i][j].x * zoom, data[i][j].y * zoom, zoom-1, zoom-1);
					}
					
					if (j > 0)
					{
						bg.drawLine(data[i][j-1].x * zoom + zoom/2, data[i][j-1].y * zoom + zoom/2 , data[i][j].x * zoom + zoom/2, data[i][j].y * zoom + zoom/2);
					}
				}
			}
		}
	}
	
	public void setFillMode(boolean fillMode)
	{
		this.fillMode = fillMode;
	}
	
	public void setZoom(int zoom)
	{
		if (zoom > 0)
		{
			this.zoom = zoom;

			buffer = new BufferedImage(width*zoom, height*zoom, BufferedImage.TYPE_INT_RGB);
			buffer.getGraphics().setColor(Color.WHITE);
			buffer.getGraphics().fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
		}
	}

	public void setBufferSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		buffer = new BufferedImage(width*zoom, height*zoom, BufferedImage.TYPE_INT_RGB);
		buffer.getGraphics().setColor(Color.WHITE);
		buffer.getGraphics().fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
	}
	
	public BufferedImage getBuffer()
	{
		return buffer;
	}

}
