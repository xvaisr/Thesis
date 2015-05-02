/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnviromentalMap;

import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjFeatures.MoveableGameObject;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnviromentalMap.SpecializedMaps.StaticObjetcsTreeMap;
import Enviroment.EnviromentalMap.SpecializedMaps.DynamicObjectsTreeMap;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class GameMapBackup implements MapInterface {
    private StaticObjetcsTreeMap enviromentMap;
    private DynamicObjectsTreeMap agentMap;
    private Dimension size;

    public GameMapBackup() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public GameMapBackup(int width, int height) {
        this(width, height, DynamicObjectsTreeMap.DEFAULT_CHUNK_SIZE);
    }
    
    public GameMapBackup(int width, int height, int chunkSize) {
        this.size = new Dimension(width, height);
        this.enviromentMap = new StaticObjetcsTreeMap(size);
        this.agentMap = new DynamicObjectsTreeMap(chunkSize, this.size);
    }

    public ArrayList<GameObject> getDinamycMapArea(Rectangle r) {
        return this.agentMap.getGameObjectsInArea(r);
    }
    
    public ArrayList<GameObject> getStaticMapArea(Rectangle r) {
        return this.enviromentMap.getGameObjectsInArea(r);
    }
    
    
    @Override
    public Dimension getMapSize() {
        return new Dimension(this.size);
    }

    @Override
    public int getMapWidth() {
        return this.size.width;
    }

    @Override
    public int getMapHeight() {
        return this.size.height;
    }

    @Override
    public boolean addGameObject(GameObject o) {
        Point p = o.getPosition();
        return this.addGameObject(o, p);
    }

    @Override
    public boolean addGameObject(GameObject o, int x, int y) {
        return this.addGameObject(o, new Point(x, y));
    }

    @Override
    public boolean addGameObject(GameObject o, Point p) {
        if (o instanceof MoveableGameObject) {
            boolean colision = false;
            if (o instanceof CollidableGameObject) {
                ArrayList<GameObject> colideList;
                colideList = this.enviromentMap
                                .getGameObjectsInArea(o.getBoundingBox());

                for(GameObject colider : colideList) {

                    if (colider instanceof CollidableGameObject) {
                        CollidableGameObject a, b;
                        a = (CollidableGameObject) o;
                        b = (CollidableGameObject) colider;

                        colision = a.colides(b);
                    }

                    if (colision) {
                        return false;
                    }
                }

            }
            
            return this.agentMap.addGameObject(o);
        }
        
        return this.enviromentMap.addGameObject(o, p);
    }

    @Override
    public boolean moveGameObjectTo(GameObject o, int x, int y) {
        return this.moveGameObjectTo(o, new Point(x, y));
    }

    @Override
    public boolean moveGameObjectTo(GameObject o, Point p) {
        if (o instanceof MoveableGameObject) {
            return this.agentMap.moveGameObjectTo(o, p);
        }
        return this.enviromentMap.moveGameObjectTo(o, p);
    }

    @Override
    public ArrayList<GameObject> getGameObjectsInArea(Rectangle r) {
        ArrayList<GameObject> objectList;
        objectList = this.enviromentMap.getGameObjectsInArea(r);
        objectList.addAll(this.agentMap.getGameObjectsInArea(r));
        return objectList;
    }

    @Override
    public ArrayList<GameObject> getGameObjectsAt(Point p) {
        ArrayList<GameObject> objectList;
        objectList = this.enviromentMap.getGameObjectsAt(p);
        objectList.addAll(this.agentMap.getGameObjectsAt(p));
        return objectList;
    }

    @Override
    public void removeGameObject(GameObject o) {
        if (o instanceof MoveableGameObject) {
            o.leaveMapContainer();
        }
        else {
            this.enviromentMap.removeGameObject(o);
        }
    }

    @Override
    public void setAsInternalMap(MapInterface apiLayer) {
        throw new UnsupportedOperationException("This can't be set as interal map");
    }

    @Override
    public MapInterface getMap() {
        return this;
    }
    
}
