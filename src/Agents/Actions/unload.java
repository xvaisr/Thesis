/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Agents.Actions;

import Enviroment.EnvObjects.Agents.SimpleAnt;
import Enviroment.EnvObjects.GameObject;
import jason.asSyntax.Term;
import java.util.List;

/**
 *
 * @author lennylinux
 */
public class unload extends AgentAction {

    @Override
    public boolean execute(GameObject ag, List<Term> terms) {
        SimpleAnt ant;
        
        if (!(ag instanceof SimpleAnt)) {
            return false;
        }
        
        ant = ((SimpleAnt) ag);
        return ant.dropItem();
    }
    
}
