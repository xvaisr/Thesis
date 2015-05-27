/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjects;

import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjFeatures.Emitors.VisualEmitor;
import Enviroment.EnvObjects.ObjectParts.LineSegment;
import Graphics.Painters.Painter;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class RectangleObject extends DetectableObjectPrototype implements DrawableGameObject{
    
    private Rectangle r;
    // DrawableGameObject related
    private volatile Color color;
    private volatile Painter painter;
    
    public RectangleObject(Rectangle r) {
        super();
        this.r = r;
        // Drawable object values
        this.color = Color.RED; // color will be redefined form outside
        this.painter = null;
        this.setEmitor(new VisualEmitor(this));
    }    
    
    @Override
    public synchronized void setPosition(Point position) {
        if (position == null) {
            return;
        }
        this.r.setLocation(position);
    }
    
    @Override
    public Point getPosition() {
        return new Point(this.r.getLocation());
    }
    
    @Override
    public ArrayList<Point> getVertices() {
        return new ArrayList();
    }
    
    @Override
    public ArrayList<Point> getInnerVerticesList() {
        return new ArrayList();
    }
    
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(this.r);
    }
    
    @Override
    public ArrayList<LineSegment> getShapeSegments() {
        return new ArrayList();
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setPainter(Painter p) {
        this.painter = p;
    }

    @Override
    public Painter getPainter() {
        return this.painter;
    }
}
