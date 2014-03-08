/*
 * Created on 2004-4-6
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JPanel;

import segment.Element;

/**
 * @author qianzhiqiang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public final class HistoPane extends JDialog
{
	private Element[][] data;
	private JPanel hisPanel;
	private int[] gram;
	private double[] unit;
	private double max;

	public void setData(Element[][] data)
	{
		this.data = data;
		
		Arrays.fill(gram, 0);
		
		for (int i=0; i<data.length; i++)
		{
			for (int j=0; j<data[i].length; j++)
			{
				gram[data[i][j].value2] += 1;
			}
		}
		
		long sum = 0;
		
		for (int i=0; i<gram.length; i++)
		{
			sum += gram[i];
		}

		if (sum != 0)
		{
			for (int i=0; i<unit.length; i++)
			{
				unit[i] = (double)gram[i] / sum;
			}
		}
		else
		{
			Arrays.fill(unit, 0);
		}
		
		double[] tmp = new double[unit.length];
		System.arraycopy(unit, 0, tmp, 0, unit.length);
		Arrays.sort(tmp);
		max = tmp[255];
	}
	
	public HistoPane(java.awt.Frame parent, boolean modal)
	{
		super(parent, modal);
		
		gram = new int[256];
		unit = new double[256];
		
		initComponents();
	}

	private void initComponents()
	{
		getContentPane().setLayout(new BorderLayout());
		
		addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent evt)
			{
				closeDialog();
			}
		});
		
		hisPanel = new JPanel()
		{
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			g.drawLine(40, 27, 40, 331);
			g.drawLine(37, 331, 350, 331);
			g.drawString("0", 28, 328);

			g.drawLine(37, 30, 40, 30);
			g.drawString(String.valueOf(Math.round(max*100)), 15, 27);

			g.drawLine(50, 331, 50, 334);
			g.drawString("0", 54, 346);

			g.drawLine(305, 331, 305, 334);
			g.drawString("255", 309, 346);

			g.setColor(Color.BLUE);

			for (int i=0; i<unit.length; i++)
			{
				if (unit[i]>0)
				{
					g.drawLine(50+i, 330, 50+i, (int)Math.round(330 - 300*unit[i]/max));
				}
			}
			
		}};
		
		hisPanel.setBackground(Color.WHITE);
		
		getContentPane().add(hisPanel);
		
		this.setSize(400, 400);
		
		this.setTitle("Histogram");
	
	}

	private void closeDialog()
	{
		setVisible(false);
		dispose();
	}

}
