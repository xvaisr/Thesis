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
public interface CollidableGameObject extends GameObject {
    
    public boolean colides(CollidableGameObject another);
    
    
}
