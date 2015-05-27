/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Agents.Actions;

import Enviroment.EnvObjFeatures.DetectableGameObject;
import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjects.Agents.Agent;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnvObjects.Resources.ResourceBlock;
import Enviroment.Model;
// import jason.asSyntax.Literal; // needed later
import jason.asSyntax.Term;
import java.util.ArrayList;
import java.util.List;

public class update_percepts extends AgentAction {

    @Override
    public boolean execute(GameObject ag, List<Term> terms) {
        if (!(ag instanceof Agent)) {
            return false;
        }
        
        Agent agent = ((Agent) ag);
        
        ArrayList<Sense> senseList = agent.getSenseList();
        for (Sense sense : senseList) {
            this.updateAgentPreceptViaSense(agent, sense);
        }
        
        return true;
    }
    
    public void updateAgentPreceptViaSense(Agent ag, Sense s) {
        ArrayList<DetectableGameObject> objectList;
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
    
    private void addBelief(String agent_name, String string)
    {
	//this.addPercept(agent_name, Literal.parseLiteral(string+"[source(precept)]"));
    }
    
}
