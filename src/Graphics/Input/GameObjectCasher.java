/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.Input;

import Enviroment.EnvObjects.GameObject;
import Graphics.ViewAndDraw.ViewSpaceHolder;
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
