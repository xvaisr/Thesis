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
import java.awt.Point;
import java.util.List;

public class crawl extends AgentAction {

    @Override
    public boolean execute(GameObject ag, List<Term> terms) {
        SimpleAnt ant;
        
        if (!(ag instanceof SimpleAnt)) {
            return false;
        }
        if (terms.size() < 2) {
            return false;
        }
        ant = ((SimpleAnt) ag);
        
        int x = Integer.parseInt(terms.get(0).toString());
        int y = Integer.parseInt(terms.get(1).toString());
        ant.setDestination(new Point(x, y));
        
        return true;
    }
    
}
