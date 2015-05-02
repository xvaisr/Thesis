/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.Painters;

import Enviroment.EnvObjFeatures.DrawableGameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public class RectanglePainter implements Painter {

    @Override
    public void paint(Graphics g, DrawableGameObject obj, Rectangle aov) {
        int ny;
        
        Rectangle r = obj.getBoundingBox();
        Point p = r.getLocation();
        r.translate(-aov.x, -aov.y); // shift rectangle to area of view
        
        ny = aov.height - r.y - r.height;       
        r.translate(0, (ny - r.y)); // shif rectangle to it's correct position
        
        g.setColor(Color.BLACK);
        String s = "x: " + p.x + "\ny: " + p.y;
        g.drawString(s, r.x, (r.y + r.height/2));
        
        g.setColor(obj.getColor());
        // g.fillRect(r.x, r.y, r.width, r.height);
        
        // g.setColor(Color.BLACK);
        g.drawRect(r.x, r.y, r.width, r.height);
    }
    
}
