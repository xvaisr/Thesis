/**
 * BP, anthill strategy game
 * Creates the game world
 *
 * @Author  xvaisr00 Roman Vais
 * @Date    2015/02/16
 * @File    Enviroment.GameMap.java
 * ------------------------------------------- * 
 * @OriginalAuthor  xsimet00 Vojtech Simetka
 * @OriginalDate    2012/09/25
 * @Version         1
 * @OriginalFile    graphic.World.java
 */

package Enviroment;

import Agents.AgentInfo;
import graphic.DialogMenu;
import graphic.EnumExperiments;
import graphic.EnumTeams;
import graphic.EnumTeams;
import graphic.Obstacles;
import graphic.PanelMap;
import graphic.Team;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Creates the game world
 * @author Vojtech Simetka
 *
 */
public class GameMap
{
	// GameMap parameters
	private int width;
	private int height;
	private ArrayDeque<Resources> food = new ArrayDeque<Resources>();
	private ArrayDeque<Resources> water = new ArrayDeque<Resources>();
	private Team team_a = null;
	private Team team_b = null;
	private Team team_c = null;
	private Team team_d = null;
	Obstacles[][] obstacle_map;
	private List<Obstacles> obstacle_list = new ArrayList<Obstacles>();
	PathMap paths = new PathMap();
	List<Point> points = new ArrayList<Point>();
	private Map<String, Team> agent2team = new HashMap<String,Team>();
	
	private final PanelMap map;
	private String map_source;
	private int current_food;
	private int current_water;
	
	/**
	 * MyWorld constructor
	 */
	public GameMap(PanelMap map)
	{
		this.current_food = 0;
		this.current_water = 0;
		
		this.map = map;
		
		// Creates teams
		team_a = new Team(EnumTeams.a);
		team_b = new Team(EnumTeams.b);
		team_c = new Team(EnumTeams.c);
		team_d = new Team(EnumTeams.d);
		
		this.agent2team.put(team_a.getName(), team_a);
		this.agent2team.put(team_b.getName(), team_b);
		this.agent2team.put(team_c.getName(), team_c);
		this.agent2team.put(team_d.getName(), team_d);
		
		// Creates adequate world
		WorldsDatabase.createWorld(this);
		
		// Calculates all routes from every corner of every obstacle to every corner of every obstacle
		this.connectObstacles();

		if (map != null)
			this.map.initializeFogOfWar(this.height, this.width, this.map_source, this.obstacle_list);
	}

	/**
	 * Gets food pointer
	 * @return pointer to food list
	 */
	public ArrayDeque<Resources> getFood()
	{
		return this.food;
	}

	/**
	 * Gets water pointer
	 * @return pointer to water list
	 */
	public ArrayDeque<Resources> getWater()
	{
		return this.water;
	}

	/**
	 * Uses A* algorithm to connect all obstacle vertexes
	 */
	private void connectObstacles()
	{
		// Connects all vertexes of each obstacle
		for (Obstacles obstacle: this.obstacle_list)
		{
			this.connectObstacleVertexes(obstacle.getPoints());
			Collections.addAll(points, obstacle.getPoints());
		}
		
		// Adds all points that are directly visible
		for (Point point1: points)
		{
			for (Point point2: points)
			{
				if (this.isVisible(point1, point2))
					this.addPairToPath(point1, point2);
			}
		}
		
		ArrayList<Path> paths = new ArrayList<Path>();
		
		// Adds all other points
		for (Point point1: points)
		{
			for (Point point2: points)
			{
				// This path has not been resolved yet, calculate it and add to map
				if (point1 != point2/* &&
					this.paths.get(point1, point2) == null*/)
					this.findPath(point1, point2, paths);
			}
		}
		
		// Adds all found paths, it automatically chooses best ones
		for (Path path: paths)
		{
			this.paths.put(path.getPath().getFirst(), path.getPath().getLast(), path);
		}
	}
	
	/**
	 * Finds best path from point1 to point2. It also adds paths to other points that are on the way and haven't been resolved yet 
	 * @param point1 Start point
	 * @param point2 Target point
	 */
	private void findPath(Point point1, Point point2, ArrayList<Path>path)
	{
		PriorityQueue<Path> open = new PriorityQueue<Path>();
		HashMap<Point, Double> close = new HashMap<Point, Double>();
		open.add(new Path(point1,point1));
		while(!open.isEmpty())
		{
			// Retrieves first element from open (it is guaranteed to have minimal g)
			Path current = open.remove();
			
			// We found solution, add it to map
			if (point1 != current.getPath().getLast())
				path.add(current);
			
			close.put(current.getPath().getLast(), current.getDistance());
			
			for(Point p: points)
			{
				Path nextPath = this.paths.get(current.getPath().getLast(), p);
				
				// There is not any known connection from current ending point to point p
				if (nextPath == null || close.get(nextPath.getPath().getLast()) != null)
					continue;
				
				open.add(new Path(current, nextPath));
			}
		}
	}

	/**
	 * Returns true if point p1 is directly visible from point p2
	 * @param p1 Point p1
	 * @param p2 Point p2
	 * @return True if they are visible from each other
	 */
	private boolean isVisible(Point p1, Point p2)
	{
		// Stores both points
		int y1 = p1.y;
		int y2 = p2.y;
		int x1 = p1.x;
		int x2 = p2.x;

		// There is no point of drawing line with starting point same as ending
		if ( x1 == x2 && y1 == y2 )
			return false;
		
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
				if (this.obstacle_map[y][x] != null)
					return false;
			}
			
			else
			{
				if (this.obstacle_map[x][y] != null)
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
	 * COnnects obstacle's vertexes
	 * @param points Points to be connected
	 */
	private void connectObstacleVertexes(Point[] points)
	{
		// Adds connection between all points within obstacle
		for (int i = 0; i < points.length - 1; i++)
			this.addPairToPath(points[i], points[i+1]);
		
		// Connects last and first point
		this.addPairToPath(points[points.length-1], points[0]);
	}
	
	/**
	 * Adds pair of points that are directly visible into the paths map
	 * @param p1 Point p1
	 * @param p2 Point p2
	 */
	private void addPairToPath(Point p1, Point p2)
	{
		this.paths.put(p1, p2, new Path(p1, p2));
		this.paths.put(p2, p1, new Path(p2, p1));
	}

	/**
	 * Adds obstacle to the world
	 * @param p1 Vertex of the obstacle
	 * @param p2 Vertex of the obstacle
	 * @param p3 Vertex of the obstacle
	 */
	public void addObstacle(Point p1, Point p2, Point p3)
	{
		// Obstacle has zero area, do not add this
		if (p1.x == p2.x && p1.y == p2.y ||
			p2.x == p3.x && p2.y == p3.y ||
			p3.x == p1.x && p3.y == p1.y)
			return;
		
		if (this.obstacle_map == null)
			this.obstacle_map = new Obstacles[this.height][this.width];
		
		Point points[] = this.rotateClockwise(p1,p2,p3);
		
		// Creates new obstacle
		Obstacles obstacle = new Obstacles(points);
		
		// Adds obstacle reference to array for path detection
		this.addObstacleToArray(obstacle);
	}
	
	/**
	 * Adds new obstacle to obstacle map and merges it with others if necessary
	 * @param obstacle Obstacle to be added
	 */
	private void addObstacleToArray(Obstacles obstacle)
	{	
		// Creates set for storing intersecting obstacles
		HashSet<Obstacles> intersecting = new HashSet<Obstacles>();
		
		// Gets area map of the obstacle
		boolean area[][] = obstacle.getArea();
		
		// Detects intersections and adds obstacle to obstacles map
		for (int y = 0; y < obstacle.getMax().y - obstacle.getMin().y; y++)
		{
			for (int x = 0; x < obstacle.getMax().x - obstacle.getMin().x; x++)
			{
				// Obstacle fills cell at xy coordinate
				if (area[y][x])
				{
					// Recalculates internal obstacle's coordinates to model's coordinates
					int i = y + obstacle.getMin().y;
					int j = x + obstacle.getMin().x;
					
					// Cell is free, add reference to this obstacle
					if (this.obstacle_map[i][j] == null)
						this.obstacle_map[i][j] = obstacle;
					
					// Cell is taken by some other obstacle, add it to set for merging
					else
						intersecting.add(this.obstacle_map[i][j]);
				}
			}
		}
		
		// Adds final and merged obstacle to array for painting
		if (intersecting.isEmpty())
			this.obstacle_list.add(obstacle);
		else
		{
			// Detects intersections and adds obstacle to obstacles map
			for (int y = 0; y < obstacle.getMax().y - obstacle.getMin().y+1; y++)
			{
				for (int x = 0; x < obstacle.getMax().x - obstacle.getMin().x + 1; x++)
				{
					// Obstacle fills cell at xy coordinate
					if (area[y][x])
					{
						// Recalculates internal obstacle's coordinates to model's coordinates
						int i = y + obstacle.getMin().y;
						int j = x + obstacle.getMin().x;
						
						// Cell is free, add reference to this obstacle
						if (this.obstacle_map[i][j] == obstacle)
							this.obstacle_map[i][j] = null;
					}
				}
			}
		}
	}
	
	/**
	 * Rotates points into clockwise order and orders them to start with point of lowest y and x
	 * @param p1 Vertex one
	 * @param p2 Vertex two
	 * @param p3 Vertex three
	 * @return Points in correct order
	 */
	private Point[] rotateClockwise(Point p1, Point p2, Point p3)
	{
		Point[] points = new Point[3];
		points[0] = p1;
		points[1] = p2;
		points[2] = p3;
		Point[] result = new Point[3];
		
		int start_point = 0;
		
		// Calculates sums of dx * dy
		int sum = (p1.x - p2.x) * (p1.y - p2.y) +
				  (p2.x - p3.x) * (p2.y - p3.y) +
				  (p3.x - p1.x) * (p3.y - p1.y);
		
		// Determines which point has the lowest y and if necessary x
		for (int i = 1; i < 3; i++)
		{
			if (points[i].y < points[start_point].y ||
				(points[i].y == points[start_point].y &&
				 points[i].x < points[start_point].x))
				start_point = i;
		}
		
		// Increases start point so we don't get out of boundaries 
		start_point += 3;
		
		// Rotates points so that the Point point with lowest y is first in list
		for (int j = 0; j < 3;j++)
		{
			result[j] = points[start_point % 3];
			if (sum < 0)
				start_point ++;
			else
				start_point --;
		}
		
		return result;
	}

	/**
	 * Adds food to world
	 * @param x Coordinate X of new food
	 * @param y Coordinate Y of new food
	 * @param amount Amount of new food
	 */
	public void addFood(int x, int y, int amount)
	{
		// Place is taken by obstacle
		if (this.obstacle_map != null)
		{
			if (this.obstacle_map[y][x] != null)
				return;
		}
		
		if (x >= this.width - Model.getConfiguration().getResourceRadius() ||
			x <= Model.getConfiguration().getResourceRadius() ||
			y >= this.height - Model.getConfiguration().getResourceRadius()||
			y <= Model.getConfiguration().getResourceRadius())
			return;
		
		// Adds food
		synchronized(this.food)
		{
			Resources newFood = new Resources(x, y, amount);
			this.food.add(newFood);
			if (map != null)
				this.map.addFood(newFood);
			this.current_food += amount;
		}
		
		Model.getStatistics().addFood(Model.getAgentsMovenentThread().getGameLength(), amount);
	}

	/**
	 * Adds water to world
	 * @param x Coordinate X of new water
	 * @param y Coordinate Y of new water
	 * @param amount Amount of new water
	 */
	public void addWater(int x, int y, int amount)
	{
		// Place is taken by obstacle
		if (this.obstacle_map != null)
		{
			if (this.obstacle_map[y][x] != null)
				return;
		}
		
		if (x >= this.width - Model.getConfiguration().getResourceRadius() ||
			x <= Model.getConfiguration().getResourceRadius() ||
			y >= this.height - Model.getConfiguration().getResourceRadius()||
			y <= Model.getConfiguration().getResourceRadius())
			return;
		
		// Adds new water
		synchronized(this.water)
		{
			Resources newWater = new Resources(x, y, amount);
			this.water.add(newWater);
			if (map != null)
				this.map.addWater(newWater);
			this.current_water += amount;
		}

		Model.getStatistics().addWater(Model.getAgentsMovenentThread().getGameLength(), amount);
	}
	
	/**
	 * Removes water from the world
	 * @param water Water to be removed
	 */
	private void removeWater(Resources water)
	{
		synchronized(this.water)
		{
			this.water.remove(water);
			if (map != null)
				this.map.removeWater(water);
		}
	}

	/**
	 * Removes food from the world
	 * @param food Food to be removed
	 */
	private void removeFood(Resources food)
	{
		synchronized(this.food)
		{
			this.food.remove(food);
			if (map != null)
				this.map.removeFood(food);
		}
	}
	
	/**
	 * Sets new agents destination
	 * @param x Coordinate x of destination
	 * @param y Coordinate y of destination
	 * @param agent_info Agents information
	 */
	public void move(int x, int y, AgentInfo agent_info)
	{
		if (agent_info != null)
			agent_info.moveTo(x, y);
	}

	/**
	 * Gets model width
	 * @return Width of model
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * Sets model width
	 * @param width Width of model
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * Gets model height
	 * @return Height of model
	 */
	public int getHeight()
	{
		return this.height;
	}

	/**
	 * Sets model height
	 * @param height Height of model
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * Collects water from one location and adds it to agent
	 * @param agent_info Information of agent who is attempting to collect water
	 * @return True if water was successfully collected
	 */
	public boolean collect_water(AgentInfo agent_info)
	{
		int x = agent_info.getIntPositionX();
		int y = agent_info.getIntPositionY();
		
		synchronized (this.water)
		{ 
			for(Resources water: this.water)
			{
				// Water on these coordinates was found
				if (water.getX() == x &&
					water.getY() == y)
				{
					// Agent can collect whole food 
					if (water.getAmout() == 1)
						this.removeWater(water);
					
					// Agent can collect only part of food
					else
						water.setAmount(water.getAmout() - 1);
					
					agent_info.addWater();

					this.current_water -= 1;
					Model.getStatistics().removeWater(Model.getAgentsMovenentThread().getGameLength(), 1);
					
					return true;
				}
			}
		}

		// Water was probably already collected
		return false;
	}

	/**
	 * Agent unloads water it carries
	 * @param agent_info
	 */
	public void unload_water(AgentInfo agent_info)
	{	
		if (agent_info.getWater())
		{
			// Adds water to adequate team
			switch(agent_info.getTeam())
			{
			case a:
				this.team_a.addWater(1);
				break;
			case b:
				this.team_b.addWater(1);
				break;
			case c:
				this.team_c.addWater(1);
				break;
			case d:
				this.team_d.addWater(1);
				break;
			default:
				break;
			}
			
			// Removes water from agent
			agent_info.removeWater();
			
			this.endExperiment();
		}
	}
	
	/**
	 * Ends the resource experiment if necessary
	 */
	private void endExperiment()
	{
		if (Model.getConfiguration().getExperiment() == EnumExperiments.Resources &&
			this.current_food == 0 &&
			this.current_water == 0)
		{
			if (this.areAgentsEmpty(Model.getEnvironment().getActive_agentsA()) &&
				this.areAgentsEmpty(Model.getEnvironment().getActive_agentsB()) &&
				this.areAgentsEmpty(Model.getEnvironment().getActive_agentsC()) &&
				this.areAgentsEmpty(Model.getEnvironment().getActive_agentsD()))
				Model.endExperiment();
		}
	}
	
	/**
	 * Returns true if agents from source are not carrying any resource
	 * @param source Source of agents
	 * @return True if agents from source are not carrying any resource
	 */
	private boolean areAgentsEmpty(ArrayDeque<AgentInfo> source)
	{
		synchronized(source)
		{
			for (AgentInfo agent: source)
			{
				if (!agent.isEmpty())
					return false;
			}
		}
		return true;
	}

	/**
	 * Collects food from one location and adds it to agent
	 * @param agent_info Name of agent who is attempting to collect food
	 * @return True if food was successfully collected
	 */
	public boolean collect_food(AgentInfo agent_info)
	{
		int x = agent_info.getIntPositionX();
		int y = agent_info.getIntPositionY();
		
		synchronized (this.food)
		{ 
			for(Resources food: this.food)
			{
				// Food on these coordinates was found
				if (food.getX() == x &&
					food.getY() == y)
				{
					// Agent can collect whole food 
					if (food.getAmout() == 1)
						this.removeFood(food);
					
					// Agent can collect only part of food
					else
						food.setAmount(food.getAmout() - 1);
					
					// Adds food to agent
					agent_info.addFood();
					
					// Adds information to statistics about food change
					this.current_food -= 1;
					Model.getStatistics().removeFood(Model.getAgentsMovenentThread().getGameLength(), 1);
						
					return true;
				}
			}
		}

		// Food was probably already collected
		return false;
	}

	/**
	 * Agent unloads food it carries
	 * @param agent_info
	 */
	public void unload_food(AgentInfo agent_info)
	{	
		if (agent_info.getFood())
		{
			// Adds water to adequate team
			switch(agent_info.getTeam())
			{
			case a:
				this.team_a.addFood(1);
				break;
			case b:
				this.team_b.addFood(1);
				break;
			case c:
				this.team_c.addFood(1);
				break;
			case d:
				this.team_d.addFood(1);
				break;
			default:
				break;
			}
			
			// Removes food from agent
			agent_info.removeFood();

			this.endExperiment();
		}
	}

	/**
	 * Executes one move for all agents in the world
	 */
	public void doOneStepForAllAgents()
	{
		if (Model.isGame_running())
		{
			// Moves all agents od team c
			synchronized(Model.getEnvironment().getActive_agentsA())
			{
				for (AgentInfo agent: Model.getEnvironment().getActive_agentsA())
					agent.doStep();
			}
			
			// Moves all agents of team B
			synchronized(Model.getEnvironment().getActive_agentsB())
			{
				for (AgentInfo agent: Model.getEnvironment().getActive_agentsB())
					agent.doStep();
			}
			
			// Moves all agents of team C
			synchronized(Model.getEnvironment().getActive_agentsC())
			{
				for (AgentInfo agent: Model.getEnvironment().getActive_agentsC())
					agent.doStep();
			}
			
			// Moves all agents of team D
			synchronized(Model.getEnvironment().getActive_agentsD())
			{
				for (AgentInfo agent: Model.getEnvironment().getActive_agentsD())
					agent.doStep();
			}
		}
		
		//Tests if the time experiment already ended if it is being rnnes
		if (Model.getConfiguration().getExperiment() == EnumExperiments.TimeComplexity)
		{
			if (Model.getAgentsMovenentThread().getGameLength() >= Model.getConfiguration().getExperimentGameLenght())
				Model.endExperiment();
		}
		
		// Tests if the game didnt ended yet and if so tests if the user was already notified
		else if (Model.isGameEnd() && !Model.isUserNotified())
		{
			if (Model.getConfiguration().getExperiment() != EnumExperiments.None)
				Model.endExperiment();
			
			else
			{
				Model.setUserNotified();
				
	        	if (Model.isGame_running())
			    	new DialogMenu();
			}
		}
	}

	/**
	 * Gets anthill of selected team
	 * @param team Team which anthill we want
	 * @return Coordinates of anthill for team
	 */
	public Point getAnthill(EnumTeams team)
	{	
		return this.getTeam(team).getAnthill();
	}

	/**
	 * Attacks agent
	 * @param defender Agent who is attacked
	 * @param attacker Agent who attacks
	 */
	public void attack(AgentInfo defender, AgentInfo attacker)
	{
		attacker.setEnemyToAttack(defender);
	}

	/**
	 * Gets adequate team
	 * @param team Team enumerator to be retrieved
	 * @return Team descriptor
	 */
	public Team getTeam(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.team_a;
		case b:
			return this.team_b;
		case c:
			return this.team_c;
		case d:
			return this.team_d;
		default:
			return null;
		}
	}

	/**
	 * Stops attack of the agent
	 * @param agent Agent who wants to stop attacking
	 */
	public void stopAttack(AgentInfo agent)
	{
		if (agent == null)
			return;
		agent.setEnemyToAttack(null);
	}
	
	/**
	 * Gets list of obstacles
	 * @return List of obstacles
	 */
	public List<Obstacles> getObstacles()
	{
		return this.obstacle_list;
	}
	
	/**
	 * Determines whether location with coordinates x y is free of obstacles
	 * @param x Coordinate x
	 * @param y Coordinate y
	 * @return True if location is free of obstacles
	 */
	public boolean isFree(int x, int y)
	{
		if (this.obstacle_map == null)
			return true;
		return this.obstacle_map[y][x] == null;
	}

	/**
	 * Adds map texture source path
	 * @param string Map texture source path
	 */
	public void addMap(String string)
	{
		this.map_source = string;
	}

	/**
	 * Gets team with a name matching argument
	 * @param name Name of the team to be obtained
	 * @return Team with a name matching argument
	 */
	public Team getTeam(String name)
	{
		return this.agent2team.get(name);
	}

	/**
	 * Gets current amount of water in the environment
	 * @return Current amount of water in the environment
	 */
	public int getCurrentFoodCount()
	{
		return this.current_food;
	}

	/**
	 * Gets current amount of food in the environment
	 * @return Current amount of food in the environment
	 */
	public int getCurrentWaterCount()
	{
		return this.current_water;
	}

	/**
	 * Adds new anthill position
	 * @param team Team of the anthills position
	 * @param x Coordinate x
	 * @param y Coordinate y
	 */
	public void addAnthill(EnumTeams team, int x, int y)
	{
		Team anthill = this.getTeam(team);
		
		anthill.setAnthill(x, y);
		
		this.map.addAnthill(anthill);
	}
}
