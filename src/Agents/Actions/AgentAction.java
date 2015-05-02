/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
