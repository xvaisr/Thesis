/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class upgrade_attack extends AgentAction {

    @Override
    public boolean execute(GameObject ag, List<Term> terms) {
        AntHill hill;
        
        if(!(ag instanceof AntHill)) {
            return false;
        }
        
        hill = ((AntHill) ag);
        return hill.upgradeAttack();
    }
    
}
