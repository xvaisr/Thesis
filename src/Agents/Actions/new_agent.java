/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Agents.Actions;

import Enviroment.EnvObjects.Agents.AntHill;
import Enviroment.EnvObjects.GameObject;
import jason.asSyntax.Term;
import java.util.List;

/**
 *
 * @author lennylinux
 */
public class new_agent extends AgentAction {

    @Override
    public boolean execute(GameObject ag, List<Term> terms) {
        AntHill hill;
        
        if(!(ag instanceof AntHill)) {
            return false;
        }
        
        hill = ((AntHill) ag);
        return hill.createAnt();
    }
    
}
