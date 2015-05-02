/**
 * BP, anthill strategy game
 * Returns true if the agent in first argument is weaker then the one in second argument
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/05
 * @version 1
 * @file    actions.isWeaker.java
 */
package systemactions;

import Enviroment.EnvObjects.Agents.Agent;
import Enviroment.EnvObjects.GameObject;
import jason.JasonException;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

/**
 * Returns true if the agent in first argument is weaker then the one in second argument 
 * @author Vojtech Simetka
 */
public class isWeaker extends SystemAction {
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) 
                          throws Exception
    {
        GameObject objectMe = null, objectEnemy = null;
        try {
            objectMe = Enviroment.Model.getAgentByName(terms[0].toString());
            objectEnemy = Enviroment.Model.getAgentByName(terms[1].toString());
        }
        catch (ArrayIndexOutOfBoundsException e)  {
            throw new JasonException("The internal action ’getEscapeDirection’"+
                "has not received it's arguments!");
        }
        
        // Enemies ! We need enemies ...
        if (objectMe == null || objectEnemy == null) {
            return false;
        }
        
        Agent me = null, enemy = null;
        if (objectMe instanceof Agent) {
            me  = ((Agent) objectMe);
        }
        
        if (objectEnemy instanceof Agent) {
            enemy = ((Agent) objectEnemy);
        }
        
        // Remind me what am I trying to do ...?
        if (me == null || enemy == null) {
            return false;
        }
        
        int myHp, enHp, myArmor, enArmor, myAttack, enAttack;
        myHp = me.getHealth();
        enHp = enemy.getHealth();
        
        myArmor = me.getTeam().getArmour();
        enArmor = enemy.getTeam().getArmour();
        
        myAttack = me.getTeam().getAttack();
        enAttack = enemy.getTeam().getAttack();
        
	
        // Calculates chances of winning for both agents
        int chance = (myHp / (myAttack - enArmor)) - (myHp / (enAttack - myArmor));
        
        // Decides if there is even slight chance to defeat enemy
	return chance <= 0;
    }
}
