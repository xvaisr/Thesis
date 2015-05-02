/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects.ObjectParts;

import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjFeatures.MoveableGameObject;
import Enviroment.EnviromentalMap.MapContainer;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

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
