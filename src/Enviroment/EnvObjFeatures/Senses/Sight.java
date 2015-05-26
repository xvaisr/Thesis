/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Senses;

import Enviroment.EnvObjFeatures.DetectableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Emitors.VisualEmitor;
import Enviroment.EnvObjFeatures.SensingGameObject;
import Enviroment.EnvObjects.ObjectParts.LineSegment;
import Enviroment.EnviromentalMap.CollisionDetector;
import Enviroment.EnviromentalMap.Compas;
import RTreeAlgorithm.Pair;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author lennylinux
 */
public class Sight extends AbstractSense {
    
    private int closing;
    private int close;
    private final ArrayList<Pair<LineSegment, DetectableGameObject>> walls;
    
    public Sight(SensingGameObject preceptor) {
        super(preceptor);
        this.walls = new ArrayList();
        this.calculateClasification();
    }
    
    @Override
    public int preceptionStrenght(Emitor e) {
        // if it is emitor of diferent sense, it is deactivated, 
        if ((e == null) || !e.getEmitorActive() // or there is mistake in code
                        || (e.getClass() != this.getPreciveable()))
        {
            return NO_PRECEPT_STRENGHT;
        }
        
        /* 
           If dispersion area of emitor doesn't intersect detection area, 
           originator cannot be precived by preceptor.
         */
        if (!this.getDetectionArea().intersects(e.getDispersionArea())) {
            return NO_PRECEPT_STRENGHT;
        }
        
        // Nonexisting object cannot be detected
        DetectableGameObject originator = e.getOriginator();
        if (originator == null) {
            return NO_PRECEPT_STRENGHT;
        }
        
        boolean inRange = false;
        Point p;
        ArrayList<Point> vertices = originator.getVertices();
        
        // if object doesn't have any verticies, ve need mid point;
        vertices.add(originator.getPosition());
        
        p = this.getPreceptor().getPosition();
        
        // if any vertex is in ranage object can bee seen
        int str = NO_PRECEPT_STRENGHT;
        for (Point o : vertices) {
            int delta = CollisionDetector.getDistance(o, p);
            str = delta - (this.getSenseRange() + e.getEmitorStrenght());
            inRange = str > 0;
            if (inRange) break;
        }
        
        return ((inRange)? 1 : NO_PRECEPT_STRENGHT);
    }

    @Override
    public Class<? extends Emitor> getPreciveable() {
        return VisualEmitor.class;
    }

    @Override
    public List<String> getPercepts() {
        LinkedList<String> precepts = new LinkedList();
        
        ArrayList<Point> directionVectors;
        ArrayList<Point> intersections = new ArrayList();
        Point me = this.getPreceptor().getPosition();
        int range = this.getSenseRange();
        
        directionVectors = Compas.getDIrectionList();
        
        // measuring wall distance
        for (Pair<LineSegment, DetectableGameObject> wall : this.walls) {
            LineSegment seg = wall.getA();            
            for (Point d : directionVectors) {
                Point focus = new  Point((range * d.x), (range * d.y));
                LineSegment ray = new LineSegment(me, focus);
                Point intersector = seg.getIntersection(ray);
                if (ray.insideSegment(intersector) &&
                    seg.insideSegment(intersector)) 
                {
                    intersections.add(intersector);
                }
            }
            
            int d;
            String name, distance, direction, precept;
            name = wall.getB().getClass().getSimpleName().toLowerCase();

            for (Point p : intersections) {
                d = CollisionDetector.getDistance(me, p);
                direction = Compas.getDirectionName(me, p);
                distance = this.getDistanceClass(d);
                
                precept = "sight(" + name + ", " + direction + ", " + distance + ")";
                precepts.add(precept);
            }
        }
        
        ArrayList<DetectableGameObject> additionalObjects;
        additionalObjects = this.getDynamicMapObjects();
        
        ArrayList<LineSegment> visibleWalls;
        visibleWalls = this.getWalls();
        
        
        for(DetectableGameObject obj : additionalObjects) {
            boolean behind = false;
            for (LineSegment wall : visibleWalls) {
                LineSegment ray;
                ray = new LineSegment(me, obj.getPosition());
                if (ray.getIntersect(wall)) {
                    behind = true;
                    break;
                }
            }
            if (!behind) {
                int d;
                String name, distance, direction, precept;
                name = obj.getClass().getSimpleName().toLowerCase();
                
                d = CollisionDetector.getDistance(me, obj.getPosition());
                direction = Compas.getDirectionName(me, obj.getPosition());
                distance = this.getDistanceClass(d);
                
                precept = "sight(" + name + ", " + direction + ", " + distance + ")";
                precepts.add(precept);
            }
        }
        
        return precepts;
    }
    
    @Override
    public void setSenseRange(int range) {
        super.setSenseRange(range);
        this.calculateClasification();
    }
    
    @Override
    public void updatePreception() {
        super.updatePreception();
        this.walls.clear();
        this.storeLineSegments();
    }
    
    private void storeLineSegments() {
        ArrayList<DetectableGameObject> cached;
        cached = this.getCache();
        
        Point me = this.getPreceptor().getPosition();
        
        for (DetectableGameObject detectable : cached) {
            LineSegment ray;
            ray = new LineSegment(me, detectable.getPosition());
            
            for (LineSegment wall : detectable.getShapeSegments()) {
                Point intersector = ray.getIntersection(wall);
                if (ray.insideSegment(intersector) &&
                    wall.insideSegment(intersector)) 
                {
                    Pair wallInfo = new Pair(wall, detectable);
                    this.walls.add(wallInfo);
                    
                    LineSegment wing;
                    
                    wing = wall.getKAttached();
                    ray = new LineSegment(me, wing.getL());
                    if (!ray.getIntersect(wall)) {
                        wallInfo = new Pair(wing, detectable);
                        this.walls.add(wallInfo);
                    }
                    
                    wing = wall.getLAttached();
                    ray = new LineSegment(me, wing.getK());
                    if (!ray.getIntersect(wall)) {
                        wallInfo = new Pair(wing, detectable);
                        this.walls.add(wallInfo);
                    }
                    break;
                }
            }
        }
    }
    
    public ArrayList<LineSegment> getWalls() {
        ArrayList<LineSegment> wallList = new ArrayList();
        
        for (Pair<LineSegment, DetectableGameObject> wall : this.walls) {
            wallList.add(wall.getA());
        }
        return wallList;
    }
    
    private String getDistanceClass(int distance) {
        if (distance <= this.close) {
            return "close";
        }
        if (distance > this.closing) {
            return "faraway";
        }
        return "closing";
    }

    private void calculateClasification() {
        int range = this.getSenseRange();
        this.closing = (range / 3) * 2;
        this.close = (range * 15) / 100;
    }
}
