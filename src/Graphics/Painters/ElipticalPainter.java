/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.Painters;

import Enviroment.EnvObjFeatures.DrawableGameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public class ElipticalPainter implements Painter {

    @Override
    public void paint(Graphics g, DrawableGameObject obj, Rectangle aov) {
        int ny;
        
        Rectangle r = obj.getBoundingBox();
        r.translate(-aov.x, -aov.y); // shift rectangle to area of view
        
        ny = aov.height - r.y - r.height;       
        r.translate(0, (ny - r.y)); // shif rectangle to it's correct position
        
        g.setColor(obj.getColor());
        g.fillOval(r.x, r.y, r.width, r.height);
        
        g.setColor(Color.BLACK);
        g.fillOval(r.x, r.y, r.width, r.height);
        
    }
    
}
