/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Enviroment;

import Enviroment.EnvObjects.Agents.Agent;
import Agents.Team.Team;
import jason.environment.Environment; // TimeSteppedEnvironment;
import Agents.Actions.AgentAction;
import Enviroment.EnvObjects.GameObject;
import GraphicInterface.UserInterface;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author lennylinux
 */
public class EnvirometControler extends Environment {
    
    static Logger logger = Logger.getLogger(EnvirometControler.class.getName());
    
    private HashMap<String, AgentAction> actions;
    private List<String> architecture = new ArrayList<String>();
    
    public EnvirometControler() {
    }
    
/* Overriden Super Class methods */
    
    @Override
    public void init(String[] args) {
        // this is supposed to be required
        this.architecture.add("jason.architecture.AgArch");
        
        // attach this to a Model
        Model.getInstance().setEnviromentalControler(this);
        
        // init actions map
        this.actions = new HashMap();
        this.initActions();
        
        // first setup teams
        Model.addTeam(new Team(Color.red));
        
        // after teams are set up, map can be created
        Model.setNewMap(2, 2);
        // start up user unterface
        UserInterface ui = UserInterface.getInstance();
        // MenuInterfacce ui = MenuInterfacce.getInstance();
        Thread t = new Thread(ui);
        t.start();
    }
    
    @Override
    public boolean executeAction(String agent_name, Structure action) {
        String msg = agent_name + " doing: " + action.getFunctor();
        logger.info(msg);
        GameObject ag = Model.getAgentByName(agent_name);
        AgentAction ac = this.actions.get(action.getFunctor());
        if (ac != null) {
            return ac.execute(ag, action.getTerms());
        }
        
        System.err.println("Doimplementuj / dolinkuj akci: " + action.toString());
        return true;
    }
       
    public boolean addAgent(Agent ag) {
        try {
            String ai;
            ai = ag.getTeam().getAntAiPath();
            System.err.println(ai);
            ai = this.getClass().getResource(ai).toExternalForm();
            System.err.println(ai);
            
            this.getEnvironmentInfraTier().getRuntimeServices()
                .createAgent(ag.getName(), ai, null, this.architecture, null, null);			
        }    
	catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    
    
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
