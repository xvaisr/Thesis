/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Senses;

import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.SensingGameObject;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public interface Sense {
    
    public void setSenseRange(int range);
    public int getSenseRange();
    public void setSenseDynamicRange(int range);
    public int getSenseDynamicRange();
    public Rectangle getDetectionArea();
    public Rectangle getDynamicDetectionArea();
    public boolean setPreceptor(SensingGameObject preceptor);
    public SensingGameObject getPreceptor();
    public void removeSense();
    
    public boolean canPrecive(Emitor e);
    public Class<? extends Emitor> getPreciveable();
    
}
