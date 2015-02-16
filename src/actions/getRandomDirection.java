/**
 * BP, anthill strategy game
 * Gets free of obstacle random location on the map
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.getRandomDirection.java
 */
package actions;

import Enviroment.Model;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

import java.util.Random;

/**
 * Gets free of obstacle random location on the map 
 * @author Vojtech Simetka
 */
public class getRandomDirection extends DefaultInternalAction {
    private static Random rand = new Random();

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms)
	{
		int x = rand.nextInt(Model.getWorld().getWidth()-1);
		int y = rand.nextInt(Model.getWorld().getHeight()-1);
		while (!Model.getWorld().isFree(x,y))
		{
			x = rand.nextInt(Model.getWorld().getWidth()-1);
			y = rand.nextInt(Model.getWorld().getHeight()-1);
		}
		
		return un.unifies(terms[0], new NumberTermImpl(x)) &&
			   un.unifies(terms[1], new NumberTermImpl(y));
	}
}
