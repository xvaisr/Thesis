/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjects.GameObject;

public interface CollidableGameObject extends GameObject {
    
    public boolean colides(CollidableGameObject another);
    
    
}
