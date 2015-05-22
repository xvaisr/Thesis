/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Senses;

import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Emitors.SmellEmitor;
import Enviroment.EnvObjFeatures.SensingGameObject;
import Enviroment.EnviromentalMap.CollisionDetector;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class SenseOfSmell extends AbstractSense {

    public SenseOfSmell(SensingGameObject preceptor) {
        super(preceptor);
    }

    @Override
    public Class<? extends Emitor> getPreciveable() {
        return SmellEmitor.class;
    }
    
    @Override
    public boolean canPrecive(Emitor e) {
        boolean precept = super.canPrecive(e);
        
        // if can't be detected, aditional check is useless
        if(!precept) {
            return precept;
        }
        
        Point o, p;
        o = e.getOriginator().getPosition();
        p = this.getPreceptor().getPosition();
        
        int distance = CollisionDetector.getDistance(o, p);
        int sensStrght = this.getSenseRange();
        int emitStrght = e.getEmitorStrenght();
        
        int volume = (emitStrght - distance);
        
        // is it strong enough smell ?
        return precept && ((volume + sensStrght) > 0);
    }
}

