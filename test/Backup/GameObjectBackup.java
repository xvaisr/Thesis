
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects;
import Enviroment.EnvObjects.ObjectParts.Shape;
import Enviroment.EnvObjFeatures.Emitors.AbstractEmitor;
import Enviroment.EnvObjFeatures.Senses.AbstractSense;
import Enviroment.EnvObjFeatures.EnumSenses;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import Enviroment.EnvObjFeatures.*;
import Enviroment.EnviromentalMap.GameMapBackup;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public class GameObjectBackup extends Object {
    /* object inner variables */
    private final EnumGameObjects type;         // type of GameObjectBackup
    private Point position;                     // integer positioning
    private Shape shape;                        // shape of GameObjectBackup
    private ArrayList<Point> vertices;          // sorted list of vertices
    private GameMapBackup map;                  // map this object belongs to
    private Rectangle r;                        // tight rectangle around
    private boolean selected;
    
    /* things affecting preception by sences */
    private Map <EnumSenses, AbstractSense> sences;
    private Map <EnumEmitors, AbstractEmitor> emitors;
    
    /* Constructors */
    public GameObjectBackup(EnumGameObjects type) {
        this(type, null, new Point());
    }
    
    public GameObjectBackup(EnumGameObjects type, GameMapBackup map) {
        this(type, map, new Point());
    }
    
    public GameObjectBackup(EnumGameObjects type, GameMapBackup map, int x, int y) {
        this(type, map, new Point(x, y));
    }
    
    public GameObjectBackup (EnumGameObjects type, GameMapBackup map, Point p) {
        this.type = type;
        this.position = new Point(p);
        this.vertices = new ArrayList();
        this.shape = null;
        this.map = map;
    }
 
/* Object types */
    public EnumGameObjects getType() {
        return this.type;
    }
    
/* General enviroment object related methods */
  public boolean setMap(GameMapBackup map) {
        if (this.map == null) return false;
        this.map = map;
        return true;
    }
    
    public void removeMap() {
        this.map = null;
    }
    
    public GameMapBackup getMap() {
        return this.map;
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
    
    public Point getPosition() {
        return new Point(this.position);
    }
    
    public void move (int speed, Point destination) {                           // TODO doimplementovat metodu pro pohyb
        if (!this.canMove()) {return;}
        
    }
    
    public boolean canMove() {
        return false;
    }
    
/* Shape and verticies */
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
    
    public Rectangle getRectangle() {
        return new Rectangle(this.r);
    }
    
    public int getAreaSize() {
        return (int) Math.round(this.shape.getArea());
    }
    
/* Sences related methods */    
    
    public AbstractSense getSense (EnumSenses type) {
        return this.sences.get(type);
    }
    
    public boolean getHasSense(EnumSenses type) {
        return this.sences.get(type) != null;
    }
    
    public void setSense(AbstractSense s) {
        this.sences.put(s.getType(), s);
    }
    
    public void removeSense(EnumSenses type) {
        this.sences.remove(type);
    }
    
/* Interactive and precevable object related methods */    
    public AbstractEmitor getEmitor(EnumEmitors type) {
        return this.emitors.get(type);
    }
    
    public void setEmitor(AbstractEmitor e) {
        this.emitors.put(e.getType(), e);
    }    

    public void removeEmitor(EnumEmitors type) {
        this.emitors.remove(type);
    }
    
/* Selection by user managing methods */
    
    public boolean getIsSelected() {
        return this.selected;
    }
    
    public void select() {
        this.selected = this.canBeSelected();
    }
    
    public void deselect() {
        this.selected = false;
    }

    public boolean canBeSelected() {
        return false;
    }
    
}
