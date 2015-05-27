/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package GraphicInterface.ViewComponents.Components.InteractiveComponents;

import GraphicInterface.InterfaceViews.View;

public class MainMenu extends Menu {
    public MainMenu() {
        super();
        this.initMainMenu();
    }
    
    public MainMenu(View view) {
        super(view);
        this.initMainMenu();
    }
    
    private void initMainMenu() {
        this.setButtonIndent(12);
        this.addMenuButton("New Game");
        // this.addMenuButton("Vlk sel spolecne s Karkulou ...");
        this.addMenuButton("Exit");
        this.getBox();
    }
    
}
