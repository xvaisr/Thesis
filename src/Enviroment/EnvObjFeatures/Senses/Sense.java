/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Enviroment.EnvObjFeatures.Senses;

import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.SensingGameObject;
import java.awt.Rectangle;
import java.util.List;

public interface Sense {
    public static int NO_PRECEPT_STRENGHT = -1;
    
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
    public int preceptionStrenght(Emitor e);
    
    public Class<? extends Emitor> getPreciveable();
    public void updatePreception();
    public List<String> getPercepts();
    
}
