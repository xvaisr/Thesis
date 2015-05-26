/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects;

import Enviroment.EnvObjects.GameObject;
import Enviroment.EnvObjects.ObjectParts.LineSegment;
import Enviroment.EnvObjects.ObjectParts.Shape;
import Enviroment.EnviromentalMap.MapContainer;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * 
 * @author lennylinux
 */

public class GameObjectPrototype implements GameObject {
    
    private volatile Point ps;                      // integer positioning
    private volatile Shape shape;                   // shape of GameObject
    private final ArrayList<Point> vertices;        // sorted list of vertices
    private final ArrayList<LineSegment> segments;  // list of LineSegments
    private volatile MapContainer map;              // map this object belongs to
    private volatile Rectangle r;                   // tight rectangle around

    public GameObjectPrototype() {
        this(new Point());
    }

    public GameObjectPrototype(int x, int y) {
        this(new Point(x, y));
    }
    
    public GameObjectPrototype(Point position) {
        this(null, position);
    }
    
    public GameObjectPrototype(MapContainer mc, int x, int y) {
        this(mc, new Point(x, y));
    }
    
    public GameObjectPrototype(MapContainer mc, Point position) {
        this.map = mc;
        this.ps = new Point(position);
        this.vertices = new ArrayList();
        this.segments = new ArrayList();
        this.shape = null;
        this.r = null;
        
    }
    
    private void translateVertices(Point cp, Point np) {
        synchronized(this.vertices) {
            int x = np.x - cp.x;
            int y = np.y - cp.y;
            for (Point p : this.vertices) {           
                p.translate(x, y);
                p.equals(p);
            }
        }
    }
    
    private void createSegments() {
        ArrayList<LineSegment> segList;
        segList = new ArrayList();
        synchronized(this.segments) {
            this.segments.clear();
            synchronized(this.vertices) {
                int size = this.vertices.size();
                if (size < 2) {
                    return;
                }
                
                LineSegment current = null, prev = null;
                for (int i = 0; i < size; i++) {
                    Point k, l;
                    k = this.vertices.get(i);
                    l = this.vertices.get((i + 1) % size);
                    current = new LineSegment(k, l);
                    segList.add(current);
                    
                    if (prev != null) {
                        current.attacheLineSegment(prev);
                        prev.attacheLineSegment(current);
                    }
                    
                    prev = current;
                }
                if (segList.size() > 2) {
                    current =  segList.get(0);
                    if ((prev != null) && (prev != current)) {
                        current.attacheLineSegment(prev);
                        prev.attacheLineSegment(current);
                    }
                }
            }
            this.segments.addAll(segList);
        }
    }
    
    @Override
    public boolean setMapContainer(MapContainer mc) {
        if (this.map != null) {
            return false;
        }
        this.map = mc;
        return true;
    }

    @Override
    public MapContainer getMapContainer() {
        return this.map;
    }

    @Override
    public void leaveMapContainer() {
        this.map.removeGameObject(this);
    }

    @Override
    public void setPosition(int x, int y) {
        this.setPosition(new Point(x, y));
    }

    @Override
    public synchronized void setPosition(Point position) {
        if (position == null || position.equals(this.ps)) {
            return;
        }
        // set location
        this.ps.setLocation(position);
        
        // clear old cashed data
        synchronized(this.vertices) {
            this.vertices.clear();
            synchronized(this.segments) {
                this.segments.clear();
            }
            this.r = new Rectangle(this.ps);
        }
    }

    @Override
    public Point getPosition() {
        return new Point(this.ps);
    }

    @Override
    public void setNewShape(Shape s) {
        synchronized(this.vertices) {
            // clear old data
            this.vertices.clear();
            synchronized(this.segments) {
                this.segments.clear();
            }
            this.r = new Rectangle(ps);
        }
        
        // set new shape 
        this.shape = s;
        if (this.shape == null) { // if clearing shape of object all is done
            return; 
        }

        // lock the shape so it can't be changed outside this object
        this.shape.finished();
    }

    @Override
    public ArrayList<Point> getVertices() {
        ArrayList<Point> vertexList = new ArrayList();
        synchronized (this.vertices) {
            if (this.vertices.isEmpty() && this.shape != null) {
                this.vertices.addAll(this.shape.getVertices());
                translateVertices(this.shape.getMidpoint(), this.ps);
            }

            for(Point p : this.vertices) {
                vertexList.add(new Point(p));
            }
        }
        return vertexList;
    }

    @Override
    public ArrayList<Point> getInnerVerticesList() {
        ArrayList<Point> list;
        synchronized (this.vertices) {
            if (this.vertices.isEmpty()) {
                list = this.getVertices();
            }
            else {
                list = (ArrayList<Point>) this.vertices.clone();
            }    
        }
        return list;
    }
    
    @Override
    public Rectangle getBoundingBox() {
        if (this.r.isEmpty()) {
            ArrayList<Point> list = getInnerVerticesList();
            
            // if midpoint of the shape is shifted outside area bounded
            // by verticies, bounding box is deneraly greater than necessary
            Rectangle box = new Rectangle(this.ps);
            for (Point point : list) {
                box.add(point);
            }
            this.r = box;
        }
        return new Rectangle(this.r);
    }
    
    @Override
    public ArrayList<LineSegment> getShapeSegments() {
        ArrayList<LineSegment> segmentList;
        synchronized (this.segments) {
            if (this.segments.isEmpty() && this.shape != null) {
                this.createSegments();
            }
            segmentList = ((ArrayList<LineSegment>) this.segments.clone());
        }
        return segmentList;
    }
}
