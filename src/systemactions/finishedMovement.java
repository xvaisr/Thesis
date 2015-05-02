/**
 * BP, anthill strategy game
 * Returns true if an ant finished all it's actions
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.finishedMovement.java
 */
package systemactions;

import Enviroment.EnvObjFeatures.AgentInterface;
import Enviroment.Model;
import Enviroment.EnvObjects.Agents.Agent;
import Enviroment.EnvObjects.GameObject;
import jason.JasonException;
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
    	GameObject object = null;
        try {
            object = Model.getAgentByName(terms[0].toString());
        }
        catch (ArrayIndexOutOfBoundsException e)  {
            throw new JasonException("The internal action ’finishedMovement’"+
                "has not received it's argument!");
        }
        
        if (object == null) {
            return true;
        }
        
        Agent ag;
        if (object instanceof Agent) {
            ag  = ((Agent) object);
            return ag.getPosition().equals(ag.getDestinattion());
        }
        
        return true;
    }
}
