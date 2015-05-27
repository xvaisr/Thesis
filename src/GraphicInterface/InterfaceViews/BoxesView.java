/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package GraphicInterface.InterfaceViews;

import GraphicInterface.ViewComponents.Components.InteractiveComponents.Button;
import GraphicInterface.ViewComponents.Components.DrawRectangle;
import GraphicInterface.ViewComponents.Container;
import GraphicInterface.ViewComponents.UiComponent;
import java.awt.Color;
import java.util.ArrayList;

public class BoxesView extends View {

    private ArrayList<DrawRectangle> rectangles;

    public BoxesView() {
        super();
        Color c = Color.DARK_GRAY;
        this.rectangles = new ArrayList(5);
        Container box = this.getContainer();
        
        for (int i = 0; i < 5; i++) {
            DrawRectangle r = new DrawRectangle(c, 10, 10);
            int j = (i+1)*10;
            r.setMargin(j, j, i, i);
            c = c.brighter();
            c = c.brighter();
            
            this.rectangles.add(r);
            box.addComponent(r);
        }
        
        box.setVisible(true);
        
    }
    
    public BoxesView(int width, int height) {
        super(width, height);
        Color c = Color.DARK_GRAY;
        this.rectangles = new ArrayList(5);
        Container box = this.getContainer();
        
        for (int i = 0; i < 5; i++) {
            DrawRectangle r = new DrawRectangle(c, 100, 200);
            int j = (i+1)*10;
            r.setMargin(j, j, 0, j);
            c = c.brighter();
            
            this.rectangles.add(r);
            box.addComponent(r);
        }
        
        Button b = new Button("This is a button !", false, true);
        b.setAlign(EnumAlign.center);
        b.setVerticalAlign(EnumAlign.center);
        b.setMargin(30, 30, 30, 30);
        
        box.addComponent(b);
        
        if (box instanceof UiComponent) {
            ((UiComponent) box).setVisible(true);
        }
    }
    
}
