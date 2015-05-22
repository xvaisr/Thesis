/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects.Obsticles;
import Enviroment.EnvObjFeatures.CollidableGameObject;
import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjFeatures.Emitors.Emitor;
import Enviroment.EnvObjFeatures.Emitors.VisualEmitor;
import Enviroment.EnvObjects.DetectableObjectPrototype;
import Enviroment.EnviromentalMap.CollisionDetector;
import Enviroment.EnviromentalMap.MapContainer;
import Graphics.Painters.Painter;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class Obsticle extends DetectableObjectPrototype 
                      implements DrawableGameObject, CollidableGameObject
{
    
    private volatile Color color;
    private volatile Painter painter;
    
    public Obsticle() {
        super();
        this.initObsticle();
    }
    
    public Obsticle(int x, int y) {
        super(x, y);
        this.initObsticle();
    }
    
    public Obsticle(Point position) {
        super(position);
        this.initObsticle();
    }
    
    public Obsticle(MapContainer mc, int x, int y) {
        super(mc, x, y);
        this.initObsticle();
    }
    
    public Obsticle(MapContainer mc, Point position) {
        super(mc, position);
        this.initObsticle();
    }

    
    private void initObsticle() {
        this.color = new Color(85, 51, 17);
        this.painter = null;
        
        // Visual emitor (the only one obsticla has ...)
        Emitor e = new VisualEmitor(this);
        e.setEmitorActive(true);
        this.setEmitor(e);
    }
    
    @Override
    public void setColor(Color c) {
        this.color = c;
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