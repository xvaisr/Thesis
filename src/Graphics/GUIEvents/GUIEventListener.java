/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.GUIEvents;

/**
 * This interface imlements observ
 * @see GUIEventCaster and GUIEvent
 * @author Roman Vais romanvais@seznam.cz
 */
public interface GUIEventListener {
    /* probably sholud be implemented as synchronized method if handeling
       requires access to shared resources                                  */
    
    /**
     * Method used for recieving events from NetworkEventCaster
     * @param e GUIEvent
     * @see GUIEventListener and GUIEvent
     */
    public void handleGUIEvent(GUIEvent e);
    
}
