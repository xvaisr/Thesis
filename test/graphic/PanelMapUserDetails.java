/**
 * BP, anthill strategy game
 * Draws user's details of the game
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/26
 * @version 1
 * @file    graphic.MapUserDetails.java
 */
package graphic;

import Enviroment.Original.Model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

/**
 * Draws user's details of the game
 * @author Vojtech Simetka
 *
 */
public class PanelMapUserDetails extends JPanel implements ComponentListener
{
	private final PanelMap map;
	private int ants_x = 0;
	private int string_start_y = 0;
	private int food_x = 0;
	private int font_size;
	private int water_x;
	private int string_water_x;
	private int string_food_x;
	private int resource_y;
	
	/**
	 * PanelMapUserDetails constructor
	 * @param map Map reference
	 */
	public PanelMapUserDetails(PanelMap map)
	{
		this.map = map;
		this.addComponentListener(this);
		
		this.recalculate();
	}
	
	/**
	 * Paints component
	 */
	protected void paintComponent(Graphics g)
	{	
	    g.setColor(Color.black);
	    
	    
	    g.drawString("Agents: " + this.map.agents.size(), this.ants_x, this.string_start_y);
	    
	    
	    g.setColor(Color.yellow);
	    g.fillRect(this.food_x, this.resource_y, this.font_size, this.font_size);
	    
	    g.setColor(Color.black);
	    g.drawRect(this.food_x, this.resource_y, this.font_size, this.font_size);
	    g.drawString("Food: " + Model.getUserTeam().getFood(), this.string_food_x, this.string_start_y);

	    g.setColor(Color.cyan);
	    g.fillRect(this.water_x, this.resource_y, this.font_size, this.font_size);
	    
	    g.setColor(Color.black);
	    g.drawRect(this.water_x, this.resource_y, this.font_size, this.font_size);
	    g.drawString("Water: " + Model.getUserTeam().getWater(), this.string_water_x, this.string_start_y);
	}
	
	/**
	 * Recalculates components size etc...
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
