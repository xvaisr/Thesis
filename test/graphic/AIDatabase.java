/**
 * BP, anthill strategy game
 * The AI database responsible for getting correct ASL source file
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/05/10
 * @version 1
 * @file    graphic.AIDatabase.java
 */

package graphic;

import Enviroment.Original.Model;

/**
 * The AI database responsible for getting correct ASL source file
 * @author Vojtech Simetka
 *
 */
public class AIDatabase
{
	/**
	 * Artifficial intelligences enumerator
	 */
	public enum EnumAI
	{
		basic,
		cooperative,
		advanced,
		blank,
		user,
		user_and_basic,
		user_and_cooperative,
		user_and_advanced,
		user_and_blank,
	}

	/**
	 * Used for determination if the AI and user controls one anthill together
	 * @param ai AI to be decoded
	 * @return True if the AI and user controls one anthill together
	 */
	public static boolean isUserAI(AIDatabase.EnumAI ai)
	{
		return ai == AIDatabase.EnumAI.user ||
			   ai == AIDatabase.EnumAI.user_and_advanced ||
			   ai == AIDatabase.EnumAI.user_and_basic ||
			   ai == AIDatabase.EnumAI.user_and_cooperative ||
			   ai == AIDatabase.EnumAI.user_and_blank;
	}

	/**
	 * Gets ASL anthill source file for the team
	 * @param team Team for which we want to know the ASL source file
	 * @return ASL anthill source file for the team
	 */
	public static String getAnthillAI(Team team)
	{
		switch (team.getAI())
		{
		// User and advanced AI
		case user_and_advanced:
			Model.setUserPlaying(team);
			
		// Advanced AI
		case advanced:
			return Model.getEnvironment().getClass().getResource("/src/asl/advanced_anthill.asl").toExternalForm();
		
		// Basic AI
		case basic:
			return Model.getEnvironment().getClass().getResource("/src/asl/basic_anthill.asl").toExternalForm();

		// Cooperative AI
		case cooperative:
			return Model.getEnvironment().getClass().getResource("/src/asl/cooperative_anthill.asl").toExternalForm();

		// Blank AI
		case blank:
			return Model.getEnvironment().getClass().getResource("/src/asl/blank_anthill.asl").toExternalForm();
			
		// Sets that user is playing
		default:
			Model.setUserPlaying(team);
			return null;
		}
	}
	
	/**
	 * Gets ASL ant source file for the team
	 * @param team Team for which we want to know the ASL source file
	 * @return ASL ant source file for the team
	 */
	public static String getAntAI(EnumTeams team)
	{
		switch (Model.getWorld().getTeam(team).getAI())
		{
		case advanced:
		case user_and_advanced:
			return Model.getEnvironment().getClass().getResource("/src/asl/advancedAI.asl").toExternalForm();
	
		case basic:
		case user_and_basic:
			return Model.getEnvironment().getClass().getResource("/src/asl/basicAI.asl").toExternalForm();
	
		case cooperative:
		case user_and_cooperative:
			return Model.getEnvironment().getClass().getResource("/src/asl/cooperativeAI.asl").toExternalForm();

		case blank:
		case user_and_blank:
			return Model.getEnvironment().getClass().getResource("/src/asl/blankAI.asl").toExternalForm();
		default:
			return null;
		}
	}
	
	/**
	 * Gets ASL anthill source file for the team with time complexity internal actions
	 * @param team Team for which we want to know the ASL source file
	 * @return ASL anthill source file for the team
	 */
	public static String getTimeComplexityAnthillAI(Team team)
	{
		switch (team.getAI())
		{
		// Advanced AI
		case advanced:
			return Model.getEnvironment().getClass().getResource("/src/asl/advanced_anthill_apm.asl").toExternalForm();
			
		// Basic AI
		case basic:
			return Model.getEnvironment().getClass().getResource("/src/asl/basic_anthill_apm.asl").toExternalForm();
			
		// Cooperative AI
		case cooperative:
			return Model.getEnvironment().getClass().getResource("/src/asl/cooperative_anthill_apm.asl").toExternalForm();
			

		// Blank AI
		case blank:
			return Model.getEnvironment().getClass().getResource("/src/asl/blank_anthill_apm.asl").toExternalForm();
			
		// Sets that the user is playing
		default:
			Model.setUserPlaying(team);
			return null;
		}
	}

	/**
	 * Gets ASL ant source file for the team with time complexity internal actions
	 * @param team Team for which we want to know the ASL source file
	 * @return ASL ant source file for the team
	 */
	public static String getTimeComplexityAntAI(EnumTeams team)
	{
		switch (Model.getWorld().getTeam(team).getAI())
		{
		case advanced:
			return Model.getEnvironment().getClass().getResource("/src/asl/advancedAI_apm.asl").toExternalForm();
	
		case basic:
			return Model.getEnvironment().getClass().getResource("/src/asl/basicAI_apm.asl").toExternalForm();
			
		case cooperative:
			return Model.getEnvironment().getClass().getResource("/src/asl/cooperativeAI_apm.asl").toExternalForm();

		case blank:
		case user_and_blank:
			return Model.getEnvironment().getClass().getResource("/src/asl/blankAI_apm.asl").toExternalForm();
			
		default:
			return null;
		}
	}
}
