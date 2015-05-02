/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects.Agents;

import Agents.Team.Team;
import Enviroment.EnvObjFeatures.AgentInterface;
import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Emitors.SmellEmitor;
import Enviroment.EnvObjFeatures.Emitors.VisualEmitor;
import Enviroment.EnvObjects.DetectableObjectPrototype;
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
public class AntHill extends DetectableObjectPrototype
                     implements DrawableGameObject, AgentInterface,
                             CollidableGameObject
{
    // private emitor constants
    private final int SMELL_DISTANCE = 150;  // How far is anthill pecivable via smell
    
    // shape setting constants
    private static final int ANTHILL_CORNERS = 25;
    
    // default name constant
    private static final String DEFAULT_ANTHILL_NAME = "NeutralAnthill";
    
    // AgentInterface
    private volatile String name;
    private volatile Team team;
    
    // DrawableGameObject related
    private volatile Color color;
    private volatile Painter painter;
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Anthill Constructors">
    public AntHill() {
        super();
        
        this.initAnthill();
    }
    
    public AntHill(int x, int y) {
        super(x, y);
        
        this.initAnthill();
    }
    
    public AntHill(Point position) {
        super(position);
        
        this.initAnthill();
    }
    
    public AntHill(MapContainer mc, int x, int y) {
        super(mc, x, y);
        
        this.initAnthill();
    }
    
    public AntHill(MapContainer mc, Point position) {
        super(mc, position);
        
        this.initAnthill();
    }
    // </editor-fold>
    
    private void initAnthill() {
        // Drawable object values
        this.color = Color.LIGHT_GRAY; // color will be redefined form outside
        this.painter = null;
        
        // Shape related
        Shape s = new Shape();
        s.addVertex(-ANTHILL_CORNERS, ANTHILL_CORNERS);
        s.addVertex(ANTHILL_CORNERS, ANTHILL_CORNERS);
        s.addVertex(ANTHILL_CORNERS, -ANTHILL_CORNERS);
        s.addVertex(-ANTHILL_CORNERS, -ANTHILL_CORNERS);
        this.setNewShape(s);
        
        // setup defaults
        this.name = DEFAULT_ANTHILL_NAME;
        this.team = null;
        
        // setup default emitors - visibility (ant)
        Emitor e;
        e = new VisualEmitor(this);
        e.setEmitorActive(true);
        
        e = new SmellEmitor(this);
        e.setEmitorRange(SMELL_DISTANCE);
        e.setEmitorActive(true);
        
        // setup smell emitor's "smell"
        SmellEmitor smell;
        try {
            smell = (SmellEmitor) this.getEmitor(SmellEmitor.class);
            smell.setEmitorSmell(this.color.getRed());
        }
        catch (Exception ex) {
            // can't setup smell's "smell" :( No matter, do nothing
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="DrawableGameObject interface">
    @Override
    public void setColor(Color c) {
        this.color = c;
        
        // setup smell emitor's "smell" to be identical with current color
        SmellEmitor smell;
        try {
            smell = (SmellEmitor) this.getEmitor(SmellEmitor.class);
            smell.setEmitorSmell(this.color.getRed());
        }
        catch (Exception ex) {
            // can't setup smell's "smell" :( No matter, do nothing
        }
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
        this.setColor(this.team.getColor());
    }
    // </editor-fold>

    @Override
    public boolean colides(CollidableGameObject another) {
        if (another instanceof Agent) {
            return false;
        }
        return CollisionDetector.getCollide(this, another);
    }
}
