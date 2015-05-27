/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjects;

import Enviroment.EnvObjFeatures.PoweredGameObject;
import Enviroment.EnviromentalMap.MapContainer;
import java.awt.Point;

public class PoweredGameObjectPrototype extends GameObjectPrototype implements PoweredGameObject {
    
    private int baseEnergy;
    private int energy;

    public PoweredGameObjectPrototype() {
        super(new Point());
        
        this.baseEnergy = DEFAULT_ENERGY_LEVEL;
        this.energy = DEFAULT_ENERGY_LEVEL;
    }

    public PoweredGameObjectPrototype(int x, int y) {
        super(new Point(x, y));
        
        this.baseEnergy = DEFAULT_ENERGY_LEVEL;
        this.energy = DEFAULT_ENERGY_LEVEL;
    }
    
    public PoweredGameObjectPrototype(Point position) {
        super(null, position);
        
        this.baseEnergy = DEFAULT_ENERGY_LEVEL;
        this.energy = DEFAULT_ENERGY_LEVEL;
    }
    
    public PoweredGameObjectPrototype(MapContainer mc, int x, int y) {
        super(mc, new Point(x, y));
        
        this.baseEnergy = DEFAULT_ENERGY_LEVEL;
        this.energy = DEFAULT_ENERGY_LEVEL;
    }
    
    public PoweredGameObjectPrototype(MapContainer mc, Point position) {
        super(mc, position);
        
        this.baseEnergy = DEFAULT_ENERGY_LEVEL;
        this.energy = DEFAULT_ENERGY_LEVEL;
    }
    
    private void cutEnergy() {
        if (this.energy > this.baseEnergy) {
            this.energy = this.baseEnergy;
        }
    }
    
    @Override
    public void setBaseEnergyLevel(int en) {
        if (en > 0) {
            this.baseEnergy = en;
        }
        this.cutEnergy();
    }

    @Override
    public void setEnergyLevel(int en) {
        if (en >= 0) {
            this.energy = en;
        }
        this.cutEnergy();
    }

    @Override
    public void increaseBaseEnergyLevel(int en) {
        if (en >= 0) {
            this.baseEnergy += en;
        }
    }
    
    @Override
    public void increaseEnergyLevel(int en) {
        if (en >= 0) {
            this.energy += en;
        }
        this.cutEnergy();
    }

    @Override
    public int getEnergyLevel() {
        return this.energy;
    }

    @Override
    public void consumeEnergy(int en) {
        if (en >= 0) {
            this.energy -= en;
        }
    }

    @Override
    public boolean isOverCharged() {
        return (this.energy > this.baseEnergy);
    }

    @Override
    public boolean isLowOnEnergy() {
        return (this.energy <= DEFAULT_ENERGY_RESERVES);
    }

    @Override
    public boolean isEnergyDepleted() {
        return (this.energy) <= 0;
    }
    
}
