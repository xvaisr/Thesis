/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjects.Pheromones;

import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjFeatures.Emitors.SmellEmitor;
import Enviroment.EnvObjects.DetectableObjectPrototype;
import Enviroment.EnviromentalMap.MapContainer;
import Graphics.Painters.Painter;
import java.awt.Color;
import java.awt.Point;

public class PheromonePoint extends DetectableObjectPrototype 
                            implements DrawableGameObject
{
    public static final int DISPERSION_RADIUS = 30;
    public static final int CONCENTRATION = 1500;
    public static final int FADING_PERCENTAGE = 5;
    
    // DrawableGameObject related
    private volatile Color color;
    private volatile Painter painter;
    
    public PheromonePoint() {
        super();
        this.initPheromone();
    }
    
    public PheromonePoint(int x, int y) {
        super(x, y);
        this.initPheromone();
    }
    
    public PheromonePoint(Point position) {
        super(position);
        this.initPheromone();
    }
    
    public PheromonePoint(MapContainer mc, int x, int y) {
        super(mc, x, y);
        this.initPheromone();
    }
    
    public PheromonePoint(MapContainer mc, Point position) {
        super(mc, position);
        this.initPheromone();
    }
    
    private void initPheromone() {
        this.setColor(Color.DARK_GRAY);
        this.setPainter(null);
        
        SmellEmitor smell = new SmellEmitor(this);
        smell.setDecreasePercentage(FADING_PERCENTAGE);
        smell.setDispersionRadius(DISPERSION_RADIUS);
        smell.setEmitorStrength(CONCENTRATION);
        smell.setEmitorActive(true);
        this.setEmitor(smell);
    }

    @Override
    public void setColor(Color c) {
        if (c == null) {
            return;
        }
        
        SmellEmitor smell = (SmellEmitor) this.getEmitor(SmellEmitor.class);
        this.color = c;
        smell.setEmitorSmell(c.getRGB());
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


    
}
