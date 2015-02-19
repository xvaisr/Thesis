
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
        this(type, 0, 0);
    }
    
    public GameObject(EnumGameObjects type, int x, int y) {
        this(type, new Point(x, y));
    }
    
    public GameObject(EnumGameObjects type, Point p) {
        this.type = type;
        this.position = new Point(p);
        this.relPosition = new Point(p);
        this.vertices = null;
        this.shape = null;
        this.map = null;
    }
 
/* General enviroment object related methods */
    
    public void setNewShape(Shape s) {
        this.shape = s;
        this.vertices.clear();
        ArrayList<Point> vertexList = this.shape.getVertices();
        
    }
    
    public Shape getShape() {
        return this.shape;
    }
        
    public EnumGameObjects getType() {
        return this.type;
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }

    public void setPosition(Point position) {
        this.position.setLocation(position);
    }
    
    public void move() {
    }
    
    public Point getPosition() {
        return new Point(this.position);
    }
    
    public Point getRelPosition() {
        return new Point(this.relPosition);
    }
    
    public int getAreaSize() {
        return 0;
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
