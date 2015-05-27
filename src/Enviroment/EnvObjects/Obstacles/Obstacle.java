/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Enviroment.EnvObjects.Obstacles;

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

public class Obstacle extends DetectableObjectPrototype 
                      implements DrawableGameObject, CollidableGameObject
{
    
    private volatile Color color;
    private volatile Painter painter;
    
    public Obstacle() {
        super();
        this.initObsticle();
    }
    
    public Obstacle(int x, int y) {
        super(x, y);
        this.initObsticle();
    }
    
    public Obstacle(Point position) {
        super(position);
        this.initObsticle();
    }
    
    public Obstacle(MapContainer mc, int x, int y) {
        super(mc, x, y);
        this.initObsticle();
    }
    
    public Obstacle(MapContainer mc, Point position) {
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