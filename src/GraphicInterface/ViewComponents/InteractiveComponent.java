/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface.ViewComponents;

import GraphicInterface.GUIEvents.GUIEventListener;
import GraphicInterface.Input.MouseEventProcessor;

/**
 *
 * @author lennylinux
 */
public interface InteractiveComponent extends UiComponent, GUIEventListener {
    
    public MouseEventProcessor getMouseEventProcessor();
}
