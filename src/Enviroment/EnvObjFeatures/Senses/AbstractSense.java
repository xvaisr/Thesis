/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures.Senses;
import Enviroment.EnvObjFeatures.DetectableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.SensingGameObject;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnviromentalMap.CollisionDetector;
import Enviroment.EnviromentalMap.ExtendedMapInterface;
import Enviroment.EnviromentalMap.MapContainer;
import Enviroment.EnviromentalMap.MapInterface;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class AbstractSense implements Sense {
    private SensingGameObject preceptor;
    private final ArrayList<DetectableGameObject> cache;
    private int range;
    private int dynamicRange;
    
    public AbstractSense(SensingGameObject preceptor) {
        if (preceptor == null) {
            throw new NullPointerException("Preceptor cannot be Null!");
        }
        this.cache = new ArrayList();
        this.preceptor = preceptor;
        this.range = 0;
        this.dynamicRange = 0;
        this.attacheToPreceptor();
    }
    
    private void attacheToPreceptor() {
        this.preceptor.setSense(this);
    }
    
    private boolean haveAccesToMap() {
        if(this.preceptor == null) {
            return false;
        }
        MapContainer container = this.getPreceptor().getMapContainer();
        if(container == null || container.getMap() == null) {
            return false;
        }
        return true;
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
    public void updatePreception() {
        if (!this.haveAccesToMap()) {
            return;
        }
        
        ArrayList<DetectableGameObject> detected;
        
        MapInterface map = this.getPreceptor().getMapContainer().getMap();
        if (!(map instanceof ExtendedMapInterface)) {
            detected = new ArrayList();
            
            ArrayList<GameObject> objects;
            objects = map.getGameObjectsInArea(this.getDetectionArea());
            for (GameObject go : objects) {
                if(go instanceof DetectableGameObject) {
                    detected.add((DetectableGameObject) go);
                }
            }
        }
        else if (map instanceof ExtendedMapInterface) {
            detected = ((ExtendedMapInterface) map).getDetectedObjectsStaticMap(this);
        }
        else {
            detected = new ArrayList();
        }
        
        synchronized(this.cache) {
            this.cache.clear();
            this.cache.addAll(detected);
        }
    }
    
    @Override
    public boolean canPrecive(Emitor e) {
        return this.preceptionStrenght(e) > 0;
    }
    
    @Override
    public int preceptionStrenght(Emitor e) {
        // if it is emitor of diferent sense, it is deactivated, 
        if ((e == null) || !e.getEmitorActive() // or there is mistake in code
                        || (e.getClass() != this.getPreciveable()))
        {
            return NO_PRECEPT_STRENGHT;
        }
        
        /* 
           If dispersion area of emitor doesn't intersect detection area, 
           originator cannot be precived by preceptor.
         */
        if (!this.getDetectionArea().intersects(e.getDispersionArea())) {
            return NO_PRECEPT_STRENGHT;
        }
        
        // Nonexisting object cannot be detected
        DetectableGameObject originator = e.getOriginator();
        if (originator == null) {
            return NO_PRECEPT_STRENGHT;
        }
        
        Point o, p;
        o = originator.getPosition();
        p = this.preceptor.getPosition();
        
        // make sure that range circels intersect
        int distance = CollisionDetector.getDistance(o, p);
        return (distance - (this.getSenseRange() + e.getDispersionRadius()));
    }
    
    public ArrayList<DetectableGameObject> getCache() {
        synchronized (this.cache) {
            return (ArrayList<DetectableGameObject>) this.cache.clone();
        }
    }
    
    public ArrayList<DetectableGameObject> getDynamicMapObjects() {
        ArrayList<DetectableGameObject> detected;
        
        if (!this.haveAccesToMap()) {
            return new ArrayList();
        }
        
        MapInterface map = this.getPreceptor().getMapContainer().getMap();
        if (!(map instanceof ExtendedMapInterface)) {
            return new ArrayList();
        }
        
        return ((ExtendedMapInterface) map).getDetectedObjectsDynamicMap(this);
    }
}
