/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjects.GameObject;

/**
 *
 * @author lennylinux
 */
public interface DestructableGameObject extends GameObject {
    
    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_LOW_HEALTH = 15;
    
    public void setBaseHealth(int hp);
    public void setHealth(int hp);
    public void increaseBaseHealth(int hp);
    public void increaseHealth(int hp);
    public int getHealth();
    public void takeDamage(int dmg);
    public boolean isDamaged();
    public boolean isHeavilyDamaged();
    public boolean isDestroied();
    public GameObject destroy();
    
    
}
