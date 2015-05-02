/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjects.GameObject;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public interface DetectableGameObject extends GameObject {
    public static final int EMITORS_INITIAL_CAPACITY = 10;
    
    public Emitor getEmitor(Class<? extends Emitor> emitorClass);
    public boolean getHasEmitor(Class<? extends Emitor> emitorClass);
    public ArrayList<Emitor> getEmitorList();
    public void setEmitor(Emitor e);
    public void removeEmitor(Emitor e);
    
}
