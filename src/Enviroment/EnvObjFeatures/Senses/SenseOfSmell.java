/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Senses;

import Enviroment.EnvObjFeatures.DetectableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Emitors.SmellEmitor;
import Enviroment.EnvObjFeatures.SensingGameObject;
import Enviroment.EnviromentalMap.CollisionDetector;
import Enviroment.EnviromentalMap.Compas;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

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
    public int preceptionStrenght(Emitor e) {
        if ((e == null) || !e.getEmitorActive() // or there is mistake in code
                        || (e.getClass() != this.getPreciveable()))
        {
            return NO_PRECEPT_STRENGHT;
        }
        
        Point o, p;
        o = e.getOriginator().getPosition();
        p = this.getPreceptor().getPosition();
        
        int distance = CollisionDetector.getDistance(o, p);
        int sensStrght = this.getSenseRange();
        int emitStrght = e.getEmitorStrenght();
        
        int volume = (emitStrght - distance);
        
        // is it strong enough smell ?
        return (volume + sensStrght);
    }

    @Override
    public List<String> getPercepts() {
        LinkedList<String> preceptList = new LinkedList();
        for (DetectableGameObject obj : this.getCache()) {
            SmellEmitor smellEmitor = (SmellEmitor) obj.getEmitor(SmellEmitor.class);
            String direction;
            int smell, strenght;
            Point p, e;
            
            smell = smellEmitor.getEmitorSmell();
            p = this.getPreceptor().getPosition();
            e = obj.getPosition();
            direction = Compas.getDirectionName(e, p);
            strenght = this.preceptionStrenght(smellEmitor);
            
            String preception = "smell(" + smell + ", " + direction + ", " + strenght + ")";
            preceptList.add(preception);
        }
        return preceptList;
    }
}

