/**
 * BP, anthill strategy game
 * Map class, responsible for displaying game elements
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/26
 * @version 1
 * @file    graphic.Map.java
 */

package graphic;

import Agents.AgentInfo;
import Enviroment.Original.Model;
import Enviroment.Original.Resources;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Map class, responsible for displaying game elements
 * @author Vojtech Simetka
 *
 */
public class PanelMap extends JPanel implements MouseListener, MouseMotionListener, ComponentListener
{
	// Constants
	protected final int agentRadius;
	protected final int resourcesRadius;
	ArrayDeque<AgentInfo> agents = new ArrayDeque<AgentInfo>();
	ArrayDeque<Resources> food = new ArrayDeque<Resources>();
	ArrayDeque<Resources> water = new ArrayDeque<Resources>();
	HashSet<Team> anthills = new HashSet<Team>();
	
	private Set<AgentInfo> visible_agents = new HashSet<AgentInfo>();
	private Set<Resources> visible_food = new HashSet<Resources>();
	private Set<Resources> visible_water = new HashSet<Resources>();
	
	private int fog_of_war_height = 0;
	private int fog_of_war_width = 0;
	private int fog_of_war_start_x = 0;
	private int fog_of_war_start_y = 0;
	
	private Rectangle2D selection_rect = new Rectangle2D.Double();
	private Point start_point;
	private Point end_point;
	private final List<AgentInfo> selected = new ArrayList<AgentInfo>();
	private Point mouse_position = new Point();
	
	private int offsetX = 0;
	private int offsetY = 0;
	private final List<Resources> selected_water = new ArrayList<Resources>();
	private final List<Resources> selected_food = new ArrayList<Resources>();
	
	private final PanelMinimap minimap = new PanelMinimap(this);

	// Variables used for drawing map
	private BufferedImage percepts;
	private BufferedImage fog;
	private TexturePaint unveiled;
	private TexturePaint percepts_paint;
	private boolean attack;
	private boolean gather;
	private boolean move;
	private boolean dont_draw_rect;
	private PanelMapUserControl user_panel;

	/**
	 * Map constructor, reads textures
	 */
	public PanelMap()
	{	
		this.agentRadius = Model.getConfiguration().getAgentRadius();
		this.resourcesRadius = Model.getConfiguration().getResourceRadius();
		
		//
		this.setBackground(Color.black);
		
		// Adds mouse listeners
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		// Adds component listener
		this.addComponentListener(this);
		
		Model.getEnvironment().setMap(this);
	}
	
	/**
	 * Initializes the fog of war
	 * @param height Height of the fog of war
	 * @param width Width of the fog of war
	 */
	public void initializeFogOfWar(int height, int width, String path, List<Obstacles> obstacle_list)
	{
		if (this.getWidth() < width)
		{
			this.fog_of_war_width = this.getWidth();
			this.fog_of_war_start_x = 0;
		}
		else
		{
			this.fog_of_war_width = width;
			this.fog_of_war_start_x = -this.offsetX;
		}

		if (this.getHeight() < height)
		{
			this.fog_of_war_height = this.getHeight();
			this.fog_of_war_start_y = 0;
		}
		
		else
		{
			this.fog_of_war_height = height;
			this.fog_of_war_start_y = -this.offsetY;
		}
		
		BufferedImage unveiled = null;
		
		// No texture, create new one and fill it with white colour
		if (path == null)
		{
			this.percepts = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			this.fog = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			unveiled = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
			this.percepts.getGraphics().setColor(Color.white);
			this.percepts.getGraphics().fillRect(0, 0, width, height);

			this.fog.getGraphics().setColor(Color.white);
			this.fog.getGraphics().fillRect(0, 0, width, height);
			
			unveiled.getGraphics().setColor(Color.white);
			unveiled.getGraphics().fillRect(0, 0, width, height);
		}
		
		// Loads textures
		else
		{
			try
			{
				// Loads textures
				this.percepts = ImageIO.read(this.getClass().getResource(path));
				this.fog = ImageIO.read(this.getClass().getResource(path));
				unveiled = ImageIO.read(this.getClass().getResource(path));
			}
			catch (IOException e)
			{
				System.out.println("Error loading textures");
				e.printStackTrace();
			}
		}
		
		// Draws textures on the fog
		Graphics2D g2d = this.fog.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f));
		g2d.setColor(Color.black);
		g2d.fillRect(0,0,this.fog.getWidth(),this.fog.getHeight());
		
		// Draws obstacles
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		this.drawObstacles(g2d, obstacle_list);
		
		// Draws unveiled texture
		g2d = unveiled.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2d.setColor(Color.black);
		g2d.fillRect(0,0,unveiled.getWidth(),unveiled.getHeight());
		
		// Draws obstacles
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		this.drawObstacles(g2d, obstacle_list);
		
		// Draws obstacles
		this.drawObstacles(this.percepts.createGraphics(), obstacle_list);
		
		this.unveiled = new TexturePaint(unveiled, new Rectangle(0, 0, unveiled.getWidth(), unveiled.getHeight()));
	}

	/**
	 * Overwrites painting method to display map
	 * @param g Graphic pointer
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		
		Graphics2D g2d = (Graphics2D) g;
	    
		// Draws fog of war
 		if (this.fog != null)
 			this.drawFog(g2d);
		
 		// Draws user's game elements
		if (Model.isUserPlaying())
			this.drawUserGameElements(g2d);
		
		// Draws all game elements
		else
			this.drawGameElements(g2d);
		
		
		// Draws selection rectangle if necessary
		this.drawSelectionRectangle(g2d);
		
		// Draws game time
		this.drawTime(g2d);
	}
	
	/**
	 * Draws all game elements to the map
	 * @param g2d Graphics2D reference
	 */
	private void drawGameElements(Graphics2D g2d)
	{
		// Draw anthill
		this.drawAntHills(g2d);
		
		// Draw food
		synchronized(this.food)
		{
			for (Resources food: this.food)
				this.drawFood(g2d, food);
		}
		
		// Draw water
		synchronized(this.water)
		{
			for (Resources water: this.water)
				this.drawWater(g2d, water);
		}

		// Draws agents
		synchronized(this.agents)
		{
			for (AgentInfo agent: this.agents)
				this.drawAg(g2d, agent);
		}
	}

	/**
	 * Draws game elements visible by user's units
	 * @param g2d Graphics2D reference
	 */
	private void drawUserGameElements(Graphics2D g2d)
	{
		// Draw anthill
		this.drawAntHills(g2d);
		
		// Draws discovered food
		synchronized(this.visible_food)
		{
			for (Resources food: this.visible_food)
				this.drawFood(g2d, food);
		}
		
		// Draws discovered water
		synchronized(this.visible_water)
		{
			for (Resources water: this.visible_water)
				this.drawWater(g2d, water);
		}
		
		// Draws agents
		synchronized(this.visible_agents)
		{
			for (AgentInfo enemy: this.visible_agents)
				this.drawEnemies(g2d, enemy);
		}
		
		// Draws agents
		synchronized(this.visible_agents)
		{
			for (AgentInfo agents: this.agents)
				this.drawAg(g2d, agents);
		}
	}

	/**
	 * Draws the fog of war
	 * @param g2d Graphics2D reference
	 */
	private void drawFog(Graphics2D g2d)
	{
		// Draws map's texture
		g2d.setPaint(new TexturePaint(this.fog, new Rectangle(-this.offsetX, -this.offsetY, this.fog.getWidth(), this.fog.getHeight())));
		g2d.fillRect(this.fog_of_war_start_x, this.fog_of_war_start_y, this.fog_of_war_width, this.fog_of_war_height);
		
		// Draws perceptual range
		if (this.percepts_paint != null)
 		{
	 		synchronized(this.agents)
	 		{
	 			// Draws perceptual range reveals map
	 			if (Model.getConfiguration().drawFog())
	 			{
			 		for (AgentInfo agent: this.agents)
			 		{
			 			this.drawPerceptRange(g2d, agent, this.percepts_paint);
			 			
			 			// reveals map
			 			if (this.fog != null)
			 				this.add_revealed(agent.getIntPositionX(), agent.getIntPositionY());
			 		}
	 			}
	 			
	 			// Draws perceptual range only
	 			else
	 			{
			 		for (AgentInfo agent: this.agents)
			 			this.drawPerceptRange(g2d, agent, this.percepts_paint);
	 			}
	 		}
 		}
	}

	/**
	 * Draws countdown to the middle of the game
	 * @param g2d Graphic pointer
	 * @param s Number of seconds till the game starts
	 * @param ms Number of milliseconds till the game starts
	 */
	private void drawCountDown(Graphics2D g2d, long s, long ms)
	{
		g2d.setColor(Color.red);
		
		int font_height = (int)((this.getHeight() * ms) / 100);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(ms)/100));
		
		// Sets font height
		g2d.setFont(new Font("", Font.PLAIN, font_height));
		
		// Draws countdown
		g2d.drawString(Long.toString(s+1), this.getWidth()/2-font_height/5, this.getHeight()/2+font_height/3);
	}
	
	/**
	 * Draws time to the bottom left corner of the map
	 * @param g2d Graphic pointer
	 */
	private void drawTime(Graphics2D g2d)
	{	
		// Sets opacity to 100%
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		// Draws time background
		g2d.setColor(new Color(240,240,240));
		g2d.fillRoundRect(-10, this.getHeight()-25, 100, 30,10,5);
		
		g2d.setColor(Color.black);
		
		if (Model.getAgentsMovenentThread() == null)
			return;
		
		// Calculates time
		long ms = Math.abs(Model.getAgentsMovenentThread().getGameLength()%100);
	    long s = Math.abs(Model.getAgentsMovenentThread().getGameLength()/100%60);
	    long m = Model.getAgentsMovenentThread().getGameLength()/6000%60;
	    long h = Model.getAgentsMovenentThread().getGameLength()/360000;
	    
	    // Game is in process, display time
	    if (Model.getAgentsMovenentThread().getGameLength() > 0)
	    	g2d.drawString(String.format("%02d:%02d:%02d:%02d", h, m, s, ms), 5, this.getHeight()-5);
	    
	    // Game is about to be started, draw countdown
	    else
	    {
		    g2d.drawString(String.format("%02d:%02d:%02d:%02d", 0, 0, 0, 0), 5, this.getHeight()-5);
		    this.drawCountDown(g2d, s, ms);
	    }
	}

	/**
	 * Draws all obstacles to the map texture
	 * @param g2d Graphic pointer
	 */
	private void drawObstacles(Graphics2D g2d, List<Obstacles> obstacle_list)
	{
		g2d.setColor(Color.black);
		
		// For all obstacles
		for (Obstacles obstacle: obstacle_list)
		{
			// Obstacle x points
			int x[] = new int[obstacle.getLength()];
			int y[] = new int[obstacle.getLength()];
			
			int ox[] = obstacle.getXs();
			int oy[] = obstacle.getYs();
			
			// Adds offsets so that the obstacle is draw at adequate position
			for (int i = 0; i < x.length; i++)
			{
				x[i] = ox[i] - this.offsetX;
				y[i] = oy[i] - this.offsetY;
			}
			
			// Draws obstacle
			g2d.fillPolygon(x, y, x.length);
		}
	}

	/**
	 * Draws perceptual range of agent to the map texture
	 * @param g2d Graphic pointer
	 * @param agent Agent information
	 */
	private void drawPerceptRange(Graphics2D g2d, AgentInfo agent, TexturePaint percept)
	{
		// Draws texture on rectangle
		g2d.setPaint(percept);
		g2d.fillOval(agent.getIntPositionX() - Model.getEnvironment().getPerceptRadius() - offsetX,
					(agent.getIntPositionY()) - Model.getEnvironment().getPerceptRadius() - offsetY,
					2 * Model.getEnvironment().getPerceptRadius(),
					2 * Model.getEnvironment().getPerceptRadius());

		g2d.setColor(Color.black);
	}
	
	/**
	 * Draws selection rectangle if necessary
	 * @param g2d Graphics pointer
	 */
	private void drawSelectionRectangle(Graphics2D g2d)
	{
		// Draw inner part of rectangle
		g2d.setColor(new Color(30,160,30));			
		Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .75f);
		g2d.setComposite(c);
		g2d.fill(selection_rect);
		    
		// Draw rectangle's borders
		g2d.setColor(Color.GREEN);
		g2d.draw(selection_rect);
	}

	/**
	 * Draws agent
	 * @param g2d graphics pointer
	 * @param agent Agent's descriptor
	 */
	private void drawAg(Graphics2D g2d, AgentInfo agent)
	{
		// Draws selection ring around agent if necessary
		if (agent.isSelected())
		{
			g2d.setColor(Color.green);
			
			g2d.setStroke(new BasicStroke(2,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND,
                    10));
			
			// Draws selection ring around agent
			g2d.drawOval(agent.getIntPositionX()-agentRadius-3 - offsetX,
				         agent.getIntPositionY()-agentRadius-3 - offsetY,
				         2*this.agentRadius+6,
				         2*this.agentRadius+6);
			
			g2d.setStroke(new BasicStroke(1,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND,
                    10));
			
			// Draws where agent is going
			g2d.drawLine(agent.getIntPositionX() - this.offsetX, agent.getIntPositionY()- this.offsetY, agent.getDestinationX() - this.offsetX, agent.getDestinationY()- this.offsetY);
			Point previous = null;
			synchronized(agent.getPath())
			{
				for (Point p: agent.getPath())
				{
					if (previous != null)
						g2d.drawLine(p.x - this.offsetX, p.y- this.offsetY, previous.x - this.offsetX, previous.y- this.offsetY);
				
					previous = p;
				}
				if (previous != null)
					g2d.drawLine(agent.getDestinationX() - this.offsetX, agent.getDestinationY()- this.offsetY, previous.x - this.offsetX, previous.y- this.offsetY);
			}
		}
		
		// Selects agents color based on agent's team
		g2d.setColor(Model.getWorld().getTeam(agent.getTeam()).getColor());
		
		// Draws agent
		g2d.fillOval(agent.getIntPositionX() - agentRadius - offsetX,
					 agent.getIntPositionY() - agentRadius - offsetY,
					 2 * this.agentRadius,
					 2 * this.agentRadius);
		
		g2d.setColor(Color.black);
		g2d.drawOval(agent.getIntPositionX() - agentRadius - offsetX,
					 agent.getIntPositionY() - agentRadius - offsetY,
					 2 * this.agentRadius,
					 2 * this.agentRadius);
		
		
		// Agent is carrying water
		if (agent.getWater())
		{
			g2d.setColor(Color.cyan);
			g2d.fillRect(agent.getIntPositionX() - offsetX,
						 agent.getIntPositionY() - offsetY,
						 this.resourcesRadius,
						 this.resourcesRadius);
		}
		// Agent is carrying food
		else if (agent.getFood())
		{
			g2d.setColor(Color.yellow);
			g2d.fillRect(agent.getIntPositionX() - offsetX,
					     agent.getIntPositionY() - offsetY,
					     this.resourcesRadius,
					     this.resourcesRadius);
		}
		
		// Draws HP background
		g2d.setColor(new Color(238,238,238));
		g2d.fillRect(agent.getIntPositionX() - agentRadius - 4 - offsetX,
				     agent.getIntPositionY() - 2 * agentRadius - offsetY,
				     30,
				     5);
		
		// Chooses HP color
		float health = (float)agent.getHp()/100;
		if (health > 0.8)
			g2d.setColor(Color.green);
		else if (health > 0.6)
			g2d.setColor(new Color(100,170,0));
		else if (health > 0.4)
			g2d.setColor(new Color(170,120,0));
		else if (health > 0.2)
			g2d.setColor(new Color(210,70,0));
		else
			g2d.setColor(Color.red);
		
		
		// Draws agents health
		g2d.fillRect(agent.getIntPositionX() - agentRadius - 4 - offsetX,
					 agent.getIntPositionY() - 2 * agentRadius - offsetY,
					 (int)(30*health),
					 5);
		g2d.setColor(Color.black);
		
		// Draws health gridlines
		for (int i = 0, j = 6; i < 5; i++, j+=6)
			g2d.drawRect(agent.getIntPositionX() - agentRadius - 5 - offsetX,
						 agent.getIntPositionY() - 2 * agentRadius - 1 - offsetY,
						 j,
						 6);
	}
	
	/**
	 * Draws enemies, not all information are drawn
	 * @param g2d Graphics2D reference
	 * @param agent Agent to be drawn
	 */
	private void drawEnemies(Graphics2D g2d, AgentInfo agent)
	{
		// Draws selection ring around agent if necessary
		if (agent.isSelected())
		{
			g2d.setColor(Color.green);
			
			g2d.setStroke(new BasicStroke(2,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND,
                    10));
			
			// Draws selection ring around agent
			g2d.drawOval(agent.getIntPositionX()-agentRadius-3 - offsetX,
				         agent.getIntPositionY()-agentRadius-3 - offsetY,
				         2*this.agentRadius+6,
				         2*this.agentRadius+6);
			
			g2d.setStroke(new BasicStroke(1,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND,
                    10));
		}
		
		// Selects agents color based on agent's team
		g2d.setColor(Model.getWorld().getTeam(agent.getTeam()).getColor());
		
		// Draws agent
		g2d.fillOval(agent.getIntPositionX() - agentRadius - offsetX,
					 agent.getIntPositionY() - agentRadius - offsetY,
					 2 * this.agentRadius,
					 2 * this.agentRadius);
		
		g2d.setColor(Color.black);
		g2d.drawOval(agent.getIntPositionX() - agentRadius - offsetX,
					 agent.getIntPositionY() - agentRadius - offsetY,
					 2 * this.agentRadius,
					 2 * this.agentRadius);
		
		
		// Agent is carrying water
		if (agent.getWater())
		{
			g2d.setColor(Color.cyan);
			g2d.fillRect(agent.getIntPositionX() - offsetX,
						 agent.getIntPositionY() - offsetY,
						 this.resourcesRadius,
						 this.resourcesRadius);
		}
		// Agent is carrying food
		else if (agent.getFood())
		{
			g2d.setColor(Color.yellow);
			g2d.fillRect(agent.getIntPositionX() - offsetX,
					     agent.getIntPositionY() - offsetY,
					     this.resourcesRadius,
					     this.resourcesRadius);
		}
		
		// Draws HP background
		g2d.setColor(new Color(238,238,238));
		g2d.fillRect(agent.getIntPositionX() - agentRadius - 4 - offsetX,
				     agent.getIntPositionY() - 2 * agentRadius - offsetY,
				     30,
				     5);
		
		// Chooses HP color
		float health = (float)agent.getHp()/100;
		if (health > 0.8)
			g2d.setColor(Color.green);
		else if (health > 0.6)
			g2d.setColor(new Color(100,170,0));
		else if (health > 0.4)
			g2d.setColor(new Color(170,120,0));
		else if (health > 0.2)
			g2d.setColor(new Color(210,70,0));
		else
			g2d.setColor(Color.red);
		
		
		// Draws agents health
		g2d.fillRect(agent.getIntPositionX() - agentRadius - 4 - offsetX,
					 agent.getIntPositionY() - 2 * agentRadius - offsetY,
					 (int)(30*health),
					 5);
		g2d.setColor(Color.black);
		
		// Draws health gridlines
		for (int i = 0, j = 6; i < 5; i++, j+=6)
			g2d.drawRect(agent.getIntPositionX() - agentRadius - 5 - offsetX,
						 agent.getIntPositionY() - 2 * agentRadius - 1 - offsetY,
						 j,
						 6);
	}

	/**
	 * Adds discovered area to the map
	 * @param x Center of x coordinate for the circle
	 * @param y Center of y coordinate for the circle
	 */
	private void add_revealed(int x, int y)
	{
		Graphics2D g2d = this.fog.createGraphics();
		g2d.setPaint(this.unveiled);
		g2d.fillOval(x - Model.getEnvironment().getPerceptRadius(),
					 y - Model.getEnvironment().getPerceptRadius(),
					 2 * Model.getEnvironment().getPerceptRadius(),
					 2 * Model.getEnvironment().getPerceptRadius());
	}
	
	/**
	 * Draws water
	 * @param g2d Graphics pointer
	 * @param water Coordinates of water
	 */
	private void drawWater(Graphics2D g2d, Resources water)
	{
		// Draws selected ring, if resource is selected
		if (water.isSelected())
		{
			g2d.setColor(Color.yellow);
			g2d.drawOval(water.getX() - 2 * this.resourcesRadius - offsetX,
						 water.getY() - 2 * this.resourcesRadius - offsetY,
						 4 * this.resourcesRadius,
						 4 * this.resourcesRadius);
		}
		
		// draws resource itself
		if (water.isDirectlyVisible())
			g2d.setColor(Color.cyan);
		
		else
			g2d.setColor(new Color(60,130,130));
			
		g2d.fillRect(water.getX() - this.resourcesRadius - offsetX,
					 water.getY() - this.resourcesRadius - offsetY,
					 2 * this.resourcesRadius,
					 2 * this.resourcesRadius);
	}

	/**
	 * Draws food
	 * @param g2d Graphics pointer
	 * @param food Coordinates of food
	 */
	private void drawFood(Graphics2D g2d, Resources food)
	{
		// Draws selection ring, if resource is selected
		if (food.isSelected())
		{
			g2d.setColor(Color.yellow);
			g2d.drawOval(food.getX() - 2 * this.resourcesRadius - offsetX,
						 food.getY() - 2 * this.resourcesRadius - offsetY,
						 4 * this.resourcesRadius,
						 4 * this.resourcesRadius);
		}
		
		// Draws resource itself
		if (food.isDirectlyVisible())
			g2d.setColor(Color.yellow);
		
		else
			g2d.setColor(new Color(160,160,70));
		
		g2d.fillRect(food.getX() - this.resourcesRadius - offsetX, 
					 food.getY() - this.resourcesRadius - offsetY,
					 2 * this.resourcesRadius,
					 2 * this.resourcesRadius);
	}

	/**
	 * Draws anthills of all teams present in game
	 * @param g2d Graphic reference
	 */
	private void drawAntHills(Graphics2D g2d)
	{
		synchronized(this.anthills)
		{
			for (Team anthill: this.anthills)
				drawAnthill(g2d,anthill);
		}
	}
	
	/**
	 * Draws anthill
	 * @param g2d Graphic pointer
	 * @param team Reference to the anthill
	 */
	private void drawAnthill(Graphics2D g2d, Team team)
	{
		g2d.setColor(Color.black);
		g2d.fillRect(team.getAnthill().x- 20 - offsetX,
					 team.getAnthill().y - 20 - offsetY,
					 40,
					 40);
		
		// Gets color of the team and sets it
		g2d.setColor(team.getColor());

		// Draws name of the team
		g2d.drawString(team.getTeam().toString(),
					   team.getAnthill().x - 10 - offsetX,
					   team.getAnthill().y - offsetY);
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	/**
	 * 
	 * @param e mouse event
	 */
	public void mousePressed(MouseEvent e)
	{
		// Left mouse click
		if (SwingUtilities.isLeftMouseButton(e))
		{
				this.start_point = e.getPoint();
				this.end_point = e.getPoint();
		}
	}

	/**
	 * Mouse release event
	 */
	public void mouseReleased(MouseEvent e)
	{
		if (this.user_panel != null)
			this.user_panel.disableAll();
		
		// Left mouse click - select units
		if (SwingUtilities.isLeftMouseButton(e))
		{
			if (this.attack)
			{
				this.user_panel.disableAttack();
				int x = e.getX()+this.offsetX;
				int y = e.getY()+this.offsetY;
				AgentInfo enemy = this.enemyAt(x,y);
				
				if (enemy == null)
				{
					for (AgentInfo agent: this.agents)
						agent.moveWithAttack(x,y);
				}
				else
				{
					for (AgentInfo agent: this.agents)
						agent.attack(enemy);
				}
				this.attack = false;
				this.dont_draw_rect = false;
			}
			
			else if (this.gather)
			{
				int x = e.getX()+this.offsetX;
				int y = e.getY()+this.offsetY;
				Point collect_resource_at = this.resourceAt(x,y);
				if (collect_resource_at != null)
				{
					for (AgentInfo agent: this.agents)
						agent.collect(collect_resource_at);
				}
				this.gather = false;
				this.dont_draw_rect = false;
			}
			
			else if (this.move)
			{
				int x = e.getX()+this.offsetX;
				int y = e.getY()+this.offsetY;
				
				for (AgentInfo agent: this.agents)
					agent.moveTowards(x, y);
				
				this.move = false;
				this.dont_draw_rect = false;
			}
			
			else
				{
				
				// Selects agents 
				this.selectAgents();
	
				// Reinitializes rectangle
				this.selection_rect = new Rectangle2D.Double();
				
				// No agent was selected, select some resources that are within selection rectangle
				if (this.selected.isEmpty())
					this.selectResource();
				
				// At least one agent was selected, deselect any food that is selected
				else
				{
					synchronized(this.selected_water)
					{
						for (Resources resource: this.selected_water)
							resource.deselect();
						
						this.selected_water.clear();
					}
					synchronized(this.selected_food)
					{
						for (Resources resource: this.selected_food)
							resource.deselect();
						
						this.selected_food.clear();
					}
				}
			}
		}
		
		// Right mouse click - if a unit is selected, move to location
		else if (SwingUtilities.isRightMouseButton(e))
		{
			if (Model.isUserPlaying())
				{
				
				int x = e.getX() + offsetX;
				int y = e.getY() + offsetY;
				
				AgentInfo enemy = this.enemyAt(x,y);
				Point collect_resource_at = this.resourceAt(x,y);
				
				// Does an action for all selected agents
				synchronized(this.selected)
				{
					for (AgentInfo agent: this.selected)
					{
						if (agent.isUserControlled())
						{
							if (enemy != null)
							{
								agent.attack(enemy);
								continue;
							}
							if (collect_resource_at != null)
								agent.collect(collect_resource_at);
							
							else
								agent.moveTowards(x,y);
						}
					}
				}
			}
		}
	}

	/**
	 * Returns exact location of perceived resources around coordinates x and y
	 * @param x Coordinate x
	 * @param y Coordinate y
	 * @return Exact location of a resources around coordinate x and y
	 */
	private Point resourceAt(int x, int y)
	{
		synchronized(this.visible_food)
		{
			for (Resources food: this.visible_food)
			{
				if (food.getX() >= x - this.resourcesRadius &&
					food.getY() >= y - this.resourcesRadius &&
					food.getX() <= x + this.resourcesRadius &&
					food.getY() <= y + this.resourcesRadius)
					return new Point(food.getX(), food.getY());
			}
		}
		
		synchronized(this.visible_water)
		{
			for (Resources food: this.visible_water)
			{
				if (food.getX() >= x - this.resourcesRadius &&
					food.getY() >= y - this.resourcesRadius &&
					food.getX() <= x + this.resourcesRadius &&
					food.getY() <= y + this.resourcesRadius)
					return new Point(food.getX(), food.getY());
			}
		}
		
		return null;
	}

	/**
	 * Returns enemy who is nearby location x and y
	 * @param x Coordinate x
	 * @param y Coordinate y
	 * @return Enemy nearby location x and y
	 */
	private AgentInfo enemyAt(int x, int y)
	{
		AgentInfo agent = null;
		synchronized(this.agents)
		{
			if (this.agents.isEmpty())
				return null;
			
			agent = this.agents.peek();
		}
		
		synchronized(this.visible_agents)
		{
			for (AgentInfo enemy: this.visible_agents)
			{
				if (enemy.getAlliance() != agent.getAlliance() &&
					enemy.getIntPositionX() >= x - this.agentRadius &&
					enemy.getIntPositionY() >= y - this.agentRadius &&
					enemy.getIntPositionX() <= x + this.agentRadius &&
					enemy.getIntPositionY() <= y + this.agentRadius)
					return enemy;
			}
		}
		return null;
	}

	/**
	 * Selects all resources that are within selection
	 */
	private void selectResource()
	{	
		// Clears selection
		synchronized(this.selected_water)
		{
			this.selected_water.clear();
		}
		
		synchronized(this.selected_food)
		{
			this.selected_food.clear();
		}
		
		if (Model.isUserPlaying())
		{
			this.addFoodToSelectionFromList(this.visible_food);
			
			this.addWaterToSelectionFromList(this.visible_water);
		}
		
		else
		{
			this.addFoodToSelectionFromList(this.food);
			
			this.addWaterToSelectionFromList(this.water);
		}
	}
	
	/**
	 * Adds water from source to selection if it is within selection rectangle
	 * @param source Source of the water
	 */
	private void addWaterToSelectionFromList(ArrayDeque<Resources> source)
	{
		synchronized(source)
		{
			for (Resources water: source)
			{
				// If within selection rectangle, add agent to selection
				if (this.isWithinSelection(water.getX()  - offsetX,
										   water.getY()  - offsetY))
					this.addWaterToSelection(water);
				
				// Deselects resource that is not within selection rectangle
				else
					water.deselect();
			}
		}
	}

	/**
	 * Adds water from source to selection if it is within selection rectangle
	 * @param source Source of the water
	 */
	private void addWaterToSelectionFromList(Set<Resources> source)
	{
		synchronized(source)
		{
			for (Resources water: source)
			{
				// If within selection rectangle, add agent to selection
				if (this.isWithinSelection(water.getX()  - offsetX,
										   water.getY()  - offsetY))
					this.addWaterToSelection(water);
				
				// Deselects resource that is not within selection rectangle
				else
					water.deselect();
			}
		}
	}

	/**
	 * Adds food from source to selection if it is within selection rectangle
	 * @param source Source of the food
	 */
	private void addFoodToSelectionFromList(ArrayDeque<Resources> source)
	{
		synchronized(source)
		{
			for (Resources food: source)
			{
				// If within selection rectangle, add agent to selection
				if (this.isWithinSelection(food.getX()  - offsetX,
										   food.getY()  - offsetY))
					this.addFoodToSelection(food);
				
				// Deselects resource that is not within selection rectangle
				else
					food.deselect();
			}
		}
	}

	/**
	 * Adds food from source to selection if it is within selection rectangle
	 * @param source Source of the food
	 */
	private void addFoodToSelectionFromList(Set<Resources> source)
	{
		synchronized(source)
		{
			for (Resources food: source)
			{
				// If within selection rectangle, add agent to selection
				if (this.isWithinSelection(food.getX()  - offsetX,
										   food.getY()  - offsetY))
					this.addFoodToSelection(food);
				
				// Deselects resource that is not within selection rectangle
				else
					food.deselect();
			}
		}
	}
	
	/**
	 * Adds resource to selected list
	 * @param resource Resource to be added to selected list
	 */
	private void addFoodToSelection(Resources resource)
	{
		synchronized(this.selected_food)
		{
			this.selected_food.add(resource);
		}
		
		// Marks this resource as selected
		resource.select();
	}
	
	/**
	 * Adds resource to selected list
	 * @param resource Resource to be added to selected list
	 */
	private void addWaterToSelection(Resources resource)
	{
		synchronized(this.selected_water)
		{
			this.selected_water.add(resource);
		}
		
		// Marks this resource as selected
		resource.select();
	}

	/**
	 * Mouse dragged, draw selection rectangle
	 */
	public void mouseDragged(MouseEvent e)
	{
		if (this.dont_draw_rect)
			return;
			
		if (SwingUtilities.isLeftMouseButton(e))
		{
			this.end_point.x = e.getX();
			this.end_point.y = e.getY();
			
			// End point's coordinate x is off the map 
			if (this.end_point.x > this.getWidth())
				this.end_point.x = this.getWidth();
			
			// End point's coordinate x is off the map
			else if (this.end_point.x < 0)
				this.end_point.x = 0;
			
			// End point's coordinate y is off the map
			if (this.end_point.y > this.getHeight())
				this.end_point.y = this.getHeight();
			
			// End point's coordinate y is off the map
			else if (this.end_point.y < 0)
				this.end_point.y = 0;
				
			// Creates selection rectangle
			selection_rect.setFrameFromDiagonal(this.end_point.x, this.end_point.y, start_point.x, start_point.y);
		}
	}

	/**
	 * Mouse moved, changes information about its position
	 */
	public void mouseMoved(MouseEvent e)
	{
		this.mouse_position = new Point(e.getX() + this.offsetX, e.getY() + this.offsetY);
	}
	
	/**
	 * Selects all agents under selection rectangle
	 */
	private void selectAgents()
	{
		// Clears selection
		this.selected.clear();
		
		// Goes through all agents and decides whose lies within selection rectangle
		if (Model.isUserPlaying())
		{
			this.selectAgentsFromList(this.agents);
			
			if (this.selected.isEmpty())
			{
				this.selectAgentsFromList(this.visible_agents);
				this.user_panel.hideButtons();
			}
			else
				this.user_panel.showButtons();
		}
		
		else
			this.selectAgentsFromList(this.agents);
	}
	
	/**
	 * Selects agents from source which are within selection rectangle
	 * @param source Source of the agents
	 */
	private void selectAgentsFromList(ArrayDeque<AgentInfo> source)
	{
		synchronized(source)
		{
			for (AgentInfo agent: source)
			{
				// If within selection rectangle, add agent to selection
				if (this.isWithinSelection(agent.getIntPositionX()  - offsetX,
										   agent.getIntPositionY()  - offsetY))
				{
					if (this.user_panel != null)
					{
						if (agent.isHolding())
							this.user_panel.enableHold();
						
						else if (agent.isGathering())
							this.user_panel.enableGather();
						
						else if (agent.isMoving())
							this.user_panel.enableMove();
						
						else if (agent.isAttacking())
							this.user_panel.enableAttack();
					}
					this.selectAgent(agent);
				}
			
				// Agent that doesn't lie within selection is unselected 
				else
					agent.deselect();
			}
		}
	}

	
	/**
	 * Selects agents from source which are within selection rectangle
	 * @param source Source of the agents
	 */
	private void selectAgentsFromList(Set<AgentInfo> source)
	{
		synchronized(source)
		{
			for (AgentInfo agent: source)
			{
				// If within selection rectangle, add agent to selection
				if (this.isWithinSelection(agent.getIntPositionX()  - offsetX,
										   agent.getIntPositionY()  - offsetY))
					this.selectAgent(agent);
			
				// Agent that doesn't lie within selection is unselected 
				else
					agent.deselect();
			}
		}
	}
	
	/**
	 * Detects if object is within boundaries of selection rectangle
	 * @param x Coordinate x of the object
	 * @param y Coordinate y of the object
	 * @return True if the object lies within selection rectangle
	 */
	private boolean isWithinSelection(int x, int y)
	{
		// Center of rectangle in coordinate x
		int xs = (this.start_point.x + this.end_point.x) / 2;
		// Half of the rectangle's width
		int xl = Math.abs((this.start_point.x - this.end_point.x)/2) + this.agentRadius ;
		
		// Center of rectangle in coordinate y
		int ys = (this.start_point.y + this.end_point.y) / 2;
		// Half of the rectangle's height
		int yl = Math.abs((this.start_point.y - this.end_point.y)/2) + this.agentRadius;
		
		// True if point lies within center of rectangle +- half of the width/height
		return (xs - xl <= x) &&
			   (xs + xl >= x) && 
			   (ys - yl <= y) &&
			   (ys + yl >= y);
	}
	
	/**
	 * Selects agent
	 * @param agent Agents descriptor
	 */
	private void selectAgent(AgentInfo agent)
	{
		synchronized(this.selected)
		{
			this.selected.add(agent);
			agent.select();
		}
	}

	/**
	 * Adds new agent to display
	 * @param new_agent Agent to be added
	 */
	public void addAgent(AgentInfo new_agent)
	{
		synchronized(this.agents)
		{
			this.agents.add(new_agent);
		}
	}
	
	/**
	 * Removes agent from display
	 * @param agent Agent to be removed
	 */
	public void removeAgent(AgentInfo agent)
	{
		synchronized(this.agents)
		{
			this.agents.remove(agent);
		}
		synchronized(this.selected)
		{
			this.selected.remove(agent);
		}
	}

	/**
	 * Adds water to display
	 * @param newWater Water to be added
	 */
	public void addWater(Resources newWater)
	{
		synchronized(this.water)
		{
			this.water.add(newWater);
		}
	}

	/**
	 * Adds food to display
	 * @param newFood Food to be added
	 */
	public void addFood(Resources newFood)
	{
		synchronized(this.food)
		{	
			this.food.add(newFood);
		}
	}

	/**
	 * Removes water from display
	 * @param water Water to be removed
	 */
	public void removeWater(Resources water)
	{
		synchronized(this.water)
		{
			this.water.remove(water);
		}
		synchronized(this.selected_water)
		{
			this.selected_water.remove(water);
		}
	}

	/**
	 * Removes food from display
	 * @param food Food to be removed
	 */
	public void removeFood(Resources food)
	{
		synchronized(this.food)
		{
			this.food.remove(food);
		}
		synchronized(this.selected_food)
		{
			this.selected_food.remove(food);
		}
	}

	/**
	 * Gets mouse position
	 * @return Position of the mouse
	 */
	public Point getMouse_position()
	{
		return this.mouse_position;
	}
	
	/**
	 * Gets offset X
	 * @return X offset
	 */
	public int getOffsetX()
	{
		return this.offsetX;
	}
	
	/**
	 * Gets offset Y
	 * @return Y offset
	 */
	public int getOffsetY()
	{
		return this.offsetY;
	}

	/**
	 * Increases offset Y by amount i
	 * @param i How much should the offset be increased
	 */
	public void incOffsetY(int i)
	{
		this.setOffsetY(this.offsetY + i);
	}

	/**
	 * Increases offset X by amount i
	 * @param i How much should the offset be increased
	 */
	public void incOffsetX(int i)
	{
		this.setOffsetX(this.offsetX + i);
	}

	/**
	 * Decrements offset by amount i
	 * @param i How much should the offset be decreased
	 */
	public void decOffsetY(int i)
	{
		this.setOffsetY(this.offsetY - i);
	}

	/**
	 * Decrements offset by amount i
	 * @param i How much should the offset be decreased
	 */
	public void decOffsetX(int i)
	{
		this.setOffsetX(this.offsetX - i);
	}

	/**
	 * Gets a list of selected agents
	 * @return Reference to a list of selected agent
	 */
	public final List<AgentInfo> getSelectedAgents()
	{
		return this.selected;
	}
	
	/**
	 * Gets a list of selected water
	 * @return Reference to a list of selected water
	 */
	public final List<Resources> getSelectedWater()
	{
		return this.selected_water;
	}
	
	/**
	 * Gets a list of selected food
	 * @return Reference to a list of selected food
	 */
	public final List<Resources> getSelectedFood()
	{
		return this.selected_food;
	}
	
	/**
	 * Returns minimap reference
	 * @return Pointer to minimap
	 */
	public final PanelMinimap getMinimap()
	{
		return this.minimap;
	}

	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}

	public void componentResized(ComponentEvent e)
	{
		// If necessary recalculates offsets after resizing
		this.setOffsetX(this.offsetX);
		this.setOffsetY(this.offsetY);
		
		
		if (this.getWidth() < Model.getWorld().getWidth())
		{
			this.fog_of_war_width = this.getWidth();
			this.fog_of_war_start_x = 0;
		}
		else
		{
			this.fog_of_war_width = Model.getWorld().getWidth();
			this.fog_of_war_start_x = -this.offsetX;
		}

		if (this.getHeight() < Model.getWorld().getHeight())
		{
			this.fog_of_war_height = this.getHeight();
			this.fog_of_war_start_y = 0;
		}
		else
		{
			this.fog_of_war_height = Model.getWorld().getHeight();
			this.fog_of_war_start_y = -this.offsetY;
		}
	}

	public void componentShown(ComponentEvent e) {}

	/**
	 * Sets offset x to value i, makes sure it never leaves game space
	 * @param i new value of offset x
	 */
	public void setOffsetX(int i)
	{
		// Display is bigger then game size
		if (this.getWidth() >= Model.getWorld().getWidth())
			this.offsetX = -(this.getWidth() - Model.getWorld().getWidth()) / 2;
		
		// Can't go off the map
		else if (i < 0)
			this.offsetX = 0;
		
		// Can't go off the map
		else if (i > Model.getWorld().getWidth() - this.getWidth())
			this.offsetX = Model.getWorld().getWidth() - this.getWidth();
		
		// New value of offset x is acceptable, change it
		else 
			this.offsetX = i;
		
		// Notifies minimap that offset x changed
		this.minimap.offsetXChanged();
		
		if (this.percepts != null)
			this.percepts_paint = new TexturePaint(this.percepts, new Rectangle(-this.offsetX, -this.offsetY, this.percepts.getWidth(), this.fog.getHeight()));
	}

	/**
	 * Sets offset y to value i, makes sure it never leaves game space
	 * @param i New value of offset y
	 */
	public void setOffsetY(int i)
	{
		// Display is bigger then game size
		if (this.getHeight() >= Model.getWorld().getHeight())
			this.offsetY = -(this.getHeight() - Model.getWorld().getHeight()) / 2;
		
		// Can't go off the map
		else if (i < 0)
			this.offsetY = 0;

		// Can't go off the map
		else if (i > Model.getWorld().getHeight() - this.getHeight())
			this.offsetY = Model.getWorld().getHeight() - this.getHeight();
		
		// New value of offster y is acceptable, change it
		else 
			this.offsetY = i;
		
		// Notifies minimap that offset y changed
		this.minimap.offsetYChanged();
		
		if (this.percepts != null)
			this.percepts_paint = new TexturePaint(this.percepts, new Rectangle(-this.offsetX, -this.offsetY, this.percepts.getWidth(), this.fog.getHeight()));
	}

	/**
	 * Gets fog of war texture
	 * @return fog Fog of war texture
	 */
	public BufferedImage getFog()
	{
		return this.fog;
	}

	/**
	 * Sets visible enemies
	 */
	public Set<AgentInfo> getVisibleEnemies()
	{
		return this.visible_agents;
	}

	/**
	 * Gets discovered food
	 * @return Discovered food
	 */
	public Set<Resources> getDiscoveredFood()
	{
		return this.visible_food;
	}

	/**
	 * Gets discovered water
	 * @return Discovered water
	 */
	public Set<Resources> getDiscoveredWater()
	{
		return this.visible_water;
	}

	/**
	 * Sets the attack button
	 */
	public void setAttack()
	{
		if (this.user_panel == null)
			return;
		
		this.user_panel.enableAttack();
		this.user_panel.disableMove();
		this.user_panel.disableGather();
		this.user_panel.disableHold();
		this.dont_draw_rect = true;
		this.attack = true;
		this.gather = false;
		this.move = false;
	}

	/**
	 * Sets the gather button
	 */
	public void setGather()
	{
		if (this.user_panel == null)
			return;
		
		this.user_panel.disableAttack();
		this.user_panel.disableMove();
		this.user_panel.enableGather();
		this.user_panel.disableHold();
		this.dont_draw_rect = true;
		this.attack = false;
		this.gather = true;
		this.move = false;
	}

	/**
	 * Sets the move button
	 */
	public void setMove()
	{
		if (this.user_panel == null)
			return;
		
		this.user_panel.disableAttack();
		this.user_panel.enableMove();
		this.user_panel.disableGather();
		this.user_panel.disableHold();
		this.dont_draw_rect = true;
		this.attack = false;
		this.gather = false;
		this.move = true;
	}

	/**
	 * Toggles the hold button and hold on selected ants
	 */
	public void setHold()
	{
		if (this.user_panel == null)
			return;
		
		this.user_panel.disableAttack();
		this.user_panel.disableMove();
		this.user_panel.disableGather();
		this.user_panel.toogleHold();
		for (AgentInfo agent: this.selected)
			agent.hold();
	}

	/**
	 * Stops selected ants
	 */
	public void stopSelectedAgents()
	{
		if (this.user_panel == null)
			return;
		
		this.user_panel.disableAttack();
		this.user_panel.disableMove();
		this.user_panel.disableGather();
		this.user_panel.disableHold();
		for (AgentInfo agent: this.selected)
			agent.stop();
	}

	/**
	 * Gets user's control panel
	 * @param panel User's control pane
	 */
	public void setUserControlPanel(PanelMapUserControl panel)
	{
		this.user_panel = panel;
	}

	/**
	 * Adds anthill to the display
	 * @param anthill Anthill to be added
	 */
	public void addAnthill(Team anthill)
	{
		synchronized(this.anthills)
		{
			this.anthills.add(anthill);
		}
	}

	public void removeAnthill(Team anthill)
	{
		synchronized(this.anthills)
		{
			this.anthills.remove(anthill);
		}
	}

	public void removeAllAnthills()
	{
		synchronized(this.anthills)
		{
			this.anthills.clear();
		}
	}
}
