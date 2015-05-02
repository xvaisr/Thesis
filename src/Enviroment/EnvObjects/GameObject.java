/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects;

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
public interface GameObject {
    
    public boolean setMapContainer(MapContainer mc);    
    public MapContainer getMapContainer();
    public void leaveMapContainer();
    
    public void setPosition(int x, int y);
    public void setPosition(Point position);
    public Point getPosition();
    
    public void setNewShape(Shape s);
    public ArrayList<Point> getVertices();
    public ArrayList<Point> getInnerVerticesList();
    public Rectangle getBoundingBox();
    public ArrayList<LineSegment> getShapeSegments();
    
}
