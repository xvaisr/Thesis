/**
 * BP, anthill strategy game
 * Calculates and returns air distance between two point on the map
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.calculateDistance.java
 */
package systemactions;

import jason.NoValueException;
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
public class calculateDistance extends SystemAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms)
	{		
            try {
                // Gets information about both agents
                int x = (int)((NumberTerm) terms[0]).solve();
                int y = (int)((NumberTerm) terms[1]).solve();
                int ax = (int)((NumberTerm) terms[2]).solve();
                int ay = (int)((NumberTerm) terms[3]).solve();
                
                int dx = ax - x;
                int dy = ay - y;
                
                // Decides if there is even slight chance to defeat enemy
                return un.unifies(terms[4], new NumberTermImpl(dx*dx + dy*dy));
            } 
            catch (NoValueException ex) {
                return false;
            }
            
	}
}
