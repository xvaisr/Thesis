/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Senses;

import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Emitors.SoundEmitor;
import Enviroment.EnvObjFeatures.SensingGameObject;

/**
 *
 * @author lennylinux
 */
public class Hearing extends AbstractSense {
    
    public Hearing(SensingGameObject preceptor) {
        super(preceptor);
    }

    @Override
    public Class<? extends Emitor> getPreciveable() {
        return SoundEmitor.class;
    }
}
