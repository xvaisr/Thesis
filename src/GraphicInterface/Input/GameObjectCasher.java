/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package GraphicInterface.Input;

import Enviroment.EnvObjects.GameObject;
import GraphicInterface.InterfaceViews.ViewSpaceHolder;
import java.util.ArrayList;

public interface GameObjectCasher {

    public void setViewChanged();
    public void setMapChanged();
    
    public void setNewViewSpaceHolder(ViewSpaceHolder view);
    
    public ArrayList<GameObject> getVisibleObjects();
}
