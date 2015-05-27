/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures.Emitors;

import Enviroment.EnvObjFeatures.DetectableGameObject;
import java.awt.Rectangle;

public interface Emitor {
    public static final int AUTO_RANGE = -1;
    
    public void setEmitorStrength(int strenght);    
    public int getEmitorStrenght();
    public void setDispersionRadius(int radius);
    public int getDispersionRadius();
    public Rectangle getDispersionArea();    
    public void setEmitorActive(boolean active);    
    public boolean getEmitorActive();
    public boolean setOriginator(DetectableGameObject originator);
    public DetectableGameObject getOriginator();    
    public void removeEmitor();
    
}
