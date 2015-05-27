/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Graphics.Painters;

import Enviroment.EnvObjFeatures.DrawableGameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;


public class PolygonPainter implements Painter {

    @Override
    public void paint(Graphics g, DrawableGameObject obj, Rectangle aov) {
        ArrayList<Point> verticies;
        Polygon p;
        int cy, ny;
        
        p = new Polygon();
        
        verticies = obj.getVertices();
        for (Point v : verticies) {
            v.translate(-aov.x, -aov.y); // shift point to window from original place
            
            cy = v.y;
            ny = (aov.height - cy);
            v.translate(0, (ny - cy)); // switch y-axis direction 
            
            p.addPoint(v.x, v.y);
        }
        
       /* if (verticies.size() > 0 && verticies.get(0) != null) {
            Point v = verticies.get(0);
            p.addPoint(v.x, v.y);
        } */
        
        g.setColor(obj.getColor());
        g.fillPolygon(p);
        
        g.setColor(Color.BLACK);
        g.drawPolygon(p);
        
        /* just for debug purposes * /
        Rectangle r = obj.getBoundingBox();
        r.translate(-aov.x, -aov.y); // shift rectangle to area of view
        
        ny = aov.height - r.y - r.height;       
        r.translate(0, (ny - r.y)); // shif rectangle to it's correct position
 
        // g.setColor(Color.BLACK);
        // String s = "id: " + obj.getID();
        // g.drawString(s, r.x, (r.y + r.height/2));

        g.setColor(Color.red);
        g.drawRect(r.x, r.y, r.width, r.height);
        
        Point v = obj.getPosition();
        v.translate(-aov.x, -aov.y); // shift point to window from original place
            
            cy = v.y;
            ny = (aov.height - cy);
            v.translate(0, (ny - cy)); // switch y-axis direction 
        
        r = new Rectangle(v);
        r.grow(3, 3);
        
        
        g.fillRect(r.x, r.y, r.width, r.height);
       
        // */
    }
    
}
/*
    private void drawObsicle(Graphics g, GameObjectBackup obj, Rectangle aov) {
        ArrayList<Point> verticies;
        Polygon p;
        int cy, ny;
        
        p = new Polygon();
        
        verticies = obj.getVertices();
        for (Point v : verticies) {
            v.translate(-aov.x, -aov.y); // shift point to window from original place
            
            cy = v.y;
            ny = (aov.height - cy);
            v.translate(0, (ny - cy)); // switch y-axis direction
            
            p.addPoint(v.x, v.y);
        }
        
        g.setColor(new Color(85, 51, 17));
        g.fillPolygon(p);
        
        g.setColor(Color.BLACK);
        g.drawPolygon(p);
    }

*/