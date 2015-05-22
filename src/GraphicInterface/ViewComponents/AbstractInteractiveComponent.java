/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface.ViewComponents;

import GraphicInterface.Input.MouseBehavior;
import GraphicInterface.Input.MouseEventProcessor;
import java.awt.Dimension;

/**
 *
 * @author lennylinux
 */
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
