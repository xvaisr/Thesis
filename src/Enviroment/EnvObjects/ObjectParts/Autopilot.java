/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjects.ObjectParts;

import Enviroment.EnvObjFeatures.MoveableGameObject;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class Autopilot  {

    private MoveableGameObject managed;
    
    
    public Autopilot(MoveableGameObject managed) {
        if (managed == null) {
            throw new NullPointerException("Autopilot can't exist on its own. Managed MovableGameObject reference is required!");
        }
        this.managed = managed;
    }
    
    public void setSpeed(int speed) {
        
    }
    
    public int getSpeed() {
        return 0;
    }
    
    public void setDestination(Point dest) {
    }
    
    public Point getDestinattion() {
        return null;
    }
    public boolean getCanMove() {
        return false;
    }
    public boolean move(double unifiedDistance)  {
        return false;
    }
    
    
}
