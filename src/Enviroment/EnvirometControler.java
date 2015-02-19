/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment;

import jason.asSyntax.Structure;

/**
 *
 * @author lennylinux
 */
public class EnvirometControler extends jason.environment.TimeSteppedEnvironment {

    public EnvirometControler() {
    }
    
/* Overriden Super Class methods */
    
    @Override
    public void init(java.lang.String[] args) {
        
    }
    
    @Override
    public boolean executeAction(String agent_name, Structure action) {
        return false;
    }
       
    @Override
    protected void updateAgsPercept() {
        
    }
    
    
    
/*    @Override
    protected void stepFinished(int step, long time, boolean timeout) {
    
    } */
    
/* Own aditional methods */    
    
    
}
