/**
 * BP, anthill strategy game
 * Singleton used as a interface between Jason and Java, controlls overall game
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/20
 * @version 1
 * @file    graphic.Model.java
 */
package Enviroment;

import graphic.AIDatabase;
import graphic.Configuration;
import graphic.EnumAlliances;
import graphic.EnumTeams;
import graphic.PanelMap;
import graphic.Statistics;
import graphic.Team;
import graphic.ThreadEvents;
import graphic.ThreadFPS;
import graphic.ThreadStartGame;
import graphic.ThreadUserGameElementsUpdater;
import graphic.View;
import Enviroment.WorldsDatabase.Worlds;


/**
 * Stores statically information about game
 * This is necessary for internal actions
 * Singleton used as a interface between Jason and Java, controls overall game
 * @author Vojtech Simetka
 *
 */
public class Model
{
	private static GameMap world = null;
	private static View view = null;
	private static Environment environment = null;
	private static ThreadFPS animation = null;
	private static ThreadEvents random_events = null;
	private static ThreadEnvironmentSteps agents_movenent = null;
	private static ThreadUserGameElementsUpdater enemy_updater = null;
	private static Configuration configuration = null;
	private static Statistics statistics = null;
	
	private static boolean game_running = false;
	
	private static boolean user_playing = false;
	private static Team user_team;
	private static int experiments_with_current_settings;
	
	private static boolean game_ended = false;
	private static boolean user_notified = false;
	
	/**
	 * Creates everything what is necessary for the game, called in Environment.init()
	 * @param environment Reference to already existing environment since it is created by MAS as a first component
	 */
	public static void init(Environment environment)
	{
		// Loads last used preferences
        Model.configuration = new Configuration();
		Model.view = new View();
		
        // Creates game objects
		Model.environment = environment;
	}

	/**
	 * Starts new world, this is called when user creates new game
	 */
	public synchronized static void startWorld(PanelMap map)
	{
		// Multiple attempts to start game
		if (Model.game_running)
			return;
		
		// We don't yet know if there is user playing the game
		Model.user_playing = false;

		// Creates threads
        Model.animation = new ThreadFPS();
        Model.agents_movenent = new ThreadEnvironmentSteps();
        Model.random_events = new ThreadEvents();
		
		// Creates new statistics for world
		Model.statistics = new Statistics();
		
		// Erases all information about world in view
		Model.world = new GameMap(map);
		
		// This should start game
		Model.game_running = true;
		Model.game_ended = false;
		Model.user_notified = false;
		
        // Creates and starts agents in environment
    	Model.environment.start();
    	
    	if (Model.user_playing)
    	{
    		Model.enemy_updater  = new ThreadUserGameElementsUpdater(map);
    		Model.enemy_updater.start();
    	}
        
        // Starts threads
        Model.agents_movenent.start();
        Model.random_events.start();
        Model.animation.start();
	}
	
	/**
	 * Destroys world when game finishes
	 */
	public static void destroyWorld()
	{	
		// Saves the game length
		Model.statistics.setGameLength(Model.agents_movenent.getGameLength());
		
		// Kills all agents
		Model.environment.killAllAgents();
		
		// Destroys map
		Model.getEnvironment().setMap(null);
		
        // Stops game
		Model.game_running = false;
		
		// Destroys all listeners for frame
		Model.view.removeAllListeners();
		
		// Destroys all threads
		Model.animation.delete();
        Model.random_events.delete();
        Model.agents_movenent.delete();
        
        if (Model.enemy_updater != null)
        	Model.enemy_updater.delete();
        
        Model.enemy_updater = null;
        Model.animation = null;
        Model.random_events = null;
        Model.agents_movenent = null;
        
        // Repaints the game
        Model.getView().repaint();
        
        Model.user_playing = false;
	}

	/**
	 * Gets configuration
	 * @return Configuration of game client
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Gets world pointer
	 * @return world pointer
	 */
	public static GameMap getWorld()
	{
		return world;
	}

	/**
	 * Gets view pointer
	 * @return View pointer
	 */
	public static View getView()
	{
		return view;
	}

	/**
	 * Gets environment pointer
	 * @return Environment pointer
	 */
	public static Environment getEnvironment()
	{
		return environment;
	}

	/**
	 * Tests if game is running
	 * @return True if game is running
	 */
	public static boolean isGame_running()
	{
		return game_running;
	}

	/**
	 * Pauses the game
	 */
	public static void pauseGame()
	{
		Model.game_running = false;
		Model.random_events.pauseThread();
		Model.agents_movenent.pauseThread();
		Model.animation.pauseThread();
	}
	
	/**
	 * Resumes the game
	 */
	public static void resumeGame()
	{
		Model.animation.resumeThread();
		Model.random_events.resumeThread();
		Model.agents_movenent.resumeThread();
		Model.game_running = true;
	}

	/**
	 * Gets agents' movement thread
	 * @return Agents' movement thread
	 */
	public static ThreadEnvironmentSteps getAgentsMovenentThread()
	{
		return agents_movenent;
	}
	
	/**
	 * Gets game's statistics
	 * @return Game's statistics
	 */
	public static Statistics  getStatistics()
	{
		return Model.statistics;
	}
	
	/**
	 * Returns true if the user is playing
	 * @return True if the user is playing
	 */
	public static boolean isUserPlaying()
	{
		return Model.user_playing;
	}
	
	/**
	 * Sets the team of the user
	 * @param team User's team
	 */
	public static void setUserPlaying(Team team)
	{
		Model.user_playing = true;
		Model.user_team = team;
	}
	
	/**
	 * Gets user's team
	 * @return User's team
	 */
	public static Team getUserTeam()
	{
		return Model.user_team;
	}

	/**
	 * Finishes current game of the experiment and creates new one if necessary
	 */
	public synchronized static void endExperiment()
	{
        // Destroys world
    	Model.destroyWorld();
    	
    	// Displays experiment is being saved screen
		Model.getView().showExperimentsSaving();
    	
    	switch(Model.getConfiguration().getExperiment())
    	{
		case TimeComplexity:
			Model.nextTimeComplexityExperiment();
			break;
			
		case Resources:
			Model.nextResourcesExperiment();
			break;
			
		case DeathMatch:
			Model.nextDeathMatchExperiment();
			break;
			
		default:
			break;
    	}
	}
	
	/**
	 * Creates next death match experiment
	 */
	private static void nextDeathMatchExperiment()
	{
		Model.getStatistics().saveExperiment("Death Match",Model.getConfiguration().getAI(EnumTeams.a).toString() + " and "+ Model.getConfiguration().getAI(EnumTeams.b).toString(), Model.getConfiguration().getScenario().toString(), Integer.toString(Model.getConfiguration().getAgent_count_a()) + " and " + Integer.toString(Model.getConfiguration().getAgent_count_b()) + " ants");
		
		if (Model.experiments_with_current_settings >= Model.getConfiguration().experiment_count())
		{
			Model.getConfiguration().setAgent_count_a(Model.getConfiguration().getAgent_count_a()+1);
			
			if (Model.getConfiguration().getAgent_count_a() > 9)
			{
				if (Model.getConfiguration().getAgent_count_b() >= 9)
				{
					if (Model.getConfiguration().getAI(EnumTeams.a) == AIDatabase.EnumAI.basic &&
						Model.getConfiguration().getAI(EnumTeams.b) == AIDatabase.EnumAI.cooperative)
					{
						Model.getConfiguration().reInit();
						Model.getConfiguration().setAgent_count_a(1);
						Model.getConfiguration().setAgent_count_b(1);
						Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.basic);
						Model.getConfiguration().addAI(EnumTeams.b, AIDatabase.EnumAI.advanced);
						Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
						Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_2);
					}
					else if (Model.getConfiguration().getAI(EnumTeams.a) == AIDatabase.EnumAI.basic &&
							 Model.getConfiguration().getAI(EnumTeams.b) == AIDatabase.EnumAI.advanced)
					{
						Model.getConfiguration().reInit();
						Model.getConfiguration().setAgent_count_a(1);
						Model.getConfiguration().setAgent_count_b(1);
						Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.cooperative);
						Model.getConfiguration().addAI(EnumTeams.b, AIDatabase.EnumAI.advanced);
						Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
						Model.getConfiguration().addTeamToAlliance(EnumTeams.b, EnumAlliances.Alliance_2);
					}
					else
					{
						Model.getView().showMenu();
						return;
					}
				}
				
				else
				{
					Model.getConfiguration().setAgent_count_a(1);
					Model.getConfiguration().setAgent_count_b(Model.getConfiguration().getAgent_count_b()+1);
				}
			}
				
			Model.experiments_with_current_settings = 0;
		}
		(new ThreadStartGame()).start();
		Model.experiments_with_current_settings++;
	}

	/**
	 * Creates next resources experiment
	 */
	private static void nextResourcesExperiment()
	{
		Model.getStatistics().saveExperiment("Resources", Model.getConfiguration().getAI(EnumTeams.a).toString() + "AI", Model.getConfiguration().getScenario().toString(), Integer.toString(Model.getConfiguration().getAgent_count_a()) + " ants");
		
		if (Model.experiments_with_current_settings >= Model.getConfiguration().experiment_count())
		{
			Model.getConfiguration().setAgent_count_a(Model.getConfiguration().getAgent_count_a()+1);
			
			if (Model.getConfiguration().getAgent_count_a() >= 10)
			{
				if (Model.getConfiguration().getAI(EnumTeams.a) == AIDatabase.EnumAI.basic)
				{
					Model.getConfiguration().reInit();
					Model.getConfiguration().setAgent_count_a(1);
					Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.cooperative);
					Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
				}
				else if (Model.getConfiguration().getAI(EnumTeams.a) == AIDatabase.EnumAI.cooperative)
				{
					Model.getConfiguration().reInit();
					Model.getConfiguration().setAgent_count_a(1);
					Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.advanced);
					Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
				}
				else
				{
					if (Model.getConfiguration().getScenario() == Worlds.ResourceExperiment1)
					{
						Model.getConfiguration().reInit();
						Model.getConfiguration().setAgent_count_a(1);
						Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.basic);
						Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
						Model.getConfiguration().setScenario(Worlds.ResourceExperiment2);
					}
					else if (Model.getConfiguration().getScenario() == Worlds.ResourceExperiment2)
					{
						Model.getConfiguration().reInit();
						Model.getConfiguration().setAgent_count_a(1);
						Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.basic);
						Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
						Model.getConfiguration().setScenario(Worlds.ResourceExperiment3);
					}
					else
					{
						Model.getView().showMenu();
						return;
					}
				}
			}
				
			Model.experiments_with_current_settings = 0;
		}
		(new ThreadStartGame()).start();
		Model.experiments_with_current_settings++;
		
	}

	/**
	 * Creates next time complexity experiment
	 */
	private static void nextTimeComplexityExperiment()
	{
		Model.getStatistics().saveExperiment("Time Complexity",
				Model.getConfiguration().getScenario().toString(),
				Model.getConfiguration().getAI(EnumTeams.a) + "_" + Model.getConfiguration().getAI(EnumTeams.b) + "_" + Model.getConfiguration().getAI(EnumTeams.c),
				Integer.toString(Model.getConfiguration().getAgent_count_a()) + " ants");
		
		if (Model.experiments_with_current_settings >= Model.getConfiguration().experiment_count())
		{
			Model.getConfiguration().setAgent_count_a(Model.getConfiguration().getAgent_count_a()+1);
			Model.getConfiguration().setAgent_count_b(Model.getConfiguration().getAgent_count_b()+1);
			Model.getConfiguration().setAgent_count_c(Model.getConfiguration().getAgent_count_c()+1);
			
			if (Model.getConfiguration().getAgent_count_a() > 9)
			{
				if (Model.getConfiguration().getAI(EnumTeams.a) == AIDatabase.EnumAI.basic &&
					Model.getConfiguration().getAI(EnumTeams.b) == AIDatabase.EnumAI.cooperative &&
					Model.getConfiguration().getAI(EnumTeams.c) == AIDatabase.EnumAI.advanced)
				{
					Model.getConfiguration().reInit();
					Model.getConfiguration().setAgent_count_a(1);
					Model.getConfiguration().setAgent_count_b(1);
					Model.getConfiguration().setAgent_count_c(1);
					Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.advanced);
					Model.getConfiguration().addAI(EnumTeams.b, AIDatabase.EnumAI.basic);
					Model.getConfiguration().addAI(EnumTeams.c, AIDatabase.EnumAI.cooperative);
					Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
					Model.getConfiguration().addTeamToAlliance(EnumTeams.b, EnumAlliances.Alliance_2);
					Model.getConfiguration().addTeamToAlliance(EnumTeams.c, EnumAlliances.Alliance_3);
				}
				else if (Model.getConfiguration().getAI(EnumTeams.a) == AIDatabase.EnumAI.advanced &&
						 Model.getConfiguration().getAI(EnumTeams.b) == AIDatabase.EnumAI.basic &&
						 Model.getConfiguration().getAI(EnumTeams.c) == AIDatabase.EnumAI.cooperative)
				{
					Model.getConfiguration().reInit();
					Model.getConfiguration().setAgent_count_a(1);
					Model.getConfiguration().setAgent_count_b(1);
					Model.getConfiguration().setAgent_count_c(1);
					Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.cooperative);
					Model.getConfiguration().addAI(EnumTeams.b, AIDatabase.EnumAI.advanced);
					Model.getConfiguration().addAI(EnumTeams.c, AIDatabase.EnumAI.basic);
					Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
					Model.getConfiguration().addTeamToAlliance(EnumTeams.b, EnumAlliances.Alliance_2);
					Model.getConfiguration().addTeamToAlliance(EnumTeams.c, EnumAlliances.Alliance_3);
				}
				else
				{
					Model.getView().showMenu();
					return;
				}
			}
				
			Model.experiments_with_current_settings = 0;
		}
		(new ThreadStartGame()).start();
		Model.experiments_with_current_settings++;
	}

	/**
	 * Creates next performance experiment
	 */
	public static void nextPerformanceExperiment()
	{
		Model.destroyWorld();
		
		Model.getConfiguration().setAgent_count_a(Model.getConfiguration().getAgent_count_a()+1);
		Model.getConfiguration().setAgent_count_b(Model.getConfiguration().getAgent_count_b()+1);
		Model.getConfiguration().setAgent_count_c(Model.getConfiguration().getAgent_count_c()+1);
		
    	if (Model.getConfiguration().getAgent_count_a() > 9)
    	{
    		if (Model.getConfiguration().isNoGui())
    		{
    			Model.getConfiguration().setNoGui(false);
    			Model.getConfiguration().setFog(false);
    			Model.getConfiguration().setFps(30);
    		}
    		
    		else if (!Model.getConfiguration().drawFog())
    		{
    			Model.getConfiguration().setNoGui(false);
    			Model.getConfiguration().setFog(true);
    			Model.getConfiguration().setFps(30);
    		}
    		
    		else
    		{
    			Model.getView().showMenu();
				return;
    		}
    		
			Model.getConfiguration().setAgent_count_a(1);
			Model.getConfiguration().setAgent_count_b(1);
			Model.getConfiguration().setAgent_count_c(1);
    	}
    	(new ThreadStartGame()).start();
	}
	
	/**
	 * Creates previous performance experiment
	 */
	public static void previousPerformanceExperiment()
	{
		Model.destroyWorld();
		
		Model.getConfiguration().setAgent_count_a(Model.getConfiguration().getAgent_count_a()-1);
		Model.getConfiguration().setAgent_count_b(Model.getConfiguration().getAgent_count_b()-1);
		Model.getConfiguration().setAgent_count_c(Model.getConfiguration().getAgent_count_c()-1);
		
    	if (Model.getConfiguration().getAgent_count_a() < 1)
    	{
    		if (Model.getConfiguration().drawFog())
    		{
    			Model.getConfiguration().setNoGui(false);
    			Model.getConfiguration().setFog(false);
    			Model.getConfiguration().setFps(30);
    		}
    		
    		else if (!Model.getConfiguration().isNoGui())
    		{
    			Model.getConfiguration().setNoGui(true);
    			Model.getConfiguration().setFog(false);
    			Model.getConfiguration().setFps(1);
    		}
    		
    		else
    		{
    			Model.getView().showMenu();
				return;
    		}
    		
			Model.getConfiguration().setAgent_count_a(9);
			Model.getConfiguration().setAgent_count_b(9);
			Model.getConfiguration().setAgent_count_c(9);
    	}
    	(new ThreadStartGame()).start();
	}
	
	/**
	 * Creates next performance experiment 2
	 */
	public static void nextPerformanceExperiment2()
	{
		Model.destroyWorld();
		
		Model.getConfiguration().setWorldSize(Model.getConfiguration().getWorldSize() + 200);
		
		Model.getConfiguration().setAgent_count_a(Model.getConfiguration().getAgent_count_a()+1);
		Model.getConfiguration().setAgent_count_b(Model.getConfiguration().getAgent_count_b()+1);
		Model.getConfiguration().setAgent_count_c(Model.getConfiguration().getAgent_count_c()+1);
		Model.getConfiguration().setAgent_count_d(Model.getConfiguration().getAgent_count_d()+1);
		
    	if (Model.getConfiguration().getWorldSize() > 3000)
    	{
    		if (!Model.getConfiguration().isNoGui())
    		{
    			Model.getConfiguration().setNoGui(false);
    			Model.getConfiguration().setFog(false);
    			Model.getConfiguration().setFps(1);
    		}
    		
    		else
    		{
    			Model.getView().showMenu();
				return;
    		}
    		
			Model.getConfiguration().setAgent_count_a(2);
			Model.getConfiguration().setAgent_count_b(2);
			Model.getConfiguration().setAgent_count_c(2);
			Model.getConfiguration().setAgent_count_d(2);
			Model.getConfiguration().setWorldSize(400);
    	}
    	(new ThreadStartGame()).start();
	}
	
	/**
	 * Creates previous performance experiment 2
	 */
	public static void previousPerformanceExperiment2()
	{
		Model.destroyWorld();

		Model.getConfiguration().setWorldSize(Model.getConfiguration().getWorldSize() + 200);
		
		Model.getConfiguration().setAgent_count_a(Model.getConfiguration().getAgent_count_a()-1);
		Model.getConfiguration().setAgent_count_b(Model.getConfiguration().getAgent_count_b()-1);
		Model.getConfiguration().setAgent_count_c(Model.getConfiguration().getAgent_count_c()-1);
		Model.getConfiguration().setAgent_count_d(Model.getConfiguration().getAgent_count_d()-1);
		
    	if (Model.getConfiguration().getWorldSize() < 600)
    	{
    		if (Model.getConfiguration().isNoGui())
    		{
    			Model.getConfiguration().setNoGui(false);
    			Model.getConfiguration().setFog(false);
    			Model.getConfiguration().setFps(30);
    		}
    		
    		else
    		{
    			Model.getView().showMenu();
				return;
    		}
    		
			Model.getConfiguration().setAgent_count_a(15);
			Model.getConfiguration().setAgent_count_b(15);
			Model.getConfiguration().setAgent_count_c(15);
			Model.getConfiguration().setAgent_count_d(15);
			Model.getConfiguration().setWorldSize(3000);
    	}
    	(new ThreadStartGame()).start();
	}

	/**
	 * Resets counter of finished experiments
	 */
	public static void resetFinishedExperimentsCounter()
	{
		Model.experiments_with_current_settings = 1;
	}
	
	/**
	 * Sets that the game ended
	 */
	public static void setGameEnd()
	{
		Model.game_ended = true;
	}
	
	/**
	 * Returns true if game ended
	 * @return True if game ended
	 */
	public static boolean isGameEnd()
	{
		return Model.game_ended;
	}

	/**
	 * Returns true if user was already notified about the end of the game
	 * @return True if user was notified about the end of the game
	 */
	public static boolean isUserNotified()
	{
		return Model.user_notified;
	}
	
	/**
	 * Sets that user was already notified about the end of the game
	 */
	public static void setUserNotified()
	{
		Model.user_notified = true;
	}
}
