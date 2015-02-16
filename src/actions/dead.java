/**
 * BP, anthill strategy game
 * Returns true if an ant is dead
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.dead.java
 */
package actions;

import Enviroment.Model;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

/**
 * Returns true if an ant is dead
 * @author Vojtech Simetka
 */
public class dead extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception
    {
    	return Model.getEnvironment().agent2info(terms[0].toString()) == null;
    }
}
