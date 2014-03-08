/*
 * Created on 2004-4-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import segment.Analyzer;
import segment.Element;
import segment.Matcher;
import segment.Raster;

/**
 * @author qianzhiqiang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public final class Board extends JFrame
{
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	private javax.swing.JMenuItem jMenuItem5;
	private javax.swing.JMenuItem jMenuItem6;
	private javax.swing.JMenuItem jMenuItem7;
	private javax.swing.JMenuItem jMenuItem8;
	private javax.swing.JMenuItem jMenuItem9;
	private javax.swing.JMenuItem jMenuItem10;
	private javax.swing.JMenuItem jMenuItem11;
	private javax.swing.JMenuItem jMenuItem12;
	private javax.swing.JMenuItem jMenuItem13;
	private javax.swing.JMenuItem jMenuItem14;
	private javax.swing.JMenuItem jMenuItem15;
	private javax.swing.JMenuItem jMenuItem16;
	private javax.swing.JMenuItem jMenuItem17;
	private javax.swing.JMenuItem jMenuItem18;
	private javax.swing.JMenuItem jMenuItem19;
	private javax.swing.JMenuItem jMenuItem20;
	private javax.swing.JMenuItem jMenuItem21;
	private javax.swing.JMenuItem jMenuItem22;
	private javax.swing.JMenuItem jMenuItem23;
	private javax.swing.JMenuItem jMenuItem24;
	private javax.swing.JMenuItem jMenuItem25;
	private javax.swing.JMenuItem jMenuItem26;
	private javax.swing.JMenuItem jMenuItem27;
	private javax.swing.JMenuItem jMenuItem28;
	private javax.swing.JMenuItem jMenuItem29;
	private javax.swing.JMenuItem jMenuItem30;
	private javax.swing.JMenuItem jMenuItem31;
	private javax.swing.JMenuItem jMenuItem32;
	private javax.swing.JMenu jMenu4;
	private javax.swing.JMenu jMenu5;
	private javax.swing.JMenu jMenu6;
	private javax.swing.JMenu jMenu7;
	private javax.swing.JMenu jMenu8;
	private javax.swing.JMenu jMenu9;
	private JScrollPane jScrollPane1;
	private DrawingPane jPanel2;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JSeparator jSeparator3;
	private javax.swing.JSeparator jSeparator4;
	private javax.swing.JSeparator jSeparator5;
	private javax.swing.JSeparator jSeparator6;
	private javax.swing.JSeparator jSeparator7;
	private javax.swing.JSeparator jSeparator8;
	private javax.swing.JSeparator jSeparator9;

	private Element[] lattice = new Element[]	{	new Element(20, 20),
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
	
	private Element[][] drawData = o2t(lattice);
	
	private int[][] splitData;
	private int splitMode = 0;
	
	private Matcher matcher;
	private Raster raster;
	private Analyzer analyzer;
	private JFileChooser chooser;
	private OptionPane optionDlg;
	private HistoPane hisDlg;
	
	public int houfPrecision = 3;
	public int aggregateThreshold = 5;
	public int filterThreshold = 10;
	public int bynaThreshold = 128;
	public int combinePrecision = 1;
	
	public boolean fillMode = true;
	public int zoom = 3;

	public Board()
	{
		matcher = new Matcher();
		analyzer = new Analyzer();
		
		chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(new FileFilter() 
		{
		/* (non-Javadoc)
		 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
		 */
		public boolean accept(File f)
		{
			if(f.isDirectory())//是一个目录
				return true;
			
			String s = f.getAbsolutePath();
			
			int i = s.lastIndexOf('.');
			
			if (i > -1 && i<s.length()-1)
			{
				String ext = s.substring(i+1);
				
				if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg"))
				{
					return true;
				}
			}
			
			return false;
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.filechooser.FileFilter#getDescription()
		 */
		public String getDescription()
		{
			return "JPEG Image Files";
		}
		});
		
		optionDlg = new OptionPane(this, true);
		
		hisDlg = new HistoPane(this, true);
		
		initComponents();
		
	}

	private void initComponents()
	{
		jPanel2 = new DrawingPane();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu2 = new javax.swing.JMenu();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenu3 = new javax.swing.JMenu();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem3 = new javax.swing.JMenuItem();
		jMenuItem11 = new javax.swing.JMenuItem();
		jSeparator2 = new javax.swing.JSeparator();
		jSeparator4 = new javax.swing.JSeparator();
		jSeparator5 = new javax.swing.JSeparator();
		jSeparator6 = new javax.swing.JSeparator();
		jSeparator7 = new javax.swing.JSeparator();
		jSeparator8 = new javax.swing.JSeparator();
		jSeparator9 = new javax.swing.JSeparator();
		jMenuItem8 = new javax.swing.JMenuItem();
		jMenuItem9 = new javax.swing.JMenuItem();
		jMenuItem10 = new javax.swing.JMenuItem();
		jSeparator3 = new javax.swing.JSeparator();
		jMenuItem12 = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JSeparator();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();
		jMenuItem5 = new javax.swing.JMenuItem();
		jMenuItem6 = new javax.swing.JMenuItem();
		jMenuItem7 = new javax.swing.JMenuItem();
		jMenuItem13 = new javax.swing.JMenuItem();
		jMenuItem14 = new javax.swing.JMenuItem();
		jMenuItem15 = new javax.swing.JMenuItem();
		jMenuItem16 = new javax.swing.JMenuItem();
		jMenuItem17 = new javax.swing.JMenuItem();
		jMenuItem18 = new javax.swing.JMenuItem();
		jMenuItem19 = new javax.swing.JMenuItem();
		jMenuItem20 = new javax.swing.JMenuItem();
		jMenuItem21 = new javax.swing.JMenuItem();
		jMenuItem22 = new javax.swing.JMenuItem();
		jMenuItem23 = new javax.swing.JMenuItem();
		jMenuItem24 = new javax.swing.JMenuItem();
		jMenuItem25 = new javax.swing.JMenuItem();
		jMenuItem26 = new javax.swing.JMenuItem();
		jMenuItem27 = new javax.swing.JMenuItem();
		jMenuItem28 = new javax.swing.JMenuItem();
		jMenuItem29 = new javax.swing.JMenuItem();
		jMenuItem30 = new javax.swing.JMenuItem();
		jMenuItem31 = new javax.swing.JMenuItem();
		jMenuItem32 = new javax.swing.JMenuItem();
		jMenu4 = new javax.swing.JMenu();
		jMenu5 = new javax.swing.JMenu();
		jMenu6 = new javax.swing.JMenu();
		jMenu7 = new javax.swing.JMenu();
		jMenu8 = new javax.swing.JMenu();
		jMenu9 = new javax.swing.JMenu();
		jScrollPane1 = new javax.swing.JScrollPane();
		
		getContentPane().setLayout(new BorderLayout());
		
		addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent evt)
			{
				exitForm();
			}
			
			
		});
		
		jPanel2.setBackground(Color.WHITE);
		jPanel2.setPreferredSize(new Dimension(3072, 2304));
		
		jScrollPane1.setViewportView(jPanel2);
		getContentPane().add(jScrollPane1);
		
		jMenu2.setText("System");
		
		jMenuItem20.setText("Save ...");
		jMenuItem20.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                save();
            }
        });
		
		jMenu2.add(jMenuItem20);

		jMenu2.add(jSeparator6);
		
		jMenuItem2.setText("Exit");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                exitForm();
            }
        });
		
		jMenu2.add(jMenuItem2);
		jMenuBar1.add(jMenu2);

		jMenu1.setText("Function");
		jMenuItem3.setText("Original");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                origin();
            }
        });
		jMenu1.add(jMenuItem3);
		
		jMenu1.add(jSeparator2);
		
		jMenuItem8.setText("Grayscale");
		jMenuItem8.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                greylize();
            }
        });
		jMenu1.add(jMenuItem8);
		
		jMenuItem9.setText("Inverse");
		jMenuItem9.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                inverse();
            }
        });
		jMenu1.add(jMenuItem9);
		
		jMenuItem10.setText("Bynalize");
		jMenuItem10.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                bynalize();
            }
        });
		jMenu1.add(jMenuItem10);
		
		jMenu1.add(jSeparator3);

		jMenuItem12.setText("Extract");
		jMenuItem12.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                extract();
            }
        });
		jMenu1.add(jMenuItem12);
		
		jMenu1.add(jSeparator1);
		
		jMenu4.setText("Detection");
		jMenu1.add(jMenu4);
		
		jMenu5.setText("Local");
		jMenu4.add(jMenu5);
		
		jMenuItem13.setText("Linear (Horizontal)");
		jMenuItem13.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                linearH();
            }
        });
		jMenu5.add(jMenuItem13);
		
		jMenuItem14.setText("Linear (Vertical)");
		jMenuItem14.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                linearV();
            }
        });
		jMenu5.add(jMenuItem14);
		
		jMenuItem15.setText("Linear (Composite)");
		jMenuItem15.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                linearC();
            }
        });
		jMenu5.add(jMenuItem15);
		
		jMenu5.add(jSeparator4);

		jMenuItem16.setText("Sobel (Horizontal)");
		jMenuItem16.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                sobelH();
            }
        });
		jMenu5.add(jMenuItem16);
		
		jMenuItem17.setText("Sobel (Vertical)");
		jMenuItem17.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                sobelV();
            }
        });
		jMenu5.add(jMenuItem17);
		
		jMenuItem18.setText("Sobel (Composite)");
		jMenuItem18.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                sobelC();
            }
        });
		jMenu5.add(jMenuItem18);
		
		jMenu5.add(jSeparator5);

		jMenuItem19.setText("Connect");
		jMenuItem19.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                connect();
            }
        });
		jMenu5.add(jMenuItem19);
		
		jMenu6.setText("Global");
		jMenu4.add(jMenu6);
		
		jMenuItem1.setText("Hough");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                houf();
            }
        });
		jMenu6.add(jMenuItem1);
		
		jMenuItem4.setText("Aggregate");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                aggregate();
            }
        });
		jMenu6.add(jMenuItem4);
		
		jMenuItem5.setText("Refine");
		jMenuItem5.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                refine();
            }
        });
		jMenu6.add(jMenuItem5);
		
		jMenuItem7.setText("Filter");
		jMenuItem7.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                filter();
            }
        });
		jMenu6.add(jMenuItem7);
		
		jMenu6.add(jSeparator7);
		
		jMenuItem21.setText("Combine (Single Center)");
		jMenuItem21.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                combine();
            }
        });
		jMenu6.add(jMenuItem21);
		
		jMenuItem22.setText("Combine (Single Edges)");
		jMenuItem22.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                combine2();
            }
        });
		jMenu6.add(jMenuItem22);
		
		jMenuItem23.setText("Combine (Multi Merge)");
		jMenuItem23.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                combine3();
            }
        });
		jMenu6.add(jMenuItem23);
		
		jMenu7.setText("Skeleton");
		jMenu1.add(jMenu7);
		
		jMenuItem24.setText("Zhang-Suen");
		jMenuItem24.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                zhangSuen();
            }
        });
		jMenu7.add(jMenuItem24);
		
		jMenuItem25.setText("Zhang-Suen + Holt");
		jMenuItem25.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                zhangSuenHolt();
            }
        });
		jMenu7.add(jMenuItem25);
		
		jMenu1.add(jSeparator8);
		
		jMenu8.setText("Analysis");
		jMenu1.add(jMenu8);
		
		jMenuItem26.setText("Split (H)");
		jMenuItem26.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                splitH();
            }
        });
		jMenu8.add(jMenuItem26);
		
		jMenuItem27.setText("Split (V)");
		jMenuItem27.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                splitV();
            }
        });
		jMenu8.add(jMenuItem27);
		
		jMenuItem28.setText("Split (H-V)");
		jMenuItem28.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                splitHV();
            }
        });
		jMenu8.add(jMenuItem28);

		jMenuItem30.setText("Split (V-H)");
		jMenuItem30.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                splitVH();
            }
        });
		jMenu8.add(jMenuItem30);

		jMenuItem31.setText("Centroid");
		jMenuItem31.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                centroid();
            }
        });
		jMenu8.add(jMenuItem31);

		jMenuItem32.setText("Normalize");
		jMenuItem32.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                normalize();
            }
        });
		jMenu8.add(jMenuItem32);

		jMenu8.add(jSeparator9);
		
		jMenu9.setText("Recognize");
		jMenu8.add(jMenu9);
		
		jMenuItem29.setText("Number");
		jMenuItem29.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                recognizeNumber();
            }
        });
		jMenu9.add(jMenuItem29);

		jMenuBar1.add(jMenu1);
		
		jMenu3.setText("Option");
		jMenuBar1.add(jMenu3);
		
		jMenuItem6.setText("Option ...");
		jMenuItem6.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                option();
            }
        });
		jMenu3.add(jMenuItem6);

		jMenuItem11.setText("Histogram ...");
		jMenuItem11.addActionListener(new java.awt.event.ActionListener() 
		{
            public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
                histo();
            }
        });
		jMenu3.add(jMenuItem11);

		setJMenuBar(jMenuBar1);
		
		this.setSize(410, 330);
		
		this.setLocationRelativeTo(null);

		this.setTitle("Image");
	}
	
	private String getExtension(String filename) 
	{
		int i = filename.lastIndexOf('.');
		
		if(i > -1 && i < filename.length()-1) 
		{
			return filename.substring(i+1).toLowerCase();
		};
		
		return "";
	}
	
	private void save()
	{
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			File file = chooser.getSelectedFile();

			if (file.exists())
			{
				int iResult = JOptionPane.showConfirmDialog(this, "文件已存在，是否覆盖？", "提示", JOptionPane.YES_NO_OPTION);
				if (iResult == JOptionPane.NO_OPTION)
				{
					return;
				}
			}

			String extension = getExtension(file.getAbsolutePath());
			try 
			{
				BufferedImage image = jPanel2.getBuffer();
				
				if (image != null)
				{
					 if (ImageIO.write(image, extension, file))
					 {
						 JOptionPane.showMessageDialog(this, "保存完成", "提示", JOptionPane.DEFAULT_OPTION);
					 }
					 else
					 {
						 JOptionPane.showMessageDialog(this, "不支持指定的文件类型", "提示", JOptionPane.OK_OPTION);
					 }
				}
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}

	private void origin()
	{
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			raster = new segment.Raster(chooser.getSelectedFile().getAbsolutePath());
			
			//jPanel2.setPreferredSize(new Dimension(rst.getWidth(), rst.getHeight()));
			
			lattice = raster.scanElements(this);
			
			drawData = o2t(lattice);
			
			jPanel2.setBufferSize(raster.getWidth(), raster.getHeight());
		}
		
		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void greylize()
	{
		lattice = raster.greylize(lattice);
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void inverse()
	{
		lattice = raster.inverse(lattice);
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void bynalize()
	{
		lattice = raster.bynalize(lattice, bynaThreshold);
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void extract()
	{
		lattice = raster.extract(lattice);
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void linearH()
	{
		lattice = matcher.linearH(lattice, raster.getWidth());
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void linearV()
	{
		lattice = matcher.linearV(lattice, raster.getWidth());
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void linearC()
	{
		lattice = matcher.linearC(lattice, raster.getWidth());
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void sobelH()
	{
		lattice = matcher.sobelH(lattice, raster.getWidth());
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void sobelV()
	{
		lattice = matcher.sobelV(lattice, raster.getWidth());
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void sobelC()
	{
		lattice = matcher.sobelC(lattice, raster.getWidth());
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void connect()
	{
		lattice = matcher.connect(lattice, raster.getWidth());
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void houf()
	{
		drawData = matcher.houf(lattice, houfPrecision);
		
		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void aggregate()
	{
		drawData = matcher.aggregate(drawData, aggregateThreshold);
		
		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void refine()
	{
		drawData = matcher.refine(drawData);
		
		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void filter()
	{
		drawData = matcher.filter(drawData, filterThreshold);
		
		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void combine()
	{
		drawData = matcher.combine(drawData, combinePrecision);
		
		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void combine2()
	{
		drawData = matcher.combine2(drawData, combinePrecision);
		
		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void combine3()
	{
		
	}
	
	private void zhangSuen()
	{
		lattice = matcher.zhangSuenThinning(lattice, raster.getWidth());
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void zhangSuenHolt()
	{
		lattice = matcher.zhangSuenHoltThinning(lattice, raster.getWidth());
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void splitH()
	{
		int[] hs = analyzer.splitHorizontal(lattice, raster.getWidth());
		
		jPanel2.drawGridH(hs);
		
		jPanel2.repaint();
	}
	
	private void splitV()
	{
		int[] vs = analyzer.splitVertical(lattice, raster.getWidth());
		
		jPanel2.drawGridV(vs);
		
		jPanel2.repaint();
	}
	
	private void splitHV()
	{
		int[][] hvs = analyzer.splitH2V(lattice, raster.getWidth());
		
		splitData = hvs;
		splitMode = 0;
		
		jPanel2.drawGridHV(hvs);
		
		jPanel2.repaint();
	}
	
	private void splitVH()
	{
		int[][] vhs = analyzer.splitV2H(lattice, raster.getWidth());
		
		splitData = vhs;
		splitMode = 1;
		
		jPanel2.drawGridVH(vhs);
		
		jPanel2.repaint();
	}
	
	private void centroid()
	{
		int[][] cs = analyzer.centroid(lattice, raster.getWidth(), splitMode, splitData);
		
		jPanel2.drawCentroid(cs);
		
		jPanel2.repaint();
	}
	
	private void normalize()
	{
		lattice = analyzer.normalize(lattice, raster.getWidth(), splitMode, splitData);
		
		drawData = o2t(lattice);

		jPanel2.setData(drawData);
		
		jPanel2.repaint();
	}
	
	private void recognizeNumber()
	{
		String s = analyzer.identifyNumber(lattice, raster.getWidth(), splitMode, splitData);
		
		JOptionPane.showMessageDialog(this, s);
	}
	
	private void option()
	{
		optionDlg.setLocationRelativeTo(this);
		optionDlg.show();
		
		jPanel2.setFillMode(fillMode);
		jPanel2.setZoom(zoom);
		jPanel2.redraw();
		
		jPanel2.repaint();
	}
	
	private void histo()
	{
		hisDlg.setData(drawData);
		
		hisDlg.setLocationRelativeTo(this);
		hisDlg.show();
	}

	/** Exit the Application */
	private void exitForm()
	{
		System.exit(0);
	}
	
	public static Element[][] o2t(Element[] d)
	{
		if (d != null)
		{
			Element[][] r = new Element[d.length][1];
			
			for (int i=0; i<d.length; i++)
			{
				r[i][0] = d[i];
			}
			
			return r;
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Board().show();
	}
	
}
