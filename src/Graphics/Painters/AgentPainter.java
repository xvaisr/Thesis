/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.Painters;

import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjFeatures.Senses.Sight;
import Enviroment.EnvObjFeatures.SensingGameObject;
import Enviroment.EnvObjects.ObjectParts.LineSegment;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public class AgentPainter implements Painter {
    private static final int RADIUS = 6;
    
    @Override
    public void paint(Graphics g, DrawableGameObject obj, Rectangle aov) {
        int ny;
        
        Rectangle r = obj.getBoundingBox();
        r.translate(-aov.x, -aov.y); // shift rectangle to area of view
        
        ny = aov.height - r.y - r.height;       
        r.translate(0, (ny - r.y)); // shif rectangle to it's correct position
        
        // r.grow(RADIUS, RADIUS);
        
        g.setColor(obj.getColor());
        g.fillOval(r.x, r.y, r.width, r.height);
        
        g.setColor(Color.BLACK);
        g.drawOval(r.x, r.y, r.width, r.height);
        
        Sight s;
        if (obj instanceof SensingGameObject) {
            s = (Sight) ((SensingGameObject) obj).getSense(Sight.class);
        }
        else {
            return;
        }
        g.setColor(Color.MAGENTA);
        for (LineSegment wall : s.getWalls()) {
            
            Point k, l;
            int ky,ly;
            
            k = new Point(wall.getK());
            l = new Point(wall.getL());

            k.translate(-aov.x, -aov.y); // shift point to window from original place
            l.translate(-aov.x, -aov.y); // shift point to window from original place
            
            ky = k.y;
            ly = l.y;
            ny = (aov.height - ky);
            k.translate(0, (ny - ky)); // switch y-axis direction 
            ny = (aov.height - ly);
            l.translate(0, (ny - ly)); // switch y-axis direction 
            
            g.drawLine(k.x, k.y, l.x, l.y);
            g.drawLine(k.x + 1, k.y, l.x + 1, l.y);
            g.drawLine(k.x - 1, k.y, l.x - 1, l.y);
        }
        
        r = s.getDetectionArea();
        r.translate(-aov.x, -aov.y); // shift rectangle to area of view
        
        ny = aov.height - r.y - r.height;       
        r.translate(0, (ny - r.y)); // shif rectangle to it's correct position
        
        g.drawOval(r.x, r.y, r.width, r.height);
        
    }
    
}
