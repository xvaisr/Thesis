/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package systemactions;

import Enviroment.EnvObjects.GameObject;
import Enviroment.Model;
import jason.JasonException;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;
import java.awt.Point;
import systemactions.SystemAction;

/**
 * Gets position of an ant in the first argument
 * @author Vojtech Simetka
 */
public class getPosition extends SystemAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception
    {
    	GameObject object = null;
        try {
            object = Model.getAgentByName(terms[0].toString());
        }
        catch (ArrayIndexOutOfBoundsException e)  {
            throw new JasonException("The internal action ’getPosition’"+
                "has not received it's argument!");
        }
        
        if (object == null) {
            return false;
        }
        
        Point p = object.getPosition();
            
        boolean unifiedX, unifiedY;
        unifiedX = un.unifies(terms[1], new  NumberTermImpl(p.x));
        unifiedY = un.unifies(terms[2], new  NumberTermImpl(p.y));
        return unifiedX && unifiedY;
    }
}
