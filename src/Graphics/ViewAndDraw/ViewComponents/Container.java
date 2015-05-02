/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw.ViewComponents;

import Graphics.GUIEvents.GUIEventListener;
import Graphics.ViewAndDraw.ViewSpaceHolder;
import java.awt.Graphics;

/**
 *
 * @author lennylinux
 */
public interface Container extends UiComponent, GUIEventListener {


    
    public void addComponent(UiComponent comp);
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
