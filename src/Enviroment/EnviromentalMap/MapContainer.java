/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnviromentalMap;

import Enviroment.EnviromentalMap.MapInterface;
import Enviroment.EnvObjects.GameObject;
import java.awt.Point;

public interface MapContainer {
    
    public MapInterface getMap();
    public boolean addGameObject(GameObject o);
    public boolean getIsInsideContainer(GameObject o);
    public void removeGameObject(GameObject o);
    public boolean outOfBounds(Point p);
    
}
