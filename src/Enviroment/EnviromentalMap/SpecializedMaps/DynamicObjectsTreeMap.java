/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnviromentalMap.SpecializedMaps;
import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnviromentalMap.Chunk;
import Enviroment.EnviromentalMap.ExtendedMapInterface;
import Enviroment.EnviromentalMap.MapContainer;
import Enviroment.EnviromentalMap.MapInterface;
import static Enviroment.EnviromentalMap.MapInterface.DEFAULT_HEIGHT;
import static Enviroment.EnviromentalMap.MapInterface.DEFAULT_WIDTH;
import RTreeAlgorithm.Rtree;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class DynamicObjectsTreeMap implements MapInterface {
    public static final int DEFAULT_CHUNK_SIZE = 75;

    private volatile MapInterface parent;
    private final Rtree<Chunk> dynamicMapTree;
    private Dimension size;

    public DynamicObjectsTreeMap() {
        this(DEFAULT_CHUNK_SIZE);
    }
    
    public DynamicObjectsTreeMap(int chunkSize) {
        this(chunkSize, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public DynamicObjectsTreeMap(int chunkSize, int width, int height) {
        this(chunkSize, new Dimension(width, height));
    }
    
    public DynamicObjectsTreeMap(int chunkSize, Dimension d) {
        this.dynamicMapTree = new Rtree();
        this.size = new Dimension(d);
        
        int horizontal, vertical, modulo;
        horizontal = this.size.width / chunkSize;
        modulo = this.size.width % chunkSize;
        if (modulo != 0) {
            horizontal++;
        }
        vertical = this.size.width / chunkSize;
        modulo = this.size.width % chunkSize;
        if (modulo != 0) {
            vertical++;
        }
        
        for (int i = 0; i < horizontal; i++) {
            for (int j = 0; j < vertical; j++) {
                Rectangle r = new Rectangle(i*chunkSize, j*chunkSize, chunkSize, chunkSize);
                this.dynamicMapTree.Insert(new Chunk(this, r), r);
            }
        }
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

    private boolean outOfBounds(Point p) {
        boolean x, y;
        x = p.x < 0 || p.x > this.getMapWidth();
        y = p.y < 0 || p.y > this.getMapHeight();
        return x || y;
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
       
        // if object is outside of map, do not allow to be placed here
        if (this.outOfBounds(o.getPosition())) {
            return false;
        }

        o.setPosition(p);

        ArrayList<Chunk> chunksFound;
        chunksFound = this.dynamicMapTree.Find(p);
        
        for (Chunk c : chunksFound) {
            
            if (c.addGameObject(o)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean moveGameObjectTo(GameObject o, int x, int y) {
        return this.moveGameObjectTo(o, new Point(x, y));
    }
    
    @Override
    public boolean moveGameObjectTo(GameObject o, Point np) {
        boolean collides = false;
        Point cp = o.getPosition();
        Rectangle area = o.getBoundingBox();
        MapContainer container = o.getMapContainer();
        
        
        if ((container == null || container.getMap() == null) ||
            (container.getMap() != this && container.getMap() != this.parent))
        {
            return false;
        }
        
        // if object is shifting outside of map, do not allow it
        if (this.outOfBounds(np)) {
            return false;
        }
        
        // solve collisions
        if (o instanceof CollidableGameObject) {
            ArrayList<GameObject> objectList;
            CollidableGameObject collidable = null, colliding = null;
            
            collidable = ((CollidableGameObject) o);
            
            // shift search area to correct location
            area.translate((np.x - cp.x), (np.y - cp.y));
            
            // get objects whitch can possibly colide with moving object
            if (this.parent != null) {
                if (this.parent instanceof ExtendedMapInterface) {
                    // if it is possible only from static map (optimization)
                    objectList = ((ExtendedMapInterface) this.parent)
                        .getGameObjectsInAreaStaticMap(area);
                }
                else {
                    objectList = this.parent.getGameObjectsInArea(area);
                }
            }
            else { // if there is no connection to other maps, give empty list
                objectList = new ArrayList(0);
            }
                   
            for (GameObject z : objectList) {
                if (z instanceof CollidableGameObject) {
                    colliding = ((CollidableGameObject) z);
                }
                else {
                    colliding = null;
                }
                
                // skip noncoliding objects
                if (colliding == null) {
                    continue;
                }
                
                // solve colision
                collidable.setPosition(np);
                collides = collidable.colides(colliding);
                
                if (collides) {
                    // return object to it's original position
                    collidable.setPosition(cp);
                    break;
                }
            }
            
        }
        
        // if there is colision, object can't move there
        if (collides) {
            return false;
        }
        
        // if there is no problem, move object
        o.setPosition(np);
        
        // if out of bounds of it's original chunk
        if (container.outOfBounds(np)) {
            
            ArrayList<Chunk> chunksFound;
            chunksFound = this.dynamicMapTree.Find(np);
        
            for (Chunk c : chunksFound) {
                if (c.getRectangle().contains(np) && c.addGameObject(o)) {
                    container.removeGameObject(o);
                    o.setMapContainer(c);
                    return true;
                }
            }
            
            // if no chunk found or there is an error, set objects original
            // position and fail
            o.setMapContainer(container);
            o.setPosition(cp);
            return false;
        }
        
        return true;
    }
    
    @Override
    public ArrayList<GameObject> getGameObjectsInArea(Rectangle r) {
        ArrayList<GameObject> objectList = new ArrayList();
        ArrayList<Chunk> chunks;
        
        chunks = this.dynamicMapTree.Find(r);
        
        for (Chunk c : chunks) {
            ArrayList<GameObject> chunkInnerlist;
            chunkInnerlist = c.getInnerArrayList();
            synchronized (chunkInnerlist) {
                objectList.addAll(chunkInnerlist);
            }
        }
        
        return objectList;
    }

    @Override
    public ArrayList<GameObject> getGameObjectsAt(Point p) {
        ArrayList<GameObject> objectList = new ArrayList();
        ArrayList<Chunk> chunks;
        
        chunks = this.dynamicMapTree.Find(p);
        
        for (Chunk c : chunks) {
            GameObject o = c.getObjectHere(p);
            if (o != null) {
                objectList.add(o);
            }
        }
        
        return objectList;
    }
    
    public ArrayList<Chunk> getChunksInArea(Rectangle r) {
        return this.dynamicMapTree.Find(r);
    }

    @Override
    public void removeGameObject(GameObject o) {
        MapContainer c = o.getMapContainer();
        if (c.getMap() != this) {
            return;
        }
        o.leaveMapContainer();
    }

    @Override
    public void setAsInternalMap(MapInterface apiLayer) {
        this.parent = apiLayer;
    }

    @Override
    public MapInterface getMap() {
        if (parent != null) {
            return parent;
        }
        return this;
    }
    
}
