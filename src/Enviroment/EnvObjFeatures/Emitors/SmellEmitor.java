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
public class SmellEmitor extends AbstractEmitor {
    private static final int DEFAULT_SMELL = 0;
    private double concentration;
    private int decPercentage;
    private int smell;
    

    public SmellEmitor(DetectableGameObject originator) {
        super(originator);
        this.smell = DEFAULT_SMELL;
        this.decPercentage = 0;
        this.concentration = 0;
    }
    
    public void setDecreasePercentage(int percentage) {
        if (percentage < 0 || percentage > 100) {
            return;
        }
        synchronized(this) {
            this.decPercentage = percentage;
        }
    }
    
    public void decreaseIntensity() {
        this.concentration = this.concentration
                   - (this.concentration * (((double) this.decPercentage)/ 100));
    }
    
    public void setEmitorSmell(int smell) {
        this.smell = smell;
    }
    
    public int getEmitorSmell() {
        return this.smell;
    }
    
    @Override
    public void setEmitorStrength(int strenght) {
        this.concentration = (double) strenght;
    }
    
    @Override
    public int getEmitorStrenght() {
        Long l = new Long(Math.round(this.concentration));
        return l.intValue();        
    }
    
}
