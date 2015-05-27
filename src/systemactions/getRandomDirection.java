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
import Enviroment.EnviromentalMap.Compas;
import Enviroment.Model;
import jason.JasonException;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Gets free of obstacle random location on the map 
 * @author Vojtech Simetka
 */
public class getRandomDirection extends SystemAction {
    private static final int SUITABLE_DISTANCE = 10;

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) 
                         throws Exception
    {
	GameObject object = null;
        try {
            object = Model.getAgentByName(terms[0].toString());
        }
        catch (ArrayIndexOutOfBoundsException e)  {
            throw new JasonException("The internal action ’getRandomDirection’"+
                "has not received it's argument!");
        }
        
        // there is no agent going by that name
        if (object == null) {
            return false;
        }

        // make sure it's an agent
        Agent ag = null;
        if (object instanceof Agent) {
            ag  = ((Agent) object);
        }
        
        // Remind me what am I trying to do ...?
        if (ag == null) {
            return false;
        }
        
        // look for friends to follow
        Sense sight = ag.getSense(Sight.class);
        ArrayList<GameObject> objectList;
        objectList = Model.getCurrentMap()
                          .getGameObjectsInArea(sight.getDetectionArea());
        
        Agent friend = null;
        for (GameObject gameObject : objectList) {
            if (gameObject instanceof Agent) {
                friend = ((Agent) gameObject);
                if(friend.getColor() == ag.getColor()) {
                    break;
                }
            }
        }
        
        // if you found friend go towards him if he is not at the same spoot
        if(friend != null && friend.getPosition() != ag.getPosition()) {
            boolean unifiedX, unifiedY;
            Point friendsPosition = friend.getPosition();
            
            unifiedX = un.unifies(terms[2], new  NumberTermImpl(friendsPosition.x));
            unifiedY = un.unifies(terms[3], new  NumberTermImpl(friendsPosition.y));
            return unifiedX && unifiedY;
        }
        
        
        // try to go oppesite direction of direction enemy is coming form
        boolean canCollide = false;
        Point dest = new Point();
        for(int i = 0; i < 4; i++) {
            Point direction = Compas.getRandomDirection();

            dest = new Point((SUITABLE_DISTANCE*direction.x), (SUITABLE_DISTANCE*direction.y));

            // check for possible obsticles in destination point
            for (GameObject gameObject : objectList) {
                if (gameObject instanceof CollidableGameObject) {
                    canCollide = gameObject.getBoundingBox().contains(dest);
                }
                if (canCollide) {
                    break;
                }
            }
            if (!canCollide) {
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
        return false;
    }
}
