/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnviromentalMap;

import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjFeatures.DetectableGameObject;
import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Emitors.SmellEmitor;
import Enviroment.EnvObjFeatures.Emitors.VisualEmitor;
import Enviroment.EnvObjFeatures.MoveableGameObject;
import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnviromentalMap.SpecializedMaps.DynamicObjectsTreeMap;
import RTreeAlgorithm.Rtree;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author lennylinux
 */
public class GameMap implements ExtendedMapInterface, MapContainer {
    private static final int DEFAULT_EMITORMAP_COUNT = 5;
    
    private final HashMap<Class<? extends Emitor>, Rtree<Emitor>> emitorMaps;
    private ArrayList<GameObject> addedObjects;
    private final ArrayList<Rtree<Emitor>> mapsList;
    private DynamicObjectsTreeMap dynamicMap;
    private Dimension size;
    private int index;

    public GameMap() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public GameMap(int width, int height) {
        this(width, height, DynamicObjectsTreeMap.DEFAULT_CHUNK_SIZE);
    }
    
    public GameMap(int width, int height, int chunkSize) {
        this.size = new Dimension(width, height);
        this.emitorMaps = new HashMap(DEFAULT_EMITORMAP_COUNT);
        this.mapsList = new ArrayList(DEFAULT_EMITORMAP_COUNT);
        this.dynamicMap = null;
        this.init(chunkSize);
    }

    private void init(int chunkSize) {
        // inicialize all emitor submaps
        Rtree<Emitor> mapTree;
        // initialize visual map
        mapTree = new Rtree();
        this.emitorMaps.put(VisualEmitor.class, mapTree);
        this.mapsList.add(mapTree);
        // initialize smell map
        mapTree = new Rtree();
        this.emitorMaps.put(SmellEmitor.class, mapTree);
        this.mapsList.add(mapTree);
        
        this.index = 0;
        // no static object emits sound so no map required
        
        this.addedObjects = new ArrayList(50);
        
        // inicialze dynamic map
        this.dynamicMap = new DynamicObjectsTreeMap(chunkSize, this.size);
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
    
    // <editor-fold defaultstate="collapsed" desc="Adding and moving objects">
    @Override
    public boolean addGameObject(GameObject o) {
        boolean success = false;
        
        // if object is outside of map, do not allow to be placed here
        if (this.outOfBounds(o.getPosition())) {
            return false;
        }
        
        // <editor-fold defaultstate="collapsed" desc="Adding and moving objects">
        // if object is instance of MoveableGameObject, it belongs to dynamic map
        if (o instanceof MoveableGameObject) {
            // we need to check for colisions;
            if (o instanceof CollidableGameObject) {
                CollidableGameObject collidable;
                collidable = (CollidableGameObject) o;
                
                // visual map is first at list, get object throgh emitor
                Rtree<Emitor> map;
                synchronized(this.mapsList) {
                    map = this.emitorMaps.get(VisualEmitor.class);
                }
                boolean collision = false;
                synchronized(map) {
                    collision = this.collisionOccured(collidable, o.getPosition(), map);
                }
                
                /*
                 * if collision with static maps occured, can't put object into 
                 * dynamic map as well
                 */
                if (collision) {
                    return false;
                }
                
            }
            // if it's movable object but doesn't collide, just put it into dynamic map
            
            // synchronization is done inside chunk implementation on their level
            success = this.dynamicMap.addGameObject(o);
        }
        // </editor-fold>
        // if inseretd object is static, put it into static maps via it's emitors
        else if (o instanceof DetectableGameObject) {
            DetectableGameObject detectable;
            detectable = (DetectableGameObject) o;
            success = this.addDetectableObject(detectable, detectable.getPosition());
        }
        
        // if object is not detectable or movable there is no map to put that in
        if (success) {
            this.addedObjects.add(o);
        }
        
        return success;
    }

    @Override
    public boolean addGameObject(GameObject o, int x, int y) {
        boolean success;
        // save original position in case adding game object would fail
        Point originalPosition = o.getPosition();
        
        // set desired position
        o.setPosition(x, y);
        // try add object with overloading of this method (single implementation)
        success = this.addGameObject(o);
        // if it failed set original position
        if (!success) {
            o.setPosition(originalPosition);
        }
        return success;
    }

    @Override
    public boolean addGameObject(GameObject o, Point p) {
        boolean success;
        // save original position in case adding game object would fail
        Point originalPosition = o.getPosition();
        
        // set desired position
        o.setPosition(p);
        // try add object with overloading of this method (single implementation)
        success = this.addGameObject(o);
        // if it failed set original position
        if (!success) {
            o.setPosition(originalPosition);
        }
        return success;
    }

    private boolean addDetectableObject(DetectableGameObject o, Point position) {
        // if object is outside of map, do not allow to be placed here
        if (this.outOfBounds(position)) {
            return false;
        }
        
        ArrayList<Emitor> emitorList;
        Rtree<Emitor> map;
        CollidableGameObject collidable = null;
        Point originalPosition = o.getPosition();
        
        // check if given object is collidable
        if (o instanceof CollidableGameObject) {
            collidable = (CollidableGameObject) o;
        }

        /* 
         * To prevent need of changing all trees in case of collision, visual
         * emitor must be procesed first, because visual emitor is used
         * for collision detection. If object is placed to visual map, no other
         * object can take it's place. That implies no collision can occure while
         * adding object to other maps.
         */
        
        emitorList = o.getEmitorList();
        Emitor visualEmitor = o.getEmitor(VisualEmitor.class);
        // if object has visual emitor and it is not first on the list
        if (visualEmitor != null && emitorList.indexOf(visualEmitor) != 0) {
            emitorList.remove(visualEmitor);
            // put visual emitor to the front (needs to be first element)
            emitorList.add(0, visualEmitor);
        }
        
        // add all emitors of the inserted object to dedicated maps
        for (Emitor emitor : emitorList) {
            synchronized(this.emitorMaps) {
                map = this.emitorMaps.get(emitor.getClass());

                // if there is no map for this kind of emitor, create it
                if(map == null) {
                    map = new Rtree();
                    this.emitorMaps.put(emitor.getClass(), map);
                    synchronized(this.mapsList) {
                        this.mapsList.add(map);
                    }
                }
            }
            
            // If there is visual emitor, should be pocessed in first iteration
            // all collisions are checked against visual map ...
            if (emitor instanceof VisualEmitor) {
                // check if there are collisions
                boolean collision;
                synchronized (map) {
                    collision = this.collisionOccured(collidable, position, map);
                }
                // there is collision so I can't add this object to this place
                if (collision) {
                    return false;
                }
            }
            
            /* 
             * because R tree is imposible to synchronize due to fact that 
             * changes in tree are gradually bubbling throw (data inconsistenci)
             * outer synchronization is required
             */
            synchronized (map) {
                o.setPosition(position);
                // if object is in this map, it needs to be moved to new position
                if (o.getMapContainer() != null && o.getMapContainer().getMap() == this) {
                    // mapos contains emitors, not object itself
                    
                    // for delete search ve need to use original position 
                    map.Delete(emitor, originalPosition);
                }
                // insert object (emitor) to it's new location;
                map.Insert(emitor, emitor.getDispersionArea());
            }
            
        }
        
        // set this as map container fo the object
        o.setMapContainer(this);
        
        // object has been successfully added to all maps it belongs
        
        
        return true;
    }
    
    private boolean collisionOccured(CollidableGameObject collidable, 
                                    Point np, Rtree<Emitor> map)
    {
        // if some of these parametrs is null I can't decide collision
        if (collidable == null || map == null || np == null) {
            return false; // let's pretend "no collision"
        }
        
        this.index++;
        
        boolean collides = false;
        // Visual emitor's bounding box is identical with object's bounding box
        Rectangle searchArea = collidable.getBoundingBox();
        
        // shift search area to correct location
        Point cp = collidable.getPosition();
        searchArea.translate((np.x - cp.x), (np.y - cp.y));
        
        // search for all potenciali colliding objects
        ArrayList<Emitor> emitorList = map.Find(searchArea);
        for (Emitor emitor : emitorList) {
            // if emitor doesn't have collidable originator, just skip it
            if (emitor.getOriginator() instanceof CollidableGameObject) {
                CollidableGameObject colliding 
                                = (CollidableGameObject) emitor.getOriginator();

                collidable.setPosition(np);
                collides = collidable.colides(colliding);

                // if there is collision object cannot be inserted
                if (collides) {
                    collidable.setPosition(cp);
                    break;
                }
            }
            boolean a = emitor.getOriginator() instanceof GameObject;
        }

        if (emitorList.isEmpty()) {
            for (GameObject g : this.addedObjects) {
                boolean intersects = collidable.getBoundingBox().intersects(g.getBoundingBox());
                intersects = intersects || false;
            }
        }
        
        
        return collides;
    }
    
    
    @Override
    public boolean moveGameObjectTo(GameObject o, int x, int y) {
        return this.moveGameObjectTo(o, new Point(x, y));
    }

    @Override
    public boolean moveGameObjectTo(GameObject o, Point p) {
        /* 
         * Basic algorithm is more or less the same as adding object to map:
         * - check to where object belongs
         * - check for collisions
         * - remove from original placement / container
         * - put it to new one
         */
        boolean success = false;
        
/*        // if object is instance of MoveableGameObject, it belongs to dynamic map
        if (o instanceof MoveableGameObject) {
            // we need to check for collisions;
            if (o instanceof CollidableGameObject) {
                CollidableGameObject collidable;
                collidable = (CollidableGameObject) o;
                
                // visual map is first at list, get object throgh emitor
                Rtree<Emitor> map;
                synchronized(this.mapsList) {
                    map = this.emitorMaps.get(VisualEmitor.class);
                }
                boolean collision = false;
                synchronized(map) {
                    collision = this.collisionOccured(collidable, o.getPosition(), map);
                }
                
                /*
                 * if collision with static maps occured, can't put object into 
                 * dynamic map as well
                 * /
                if (collision) {
                    return false;
                }
                
            }
            /* if it's movable object but doesn't collide, let dynamic map
             * to deal with it synchronization is done inside chunk 
             * implementation on their level
              * / 
            success = this.dynamicMap.moveGameObjectTo(o, p);
        }
        else if (o instanceof DetectableGameObject) {
            DetectableGameObject detectable;
            detectable = (DetectableGameObject) o;
            // method "addDetectableObject" can take care of moving object as well
            success = this.addDetectableObject(detectable, detectable.getPosition());
        }
        
        // if object is not detectable or movable there is no map to put that in
        */
        return success; 
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Map search related methods">
    private ArrayList<GameObject> findObjectsInMap(Rectangle area, Rtree<Emitor> map) {
        ArrayList<GameObject> objectList = new ArrayList();
        ArrayList<Emitor> emitorList;
        
        emitorList = map.Find(area);
        for (Emitor emitor : emitorList) {
            objectList.add(emitor.getOriginator());
        }
        
        return objectList;
    }
    
    private ArrayList<GameObject> findObjectsInMap(Point p, Rtree<Emitor> map) {
        ArrayList<GameObject> objectList = new ArrayList();
        ArrayList<Emitor> emitorList;
        
        emitorList = map.Find(p);
        for (Emitor emitor : emitorList) {
            objectList.add(emitor.getOriginator());
        }
        
        return objectList;
    }
    
    @Override
    public ArrayList<GameObject> getGameObjectsInArea(Rectangle r) {
        ArrayList<GameObject> objectList;
        // take objects form static maps
        objectList = this.getGameObjectsInAreaStaticMap(r);
        
        // add objects from dynamic map
        objectList.addAll(this.dynamicMap.getGameObjectsInArea(r));
        
        return objectList;
    }
    
    @Override
    public ArrayList<GameObject> getGameObjectsInAreaStaticMap(Rectangle r) {
        HashSet objectSet = new HashSet(); // to eliminat duplicities
        ArrayList<Rtree<Emitor>> maps;
        
        // we don't want to block map list, but need to ensure there is no data-race
        synchronized(this.mapsList) {
            maps = (ArrayList<Rtree<Emitor>>) this.mapsList.clone();
        }
        
        // we need to search throw all maps ...
        for (Rtree<Emitor> treeMap : maps) {
            // synchronized to make sure no object is being added into map
            synchronized(treeMap) {
                objectSet.addAll(findObjectsInMap(r, treeMap));
            }
        }
        
        // return as array - interfaces compatibylity 
        return new ArrayList(objectSet);
    }
    
    @Override
    public ArrayList<GameObject> getGameObjectsInAreaDynamicMap(Rectangle r) {
        // let dynamic map handle this one ...
        return this.dynamicMap.getGameObjectsInArea(r);
    }
    
    @Override
    public ArrayList<GameObject> getGameObjectsAtStaticMap(Point p) {
        HashSet objectSet = new HashSet(); // to eliminat duplicities
        ArrayList<Rtree<Emitor>> maps;
        
        // we don't want to block map list, but need to ensure there is no data-race
        synchronized(this.mapsList) {
            maps = (ArrayList<Rtree<Emitor>>) this.mapsList.clone();
        }
        
        // we need to search throw all maps ...
        for (Rtree<Emitor> treeMap : maps) {
            // synchronized to make sure no object is being added into map
            synchronized(treeMap) {
                objectSet.addAll(findObjectsInMap(p, treeMap));
            }
        }
        
        // return as array - interfaces compatibylity 
        return new ArrayList(objectSet);
    }
    
    @Override
    public ArrayList<GameObject> getGameObjectsAtDynamicMap(Point p) {
        return this.dynamicMap.getGameObjectsAt(p);
    }
    
    @Override
    public ArrayList<GameObject> getGameObjectsAt(Point p) {
        ArrayList<GameObject> objectList;
        // take objects form static maps
        objectList = this.getGameObjectsAtStaticMap(p);
        
        // add objects from dynamic map
        objectList.addAll(this.dynamicMap.getGameObjectsAt(p));
        
        return objectList;
    }
    
    @Override
    public ArrayList<GameObject> getDetectedObjects(Sense s) {
        ArrayList<GameObject> objectList;
        // take objects form static maps
        objectList = this.getDetectedObjectsStaticMap(s);
        
        // add objects from dynamic map
        objectList.addAll(this.getDetectedObjectsDynamicMap(s));
        
        return objectList;
    }
    
    @Override
    public ArrayList<GameObject> getDetectedObjectsStaticMap(Sense s) {
        ArrayList<GameObject> objectList;
        Rtree<Emitor> map = null;
        synchronized(this.emitorMaps) {
            map = this.emitorMaps.get(s.getPreciveable());
        }
        
        if (map == null) {
            return new ArrayList();
        }
        
        objectList = this.findObjectsInMap(s.getDetectionArea(), map);
        return objectList;
    }
    
    @Override
    public ArrayList<GameObject> getDetectedObjectsDynamicMap(Sense s) {
        ArrayList<GameObject> objectList;
        // take objects form static maps
        objectList = this.dynamicMap.getGameObjectsInArea(s.getDynamicDetectionArea());
        
        // remove all objects from dynamic map which are not detectable by given
        for (GameObject gameObject : objectList) {  // sense
            DetectableGameObject detectable = null;
            
            // we need DetectableGameObject instance to call it's methods
            if (gameObject instanceof DetectableGameObject) {
                detectable = (DetectableGameObject) gameObject;
            }
                        
            if ((detectable != null) &&
                (detectable.getHasEmitor(s.getPreciveable())) )
            {
                // If given object is preciveable by given sense, skip removing part
                if (s.canPrecive(detectable.getEmitor(s.getPreciveable()))) {
                    continue;
                }
            }
            
            // if we don't have detectable instance or it can't be detected,
            objectList.remove(gameObject);   // remove object from tghe list
        }
        
        return objectList;
    }
    // </editor-fold>
    

    @Override
    public void removeGameObject(GameObject o) {
        if(o.getMapContainer() == this && o instanceof DetectableGameObject) {
            // cast as detectable to use it's methods
            DetectableGameObject detectable;
            detectable = (DetectableGameObject) o;
            
            // get emitor list
            ArrayList<Emitor> emitorList;
            emitorList = detectable.getEmitorList();
            
            Rtree<Emitor> map;
            // all emitors of the inserted object need to be deleted (from all maps)
            for (Emitor emitor : emitorList) {
                synchronized(this.emitorMaps) {
                    map = this.emitorMaps.get(emitor.getClass());

                    // if there is no map for this kind of emitor, 
                    if(map == null) {  // there is nothing to delete
                        continue;
                    }
                }
            
                /* 
                 * because R tree is imposible to synchronize due to fact that 
                 * changes in tree are gradually bubbling throw (data inconsistenci)
                 * outer synchronization is required
                 */
                synchronized (map) {
                    // for delete search ve need to use original position 
                    map.Delete(emitor, emitor.getOriginator().getPosition());
                }
            }
        }
        else if (o.getMapContainer() instanceof Chunk) {
            this.dynamicMap.removeGameObject(o);
        }
        
        // object should be removed completely or it never existed in this map
        // in a first place
    }

    
    // <editor-fold defaultstate="collapsed" desc="MapContainer interface">
    @Override
    public void setAsInternalMap(MapInterface apiLayer) {
        throw new UnsupportedOperationException("This map can't be set as internal map");
    }

    @Override
    public MapInterface getMap() {
        return this;
    }

    @Override
    public boolean getIsInsideContainer(GameObject o) {
        boolean contained = false;
        ArrayList<GameObject> objectList;
        objectList = getGameObjectsAtStaticMap(o.getPosition());
        for (GameObject gameObject : objectList) {
            contained = (gameObject == o);
            if (contained) {
                break;
            }
        }
        return contained;
    }
    // </editor-fold>
    
}
