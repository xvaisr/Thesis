/**
 * BP, anthill strategy game
 * Gets current game time
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.getGameTime.java
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
