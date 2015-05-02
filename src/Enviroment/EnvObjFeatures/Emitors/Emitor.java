/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Emitors;

import Enviroment.EnvObjFeatures.DetectableGameObject;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public interface Emitor {
    public static final int AUTO_RANGE = -1;
    
    public void setEmitorRange(int range);    
    public int getEmitorRange();    
    public Rectangle getDispersionArea();    
    public void setEmitorActive(boolean active);    
    public boolean getEmitorActive();
    public boolean setOriginator(DetectableGameObject originator);
    public DetectableGameObject getOriginator();    
    public void removeEmitor();
    
}
