/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnviromentalMap;
import java.util.ArrayList;
import Enviroment.EnvObjects.*;
import java.awt.Point;
import java.awt.Rectangle;

public class Chunk implements MapContainer {
    private final MapInterface map;
    private final Rectangle r;
    private int i;
    private final ArrayList<GameObject> contained;
    
    /**
     *
     * @param map
     * @param blc - bottom left corner
     * @param width - chunk width
     * @param height - chunk height
     */
    public Chunk(MapInterface map, Point blc, int width, int height) {
        this(map, blc.x, blc.y, width, height);
    }
    /**
     *
     * @param map
     * @param x - x-axis position of bottom left corner
     * @param y - y-axis position of bottom left corner
     * @param width
     * @param height
     */
    public Chunk(MapInterface map, int x, int y, int width, int height) {
        this.map = map;
        this.r = new Rectangle(x, y, width, height);
        this.contained = new ArrayList();
        this.i = 0;
    }
    
    public Chunk(MapInterface map, Rectangle r) {
        this.map = map;
        this.r = new Rectangle(r);
        this.contained = new ArrayList();
        this.i = 0;
    }
    
    public Rectangle getRectangle() {
        return new Rectangle(this.r);
    }
    
    @Override
    public boolean addGameObject(GameObject obj) {
        if (!this.r.contains(obj.getPosition())) {
            return false;
        }
        synchronized(this.contained) {
            this.contained.add(obj);
        }
        obj.setMapContainer(this);
        return true;
    }
    
    public int getCount() {
        synchronized(this.contained) {
            return this.contained.size();
        }
    }
    
    public GameObject getNext() {
        this.i = i % this.getCount();
        return this.getObject(this.i);
    }
    
    public GameObject getObject(int i) {
        synchronized(this.contained) {
            if(i < 0 || i > this.contained.size()) {
                return null;
            }
            return this.contained.get(i);
        }
    }
    
    public GameObject getObjectHere(Point c) {
        GameObject selected = null;
        synchronized(this.contained) {
            for (GameObject o : this.contained) {
                if (o.getPosition().equals(c)) {
                    selected = o;
                    break;
                }
            }
        }
        return selected;
    }
    
    public ArrayList<GameObject> getInnerArrayList() {
        return this.contained;
    }
    
    public ArrayList<GameObject> getCopyInnerArrayList() {
        return (ArrayList<GameObject>) this.contained.clone();
    }
    
    @Override
    public boolean getIsInsideContainer(GameObject obj) {
        boolean inside = false;
        synchronized(this.contained) {
            inside = this.contained.contains(obj);
        }
        
        Point p = obj.getPosition();
        return inside && this.r.contains(p);
    }
    
    @Override
    public void removeGameObject(GameObject o) {
        synchronized(this.contained) {
            this.contained.remove(o);
        }
    }

    @Override
    public MapInterface getMap() {
        return this.map.getMap();
    }

    @Override
    public boolean outOfBounds(Point p) {
        return !this.r.contains(p);
    }


    

    
}
