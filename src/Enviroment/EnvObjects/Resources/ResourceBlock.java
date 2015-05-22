/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects.Resources;
import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjFeatures.DestructableGameObject;
import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjFeatures.Emitors.SmellEmitor;
import Enviroment.EnvObjFeatures.Emitors.VisualEmitor;
import Enviroment.EnvObjFeatures.ResourceGameObject;
import Enviroment.EnvObjects.DetectableObjectPrototype;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnvObjects.ObjectParts.Shape;
import Enviroment.EnviromentalMap.CollisionDetector;
import Enviroment.EnviromentalMap.MapContainer;
import Graphics.Painters.Painter;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class ResourceBlock extends DetectableObjectPrototype
                           implements DestructableGameObject, DrawableGameObject,
                                   ResourceGameObject, CollidableGameObject
{
    private static final Color WATER_COLOR = Color.BLUE;
    private static final Color FOOD_COLOR = Color.ORANGE.darker();
    
    private final int INIT_BLOCK_HP = 100;
    private final int INIT_BLOCK_CONTAINS = 25;
    private final int INIT_DROPLET_CONTAINS = 16;
    private final int INIT_CORNERS = 20;
    
    private final int SMELL_DISTANCE = 65;
    private final int WATTER_SMELL_STRENGHT = 30;
    private final int FOOD_SMELL_STRENGHT = 75;
    
    // resource block related
    private final ResourceTypes type;
    private int contains;
    
    // Destructable object related
    private volatile int baseHealth;
    private volatile int health;
    
    // Drawable object related
    private volatile Color color;
    private volatile Painter painter;

    public ResourceBlock(ResourceTypes type) {
        super();
        this.type = type;
        this.init();
    }

    public ResourceBlock(ResourceTypes type, int x, int y) {
        super(x, y);
        this.type = type;
        this.init();
    }
    
    public ResourceBlock(ResourceTypes type, Point position) {
        super(position);
        this.type = type;
        this.init();
    }
    
    public ResourceBlock(ResourceTypes type, MapContainer mc, int x, int y) {
        super(mc, x, y);
        this.type = type;
        this.init();
    }
    
    public ResourceBlock(ResourceTypes type, MapContainer mc, Point position) {
        super(mc, position);
        this.type = type;
        this.init();
    }
    
    private void init() {
        // Drawable object values
        this.color = Color.DARK_GRAY; // color will be redefined form outside
        this.painter = null;
        
        // visibility emitor
        VisualEmitor ve = new VisualEmitor(this);
        ve.setEmitorActive(true);
        /*
           setting up strenght ad radius of visual emitor is not necesary
            - different implementation of detection
         */
        
        // smell emitor
        SmellEmitor se;
        se = new SmellEmitor(this);
        se.setDecreasePercentage(0); // cannot decrease
        // how it smells and how strong is going to be set later
        se.setDispersionRadius(SMELL_DISTANCE);
        se.setEmitorActive(true);
        
        // resource block related, type related
        ResourceTypes rtype = this.getResourceType();
        if (rtype == ResourceTypes.water) {
            this.setColor(WATER_COLOR);
            se.setEmitorStrength(WATTER_SMELL_STRENGHT);
            this.contains = INIT_DROPLET_CONTAINS;
        }
        else if (rtype == ResourceTypes.food) {
            this.setColor(FOOD_COLOR);
            se.setEmitorStrength(FOOD_SMELL_STRENGHT);
            this.contains = INIT_BLOCK_CONTAINS;
        }
        
        // Destructable object values
        this.baseHealth = INIT_BLOCK_HP;
        this.health = INIT_BLOCK_HP;
        
        // Set wanted shape
        Shape s = new Shape();
        s.addVertex(INIT_CORNERS, INIT_CORNERS);
        s.addVertex(INIT_CORNERS, - INIT_CORNERS);
        s.addVertex(- INIT_CORNERS, - INIT_CORNERS);
        s.addVertex(- INIT_CORNERS, INIT_CORNERS);
        this.setNewShape(s);
    }
    
    
    @Override
    public ResourceTypes getResourceType() {
        return this.type;
    }
    
    private Resource dispenseResource () {
            this.health = INIT_BLOCK_HP;
            --this.contains;
            Resource r = new Resource(this.type);
            r.setColor(this.getColor());
            return r;
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
        GameObject drop = null;
        if (this.isDestroied() && this.contains > 0) {
            drop = this.dispenseResource();
        }
        else if(this.isDestroied()) {
            this.leaveMapContainer();
        }
        return drop;
    }
    
    @Override
    public void setColor(Color c) {
        if (c == null) {
            return;
        }
        
        this.color = c;
        SmellEmitor se = (SmellEmitor) this.getEmitor(SmellEmitor.class);
        se.setEmitorSmell(this.color.getRGB());
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

    @Override
    public boolean colides(CollidableGameObject another) {
        return CollisionDetector.getCollide(this, another);
    }
}
