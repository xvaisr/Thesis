/**
 * BP, anthill strategy game
 * Extension of jason.environment.Environment
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/25
 * @version 1
 * @file    graphic.Environment.java
 */
package Enviroment;

import graphic.AIDatabase;
import Agents.AgentInfo;
import graphic.EnumAlliances;
import graphic.EnumExperiments;
import graphic.EnumTeams;
import graphic.PanelMap;
import graphic.Team;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implements environment for agents
 * @author Vojtech Simetka
 *
 */
public class Environment extends jason.environment.Environment
{
	private final int perceptRadius = 75;
	private Map<String, AgentInfo> agent2info = new HashMap<String,AgentInfo>();
	private ArrayDeque<AgentInfo> active_agentsA = new ArrayDeque<AgentInfo>();
	private ArrayDeque<AgentInfo> active_agentsB = new ArrayDeque<AgentInfo>();
	private ArrayDeque<AgentInfo> active_agentsC = new ArrayDeque<AgentInfo>();
	private ArrayDeque<AgentInfo> active_agentsD = new ArrayDeque<AgentInfo>();
	private int id = 0;
	private List<String> architecture = new ArrayList<String>();
	
	private PanelMap map;
	
	/**
	 * Defines reactions to user defined actions from Jason
	 * @param agent_name Invoking agent's name
	 * @param action Action to be processed
	 */
	@Override
	public boolean executeAction(String agent_name, Structure action)
	{	
		AgentInfo agent_info = this.agent2info(agent_name);
		
		// Probably anthill action
		if (agent_info == null)
		{
			if (agent_name.contentEquals("anthilla"))
				this.anthillActions(EnumTeams.a, action.toString());
			
			else if (agent_name.contentEquals("anthillb"))
				this.anthillActions(EnumTeams.b, action.toString());
			
			else if (agent_name.contentEquals("anthillc"))
				this.anthillActions(EnumTeams.c, action.toString());
			
			else if (agent_name.contentEquals("anthilld"))
				this.anthillActions(EnumTeams.d, action.toString());
			
			// Not an anthill, somewhere is an error
			else
				System.out.println("Error no such anthill");
			
			// No failure is possible for anthill actions
			return true;
		}
		
		// Agent actions
		else 
		{
			// Execute action for the agent
			boolean result = this.agentAction(agent_info, action);
		
			// Updates agent percepts
			this.updateAgPercept(agent_info);
		
			// Return result
			return result;
		}
	}
	
	/**
	 * Executes agent action
	 * @param agent_info Reference to agent's java representation
	 * @param action Action to be performed
	 * @return True if action was executed succesfully
	 */
	private boolean agentAction(AgentInfo agent_info, Structure action)
	{

		// update_percepts
		if (action.toString().startsWith("update_percepts"))
			return true;
		
		// The unit is performing a user action, ignore AI actions
		else if (agent_info.isUserAction())
			return true;
		
		// crawl(PX,PY)
		else if (action.toString().startsWith("crawl"))
			Model.getWorld().move(Integer.parseInt(action.getTerm(0).toString()),
		 					      Integer.parseInt(action.getTerm(1).toString()),
		 					      agent_info);
		
		// collect_water(X,Y)
		else if (action.toString().contentEquals("collect(water)"))
			return Model.getWorld().collect_water(agent_info);
		
		// collect_food(X,Y)
		else if (action.toString().contentEquals("collect(food)"))
			return Model.getWorld().collect_food(agent_info);
		
		// unload
		else if (action.toString().contentEquals("unload"))
		{
			Model.getWorld().unload_water(agent_info);
			Model.getWorld().unload_food(agent_info);
		}
			
		// attack(Agent)
		else if (action.toString().startsWith("attack"))
			Model.getWorld().attack(this.agent2info(action.getTerm(0).toString()), agent_info);
		
		
		// stop_attack
		else if (action.toString().equals("stop_attack"))
			Model.getWorld().stopAttack(agent_info);
	
		// Unknown action
		else
		{
			System.out.println("Doimplementuj akci: " + action.toString());
			return false;
		}
		return true;
	}

	/**
	 * Executes anthill's action
	 * @param team Anthill's team enumerator
	 * @param action Action to be performed
	 */
	private void anthillActions(EnumTeams team, String action)
	{
		if (!action.contentEquals("update_percepts"))
		{
			// User is playing, ignore all anthill actions
			if (Model.isUserPlaying() && Model.getUserTeam().getTeam() == team)
				return;
			
			// Create new ant
			if (action.contentEquals("new_agent"))
				this.createNewAnt(team);
			
			// Upgrade attack
			else if (action.contentEquals("upgrade_attack"))
				this.upgradeAttack(team);
			
			// Upgrade armor
			else if (action.contentEquals("upgrade_armor"))
				this.upgradeArmor(team);
			
			// Upgrade speed
			else if (action.contentEquals("upgrade_speed"))
				this.upgradeSpeed(team);
		}
		
		// Updates percepts
		this.updateAnthillPercepts(team);
	}

	/**
	 * Updates anthill's percepts
	 * @param team_enum Anthill's team enumerator
	 */
	private void updateAnthillPercepts(EnumTeams team_enum)
	{
		// Gets team reference for the anthill
		Team team = Model.getWorld().getTeam(team_enum);
		
		// Clears percepts for the anthill
		this.clearPercepts(team.getName());
		
		int army = 0;
		
		switch(team_enum)
		{
		case a:
			army = this.active_agentsA.size();
			break;
		case b:
			army = this.active_agentsB.size();
			break;
		case c:
			army = this.active_agentsC.size();
			break;
		case d:
			army = this.active_agentsD.size();
			break;
		default:
			break;
		}
		
		// Adds precepts
		this.addPercept(team.getName(), Literal.parseLiteral("food("+ team.getFood()+")"));
		this.addPercept(team.getName(), Literal.parseLiteral("water("+ team.getWater()+")"));
		this.addPercept(team.getName(), Literal.parseLiteral("armor("+ team.getArmor()+")"));
		this.addPercept(team.getName(), Literal.parseLiteral("attack("+ team.getAttack()+")"));
		this.addPercept(team.getName(), Literal.parseLiteral("speed("+ team.getSpeed()+")"));
		this.addPercept(team.getName(), Literal.parseLiteral("army("+ army +")"));
		this.addPercept(team.getName(), Literal.parseLiteral("armor_upgrade_price(" + team.getArmorUpgradePriceFood() + "," + team.getArmorUpgradePriceWater() + ")"));
		this.addPercept(team.getName(), Literal.parseLiteral("attack_upgrade_price(" + team.getAttackUpgradePriceFood() + "," + team.getAttackUpgradePriceWater() + ")"));
		this.addPercept(team.getName(), Literal.parseLiteral("speed_upgrade_price(" + team.getSpeedUpgradePriceFood() + "," + team.getSpeedUpgradePriceWater() + ")"));
	}

	/**
	 * Overwrites init function of Jason's environment
	 * @param args Array of string arguments past by mas2j file
	 */
	@Override
	public void init(String[] args)
	{
		// Necessary for creation of new agents
		architecture.add("jason.architecture.AgArch");
		
		// Creates game
		Model.init(this);
	}
	
	/**
	 * Updates percepts for one agent
	 * @param agent_info Information about agent
	 */
	private void updateAgPercept(AgentInfo agent_info)
	{	
		if (agent_info == null)
			return;
		
		// If game is paused don't update percepts
		if (!Model.isGame_running())
			return;
		
		// Clears all percepts
		this.clearPercepts(agent_info.getName());
		
		// Adds percepts about agent's position
		this.addPercept(agent_info.getName(), Literal.parseLiteral("pos(" + agent_info.getIntPositionX() + "," + agent_info.getIntPositionY() + ")"));
		
		// Adds percepts about agent's healthpoint
		this.addPercept(agent_info.getName(), Literal.parseLiteral("hp("+ agent_info.getHp() + ")"));
		
		// Carrying any resource?
		if (agent_info.getFood())
			this.addPercept(agent_info.getName(), Literal.parseLiteral("collected(food)"));
		
		else if (agent_info.getWater())
			this.addPercept(agent_info.getName(), Literal.parseLiteral("collected(water)"));

		// Updates what is around agent
		this.updateAgSurrounding(agent_info);
	}
	
	/**
	 * Adds percepts that are within agents percept range
	 * @param agent_info Agent descriptor
	 */
	private void updateAgSurrounding(AgentInfo agent_info)
	{
		// Adds percepts about food within agent range
		this.addPerceptsAboutResources(agent_info, Model.getWorld().getFood(), "food");
		
		// Adds percepts about water within agent range
		this.addPerceptsAboutResources(agent_info, Model.getWorld().getWater(), "water");
		
		// Adds percepts about agents from team A
		this.addPerceptsAboutAgents(agent_info, Model.getEnvironment().getActive_agentsA());
		
		// Adds percepts about agents from team B
		this.addPerceptsAboutAgents(agent_info, Model.getEnvironment().getActive_agentsB());

		// Adds percepts about agents from team C
		this.addPerceptsAboutAgents(agent_info, Model.getEnvironment().getActive_agentsC());

		// Adds percepts about agents from team D
		this.addPerceptsAboutAgents(agent_info, Model.getEnvironment().getActive_agentsD());
	}
	
	/**
	 * Adds percepts about enemy units within agent's perceptual range
	 * @param agent_info Agent descriptor
	 * @param source Which agents will be checked and added as percepts for the agent
	 */
	private void addPerceptsAboutAgents(AgentInfo agent_info, ArrayDeque<AgentInfo> source)
	{
		synchronized (source)
		{
			for (AgentInfo agent: source)
			{
				// Skip the agent itself
				if (agent == agent_info)
					continue;
				
				// Adds percepts about enemy units
				this.addPerceptsAboutAgents(agent, agent_info);
			}
		}
	}

	/**
	 * Adds percepts about resources within agent's perceptual range
	 * @param agent_info Agent descriptor
	 * @param source Resources to be chacked and added as a percepts for the agent
	 * @param type Type of the resource
	 */
	private void addPerceptsAboutResources(AgentInfo agent_info, ArrayDeque<Resources> source, String type)
	{
		synchronized (source)
		{ 
			for (Resources resource: source)
			{
				// If the resource is within perceptual range 
				if (this.isWithinPerceptionRange(agent_info.getIntPositionX(),
												 agent_info.getIntPositionY(),
												 resource.getX(),
												 resource.getY()))
				{
					// Add percept
					this.addPercept(agent_info.getName(),
									Literal.parseLiteral("resource(" + resource.getX() + "," + resource.getY() + "," + resource.getAmout() + "," + type + ")"));
				
					// If user is not playing, select the resource as already observed
					if (!Model.isUserPlaying())
						resource.directlyVisible();
				}
			}
		}
	}
	
	/**
	 * If the enemy is within perceptual range
	 * @param me Agent descriptor
	 * @param enemy Enemy agent descriptor
	 */
	private void addPerceptsAboutAgents(AgentInfo enemy, AgentInfo me)
	{
		if (this.isWithinPerceptionRange(me.getIntPositionX(), me.getIntPositionY(), enemy.getIntPositionX(), enemy.getIntPositionY()))
		{
			// Enemy and agent are friends, add belief friend
			if (me.getAlliance() == enemy.getAlliance())
				this.addPercept(me.getName(), Literal.parseLiteral("friend(" + enemy.getIntPositionX() + "," + enemy.getIntPositionY()  + "," + enemy.getName() + ")"));
			
			// Otherwise add belief enemy
			else
				this.addPercept(me.getName(), Literal.parseLiteral("enemy(" + enemy.getIntPositionX() + "," + enemy.getIntPositionY()  + "," + enemy.getName() + ")"));
		}
	}
	
	/**
	 * Determines whether or not is something within percept range 
	 * @param x1 Source's coordinate x
	 * @param y1 Source's coordinate y
	 * @param x2 Inspected object's coordinate x
	 * @param y2 Inspected object's coordinate y
	 * @return True if object is within percept range
	 */
	public boolean isWithinPerceptionRange(int x1, int y1, int x2, int y2)
	{
		// a^2 + b^2 < c^2
		return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) < getPerceptRadius() * getPerceptRadius();
	}

	/**
	 * Gets percept radius
	 * @return Gets percept radius
	 */
	public int getPerceptRadius()
	{
		return perceptRadius;
	}
	
	/**
	 * Starts environment
	 */
	public void start()
	{
		// Just in case some agent is still alive from last game
		this.killAllAgents();
		
		// Clears and updates all agents percepts
		this.clearAllPercepts();

		// Creates anthills
		this.createAnthills();
		
		// Adds agents to their teams and environment
		this.addAgents();
	}

	/**
	 * Creates anthills for teams in game
	 */
	private void createAnthills()
	{
		// Creates anthill A
		if (Model.getConfiguration().getAgent_count_a() > 0)
			this.createAnthill(Model.getWorld().getTeam(EnumTeams.a));

		// Creates anthill B
		if (Model.getConfiguration().getAgent_count_b() > 0)
			this.createAnthill(Model.getWorld().getTeam(EnumTeams.b));

		// Creates anthill C
		if (Model.getConfiguration().getAgent_count_c() > 0)
			this.createAnthill(Model.getWorld().getTeam(EnumTeams.c));

		// Creates anthill D
		if (Model.getConfiguration().getAgent_count_d() > 0)
			this.createAnthill(Model.getWorld().getTeam(EnumTeams.d));
		
		if (Model.isUserPlaying())
			this.map.removeAllAnthills();
	}

	/**
	 * Creates anthill agent and adds initial beliefs
	 * @param team
	 */
	private void createAnthill(Team team)
	{
		String ai = null;
		
		// If this is time complexity experiment, get time complexity asl file
		if (Model.getConfiguration().getExperiment() == EnumExperiments.TimeComplexity)
			ai = AIDatabase.getTimeComplexityAnthillAI(team);
		
		// Otherwise get asl file for the AI
		else
			ai = AIDatabase.getAnthillAI(team);
		
		// The anthill is controlled by AI
		if (ai != null)
		{
			try
			{
				// Create agent
				this.getEnvironmentInfraTier().getRuntimeServices().createAgent(team.getName(), ai, null, architecture, null, null);
				
				// Add initial beliefs
				this.addBelief(team.getName(), "update_rate(" + Model.getConfiguration().getAnthillPps() + ")");
				this.addBelief(team.getName(), "speed_cap(" + Model.getConfiguration().getSpeedCap() + ")");
				this.addBelief(team.getName(), "new_ant_price(" + Model.getConfiguration().new_ant_food + "," + Model.getConfiguration().new_ant_water + ")");
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			// Starts new agent
			this.getEnvironmentInfraTier().getRuntimeServices().startAgent(team.getName());
		}
	}

	/**
	 * Creates and adds agents for all teams into the environment
	 */
	private void addAgents()
	{	
		// Team A ants
		for (int i = 0; i < Model.getConfiguration().getAgent_count_a(); i++)
			newAgent(EnumTeams.a);
		
		// Team B ants
		for (int i = 0; i < Model.getConfiguration().getAgent_count_b(); i++)
			newAgent(EnumTeams.b);
		
		// Team C ants
		for (int i = 0; i < Model.getConfiguration().getAgent_count_c(); i++)
			newAgent(EnumTeams.c);
		
		// Team D ants
		for (int i = 0; i < Model.getConfiguration().getAgent_count_d(); i++)
			newAgent(EnumTeams.d);
	}
	
	/**
	 * Creates and initializes agent of selected team
	 * @param team Team of new agent
	 */
	public void newAgent(EnumTeams team)
	{
		String agent_name = team.toString() + id++;
		AgentInfo agent_info = null;
		
		// Create user's ant
		if (AIDatabase.isUserAI(Model.getConfiguration().getAI(team)))
			agent_info = new AgentInfo(agent_name, team, this.map);
		
		// Create AI's ant
		else
			agent_info = new AgentInfo(agent_name, team);
		
		String ai = null;

		// If this is time complexity experiment, get time complexity asl file
		if (Model.getConfiguration().getExperiment() == EnumExperiments.TimeComplexity)
			ai = AIDatabase.getTimeComplexityAntAI(team);

		// Otherwise get asl file for the AI
		else
			ai = AIDatabase.getAntAI(team);

		// The ant is controlled by AI
		if (ai != null)
		{
			try
			{
				// Creates agent
				this.getEnvironmentInfraTier().getRuntimeServices().createAgent(agent_name, ai, null, architecture, null, null);
				
				// Adds initial beliefs
				this.addBelief(agent_info.getName(), "home(" + Model.getWorld().getAnthill(agent_info.getTeam()).x + "," + Model.getWorld().getAnthill(agent_info.getTeam()).y + ")");
				this.addBelief(agent_info.getName(), "update_rate(" + Model.getConfiguration().getPps() + ")");
				this.addBelief(agent_info.getName(), "anthill(" + Model.getWorld().getTeam(team).getName() + ")");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		// Adds agent to adequate team list
		this.addAgentToList(agent_info);
		
		// Adds agent to the GUI map
		this.addAgentToMap(agent_info);
		
		// Starts new agent
		if (ai != null)
			this.getEnvironmentInfraTier().getRuntimeServices().startAgent(agent_name);
	}

	/**
	 * Decides if the agent should be added to the GUI map and displayed
	 * @param agent_info Agent to be added
	 */
	private void addAgentToMap(AgentInfo agent_info)
	{
		// User is playing, add only user friendly maps
		if (Model.isUserPlaying())
		{
			// Add friendly unit to the map
			if (agent_info.isUserControlled() ||
				agent_info.getAlliance() == Model.getUserTeam().getAlliance())
			{
				if (map != null)
					this.map.addAgent(agent_info);
			}
		}
		
		// User is not playing, all ants should be added to the map
		else 
		{
			if (map != null)
				this.map.addAgent(agent_info);
		}
	}

	/**
	 * Adds agent to adequate list in the environment. There is a list for every team
	 * @param agent_info
	 */
	private void addAgentToList(AgentInfo agent_info)
	{
		// Adds name of the agent and its descriptor to the HashMap
		this.agent2info.put(agent_info.getName(), agent_info);
		
		switch(agent_info.getTeam())
		{
		// Ant of the team A
		case a:
			this.addAgentToTeamAList(agent_info);
			break;
			
		// Ant of the team B
		case b:
			this.addAgentToTeamBList(agent_info);
			break;

		// Ant of the team C
		case c:
			this.addAgentToTeamCList(agent_info);
			break;

		// Ant of the team D
		case d:
			this.addAgentToTeamDList(agent_info);
			break;
			
		// Agent of such team does not exist
		default:
			System.out.println("Adding agent with unknown team");
			break;
		}
	}
	
	/**
	 * Adds ant of team A to adequate list in the environment and records a new agent of team A
	 * @param agent Agent to be added
	 */
	private void addAgentToTeamAList(AgentInfo agent)
	{
		synchronized(this.getActive_agentsA())
		{
			// Add ant to the list
			this.active_agentsA.add(agent);
			
			// Add statistics about addition a new ant
			Model.getStatistics().addAgentA(Model.getAgentsMovenentThread().getGameLength());
		}
	}
	
	/**
	 * Adds ant of team B to adequate list in the environment and records a new agent of team B
	 * @param agent Agent to be added
	 */
	private void addAgentToTeamBList(AgentInfo agent)
	{
		synchronized(this.getActive_agentsB())
		{
			// Add ant to the list
			this.active_agentsB.add(agent);
			
			// Add statistics about addition a new ant
			Model.getStatistics().addAgentB(Model.getAgentsMovenentThread().getGameLength());
		}
	}

	/**
	 * Adds ant of team C to adequate list in the environment and records a new agent of team C
	 * @param agent Agent to be added
	 */
	private void addAgentToTeamCList(AgentInfo agent)
	{
		synchronized(this.getActive_agentsC())
		{
			// Add ant to the list
			this.active_agentsC.add(agent);
			
			// Add statistics about addition a new ant
			Model.getStatistics().addAgentC(Model.getAgentsMovenentThread().getGameLength());
		}
	}
	
	/**
	 * Adds ant of team D to adequate list in the environment and records a new agent of team D
	 * @param agent Agent to be added
	 */
	private void addAgentToTeamDList(AgentInfo agent)
	{
		synchronized(this.getActive_agentsD())
		{
			// Add ant to the list
			this.active_agentsD.add(agent);
			
			// Add statistics about addition a new ant
			Model.getStatistics().addAgentD(Model.getAgentsMovenentThread().getGameLength());
		}
	}
	
	/**
	 * Get agents of team A
	 * @return Team A agent list
	 */
	public ArrayDeque<AgentInfo> getActive_agentsA()
	{
		return active_agentsA;
	}

	/**
	 * Get agents of team B
	 * @return Team B agent list
	 */
	public ArrayDeque<AgentInfo> getActive_agentsB()
	{
		return active_agentsB;
	}
	
	/**
	 * Get agents of team C
	 * @return Team C agent list
	 */
	public ArrayDeque<AgentInfo> getActive_agentsC()
	{
		return active_agentsC;
	}

	/**
	 * Get agents of team D
	 * @return Team D agent list
	 */
	public ArrayDeque<AgentInfo> getActive_agentsD()
	{
		return active_agentsD;
	}
	
	/**
	 * Adds belief to agents belief base
	 * @param agent_name Neme of the agent
	 * @param string Belief to be added
	 */
	private void addBelief(String agent_name, String string)
	{
		this.addPercept(agent_name, Literal.parseLiteral(string+"[source(self)]"));
	}

	/**
	 * Kills agent and records that it was killed
	 * @param agent Agent to be killed
	 */
	public void killAgent(AgentInfo agent)
	{	
		// Kills agent in the Jason
		this.getEnvironmentInfraTier().getRuntimeServices().killAgent(agent.getName(), null);
			
		// Removes agent from the environment
		this.removeAgentFromList(agent);

		// Removes agent from the map
		if (map != null)
			this.map.removeAgent(agent);
		
		// Adds food for the agent death
		if (agent.getFood())
			Model.getWorld().addFood(agent.getIntPositionX(), agent.getIntPositionY(), 4);
		else
			Model.getWorld().addFood(agent.getIntPositionX(), agent.getIntPositionY(), 3);
		
		// Adds water if agent is carrying any
		if (agent.getWater())
			Model.getWorld().addWater(agent.getIntPositionX(), agent.getIntPositionY(), 1);
	
		// If time complexity or performance experiments are being run, create new agent for killed one 
		if (Model.getConfiguration().getExperiment() == EnumExperiments.TimeComplexity ||
			Model.getConfiguration().getExperiment() == EnumExperiments.Performance ||
			Model.getConfiguration().getExperiment() == EnumExperiments.Performance2)
			this.newAgent(agent.getTeam());
		
		// Checks if only one alliance is remaining and if so finish the game
		else
			this.onlyOneAllienceRemaining();
	}
	
	/**
	 * Removes any knowledge about the ant in the environment
	 * @param agent Agent to be removed
	 */
	private void removeAgentFromList(AgentInfo agent)
	{
		// Removes agent from the HashMap
		this.agent2info.remove(agent.getName());
		
		// Removes ant fromm adequate list
		switch(agent.getTeam())
		{
		// Ant of the team A
		case a:
			this.removeAgentFromTeamAList(agent);
			break;

		// Ant of the team B
		case b:
			this.removeAgentFromTeamBList(agent);
			break;
			
		// Ant of the team C
		case c:
			this.removeAgentFromTeamCList(agent);
			break;

		// Ant of the team D
		case d:
			this.removeAgentFromTeamDList(agent);
			break;
			
		// Unknown ant team, should never happen
		default:
			System.out.println("Unknown team of ant to be killed");
			break;
		}
	}
	
	/**
	 * Removes ant of team A from adequate list in the environment and records an agent of team A was killed
	 * @param agent Agent to be removed
	 */
	private void removeAgentFromTeamAList(AgentInfo agent)
	{
		synchronized(this.active_agentsA)
		{
			// Removes ant from the list
			this.active_agentsA.remove(agent);
			
			// Records agent was killed
			Model.getStatistics().removeAgentA(Model.getAgentsMovenentThread().getGameLength());
		}
	}
	
	/**
	 * Removes ant of team B from adequate list in the environment and records an agent of team B was killed
	 * @param agent Agent to be removed
	 */
	private void removeAgentFromTeamBList(AgentInfo agent)
	{
		synchronized(this.active_agentsA)
		{
			// Removes ant from the list
			this.active_agentsB.remove(agent);
			
			// Records agent was killed
			Model.getStatistics().removeAgentB(Model.getAgentsMovenentThread().getGameLength());
		}
	}
	
	/**
	 * Removes ant of team C from adequate list in the environment and records an agent of team C was killed
	 * @param agent Agent to be removed
	 */
	private void removeAgentFromTeamCList(AgentInfo agent)
	{
		synchronized(this.active_agentsC)
		{
			// Removes ant from the list
			this.active_agentsC.remove(agent);
			
			// Records agent was killed
			Model.getStatistics().removeAgentC(Model.getAgentsMovenentThread().getGameLength());
		}
	}
	
	/**
	 * Removes ant of team D from adequate list in the environment and records an agent of team D was killed
	 * @param agent Agent to be removed
	 */
	private void removeAgentFromTeamDList(AgentInfo agent)
	{
		synchronized(this.active_agentsD)
		{
			// Removes ant from the list
			this.active_agentsD.remove(agent);
			
			// Records agent was killed
			Model.getStatistics().removeAgentD(Model.getAgentsMovenentThread().getGameLength());
		}
	}

	/**
	 * Checks if there is only one alliance remaining and if so set the game ends
	 */
	private void onlyOneAllienceRemaining()
	{
		EnumAlliances reference = null;
		
		// If there is any ant for team A, safe its alliance
		if (!this.getActive_agentsA().isEmpty())
			reference = (Model.getConfiguration().getAlliance(EnumTeams.a));

		if (!this.getActive_agentsB().isEmpty())
		{
			// If so far there wasn't any team with alive ants, safe team B alliance
			if (reference == null) 
				reference = (Model.getConfiguration().getAlliance(EnumTeams.b));
			
			// Otherwise check if the alliance differs and if so return
			else if (reference != Model.getConfiguration().getAlliance(EnumTeams.b))
				return;
		}
		
		if (!this.getActive_agentsC().isEmpty())
		{
			// If so far there wasn't any team with alive ants, safe team C alliance
			if (reference == null) 
				reference = (Model.getConfiguration().getAlliance(EnumTeams.c));

			// Otherwise check if the alliance differs and if so return
			else if (reference != Model.getConfiguration().getAlliance(EnumTeams.c))
				return;
		}
		
		if (!this.getActive_agentsD().isEmpty())
		{
			// If so far there wasn't any team with alive ants, safe team D alliance
			if (reference == null) 
				reference = (Model.getConfiguration().getAlliance(EnumTeams.d));

			// Otherwise check if the alliance differs and if so return
			else if (reference != Model.getConfiguration().getAlliance(EnumTeams.d))
				return;
		}
		
		// Game ended, only one alliance remains
		Model.setGameEnd();
	}

	/**
	 * Translates name of agent to AgentInfo
	 * @param agent_name Name of an agent which info we want to retrieve
	 * @return AgentInfo of requested agent
	 */
	public AgentInfo agent2info(String agent_name)
	{
		return this.agent2info.get(agent_name);
	}

	/**
	 * Kills all agents that are in world. This is called when game ends and killed agents are not stored in statistics.
	 */
	public void killAllAgents()
	{	
		// Kills all agents
		for (String agent: this.getEnvironmentInfraTier().getRuntimeServices().getAgentsNames())
			this.getEnvironmentInfraTier().getRuntimeServices().killAgent(agent, null);
		
		// Clears team lists
		this.active_agentsA.clear();
		this.active_agentsB.clear();
		this.active_agentsC.clear();
		this.active_agentsD.clear();
		
		// Clears HashMap
		this.agent2info.clear();
		
		// Sets id of next agent to 0
		this.id = 0;
	}
	
	/**
	 * Sets map reference to the environment.
	 * @param map Reference to map
	 */
	public void setMap(PanelMap map)
	{
		this.map = map;
	}
	
	/**
	 * Internal action that creates new ant.
	 * @param team Team which creates new ant.
	 */
	public void createNewAnt(EnumTeams team)
	{
		Team team_ptr = Model.getWorld().getTeam(team);
		
		synchronized(team_ptr)
		{
			// If the anthill has enough resources to create new ant create it 
			if (this.isNewAntCreatable(team_ptr))
			{
				// Removes adequate resources for creation of the new ant
				team_ptr.removeFood(Model.getConfiguration().new_ant_food);
				team_ptr.removeWater(Model.getConfiguration().new_ant_water);

				// If this is time complexity or performance experiment, do not actually create new ant
				if (Model.getConfiguration().getExperiment() == EnumExperiments.TimeComplexity ||
					Model.getConfiguration().getExperiment() == EnumExperiments.Performance ||
					Model.getConfiguration().getExperiment() == EnumExperiments.Performance2)
					return;
				
				// Create new ant
				this.newAgent(team);
			}
		}
	}
	
	/**
	 * Creates new ant. Triggered by the user from the GUI.
	 * @param team
	 */
	public void createNewAnt(Team team)
	{
		synchronized(team)
		{
			// If the team has enough resources to create new ant create it.
			if (this.isNewAntCreatable(team))
			{
				// Removes adequate resources for creation of the new ant
				team.removeFood(Model.getConfiguration().new_ant_food);
				team.removeWater(Model.getConfiguration().new_ant_water);
				
				// Create new ant
				this.newAgent(team.getTeam());
			}
		}
	}
	
	/**
	 * Returns true if the team has enough resources for creation of new ant
	 * @param team Reference to team
	 * @return True if new ant can be created
	 */
	public boolean isNewAntCreatable(Team team)
	{
		if (team.getFood() >= Model.getConfiguration().new_ant_food &&
			team.getWater() >= Model.getConfiguration().new_ant_water)
			return true;
		
		return false;
	}
	
	/**
	 * Upgrades armour of the team
	 * @param team Team that wants to upgrade armour
	 */
	public void upgradeArmor(EnumTeams team)
	{
		Model.getWorld().getTeam(team).upgradeArmor();
	}

	
	/**
	 * Upgrades attack of the team
	 * @param team Team that wants to upgrade attack
	 */
	public void upgradeAttack(EnumTeams team)
	{
		Model.getWorld().getTeam(team).upgradeAttack();
	}

	
	/**
	 * Upgrades speed of the team
	 * @param team Team that wants to upgrade speed
	 */
	public void upgradeSpeed(EnumTeams team)
	{
		Model.getWorld().getTeam(team).upgradeSpeed();
	}

	/**
	 * 
	 * @param team
	 * @return
	 */
	public ArrayDeque<AgentInfo> getActiveAgents(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.active_agentsA;
		case b:
			return this.active_agentsB;
		case c:
			return this.active_agentsC;
		case d:
			return this.active_agentsD;
		default:
			return null;
		}
	}
}
