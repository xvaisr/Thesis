/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw;

import Enviroment.EnvObjects.GameObject;
import Enviroment.EnviromentalMap.MapInterface;
import Enviroment.Model;
import Enviroment.SearchForDraw;
import Graphics.Input.GameObjectCasher;
import Graphics.ViewAndDraw.View;
import Graphics.Input.MouseBehavior;
import Graphics.ViewAndDraw.ViewComponents.Components.MapComponent;
import Graphics.ViewAndDraw.ViewComponents.Container;
import Graphics.ViewAndDraw.ViewComponents.UiComponent;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class MapView extends View implements GameObjectCasher {
    private final Rectangle selection;
    private SearchForDraw casher;
    private MapComponent map;

    public MapView() {
        super();
        this.selection = new Rectangle();
        this.casher = null;
        
        this.map = new MapComponent(this);
        Container c = this.getContainer();
        c.addComponent(map);
        c.setVisible(true);
    }
    
    public MapView(int width, int height) {
        this(new Point(), width, height);
    }
    
    public MapView(Point p, int width, int height) {
        super(p, width, height);
        this.selection = new Rectangle();
        this.casher = null;
        
        UiComponent map = new MapComponent(this);
        Container c = this.getContainer();
        c.addComponent(map);
        c.setVisible(true);
    }
    
    public void setSelection (Rectangle r) {
        synchronized(this.selection) {
            if (r != null) {
                this.selection.setLocation(r.getLocation());
                this.selection.setSize(r.getSize());
            }
            else {
                this.selection.setLocation(0, 0);
                this.selection.setSize(0, 0);
            }
        }
    }
    
    public void setSearcher(SearchForDraw s) {
        if (s != null) {
            this.casher = s;
            this.map.setNewGameObjectCasher(casher);
        }
    }
    
    @Override
    public void setWindowSize(int width, int height) {
        super.setWindowSize(width, height);
        if (this.casher != null) {
            casher.setViewChanged();
        }
        
    }
    
    @Override
    public void setWindowSize(Dimension d) {
        super.setWindowSize(d);
        if (this.casher != null) {
            casher.setViewChanged();
        }
    }
    
    @Override
    public boolean shiftWindow(int horizontaly, int verticaly) {
        boolean b = super.shiftWindow(horizontaly, verticaly);
        if (b && this.casher != null) {
            casher.setViewChanged();
        }
        return b;
    }
    
    @Override
    public ArrayList<GameObject> getVisibleObjects() {
        return new ArrayList();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setViewChanged() {}

    @Override
    public void setMapChanged() {}

    @Override
    public void setNewViewSpaceHolder(ViewSpaceHolder view) {}
    
}
