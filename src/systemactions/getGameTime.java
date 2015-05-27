/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package systemactions;

import Enviroment.Model;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

/**
 * Gets current game time
 * @author Vojtech Simetka
 */
public class getGameTime extends SystemAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms)
	{
		// I don't have slightiest idea why this internal action was implemented in original code ...
		return un.unifies(terms[0], new NumberTermImpl(System.currentTimeMillis() - Model.getGameStart()));
	}
}
