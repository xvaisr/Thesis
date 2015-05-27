/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjects.GameObject;
import java.util.ArrayList;

public interface DetectableGameObject extends GameObject {
    public static final int EMITORS_INITIAL_CAPACITY = 10;
    
    public Emitor getEmitor(Class<? extends Emitor> emitorClass);
    public boolean getHasEmitor(Class<? extends Emitor> emitorClass);
    public ArrayList<Emitor> getEmitorList();
    public void setEmitor(Emitor e);
    public void removeEmitor(Emitor e);
    
}
