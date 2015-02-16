/**
 * BP, anthill strategy game
 * Calculates and returns air distance between two point on the map
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.calculateDistance.java
 */
package actions;

import Agents.AgentInfo;
import Enviroment.Model;
import graphic.Team;
import jason.NoValueException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

/**
 * Calculates and returns air distance between two point on the map
 * @author Vojtech Simetka
 *
 */
public class calculateDistance extends DefaultInternalAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws NoValueException
	{	
            
            int ax, ay, x, y;
            try {
		// Gets information about both agents
		x = (int)((NumberTerm) terms[0]).solve();
		y = (int)((NumberTerm) terms[1]).solve();
		ax = (int)((NumberTerm) terms[2]).solve();
		ay = (int)((NumberTerm) terms[3]).solve();
		
            }
            catch(jason.NoValueException e) {
                throw (e);
            }
		
		int dx = ax - x;
		int dy = ay - y;
		
		// Decides if there is even slight chance to defeat enemy
		return un.unifies(terms[4], new NumberTermImpl(dx*dx + dy*dy));
	}
}
