/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjects;

import Enviroment.EnvObjects.ObjectParts.LineSegment;
import Enviroment.EnvObjects.ObjectParts.Shape;
import Enviroment.EnviromentalMap.MapContainer;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

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
