/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package GraphicInterface.GUIEvents;

import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

/**
 *
 * @author Roman Vais romanvais@seznam.cz
 */
public class GUIEvent  {
    public static enum type {
        keyTyped,
        keyPressed,
        keyReleased,
        mouseClicked,
        mousePressed,
        mouseReleased,
        mouseEntered,
        mouseExited,
        mouseDragged,
        mouseMoved,
        componentResized,
        componentMoved,
        componentShown,
        componentHidden,
        windowOpened,
    }
    private GUIEventCaster source;
    private GUIEvent.type t;
    private KeyEvent key;
    private MouseEvent mouse;
    private ComponentEvent component;
    private WindowEvent window;
    
    public GUIEvent(GUIEventCaster source, GUIEvent.type t, MouseEvent m) {
        if (source == null) throw new NullPointerException("Source of GUIEvent can't be NULL!");
        this.t = t;
        this.source = source;
        this.mouse = m;
        this.key = null;
        this.component = null;
        this.window = null;
    }
    
    public GUIEvent(GUIEventCaster source, GUIEvent.type t, KeyEvent k) {
        if (source == null) throw new NullPointerException("Source of GUIEvent can't be NULL!");
        this.t = t;
        this.source = source;
        this.mouse = null;
        this.key = k;
        this.component = null;
        this.window = null;
    }
    
    public GUIEvent(GUIEventCaster source, GUIEvent.type t, ComponentEvent c) {
        if (source == null) throw new NullPointerException("Source of GUIEvent can't be NULL!");
        this.t = t;
        this.source = source;
        this.mouse = null;
        this.key = null;
        this.component = c;
        this.window = null;
    }
    
    public GUIEvent(GUIEventCaster source, GUIEvent.type t, WindowEvent c) {
        if (source == null) throw new NullPointerException("Source of GUIEvent can't be NULL!");
        this.t = t;
        this.source = source;
        this.mouse = null;
        this.key = null;
        this.component = null;
        this.window = c;
    }
    
    public GUIEventCaster getSource() {
        return this.source;
    }
    
    public GUIEvent.type getType() {
        return this.t;
    }
    
    public MouseEvent getMouseEvent() {
        return this.mouse;
    }
    
    public KeyEvent getKeyEvent() {
        return this.key;
    }
    
    public ComponentEvent getComponentEvent() {
        return this.component;
    }
    
    public WindowEvent getWindowEvent() {
        return this.window;
    }
}
