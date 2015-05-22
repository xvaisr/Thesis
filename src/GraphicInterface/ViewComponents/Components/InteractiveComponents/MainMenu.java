/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface.ViewComponents.Components.InteractiveComponents;

import GraphicInterface.InterfaceViews.View;

/**
 *
 * @author lennylinux
 */
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
