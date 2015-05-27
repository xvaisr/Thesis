/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjects.GameObject;

public interface CarryableGameObject extends GameObject {
    public boolean getIsCarryable();
    public boolean getIsCarried();
    public CarrierGameObject getIsCarriedBy();
    public boolean setCarrier(CarrierGameObject carrier);
    public boolean removeCarrier(CarrierGameObject carrier);
}
