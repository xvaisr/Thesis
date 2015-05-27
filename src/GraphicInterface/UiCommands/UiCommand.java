/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package GraphicInterface.UiCommands;

import GraphicInterface.Input.MouseEventProcessor;

public class UiCommand  {
    
    public static enum type {
        mouseCommand
    }
    
    private UiCommand.type ctype;
    private MouseEventProcessor mouse;
    private String command;
    
    public UiCommand(String command, MouseEventProcessor mouse) {
        this.ctype = UiCommand.type.mouseCommand;
    }
  
}
