/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package systemactions;

import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjFeatures.Senses.Sight;
import Enviroment.EnvObjects.Agents.Agent;
import Enviroment.EnvObjects.GameObject;
import Enviroment.Model;
import Enviroment.EnviromentalMap.Compas;
import jason.JasonException;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Gets escape direction from the enemy
 * @author Vojtech Simetka
 */
public class getEscapeDirection extends SystemAction {
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) 
                          throws Exception
    {
        GameObject objectMe = null, objectEnemy = null;
        try {
            objectMe = Model.getAgentByName(terms[0].toString());
            objectEnemy = Model.getAgentByName(terms[1].toString());
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
        
        // look for friends
        Sense sight = me.getSense(Sight.class);
        ArrayList<GameObject> objectList;
        objectList = Model.getCurrentMap()
                          .getGameObjectsInArea(sight.getDetectionArea());
        
        Agent friend = null;
        for (GameObject gameObject : objectList) {
            if (gameObject instanceof Agent) {
                friend = ((Agent) gameObject);
                if(friend.getColor() == me.getColor()) {
                    break;
                }
            }
        }
        
        // if you found friend go towards him
        if(friend != null) {
            boolean unifiedX, unifiedY;
            Point friendsPosition = friend.getPosition();
            
            unifiedX = un.unifies(terms[2], new  NumberTermImpl(friendsPosition.x));
            unifiedY = un.unifies(terms[3], new  NumberTermImpl(friendsPosition.y));
            return unifiedX && unifiedY;
        }
        
        // try to go oppesite direction of direction enemy is coming form
	String direction = Compas.getDirectionName(enemy.getPosition(), me.getPosition());
        Point dirVector = Compas.getDirectionByName(direction);
        
        int canSeeUpTo = me.getSense(Sight.class).getSenseRange();
        Point dest = new Point((canSeeUpTo*dirVector.x), (canSeeUpTo*dirVector.y));
        
        // check for possible obsticles in destination point
        boolean canCollide = false;
        for (GameObject gameObject : objectList) {
            if (gameObject instanceof CollidableGameObject) {
                canCollide = gameObject.getBoundingBox().contains(dest);
            }
            if (canCollide) {
                break;
            }
        }
        
        // if there is not an obsticle go there
        if (!canCollide) {
            boolean unifiedX, unifiedY;
            unifiedX = un.unifies(terms[2], new  NumberTermImpl(dest.x));
            unifiedY = un.unifies(terms[3], new  NumberTermImpl(dest.y));
            return unifiedX && unifiedY;
        }
        
        // It is desperate ... Go home ...
	GameObject hill = me.getTeam().getHill();
        dest = hill.getPosition();
        
        boolean unifiedX, unifiedY;
        unifiedX = un.unifies(terms[2], new  NumberTermImpl(dest.x));
        unifiedY = un.unifies(terms[3], new  NumberTermImpl(dest.y));
        return unifiedX && unifiedY;    
    }
}
