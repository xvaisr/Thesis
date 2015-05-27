/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Enviroment.EnvObjFeatures.Emitors;

import Enviroment.EnvObjFeatures.DetectableGameObject;

public class VisualEmitor extends AbstractEmitor {

    public VisualEmitor(DetectableGameObject originator) {
        super(originator);
        this.setEmitorStrength(AUTO_RANGE);
    }

}
