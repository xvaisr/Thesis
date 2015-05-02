/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects.Agents;

import Agents.Team.Team;
import Enviroment.EnvObjFeatures.Senses.AbstractSense;
import Enviroment.EnvObjFeatures.Emitors.AbstractEmitor;
import Enviroment.EnvObjFeatures.*;
import Enviroment.EnvObjFeatures.Emitors.SoundEmitor;
import Enviroment.EnvObjFeatures.Emitors.VisualEmitor;
import Enviroment.EnvObjFeatures.Senses.Hearing;
import Enviroment.EnvObjFeatures.Senses.SenseOfSmell;
import Enviroment.EnvObjFeatures.Senses.Sight;
import Enviroment.EnvObjects.GameObjectBackup;
import Enviroment.EnvObjects.EnumGameObjects;
import Enviroment.EnvObjects.GameObjectBackup;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class Agent extends GameObjectBackup implements CarryableGameObject, CarrierGameObject{
    /* private initial constants */ 
    private final int INIT_HP = 100;
    private final int INIT_EN = 100;
    
    private final int SIGHT_RANGE = 75;
    private final int SMELL_RANGE = 400;
    private final int HEARING_RANGE = 250;
    
    private final int SOUND_RANGE = 250;        // broadcast
    private final int COMUNICATION_RANGE = 60;  // comunication with friends
    
    private final Team team;            // Team to which agent belongs
    private final String name;          // agent's name
    private CarryableGameObject carrying;         // item which is agent carrying
    private CarrierGameObject carrier;           // agent who is carrying this one
    private int en;                     // agent's energi
    private int hp;                     // agent's helath / hit points
    private final boolean controled;    // is agent controled by player
    private int frags;                  // how many agent killed
    private Point dest;
    
    public Agent(String name, Team t, boolean controled) {
        super(EnumGameObjects.agent);
        
        // setup agent 
        this.name = name;
        this.team = t;
        this.carrying = null;
        this.carrier = null;
        this.team.setMember(this);
        
        // setup agent's stats
        this.hp = INIT_HP;
        this.en = INIT_EN;
        this.frags = 0;
        this.controled = controled;
        
        // setup emitors and senses for interaction with enviroment
        AbstractEmitor e;
        // sound 
        e = new SoundEmitor(this);
        e.setEmitorActive(false);
        e.setEmitorRange(COMUNICATION_RANGE);
        // visibility
        e = new VisualEmitor(this);
        e.setEmitorActive(true);
        
        AbstractSense s;
        // hearing
        s = new Hearing(this);
        s.setSenseRange(HEARING_RANGE);
        // sight
        s = new Sight(this);
        s.setSenseRange(SIGHT_RANGE);
        // smell
        s = new SenseOfSmell(this);
        s.setSenseRange(SMELL_RANGE);
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
    
    public Team getTeam() {
        return this.team;
    }
    
 /* Overriden GameObject methods */
    
    @Override
    public boolean canBeSelected() {
        return this.controled && !this.getIsSelected();
    }
    
    @Override
    public boolean canMove() {
        return true;
    }
    
/* "CanCarry" interface methods */
    @Override
    public boolean setCarrying(CarryableGameObject item) {
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
    public boolean tryCarry(CarryableGameObject item) {       
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
    public CarryableGameObject getItem() {
        return this.carrying;
    }
    
/* "Carryable" interface methods */
    @Override
    public boolean getIsCarryable() {
        return !(this.getIsCarried() || this.getItem() != null);
    }

    @Override
    public boolean getIsCarried() {
        return this.carrier != null;
    }

    @Override
    public CarrierGameObject getIsCarriedBy() {
        return this.carrier;
    }

    @Override
    public boolean setCarrier(CarrierGameObject carrier) {
        boolean possible = ((carrier != this) && (!this.getIsCarryable()));
        if (possible) {
            this.carrier = carrier;
        }
        return possible;
    }

    @Override
    public boolean removeCarrier(CarrierGameObject carrier) {
        boolean possible = (carrier == this.carrier && carrier.getItem() == this);
        if (possible) {
            this.carrier = null;
        }
        return possible;
    }

   
}
