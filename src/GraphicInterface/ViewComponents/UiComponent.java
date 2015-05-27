/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package GraphicInterface.ViewComponents;

import GraphicInterface.InterfaceViews.EnumAlign;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public interface UiComponent {

    public void setParent(Container parent);
    public Container getParent();
    
    public void setSize(int width, int height);
    public void setSize(Dimension d);
    public Dimension getSize();
    public int getWidth();
    public int getHeight();
    
    public void setPosition(Point p);
    public void setPosition(int x, int y);
    public Point getPosition();
    
    public Rectangle getBox();
    
    public void setAlign(EnumAlign align);
    public void setVerticalAlign(EnumAlign align);
    public void setMargin(int top, int right, int bottom, int left);
    
    public void setHorizontalTrailing(boolean trail);
    public boolean getHorizontalTrailing();
    public void setVerticalTrailing(boolean trail);
    public boolean getVerticalTrailing();
    
    public void setVisible(boolean visible);
    public boolean getVisible();
    
    public void updateBox();
    
    public void paint(Graphics g);

    
}
