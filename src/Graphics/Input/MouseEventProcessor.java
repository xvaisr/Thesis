/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.Input;

import Graphics.GUIEvents.GUIEventListener;
import Graphics.ViewAndDraw.ViewComponents.InteractiveComponent;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.event.MouseInputListener;
/**
 *
 * @author lennylinux
 */
public interface MouseEventProcessor extends MouseInputListener, GUIEventListener{
    
    public void setParentComponent(InteractiveComponent component);
    public void reset();
    
    public Object getLock();
    public Point getShiftVector();
    public Rectangle getSelectionRectangle();
    
    public boolean getLeftClick();
    public boolean getRightClick();
    public boolean getShiftAction();
    public boolean getSelectAction();
    public boolean getHoverAction();
    public boolean getLeftMouse();
    public boolean getRightMouse();
    
    
    
    
    
    
}
