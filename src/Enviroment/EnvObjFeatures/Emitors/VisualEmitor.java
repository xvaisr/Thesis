/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Emitors;

import Enviroment.EnvObjFeatures.DetectableGameObject;

/**
 *
 * @author lennylinux
 */
public class VisualEmitor extends AbstractEmitor {

    public VisualEmitor(DetectableGameObject originator) {
        super(originator);
        this.setEmitorStrength(AUTO_RANGE);
    }

}
