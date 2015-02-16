/**
 * BP, anthill strategy game
 * Returns true if an ant finished all it's actions
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.finishedMovement.java
 */
package actions;

import Agents.AgentInfo;
import Enviroment.Model;
import jason.asSemantics.*;
import jason.asSyntax.*;

/**
 * Returns true if an ant finished all it's actions
 * @author Vojtech Simetka
 */
public class finishedMovement extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception
    {
    	AgentInfo agent = Model.getEnvironment().agent2info(terms[0].toString());
    	
        if (agent != null && agent.finished())
        	return true;
        
        return false;
    }
}
