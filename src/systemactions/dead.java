/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package systemactions;

import Enviroment.EnvObjects.Agents.Agent;
import Enviroment.EnvObjects.GameObject;
import Enviroment.Model;
import jason.JasonException;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

/**
 * Returns true if an ant is dead
 * @author Vojtech Simetka
 */
public class dead extends SystemAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms)
                        throws Exception 
    {
        GameObject object = null;
        try {
            object = Model.getAgentByName(terms[0].toString());
        }
        catch (ArrayIndexOutOfBoundsException e)  {
            throw new JasonException("The internal action ’dead’"+
                "has not received it's argument!");
        }
        
        if (object == null) {
            return true;
        }
        
        Agent ag;
        if (object instanceof Agent) {
            ag  = ((Agent) object);
            return ag.isDestroied();
        }
        
        return true;
    }
}
