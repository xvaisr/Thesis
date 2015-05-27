/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package GraphicInterface.UiCommands;

/**
 * This interface imlements observer patter
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
