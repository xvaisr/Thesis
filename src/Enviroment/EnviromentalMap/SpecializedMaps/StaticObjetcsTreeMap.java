/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnviromentalMap.SpecializedMaps;

import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnviromentalMap.MapContainer;
import Enviroment.EnviromentalMap.MapInterface;
import RTreeAlgorithm.*;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class StaticObjetcsTreeMap implements MapInterface, MapContainer {
    
    private volatile MapInterface parent;
    private final Rtree<GameObject> staticMapTree;
    private Dimension size;

    public StaticObjetcsTreeMap() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public StaticObjetcsTreeMap(int width, int height) {
        this(new Dimension(width, height));
    }
    
    public StaticObjetcsTreeMap(Dimension d) {
        this.parent = null;
        this.staticMapTree = new Rtree();
        this.size = new Dimension(d);
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
        boolean colision = false;
        
        if ((p.x < 0 || p.x > this.getMapWidth()) ||
            (p.y < 0 || p.y > this.getMapHeight()))
        {
            return false;
        }
        
        if (o instanceof CollidableGameObject) {
            ArrayList<GameObject> colideList;
            colideList = this.getGameObjectsInArea(o.getBoundingBox());
            
            
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
        
        o.setPosition(p);
        synchronized (this.staticMapTree) {
            this.staticMapTree.Insert(o, o.getBoundingBox());
        }
        
        o.setMapContainer(this);
        return true;
    }

    @Override
    public boolean moveGameObjectTo(GameObject o, int x, int y) {
        return this.moveGameObjectTo(o, new Point(x, y));
    }
    
    @Override
    public boolean moveGameObjectTo(GameObject o, Point p) {
        boolean colision = false;
        
        if (!this.getIsInsideContainer(o)) {
            return false;
        }
        
        if (o instanceof CollidableGameObject) {
            ArrayList<GameObject> colideList;
            colideList = this.getGameObjectsInArea(o.getBoundingBox());
            
            
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
        
        this.removeGameObject(o);
        o.setPosition(p);
            
        synchronized (this.staticMapTree) {
            this.staticMapTree.Insert(o, o.getBoundingBox());
        }
        
        o.setMapContainer(this);
        return true;
    }

    @Override
    public ArrayList<GameObject> getGameObjectsInArea(Rectangle r) {
        synchronized (this.staticMapTree) {
            return this.staticMapTree.Find(r);
        }
    }

    @Override
    public ArrayList<GameObject> getGameObjectsAt(Point p) {
        synchronized (this.staticMapTree) {
            return this.staticMapTree.Find(p);
        }
    }

    @Override
    public void removeGameObject(GameObject o) {
        synchronized (this.staticMapTree) {
            this.staticMapTree.Delete(o, o.getBoundingBox());
        }
    }

    @Override
    public MapInterface getMap() {
        if (parent != null) {
            return parent;
        }
        return this;
    }

    @Override
    public boolean getIsInsideContainer(GameObject o) {
        Point p = o.getPosition();
        
        if(o.getMapContainer() != this) {
            return false;
        }
        
        if ((p.x < 0 || p.x > this.getMapWidth()) ||
            (p.y < 0 || p.y > this.getMapHeight()))
        {
            return false;
        }
        
        return true;
    }

    @Override
    public void setAsInternalMap(MapInterface apiLayer) {
        this.parent = apiLayer;
    }

    
    
    
}
