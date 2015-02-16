/**
 * BP, anthill strategy game
 * Sends message to all team ants of the ant
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.teamBroadcast.java
 */
package actions;

import Agents.AgentInfo;
import graphic.EnumTeams;
import Enviroment.Model;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.Message;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

/**
 * Sends message to all team ants of the ant
 * @author Vojtech Simetka
 */
public class teamBroadcast extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
    	Term ilf  = terms[0];
        Term pcnt = terms[1];
        
        EnumTeams team;
       
        AgentInfo info = Model.getEnvironment().agent2info(ts.getUserAgArch().getAgName());
        if (info == null)
        	team = Model.getWorld().getTeam(ts.getUserAgArch().getAgName()).getTeam();
        else
        	team = info.getTeam();
        Message m = new Message(ilf.toString(), ts.getUserAgArch().getAgName(), null, pcnt);
    	
        switch(team)
        {
		case a:
			synchronized(Model.getEnvironment().getActive_agentsA())
			{
				for (AgentInfo agent_name: Model.getEnvironment().getActive_agentsA())
				{
					if (info == agent_name)
						continue;
					m.setReceiver(agent_name.getName());
			        ts.getUserAgArch().sendMsg(m);
				}
			}
			break;
		case b:
			synchronized(Model.getEnvironment().getActive_agentsB())
			{
				for (AgentInfo agent_name: Model.getEnvironment().getActive_agentsB())
				{
					if (info == agent_name)
						continue;
					m.setReceiver(agent_name.getName());
			        ts.getUserAgArch().sendMsg(m);
				}
			}
			break;
		case c:
			synchronized(Model.getEnvironment().getActive_agentsC())
			{
				for (AgentInfo agent_name: Model.getEnvironment().getActive_agentsC())
				{
					if (info == agent_name)
						continue;
					m.setReceiver(agent_name.getName());
			        ts.getUserAgArch().sendMsg(m);
				}
			}
			break;
		case d:
			synchronized(Model.getEnvironment().getActive_agentsD())
			{
				for (AgentInfo agent_name: Model.getEnvironment().getActive_agentsD())
				{
					if (info == agent_name)
						continue;
					m.setReceiver(agent_name.getName());
			        ts.getUserAgArch().sendMsg(m);
				}
			}
			break;
			
		default:
			break;
        
        }
        
        return true;
    }
}
