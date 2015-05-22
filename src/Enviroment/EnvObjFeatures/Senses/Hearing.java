/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Senses;

import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Emitors.SoundEmitor;
import Enviroment.EnvObjFeatures.SensingGameObject;
import Enviroment.EnviromentalMap.CollisionDetector;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class Hearing extends AbstractSense {
    private static final int AUDIBILITY_THRESHOLD = 15;
    
    public Hearing(SensingGameObject preceptor) {
        super(preceptor);
    }

    @Override
    public Class<? extends Emitor> getPreciveable() {
        return SoundEmitor.class;
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
        int threshold = AUDIBILITY_THRESHOLD;
        
        // is it loud enough ?
        return precept && ((volume + sensStrght - threshold) > 0);
    }
}
