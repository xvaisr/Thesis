/**
 * BP, anthill strategy game
 * Contains various configuration for both game and simulation
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/11/19
 * @version 1
 * @file    graphic.Configuration.java
 */
package graphic;

import Enviroment.Model;
import graphic.EnumTeams;
import Enviroment.WorldsDatabase.Worlds;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


/**
 * Contains various configuration for both game and simulation
 * @author Vojtech Simetka
 *
 */
public class Configuration
{
	// Random events settings
	private int events_time_between_events = 5000;
	private float events_food_chance = 0.5f;
	private float events_water_chance = 0.5f;
	private boolean events_enabled = true;
	
	// Game-play settings
	private Worlds scenario = Worlds.Custom1;
	private int move_speed = 150;
	private int duration_between_perceptual_update = 200;
	private int agent_count_a = 0;
	private int agent_count_b = 0;
	private int agent_count_c = 0;
	private int agent_count_d = 0;
	
	// Alliances
	private Map<EnumTeams,EnumAlliances> team2alliance = new HashMap<EnumTeams,EnumAlliances>();
	
	// AIs
	private Map<EnumTeams,AIDatabase.EnumAI> team2AI = new HashMap<EnumTeams,AIDatabase.EnumAI>();
	
	// Graphic setting
	private int fps = 30;
	private Color team_a_color = new Color(180,20,30);
	private Color team_b_color = new Color(40,70,250);
	private Color team_c_color = new Color(250,140,40);
	private Color team_d_color = new Color(80,0,130);
	
	private int agent_radius = 10;
	private int resource_radius = 5;
	private boolean noGui = false;
	
	// Upgrade costs
	public int new_ant_food = 10;
	public int new_ant_water = 1;
	public int armor_upgrade_food = 4;
	public int armor_upgrade_water = 6;
	public int attack_upgrade_food = 4;
	public int attack_upgrade_water = 6;
	public int speed_upgrade_food = 0;
	public int speed_upgrade_water = 10;
	

	private float speed_cap = 1.5f;
	private boolean draw_fog = false;
	private EnumExperiments experiment = EnumExperiments.None;
	private int experiment_count = 10;
	private long experiment_length = 100000;
	private int world_size = 500;
	
	/**
	 * Gets chance of new food occurrence
	 * @return Chance of new food occurrence
	 */
	public float getEvents_food_chance()
	{
		return this.events_food_chance;
	}
	
	/**
	 * Sets chance of new food occurrence
	 * @param chance Chance of new food occurrence
	 */
	public void setEvents_food_chance(float chance)
	{
		this.events_food_chance = chance;
	}
	
	/**
	 * Gets time between random events
	 * @return Period of random events 
	 */
	public int getEvents_time_between_events()
	{
		return this.events_time_between_events;
	}
	
	/**
	 * Sets time between random events
	 * @param period Sets period of random events
	 */
	public void setEvents_time_between_events(int period)
	{
		this.events_time_between_events = period;
	}
	
	/**
	 * Gets chance of new water occurrence
	 * @return Chance of new water occurrence
	 */
	public float getEvents_water_chance()
	{
		return this.events_water_chance;
	}
	
	/**
	 * Sets chance of new food occurrence
	 * @param chance Chance of new events to occur
	 */
	public void setEvents_water_chance(float chance)
	{
		this.events_water_chance = chance;
	}
	
	/**
	 * Gets current scenario
	 * @return Current game scenario
	 */
	public Worlds getScenario()
	{
		return this.scenario;
	}
	
	/**
	 * Sets game scenario
	 * @param resourceexperiment1 Scenario to be set
	 */
	public void setScenario(Worlds resourceexperiment1)
	{
		this.scenario = resourceexperiment1;
	}
	
	/**
	 * Gets frequency of movements
	 * @return Movement frequency
	 */
	public int getMove_speed()
	{
		return this.move_speed;
	}
	
	/**
	 * Sets frequency of movement
	 * @param move_speed
	 */
	public void setMove_speed(int move_speed)
	{
		this.move_speed = move_speed;
		if (Model.getAgentsMovenentThread() != null)
			Model.getAgentsMovenentThread().reinitSpeed();
	}
	
	/**
	 * Gets duration for how long every agent fells asleep after making any action
	 * @return Period between percept updates
	 */
	public int getPps()
	{
		return this.duration_between_perceptual_update;
	}
	
	/**
	 * Gets duration for how long every agent fells asleep after making any action
	 * @param duration Length of period between percept updates
	 */
	public void setPps(int duration)
	{
		this.duration_between_perceptual_update = duration;
	}
	
	/**
	 * Gets frame repaint frequency
	 * @return Frequency of repaint for map
	 */
	public int getFps()
	{
		return this.fps;
	}
	
	/**
	 * Sets frame repaint frequency
	 * @param fps Frequency of repaint for map
	 */
	public void setFps(int fps)
	{
		this.fps = fps;
	}

	/**
	 * Gets count of team A agents
	 * @return Agent count for team A
	 */
	public int getAgent_count_a()
	{
		return this.agent_count_a;
	}

	/**
	 * Sets count of agents for team A
	 * @param count New team A agent count
	 */
	public void setAgent_count_a(int count)
	{
		this.agent_count_a = count;
	}

	/**
	 * Gets count of team B agents
	 * @return Agent count for team B
	 */
	public int getAgent_count_b()
	{
		return this.agent_count_b;
	}

	/**
	 * Sets count of agents for team B
	 * @param count New team B agent count
	 */
	public void setAgent_count_b(int count)
	{
		this.agent_count_b = count;
	}

	/**
	 * Gets count of team C agents
	 * @return Agent count for team C
	 */
	public int getAgent_count_c()
	{
		return this.agent_count_c;
	}

	/**
	 * Sets count of agents for team C
	 * @param count New team C agent count
	 */
	public void setAgent_count_c(int count)
	{
		this.agent_count_c = count;
	}

	/**
	 * Gets count of team D agents
	 * @return Agent count for team D
	 */
	public int getAgent_count_d()
	{
		return this.agent_count_d;
	}

	/**
	 * Sets count of agents for team D
	 * @param count New team D agent count
	 */
	public void setAgent_count_d(int count)
	{
		this.agent_count_d = count;
	}

	/**
	 * Sets color of agents for team A
	 * @param color Color of A team agents
	 */
	public void setTeam_a(Color color)
	{
		this.team_a_color = color;
	}

	/**
	 * Sets color of agents for team B
	 * @param color Color of B team agents
	 */
	public void setTeam_b(Color color) {
		this.team_b_color = color;
	}

	/**
	 * Sets color of agents for team C
	 * @param color Color of C team agents
	 */
	public void setTeam_c(Color color)
	{
		this.team_c_color = color;
	}

	/**
	 * Sets color of agents for team D
	 * @param color Color of D team agents
	 */
	public void setTeam_d(Color color)
	{
		this.team_d_color = color;
	}
	
	/**
	 * Returns color for team
	 * @param team Team enumerator
	 * @return Color of the team
	 */
	public Color getColor(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.team_a_color;
		case b:
			return this.team_b_color;
		case c:
			return this.team_c_color;
		case d:
			return this.team_d_color;
		default:
			return null;
		}
	}
	
	/**
	 * Adds team to alliance
	 * @param team Team to be bound with alliance
	 * @param alliance Alliance to which will be added team
	 */
	public void addTeamToAlliance(EnumTeams team, EnumAlliances alliance)
	{
		this.team2alliance.put(team, alliance);
	}

	/**
	 * Gets alliance for team
	 * @param team Team for which we want to know alliance
	 * @return Alliance for team
	 */
	public EnumAlliances getAlliance(EnumTeams team)
	{
		return this.team2alliance.get(team);
	}
	
	/**
	 * Reinitializes configuration
	 */
	public void reInit ()
	{
		team2alliance = new HashMap<EnumTeams,EnumAlliances>();
		team2AI = new HashMap<EnumTeams,AIDatabase.EnumAI>();
		agent_count_a = 0;
		agent_count_b = 0;
		agent_count_c = 0;
		agent_count_d = 0;
	}

	/**
	 * Gets AI for the team
	 * @param team Team which's AI we're searching
	 * @return AI enumerator for team
	 */
	public AIDatabase.EnumAI getAI(EnumTeams team)
	{
		return team2AI.get(team);
	}

	/**
	 * Adds AI for the team
	 * @param team Team for which we are adding AI
	 * @param AI AI enumerator
	 */
	public void addAI(EnumTeams team,AIDatabase.EnumAI AI)
	{
		this.team2AI.put(team, AI);
	}

	/**
	 * Returns visual size of the agent
	 * @return Visual size of the agent
	 */
	public int getAgentRadius()
	{
		return this.agent_radius;
	}

	/**
	 * Returns anthill's perceptual update rate
	 * @return Anthill's perceptual update rate
	 */
	public int getAnthillPps()
	{
		return this.duration_between_perceptual_update * 10;
	}

	/**
	 * Returns speed update cap
	 * @return Speed update cap
	 */
	public float getSpeedCap()
	{
		return this.speed_cap;
	}

	/**
	 * Returns true if fog should be displayed
	 * @return True if fog should be displayed
	 */
	public boolean drawFog()
	{
		return this.draw_fog;
	}
	
	/**
	 * Sets if fog should be displayed
	 * @param bool Whether or not fog should be displayed
	 */
	public void setFog(boolean bool)
	{
		this.draw_fog = bool;
	}

	/**
	 * Sets which experiment is being run
	 * @param experiment Experiment which is being run
	 */
	public void setExperiment(EnumExperiments experiment)
	{
		this.experiment = experiment;
	}

	/**
	 * Returns experiment which is being run
	 * @return Experiment which is being run
	 */
	public EnumExperiments getExperiment()
	{
		return this.experiment;
	}

	/**
	 * Returns how many repetition experiments should be performed
	 * @return How many repetition experiments should be performed
	 */
	public int experiment_count()
	{
		return this.experiment_count;
	}

	/**
	 * Returns true if events thread is enabled
	 * @return True if events thread is enabled
	 */
	public boolean isEventsEnabled()
	{
		return events_enabled;
	}

	/**
	 * Sets if random events should occur
	 * @param bool Whether or not random events should occur
	 */
	public void setEvents(boolean bool)
	{
		this.events_enabled = bool;
	}

	/**
	 * Returns visual size of a resource field
	 * @return Visual size of a resource field
	 */
	public int getResourceRadius()
	{
		return this.resource_radius;
	}

	/**
	 * Returns true if game should run without GUI
	 * @return True if game should run without GUI
	 */
	public boolean isNoGui()
	{
		return noGui;
	}

	/**
	 * Sets if game should be run without GUI
	 * @param noGui Whether or not the game should be run without GUI
	 */
	public void setNoGui(boolean noGui)
	{
		this.noGui = noGui;
	}

	/**
	 * Returns how long the experiment should take
	 * @return How long the experiment should take
	 */
	public long getExperimentGameLenght()
	{
		return this.experiment_length;
	}

	/**
	 * Gets world size for experiment
	 * @return World size for experiment
	 */
	public int getWorldSize()
	{
		return this.world_size;
	}
	
	/**
	 * Sets world size for the experiment
	 * @param size World size for the experiment
	 */
	public void setWorldSize(int size)
	{
		this.world_size = size;
	}
}
