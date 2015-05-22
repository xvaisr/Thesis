/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicInterface.UiCommands;

/**
 * This interface imlements observ
 * @see UiCommand class
 * @author Roman Vais romanvais@seznam.cz
 */
public interface UiCommandListener {
    /* probably sholud be implemented as synchronized method if handeling
       requires access to shared resources                                  */
    
    /**
     * Method used for recieving commands to be performed
     * @param command UiCommand object containing information needed to perform
     *            action required
     */
    public void carryOutCommand(UiCommand command);
    
}
