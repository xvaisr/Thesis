/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package GraphicInterface.ViewComponents;

import GraphicInterface.GUIEvents.GUIEventListener;
import GraphicInterface.InterfaceViews.ViewSpaceHolder;
import GraphicInterface.ViewComponents.UiComponent;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public interface Container extends UiComponent, GUIEventListener {


    
    public void addComponent(UiComponent comp);
    public ArrayList<UiComponent> getComponents();
    public void removeComponent(UiComponent comp);
    public void removeAllComponents();
    
    public void paintContent(Graphics g);
    
    public void aovChanged();
    public ViewSpaceHolder getViewSpaceHolder();
    
    /* public void registerEventListener(GUIEventListener listener);
       public void registerEventListeners();
       public void dispatchGUIEvent(GUIEvent e);
    */
    
    
    
}
