/**
 * BP, anthill strategy game
 * Gets coordinates of the ant's anthill
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.getHome.java
 */
package actions;

import graphic.EnumTeams;
import Enviroment.Model;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

/**
 * Gets coordinates of the ant's anthill
 * @author Vojtech Simetka
 */
public class getHome extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception
    {
    	EnumTeams team = Model.getEnvironment().agent2info(terms[0].toString()).getTeam();
    	
    	return un.unifies(terms[1], new NumberTermImpl((int)Model.getWorld().getAnthill(team).x)) &&
    		   un.unifies(terms[2], new NumberTermImpl((int)Model.getWorld().getAnthill(team).y));
    }
}
