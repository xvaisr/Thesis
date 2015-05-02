/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects;

import Enviroment.EnvObjFeatures.CarrierGameObject;
import Enviroment.EnvObjFeatures.CarryableGameObject;
import Enviroment.EnviromentalMap.CollisionDetector;
import Enviroment.EnviromentalMap.MapContainer;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class CarryableCarrierPrototype extends GameObjectPrototype
                                       implements CarrierGameObject, CarryableGameObject
{

    private CarrierGameObject carrier;
    private CarryableGameObject item;

    public CarryableCarrierPrototype() {
        super(new Point());
        
        this.carrier = null;
        this.item = null;
    }

    public CarryableCarrierPrototype(int x, int y) {
        super(new Point(x, y));
        
        this.carrier = null;
        this.item = null;
    }
    
    public CarryableCarrierPrototype(Point position) {
        super(null, position);
        
        this.carrier = null;
        this.item = null;
    }
    
    public CarryableCarrierPrototype(MapContainer mc, int x, int y) {
        super(mc, new Point(x, y));
        
        this.carrier = null;
        this.item = null;
    }
    
    public CarryableCarrierPrototype(MapContainer mc, Point position) {
        super(mc, position);
        
        this.carrier = null;
        this.item = null;
    }
    
    @Override
    public boolean setCarrying(CarryableGameObject item) {
        if(this.getIsCarried() || !item.getIsCarryable() || item.getIsCarried()) {
            return false;
        }
        
        Point c, i;
        c = this.getPosition();
        i = item.getPosition();
        
        if (CollisionDetector.getDistance(c, i) > DEFAULT_GRABBING_DISTANCE) {
            return false;
        }
        
        this.item = item;
        return true;
    }

    @Override
    public boolean dropItem() {
        return this.item.removeCarrier(this);
    }

    @Override
    public CarryableGameObject getItem() {
        return this.item;
    }

    @Override
    public boolean getIsCarryable() {
         return !this.getIsCarried();
    }

    @Override
    public boolean getIsCarried() {
        return this.carrier == null;
    }

    @Override
    public CarrierGameObject getIsCarriedBy() {
        return this.carrier;
    }

    @Override
    public boolean setCarrier(CarrierGameObject carrier) {
        if (this.getIsCarried()) {
            return false;
        }
        this.carrier = carrier;
        return true;
    }

    @Override
    public boolean removeCarrier(CarrierGameObject carrier) {
        if (carrier == this.getIsCarriedBy()) {
            this.carrier = null;
            return true;
        }
        return false;
    }
    
}
