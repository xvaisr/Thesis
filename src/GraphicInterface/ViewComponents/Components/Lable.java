/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface.ViewComponents.Components;

import GraphicInterface.ViewComponents.AbstractComponent;
import Thesis.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
/**
 *
 * @author lennylinux
 */
public class Lable extends AbstractComponent {
    
    private static final int defaultFontSize = 18;
    private static final String defaultFontName = "Arial";
    private static final int defaultFontStyle = Font.BOLD;
    private static final Color defaultColor = Color.BLACK;
    private static final AffineTransform identity = new AffineTransform();
    
    private static final String defaultText = "Lable";
    private static final int defaultPadding = 5;
        
    private volatile String text;
    private volatile Font font;
    private volatile Color color;

    public Lable() {
        this(defaultText, new Font(defaultFontName, defaultFontStyle , defaultFontSize));
        
    }

    public Lable(String text) {
        this(text, defaultFontSize, defaultFontStyle);
    }
    
    public Lable(String text, int fontSize) {
        this(text, fontSize, defaultFontStyle);
    }
    
    public Lable(String text, int fontSize, int fontStyle) {
        this(text, defaultFontName, fontSize, fontStyle);
    }
    
    public Lable(String text, String fontName, int fontSize, int fontStyle) {
        this(text, new Font(fontName, fontStyle, fontSize));
    }

    @SuppressWarnings("RedundantStringConstructorCall")
    public Lable(String text, Font f) {
        if (f == null) {
            throw new NullPointerException("No font specified! Font parametr must not be NULL!");
        }
        this.setHorizontalTrailing(false);
        this.setVerticalTrailing(false);
        
        this.text = new String(text);
        this.font = f;
        this.color = defaultColor;
        
        this.setSizeByText();
        this.setMargin(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
    }  
    
    @SuppressWarnings("RedundantStringConstructorCall")
    public void setText(String text) {
        this.text = new String(text);
        this.setSizeByText();
    }
    
    private void setSizeByText() {
        Rectangle box = new Rectangle();
        FontRenderContext c = new FontRenderContext(identity, true, false);
        box.setFrame(this.font.getStringBounds(this.text, c));
        
        this.setSize(box.getSize());
    }
    
    public void setFont(Font f) {
        if (f == null) {
            throw new NullPointerException("No font specified! Font parametr must not be NULL!");
        }
        this.font = f;
        this.setSizeByText();
    }

    public void setColor(Color c) {
        if (c == null) {
            c = defaultColor;
        }
        this.color = c;
    }
    
    @Override
    public void paint(Graphics g) {
        if (this.getVisible()) {
            Point position = this.getBox().getLocation();
            g.setColor(this.color);
            g.setFont(this.font);
            g.drawString(text, position.x, (position.y + this.getHeight()));
            
            /* just for debug purposes
            if (Main.debug) {
                Rectangle r = this.getBox();
                g.setColor(Color.GREEN);
                
                g.drawRect(r.x, r.y, r.width, r.height);
            }
            */
        }
    }

    
}
