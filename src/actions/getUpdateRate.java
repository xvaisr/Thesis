/**
 * BP, anthill strategy game
 * Gets perceptual update rate for the ant
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.getRandomDirection.java
 */
package actions;

import Enviroment.Model;
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
    	return un.unifies(terms[0], new NumberTermImpl(Model.getConfiguration().getPps()));
    }
}
