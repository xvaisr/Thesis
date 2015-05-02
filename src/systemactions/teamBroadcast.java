/**
 * BP, anthill strategy game
 * Sends message to all team ants of the ant
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.teamBroadcast.java
 */
package systemactions;

import Enviroment.EnvObjects.Agents.Agent;
import Enviroment.EnvObjects.GameObject;
import Enviroment.Model;
import jason.asSemantics.Message;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;
import java.util.ArrayList;

/**
 * Sends message to all team ants of the ant
 * @author Vojtech Simetka
 */
public class teamBroadcast extends SystemAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
    	Term ilf  = terms[0]; //  illocutionary force
        Term pcnt = terms[1]; // proposal content
        
        GameObject object = Model.getAgentByName(ts.getUserAgArch().getAgName());
        
        if (object == null) {
            return false;
        }
        
        // do we have a sender ?
        Agent ag;
        if (object instanceof Agent) {
            ag  = ((Agent) object);
        }
        else { // if not, fail ...
            return false;
        }
        
        // put message togethert
        Message m = new Message(ilf.toString(), ts.getUserAgArch().getAgName(), null, pcnt);
        
        // send it to all members of the team;
        ArrayList<Agent> teamMembers = ag.getTeam().getMemberList();
        for (Agent agent : teamMembers) {
            m.setReceiver(agent.getName());
            ts.getUserAgArch().sendMsg(m);
        }
        
        return true;
    }
}
