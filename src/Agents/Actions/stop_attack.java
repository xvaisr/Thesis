/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Agents.Actions;

import Enviroment.EnvObjFeatures.DestructableGameObject;
import Enviroment.EnvObjects.Agents.SimpleAnt;
import Enviroment.EnvObjects.GameObject;
import jason.asSyntax.Term;
import java.util.List;

/**
 *
 * @author lennylinux
 */
public class stop_attack extends AgentAction {

    @Override
    public boolean execute(GameObject ag, List<Term> terms) {
        SimpleAnt ant;
        DestructableGameObject target;
        
        if (!(ag instanceof SimpleAnt)) {
            return false;
        }
        
        ant = ((SimpleAnt) ag);
        ant.setTarget(null);
        return true;
    }
    
}
