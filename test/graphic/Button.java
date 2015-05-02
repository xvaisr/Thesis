/**
 * BP, anthill strategy game
 * Custom button used in the game
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/15
 * @version 1
 * @file    graphic.Button.java
 */
package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * Custom button used in the game
 * @author Vojtech Simetka
 *
 */
public class Button extends JPanel implements ComponentListener, MouseListener
{
	private int font_size = 0;
	private String label;
	private boolean active = false;
	private Font font = new Font("", Font.PLAIN, this.font_size);
	private final ButtonActionListener listener;
	private boolean mousePressed = false;
	private GradientPaint paint_inactive;
	private GradientPaint paint_active1;
	private GradientPaint paint_active2;
	private GradientPaint grey_paint;
	private boolean paint_gray;
	private boolean hidden;
	private boolean activated;
	
	protected Button()
	{
		this.label = "";
		this.listener = null;
	}
	
	/**
	 * Button constructor
	 * @param label Label to be displayed on the button
	 * @param listener Button's action listener
	 */
	protected Button(String label, ButtonActionListener listener)
	{
		this.label = label;
		
		this.listener = listener;
		
		this.addMouseListener(this);
		this.addComponentListener(this);
		
		this.calculateVariables();
	}
	
	/**
	 * Paint component method
	 */
	@Override
	protected void paintComponent(Graphics g)
	{	
		Graphics2D g2d = (Graphics2D)g;
		
		if (this.hidden)
			return;
		
		else if (this.paint_gray)
			this.paintGray(g2d);
		
		// Draws active version of the button
		else if (this.active || this.activated)
			this.paintActive(g2d);
		
		// Draws inactive version of the button
		else
			this.paintInactive(g2d);
	}
	
	/**
	 * Paints inactive version of the button
	 * @param g2d Graphics2D reference
	 */
	private void paintInactive(Graphics2D g2d)
	{
		g2d.setPaint(this.paint_inactive);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setColor(new Color(90,150,190));
		g2d.drawRect(1, 1, this.getWidth()-3, this.getHeight()-3);
		g2d.setColor(new Color(45,75,95));
		g2d.drawRect(2, 2, this.getWidth()-5, this.getHeight()-5);
		g2d.setColor(new Color(150,150,150));
		
		this.paintLabel(g2d);
	}

	/**
	 * Paints active version of the button
	 * @param g2d Graphics2D reference
	 */
	private void paintActive(Graphics2D g2d)
	{
		g2d.setPaint(this.paint_active1);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight()/2);
		g2d.setPaint(this.paint_active2);
		g2d.fillRect(0, this.getHeight()/2, this.getWidth(), this.getHeight());
		g2d.setColor(new Color(40,200,230));
		g2d.drawRect(1, 1, this.getWidth()-3, this.getHeight()-3);
		g2d.setColor(new Color(20,100,115));
		g2d.drawRect(2, 2, this.getWidth()-5, this.getHeight()-5);
		g2d.setColor(new Color(200,200,200));
		
		this.paintLabel(g2d);
	}
	
	private void paintGray(Graphics2D g2d)
	{
		g2d.setPaint(this.grey_paint);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setColor(new Color(140,140,140));
		g2d.drawRect(1, 1, this.getWidth()-3, this.getHeight()-3);
		g2d.setColor(new Color(70,70,70));
		g2d.drawRect(2, 2, this.getWidth()-5, this.getHeight()-5);
		g2d.setColor(Color.black);
		g2d.setFont(this.font);
	
	
		FontMetrics fm = g2d.getFontMetrics();
	    Rectangle2D rect = fm.getStringBounds(this.label, g2d);
		
		// Draws label
		g2d.drawString(this.label, (int)((this.getWidth()-rect.getWidth())/2), (int)((this.getHeight())/2) + fm.getDescent());
	}
	
	/**
	 * Paints label of the button
	 * @param g2d
	 */
	private void paintLabel(Graphics2D g2d)
	{
		g2d.setFont(this.font);
		
		
		FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(this.label, g2d);
		
		// Draws label
		g2d.drawString(this.label, (int)((this.getWidth()-rect.getWidth())/2), (int)((this.getHeight())/2) + fm.getDescent());
	}
	
	/**
	 * Calculates variables concerning button like font size etc
	 */
	private void calculateVariables()
	{
		this.font_size = (int)(this.getHeight()/2.5);
		this.font = new Font("", Font.PLAIN, this.font_size);
		this.paint_inactive = new GradientPaint(0,0,new Color(16,73,126),0, this.getHeight(),new Color(5,20,35));
		this.paint_active1 = new GradientPaint(0,0,new Color(20,70,150),0, this.getHeight()/2,new Color(30,120,210));
		this.paint_active2 = new GradientPaint(0,this.getHeight()/2,new Color(30,110,190),0, this.getHeight(),new Color(110,170,220));
		this.grey_paint = new GradientPaint(0,this.getHeight()/2,new Color(110,110,110),0, this.getHeight(),new Color(170,170,170));
		
		this.repaint();
	}

	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}

	public void componentResized(ComponentEvent arg0)
	{	
		this.calculateVariables();
	}

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0)
	{
		this.active = true;
		this.repaint();
	}

	public void mouseExited(MouseEvent arg0)
	{
		this.active = false;
		this.repaint();
	}

	public void mousePressed(MouseEvent arg0)
	{
		if (arg0.getX() > 0 &&
			arg0.getY() > 0 &&
			arg0.getX() < this.getWidth() &&
			arg0.getY() < this.getHeight())
			this.mousePressed  = true;
		
		// Resets mouse pressed to false
		else
			this.mousePressed  = false;
	}
	public void mouseReleased(MouseEvent arg0)
	{
		if (this.mousePressed &&
			arg0.getX() > 0 &&
			arg0.getY() > 0 &&
			arg0.getX() < this.getWidth() &&
			arg0.getY() < this.getHeight())
		{
			this.listener.actionToBePerformed();
		}
		
		// Resets mouse pressed to false
		this.mousePressed  = false;
		this.active = false;
	}
	
	/**
	 * Sets new label
	 * @param label New label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	/**
	 * Sets paint grey
	 * @param value Value of gray paint
	 */
	public void setPaintGrey(boolean value)
	{
		this.paint_gray = value;
	}
	/**
	 * Hides the button
	 */
	public void hideButton()
	{
		this.hidden = true;
	}
	
	/**
	 * Shows the button
	 */
	public void showButton()
	{
		this.hidden = false;
	}

	/**
	 * Toggles the button activation
	 */
	public void toogle()
	{
		this.activated = !this.activated;
	}
	
	/**
	 * Activates the button
	 */
	public void activate()
	{
		this.activated = true;
	}

	/**
	 * Deactivates the button
	 */
	public void deactivate()
	{
		this.activated = false;
	}
}
