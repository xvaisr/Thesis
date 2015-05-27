/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package GraphicInterface.ViewComponents.Components.InteractiveComponents;

import GraphicInterface.InterfaceViews.EnumAlign;
import GraphicInterface.InterfaceViews.View;
import GraphicInterface.ViewComponents.Components.EmptyContainer;
import GraphicInterface.ViewComponents.UiComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Menu extends EmptyContainer {
    
    private static final Color DEFAULT_BACKGROUN = Color.white;
    private static final Color DEFAULT_BUTTON_BACKGROUN_COLOR = Color.white;
    private static final Color DEFAULT_BUTTON_HOVER_COLOR = Color.lightGray;
    private static final Color DEFAULT_LABLE_COLOR = Color.black;
    private static final int DEFAULT_INDENT = 6;
    private static final int DEFAULT_TOP_PADDING = 150;
    
    private int currentWidth;
    private int currentHeight;
    private int buttonsCount;
    private int buttonIndent;
    private int topPadding;
    
    private Color background;
    private Color buttonBackground;
    private Color buttonHoverBackground;
    private Color buttonLable;
    
    public Menu() {
        super();
        this.initMenu();
    }
    
    public Menu(View view) {
        super(view);
        this.initMenu();
    }
    
    private void initMenu() {
        this.currentWidth = 0;
        this.currentHeight = 0;
        this.buttonsCount = 0;
        this.buttonIndent = DEFAULT_INDENT;
        this.topPadding = DEFAULT_TOP_PADDING;
    
        this.background = DEFAULT_BACKGROUN;
        this.buttonBackground = DEFAULT_BUTTON_BACKGROUN_COLOR;
        this.buttonHoverBackground = DEFAULT_BUTTON_HOVER_COLOR;
        this.buttonLable = DEFAULT_LABLE_COLOR;
        
        this.setAlign(EnumAlign.center);
        this.setVerticalAlign(EnumAlign.center);
        this.setHorizontalTrailing(true);
        this.setVerticalTrailing(true);
    }
    
    public void setBackgroud(Color bg) {
        if (bg == null) {
            bg = DEFAULT_BACKGROUN;
        }
        this.background = bg;
    }
    
    public void setButtonBackgroud(Color bg) {
        if (bg == null) {
            bg = DEFAULT_BUTTON_BACKGROUN_COLOR;
        }
        this.buttonBackground = bg;
    }
    
    public void setButtonHoverColor(Color c) {
        if (c == null) {
            c = DEFAULT_BUTTON_HOVER_COLOR;
        }
        this.buttonHoverBackground = c;
    }
    
    public void setButtonLableColor(Color c) {
        if (c == null) {
            c = DEFAULT_LABLE_COLOR;
        }
        this.buttonLable = c;
    }
    
    public void setButtonIndent(int indent) {
        if (indent < 0) {
            indent = DEFAULT_INDENT;
        }
        this.buttonIndent = indent;
        
    }
    
    public void setTopPadding(int padding) {
        if (padding < 0) {
            padding = DEFAULT_TOP_PADDING;
        }
        this.topPadding = padding;      
    }
    
    public void addMenuButton(String name) {
        if (name == null || name.isEmpty()) {
            return;
        }
        
        // set defaults
        Button b = new Button(name);
        b.setBackgorundColor(this.buttonBackground);
        b.setHoverColor(this.buttonHoverBackground);
        b.setTextColor(this.buttonLable);
        
        // set sizes
        int w = b.getWidth();
        int h = b.getHeight();
        
        if (w > this.currentWidth) {
            this.currentWidth = w;
            
            for (UiComponent comp : this.getComponents()) {
                if (comp instanceof Button) {
                    comp.setSize(this.currentWidth, comp.getHeight());
                }
            }
        }
        
        if (h > this.currentHeight) {
            this.currentHeight = h;
            
            for (UiComponent comp : this.getComponents()) {
                if (comp instanceof Button) {
                    comp.setSize(this.currentWidth, this.currentHeight);
                }
            }
        }
        
        b.setSize(this.currentWidth, this.currentHeight);
        
        // set aligment and trailing
        b.setAlign(EnumAlign.center);
        b.setVerticalAlign(EnumAlign.top);
        b.setHorizontalTrailing(false);
        b.setVerticalTrailing(false);
        
        // set indentation
        /* 
         * All components are taking position independently on siblings.
         *  Position is influenced only by containers borders
         */
        int heihgt = b.getBox().height;
        int topMargin = this.buttonsCount * (heihgt + this.buttonIndent);
        topMargin += this.topPadding;
        b.setMargin(topMargin, 0, 0, 0);
        
        // set current visibilyty
        b.setVisible(this.getVisible());
        
        // all set, add button
        this.addComponent(b);
        this.buttonsCount++;
    }
        
    @Override
    public void paint(Graphics g) {
        if (!this.getVisible()) {
            return;
        }
        
        // paint self
        Rectangle r  = this.getBox();
        
        g.setColor(this.background);
        g.fillRect(r.x, r.y, r.width, r.height);
        
        // paint content
        this.paintContent(g);
        
        
    }
    
    
}
