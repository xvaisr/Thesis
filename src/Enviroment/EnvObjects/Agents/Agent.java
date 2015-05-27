/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjects.Agents;
// <editor-fold defaultstate="collapsed" desc="Imports">
import Agents.Team.Team;
import Enviroment.EnvObjFeatures.AgentInterface;
import Enviroment.EnvObjFeatures.CarrierGameObject;
import static Enviroment.EnvObjFeatures.CarrierGameObject.DEFAULT_GRABBING_DISTANCE;
import Enviroment.EnvObjFeatures.CarryableGameObject;
import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjFeatures.DestructableGameObject;
import Enviroment.EnvObjFeatures.DetectableGameObject;
import static Enviroment.EnvObjFeatures.DetectableGameObject.EMITORS_INITIAL_CAPACITY;
import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Emitors.SmellEmitor;
import Enviroment.EnvObjFeatures.Emitors.SoundEmitor;
import Enviroment.EnvObjFeatures.Emitors.VisualEmitor;
import Enviroment.EnvObjFeatures.MoveableGameObject;
import Enviroment.EnvObjFeatures.PoweredGameObject;
import Enviroment.EnvObjFeatures.Senses.Hearing;
import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjFeatures.Senses.SenseOfSmell;
import Enviroment.EnvObjFeatures.Senses.Sight;
import Enviroment.EnvObjFeatures.SensingGameObject;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnvObjects.GameObjectPrototype;
import Enviroment.EnvObjects.ObjectParts.Shape;
import Enviroment.EnviromentalMap.Chunk;
import Enviroment.EnviromentalMap.CollisionDetector;
import Enviroment.EnviromentalMap.Compas;
import Enviroment.EnviromentalMap.MapContainer;
import Enviroment.Model;
import Graphics.Painters.Painter;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

// </editor-fold>

/**
 *
 * @author lennylinux
 */
public class Agent extends GameObjectPrototype implements SensingGameObject,
                DetectableGameObject, MoveableGameObject, CarrierGameObject,
                CarryableGameObject, CollidableGameObject, DestructableGameObject,
                PoweredGameObject, DrawableGameObject, AgentInterface
{
    // Private senses constants
    private static final int SIGHT_RANGE = 75;             // sight detection range
    private static final int SMELL_RANGE = 400;            // efective smell detection range
    private static final int SMELL_DYNAMIC_RANGE = 800;    // smell detection range for dynamic map
    private static final int HEARING_RANGE = 250;          // efective hearing range
    private static final int HEARING_DYNAMIC_RANGE = 500;  // hearing range for dynamic map
    
    // private emitors constants
    private static final int SOUND_RANGE = 250;        // broadcast
    private static final int COMUNICATION_RANGE = 60;  // comunication with friends
    private static final int SMELL_EMITION = 10;       // for recognizing 
    
    // Agent himself
    private volatile String name;
    private volatile Team team;
    
    // senses and emitors
    private final HashMap <Class<? extends Emitor>, Emitor> emitors;
    private final ArrayList<Emitor> emitorList;
    private final HashMap <Class<? extends Sense>, Sense> senses;
    private final ArrayList<Sense> senseList;
    
    // movement
    private Point destination;
    private double pedometr;
    private Double rx, ry;
    private int speed;
    
    // agent is carrier of other objects
    private CarryableGameObject item;
    
    // agent can be carried by other agent
    private CarrierGameObject carrier;
    
    // agent is destructable and can be killed
    private volatile int baseHealth;
    private volatile int health;
    
    // agent requires energy to function
    private volatile int baseEnergy;
    private volatile int energy;
    
    // DrawableGameObject related
    private volatile Color color;
    private volatile Painter painter;
    
    
    // <editor-fold defaultstate="collapsed" desc="Agent Constructors">
    public Agent() {
        super();
        this.emitors = new HashMap(EMITORS_INITIAL_CAPACITY);
        this.emitorList = new ArrayList(EMITORS_INITIAL_CAPACITY);
        this.senses = new HashMap(SENSES_INITIAL_CAPACITY);
        this.senseList = new ArrayList(SENSES_INITIAL_CAPACITY);
        
        this.initAgent();
    }
    
    public Agent(int x, int y) {
        super(x, y);
        this.emitors = new HashMap(EMITORS_INITIAL_CAPACITY);
        this.emitorList = new ArrayList(EMITORS_INITIAL_CAPACITY);
        this.senses = new HashMap(SENSES_INITIAL_CAPACITY);
        this.senseList = new ArrayList(SENSES_INITIAL_CAPACITY);
        
        this.initAgent();
    }
    
    public Agent(Point position) {
        super(position);
        this.emitors = new HashMap(EMITORS_INITIAL_CAPACITY);
        this.emitorList = new ArrayList(EMITORS_INITIAL_CAPACITY);
        this.senses = new HashMap(SENSES_INITIAL_CAPACITY);
        this.senseList = new ArrayList(SENSES_INITIAL_CAPACITY);
        
        this.initAgent();
    }
    
    public Agent(MapContainer mc, int x, int y) {
        super(mc, x, y);
        this.emitors = new HashMap(EMITORS_INITIAL_CAPACITY);
        this.emitorList = new ArrayList(EMITORS_INITIAL_CAPACITY);
        this.senses = new HashMap(SENSES_INITIAL_CAPACITY);
        this.senseList = new ArrayList(SENSES_INITIAL_CAPACITY);
        
        this.initAgent();
    }
    
    public Agent(MapContainer mc, Point position) {
        super(mc, position);
        this.emitors = new HashMap(EMITORS_INITIAL_CAPACITY);
        this.emitorList = new ArrayList(EMITORS_INITIAL_CAPACITY);
        this.senses = new HashMap(SENSES_INITIAL_CAPACITY);
        this.senseList = new ArrayList(SENSES_INITIAL_CAPACITY);
        
        this.initAgent();
    }
    // </editor-fold>
    
    private void initAgent() {
        // setup shape for colision detection
        Shape sh = new Shape();
        sh.addVertex(0, 6);
        sh.addVertex(4, 4);
        sh.addVertex(6, 0);
        sh.addVertex(4, -4);
        sh.addVertex(0, -6);
        sh.addVertex(-4 , -4);
        sh.addVertex(-6, 0);
        sh.addVertex(-4, 4);
        this.setNewShape(sh);
        
        // Agents values
        this.name = DEFAULT_AGENT_NAME;
        this.team = null;
        
        // DestructableGameObject values
        this.baseHealth = DEFAULT_HEALTH;
        this.health = DEFAULT_HEALTH;
        
        // PoweredGameObject values
        this.baseEnergy = DEFAULT_ENERGY_LEVEL;
        this.energy = DEFAULT_ENERGY_LEVEL;
        
        // MoveableGameObjectValues
        Point p = this.getPosition();
        this.rx = p.getX();
        this.ry = p.getY();
        this.destination = p;
        this.speed = DEFAULT_SPEED;
        this.pedometr = 0.0;
        
        // Carrier and Carryable objects values
        this.item = null;
        this.carrier = null;
        
        // Drawable object values
        this.color = Color.DARK_GRAY; // color will be redefined form outside
        this.painter = null;
        
        // setup emitors and senses for interaction with enviroment
        Emitor e;
        // sound 
        e = new SoundEmitor(this);
        e.setEmitorActive(false);
        e.setEmitorStrength(COMUNICATION_RANGE);
        e.setDispersionRadius(COMUNICATION_RANGE);
        // visibility
        e = new VisualEmitor(this);
        e.setEmitorActive(true);
        /*
           setting up strenght ad radius of visual emitor is not necesary
            - different implementation of detection
         */
        // smell
        SmellEmitor se;
        se = new SmellEmitor(this);
        se.setDecreasePercentage(0); // cannot decrease
        se.setEmitorSmell(this.color.getRGB());
        se.setEmitorStrength(SMELL_EMITION);
        se.setDispersionRadius(SMELL_EMITION);
        se.setEmitorActive(true);
        
        Sense s;
        // hearing
        s = new Hearing(this);
        s.setSenseRange(HEARING_RANGE);
        s.setSenseDynamicRange(HEARING_DYNAMIC_RANGE);
        // sight
        s = new Sight(this);
        s.setSenseRange(SIGHT_RANGE);
        s.setSenseDynamicRange(SIGHT_RANGE);
        // smell
        s = new SenseOfSmell(this);
        s.setSenseRange(SMELL_RANGE);
        s.setSenseDynamicRange(SMELL_DYNAMIC_RANGE);
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="Agent interface">
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if (name  == null || name.isEmpty()) {
            return;
        }
        this.name = name;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public void setTeam(Team team) {
        if (team == null) {
            return;
        }
        this.team = team;
        this.team.setMember(this);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="SensingGameObject interface">
    @Override
    public Sense getSense(Class<? extends Sense> senseClass) {
        Sense s;
        synchronized (this.senses) {
            s = this.senses.get(senseClass);
        }
        return s;
    }

    @Override
    public boolean getHasSense(Class<? extends Sense> senseClass) {
        boolean has;
        synchronized (this.senses) {
            has = this.senses.containsKey(senseClass);
        }
        return has;
    }

        @Override
    public ArrayList<Sense> getSenseList() {
        ArrayList<Sense> outputList;
        synchronized(this.senseList) {
            outputList = (ArrayList<Sense>) this.senseList.clone();
        }
        return outputList;
    }
    
    @Override
    public void setSense(Sense s) {
        if (s == null) {
            return;
        }
        synchronized (this.senses) {
            this.senses.put(s.getClass(), s);
            synchronized(this.senseList) {
                this.senseList.add(s);
            }
        }
    }

    @Override
    public void removeSense(Sense s) {
        if (s == null) {
            return;
        }
        synchronized (this.senses) {
            if (this.getSense(s.getClass()) == s) {
                this.senses.remove(s.getClass());
            }
            synchronized(this.senseList) {
                if (this.senseList.contains(s)) {
                    this.senseList.remove(s);
                }
            }
        }
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DetectableGameObject interface">
    @Override
    public Emitor getEmitor(Class<? extends Emitor> emitorClass) {
        Emitor e;
        synchronized (this.emitors) {
            e = this.emitors.get(emitorClass);
        }
        return e;
    }

    @Override
    public boolean getHasEmitor(Class<? extends Emitor> emitorClass) {
        boolean has;
        synchronized (this.emitors) {
            has = this.emitors.containsKey(emitorClass);
        }
        return has;
    }

    @Override
    public ArrayList<Emitor> getEmitorList() {
        ArrayList<Emitor> outputList;
        synchronized(this.emitorList) {
            outputList = (ArrayList<Emitor>) this.emitorList.clone();
        }
        return outputList;
    }
    
    @Override
    public void setEmitor(Emitor e) {
        if (e == null) {
            return;
        }
        synchronized (this.emitors) {
            this.emitors.put(e.getClass(), e);
            synchronized(this.emitorList) {
                this.emitorList.add(e);
            }
        }
    }

    @Override
    public void removeEmitor(Emitor e) {
        if (e == null) {
            return;
        }
        synchronized (this.emitors) {
            if (this.getEmitor(e.getClass()) == e) {
                this.emitors.remove(e.getClass());
            }
            synchronized(this.emitorList) {
                if (this.emitorList.contains(e)) {
                    this.emitorList.remove(e);
                }
            }
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="MoveableGameObject interface">
    @Override
    public void setSpeed(int speed) {
        if (speed >= 0) this.speed = speed;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }
    
    @Override
    public void setDestination(Point dest) {
        if (dest != null) 
        this.destination = dest; 
    }

    @Override
    public Point getDestinattion() {
        return new Point(this.destination);
    }
    
    @Override
    public boolean getCanMove() {
        //return false;
        return !(this.isLowOnEnergy() || this.isHeavilyDamaged());
    }

    @Override
    public boolean move(double unifiedDistance) {
        boolean canMove = this.getCanMove();
        if (!canMove) {
            return false;
        }

        // if no movement is required, do nothing successfully ...
        if (this.getDestinattion().equals(this.getPosition())) {
            return true;
        }
        
        Point cp = this.getPosition(); // current position
        Point ns = new Point(); // next step
        
        Point dir = Compas.getDirectionByName(Compas.getDirectionName(this.getPosition(), this.destination));
        
        // just for not evaluating that twice
        unifiedDistance = (unifiedDistance * this.speed);
        
        // relative position / offset - acumulated fractions from each step
        this.rx += (unifiedDistance);
        this.ry += (unifiedDistance);
        
        // evaluation of coords after thist step and recalculating relative position
        ns.setLocation(this.rx.intValue(), this.ry.intValue());
        this.rx -= ns.x;
        this.ry -= ns.y;
        
        // flag indicating if agent was able to move to new coordinates
        boolean succes = true;        
        if (this.getMapContainer() != null) {
            succes = this.getMapContainer().getMap()
               .moveGameObjectTo(this, (cp.x + (ns.x  * dir.x)) , (cp.y + (ns.y  * dir.y)));
        }
        
        // dobug purposes only
        // Model.setCurrnetChunk((Chunk) this.getMapContainer());
        
        return succes;
    }
    
    @Override
    public int getPedometr() {
        Double distnace = this.pedometr;
        return distnace.intValue();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CarrierGameObject interface">
    @Override
    public boolean setCarrying(CarryableGameObject item) {
        if(this.getIsCarried() || !item.getIsCarryable() || item.getIsCarried()) {
            return false;
        }
        
        Point c, i;
        c = this.getPosition();
        i = item.getPosition();
        
        if (CollisionDetector.getDistance(c, i) > DEFAULT_GRABBING_DISTANCE) {
            return false;
        }
        
        this.item = item;
        return true;
    }

    @Override
    public boolean dropItem() {
        return this.item.removeCarrier(this);
    }

    @Override
    public CarryableGameObject getItem() {
        return this.item;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CarryableGameObject interface">
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
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="DestructableGameObject interface">
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="PoweredGameObject interface">
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
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="DrawableGameObject interface">
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Collision detection">
    @Override
    public boolean colides(CollidableGameObject another) {
        if (another instanceof Agent || another instanceof AntHill) {
            return false;
        }
        return CollisionDetector.getCollide(this, another);
    }
    // </editor-fold>
    
}

// <editor-fold defaultstate="collapsed" desc="Hide">
// </editor-fold>