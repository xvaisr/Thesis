/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjects;

import Enviroment.EnvObjFeatures.MoveableGameObject;
import Enviroment.EnviromentalMap.Compas;
import Enviroment.EnviromentalMap.MapContainer;
import java.awt.Point;

public class MoveableGameObjectPrototype extends GameObjectPrototype
                                         implements MoveableGameObject
{
    private Point destination;
    private double pedometr;
    private double rx, ry;
    private int speed;
    
    public MoveableGameObjectPrototype() {
        super(new Point());
        this.initMoveable();
        
    }

    public MoveableGameObjectPrototype(int x, int y) {
        super(new Point(x, y));
        this.initMoveable();
    }
    
    public MoveableGameObjectPrototype(Point position) {
        super(null, position);
        this.initMoveable();
    }
    
    public MoveableGameObjectPrototype(MapContainer mc, int x, int y) {
        super(mc, new Point(x, y));
        this.initMoveable();
    }
    
    public MoveableGameObjectPrototype(MapContainer mc, Point position) {
        super(mc, position);
        this.initMoveable();
    }

    private void initMoveable() {
        Point p = this.getPosition();
        this.rx = p.getX();
        this.ry = p.getY();
        this.destination = p;
        this.speed = DEFAULT_SPEED;
        this.pedometr = 0.0;
    }
    
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
        if (dest != null) { 
            this.destination = dest; 
        }
    }

    @Override
    public Point getDestinattion() {
        return new Point(this.destination);
    }
    
    @Override
    public boolean getCanMove() {
        return true;
    }

    @Override
    public boolean move(double unifiedDistance) {
        boolean canMove = this.getCanMove();
        if (!canMove) {
            return false;
        }
        
        if (this.destination.equals(this.getPosition())) {
            this.rx = this.destination.getX();
            this.ry = this.destination.getY();
            return true;
        }
        
        Point cp = this.getPosition();
        Point dir = Compas.getDirectionByName(Compas.getDirectionName(this.getPosition(), this.destination));
        double dx, dy;
        
        // just for not evaluating that twice
        unifiedDistance = (unifiedDistance * this.speed);
        
        dx = (unifiedDistance * dir.x) + this.rx;
        dy = (unifiedDistance * dir.y) + this.ry;
        
        Point np = new Point(); 
        np.setLocation((dx + cp.x), (dy + cp.y));
        
        this.rx = dx;
        this.ry = dy;
        
        if (!np.equals(cp)) {
            this.setPosition(np);
        }
        
        return true;
    }
    
    @Override
    public int getPedometr() {
        Double distnace = this.pedometr;
        return distnace.intValue();
    }
    
}
