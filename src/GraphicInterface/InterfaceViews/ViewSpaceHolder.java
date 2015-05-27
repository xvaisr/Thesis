/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package GraphicInterface.InterfaceViews;

import java.awt.Point;
import java.awt.Rectangle;

public interface ViewSpaceHolder {    
    public Rectangle getAreaOfView();
    public Point getViewPosition();
    public boolean shiftWindow(int horizontaly, int verticaly);
}
