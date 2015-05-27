/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Agents.Actions;

import Enviroment.EnvObjects.GameObject;
import jason.asSyntax.Term;
import java.util.List;

/**
 *
 * @author lennylinux
 */
public abstract class AgentAction {
    
    public String getActionName() {
        return this.getClass().getSimpleName();
    }
    
    public abstract boolean execute(GameObject ag, List<Term> terms);


    
}
