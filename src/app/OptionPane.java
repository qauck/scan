/*
 * Created on 2004-4-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package app;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 * @author qianzhiqiang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public final class OptionPane extends JDialog
{
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JTextField jTextField3;
	private javax.swing.JTextField jTextField4;
	private javax.swing.JTextField jTextField5;
    private javax.swing.JCheckBox jCheckBox1;
	
	private Board frame;
	
	InputVerifier iv = new InputVerifier()
	{
		public boolean verify(JComponent input)
		{
			if (input instanceof JTextField)
			{
				JTextField text = (JTextField)input;
				
				return isInteger(text.getText());
			}
			
			return false;
		}
	};
	
	public static boolean isInteger(String s)
	{
		try
		{
			Integer.parseInt(s);
			
			return true;
		}
		catch( Exception e)
		{
			
		}
		return false;
	}

	public OptionPane(java.awt.Frame parent, boolean modal)
	{
		super(parent, modal);
		
		this.frame = (Board)parent;
		
		initComponents();
	}

	private void initComponents()
	{
		jLabel1 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		jTextField2 = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		jTextField3 = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		jTextField4 = new javax.swing.JTextField();
		jLabel5 = new javax.swing.JLabel();
		jTextField5 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		getContentPane().setLayout(null);
		addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent evt)
			{
				closeDialog();
			}
		});
		
		jLabel1.setText("Houf Precision :");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(40, 40, 140, 20);
		
		jTextField1.setText(String.valueOf(this.frame.houfPrecision));
		jTextField1.setInputVerifier(iv);
		getContentPane().add(jTextField1);
		jTextField1.setBounds(200, 40, 140, 22);
		
		jLabel2.setText("Aggregate Threshold :");
		getContentPane().add(jLabel2);
		jLabel2.setBounds(40, 80, 140, 20);
		
		jTextField2.setText(String.valueOf(this.frame.aggregateThreshold));
		jTextField2.setInputVerifier(iv);
		getContentPane().add(jTextField2);
		jTextField2.setBounds(200, 80, 140, 22);
		
		jLabel3.setText("Filter Threshold :");
		getContentPane().add(jLabel3);
		jLabel3.setBounds(40, 120, 140, 20);
		
		jTextField3.setText(String.valueOf(this.frame.filterThreshold));
		jTextField3.setInputVerifier(iv);
		getContentPane().add(jTextField3);
		jTextField3.setBounds(200, 120, 140, 22);
		
		jLabel4.setText("Bynalize Threshold :");
		getContentPane().add(jLabel4);
		jLabel4.setBounds(40, 160, 140, 20);
		
		jTextField4.setText(String.valueOf(this.frame.bynaThreshold));
		jTextField4.setInputVerifier(iv);
		getContentPane().add(jTextField4);
		jTextField4.setBounds(200, 160, 140, 22);
		
		jLabel5.setText("Zoom Scale :");
		getContentPane().add(jLabel5);
		jLabel5.setBounds(40, 200, 140, 20);
		
		jTextField5.setText(String.valueOf(this.frame.zoom));
		jTextField5.setInputVerifier(iv);
		getContentPane().add(jTextField5);
		jTextField5.setBounds(200, 200, 140, 22);
		
        jCheckBox1.setSelected(this.frame.fillMode);
        jCheckBox1.setText("Draw Filling Mode");
        getContentPane().add(jCheckBox1);
        jCheckBox1.setBounds(40, 240, 160, 26);

        jButton1.setText("OK");
		jButton1.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				if (isInteger(jTextField1.getText()))
				{
					int hp = Integer.parseInt(jTextField1.getText());
					
					if (hp > 0)
					{
						frame.houfPrecision = hp; 
					}
				}

				if (isInteger(jTextField2.getText()))
				{
					int at = Integer.parseInt(jTextField2.getText());
					
					if (at > 0)
					{
						frame.aggregateThreshold = at;
					}
				}
				
				if (isInteger(jTextField3.getText()))
				{
					int ft = Integer.parseInt(jTextField3.getText());
					
					if (ft > 0)
					{
						frame.filterThreshold = ft;
					}
				}
				
				if (isInteger(jTextField4.getText()))
				{
					int bt = Integer.parseInt(jTextField4.getText());
					
					if (bt > 0)
					{
						frame.bynaThreshold = bt;
					}
				}
				
				if (isInteger(jTextField5.getText()))
				{
					int zs = Integer.parseInt(jTextField5.getText());
					
					if (zs > 0)
					{
						frame.zoom = zs;
					}
				}
				
				frame.fillMode = jCheckBox1.isSelected();
				
				closeDialog();
			}
		});
		getContentPane().add(jButton1);
		jButton1.setBounds(100, 280, 75, 28);
		
		jButton2.setText("Cancel");
		jButton2.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				closeDialog();
			}
		});
		getContentPane().add(jButton2);
		jButton2.setBounds(230, 280, 75, 28);

		this.setSize(400, 360);
		
		this.setTitle("Option");
	}

	private void closeDialog()
	{
		setVisible(false);
		dispose();
	}

}
