/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Emitors;
import Enviroment.EnvObjFeatures.DetectableGameObject;
import RTreeAlgorithm.RtreeNode;
import RTreeAlgorithm.RtreeNodeLinker;
import java.awt.Point;
import java.awt.Rectangle;
/**
 *
 * @author lennylinux
 */
public abstract class AbstractEmitor implements Emitor, RtreeNodeLinker {
    private DetectableGameObject originator;
    private volatile int strenght;
    private volatile int radius;
    private volatile boolean active;
    private RtreeNode linkedNode;
    
    public AbstractEmitor(DetectableGameObject originator) {
        this.originator = originator;
        this.originator.setEmitor(this);
        this.strenght = 0;
        this.radius = AbstractEmitor.AUTO_RANGE;
        this.active = true;
        this.linkedNode = null;
    }

    @Override
    public void setEmitorStrength(int strenght) {
        this.strenght = strenght;
    }

    @Override
    public int getEmitorStrenght() {
        return this.strenght;
    }
    
    @Override
    public void setDispersionRadius(int radius) {
        this.radius = radius;
    }
    
    @Override
    public int getDispersionRadius() {
        return this.radius;
    }

    @Override
    public Rectangle getDispersionArea() {
        
        // if there is infinite range, emitor is detectable when object is
        if (this.radius == AbstractEmitor.AUTO_RANGE) {
            return this.originator.getBoundingBox();
        }
        
        Rectangle area = new Rectangle(this.originator.getPosition());
        area.grow(this.radius, this.radius);
        
        return area;
        
    }

    @Override
    public void setEmitorActive(boolean act) {
        synchronized(this) {
            this.active = act;
        }
    }

    @Override
    public boolean getEmitorActive() {
        boolean tmpActive;
        synchronized(this) {
            tmpActive = this.active;
        }
        return tmpActive;        
    }

    @Override
    public boolean setOriginator(DetectableGameObject originator) {
        boolean success = false;
        synchronized(this) {
            if (this.originator == null) {
                this.originator = originator;
                success = true;
            }
        }
        return success;
    }
    
    @Override
    public DetectableGameObject getOriginator() {
        DetectableGameObject origin;
        synchronized(this) {
            origin = this.originator;
        }
        return origin;
    }

    @Override
    public void removeEmitor() {
        this.originator.removeEmitor(this);
        synchronized(this) {
            this.originator = null;
        }
    }
    
    @Override
    public void setNode(RtreeNode node) {
        this.linkedNode = node;
    }

    @Override
    public RtreeNode getNode() {
        return this.linkedNode;
    }

    @Override
    public Rectangle getBoundingBox() {
        return this.getDispersionArea();
    }

}
