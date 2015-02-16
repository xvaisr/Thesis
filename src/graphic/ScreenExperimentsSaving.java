/**
 * BP, anthill strategy game
 * Screen displaying that experiment results are being saved 
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/25
 * @version 1
 * @file    graphic.ScreenExperimentsSaving.java
 */
package graphic;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Screen displaying that experiment results are being saved 
 * @author Vojtech Simetka
 *
 */
public class ScreenExperimentsSaving extends JPanel
{
	/**
	 * Paint component
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.black);
		g.drawString("Saving experiment results.", this.getWidth()/2-50, this.getHeight()/2);
	}
}
