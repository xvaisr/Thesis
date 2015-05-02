/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw.ViewComponents;

import Graphics.ViewAndDraw.EnumAlign;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public class Matroska {
    private static final int DEFAULT_MARGIN = 0;
        
    private volatile int t, l, b, r;         // margin;
    private volatile EnumAlign align;
    private volatile EnumAlign valign;
    private volatile boolean htrailing;
    private volatile boolean vtrailing;
        
    private final MatroskaContainer linkedComponent;
    private final Rectangle originalBox;
    private final Rectangle marginedBox;
    

    public Matroska(MatroskaContainer c) {
        this(c, DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN);
    }
        
    public Matroska(MatroskaContainer c, int t, int r, int b, int l) {
        if (c == null) {
            throw new NullPointerException("Matroska must be linked with UiComponent!"
                                    + " UiComponent must not be NULL!");
        }
        
        this.t = t;
        this.l = l;
        this.r = r;
        this.b = b;
            
        this.linkedComponent = c;
        this.marginedBox = new Rectangle();
        this.originalBox = new Rectangle();
            
        this.align = EnumAlign.none;
        this.valign = EnumAlign.none;
        this.vtrailing = false;
        this.htrailing = false;
    }
        
    private void recompute() {
        int x, y, w, h;
        Rectangle container;
            
        Container c = this.linkedComponent.getParent();
        if (c == null) {
            if (!this.marginedBox.equals(this.originalBox)) {
                this.marginedBox.setBounds(this.originalBox);
            }
            return;
        }
            
        container = c.getBox();
            
        synchronized(this.originalBox) {
            x = container.x + this.originalBox.x;
            y = container.y + this.originalBox.y;
            w = this.originalBox.width;
            h = this.originalBox.height;

            switch (this.align) {
                case right :
                    x +=  container.width - this.originalBox.width - r;
                break;
                case center:
                    x += (container.width -this.originalBox.width) / 2;
                break;
                case left :
                case none:
                    x += this.l;
                default :
                break;
            }

            switch (this.valign) {
                case botom :
                    y += container.height - this.originalBox.height - b;
                break;
                case center:
                    y += (container.height - this.originalBox.height) / 2;
                break;
                case top :
                case none:
                default :
                    y += this.t;
                break;
            }
        }
        
        int dx = 0;
        int dy = 0;
        
        if (this.htrailing) {
            w = container.width - this.l - this.r;
            if (w < 0) w = 0;                
            dx = (container.x + this.l) - x;
            x = (container.x + this.l);
        }
        
        if (this.vtrailing) {
            h = container.height - this.t - this.b;
            if (h < 0) h = 0;
            dy = (container.y + this.t) - y;
            y = (container.y + this.t);
        }
        
        if (this.vtrailing || this.htrailing)  {
            synchronized (this.originalBox) {
                this.originalBox.translate(dx, dy);
            }
        }
            
        synchronized (this.marginedBox) {
            this.marginedBox.setSize(w, h);
            this.marginedBox.setLocation(x, y);
        }
    }
        
    public void setMargin(int t, int r, int b, int l) {
        synchronized (this) {
            this.t = t;
            this.l = l;
            this.r = r;
            this.b = b;
                
            this.recompute();
        }            
    }
        
    public void setOriginalBox(Rectangle box) {
        if (box == null) {
            return;
        }
        this.originalBox.setBounds(box);

        this.recompute();
    }
        
    public void setOriginalBox(int x, int y, int width, int height) {
        this.originalBox.setSize(width, height);
        this.originalBox.setLocation(x, y);
            
        this.recompute();
    }
        
    public void setOriginalBoxPosition(int x, int y) {
        this.originalBox.setLocation(x, y);
        this.recompute();
    }
        
    public void setOriginalBoxPosition(Point p) {
        this.originalBox.setLocation(p);
        this.recompute();
    }
        
    public void setOriginalBoxSize(int width, int height) {
        this.originalBox.setSize(width, height);
        this.recompute();
    }
        
    public void setOriginalBoxSize(Dimension d) {
        if (d == null) {
            return;
        }
        this.originalBox.setSize(d);
        this.recompute();
    }
        
    public void setVerticalTrailing(boolean trail) {
        this.vtrailing = trail;
        this.recompute();
    }
        
    public boolean getVerticalTrailing() {
        return this.vtrailing;
    }
    
    public void setHorizontalTrailing(boolean trail) {
        this.htrailing = trail;
        this.recompute();
    }
        
    public boolean getHorizontalTrailing() {
        return this.htrailing;
    }
        
    public void setAlign(EnumAlign align) {
        if (align == null) {
            return;
        }
        this.align = align;
        this.recompute();
    }
    
    public void setVerticalAlign(EnumAlign align) {
        if (align == null) {
            return;
        }
        this.valign = align;
        this.recompute();
    }
        
    public Rectangle getBox() {
        Rectangle box;
        synchronized (this.marginedBox) {
            box = new Rectangle(this.marginedBox);
        }
        return box;
    }

    void update() {
        this.recompute();
    }
        
}
