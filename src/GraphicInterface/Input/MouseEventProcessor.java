/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package GraphicInterface.Input;

import GraphicInterface.GUIEvents.GUIEventListener;
import GraphicInterface.ViewComponents.InteractiveComponent;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.event.MouseInputListener;
 
public interface MouseEventProcessor extends MouseInputListener, GUIEventListener{
    
    public void setParentComponent(InteractiveComponent component);
    public InteractiveComponent getParentComponent();
    
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
