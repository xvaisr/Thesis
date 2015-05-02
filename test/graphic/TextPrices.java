/**
 * BP, anthill strategy game
 * Shows price of an upgrade
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/05/15
 * @version 1
 * @file    graphic.TextPrices.java
 */
package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

/**
 * Shows price of an upgrade
 * @author Vojtech SImetka
 *
 */
public class TextPrices extends JPanel implements ComponentListener
{	
	private int food_x;
	private int font_size;
	private int resource_y;
	private int water_x;
	private int string_start_y;
	private int string_water_x;
	private int string_food_x;
	private final Point price;
	
	/**
	 * TextPrice constructor
	 * @param price Source of the price to be displayed
	 */
	public TextPrices(Point price)
	{
		this.addComponentListener(this);
		
		this.price = price;
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.yellow);
	    g.fillRect(this.food_x, this.resource_y, this.font_size, this.font_size);
	    
	    g.setColor(Color.black);
	    g.drawRect(this.food_x, this.resource_y, this.font_size, this.font_size);
	    g.drawString(Integer.toString(this.price.x), this.string_food_x, this.string_start_y);

	    g.setColor(Color.cyan);
	    g.fillRect(this.water_x, this.resource_y, this.font_size, this.font_size);
	    
	    g.setColor(Color.black);
	    g.drawRect(this.water_x, this.resource_y, this.font_size, this.font_size);
	    g.drawString(Integer.toString(this.price.y), this.string_water_x, this.string_start_y);
	}
	
	/**
	 * Recalculates relevant parameters
	 */
	private void recalculate()
	{
		this.font_size = (int)(this.getHeight()/2.5);
		this.food_x = 5;
		this.water_x = (int)(this.getWidth()/3+5);
		this.resource_y = (this.getHeight()-this.font_size)/2;
		this.string_water_x = this.water_x + this.font_size + 10;
		this.string_food_x = this.food_x + this.font_size + 10;
		this.string_start_y = this.getHeight()/2 + 5;
	}

	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void componentResized(ComponentEvent arg0) {
		this.recalculate();
	}

	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
