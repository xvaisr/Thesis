/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents.AgentTraits;
import Enviroment.EnvObjects.GameObject;

/**
 *
 * @author lennylinux
 */
public class Sence {
    private final GameObject parent;
    private final EnumSences type;
    private int strenght;
    
    public Sence(EnumSences type, GameObject parent) {
        this.type = type;
        this.parent = parent;
        this.parent.setSence(this);
    }

    public EnumSences getType() {
        return type;
    }
    
    public void setSenceStrenght(int strenght) {
        this.strenght = strenght;
    }
    
    public int getSenceStrenght() {
        return this.strenght;        
    }
    
    public void removeSence() {
        this.parent.removeSence(this.type);
    }

}
