/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw.ViewComponents;

import Graphics.GUIEvents.GUIEvent;
import Graphics.ViewAndDraw.EnumAlign;
import Graphics.ViewAndDraw.ViewSpaceHolder;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author lennylinux
 */
public final class FakeContainer implements Container, ViewSpaceHolder {

    private final UiComponent linkedComponent;
    
    public FakeContainer(UiComponent component) {
        if (component == null) {
            throw new NullPointerException("This object must be linked to " 
                    + " UiComponent and componenet parametr must not be NULL!");
        }
        this.linkedComponent = component;
    }
    
    
    @Override
    public void paintContent(Graphics g) {
        this.linkedComponent.paint(g);
    }

    @Override
    public void paint(Graphics g) {
        this.linkedComponent.paint(g);
    }
    
    @Override
    public Rectangle getBox() {
        return this.linkedComponent.getBox();
    }

    @Override
    public boolean getHorizontalTrailing() {
        return this.linkedComponent.getHorizontalTrailing();
    }

    @Override
    public boolean getVerticalTrailing() {
        return this.linkedComponent.getVerticalTrailing();
    }

    @Override
    public Dimension getSize() {
        return this.linkedComponent.getSize();
    }

    @Override
    public int getWidth() {
        return this.linkedComponent.getWidth();
    }

    @Override
    public int getHeight() {
        return this.linkedComponent.getHeight();
    }

    @Override
    public void updateBox() {
        this.linkedComponent.updateBox();
    }

    @Override
    public boolean getVisible() {
        return this.linkedComponent.getVisible();
    }

    @Override
    public ViewSpaceHolder getViewSpaceHolder() {
        Container c = this.linkedComponent.getParent();
        if (c == null) {
            return this;
        }
        return c.getViewSpaceHolder();
    }

    @Override
    public Rectangle getAreaOfView() {
        return this.getBox();
    }

    @Override
    public Point getViewPosition() {
        return this.getBox().getLocation();
    }
    
    @Override
    public void addComponent(UiComponent comp) {}

    @Override
    public void removeComponent(UiComponent comp) {}

    @Override
    public void removeAllComponents() {}

    @Override
    public void setParent(Container parent) {}

    @Override
    public void setSize(int width, int height) {}

    @Override
    public void setSize(Dimension d) {}

    @Override
    public void setPosition(Point p) {}

    @Override
    public void setPosition(int x, int y) {}

    @Override
    public void setHorizontalTrailing(boolean trail) {}

    @Override
    public void setVerticalTrailing(boolean trail) {}
    
    @Override
    public void setAlign(EnumAlign align) {}

    @Override
    public void setMargin(int top, int right, int bottom, int left) {}

    @Override
    public void setVisible(boolean visible) {}

    @Override
    public void aovChanged() {}

    @Override
    public void setVerticalAlign(EnumAlign align) {}

    @Override
    public void handleGUIEvent(GUIEvent e) {}

    @Override
    public Container getParent() {
        throw new UnsupportedOperationException("Fake container does not support your action!");
    }

    @Override
    public Point getPosition() {
        throw new UnsupportedOperationException("Fake container does not support your action!");
    }

    @Override
    public boolean shiftWindow(int horizontaly, int verticaly) {
        return false;
    }

    
    
}
