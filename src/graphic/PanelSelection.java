/**
 * BP, anthill strategy game
 * Displays selection part of the screen
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/25
 * @version 1
 * @file    graphic.Environment.java
 */
package graphic;

import Agents.AgentInfo;
import Enviroment.Model;
import Enviroment.Resources;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * Displays selection part of the screen
 * @author Vojtech Simetka
 *
 */
public class PanelSelection extends JPanel implements ComponentListener, MouseListener
{
	private int height;
	private int width;
	
	private int unit_offset_x;
	private int unit_height;
	private int unit_width;
	private int unit_corner_height;
	private int unit_corner_width;
	
	private int control_group;
	private int controll_group_height;
	
	private final PanelMap map;
	
	/**
	 * Selection constructor
	 * @param map Map reference
	 */
	PanelSelection(PanelMap map)
	{
		this.map = map;
		
		this.setBackground(Color.black);
		
		this.control_group = 0;
		
		this.addComponentListener(this);
		this.addMouseListener(this);
	}
	
	/**
	 * Paints component
	 */
	@Override
	protected void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		
	    g.setColor(Color.white);
	    
	    if (!this.map.getSelectedAgents().isEmpty())
	    {
	    	synchronized(this.map.getSelectedAgents())
	    	{
	    		if (this.map.getSelectedAgents().size() == 1)
	    		{
	    			AgentInfo agent = this.map.getSelectedAgents().get(0);
	    	    	g.drawString("Agent: " + agent.getName(), 10, 20);
	    	    	g.drawString("HP: " + agent.getHp() + "%", 10, 40);
	    	    	g.drawString("Armor: " + agent.getArmor(), 10, 60);
	    	    	g.drawString("Attack: " + agent.getAttack(), 10, 80);
	    	    	g.drawString("Speed: " + agent.getSpeed(), 10, 100);
	    		}
	    		else
	    		{
	    			while (this.control_group*9 >= this.map.getSelectedAgents().size())
	    				this.control_group--;
	    			
	    			int start = this.control_group*9;
	    			int end = start + 9;
	    			
	    			if (start + 9 > this.map.getSelectedAgents().size())
	    				end = start + this.map.getSelectedAgents().size()%9;
	    			
	    			for (int i = 0; start < end; i++, start++)
	    				this.drawAgent(g, i, this.map.getSelectedAgents().get(start));
	    			
	    			for (int i = 0; i <= (this.map.getSelectedAgents().size()-1)/9 && i < 7; i++)
	    				this.drawControllGroup(g, i);
	    		}
	    	}
	    }
	    else if (!this.map.getSelectedWater().isEmpty() || !this.map.getSelectedFood().isEmpty())
	    {
	    	int water = 0;
	    	int water_count = 0;
	    	int food = 0;
	    	int food_count = 0;
	    	synchronized(this.map.getSelectedWater())
	    	{
	    		for (Resources resource: this.map.getSelectedWater())
	    		{
	    			water_count++;
	    			water += resource.getAmout();
	    		}
	    	}
	    	synchronized(this.map.getSelectedFood())
	    	{
	    		for (Resources resource: this.map.getSelectedFood())
	    		{
	    			food_count++;
	    			food += resource.getAmout();
	    		}
	    	}
	    	if (water_count > 0)
	    		g.drawString("Water: " + water + " in " + water_count + " fields", this.getWidth()/2 - 50, this.getHeight()/2 + 10);
	    	if (food_count > 0)
	    		g.drawString("Food: " + food + " in " + food_count + " fields", this.getWidth()/2 - 50, this.getHeight()/2 - 10);
	    }
		
	    else
	    {
		    g.drawString("Performance:", 10, this.getHeight()-60);
		    g.drawString("FPS: " + Model.getView().FPS, 10, this.getHeight()-40);
		    g.drawString("MPS: " + Model.getView().MPS, 10, this.getHeight()-20);
		    
		    g.drawString("Mouse position:", 100, this.getHeight()-60);
		    g.drawString("  X: " + this.map.getMouse_position().x, 100, this.getHeight()-40);
		    g.drawString("  Y: " + this.map.getMouse_position().y, 100, this.getHeight()-20);
	    }
	}
	
	/**
	 * Draws control group
	 * @param g Graphics reference
	 * @param i Number of control group
	 */
	private void drawControllGroup(Graphics g, int i)
	{
		if (i == this.control_group)
			g.setColor(Color.white);
		else
			g.setColor(Color.gray);
		
		g.drawRect(5, i*this.controll_group_height + 5, this.unit_offset_x-10, this.controll_group_height-10);
		g.drawString(Integer.toString(i), this.unit_offset_x/2-3, i*this.controll_group_height + this.controll_group_height/2+6);
	}

	/**
	 * Draws agent information to the screen
	 * @param g Graphics reference
	 * @param i Agents number
	 * @param agent Agent reference
	 */
	private void drawAgent(Graphics g, int i, AgentInfo agent)
	{
		int x = i%3*(width)+this.unit_offset_x;
		int y = i/3*(height)+5;
		
		g.setColor(agent.getColor());
		g.drawRoundRect(x, y, this.unit_width, this.unit_height, this.unit_corner_width, this.unit_corner_height);
		
		g.setColor(Color.white);
		g.drawString(agent.getName(), x+this.width/2 - 5, y+this.height/2 - 5);
		
		// Chooses HP color
		int health = agent.getHp();
		if (health > 80)
			g.setColor(Color.green);
		else if (health > 60)
			g.setColor(new Color(100,170,0));
		else if (health > 40)
			g.setColor(new Color(170,120,0));
		else if (health > 20)
			g.setColor(new Color(210,70,0));
		else
			g.setColor(Color.red);
		
		
		// Draws agents health
		g.fillRect(x+5, y+ 4*height/5, (int)((float)(health)/100*width - 10), height/5-5);
		g.setColor(Color.black);
		
		// Draws health gridlines
		for (int j = 0; j < 6; j++)
			g.drawRect(x+5, y+ 4 * height/5, j*(width-10)/5, height/5-5);
	}

	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}

	/**
	 * Recalculates components height and width when it resizes
	 */
	public void componentResized(ComponentEvent e)
	{
		double temp = (this.getWidth()/(double)(20));
		
		this.height = this.getHeight()/3;
		this.width = (int)(temp*6);
		
		this.unit_offset_x = (int)(temp*2) + 5;
		this.unit_height = this.getHeight()/3-10;
		this.unit_width = (int)(this.getWidth()/3.5)-5;
		this.unit_corner_height = this.unit_height/5;
		this.unit_corner_width = this.unit_width/5;
		
		this.controll_group_height = this.getHeight()/7;
	}

	/**
	 * Mouse clicked, determines whether it was within unit
	 */
	public void mouseClicked(MouseEvent e)
	{
		if (this.map.getSelectedAgents().size() > 1)
		{
			if (e.getX() < this.unit_offset_x)
			{
				int size = this.map.getSelectedAgents().size()/9;
				int cg = e.getY()/this.controll_group_height;
				if (size >= cg && cg < 7)
					this.control_group = cg;
			}
			
			else
			{
				int position = ((e.getX()-this.unit_offset_x)/(this.width)) +  3 * ((e.getY()-5)/(this.height)) + this.control_group * 9;
				
				synchronized(this.map.getSelectedAgents())
				{
					if (position >= this.map.getSelectedAgents().size())
						return;
					
					AgentInfo agent = this.map.getSelectedAgents().get(position);
					for (AgentInfo ag: this.map.getSelectedAgents())
						ag.deselect();
					this.map.getSelectedAgents().clear();
					this.map.getSelectedAgents().add(agent);
					agent.select();
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
