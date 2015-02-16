/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import Enviroment.EnvObjFeatures.*;
import Agents.AgentTraits.*;

/**
 *
 * @author lennylinux
 */
public class GameObject extends Object {
    /* object inner variables */
    private final EnumGameObjects type;
    private Point position;
    private ArrayList<Point> vertexes;
    
    /* things affecting preception by sences */
    private Map <EnumSences, Sence> sences;
    private Map <EnumEmitors, Emitor> emitors;
    private Map <String, Integer> traits;
    
    /* Constructors */
    public GameObject(EnumGameObjects type) {
        this(type, 0, 0);
    }
    
    public GameObject(EnumGameObjects type, int x, int y) {
        this.type = type;
        this.position = new Point(x, y);
    }
 
/* General enviroment object related methods */
    
    public void addVertex(int x, int y) {
        this.addVertex(new Point(x, y));
    }
    
    public void addVertex(Point p) {
        this.vertexes.add(p);
    }
    
    public void setVertexes(ArrayList<Point> shape) {
        this.vertexes.clear();
        this.vertexes.addAll(shape);
    }
    
    public ArrayList<Point> getVertexes() {
        return vertexes;
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
    
    public Point getPosition() {
        return new Point(this.position);
    }
    
    public int getAreaSize() {
        return 1;
    }
    
    private void computeAreaSize() {
    }
    
/* Agent related methods */
    
    public boolean getTrait(String trait) {
        Integer value = this.traits.get(trait);
        return value.intValue() != 0;
    }
    
    public void setTrait(String trait, int value) {
        this.traits.put(trait, value);
    }
    
/* Sences related methods */    
    
    public boolean getSence(EnumSences type) {
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
