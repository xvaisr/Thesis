/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package GraphicInterface.GUIEvents;

/**
 * This interface is implementitng part of observer pattern
 * @see GUIEventListener and GUIEvent
 * @author Roman Vais romanvais@seznam.cz
 */
public interface GUIEventCaster {
    /* these methods must be implementid with synchronized modificator on
       shared resources like array of listeners                           */
    
    /**
     * This method serves for register observer/listener who should be notified on event.
     * @param listener instance of NetworkEventListener intrface implementig class 
     */
    public void addGUIListener(GUIEventListener listener);
    
    /**
     * This method serves for removing observer/listener who has been previously reistred.
     * @param listener object already added as listener via method addListener()
     */
    public void removeGUIListener(GUIEventListener listener);
    
    /**
     * This method removes all previously registred observers/listeners 
     */
    public void removeAllGUIListeners();
    
    /**
     * This method notifies all registred listeners with given event
     * @param event GUISEvent object containing information about event and
     *              data to process by listenerner
     */
    public void fireGUIEvent(GUIEvent event);
    
}
