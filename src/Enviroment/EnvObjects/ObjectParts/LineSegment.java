/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects.ObjectParts;

import Enviroment.EnviromentalMap.CollisionDetector;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class LineSegment {
    private final Point k, l, u, no;
    public double size;
    private LineSegment kAttached, lAttached;

    public LineSegment(Point k, Point l) {
        this.k = k;
        this.l = l;
        // get vector
        this.u = new Point((l.x - k.x), (l.y - k.y));
        // get normal vector
        this.no = new Point(u.y, -u.x);
            
        // get size of segmet (equals size of vector)
        this.size = CollisionDetector.getRealDistance(k, l);
    }
    
    public double getSize() {
        return this.size;
    }
        
    public Point getK() {
        return new Point(k);
    }
        
    public Point getL() {
        return new Point(l);
    }
        
    public Point getVector() {
        return this.u;
    }
        
    public Point getNormalVector() {
        return this.no;
    }
        
    public boolean getPerpendicular(LineSegment seg) {
        Point v = seg.getVector();
        int result;
            
        // scalar multiplication of two vectors;
        result = (this.u.x * v.x) + (this.u.y * v.y);
        // if u.v == 0  vector are perpendicular
        return result == 0;
    }
        
    public boolean getParalel(LineSegment seg) {
        Point n = seg.getNormalVector();
        int result;
        // if u is perpendicular to the normal vector of other segment
        // segmets are paralel
            
        // scalar multiplication of two vectors;
        result = (this.u.x * n.x) + (this.u.y * n.y);
        // if u.v == 0  vector are perpendicular
        return result == 0;
    }
        
    public Point getIntersection(LineSegment seg) {
        Point n, m;     // normal vectors;
        n = this.getNormalVector();
        m = seg.getNormalVector();    
            
        double denominator = n.x*m.y - n.y*m.x;
            
        if (denominator == 0) {
            return null;
        }
            
        // derived from implicit equations of line
        int CA = -n.x * this.k.x - n.y * this.k.y;
        int CB = -m.x * seg.k.x - m.y * seg.k.y;
        
        // intersection point 
        double xp, yp; 
        xp = (n.y*CB - m.y*CA) / denominator;
        yp = (m.x*CA - n.x*CB) / denominator;
            
        // rounded coordinations
        Long xd, yd;
        xd = Math.round(xp);
        yd = Math.round(yp);
            
        Point intersector = new Point(xd.intValue(), yd.intValue());
        return intersector;
    }

    public boolean insideSegment(Point intersector) {
        boolean xAxis, yAxis;
        int xa, xb, ya, yb;
        xa = Math.min(this.k.x, this.l.x);
        xb = Math.max(this.k.x, this.l.x);
        ya = Math.min(this.k.y, this.l.y);
        yb = Math.max(this.k.y, this.l.y);
        
        xAxis = xa <= intersector.x && intersector.x <= xb;
        yAxis = ya <= intersector.y && intersector.y <= yb;
        
        return xAxis && yAxis;
    }

    public boolean getIntersect(LineSegment seg) {
        Point intersector = this.getIntersection(seg);
        if (intersector == null) {
            return false;
        }
        return (this.insideSegment(intersector) && seg.insideSegment(intersector));
    }
    
    public void attacheLineSegment(LineSegment seg) {
        if (seg.getK().equals(this.getL())) {
            this.lAttached = seg;
        }
        else if (seg.getL().equals(this.getK())) {
            this.kAttached = seg;
        }
    }
    
    public LineSegment getLAttached() {
        return this.lAttached;
    }
    
    public LineSegment getKAttached() {
        return this.kAttached;
    }
        
}
