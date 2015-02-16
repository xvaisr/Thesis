/**
 * BP, anthill strategy game
 * Thread responsible for updating what game elements are visible by user
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/11/20
 * @version 1
 * @file    graphic.ThreadUserGameElementsUpdater.java
 */
package graphic;

import Agents.AgentInfo;
import Enviroment.Model;
import Enviroment.Resources;
import jason.asSemantics.Agent;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Set;

/**
 * Thread responsible for updating what game elements are visible by user
 * @author Vojtech Simetka
 *
 */
public class ThreadUserGameElementsUpdater extends Thread
{
	private volatile boolean threadSuspended = false;
	private volatile boolean delete = false;
	private int sleep_time = 1000;
	private final Set<AgentInfo> visible_enemies;
	private final Set<Resources> discovered_food;
	private final Set<Resources> discovered_water;
	private final PanelMap map;
	private final ArrayDeque<Resources>to_be_removed = new ArrayDeque<Resources>();
	private boolean auto_attack = false;
	
	/**
	 * Constructor
	 * @param map Reference to the map
	 */
	public ThreadUserGameElementsUpdater(PanelMap map)
	{
		this.sleep_time = Model.getConfiguration().getPps();
		this.visible_enemies = map.getVisibleEnemies();
		this.discovered_food = map.getDiscoveredFood();
		this.discovered_water = map.getDiscoveredWater();
		this.map = map;
		this.auto_attack = Model.getConfiguration().getAI(Model.getUserTeam().getTeam()) == AIDatabase.EnumAI.user;
	}
	
	/**
	 * Overrides run method
	 */
	@Override
	public void run()
	{	
		// While thread is not cancelled
		while (!delete)
		{
			synchronized(this.visible_enemies)
			{
				this.visible_enemies.clear();
				
				synchronized(this.map.agents)
				{
					for (AgentInfo agent: this.map.agents)
					{
						this.addAgentsFromTeam(Model.getEnvironment().getActive_agentsA(), agent);
						this.addAgentsFromTeam(Model.getEnvironment().getActive_agentsB(), agent);
						this.addAgentsFromTeam(Model.getEnvironment().getActive_agentsC(), agent);
						this.addAgentsFromTeam(Model.getEnvironment().getActive_agentsD(), agent);
					}
				}
			}
			
			synchronized(this.map.agents)
			{
				synchronized (this.discovered_food)
				{
					for (Resources res: this.discovered_food)
						res.indirectlyVisible();
				}
				
				synchronized (this.discovered_water)
				{
					for (Resources res: this.discovered_water)
						res.indirectlyVisible();
				}
				
				for(AgentInfo agent: this.map.agents)
				{
					this.addResource(map.food, agent, this.discovered_food);
					
					this.addResource(map.water, agent, this.discovered_water);
				}
			}
			
			synchronized(this.discovered_food)
			{
				this.to_be_removed.clear();
				
				for(Resources food: this.discovered_food)
				{
					synchronized(this.map.agents)
					{
						for(AgentInfo agent: this.map.agents)
						{
							if (Model.getEnvironment().isWithinPerceptionRange(agent.getIntPositionX(), agent.getIntPositionY(), food.getX(), food.getY()))
							{
								synchronized(map.food)
								{
									boolean found = false;
									for(Resources fmap: map.food)
									{
										if (fmap.getX() == food.getX() &&
											fmap.getY() == food.getY())
										{
											found = true;
											break;
										}
									}
									
									if (!found)
										this.to_be_removed.add(food);
								}
							}
						}
					}
				}

				for(Resources res: this.to_be_removed)
					this.discovered_food.remove(res);
			}
			
			synchronized(this.discovered_water)
			{
				this.to_be_removed.clear();
				
				for(Resources food: this.discovered_water)
				{
					synchronized(this.map.agents)
					{
						for(AgentInfo agent: this.map.agents)
						{
							if (Model.getEnvironment().isWithinPerceptionRange(agent.getIntPositionX(), agent.getIntPositionY(), food.getX(), food.getY()))
							{
								synchronized(map.water)
								{
									boolean found = false;
									for(Resources fmap: map.water)
									{
										if (fmap.getX() == food.getX() &&
											fmap.getY() == food.getY())
										{
											found = true;
											break;
										}
									}
									
									if (!found)
										this.to_be_removed.add(food);
								}
							}
						}
					}
				}
				
				for(Resources res: this.to_be_removed)
					this.discovered_water.remove(res);
			}
			
			synchronized(this.map.agents)
			{
				for (AgentInfo agent: this.map.agents)
				{
					this.checkAnthill(EnumTeams.a, agent);
					this.checkAnthill(EnumTeams.b, agent);
					this.checkAnthill(EnumTeams.c, agent);
					this.checkAnthill(EnumTeams.d, agent);
				}
			}
			
			try
			{
				// Sleeps thread
				Thread.sleep(this.sleep_time);
				
				// Pauses thread
				synchronized(this)
				{
					while (threadSuspended)
						wait();
				}
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void checkAnthill(EnumTeams team, AgentInfo agent)
	{
		Team anthill = Model.getWorld().getTeam(team);
		
		if (Model.getEnvironment().isWithinPerceptionRange(anthill.getAnthill().x, anthill.getAnthill().y, agent.getIntPositionX(),agent.getIntPositionY()))
		{
			if (Model.getEnvironment().getActiveAgents(team).size() > 0)
				this.map.addAnthill(anthill);
			
			else
				this.map.removeAnthill(anthill);
		}
			
	}

	/**
	 * Decides if resources are visible by the agent and if so adds them to the map
	 * @param source Source of the resources
	 * @param agent Agent who's percepts are being examined
	 * @param destination Where to save perceived resources
	 */
	private void addResource(ArrayDeque<Resources> source, AgentInfo agent, Set<Resources> destination)
	{
		synchronized(source)
		{	
			for (Resources resource: source)
			{
				if (Model.getEnvironment().isWithinPerceptionRange(agent.getIntPositionX(), agent.getIntPositionY(), resource.getX(), resource.getY()))
				{
					synchronized (destination)
					{
						boolean modified = false;
						for (Resources res: destination)
						{
							if (res.getX() == resource.getX() &&
								res.getY() == resource.getY())
							{
								res.setAmount(resource.getAmout());
								res.directlyVisible();
								modified = true;
								break;
							}
							
						}
						
						if (!modified)
							destination.add(new Resources(resource));
					}
				}
			}
		}
	}
	
	/**
	 * Adds percieved enemies to the map and if the ant does not have any action, automatically attacks the enemy
	 * @param enemies Source of enemies
	 * @param agent Agent who's percepts are being examined
	 */
	private void addAgentsFromTeam(ArrayDeque<AgentInfo> enemies, AgentInfo agent)
	{
		synchronized(enemies)
		{
			for (AgentInfo enemy: enemies)
			{
				if (agent.getAlliance() == enemy.getAlliance())
					return;
				
				else if (Model.getEnvironment().isWithinPerceptionRange(agent.getIntPositionX(), agent.getIntPositionY(), enemy.getIntPositionX(), enemy.getIntPositionY()))
				{
					if (this.auto_attack)
						agent.autoAttack(enemy);
					
					this.visible_enemies.add(enemy);
				}
			}
		}
	}

	/**
	 * Cancels thread
	 */
	public void cancel()
	{
		this.interrupt();
	}

	/**
	 * Resumes paused thread
	 */
	public void resumeThread()
	{
		synchronized(this)
		{
			this.threadSuspended = false;
	        notify();
		}
	}
	
	/**
	 * Pauses thread
	 */
	public void pauseThread()
	{
		synchronized(this)
		{
			this.threadSuspended = true;
	        notify();
		}
	}
	
	/**
	 * Deletes the thread
	 */
	public void delete()
	{
		synchronized(this)
		{
			this.threadSuspended = false;
			this.delete = true;
	        notify();
		}
	}
}
