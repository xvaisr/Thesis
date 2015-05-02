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
public interface CarrierGameObject extends GameObject {
    
    public static final int DEFAULT_GRABBING_DISTANCE = 8;
    
    public boolean setCarrying(CarryableGameObject item);
    public boolean dropItem();
    public CarryableGameObject getItem();
}