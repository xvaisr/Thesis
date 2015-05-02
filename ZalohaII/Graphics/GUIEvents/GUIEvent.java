/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.GUIEvents;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Roman Vais romanvais@seznam.cz
 */
public class GUIEvent  {
    public static enum type {
        mouseEvent,
        keyEvent
    }
    private GUIEventCaster source;
    private GUIEvent.type t;
    private KeyEvent key;
    private MouseEvent mouse;
    
    public GUIEvent(GUIEventCaster source, GUIEvent.type t, MouseEvent m) {
        if (source == null) throw new NullPointerException("Source of NetworkEvent can't be NULL!");
        this.t = t;
        this.source = source;
        this.mouse = m;
        this.key = null;
    }
    
    public GUIEvent(GUIEventCaster source, GUIEvent.type t, KeyEvent k) {
        if (source == null) throw new NullPointerException("Source of NetworkEvent can't be NULL!");
        this.t = t;
        this.source = source;
        this.mouse = null;
        this.key = k;
    }
    
    public GUIEventCaster getSource() {
        return this.source;
    }
    
    public GUIEvent.type getType() {
        return this.t;
    }
        
}
