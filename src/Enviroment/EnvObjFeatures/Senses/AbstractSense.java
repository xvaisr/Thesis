/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Senses;
import Enviroment.EnvObjFeatures.DetectableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.SensingGameObject;
import Enviroment.EnviromentalMap.CollisionDetector;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public abstract class AbstractSense implements Sense {
    private SensingGameObject preceptor;
    private int range;
    private int dynamicRange;
    
    public AbstractSense(SensingGameObject preceptor) {
        this.preceptor = preceptor;
        this.range = 0;
        this.dynamicRange = 0;
        this.preceptor.setSense(this);
    }

    @Override
    public void setSenseRange(int range) {
        synchronized(this) {
            this.range = range;
        }
    }
    
    @Override
    public int getSenseRange() {
        int tmpRange;
        synchronized(this) {
            tmpRange = this.range;
        }
        return tmpRange;
    }
    
    @Override
    public void setSenseDynamicRange(int range) {
        synchronized(this) {
            this.dynamicRange = range;
        }
    }

    @Override
    public int getSenseDynamicRange() {
        int tmpRange;
        synchronized(this) {
            tmpRange = this.dynamicRange;
        }
        return tmpRange;
    }
    
    @Override
    public Rectangle getDetectionArea() {
        int tmpRange = this.getSenseRange();
        
        Point p = this.preceptor.getPosition();
        Rectangle area = new Rectangle(p);
        area.grow(range, range);
        
        return area;
    }
    
    @Override
    public Rectangle getDynamicDetectionArea() {
        int tmpRange = this.getSenseDynamicRange();
        
        Point p = this.preceptor.getPosition();
        Rectangle area = new Rectangle(p);
        area.grow(range, range);
        
        return area;
    }
    
    @Override
    public boolean setPreceptor(SensingGameObject preceptor) {
        boolean success = false;
        synchronized(this) {
            if (this.preceptor == null) {
                this.preceptor = preceptor;
                success = true;
            }
        }
        return success;
    }
    
    @Override
    public SensingGameObject getPreceptor() {
        SensingGameObject tmpPreceptor;
        synchronized(this) {
            tmpPreceptor = this.preceptor;
        }
        return tmpPreceptor;
    }
    
    @Override
    public void removeSense() {
        this.preceptor.removeSense(this);
        synchronized(this) {
            this.preceptor = null;
        }
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
        
        Point o, p;
        o = originator.getPosition();
        p = this.preceptor.getPosition();
        
        // make sure that range circels intersect
        int delta = CollisionDetector.getDistance(o, p);
        return (delta > (this.getSenseRange() + e.getEmitorRange()));
    }
    
}
