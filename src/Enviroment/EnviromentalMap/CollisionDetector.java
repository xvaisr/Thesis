/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnviromentalMap;

import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjects.ObjectParts.LineSegment;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

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
        if (!aContains && !bContains) { // -> no collision
            return false; 
        }
        
        boolean intersects = false;
        // create segments for both polygons
        ArrayList<LineSegment> aSegs, bSegs;
        aSegs = a.getShapeSegments();
        bSegs = b.getShapeSegments();
        
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
    
}
