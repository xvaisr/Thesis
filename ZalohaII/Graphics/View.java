/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics;

import Enviroment.EnvObjects.GameObject;
import Enviroment.Model;
import Graphics.GUIEvents.GUIEvent;
import Graphics.GUIEvents.GUIEventListener;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class View implements Runnable, ComponentListener, GUIEventListener {
    private static final int H_LEFT = -1;
    private static final int H_RIGHT = 1;
    private static final int V_DOWN = -1;
    private static final int V_UP = 1;
    private static final int VH_NONE = 0;   
    private static final int STEP = 15;
    private static final int DELAY_MS = 200;
    private static final int ms2sec = 1000;
        
    private final GameWindow window;
    private final Rectangle aov;        // area of view
    private int vertical;               // which way is window moving verticaly
    private int horizonta;              // which way is window moving horizontaly
    private int step;                   // pixels shifted per step
    private int delay;                  // how long to wait before next step
    
    

    public View() {
        this.window = new GameWindow();
        this.aov = new Rectangle(this.window.getSize());
        this.window.addComponentListener(this);
        this.vertical = VH_NONE;        // window is not moving
        this.horizonta = VH_NONE;
        this.step = STEP;
        this.delay = DELAY_MS;
/*        this.mapHeight = Model.getMapHeight();
          this.mapWidth = Model.getMapWidth(); */
    }
    
    public void setDelay(int ms) {
        synchronized(this) {
            if (ms < 0) {
                ms = 0;
            }
            this.delay = ms;
        }
    }
    
    public void setStep(int px) {
        synchronized(this) {
            if (px < 0) {
                px = 0;
            }
            this.step = px;
        }    
    }
    
    public ArrayList<GameObject> getVisible() {
        return Model.getObjInArea(aov);
    }
    
    public Point getViewPosition() {
        Point p;
        synchronized (this) {
            p = this.aov.getLocation();
        }
        return p; 
    }
    
    private void shiftWindow() {
        int tx, ty;
        synchronized(this) {
            tx = this.step * this.horizonta;
            ty = this.step * this.vertical;
            this.aov.translate(tx, ty);
        }
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        if (e.getComponent() == this.window && e.getID() == ComponentEvent.COMPONENT_RESIZED) {
            Dimension size = this.window.getSize();
            aov.setSize(size);
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processGUIEvent(GUIEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
