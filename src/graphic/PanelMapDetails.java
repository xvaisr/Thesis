/**
 * BP, anthill strategy game
 * Draws overall details of the game
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/26
 * @version 1
 * @file    graphic.MapDetails.java
 */
package graphic;

import Enviroment.Original.Model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

/**
 * Draws overall details of the game
 * @author Vojtech Simetka
 *
 */
public class PanelMapDetails extends JPanel implements ComponentListener
{
	private int ants_x = 0;
	private int string_start_y = 0;
	private int food_x = 0;
	private int font_size;
	private int water_x;
	private int string_water_x;
	private int string_food_x;
	private int resource_y;
	
	/**
	 * PanelMapDetails constructor
	 */
	PanelMapDetails()
	{
		this.addComponentListener(this);
		
		this.recalculate();
	}
	
	protected void paintComponent(Graphics g)
	{	
	    g.setColor(Color.black);
	    
	    int count = Model.getEnvironment().getActive_agentsA().size() +
					Model.getEnvironment().getActive_agentsB().size() +
					Model.getEnvironment().getActive_agentsC().size() +
					Model.getEnvironment().getActive_agentsD().size();
	    
	    g.drawString("Agents: " + count, this.ants_x, this.string_start_y);
	    
	    
	    g.setColor(Color.yellow);
	    g.fillRect(this.food_x, this.resource_y, this.font_size, this.font_size);
	    
	    g.setColor(Color.black);
	    g.drawRect(this.food_x, this.resource_y, this.font_size, this.font_size);
	    g.drawString("Food: " + Model.getWorld().getCurrentFoodCount(), this.string_food_x, this.string_start_y);

	    g.setColor(Color.cyan);
	    g.fillRect(this.water_x, this.resource_y, this.font_size, this.font_size);
	    
	    g.setColor(Color.black);
	    g.drawRect(this.water_x, this.resource_y, this.font_size, this.font_size);
	    g.drawString("Water: " + Model.getWorld().getCurrentWaterCount(), this.string_water_x, this.string_start_y);
	}
	
	/**
	 * Recalculates relevant parameters
	 */
	private void recalculate()
	{
		this.font_size = (int)(this.getHeight()/2.5);
		this.ants_x = 10;
		this.food_x = this.getWidth()/3+5;
		this.water_x = (int)(this.getWidth()/1.5);
		this.resource_y = (this.getHeight()-this.font_size)/2;
		this.string_water_x = this.water_x + this.font_size + 10;
		this.string_food_x = this.food_x + this.font_size + 10;
		this.string_start_y = this.getHeight()/2 + 5;
	}
	
	public void componentHidden(ComponentEvent e) {}

	public void componentMoved(ComponentEvent e) {}

	public void componentResized(ComponentEvent e)
	{
		this.recalculate();
	}

	public void componentShown(ComponentEvent e) {}
}
