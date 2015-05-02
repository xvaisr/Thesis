/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects;

import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjFeatures.SensingGameObject;
import static Enviroment.EnvObjFeatures.SensingGameObject.SENSES_INITIAL_CAPACITY;
import Enviroment.EnviromentalMap.MapContainer;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author lennylinux
 */
public class SensingObjectPrototype extends GameObjectPrototype implements SensingGameObject {
    
    private final HashMap <Class<? extends Sense>, Sense> senses;
    private final ArrayList<Sense> senseList;

    public SensingObjectPrototype() {
        super();
        this.senses = new HashMap(SENSES_INITIAL_CAPACITY);
        this.senseList = new ArrayList(SENSES_INITIAL_CAPACITY);
    }
    
    public SensingObjectPrototype(int x, int y) {
        super(x, y);
        this.senses = new HashMap(SENSES_INITIAL_CAPACITY);
        this.senseList = new ArrayList(SENSES_INITIAL_CAPACITY);
    }
    
    public SensingObjectPrototype(Point position) {
        super(position);
        this.senses = new HashMap(SENSES_INITIAL_CAPACITY);
        this.senseList = new ArrayList(SENSES_INITIAL_CAPACITY);
    }
    
    public SensingObjectPrototype(MapContainer mc, int x, int y) {
        super(mc, x, y);
        this.senses = new HashMap(SENSES_INITIAL_CAPACITY);
        this.senseList = new ArrayList(SENSES_INITIAL_CAPACITY);
    }
    
    public SensingObjectPrototype(MapContainer mc, Point position) {
        super(mc, position);
        this.senses = new HashMap(SENSES_INITIAL_CAPACITY);
        this.senseList = new ArrayList(SENSES_INITIAL_CAPACITY);
    }
    

    @Override
    public Sense getSense(Class<? extends Sense> senseClass) {
        Sense s;
        synchronized (this.senses) {
            s = this.senses.get(senseClass);
        }
        return s;
    }

    @Override
    public boolean getHasSense(Class<? extends Sense> senseClass) {
        boolean has;
        synchronized (this.senses) {
            has = this.senses.containsKey(senseClass);
        }
        return has;
    }

    @Override
    public ArrayList<Sense> getSenseList() {
        ArrayList<Sense> outputList;
        synchronized(this.senseList) {
            outputList = (ArrayList<Sense>) this.senseList.clone();
        }
        return outputList;
    }
    
    @Override
    public void setSense(Sense s) {
        if (s == null) {
            return;
        }
        synchronized (this.senses) {
            this.senses.put(s.getClass(), s);
            synchronized(this.senseList) {
                this.senseList.add(s);
            }
        }
    }

    @Override
    public void removeSense(Sense s) {
        if (s == null) {
            return;
        }
        synchronized (this.senses) {
            if (this.getSense(s.getClass()) == s) {
                this.senses.remove(s.getClass());
            }
            synchronized(this.senseList) {
                if (this.senseList.contains(s)) {
                    this.senseList.remove(s);
                }
            }
        }
    }

    
}
