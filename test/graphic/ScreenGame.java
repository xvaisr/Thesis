/**
 * BP, anthill strategy game
 * Screen displaying the game itself 
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/25
 * @version 1
 * @file    graphic.ScreenGame.java
 */
package graphic;

import Enviroment.Original.Model;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JPanel;

/**
 * Screen displaying the game itself
 * @author Vojtech Simetka
 *
 */
public class ScreenGame extends JPanel implements KeyListener
{	
	private final PanelMap map;
	private final PanelSelection selection;
	
	/**
	 * Screen game constructor
	 * @param map Panel map reference
	 */
	public ScreenGame(PanelMap map)
	{
		this.map = map;
		this.selection = new PanelSelection(this.map);
		
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
	    this.add(this.map, c);
	
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
		if (Model.isUserPlaying())
			this.add(new PanelMapUserDetails(this.map), c);
			
		else
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
		if (Model.isUserPlaying())
			this.add(new PanelMapUserControl(this.map), c);
		
		else
			this.add(new PanelMapControl(), c);
		
		if (Model.getConfiguration().getExperiment() == EnumExperiments.Performance ||
			Model.getConfiguration().getExperiment() == EnumExperiments.Performance2)
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
		    if (Model.getConfiguration().getExperiment() == EnumExperiments.Performance)
		    {
				this.add(new Button("Previus", new ButtonActionListener()
				{
					public void actionToBePerformed()
					{
				    	Model.previousPerformanceExperiment();
					}
				}), c);
		    }
		    else
		    {
		    	this.add(new Button("Previus", new ButtonActionListener()
				{
					public void actionToBePerformed()
					{
				    	Model.previousPerformanceExperiment2();
					}
				}), c);
		    }
			
			c.ipady = 0;
			c.ipadx = 15;
		    c.weightx = 0.1;
		    c.weighty = 0.05;
		    c.insets = new Insets(5,5,0,0);
			c.gridheight = 1;
			c.gridwidth = 1;
			c.gridx = 3;       //aligned with button 2
			c.gridy = 1;       //third row
			if (Model.getConfiguration().getExperiment() == EnumExperiments.Performance)
			{
				this.add(new Button("Next", new ButtonActionListener()
				{
					public void actionToBePerformed()
					{
						Model.nextPerformanceExperiment();
					}
				}), c);
			}
			else
			{
				this.add(new Button("Next", new ButtonActionListener()
				{
					public void actionToBePerformed()
					{
						Model.nextPerformanceExperiment2();
					}
				}), c);
			}
			
			
		    c.ipady = 150;
		    c.ipadx = 200;
		    c.weightx = 0.3;
		    c.weighty = 0.95;
		    c.insets = new Insets(5,5,5,5);
		    c.gridx = 0;
		    c.gridwidth = 1;
		    c.gridheight = 1;
		    c.gridy = 2;
		    this.add(this.map.getMinimap(), c);
		    
			c.ipady = 0;
			c.ipadx = 200;
			c.weightx = 0.2;
			c.weighty = 0.95;   //request any extra vertical space
			c.insets = new Insets(5,5,5,5);  //top padding
			c.gridwidth = 1;
			c.gridheight = 1;
			c.gridx = 3;       //aligned with button 2
			c.gridy = 2;       //third row
			this.add(this.selection, c);
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
		    this.add(this.map.getMinimap(), c);
		
			c.ipady = 0;
			c.ipadx = 200;
			c.weightx = 0.2;
			c.weighty = 1.0;   //request any extra vertical space
			c.insets = new Insets(5,5,5,5);  //top padding
			c.gridheight = 2;
			c.gridx = 3;       //aligned with button 2
			c.gridy = 1;       //third row
			this.add(this.selection, c);
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
        case KeyEvent.VK_UP:
        	this.map.decOffsetY(10);
            break;
        case KeyEvent.VK_DOWN:
        	this.map.incOffsetY(10);
            break;
        case KeyEvent.VK_LEFT:
        	this.map.decOffsetX(10);
            break;
        case KeyEvent.VK_RIGHT :
        	this.map.incOffsetX(10);
            break;
        case KeyEvent.VK_A :
        	this.map.setAttack();
        	break;
        case KeyEvent.VK_S :
        	this.map.stopSelectedAgents();
        	break;
        case KeyEvent.VK_H :
        	this.map.setHold();
        	break;
        case KeyEvent.VK_G :
        	this.map.setGather();
        	break;
        case KeyEvent.VK_M :
        	this.map.setMove();
        	break;
            
        // Escape button, display menu dialog and pause game
        case KeyEvent.VK_ESCAPE :
        	if (Model.isGame_running())
		    	new DialogMenu();
        	break;
        default:
//        	System.out.println("Key pressed");
        	break;
		}
	}

	public void keyReleased(KeyEvent arg0)
	{
//		System.out.println("key released");
	}

	public void keyTyped(KeyEvent arg0)
	{
//		System.out.println("key typed");
	}

	/**
	 * Gets the map reference
	 * @return Map reference
	 */
	public PanelMap getMap()
	{
		return this.map;
	}
	
}
