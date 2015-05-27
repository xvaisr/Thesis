/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjects.GameObject;

public interface PoweredGameObject extends GameObject{
    
    public static final int DEFAULT_ENERGY_LEVEL = 100;
    public static final int DEFAULT_ENERGY_RESERVES = 15;
    
    public void setBaseEnergyLevel(int en);
    public void setEnergyLevel(int en);
    public void increaseBaseEnergyLevel(int en);
    public void increaseEnergyLevel(int en);
    public int getEnergyLevel();
    public void consumeEnergy(int en);
    public boolean isOverCharged();
    public boolean isLowOnEnergy();
    public boolean isEnergyDepleted();
    
}
