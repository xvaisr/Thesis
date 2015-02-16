/**
 * BP, anthill strategy game
 * Screen shown when game runs but no GUI parameter is set
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/25
 * @version 1
 * @file    graphic.ScreenGameNoGui.java
 */
package graphic;

import Enviroment.Model;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JPanel;

/**
 * Screen shown when game runs but no GUI parameter is set
 * @author Vojtech Simetka
 *
 */
public class ScreenGameNoGui extends JPanel implements KeyListener
{
	ScreenGameNoGui()
	{
		this.setLayout(new GridBagLayout());
	    
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH;
	    c.ipady = 450;
	    c.ipadx = this.getWidth();
	    c.weightx = 1.0;
	    c.weighty = 5.0;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.gridx = 0;
	    c.gridy = 0;
	    this.add(Box.createGlue(), c);
	
	    c.ipady = 15;
	    c.ipadx = 0;
	    c.weightx = 0.1;
	    c.weighty = 0.05;
	    c.insets = new Insets(5,5,0,0);
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;       //aligned with button 2
		c.gridy = 1;       //third row
		Button button = new Button("Menu (Esc)", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
		    	new DialogMenu();
			}
		});
		button.addKeyListener(this);
		this.add(button, c);
		
		
		c.ipady = 20;
		c.ipadx = 0;
		c.weightx = 0.4;
		c.weighty = 0.05;
		c.insets = new Insets(5,5,0,5);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;       //aligned with button 2
		c.gridy = 1;       //third row
		this.add(new PanelMapDetails(), c);
		
		c.ipady = 135;
		c.ipadx = 400;
		c.weightx = 0.5;
		c.weighty = 0.95;
		c.insets = new Insets(5,5,5,5);
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 2;
		this.add(new PanelMapControl(), c);
		
		if (Model.getConfiguration().getExperiment() == EnumExperiments.Performance)
		{
		    c.ipady = 0;
		    c.ipadx = 15;
		    c.weightx = 0.1;
		    c.weighty = 0.05;
		    c.insets = new Insets(5,5,0,0);
		    c.gridx = 0;
		    c.gridwidth = 1;
		    c.gridheight = 1;
		    c.gridy = 1;
			this.add(new Button("Previus", new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					Model.previousPerformanceExperiment();
				}
			}), c);
			
			c.ipady = 0;
			c.ipadx = 15;
		    c.weightx = 0.1;
		    c.weighty = 0.05;
		    c.insets = new Insets(5,5,0,0);
			c.gridheight = 1;
			c.gridwidth = 1;
			c.gridx = 3;       //aligned with button 2
			c.gridy = 1;       //third row
			this.add(new Button("Next", new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					Model.nextPerformanceExperiment();
				}
			}), c);
			
		    c.ipady = 150;
		    c.ipadx = 200;
		    c.weightx = 0.3;
		    c.weighty = 0.95;
		    c.insets = new Insets(5,5,5,5);
		    c.gridx = 0;
		    c.gridwidth = 1;
		    c.gridheight = 1;
		    c.gridy = 2;
		    this.add(Box.createGlue(), c);
		    
			c.ipady = 0;
			c.ipadx = 200;
			c.weightx = 0.2;
			c.weighty = 0.95;   //request any extra vertical space
			c.insets = new Insets(5,5,5,5);  //top padding
			c.gridwidth = 1;
			c.gridheight = 1;
			c.gridx = 3;       //aligned with button 2
			c.gridy = 2;       //third row
			this.add(Box.createGlue(), c);
		}
		
		else
		{
		    c.ipady = 150;
		    c.ipadx = 200;
		    c.weightx = 0.3;
		    c.weighty = 1.0;
		    c.insets = new Insets(5,5,5,5);
		    c.gridx = 0;
		    c.gridwidth = 1;
		    c.gridheight = 2;
		    c.gridy = 1;
		    this.add(Box.createGlue(), c);
		    
			c.ipady = 0;
			c.ipadx = 200;
			c.weightx = 0.2;
			c.weighty = 1.0;   //request any extra vertical space
			c.insets = new Insets(5,5,5,5);  //top padding
			c.gridwidth = 1;
			c.gridheight = 2;
			c.gridx = 3;       //aligned with button 2
			c.gridy = 1;       //third row
			this.add(Box.createGlue(), c);
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() ==  KeyEvent.VK_ESCAPE)
		{
        	if (Model.isGame_running())
		    	new DialogMenu();
        }
	}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}
}
