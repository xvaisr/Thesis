/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package GraphicInterface.ViewComponents;

import GraphicInterface.Input.MouseBehavior;
import GraphicInterface.Input.MouseEventProcessor;
import java.awt.Dimension;

public abstract class AbstractInteractiveComponent extends AbstractComponent
                                          implements InteractiveComponent
{
    private MouseEventProcessor mouse;

    public AbstractInteractiveComponent() {
        super();
        this.mouse = new MouseBehavior(this);
    }
    
    public AbstractInteractiveComponent(int width, int height) {
        super(width, height);
    }
    
    public AbstractInteractiveComponent(Dimension d) {
        super(d);
    }

    @Override
    public MouseEventProcessor getMouseEventProcessor() {
        return this.mouse;
    }
    
}
