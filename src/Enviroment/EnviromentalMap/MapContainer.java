/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnviromentalMap;

import Enviroment.EnviromentalMap.MapInterface;
import Enviroment.EnvObjects.GameObject;

/**
 *
 * @author lennylinux
 */
public interface MapContainer {
    
    public MapInterface getMap();
    public boolean addGameObject(GameObject o);
    public boolean getIsInsideContainer(GameObject o);
    public void removeGameObject(GameObject o);
    
}
