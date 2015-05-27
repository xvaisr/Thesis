/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjects.GameObject;

public interface CarrierGameObject extends GameObject {
    
    public static final int DEFAULT_GRABBING_DISTANCE = 8;
    
    public boolean setCarrying(CarryableGameObject item);
    public boolean dropItem();
    public CarryableGameObject getItem();
}
