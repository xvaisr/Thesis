/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Senses;

import Enviroment.EnvObjFeatures.DetectableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Emitors.VisualEmitor;
import Enviroment.EnvObjFeatures.SensingGameObject;
import Enviroment.EnviromentalMap.CollisionDetector;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class Sight extends AbstractSense {

    public Sight(SensingGameObject preceptor) {
        super(preceptor);
    }
    
    @Override
    public boolean canPrecive(Emitor e) {
        // if it is emitor of diferent sense, it is deactivated, 
        if ((e == null) || !e.getEmitorActive() // or there is mistake in code
                        || (e.getClass() != this.getPreciveable()))
        {
            return false;
        }
        
        /* 
           If dispersion area of emitor doesn't intersect detection area, 
           originator cannot be precived by preceptor.
         */
        if (!this.getDetectionArea().intersects(e.getDispersionArea())) {
            return false;
        }
        
        // Nonexisting object cannot be detected
        DetectableGameObject originator = e.getOriginator();
        if (originator == null) {
            return false;
        }
        
        boolean inRange = false;
        Point p;
        ArrayList<Point> vertices = originator.getVertices();
        
        // if object doesn't have any verticies, ve need mid point;
        vertices.add(originator.getPosition());
        
        p = this.getPreceptor().getPosition();
        
        // if any vertex is in ranage object can bee seen
        for (Point o : vertices) {
            int delta = CollisionDetector.getDistance(o, p);
            inRange = (delta > (this.getSenseRange() + e.getEmitorRange()));
            
            if (inRange) break;
        }
        
        return inRange;
    }

    @Override
    public Class<? extends Emitor> getPreciveable() {
        return VisualEmitor.class;
    }

}
