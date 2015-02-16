/**
 * BP, anthill strategy game
 * Gets current game time
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.getGameTime.java
 */
package actions;

import Enviroment.Model;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

/**
 * Gets current game time
 * @author Vojtech Simetka
 */
public class getGameTime extends DefaultInternalAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms)
	{
		// Decides if there is even slight chance to defeat enemy
		return un.unifies(terms[0], new NumberTermImpl(Model.getAgentsMovenentThread().getGameLength()));
	}
}
