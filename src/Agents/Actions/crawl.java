/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents.Actions;

import Enviroment.EnvObjects.Agents.SimpleAnt;
import Enviroment.EnvObjects.GameObject;
import jason.asSyntax.Term;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author lennylinux
 */
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
