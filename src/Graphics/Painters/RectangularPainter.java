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
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public class RectangularPainter implements Painter {

    @Override
    public void paint(Graphics g, DrawableGameObject obj, Rectangle aov) {
        int ny;
        
        Rectangle r = obj.getBoundingBox();
        Point p = r.getLocation();
        r.translate(-aov.x, -aov.y); // shift rectangle to area of view
        
        ny = aov.height - r.y - r.height;       
        r.translate(0, (ny - r.y)); // shif rectangle to it's correct position
        
        g.setColor(obj.getColor());
        g.fillRect(r.x, r.y, r.width, r.height);
        
        g.setColor(Color.BLACK);
        g.drawRect(r.x, r.y, r.width, r.height);
    }
    
}
