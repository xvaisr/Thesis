/**
 * BP, anthill strategy game
 * Displays minimap
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/26
 * @version 1
 * @file    graphic.PanelMinimap.java
 */
package graphic;

import Agents.AgentInfo;
import Enviroment.Model;
import Enviroment.Resources;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 * Displays minimap
 * @author Vojtech Simetka
 *
 */
public class PanelMinimap extends JPanel implements ComponentListener, MouseMotionListener, MouseListener
{
	private double map_ratio;
	
	// Offset x and y
	private int offset_x;
	private int offset_y;
	
	// Diameters of map
	private int width;
	private int height;
	
	// Display parameters
	private int display_width;
	private int display_height;
	private int display_x;
	private int display_y;
	
	// Map pointer
	private final PanelMap map;
	
	private TexturePaint background;

	/**
	 * Minimap consturctor
	 * @param map Map reference
	 */
	PanelMinimap(PanelMap map)
	{
		this.map = map;
		
		this.setBackground(Color.black);
		
		// Adds component listener
		this.addComponentListener(this);
		
		// Adds mouse listeners
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	/**
	 * Paints component
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		
		Graphics2D g2d = (Graphics2D) g;
			
		// Draws fog of war
 		if (this.background != null)
 		{
 			g2d.setPaint(this.background);
 			g2d.fillRect(this.offset_x, this.offset_y, this.width, this.height);
 		}
					
		// Draws agents
		if (Model.isUserPlaying())
		{
			// Draw food
			g2d.setColor(Color.yellow);
			synchronized(this.map.getDiscoveredFood())
			{
				for (Resources food: this.map.getDiscoveredFood())
				{
					g2d.fillRect((int)(food.getX() / this.map_ratio -2 + this.offset_x),
							     (int)(food.getY() / this.map_ratio -2 + this.offset_y),
							     4,
							     4);
				}
			}
				
			// Draw water
			g2d.setColor(Color.cyan);
			synchronized(this.map.getDiscoveredWater())
			{
				for (Resources water: this.map.getDiscoveredWater())
				{
					g2d.fillRect((int)(water.getX() / this.map_ratio -2 + this.offset_x),
								 (int)(water.getY() / this.map_ratio -2 + this.offset_y),
								 4,
								 4);
				}
			}
			
			synchronized(map.getVisibleEnemies())
			{
				for (AgentInfo agent: this.map.getVisibleEnemies())
				{
					// Selects agents color based on its team
					g2d.setColor(agent.getColor());
						
					g2d.fillRect((int)(agent.getIntPositionX() / map_ratio -3+offset_x),
						(int)(agent.getIntPositionY() / map_ratio -3+offset_y),6, 6);
				}
			}
			
			synchronized(map.agents)
			{
				for (AgentInfo agent: this.map.agents)
				{
					// Selects agents color based on its team
					g2d.setColor(agent.getColor());
						
					g2d.fillRect((int)(agent.getIntPositionX() / map_ratio -3+offset_x),
						(int)(agent.getIntPositionY() / map_ratio -3+offset_y),6, 6);
				}
			}
		}
		else
		{
			// Draw food
			g2d.setColor(Color.yellow);
			synchronized(this.map.food)
			{
				for (Resources food: this.map.food)
				{
					g2d.fillRect((int)(food.getX() / this.map_ratio -2 + this.offset_x),
							     (int)(food.getY() / this.map_ratio -2 + this.offset_y),
							     4,
							     4);
				}
			}
				
			// Draw water
			g2d.setColor(Color.cyan);
			synchronized(this.map.water)
			{
				for (Resources water: this.map.water)
				{
					g2d.fillRect((int)(water.getX() / this.map_ratio -2 + this.offset_x),
								 (int)(water.getY() / this.map_ratio -2 + this.offset_y),
								 4,
								 4);
				}
			}
			
			synchronized(this.map.agents)
			{
				for (AgentInfo agent: this.map.agents)
				{
					// Selects agents color based on its team
					g2d.setColor(agent.getColor());
						
					g2d.fillRect((int)(agent.getIntPositionX() / map_ratio -3+offset_x),
						(int)(agent.getIntPositionY() / map_ratio -3+offset_y),6, 6);
				}
			}
		}
		
		// Draws anthills
		synchronized(map.anthills)
		{
			for (Team anthill : map.anthills)
			{
				g2d.setColor(anthill.getColor());
				g2d.fillRect((int)(anthill.getAnthill().x / map_ratio -5+offset_x),
						(int)(anthill.getAnthill().y / map_ratio -5+offset_y),10, 10);
			}
		}

		
		// Draws rectangle of visible screen	
		g2d.setColor(Color.white);
		
		g2d.drawRect(this.display_x,
					 this.display_y,
					 this.display_width,
					 this.display_height);
	}
	
	/**
	 * Recalculates display starting coordinate x
	 */
	protected void offsetXChanged()
	{
		this.display_x = (int)(this.map.getOffsetX()/map_ratio) + this.offset_x;
	}
	
	/**
	 * Recalculate display starting coordinate y
	 */
	protected void offsetYChanged()
	{
		this.display_y = (int)(this.map.getOffsetY()/map_ratio) + this.offset_y;
	}

	public void componentHidden(ComponentEvent arg0){}
	public void componentMoved(ComponentEvent arg0) {}

	public void componentResized(ComponentEvent arg0)
	{
		// Paints background of the game
		double map_to_minimap_width = (Model.getWorld().getWidth()/(double)this.getWidth());
		double map_to_minimap_height = (Model.getWorld().getHeight()/(double)this.getHeight());
			
		if (map_to_minimap_width > map_to_minimap_height)
		{
			this.map_ratio = map_to_minimap_width;
			this.offset_y = (int) ((this.getHeight() - Model.getWorld().getHeight()/map_ratio)/2);
			this.offset_x = 0;
		}
		else
		{
			this.map_ratio = map_to_minimap_height;
			this.offset_x = (int) ((this.getWidth() - Model.getWorld().getWidth()/map_ratio)/2);
			this.offset_y = 0;
		}
		
		this.width = (int)(Model.getWorld().getWidth()/map_ratio);
		this.height = (int)(Model.getWorld().getHeight()/map_ratio);
		
		this.offsetXChanged();
		this.offsetYChanged();
		
		double displayed_map_to_model_width = (double)(this.map.getWidth()) / Model.getWorld().getWidth();
		double displayed_map_to_model_height = (double)(this.map.getHeight()) / Model.getWorld().getHeight();
		
		this.display_width = (int)(this.width * displayed_map_to_model_width)-1;
		this.display_height = (int)(this.height * displayed_map_to_model_height)-1;
		
		this.background = new TexturePaint(this.map.getFog(), new Rectangle(this.offset_x, this.offset_y, this.width, this.height));
	}

	public void componentShown(ComponentEvent arg0) {}

	public void mouseDragged(MouseEvent arg0)
	{
		this.map.setOffsetX((int)((arg0.getX()-this.display_width/2-this.offset_x)*this.map_ratio));
		this.map.setOffsetY((int)((arg0.getY()-this.display_height/2-this.offset_y)*this.map_ratio));
	}
	
	public void mousePressed(MouseEvent arg0)
	{
		this.map.setOffsetX((int)((arg0.getX()-this.display_width/2-this.offset_x)*this.map_ratio));
		this.map.setOffsetY((int)((arg0.getY()-this.display_height/2-this.offset_y)*this.map_ratio));
	}

	public void mouseMoved(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
