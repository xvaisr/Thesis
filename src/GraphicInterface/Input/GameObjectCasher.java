/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface.Input;

import Enviroment.EnvObjects.GameObject;
import GraphicInterface.InterfaceViews.ViewSpaceHolder;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public interface GameObjectCasher {

    public void setViewChanged();
    public void setMapChanged();
    
    public void setNewViewSpaceHolder(ViewSpaceHolder view);
    
    public ArrayList<GameObject> getVisibleObjects();
}
