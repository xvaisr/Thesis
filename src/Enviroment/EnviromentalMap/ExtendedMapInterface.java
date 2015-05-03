/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnviromentalMap;

import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnviromentalMap.Chunk;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public interface ExtendedMapInterface extends MapInterface {
    
    public ArrayList<GameObject> getGameObjectsInAreaStaticMap(Rectangle r);
    public ArrayList<GameObject> getGameObjectsInAreaDynamicMap(Rectangle r);
    
    public ArrayList<GameObject> getGameObjectsAtStaticMap(Point p);
    public ArrayList<GameObject> getGameObjectsAtDynamicMap(Point p);
    
    public ArrayList<GameObject> getDetectedObjects(Sense s);
    public ArrayList<GameObject> getDetectedObjectsStaticMap(Sense s);
    public ArrayList<GameObject> getDetectedObjectsDynamicMap(Sense s);
    
    public ArrayList<Chunk> getChunksInArea(Rectangle r);
    
}


/*

package Enviroment.EnviromentalMap;

import Enviroment.EnvObjFeatures.Senses.Sense;
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
    public ArrayList<GameObject> getGameObjectsInAreaStaticMap(Rectangle r);
    public ArrayList<GameObject> getGameObjectsInAreaDynamicMap(Rectangle r);
    
    public ArrayList<GameObject> getGameObjectsAtStaticMap(Point p);
    
    public ArrayList<GameObject> getDetectedObjects(Sense s);
    public ArrayList<GameObject> getDetectedObjectsStaticMap(Sense s);
    public ArrayList<GameObject> getDetectedObjectsDynamicMap(Sense s);
    
    public void removeGameObject(GameObject o);
    
    public void setAsInternalMap(MapInterface apiLayer);
    public MapInterface getMap();
    
    
    
}


*/