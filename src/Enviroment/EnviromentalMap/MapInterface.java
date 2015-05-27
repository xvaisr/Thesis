/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnviromentalMap;

import Enviroment.EnvObjects.GameObject;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public interface MapInterface {
    
    public static final int DEFAULT_WIDTH = 3000;
    public static final int DEFAULT_HEIGHT = 3000;
 
    public Dimension getMapSize();
    public int getMapWidth();
    public int getMapHeight();
    
    public boolean addGameObject(GameObject o);
    public boolean addGameObject(GameObject o, int x, int y);
    public boolean addGameObject(GameObject o, Point p);
    
    public boolean moveGameObjectTo(GameObject o, int x, int y);
    public boolean moveGameObjectTo(GameObject o, Point p);
    
    public ArrayList<GameObject> getGameObjectsInArea(Rectangle r);
    public ArrayList<GameObject> getGameObjectsAt(Point p);
    public void removeGameObject(GameObject o);
    
    public void setAsInternalMap(MapInterface apiLayer);
    public MapInterface getMap();
    
    
    
}
