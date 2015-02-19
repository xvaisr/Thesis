/**
 * BP, anthill strategy game
 * Screen displaying game menu
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/25
 * @version 1
 * @file    graphic.ScreenMenu.java
 */
package graphic;

import Enviroment.Original.Model;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JPanel;

/**
 * Screen displaying game menu
 * @author Vojtech Simetka
 *
 */
public class ScreenMenu extends JPanel
{
	/**
	 * ScreenMenu constructor
	 */
	public ScreenMenu()
	{
		this.setLayout(new GridBagLayout());
		
	    GridBagConstraints c = new GridBagConstraints();

	    c.fill = GridBagConstraints.BOTH;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.gridwidth = 1;
	    c.gridheight = 5;
	    c.gridx = 0;
	    c.gridy = 0;
		this.add(Box.createGlue(),c);

	    c.weightx = 1;
	    c.weighty = 1;
	    c.gridwidth = 1;
	    c.gridheight = 5;
	    c.gridx = 2;
	    c.gridy = 0;
		this.add(Box.createGlue(),c);
		
	    c.weightx = 1;
	    c.weighty = 0.9;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;
	    c.gridy = 0;
		this.add(Box.createGlue(),c);
		
	    c.weightx = 1;
	    c.weighty = 0.9;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;
	    c.gridy = 5;
		this.add(Box.createGlue(),c);
		
		this.setBackground(Color.black);
	    
	    Button start = new Button("Custom game", new ButtonActionListener()
	    {
	    	public void actionToBePerformed()
			{
	    		Model.getView().showCreateGame();
			}
				
	    });
	    c.insets = new Insets(5,5,5,5);
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 1;
	    c.weighty = 0.1;
	    c.gridx = 1;
	    c.gridy = 1;
	    this.add(start,c);
	    
	    Button experiments = new Button("Experiments", new ButtonActionListener()
	    {
	    	public void actionToBePerformed()
			{
	    		Model.getView().showExperiments();
			}
				
	    });
	    c.insets = new Insets(5,5,5,5);
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 1;
	    c.weighty = 0.1;
	    c.gridx = 1;
	    c.gridy = 2;
	    this.add(experiments,c);
		
		Button settings = new Button("Settings", new ButtonActionListener()
	    {
	    	public void actionToBePerformed()
			{
				Model.getView().showSettings();
			}
				
        });
		c.insets = new Insets(5,5,5,5);
	    c.weightx = 1;
	    c.weighty = 0.1;
	    c.gridx = 1;
	    c.gridy = 3;
		this.add(settings,c);
		
		Button exit = new Button("Exit game", new ButtonActionListener()
	    {
	    	public void actionToBePerformed()
			{
				System.exit(0);
			}
				
        });
		c.insets = new Insets(5,5,5,5);
	    c.weightx = 1;
	    c.weighty = 0.1;
	    c.gridx = 1;
	    c.gridy = 4;
		this.add(exit,c);
	}
}
