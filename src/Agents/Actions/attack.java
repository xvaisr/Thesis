/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents.Actions;

import Enviroment.EnvObjFeatures.DestructableGameObject;
import Enviroment.EnvObjects.Agents.SimpleAnt;
import Enviroment.EnvObjects.GameObject;
import Enviroment.Model;
import jason.asSyntax.Term;
import java.util.List;

/**
 *
 * @author lennylinux
 */
public class attack extends AgentAction {

    @Override
    public boolean execute(GameObject ag, List<Term> terms) {
        SimpleAnt ant;
        DestructableGameObject target;
        
        if (!(ag instanceof SimpleAnt)) {
            return false;
        }
        
        if (terms.isEmpty()) {
            return false;
        }
        
        ant = ((SimpleAnt) ag);
        GameObject t = Model.getAgentByName(terms.get(0).toString());
        
        if (!(t instanceof DestructableGameObject)) {
            return false;
        }
        
        target = ((DestructableGameObject) t);
        ant.setTarget(target);
        return true;
    }
    
}
