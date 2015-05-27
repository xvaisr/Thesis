/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package systemactions;

import Enviroment.EnvObjFeatures.AgentInterface;
import Enviroment.EnvObjects.GameObject;
import Enviroment.Model;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

/**
 * Internal action that notes agent's reasoning action
 * @author Vojtech Simetka
 *
 */
public class addReasoningAction extends SystemAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) {
        
        
        GameObject object = Model.getAgentByName(ts.getUserAgArch().getAgName());
        
        if (object == null) {
            return false;
        }
        
        AgentInterface ag;
        if (object instanceof AgentInterface) {
            ag  = ((AgentInterface) object);
        }
        else {
            return false;
        }
        
        // add reasoning action to team statistics;
            // ....
        
	return true;
    }
}
