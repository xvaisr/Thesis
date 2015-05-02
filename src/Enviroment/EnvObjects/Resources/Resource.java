/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects.Resources;
import Enviroment.EnvObjFeatures.CarrierGameObject;
import Enviroment.EnvObjFeatures.CarryableGameObject;
import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjFeatures.ResourceGameObject;
import Enviroment.EnvObjects.GameObjectPrototype;
import Enviroment.EnviromentalMap.MapContainer;
import Graphics.Painters.Painter;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class Resource extends GameObjectPrototype
                      implements CarryableGameObject, ResourceGameObject, 
                              DrawableGameObject
{
    // Resource related
    private final ResourceTypes type;
    private CarrierGameObject carrier;
    
    // DrawableGameObject related
    private volatile Color color;
    private volatile Painter painter;

    public Resource(ResourceTypes type) {
        super();
        this.type = type;
        this.init();
    }

    public Resource(ResourceTypes type, int x, int y) {
        super(x, y);
        this.type = type;
        this.init();
    }
    
    public Resource(ResourceTypes type, Point position) {
        super(position);
        this.type = type;
        this.init();
    }
    
    public Resource(ResourceTypes type, MapContainer mc, int x, int y) {
        super(mc, x, y);
        this.type = type;
        this.init();
    }
    
    public Resource(ResourceTypes type, MapContainer mc, Point position) {
        super(mc, position);
        this.type = type;
        this.init();
    }
    
    private void init() {
        this.carrier = null;
        
        // Drawable object values
        this.color = Color.DARK_GRAY; // color will be redefined form outside
        this.painter = null;
    }
    
    @Override
    public ResourceTypes getResourceType() {
        return this.type;
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

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setPainter(Painter p) {
        this.painter = p;
    }

    @Override
    public Painter getPainter() {
        return this.painter;
    }
}
