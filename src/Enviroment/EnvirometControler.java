/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment;

import Agents.Team.Team;
import jason.environment.Environment; // TimeSteppedEnvironment;
import Enviroment.EnvObjects.Agents.*;
import Agents.Actions.AgentAction;
import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnvObjects.Resources.ResourceBlock;
import GraphicInterface.MenuInterfacce;
import GraphicInterface.UserInterface;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author lennylinux
 */
public class EnvirometControler extends Environment {
    
    private HashMap<String, AgentAction> actions;

    public EnvirometControler() {
    }
    
/* Overriden Super Class methods */
    
    @Override
    public void init(String[] args) {
        // init actions map
        this.actions = new HashMap();
        this.initActions();
        
        // setup connection to model
        Model.getInstance().setEnviromentalControler(this);
        
        // start up user unterface
        // UserInterface ui = UserInterface.getInstance();
        MenuInterfacce ui = MenuInterfacce.getInstance();
        Thread t  = new Thread(ui);
        t.start();
    }
    
    @Override
    public boolean executeAction(String agent_name, Structure action) {
        GameObject ag = Model.getAgentByName(agent_name);
        AgentAction ac = this.actions.get(action.getFunctor());
        if (ac != null) {
            return ac.execute(ag, action.getTerms());
        }
        
        System.out.println("Doimplementuj / dolinkuj akci: " + action.toString());
        return false;
    }
       
    protected void updateAgsPercept() {
        ArrayList<Team> teams = Model.getTeamList();
        for (Team team : teams) {
            ArrayList<Agent> agentList = team.getMemberList();
            for (Agent agent : agentList) {
                this.updateAgPercept(agent);
            }
        }
        
    }
    
    private void updateAgPercept(Agent ag) {
        ArrayList<Sense> senseList = ag.getSenseList();
        for (Sense sense : senseList) {
            this.updateAgentPreceptViaSense(ag, sense);
        }
    }
    
    
    public void updateAgentPreceptViaSense(Agent ag, Sense s) {
        ArrayList<GameObject> objectList;
        objectList = Model.getCurrentMap().getDetectedObjects(s);
        for (GameObject gameObject : objectList) {
            String name;
            if (gameObject instanceof Agent) {
                name = ((Agent) gameObject).getName();
            }
            else if (gameObject instanceof ResourceBlock) {
                name = ((ResourceBlock) gameObject).getResourceType().name();
            }
            name = gameObject.getClass().getSimpleName();
            this.addBelief(ag.getName(), name);
        }
    }
    
/*    @Override
    protected void stepFinished(int step, long time, boolean timeout) {
    
    } */
    
/* Own aditional methods */    
    
    /**
     * Adds belief to agents belief base
     * @param agent_name Neme of the agent
     * @param string Belief to be added
     */
    private void addBelief(String agent_name, String string)
    {
	this.addPercept(agent_name, Literal.parseLiteral(string+"[source(self)]"));
    }

    private void initActions() {
        // this.actions.put(null, null);
    }
    
    
}
