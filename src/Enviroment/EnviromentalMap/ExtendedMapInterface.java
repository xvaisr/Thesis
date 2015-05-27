/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnviromentalMap;

import Enviroment.EnvObjFeatures.DetectableGameObject;
import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnviromentalMap.Chunk;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public interface ExtendedMapInterface extends MapInterface {
    
    public ArrayList<GameObject> getGameObjectsInAreaStaticMap(Rectangle r);
    public ArrayList<GameObject> getGameObjectsInAreaDynamicMap(Rectangle r);
    
    public ArrayList<GameObject> getGameObjectsAtStaticMap(Point p);
    public ArrayList<GameObject> getGameObjectsAtDynamicMap(Point p);
    
    public ArrayList<DetectableGameObject> getDetectedObjects(Sense s);
    public ArrayList<DetectableGameObject> getDetectedObjectsStaticMap(Sense s);
    public ArrayList<DetectableGameObject> getDetectedObjectsDynamicMap(Sense s);
    
    public ArrayList<Chunk> getChunksInArea(Rectangle r);
    
}