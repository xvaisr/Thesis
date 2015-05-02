/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects;

import Enviroment.EnvObjFeatures.DestructableGameObject;
import Enviroment.EnviromentalMap.MapContainer;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class DestructableGameObjectPrototype extends GameObjectPrototype implements DestructableGameObject {
    
    private volatile int baseHealth;
    private volatile int health;

    public DestructableGameObjectPrototype() {
        super(new Point());
        
        this.baseHealth = DEFAULT_HEALTH;
        this.health = DEFAULT_HEALTH;
    }

    public DestructableGameObjectPrototype(int x, int y) {
        super(new Point(x, y));
        
        this.baseHealth = DEFAULT_HEALTH;
        this.health = DEFAULT_HEALTH;
    }
    
    public DestructableGameObjectPrototype(Point position) {
        super(null, position);
        
        this.baseHealth = DEFAULT_HEALTH;
        this.health = DEFAULT_HEALTH;
    }
    
    public DestructableGameObjectPrototype(MapContainer mc, int x, int y) {
        super(mc, new Point(x, y));
        
        this.baseHealth = DEFAULT_HEALTH;
        this.health = DEFAULT_HEALTH;
    }
    
    public DestructableGameObjectPrototype(MapContainer mc, Point position) {
        super(mc, position);
        
        this.baseHealth = DEFAULT_HEALTH;
        this.health = DEFAULT_HEALTH;
    }
    
    private void cutHP() {
        if (this.health > this.baseHealth) {
            this.health = this.baseHealth;
        }
    }
    
    @Override
    public void setBaseHealth(int hp) {
        if (hp > 0) {
            this.baseHealth = hp;
        }
        this.cutHP();
    }

    @Override
    public void setHealth(int hp) {
        if (hp >= 0) {
            this.health = hp;
        }
        this.cutHP();
    }

    @Override
    public void increaseBaseHealth(int hp) {
        if (hp >= 0) {
            this.baseHealth += hp;
        }
    }
    
    @Override
    public void increaseHealth(int hp) {
        if (hp >= 0) {
            this.health += hp;
        }
        this.cutHP();
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void takeDamage(int dmg) {
        if (dmg >= 0) {
            this.health -= dmg;
        }
    }

    @Override
    public boolean isDamaged() {
        return (this.health < this.baseHealth);
    }

    @Override
    public boolean isHeavilyDamaged() {
        return (this.health <= DEFAULT_LOW_HEALTH);
    }

    @Override
    public boolean isDestroied() {
        return (this.health) <= 0;
    }

    @Override
    public GameObject destroy() {
        this.leaveMapContainer();
        return null;
    }
    
}
