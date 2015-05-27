/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjects.GameObject;
import Enviroment.EnvObjects.Resources.ResourceTypes;

public interface ResourceGameObject extends GameObject {
    public ResourceTypes getResourceType();
}
