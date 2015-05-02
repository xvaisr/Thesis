/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw.ViewComponents;

import Graphics.Input.MouseCommandProcessor;
import Graphics.Input.MouseEventProcessor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

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
        this.mouse = new MouseCommandProcessor(this);
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
