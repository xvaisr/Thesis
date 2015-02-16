/**
 * BP, anthill strategy game
 * Internal action that notes agent's reasoning action
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/05/01
 * @version 1
 * @file    actions.addReasoningAction.java
 */
package actions;

import Agents.AgentInfo;
import graphic.EnumTeams;
import Enviroment.Model;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

/**
 * Internal action that notes agent's reasoning action
 * @author Vojtech Simetka
 *
 */
public class addReasoningAction extends DefaultInternalAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms)
	{		
        AgentInfo info = Model.getEnvironment().agent2info(ts.getUserAgArch().getAgName());
        
        if (info != null)
        	Model.getStatistics().addReasoningAction(info.getTeam());
        
        else
        {
        	String name = ts.getUserAgArch().getAgName();
        	if (name.endsWith("a"))
            	Model.getStatistics().addReasoningAction(EnumTeams.a);
        	
        	else if (name.endsWith("b"))
            	Model.getStatistics().addReasoningAction(EnumTeams.b);

        	else if (name.endsWith("c"))
            	Model.getStatistics().addReasoningAction(EnumTeams.c);

        	else if (name.endsWith("d"))
            	Model.getStatistics().addReasoningAction(EnumTeams.d);
        }
		
		// Decides if there is even slight chance to defeat enemy
		return true;
	}
}
