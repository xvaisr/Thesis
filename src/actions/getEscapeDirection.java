/**
 * BP, anthill strategy game
 * Gets escape direction from the enemy
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.getEscapeDirection.java
 */
package actions;

import Agents.AgentInfo;
import Enviroment.Model;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

/**
 * Gets escape direction from the enemy
 * @author Vojtech Simetka
 */
public class getEscapeDirection extends DefaultInternalAction
{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms)
	{	
		AgentInfo agent = Model.getEnvironment().agent2info(terms[0].toString());
		int agentx = agent.getIntPositionX();
		int agenty = agent.getIntPositionY();
		
		AgentInfo enemy = Model.getEnvironment().agent2info(terms[1].toString());
		if (enemy == null)
			return un.unifies(terms[2], new  NumberTermImpl(agent.getIntPositionX())) &&
				   un.unifies(terms[3], new  NumberTermImpl(agent.getIntPositionY()));
		
		int enemyx = enemy.getIntPositionX();
		int enemyy = enemy.getIntPositionY();
		
		int suggestedx = agentx + (agentx - enemyx);
		int suggestedy = agenty + (agenty - enemyy);

		if (suggestedx > Model.getWorld().getWidth()-1)
			return un.unifies(terms[2], new  NumberTermImpl(Model.getWorld().getAnthill(agent.getTeam()).x)) &&
				   un.unifies(terms[3], new  NumberTermImpl(Model.getWorld().getAnthill(agent.getTeam()).y));
		
		else if (suggestedx < 0)
			return un.unifies(terms[2], new  NumberTermImpl(Model.getWorld().getAnthill(agent.getTeam()).x)) &&
				   un.unifies(terms[3], new  NumberTermImpl(Model.getWorld().getAnthill(agent.getTeam()).y));
		
		if (suggestedy > Model.getWorld().getHeight()-1)
			return un.unifies(terms[2], new  NumberTermImpl(Model.getWorld().getAnthill(agent.getTeam()).x)) &&
				   un.unifies(terms[3], new  NumberTermImpl(Model.getWorld().getAnthill(agent.getTeam()).y));
		else if (suggestedy < 0)
			return un.unifies(terms[2], new  NumberTermImpl(Model.getWorld().getAnthill(agent.getTeam()).x)) &&
				   un.unifies(terms[3], new  NumberTermImpl(Model.getWorld().getAnthill(agent.getTeam()).y));
		
		if (Model.getWorld().isFree(suggestedx, suggestedy))
			return un.unifies(terms[2], new  NumberTermImpl(suggestedx)) &&
				   un.unifies(terms[3], new  NumberTermImpl(suggestedy));
		
		return un.unifies(terms[2], new  NumberTermImpl(Model.getWorld().getAnthill(agent.getTeam()).x)) &&
			   un.unifies(terms[3], new  NumberTermImpl(Model.getWorld().getAnthill(agent.getTeam()).y));
	}
}
