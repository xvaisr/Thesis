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
public class Emitor {
    private final GameObject parent;
    private final EnumEmitors type;
    private int strenght;
    
    public Emitor(EnumEmitors type, GameObject parent) {
        this.type = type;
        this.parent = parent;
        this.parent.setEmitor(this);
    }

    public EnumEmitors getType() {
        return type;
    }
    
    public void setEmitorStrenght(int strenght) {
        this.strenght = strenght;
    }
    
    public int getEmitorStrenght() {
        return this.strenght;        
    }
    
    public void removeEmitor() {
        this.parent.removeEmitor(this.type);
    }

}
