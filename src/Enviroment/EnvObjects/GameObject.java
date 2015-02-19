
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

/**
 *
 * @author lennylinux
 */
public class GameObject extends Object {
    /* object inner variables */
    private final EnumGameObjects type;
    private Point position;                     // integer positioning
    private Point relPosition;                  // relative positionig  for GUI
    private Shape shape;
    private ArrayList<Point> vertices;      
    private GameMap map;
    
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
        this.relPosition = new Point(p);
        this.vertices = null;
        this.shape = null;
        this.map = map;
    }
 
/* General enviroment object related methods */
    
    public void setNewShape(Shape s) {
        this.shape = s;
        this.vertices.clear();
        this.vertices.addAll(this.shape.getVertices());
        this.translateVertices();
    }
    
    public Shape getShape() {
        return this.shape;
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
    
    public ArrayList<Point> getVertices() {
        ArrayList<Point> vertexList = new ArrayList();
        for(Point p : this.vertices) {
            vertexList.add(new Point(p));
        }
        return vertexList;
    }
    
    private void translateVertices() {
        if (this.shape.getChanged()) {
            this.vertices.clear();
            this.vertices.addAll(this.shape.getVertices());
        }
        for (Point p : this.vertices) {
            p.translate(-this.position.x, -this.position.y);
        }
    }
    
    public void setPosition(int x, int y) {
        if (this.map == null) return;
        this.setPosition(new Point(x, y));
    }

    public void setPosition(Point position) {
        if (this.map == null) return;
        this.position.setLocation(position);
        this.translateVertices();
    }
    
    public void move(Point destination) {
    }
    
    public Point getPosition() {
        return new Point(this.position);
    }
    
    public Point getRelPosition() {
        return new Point(this.relPosition);
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
