/**
 * BP, anthill strategy game
 * Screen displaying game settings
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/25
 * @version 1
 * @file    graphic.ScreenSettings.java
 */
package graphic;

import Enviroment.Model;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Screen displaying game settings
 * @author Vojtech Simetka
 *
 */
public class ScreenSettings extends JPanel
{
	/**
	 * Screen settings constructor
	 */
	public ScreenSettings()
	{
		JButton settings = new JButton("Return");
		settings.setAlignmentX(Component.CENTER_ALIGNMENT);
		settings.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Model.getView().showMenu();
			}
				
        });
		
		this.add(settings);
	}
	
	/**
	 * Overrides paint component
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}
