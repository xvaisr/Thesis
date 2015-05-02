/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Backup;

import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjects.ObjectParts.LineSegment;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class CollisionDetector {
    
    public static boolean getCollide(CollidableGameObject a, CollidableGameObject b) {
        // first check intersection of bounding boxes
        Rectangle aBox, bBox;
        aBox = a.getBoundingBox();
        bBox = b.getBoundingBox();
        
        // if there is not intersection, there can't be collision
        if (!aBox.intersects(bBox)) {
            return false;
        }
        
        // check if for both objects is true following statement:
        // Bounding box of object contains at least one vertex of the other object
        boolean aContains = false, bContains = false;
        ArrayList<Point> aVerticies, bVerticies;
        
        aVerticies = a.getInnerVerticesList();
        for (Point point : aVerticies) {
            bContains = bBox.contains(point);
            if (bContains) {
                break;
            }
        }
        
        bVerticies = b.getInnerVerticesList();
        for (Point point : bVerticies) {
            aContains = aBox.contains(point);
            if (aContains) {
                break;
            }
        }
        
        // if there are not verticies from both objects in area of intersection
        if (!aContains || !bContains) { // -> no collision
            return false; 
        }
        
        boolean intersects = false;
        // create segments for both polygons
        ArrayList<LineSegment> aSegs, bSegs;
        aSegs = CollisionDetector.createSegments(aVerticies);
        bSegs = CollisionDetector.createSegments(bVerticies);
        
        for (LineSegment aSegment : aSegs) {
            for (LineSegment bSegment : bSegs) {
                
                intersects = aSegment.getIntersect(bSegment);
                if (intersects) {
                    break;
                }
            }
            if (intersects) {
                break;
            }
        }
        
        return intersects;
    }
    
    public static int getDistance(Point a, Point b) {
        // Pythagorean Theorem
        double delta;
        Long distance;
        
        delta = CollisionDetector.getRealDistance(a, b);         
        distance = Math.round(delta);
        
        return distance.intValue();
    }
    
    public static double getRealDistance(Point a, Point b) {
        // Pythagorean Theorem
        int x, y;
        x = a.x - b.x;
        y = a.y - b.y;
        
        return Math.sqrt( ((double) (x*x + y*y) ));
    }
    
    private static ArrayList<LineSegment> createSegments(ArrayList<Point> verticies) {
        ArrayList<LineSegment> segments = new ArrayList(10);
        
        int size = verticies.size();
        // we need at least two points to create segment
        if (size < 2) {
           return segments;
        }
        
        // create segments
        for (int i = 0; i < size; i++) {
            Point k, l;
            k = new Point(verticies.get(i));
            l = new Point(verticies.get((i + 1) % size));
            segments.add(new LineSegment(k, l));
        }
        
        return segments;
        
    }
    
}
