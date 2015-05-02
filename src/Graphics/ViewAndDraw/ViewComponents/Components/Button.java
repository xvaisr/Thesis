/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw.ViewComponents.Components;

import Graphics.Input.MouseEventProcessor;
import Graphics.ViewAndDraw.EnumAlign;
import Graphics.ViewAndDraw.ViewComponents.AbstractInteractiveComponent;
import Graphics.ViewAndDraw.ViewComponents.FakeContainer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public class Button extends AbstractInteractiveComponent {
    private static final String DEFAUT_LABLE = "Button";
    private static final Color DEFAULT_BACKGROUN_COLOR = Color.WHITE;
    private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final Color DEFAULT_LABLE_COLOR = Color.BLACK;
    private static final int DEFAULT_PADDING = 5;
    private static final int DEFAULT_BORDER = 2;
    
    private volatile boolean border;
    private volatile boolean use3D;
    private final Lable lable;
    private final FakeContainer container;
    
    private volatile Color background;
    private volatile Color borderColor;
    
    public Button() {
        this(DEFAUT_LABLE);
    }
    
    public Button(String lable) {
        this(lable, true, true);
    }

    public Button(String lable, boolean useBorder, boolean use3D) {
        this.container = new FakeContainer(this);
        this.border = useBorder;
        this.use3D = use3D;
        
        this.lable = new Lable(lable);
        this.lable.setParent(this.container);
        this.lable.setAlign(EnumAlign.center);
        this.lable.setVerticalAlign(EnumAlign.center);
        this.lable.setColor(DEFAULT_LABLE_COLOR);
        this.lable.setVisible(true);
        
        this.resizeByLable();
        
        this.background = DEFAULT_BACKGROUN_COLOR;
        this.borderColor = DEFAULT_BORDER_COLOR;
    }
    
    private void resizeByLable() {
        Rectangle r = this.lable.getBox();
        r.grow(DEFAULT_PADDING, DEFAULT_PADDING);
        this.setSize(r.getSize());
        this.lable.updateBox();
    }
    
    @SuppressWarnings("RedundantStringConstructorCall")
    public void setText(String text) {
        this.lable.setText(text);
        this.resizeByLable();
    }
    
    public void setFont(Font f) {
        this.lable.setFont(f);
        this.resizeByLable();
    }

    public void setBackgorundColor(Color c) {
        if (c == null) {
            c = DEFAULT_BACKGROUN_COLOR;
        }
        this.background = c;
    }
    
    public void setBorderColor(Color c) {
        if (c == null) {
            c = DEFAULT_BORDER_COLOR;
        }
        this.borderColor = c;
    }
    
    public void setTextColor(Color c) {
        this.lable.setColor(c);
    }
    
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.lable.updateBox();
    }
    
    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        this.lable.updateBox();
    }
    
    @Override
    public void setPosition(Point p) {
        super.setPosition(p);
        this.lable.updateBox();
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        this.lable.updateBox();
    }
    
    @Override
    public void setAlign(EnumAlign align) {
        super.setAlign(align);
        this.lable.updateBox();
    }
    
    @Override
    public void setVerticalAlign(EnumAlign align) {
        super.setVerticalAlign(align);
        this.lable.updateBox();
    }
    
    @Override
    public void setMargin(int top, int right, int bottom, int left) {
        super.setMargin(top, right, bottom, left);
        this.lable.updateBox();
    }
    
    @Override
    public void setHorizontalTrailing(boolean trail) {
        super.setHorizontalTrailing(trail);
        this.lable.updateBox();
    }
    
    @Override
    public void setVerticalTrailing(boolean trail) {
        super.setVerticalTrailing(trail);
        this.lable.updateBox();
    }
    
    @Override
    public void updateBox() {
        super.updateBox();
        this.lable.updateBox();
    }
    
    @Override
    public void paint(Graphics g) {
        Rectangle r = this.getBox();
        
        if (this.border) {
            r.grow(DEFAULT_BORDER, DEFAULT_BORDER);

            g.setColor(this.borderColor);
            g.fillRect(r.x, r.y, r.width, r.height);

            r = this.getBox();
        }
        
        Color c = this.background;
        MouseEventProcessor mouse =  this.getMouseEventProcessor();
        
        synchronized(mouse.getLock()) {
            if (mouse.getHoverAction()) {
                c = Color.cyan;
            }
        }
        
        g.setColor(c);
        g.fill3DRect(r.x, r.y, r.width, r.height, this.use3D);
        
        this.lable.paint(g);
    }

    
}
