
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects;
import Enviroment.EnvObjFeatures.Sence;
import Enviroment.EnvObjFeatures.EnumSences;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import Enviroment.EnvObjFeatures.*;
import Enviroment.GameMap;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public class GameObject extends Object {
    /* object inner variables */
    private final EnumGameObjects type;         // type of GameObject
    private Point position;                     // integer positioning
    private Shape shape;                        // shape of GameObject
    private ArrayList<Point> vertices;          // sorted list of vertices
    private GameMap map;                        // map this object belongs to
    private Rectangle r;                        // tight rectangle around 
    
    /* things affecting preception by sences */
    private Map <EnumSences, Sence> sences;
    private Map <EnumEmitors, Emitor> emitors;
    
    /* Constructors */
    public GameObject(EnumGameObjects type) {
        this(type, null, new Point());
    }
    
    public GameObject(EnumGameObjects type, GameMap map) {
        this(type, map, new Point());
    }
    
    public GameObject(EnumGameObjects type, GameMap map, int x, int y) {
        this(type, map, new Point(x, y));
    }
    
    public GameObject(EnumGameObjects type, GameMap map, Point p) {
        this.type = type;
        this.position = new Point(p);
        this.vertices = new ArrayList();
        this.shape = null;
        this.map = map;
    }
 
    public EnumGameObjects getType() {
        return this.type;
    }
    
    public boolean putIntoMap(int x, int y) {
        if (this.map == null) return false;
        this.map.addGameObject(this, x, y);
        return true;
    }
    
    public boolean putIntoMap(Point p) {
        if (this.map == null) return false;
        this.map.addGameObject(this, p);
        return true;
    }
    
    public boolean putIntoMap(int x, int y, GameMap map) {
        if (this.map != null) return false;
        this.map = map;
        return this.putIntoMap(x, y);
    }
    
    public boolean putIntoMap(Point p, GameMap map) {
        if (this.map != null) return false;
        this.map = map;
        return this.putIntoMap(p);
    }
    
/* General enviroment object related methods */
    
    public void setNewShape(Shape s) {
        if (s == null) {
            this.shape = null;
            this.vertices.clear();
            return;
        }
        this.shape = s;
        this.shape.finished();
        this.vertices.clear();
        this.vertices.addAll(this.shape.getVertices());
        this.translateVertices(this.shape.getMidpoint(), this.position);
        this.r = this.shape.getRectangle();
        this.r.translate((this.position.x - this.shape.getMidpoint().x),
                         (this.position.y - this.shape.getMidpoint().y));
    }
    
    public ArrayList<Point> getVertices() {
        ArrayList<Point> vertexList = new ArrayList();
        for(Point p : this.vertices) {
            vertexList.add(new Point(p));
        }
        return vertexList;
    }
    
    private void translateVertices(Point cp, Point np) {
        int x = np.x - cp.x;
        int y = np.y - cp.y;
        for (Point p : this.vertices) {           
            p.translate(x, y);
            p.equals(p);
        }
    }
    
    public void setPosition(int x, int y) {
        this.setPosition(new Point(x, y));
    }

    public void setPosition(Point position) {
        if (position == null) {
            return;
        }
        translateVertices(this.position, position);
        this.r.translate((position.x - this.position.x), 
                         (position.y - this.position.y));
        this.position.setLocation(position);
    }
    
    public Rectangle getRectangle() {
        return new Rectangle(this.r);
    }
    
    public int getAreaSize() {
        return (int) Math.round(this.shape.getArea());
    }
    
    public GameMap getMap() {
        return this.map;
    }
    
/* Sences related methods */    
    
    public Sence getSence (EnumSences type) {
        return this.sences.get(type);
    }
    
    public boolean getHasSence(EnumSences type) {
        return this.sences.get(type) != null;
    }
    
    public void setSence(Sence s) {
        this.sences.put(s.getType(), s);
    }
    
    public void removeSence(EnumSences type) {
        this.sences.remove(type);
    }
    
/* Interactive and precevable object related methods */
    
    public Emitor getEmitor(EnumEmitors type) {
        return this.emitors.get(type);
    }
    
    public void setEmitor(Emitor e) {
        this.emitors.put(e.getType(), e);
    }    

    public void removeEmitor(EnumEmitors type) {
        this.emitors.remove(type);
    }
    
}
