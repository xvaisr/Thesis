/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjects;

import Enviroment.EnvObjFeatures.DetectableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnviromentalMap.MapContainer;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class DetectableObjectPrototype extends GameObjectPrototype implements DetectableGameObject {
    
    private final HashMap <Class<? extends Emitor>, Emitor> emitors;
    private final ArrayList<Emitor> emitorList;

    public DetectableObjectPrototype() {
        super();
        this.emitors = new HashMap(EMITORS_INITIAL_CAPACITY);
        this.emitorList = new ArrayList(EMITORS_INITIAL_CAPACITY);
    }
    
    public DetectableObjectPrototype(int x, int y) {
        super(x, y);
        this.emitors = new HashMap(EMITORS_INITIAL_CAPACITY);
        this.emitorList = new ArrayList(EMITORS_INITIAL_CAPACITY);
    }
    
    public DetectableObjectPrototype(Point position) {
        super(position);
        this.emitors = new HashMap(EMITORS_INITIAL_CAPACITY);
        this.emitorList = new ArrayList(EMITORS_INITIAL_CAPACITY);
    }
    
    public DetectableObjectPrototype(MapContainer mc, int x, int y) {
        super(mc, x, y);
        this.emitors = new HashMap(EMITORS_INITIAL_CAPACITY);
        this.emitorList = new ArrayList(EMITORS_INITIAL_CAPACITY);
    }
    
    public DetectableObjectPrototype(MapContainer mc, Point position) {
        super(mc, position);
        this.emitors = new HashMap(EMITORS_INITIAL_CAPACITY);
        this.emitorList = new ArrayList(EMITORS_INITIAL_CAPACITY);
    }
    

    @Override
    public Emitor getEmitor(Class<? extends Emitor> emitorClass) {
        Emitor e;
        synchronized (this.emitors) {
            e = this.emitors.get(emitorClass);
        }
        return e;
    }

    @Override
    public boolean getHasEmitor(Class<? extends Emitor> emitorClass) {
        boolean has;
        synchronized (this.emitors) {
            has = this.emitors.containsKey(emitorClass);
        }
        return has;
    }

    @Override
    public ArrayList<Emitor> getEmitorList() {
        ArrayList<Emitor> outputList;
        synchronized(this.emitorList) {
            outputList = (ArrayList<Emitor>) this.emitorList.clone();
        }
        return outputList;
    }
    
    @Override
    public void setEmitor(Emitor e) {
        if (e == null || this.emitors.containsValue(e)) {
            return;
        }
        synchronized (this.emitors) {
            this.emitors.put(e.getClass(), e);
            synchronized(this.emitorList) {
                this.emitorList.add(e);
            }
        }
    }

    @Override
    public void removeEmitor(Emitor e) {
        if (e == null) {
            return;
        }
        synchronized (this.emitors) {
            if (this.getEmitor(e.getClass()) == e) {
                this.emitors.remove(e.getClass());
            }
            synchronized(this.emitorList) {
                if (this.emitorList.contains(e)) {
                    this.emitorList.remove(e);
                }
            }
        }
    }

    
    

    
}
