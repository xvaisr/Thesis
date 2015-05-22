/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface.InterfaceViews;

import GraphicInterface.ViewComponents.Components.InteractiveComponents.Button;
import GraphicInterface.ViewComponents.Components.DrawRectangle;
import GraphicInterface.ViewComponents.Container;
import GraphicInterface.ViewComponents.UiComponent;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
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
