/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package systemactions;

import jason.NoValueException;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Term;

/**
 * Determines if point with first two coordinates is closer to last two coordinates point then the second point 
 * @author Vojtech Simetka
 *
 */
public class isCloser extends SystemAction {
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) {	
        int ax = 0, ay = 0, x = 0, y = 0, homeX = 0, homeY = 0;
        try {
            ax = (int)((NumberTerm) terms[0]).solve();
            ay = (int)((NumberTerm) terms[1]).solve();
            x = (int)((NumberTerm) terms[2]).solve();
            y = (int)((NumberTerm) terms[3]).solve();
            homeX = (int)((NumberTerm) terms[4]).solve();
            homeY = (int)((NumberTerm) terms[5]).solve();
        }
        catch (NoValueException ex) {
            return false;
        }    
            
        int first_case = (homeX - ax) * (homeX - ax) + (homeY - ay) * (homeY - ay);
        int second_case = (homeX - x) * (homeX - x) + (homeY - y) * (homeY - y);
                
        return (first_case > second_case);
        
    }
}
