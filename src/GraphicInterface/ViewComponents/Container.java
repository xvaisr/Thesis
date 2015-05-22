/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
