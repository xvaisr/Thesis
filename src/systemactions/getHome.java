/**
 * BP, anthill strategy game
 * Gets coordinates of the ant's anthill
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.getHome.java
 */
package systemactions;

import Enviroment.EnvObjects.Agents.Agent;
import Enviroment.EnvObjects.GameObject;
import Enviroment.Model;
import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;
import java.awt.Point;

/**
 * Gets coordinates of the ant's anthill
 * @author Vojtech Simetka
 */
public class getHome extends SystemAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception
    {
    	GameObject object = null;
        try {
            object = Model.getAgentByName(terms[0].toString());
        }
        catch (ArrayIndexOutOfBoundsException e)  {
            throw new JasonException("The internal action ’getHome’"+
                "has not received it's argument!");
        }
        
        if (object == null) {
            return false;
        }
        
        Point homeHill;
        if (object instanceof Agent) {
            homeHill = ((Agent) object).getTeam().getHill().getPosition();
            
            boolean unifiedX, unifiedY;
            unifiedX = un.unifies(terms[1], new  NumberTermImpl(homeHill.x));
            unifiedY = un.unifies(terms[2], new  NumberTermImpl(homeHill.y));
            return unifiedX && unifiedY;
        }
        
    	return false;
    }
}
