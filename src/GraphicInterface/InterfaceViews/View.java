/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package GraphicInterface.InterfaceViews;

import Enviroment.Model;
import GraphicInterface.GUIEvents.GUIEvent;
import GraphicInterface.GUIEvents.GUIEventListener;
import GraphicInterface.ViewComponents.Container;
import GraphicInterface.ViewComponents.Components.EmptyContainer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class View implements GUIEventListener, ViewSpaceHolder {
    // default size of view / screen;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    private final Rectangle aov;        // area of view
    private Container container;

    public View() {
        this(WIDTH, HEIGHT);
    }
    
    public View(int width, int height) {
        this.aov = new Rectangle(width, height);
        this.container = new EmptyContainer(this);
    }
    
    public View(Point p, int width, int height) {
        this.aov = new Rectangle(p.x, p.y, width, height);
        this.container = new EmptyContainer(this);
    }

    @Override
    public Rectangle getAreaOfView() {
        Rectangle r;
        synchronized (this.aov) {
            r = new Rectangle(this.aov);
        }
        return r; 
    }
    
    @Override
    public Point getViewPosition() {
        Point p;
        synchronized (this.aov) {
            p = new Point(this.aov.getLocation());
        }
        return p; 
    }
    
    public void setWindowSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            return;
        }
        int shiftY;
        boolean changed = false;
        synchronized(this.aov) {
            shiftY = height - aov.height;
            changed = this.aov.width != width || this.aov.height != height;
            this.aov.setSize(width, height);
        }
        if (changed) {
            this.container.aovChanged();
        }
    }
    
    public void setWindowSize(Dimension d) {
        this.setWindowSize(d.width, d.height);
    }
    
    @Override
    public boolean shiftWindow(int horizontaly, int verticaly) {
        int x, y;
        int mapWidth = Model.getCurrentMap().getMapWidth();
        int mapHeight = Model.getCurrentMap().getMapHeight();
        Point p;
        synchronized(this.aov) {
            p = this.aov.getLocation();
            x = horizontaly + p.x;
            y = verticaly + p.y;
            if (x < 0) {
                horizontaly = 0 - p.x;
            }
            if ((x + this.aov.width) > mapWidth) {
                horizontaly = mapWidth - this.aov.width - p.x;
            }
            if (y < 0) {
                verticaly = 0 - p.y;
            }
            if ((y + this.aov.height) > mapHeight) {
                verticaly = (mapHeight - this.aov.height) - p.y;
            }
            this.aov.translate(horizontaly, verticaly);
        }
        return (x != 0 || y != 0);
    }
    
    public Container getContainer() {
        return this.container;
    }
       
    public void paintContent(Graphics g) {
        Rectangle r = this.getAreaOfView();
        g.clearRect(0, 0, r.width, r.height);
        this.container.paint(g);
    }

    @Override
    public void handleGUIEvent(GUIEvent e) {
        this.getContainer().handleGUIEvent(e);
    }
    
}
