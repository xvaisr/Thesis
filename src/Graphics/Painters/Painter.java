/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Graphics.Painters;

import Enviroment.EnvObjFeatures.DrawableGameObject;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public interface Painter {

    public void paint (Graphics g, DrawableGameObject o, Rectangle aov);
    
}
