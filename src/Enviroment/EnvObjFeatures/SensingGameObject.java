/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjects.GameObject;
import java.util.ArrayList;

public interface SensingGameObject  extends GameObject {
    public static final int SENSES_INITIAL_CAPACITY = 10;
    
    public Sense getSense (Class<? extends Sense> senseClass);
    public boolean getHasSense(Class<? extends Sense> senseClass);
    public ArrayList<Sense> getSenseList();
    public void setSense(Sense s);
    public void removeSense(Sense s);
    
}
