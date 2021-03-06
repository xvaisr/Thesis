/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects.ObjectParts;
import Sort.QuickSort;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public final class Shape implements Cloneable {
    /* object default constants */
    private static final int VERTEX_COUNT = 10;
    private static final double DEFAULT_AREA = 0.0;
    
    private ArrayList<Coords> vertices;
    private final Point midpoint;
    private boolean change;
    private boolean lock;
    private Double area;
    private Rectangle rectangle;

    public Shape() {
        this(new Point());
    }
    
    public Shape(int x, int y) {
        this(new Point(x,y));
    }
    
    public Shape(Point midpoint) {
        this.midpoint = new Point(midpoint);
        this.vertices = new ArrayList(VERTEX_COUNT);
        this.change = false;
        this.area = Shape.DEFAULT_AREA;
        this.rectangle = new Rectangle();
        this.rectangle.translate(this.midpoint.x, this.midpoint.y);
        this.lock = false;
        
    }
    
    public void finished() {
        this.lock = true;
    }
    
    public void addVertices(ArrayList<Point> list) {
        if (this.lock) return;
        for (Point p : list) {
            this.addVertex(p);
        }
    }
    
    public void addVertex(int x, int y) {
        this.addVertex(new Point(x, y));
    }

    private void addVertex(Point p) {
        if (this.lock) return;
        this.change = true; // set change flag, area needs to be computed again
        
        // translates vertex around midpoint
        p.translate(-this.midpoint.x, -this.midpoint.y);
        
        // creates coordinations for comparsion
        Coords c = new Coords(p);
        
        // linear insert sort
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).compareTo(c) > 0) {
                this.vertices.add(i, c);
                return;
            }    
        }
        this.vertices.add(c);
    }
    
    public Point getMidpoint() {
        return new Point(midpoint);
    }
    
    public ArrayList<Point> getVertices() {
        this.recompute();
        ArrayList<Point> list = new ArrayList(this.vertices.size());
        for(Coords c : this.vertices) {
            list.add(c.getPoint());
        }
        return list;
    }
    
    public Rectangle getRectangle() {
        this.recompute();
        return new Rectangle(this.rectangle);
    }
    
    public Double getArea() {
        // this ensures only one computation of area for each shape
        this.recompute();
        return this.area;
    }

    private void recompute() {
        if (!this.change) {
            return;
        }
        
        this.change = false;
        if (this.vertices.size() < 3) { // at least 3 vertices for triangle
            this.area = Shape.DEFAULT_AREA;
            return;
        }
        
        // inicializing auxiliary variables
        this.area = Shape.DEFAULT_AREA;
        this.rectangle = new Rectangle(this.midpoint);
        Point a, b;
        a = null;
        
        // make sure verticies are sorted
         this.vertices.clone();
        ArrayList<Coords> list;
        list = (ArrayList<Coords>) QuickSort.sortObjectlist(this.vertices);
        this.vertices.clear();
        this.vertices.addAll(list);
        
        
        // compute polygon area
        for (Coords c : this.vertices) {
            b = c.getPoint();
            this.rectangle.add(b);
            if (a != null) {
                double avgHeight = (a.getY() + b.getY()) / 2;
                double diffWidth = (b.getX() - a.getY());
                this.area += avgHeight * diffWidth;
            }
            a = b;
        }
    }
    
    // serves only for holding points and theire angles as couple for comparsion
    private class Coords implements Comparable<Coords>{
        private final Point p;
        private final double angle;

        public Coords(int x, int y) {
            this(new Point(x, y));
        }
        
        public Coords(Point p) {
            this.p = p;
            this.angle = Math.atan2(p.getX(), p.getY());
        }
        
        public Point getPoint() {
            return new Point(this.p);
        }

        public double getAngle() {
            return angle;
        }

        @Override
        public int compareTo(Coords c) {
            return ((Double) this.angle).compareTo((Double) c.getAngle());
        }
                
    }
    
    @Override
    public Object clone() {
        Shape clon = new Shape(this.midpoint);
        this.recompute();
        clon.vertices = (ArrayList<Coords>) this.vertices.clone();
        clon.area = this.area;
        clon.change = this.change;
        return clon;
    }
    
}
