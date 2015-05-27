/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjects.Agents;

import Enviroment.EnvObjFeatures.DestructableGameObject;
import Enviroment.EnviromentalMap.MapContainer;
import java.awt.Point;

public class SimpleAnt extends Agent {
    
    private DestructableGameObject target;
    private Point location;
    private Point direction;
    
    
    public SimpleAnt() {
        super();       
        this.initSimpleAnt();
    }
    
    public SimpleAnt(int x, int y) {
        super(x, y);
        this.initSimpleAnt();
    }
    
    public SimpleAnt(Point position) {
        super(position);
        this.initSimpleAnt();
    }
    
    public SimpleAnt(MapContainer mc, int x, int y) {
        super(mc, x, y);
        this.initSimpleAnt();
    }
    
    public SimpleAnt(MapContainer mc, Point position) {
        super(mc, position);
        this.initSimpleAnt();
    }
    
    private void initSimpleAnt() {
    }
    
    public void setTarget(DestructableGameObject t) {
        this.target = t;
    }
    
    public void attack() {
    }
    
    public void collect() {
    }

}
