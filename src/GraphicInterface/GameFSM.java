/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface;

import GraphicInterface.UiCommands.UiCommand;

/**
 *
 * @author lennylinux
 */
public class GameFSM {

    private GameState currentState;
    
    public GameFSM() {
        this.currentState = GameState.mainMenu;
    }
    
    public void processCommand(UiCommand command) {
        
    }
    
    
}
