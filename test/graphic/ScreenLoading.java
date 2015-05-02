/**
 * BP, anthill strategy game
 * Screen shown when game is loading
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/25
 * @version 1
 * @file    graphic.ScreenGame.java
 */
package graphic;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Screen shown when game is loading
 * @author Vojtech Simetka
 *
 */
public class ScreenLoading extends JPanel
{
	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.black);
		g.drawString("Loading.", this.getWidth()/2, this.getHeight()/2);
	}
}
