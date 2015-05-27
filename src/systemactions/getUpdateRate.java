/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package systemactions;

import jason.asSemantics.*;
import jason.asSyntax.*;

/**
 * Gets perceptual update rate for the ant
 * @author Vojtech Simetka
 */
public class getUpdateRate extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception
    {
        // If I am gonna need it, I will change it // set for 16 miliseconds
    	return un.unifies(terms[0], new NumberTermImpl(16));
    }
}
