/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package GraphicInterface.InterfaceViews;

import GraphicInterface.ViewComponents.Components.InteractiveComponents.MainMenu;
import java.awt.Point;

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
