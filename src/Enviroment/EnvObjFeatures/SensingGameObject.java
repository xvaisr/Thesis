/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjects.GameObject;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public interface SensingGameObject  extends GameObject {
    public static final int SENSES_INITIAL_CAPACITY = 10;
    
    public Sense getSense (Class<? extends Sense> senseClass);
    public boolean getHasSense(Class<? extends Sense> senseClass);
    public ArrayList<Sense> getSenseList();
    public void setSense(Sense s);
    public void removeSense(Sense s);
    
}
