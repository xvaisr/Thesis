/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw.ViewComponents;

import Graphics.GUIEvents.GUIEvent;
import Graphics.GUIEvents.GUIEventListener;
import Graphics.ViewAndDraw.EnumAlign;
import Graphics.ViewAndDraw.View;
import Graphics.ViewAndDraw.ViewSpaceHolder;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public abstract class AbstractContainer extends AbstractComponent implements Container {

    private static final int DEFAULT_WIDTH = 0;
    private static final int DEFAULT_HEIGHT = 0;
    private static final int CAPACITY = 5;
    
    private volatile Container parent;
    
    private final ArrayList<UiComponent> components;
    private volatile View view;
    
    public AbstractContainer() {
        this(null);
    }
    
    public AbstractContainer(View view) {
        super(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        if (view != null) {
            super.setSize(view.getAreaOfView().getSize());
        }
        
        this.view = view;
        this.parent = null;
        this.components = new ArrayList(CAPACITY);
        super.setHorizontalTrailing(true);
        super.setVerticalTrailing(true);
    }
    
    public final void setNewView(View view) {
        if (view == null) {
            return;
        }
        
        this.view = view;
        this.aovChanged();
    }
    
    /*
      public void setParent(Container parent);
      implements superclass AbstractComponent
     */
    
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.updateContent();
    }
    
    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        this.updateContent();
    }
    /*
      public Dimension getSize();
      public int getWidth();
      public int getHeight();
      implements superclass AbstractComponent
    */
    
    @Override
    public void setPosition(Point p) {
        super.setPosition(p);
        this.updateContent();
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        this.updateContent();
    }
    
    /*
      public Rectangle getBox();
      implements superclass AbstractComponent
    */
    
    @Override
    public void setAlign(EnumAlign align) {
        super.setAlign(align);
        this.updateContent();
    }
    
    @Override
    public void setVerticalAlign(EnumAlign align) {
        super.setVerticalAlign(align);
        this.updateContent();
    }
    
    @Override
    public void setMargin(int top, int right, int bottom, int left) {
        super.setMargin(top, right, bottom, left);
        this.updateContent();
    }
    
    @Override
    public void setHorizontalTrailing(boolean trail) {
        super.setHorizontalTrailing(trail);
        this.updateContent();
    }
    
    
    
    @Override
    public void setVerticalTrailing(boolean trail) {
        super.setVerticalTrailing(trail);
        this.updateContent();
    }
    
    /*
      public boolean getHorizontalTrailing();
      public boolean getVerticalTrailing();
      implements superclass AbstractComponent
    */
    
    @Override
    public final void setVisible(boolean visible) {
        super.setVisible(visible);

        synchronized (this.components) {
            for (UiComponent comp : this.components) {
                comp.setVisible(visible);
            }
        }
    }

    /*
      public boolean getVisible();
      implements superclass AbstractComponent
    */
    
    @Override
    public void updateBox() {
        super.updateBox();
        this.updateContent();
    }
    
    private void updateContent() {
        synchronized (this.components) {
            for (UiComponent comp : this.components) {
                comp.updateBox();
            }
        }
    }
    
    @Override
    public final void addComponent(UiComponent comp) {
        synchronized (this.components) {
            this.components.add(comp);
            comp.setParent(this);
        }
    }

    @Override
    public final void removeComponent(UiComponent comp) {
        synchronized (this.components) {
            this.components.remove(comp);
            comp.setParent(null);
        }
    }
    
    @Override
    public final void removeAllComponents() {
        synchronized (this.components) {
            this.components.clear();
        }
    }
    
    @Override
    public void paintContent(Graphics g) {
        if (!this.getVisible()) {
            return;
        }
        synchronized (this.components) {
            for (UiComponent comp : this.components) {
                comp.paint(g);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (!this.getVisible()) {
            return;
        }
        this.paintContent(g);
    }
    
    @Override
    public void aovChanged() {
        ViewSpaceHolder spaceholder = this.getViewSpaceHolder();
        if (spaceholder == null) {
            return;
        }
        
        this.setSize(spaceholder.getAreaOfView().getSize());
    }
    
    @Override
    public final ViewSpaceHolder getViewSpaceHolder() {
        if (this.view != null) {
            return this.view;
        }
        else if (this.parent != null) {
            return this.parent.getViewSpaceHolder();
        }
        return null;
    }
    
    @Override
    public void handleGUIEvent(GUIEvent e) {
        synchronized (this.components) {
            for (UiComponent comp : this.components) {
                if (comp instanceof Container) {
                    ((Container) comp).handleGUIEvent(e);
                }
                else if (comp instanceof InteractiveComponent) {
                    ((InteractiveComponent) comp).getMouseEventProcessor().handleGUIEvent(e);
                }
            }
        }
    }
    
}
