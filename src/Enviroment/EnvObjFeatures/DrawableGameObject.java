/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjects.GameObject;
import Graphics.Painters.Painter;
import java.awt.Color;

public interface DrawableGameObject extends GameObject {
    
    public void setColor(Color c);
    public Color getColor();
    public void setPainter(Painter p);
    public Painter getPainter();
    
}
