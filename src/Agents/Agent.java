/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Enviroment.EnvObjects.CanCarry;
import Enviroment.EnvObjects.Carryable;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnvObjects.EnumGameObjects;

/**
 *
 * @author lennylinux
 */
public class Agent extends GameObject implements Carryable, CanCarry{
    /* private initial constants */ 
    private final int INIT_HP = 100;
    private final int INIT_EN = 100;
    
    private final Team team;        // Team to which agent belongs
    private final String name;      // agent's name
    private Carryable carrying;     // item which is agent carrying
    private CanCarry carrier;       // agent who is carrying this one
    private int en;                 // agent's energi
    private int hp;                 // agent's helath / hit points
    private boolean controled;      // by whom is agent controled; true = player
    private int frags;              // how many agent killed
    
    public Agent(String name, Team t) {
        super(EnumGameObjects.agent);
        this.name = name;
        this.team = t;
        this.carrying = null;
        this.carrier = null;
        this.team.setMember(this);
        
        this.hp = INIT_HP;
        this.en = INIT_EN;
        this.frags = 0;
        this.controled = false;
        
    }

/* Agent's methods */
    public void setHP(int hp) {
        this.hp = ((hp > 0)? hp : 0);
    }
    
    public int getHP() {
        return this.hp;
    }
    
    public void setEN(int en) {
        this.en = ((en > 0)? en : 0);
    }
    
    public int getEN() {
        return this.en;
    }
    
    public void addFrag() {
        this.frags++;
    }
    
    public int getFrags() {
        return this.frags;
    }
    
/* "CanCarry" interface methods */
    @Override
    public boolean setCarrying(Carryable item) {
        if (this.carrying == null && item.getIsCarryable()) {
            this.carrying = item;
            return true;
        }
        else if (this.carrying == null && item.getIsCarriedBy() == this) {
            return true;
        }
        return false;
    }

    @Override
    public boolean tryCarry(Carryable item) {       
        return this.carrying == null && item.setCarrier(this);
    }
    
    @Override
    public boolean dropItem() {
        if (this.carrying == null) {
            return true;
        }
        if (this.carrying.getIsCarriedBy() == this) {
            return (this.carrying.removeCarrier(this));
        }
        return false;
    }
    
    @Override
    public Carryable getItem() {
        return this.carrying;
    }
    
/* "Carryable" interface methods */
    @Override
    public boolean getIsCarryable() {
        return !this.getIsCarried();
    }

    @Override
    public boolean getIsCarried() {
        return this.carrier != null;
    }

    @Override
    public CanCarry getIsCarriedBy() {
        return this.carrier;
    }

    @Override
    public boolean setCarrier(CanCarry carrier) {
        if (carrier == this) return false;
        if (carrier.getItem() != null) return false;
        this.carrier = carrier;
        return true;
    }

    @Override
    public boolean removeCarrier(CanCarry carrier) {
        if (carrier == this.carrier && carrier.getItem() == this) {
            this.carrier = null;
            return true;
        }
        return false;
    }


   
}
