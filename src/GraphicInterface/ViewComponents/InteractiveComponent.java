/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package GraphicInterface.ViewComponents;

import GraphicInterface.GUIEvents.GUIEventListener;
import GraphicInterface.Input.MouseEventProcessor;

public interface InteractiveComponent extends UiComponent, GUIEventListener {
    
    public MouseEventProcessor getMouseEventProcessor();
}
