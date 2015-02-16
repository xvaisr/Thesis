/**
 * BP, anthill strategy game
 * Extension of jason.environment.Environment
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/25
 * @version 1
 * @file    graphic.Environment.java
 */
package Agents;

import Enviroment.Path;
import Enviroment.Model;
import graphic.EnumAlliances;
import graphic.EnumTeams;
import graphic.EnumTeams;
import graphic.PanelMap;
import graphic.Team;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.lang.Math;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Contains environmental information about agents
 * @author Vojtech Simetka
 *
 */
public class AgentInfo {
	private final EnumTeams team;
	private final EnumAlliances alliance;
	private double x;
	private double y;
	private int int_x;
	private int int_y;
	private ArrayDeque<Point> path = new ArrayDeque<Point>();
	private Point destination = new Point();
	private double dx = 0;
	private double dy = 0;
	private String name;
	
	private boolean food = false;
	private boolean water = false;
	private int hp = 100;
	private int armor = 0;
	private int attack = 10;
	private boolean selected = false;
	
	private final int tolerance = 3;
	private int sleep_for = 0;
	private final int sleep_resource = 50;
	private final int sleep_attack = 100;
	private final int replenish_duration = 500;
	private final Color color;
	
	private int replenish_hp = 0;
	private AgentInfo enemy = null;
	
	private final Team team_ptr;
	
	private final Random rand = new Random();
	
	private final boolean user_controlled;
	
	private Point collect;
	
	private boolean user_action = false;
	private PanelMap map;
	private boolean killed;
	private boolean hold;
	private boolean attack_move;
	private int kills = 0;
	
	/**
	 * Agent info constructor for non user controlled ants 
	 * @param name Agents name
	 * @param team Agents team
	 */
	AgentInfo(String name, EnumTeams team)
	{
		this.name = name;
		this.team = team;
		
		this.color = Model.getConfiguration().getColor(team);

		this.setPosition(Model.getWorld().getAnthill(team).x,Model.getWorld().getAnthill(team).y);
		this.setDestination(Model.getWorld().getAnthill(team).x,Model.getWorld().getAnthill(team).y);
		this.alliance = Model.getConfiguration().getAlliance(team);
		this.team_ptr = Model.getWorld().getTeam(team);
		
		this.user_controlled = false;
	}

	/**
	 * Agentinfor constructor for user controlled ants
	 * @param name Agents name
	 * @param team Agents team
	 * @param map Map reference
	 */
	public AgentInfo(String name, EnumTeams team, PanelMap map)
	{
		this.name = name;
		this.team = team;
		
		this.color = Model.getConfiguration().getColor(team);

		this.setPosition(Model.getWorld().getAnthill(team).x,Model.getWorld().getAnthill(team).y);
		this.setDestination(Model.getWorld().getAnthill(team).x,Model.getWorld().getAnthill(team).y);
		this.alliance = Model.getConfiguration().getAlliance(team);
		this.team_ptr = Model.getWorld().getTeam(team);
		
		this.user_controlled = true;
		this.map = map;
	}

	/**
	 * Sets agent as selected
	 */
	public void select()
	{
		this.selected = true;
	}
	
	/**
	 * Deselects agent
	 */
	public void deselect()
	{
		this.selected = false;
	}
	
	/**
	 * Does a one step of agent current action
	 */
	public synchronized void doStep()
	{	
		// Regenerates HP
		if (this.hp < 100)
		{
			if (this.replenish_hp == 0)
			{
				this.hp ++;
				this.replenish_hp = this.replenish_duration;
			}
			this.replenish_hp--;
		}
		
		// If agent sleeps after some action, skip turn
		if (this.sleep_for > 0)
		{
			this.sleep_for--;
			return;
		}
		
		// If agent is attacking enemy attack
		if (this.doAttack())
			return;
		
		// Agent is set to hold, don't move
		else if (this.hold)
			return;
		
		// Agent did not attacked, move one step
		else
			this.doOneMove();
	}
	
	/**
	 * Attempt's to attack enemy agent
	 */
	private boolean doAttack()
	{	
		AgentInfo enemy = this.enemy;
		
		// There is nobody to attack
		if (enemy == null)
			return false;
		
		// Agents are in me-lee range
		if (this.calculateDistance(this.x, this.y, enemy.x, enemy.y) <= 2 * Model.getConfiguration().getAgentRadius())
		{
			// Decreases enemie's HP
			enemy.hp -= (this.getAttack() + this.rand.nextInt(this.getAttack())/5 - enemy.getArmor());
			
			// Enemy was killed
			if (enemy.hp <= 0)
			{
				enemy.kill(this);
				this.enemy = null;
			}
			
			// Sleeps after attack
			this.sleep_for = this.sleep_attack;
			
			return true;
		}
	
		// Hold is set, do not move
		else if (this.hold)
			return true;
		
		// Enemy is within perception range, move towards him
		else if (Model.getEnvironment().isWithinPerceptionRange(this.int_x, this.int_y, enemy.int_x, enemy.int_y))
			this.moveTo(enemy.getIntPositionX(), enemy.getIntPositionY());
		
		// User controls the ant, do further actions
		else if (this.user_controlled)
		{
			// Enemy is not visible for me but somebody else sees him, attack
			if (this.map.getVisibleEnemies().contains(enemy))
				this.moveTo(enemy.getIntPositionX(), enemy.getIntPositionY());
			
			// Just move to last known position of enemy
			else
			{
				this.moveTowards(enemy.int_x, enemy.int_y);
				
				// Remove enemy
				this.enemy = null;
			}
		}
		
		// Enemy ran away
		else
			this.enemy = null;
		
		// Did not attacked enemy, do movement action
		return false;
	}

	/**
	 * Moves a step closer towards destination
	 */
	private void doOneMove()
	{
		synchronized(this)
		{
			// Agent already is near the destination
			if (is_nearby())
			{
				this.setPosition(this.getDestinationX(), this.getDestinationY());
				
				// Get new destination from path, if there is any
				this.calulateDifferencials();
			}
			
			// Moves one step toward destination
			else
				this.setPosition(this.getDifferenceX() + this.getPositionX(),
								 this.getDifferenceY() + this.getPositionY());
			
			// If the unit is controlled by user and it is either at resource location or at home, collect resource
			if (this.user_controlled &&
				this.path.isEmpty() &&
				this.collect != null)
				this.doUserCollectAction();
		}
	}

	/**
	 * This handless automated resource collection for the user
	 */
	private void doUserCollectAction()
	{
		// Ant is already carrying resource
		if (this.food || this.water)
		{
			// Ant is at anthill
			if (this.int_x == this.team_ptr.getAnthill().x &&
				this.int_y == this.team_ptr.getAnthill().y)
			{
				// Unload resource
				Model.getWorld().unload_food(this);
				Model.getWorld().unload_water(this);

				// Return to resource location
				this.moveTo(this.collect.x, this.collect.y);
			}
			
			// Resources are collected, but ant is not at the anthill, set anthill as a destination
			else
				this.moveTo(this.team_ptr.getAnthill().x, this.team_ptr.getAnthill().y);
		}
		
		// Ant is going toward resources or is at resource location and wishes to collect them 
		else
		{
			// Ant is at resource location
			if (collect.x == this.int_x &&
				collect.y == this.int_y)
			{
				// If it is food, collect it
				if (Model.getWorld().collect_food(this))
					return;
				
				// If it is water collect it
				else if (Model.getWorld().collect_water(this))
					return;
				
				// No food or water at location, stop actions
				else
					this.collect = null;
			}
			
			// Ant needs to move towards resources
			else
				this.moveTo(this.collect.x, this.collect.y);
		}
	}

	/**
	 * Returns true if agent reached destination or is nearby, takes into account truncation error
	 * @return True if agent reached or is near destination
	 */
	private boolean is_nearby()
	{
		return ((int)this.getDestinationX() - this.tolerance < this.getPositionX() &&
				(int)this.getDestinationX() + this.tolerance > this.getPositionX() &&
				(int)this.getDestinationY() - this.tolerance < this.getPositionY() &&
				(int)this.getDestinationY() + this.tolerance > this.getPositionY() ||
				(this.getDifferenceX() == 0 && this.getDifferenceY() == 0));
	}
	
	/**
	 * Sets agents position to x and y
	 * @param d new position coordinate x
	 * @param e new position coordinate y
	 */
	private void setPosition(double d, double e)
	{
		this.x = d;
		this.y = e;
		this.int_x = (int)d;
		this.int_y = (int)e;
	}

	/**
	 * Sets agent destination to x and y
	 * @param x new destination coordinate x
	 * @param y new destination coordinate y
	 */
	private void setDestination(int x, int y)
	{
		this.destination.x = x;
		this.destination.y = y;
	}

	/**
	 * Calculates route towards coordinates x and y
	 * @param x location x coordinate
	 * @param y location y coordinate
	 */
	public void moveTo(int x, int y)
	{
		// No need to calculate route, it remains same
		synchronized(this)
		{
			if (!this.path.isEmpty() &&
				x == this.path.getLast().x &&
				y == this.path.getLast().y)
				return;
		}
		
		// Destination is off map, normalizes it so it is inside map
		if (x > Model.getWorld().getWidth()-1)
			x = Model.getWorld().getWidth()-1;
		else if (x < 0)
			x = 0;
		
		if (y > Model.getWorld().getHeight()-1)
			y = Model.getWorld().getHeight()-1;
		else if (y < 0)
			y = 0;
		
		// Calculates path toward point x y
		this.addPathTo(x,y);
		
		// Calculates dx and dy for first point in path
		this.calulateDifferencials(); 
		
		// Saves EAPM
		Model.getStatistics().addAction(this.team);
	}
	
	/**
	 * Constructs path to location with coordinates x and y evading all obstacles
	 * @param x Coordinate x
	 * @param y Coordinate y
	 */
	private void addPathTo(int x, int y)
	{
		// If destination lies in obstacle get first empty point
		if (!Model.getWorld().isFree(x, y))
		{
			Point p = this.getFirstEmptyPoint(x,y,this.int_x, this.int_y);
			x = p.x;
			y = p.y;
		}	

		// If there is no obstacle in the way, move directly to the destination
		if (this.isVisible(this.getIntPositionX(), this.getIntPositionY(), x, y))
		{
			synchronized(this)
			{
				this.path.clear();
				this.path.push(new Point(x,y));
			}
		}
		
		// Otherwise get best path around obstacles
		else
		{
			ArrayDeque<Point> new_path = this.calculateBestPathAroundObstacle(x,y);
			
			synchronized(this)
			{
				this.path = new_path;
			}
		}
	}

	/**
	 * Calculates and returns best possible path from ant's position to the point with coordinates x and y
	 * @param x Coordinate x
	 * @param y Coordinate y
	 * @return Best found path from ant's position to the point with coordinates x and y
	 */
	private ArrayDeque<Point> calculateBestPathAroundObstacle(int x, int y)
	{
		// Gets all obstacle vertexes
		List<Point> to_check = Model.getWorld().points;
		
		HashSet<Point> position_points= new HashSet<Point>();
		HashSet<Point> destination_points= new HashSet<Point>();
		
		// Gets all points on the obstacle that are visible from agent's position
		for (Point p: to_check)
		{
			if (this.isVisible(p.x, p.y, this.getIntPositionX(), this.getIntPositionY()))
				position_points.add(p);
		}
		// Gets all points on the obstacle that are visible from agent's destination
		for (Point p: to_check)
		{
			if (this.isVisible(p.x, p.y, x, y))
				destination_points.add(p);
		}
		
		// Gets best path
		return this.getShortestPathBetweenTwoPoints(position_points, destination_points, x, y);
	}
	
	/**
	 * Gets shortest path from ant position to destination through points directly visible from agent's position and new destination
	 * @param position_points Points directly visible from agent's position
	 * @param destination_points Points directly visible from agent's destination
	 * @return Shortest path from agent's position to agent's destination
	 */
	private ArrayDeque<Point> getShortestPathBetweenTwoPoints(HashSet<Point> position_points, HashSet<Point> destination_points, int x, int y)
	{
		ArrayDeque<Point> best_path = new ArrayDeque<Point>();
		double distance = Double.MAX_VALUE;
		
		// For all visible points from position and destination
		for (Point pos_p: position_points)
		{
			for (Point des_p: destination_points)
			{
				// The point is directly visible from agent's destination and position
				if (pos_p == des_p)
				{
					// Calculates distance
					double temp = this.calculateDistance(this.x, this.y, pos_p.x, pos_p.y) + 
								  this.calculateDistance(pos_p.x, pos_p.y, x, y);
					
					// New path is shorter then last one found
					if (temp < distance)
					{
						best_path.clear();
						best_path.add(pos_p);
						distance = temp;
					}
				}
				
				else
				{
					// Gets path from point directly visible from agent's position to the point directly visible from agent's destination
					Path candidate = Model.getWorld().paths.get(des_p, pos_p);
					
					// Calculates distance
					double temp = candidate.getDistance() +
							this.calculateDistance(this.x, this.y, candidate.getPath().peekLast().x, candidate.getPath().peekLast().y) +
							this.calculateDistance(candidate.getPath().peekFirst().x, candidate.getPath().peekFirst().y, x, y);
					
					// New path is shorter then last one found
					if (temp < distance)
					{
						best_path = candidate.getPath();
						distance = temp;
					}
				}
			}
		}
		
		// Adds final point to the best path
		best_path.push(new Point(x,y));
		
		// Returns best found path
		return best_path;
	}

	/**
	 * Gets first free of obstacle point from the point 2 to the point 1
	 * @param x1 First point's coordinate x
	 * @param y1 First point's coordinate y
	 * @param x2 Second point's coordinate x
	 * @param y2 Second point's coordinate y
	 * @return First free of obstacle point from the point 2 to the point 1
	 */
	private Point getFirstEmptyPoint(int x1, int y1, int x2, int y2)
	{
		// Calculates coordinate difference between point 1 and point 2
		double dx = x2 - x1;
		double dy = y2 - y1;
		
		// Destination is current position
		if (dx == 0 && dy == 0)
			return new Point(x2, y2);
		
		// Pythagorean theorem
		double c = Math.sqrt(dx * dx + dy * dy);
		
		// Calculates differencials
		dx = dx/c;
		dy = dy/c;
		
		double tx = x1;
		double ty = y1;
		
		// Casts ray from point 1 to point 2 until a free of obstacles point is found
		while (!Model.getWorld().isFree((int)tx, (int)ty) &&
			   !(ty +1 > y2 && ty -1 < y2))
		{
			tx += dx;
			ty += dy;
		}
		
		// Found point is free of obstacles
		if (Model.getWorld().isFree((int)tx, (int)ty))
			return new Point((int)(tx), (int)(ty));
		
		// No free of obstacles point was found, return point 2
		else
			return new Point(x2, y2);
	}

	/**
	 * Calculates distance between two points
	 * @param x1 Point 1 coordinate x
	 * @param y1 Point 1 coordinate y
	 * @param x2 Point 2 coordinate x
	 * @param y2 Point 2 coordinate y
	 * @return Air distance between these points
	 */
	private double calculateDistance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	/**
	 * Calculates differentials for next point in path and removes it from path
	 */
	private void calulateDifferencials()
	{
		Point next_point;

		synchronized(this)
		{
			// The agent has reached destination
			if (this.path.isEmpty())
			{
				setDifferencials(0,0);
				
				// Only when user is playing, removes user action if resource to collect was set because the ant reached its destination = finished moving
				if (this.collect == null)
					this.user_action = false;
				
				return;
			}
			// Remove first point from path
			else
				next_point = this.path.removeLast();
		}
		
		// The new point is current position
		if (next_point.x == this.getIntPositionX() &&
			next_point.y == this.getIntPositionY())
		{
			// Checks next point and returns
			this.calulateDifferencials();
			return;
		}
		
		// Sets new destination
		this.setDestination(next_point.x, next_point.y);
		
		// Calculates difference between current position and destination
		double dx = this.getDestinationX() - this.getPositionX();
		double dy = this.getDestinationY() - this.getPositionY();
		
		// Pythagorean theorem
		double c = Math.sqrt(dx * dx + dy * dy);
		
		// Moves toward target at maximal speed
		this.setDifferencials(dx/c*this.team_ptr.getSpeed(),
							  dy/c*this.team_ptr.getSpeed());
	}
	
	/**
	 * Gets agent's integer position coordinate x
	 * @return Agents integer position coordinate x
	 */
	public final int getIntPositionX()
	{
		return this.int_x;
	}

	/**
	 * Gets agent's integer position coordinate y
	 * @return Agents integer position coordinate y
	 */
	public final int getIntPositionY()
	{
		return this.int_y;
	}
	
	/**
	 * Gets agent's position coordinate x in double
	 * @return Agent's position coordinate x in double
	 */
	private final double getPositionX()
	{
		return this.x;
	}

	/**
	 * Gets agent's position coordinate y in double
	 * @return Agent's position coordinate y in double
	 */
	private final double getPositionY()
	{
		return this.y;
	}
	
	/**
	 * Gets agent's destination coordinate x
	 * @return Agents destination coordinate x
	 */
	public final int getDestinationX()
	{
		return this.destination.x;
	}

	/**
	 * Gets agent's destination coordinate y
	 * @return Agents destination coordinate y
	 */
	public final int getDestinationY()
	{
		return this.destination.y;
	}
	

	/**
	 * Gets length of step in x direction
	 * @return Agents length of step in x direction 
	 */
	private final double getDifferenceX()
	{
		return this.dx;
	}
	
	/**
	 * Gets length of step in y direction
	 * @return Agents length of step in y direction
	 */
	private final double getDifferenceY()
	{
		return this.dy;
	}
	
	/**
	 * Sets differentials to new values
	 * @param dx New value of dx
	 * @param dy New value of dy
	 */
	private void setDifferencials(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Gets agent's team enumerator
	 * @return Agent's team enumerator
	 */
	public EnumTeams getTeam()
	{
		return this.team;
	}

	/**
	 * Return true if agent is selected
	 * @return True if agent is selected
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * Returns true if agent carries water
	 * @return True if agent carries water
	 */
	public boolean getWater() 
	{
		return this.water;
	}

	/**
	 * Notes that agent carries water and sets it to sleep for a while
	 */
	public void addWater()
	{
		this.water = true;
		this.sleep_for = this.sleep_resource;
	}

	/**
	 * Returns true if agent carries water
	 * @return True if agent carries water
	 */
	public boolean getFood()
	{
		return this.food;
	}

	/**
	 * Notes that agent carries food and sets it to sleep for a while
	 */
	public void addFood()
	{
		this.food = true;
		this.sleep_for = this.sleep_resource;
	}

	/**
	 * Removes water of the agent and sets it to sleep for a while
	 */
	public void removeWater()
	{
		this.water = false;
		this.sleep_for = this.sleep_resource;
	}
	
	/**
	 * Removes food of the agent and sets it to sleep for a while
	 */
	public void removeFood()
	{
		this.food = false;
		this.sleep_for = this.sleep_resource;
	}

	/**
	 * Gets agent's name
	 * @return Name of the agent
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Gets agent's HP
	 * @return Agent's HP
	 */
	public int getHp()
	{
		return this.hp;
	}

	/**
	 * Gets agent's alliance 
	 * @return Agent's alliance
	 */
	public EnumAlliances getAlliance()
	{
		return this.alliance;
	}

	/**
	 * Gets attack of the agent
	 * @return Agent's attack
	 */
	public int getAttack()
	{
		return this.attack + this.team_ptr.getAttack();
	}

	/**
	 * Sets agent's attack
	 * @param attack New agent attack
	 */
	public void setAttack(int attack)
	{
		this.attack = attack;
	}

	/**
	 * Gets agent's armor
	 * @return Agent's armor
	 */
	public int getArmor()
	{
		return this.armor + this.team_ptr.getArmor();
	}

	/**
	 * Sets agent's armor
	 * @param armor New agent's armor
	 */
	public void setArmor(int armor)
	{
		this.armor = armor;
	}
	
	/**
	 * Sets enemy to be attacked
	 * @param defender Attacked agent
	 */
	public void setEnemyToAttack(AgentInfo defender)
	{
		this.enemy = defender;
		Model.getStatistics().addAction(this.team);
	}

	/**
	 * Agent finished doing all actions and is free to do some other
	 * @return True if agent has nothing to do
	 */
	public boolean finished()
	{
		if (this.enemy == null &&
			!this.isUserAction() &&
			this.is_nearby())
			return true;
		
		return false;
	}
	
	/**
	 * Return true if there is no obstacle in way from x1 y1 to x2 y2
	 * Based on Bresenham's line algorithm
	 * @param x1 Start point's x coordinate
	 * @param y1 Start point's y coordinate
	 * @param x2 Target point's x coordinate
	 * @param y2 Target point's y coordinate
	 * @return True if path is clear
	 */
	private boolean isVisible(int x1, int y1, int x2 , int y2)
	{
		if (Model.getWorld().obstacle_map == null)
			return true;
		
		// Calculates delta x and y
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		
		// Swap of x and y, slope of the line is greater then 1
		boolean xy_swap = (dy > dx);
		if (xy_swap)
		{
			// Swaps x1 with y1
			int tmp = x1;
			x1 = y1;
			y1 = tmp;
			
			// Swaps x2 with y2
			tmp = x2;
			x2 = y2;
			y2 = tmp;
			
			// Swaps dx with dy
			tmp = dx;
			dx = dy;
			dy = tmp;
		}
		
		// Starting point's x should be greater then ending point's, swaps P1 for P2
		if (x1 > x2)
		{
			// Swap x1 with x2
			int tmp = x1;
			x1 = x2;
			x2 = tmp;
			
			// Swap y1 with y2
			tmp = y1;
			y1 = y2;
			y2 = tmp;
		}
		
		// Direction on y axis
		int step_y;
		if (y1 > y2)
			step_y = -1;
		else
			step_y = 1;
		
		// Constants for Bresenham algorithm
		int k1 = 2 * dy;
		int k2 = 2 * (dy - dx);
		int p  = 2 * dy - dx;
		
		// Initialization of x and y
		int x = x1;
		int y = y1;
		
		// Rendering cycle
		while (x <= x2)
		{
			if (!xy_swap)
			{
				if (Model.getWorld().obstacle_map[y][x] != null)
					return false;
			}
			
			else
			{
				if (Model.getWorld().obstacle_map[x][y] != null)
					return false;
			}
			
			x++;
			if (p < 0)
				p += k1;
			
			else
			{
				p += k2;
				y += step_y;
			}
		}
		return true;
	}
	
	/**
	 * Gets path of the agent
	 * @return Returns path of the agent
	 */
	public ArrayDeque<Point> getPath()
	{
		return this.path;
	}
	
	/**
	 * Gets color of the agent
	 * @return Agent's color
	 */
	public final Color getColor()
	{
		return this.color;
	}

	/**
	 * Gets agent's speed
	 * @return Agent's speed
	 */
	public float getSpeed()
	{
		return this.team_ptr.getSpeed();
	}

	/**
	 * Returns true if agent is controlled by the user
	 * @return True if agent is controlled by the user
	 */
	public boolean isUserControlled()
	{
		return this.user_controlled;
	}

	/**
	 * Sets enemy to be attacked - user action
	 * @param enemy Enemy to be attacked
	 */
	public void attack(AgentInfo enemy)
	{
		this.hold = false;
		this.enemy = enemy;
		this.collect = null;
		this.user_action = true;
		
		// Notes environment action
		Model.getStatistics().addAction(this.team);
	}

	/**
	 * Sets position of resources where to go - user action
	 * @param collect Location with resources to collect
	 */
	public void collect(Point collect)
	{
		this.enemy = null;
		
		if (!(food || water))
			this.moveTo(collect.x, collect.y);
		
		this.collect = collect;
		this.user_action = true;
		
		// Notes environment action
		Model.getStatistics().addAction(this.team);
	}
	
	/**
	 * Sets location where the agent should move to - user action
	 * @param x Coordinate x
	 * @param y Coordinate y
	 */
	public void moveTowards(int x, int y)
	{
		this.hold = false;
		this.enemy = null;
		this.collect = null;
		this.attack_move = false;
		this.moveTo(x, y);
		this.user_action = true;
	}
	
	/**
	 * Kill this agent 
	 * @param agentInfo Who killed the agent
	 */
	public synchronized void kill(AgentInfo agentInfo)
	{
		if (!killed)
		{
			// Adds kill to the killer
			agentInfo.addKill();
			
			this.killed = true;
			
			// Kills the agent
			Model.getEnvironment().killAgent(this);
		}
	}

	/**
	 * Adds kill
	 */
	private void addKill()
	{
		this.kills ++;
	}

	/**
	 * Orders the ant to stop - user action
	 */
	public void stop()
	{
		this.hold = false;
		this.enemy = null;
		this.collect = null;
		this.erasePath();
		this.user_action = false;
	}
	
	/**
	 * Clears path and set's destination to agent's position
	 */
	private void erasePath()
	{
		synchronized(this)
		{
			this.setDestination(this.int_x, this.int_y);
			this.path.clear();
		}
	}

	/**
	 * Toggles hold on the agent - user action
	 */
	public void hold()
	{
		this.hold = !this.hold;
		this.user_action = this.hold;
	}

	/**
	 * Moves towards location with coordinate x and y an attack anything in the way - user action
	 * @param x Coordinate x
	 * @param y Coordinate y
	 */
	public void moveWithAttack(int x, int y)
	{
		this.moveTowards(x, y);
		this.attack_move = true;
		this.user_action = true;
	}

	/**
	 * If agent does not have any other action, attacks enemy
	 * @param enemy Enemy to attack
	 */
	public void autoAttack(AgentInfo enemy)
	{
		// If agent does not have any action to do, attack enemy
		if (this.finished() || this.attack_move)
			this.attack(enemy);
	}

	/**
	 * Returns true if the agent has a hold toggled
	 * @return True if the agent has a hold toggled
	 */
	public boolean isHolding()
	{
		return this.hold;
	}
	
	/**
	 * Returns true if the agent is moving
	 * @return True if the agent is moving
	 */
	public boolean isMoving()
	{
		return !this.path.isEmpty() || !(this.destination.x == this.int_x && this.destination.y == this.int_y);
	}

	/**
	 * Returns true if the agent is gathering resources
	 * @return True if agent is gathering resources
	 */
	public boolean isGathering()
	{
		return this.collect != null;
	}
	
	/**
	 * Returns true if agent is attacking
	 * @return True if agent is attacking
	 */
	public boolean isAttacking()
	{
		return this.enemy != null;
	}
	
	/**
	 * Returns true if agent does not carry any resources
	 * @return True if agent does not carry any resources
	 */
	public boolean isEmpty()
	{
		return !this.food && !this.water;
	}
	
	/**
	 * Returns true if the agent has action to do from the user
	 * @return True if the agent has action to do from the user
	 */
	public boolean isUserAction()
	{
		return this.user_action;
	}
	
	/**
	 * Returns how many enemies the agent killed
	 * @return Returns number of kills for the agent
	 */
	public int getKills()
	{
		return this.kills;
	}
}
