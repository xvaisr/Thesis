/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface.InterfaceViews.Depricated;

import Enviroment.EnvObjects.GameObject;
import Enviroment.SearchForDraw;
import GraphicInterface.Input.GameObjectCasher;
import GraphicInterface.InterfaceViews.View;
import GraphicInterface.InterfaceViews.ViewSpaceHolder;
import GraphicInterface.ViewComponents.Components.InteractiveComponents.MapComponent;
import GraphicInterface.ViewComponents.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class MapViewDebug extends View implements GameObjectCasher {
    public static final MapViewDebug  mapview = new MapViewDebug(800, 600);
    private final Rectangle selection;
    private SearchForDraw casher;
    private MapComponent map;

    public MapViewDebug() {
        super();
        this.selection = new Rectangle();
        this.casher = SearchForDraw.getInstance();
        this.casher.setNewViewSpaceHolder(this);
        
        this.map = new MapComponent(this.casher);
        Container c = this.getContainer();
        c.addComponent(map);
        c.setVisible(true);
    }
    
    public MapViewDebug(int width, int height) {
        this(new Point(), width, height);
    }
    
    public MapViewDebug(Point p, int width, int height) {
        super(p, width, height);
        this.selection = new Rectangle();
        this.casher = SearchForDraw.getInstance();
        this.casher.setNewViewSpaceHolder(this);
        
        this.map = new MapComponent(this.casher);
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

    // nothing to be done in these methods
    @Override
    public void setViewChanged() {}

    @Override
    public void setMapChanged() {}

    @Override
    public void setNewViewSpaceHolder(ViewSpaceHolder view) {}

}
