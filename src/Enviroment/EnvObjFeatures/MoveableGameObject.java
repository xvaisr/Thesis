/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Enviroment.EnvObjFeatures;

import java.awt.Point;

public interface MoveableGameObject {
    public static final int DEFAULT_SPEED = 15;
    
    public void setSpeed(int speed);
    public int getSpeed();
    public void setDestination(Point dest);
    public Point getDestinattion();
    public boolean getCanMove();
    public boolean move(double unifiedDistance);
    
    public int getPedometr();
    
}
