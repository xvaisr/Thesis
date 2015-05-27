/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package GraphicInterface;

import GraphicInterface.UiCommands.UiCommand;

public class GameFSM {

    private GameState currentState;
    
    public GameFSM() {
        this.currentState = GameState.mainMenu;
    }
    
    public void processCommand(UiCommand command) {
        
    }
    
    
}
