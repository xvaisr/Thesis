/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicInterface.UiCommands;

import GraphicInterface.Input.MouseEventProcessor;

/**
 *
 * @author Roman Vais romanvais@seznam.cz
 */
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
