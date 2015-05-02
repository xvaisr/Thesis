/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjects.GameObject;

/**
 *
 * @author lennylinux
 */
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
