/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface.InterfaceViews;

import GraphicInterface.ViewComponents.Components.InteractiveComponents.MainMenu;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class MainMenuView extends View {
    
    public MainMenuView() {
        super();
        this.initMainMenuView();
    }
    
    public MainMenuView(int width, int height) {
        super(width, height);
        this.initMainMenuView();
    }
    
    public MainMenuView(Point p, int width, int height) {
        super(p, width, height);
        this.initMainMenuView();
    }
    
    private void initMainMenuView () {
        MainMenu menu = new MainMenu();
        this.getContainer().addComponent(menu);
        this.getContainer().setVisible(true);
    }

    
    
    
}
