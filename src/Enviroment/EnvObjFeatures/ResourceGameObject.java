/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjects.GameObject;
import Enviroment.EnvObjects.Resources.ResourceTypes;

/**
 *
 * @author lennylinux
 */
public interface ResourceGameObject extends GameObject {
    public ResourceTypes getResourceType();
}
