/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment;

import Enviroment.EnvObjects.GameObject;
import Graphics.ViewAndDraw.MapView;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class SearchForDraw implements Runnable {
    private MapView view;
    private ArrayList<GameObject> items;

    public SearchForDraw(MapView v) {
        this.items = new ArrayList();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
