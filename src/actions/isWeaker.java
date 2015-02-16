/**
 * BP, anthill strategy game
 * Returns true if the agent in first argument is weaker then the one in second argument
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.isWeaker.java
 */
package actions;

import Agents.AgentInfo;
import Enviroment.Model;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

/**
 * Returns true if the agent in first argument is weaker then the one in second argument 
 * @author Vojtech Simetka
 */
public class isWeaker extends DefaultInternalAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms)
	{		
		// Gets information about both agents
		AgentInfo enemy = Model.getEnvironment().agent2info(terms[0].toString());
		AgentInfo me = Model.getEnvironment().agent2info(terms[1].toString());
		
		if (enemy == null || me == null)
			return false;
		
		// Calculates chances of winning for both agents
		int chance_to_defeat_enemy = (enemy.getHp() / (me.getAttack() - enemy.getArmor())) - 
				(me.getHp() / (enemy.getAttack() - me.getArmor()));
		
		
		// Decides if there is even slight chance to defeat enemy
		return chance_to_defeat_enemy <= 0;
	}
}
