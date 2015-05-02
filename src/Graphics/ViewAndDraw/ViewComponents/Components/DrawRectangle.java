/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw.ViewComponents.Components;

import Graphics.ViewAndDraw.EnumAlign;
import Graphics.ViewAndDraw.ViewComponents.AbstractComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public class DrawRectangle extends AbstractComponent{

    private final Color c;

    public DrawRectangle(Color color, int width, int height) {
        super(width, height);
        this.c = color;
        this.setAlign(EnumAlign.center);
    }
    

    public DrawRectangle(Color color, Dimension d) {
        super(d);
        this.c = color;
        this.setAlign(EnumAlign.center);
        this.setHorizontalTrailing(false);
        this.setVerticalTrailing(false);
    }
    
    @Override
    public void paint(Graphics g) {
        if (this.getVisible()) {
            Rectangle r = this.getBox();
            g.setColor(this.c);
            g.fillRect(r.x, r.y, r.width, r.height);
        }
    }
    
}
