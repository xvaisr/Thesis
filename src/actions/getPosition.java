/**
 * BP, anthill strategy game
 * Gets position of an ant in the first argument
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.getPosition.java
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
 * Gets position of an ant in the first argument
 * @author Vojtech Simetka
 */
public class getPosition  extends DefaultInternalAction
{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms)
	{	
		AgentInfo agent = Model.getEnvironment().agent2info(terms[0].toString());
		
		if (agent == null)
			return false;
		
		return un.unifies(terms[1], new  NumberTermImpl(agent.getIntPositionX())) &&
			   un.unifies(terms[2], new  NumberTermImpl(agent.getIntPositionY()));
	}
}
