/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface.InterfaceViews;

import Enviroment.SearchForDraw;
import GraphicInterface.InterfaceViews.View;
import GraphicInterface.ViewComponents.Components.InteractiveComponents.MapComponent;
import GraphicInterface.ViewComponents.Container;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class MapView extends View {
    private SearchForDraw casher;
    private MapComponent map;

    public MapView() {
        super();
        this.initMapView();
    }
    
    public MapView(int width, int height) {
        super(width, height);
        this.initMapView();
    }
    
    public MapView(Point p, int width, int height) {
        super(p, width, height);
        this.initMapView();
    }
    
    private void initMapView () {
        this.casher = SearchForDraw.getInstance();
        this.casher.setNewViewSpaceHolder(this);
        
        this.map = new MapComponent(this.casher);
        Container c = this.getContainer();
        c.addComponent(map);
        c.setVisible(true);
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

}
