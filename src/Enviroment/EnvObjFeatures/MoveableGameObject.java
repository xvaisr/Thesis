/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures;

import java.awt.Point;

/**
 *
 * @author lennylinux
 */
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
