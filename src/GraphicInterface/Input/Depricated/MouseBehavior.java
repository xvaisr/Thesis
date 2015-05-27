/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package GraphicInterface.Input.Depricated;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import javax.swing.event.MouseInputListener;

public class MouseBehavior implements MouseInputListener {

    private final static int minQueLenght = 20;
    
    private final ArrayDeque<Point> movements;
    private boolean leftButton, rightButton, middleButton, ignore;
    private final Point selectA, selectB;
    
    
    public MouseBehavior() {
        this.movements = new ArrayDeque(minQueLenght);
        this.leftButton = false;
        this.rightButton = false;
        this.middleButton = false;
        this.ignore = true;
        this.selectA = new Point();
        this.selectB = new Point();
    }  
    
    public Point getMouseShift() {
        synchronized(this) {
            if (!this.rightButton) {
                return new Point();
            }
        }
        
        int x, y;
        x = 0;
        y = 0;
        synchronized (this.movements) {
            
        }
        return new Point(-x, y);
    }
    
    public Rectangle getSelectRectangle() {
        Rectangle r = null;
        boolean left = false;
        synchronized(this) {
            left = this.leftButton;
        }
        
        int x, y, h, w;
        if (left) {
            synchronized (this.selectB) {
                x = Math.min(this.selectA.x, this.selectB.x);
                y = Math.min(this.selectA.y, this.selectB.y);
                w = Math.max(this.selectA.x, this.selectB.x) - x;
                h = Math.max(this.selectA.y, this.selectB.y) - y;
                r = new Rectangle(x, y, w, h);
            }
        }
        return r;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {        
        synchronized(this) {
            if (this.leftButton || this.middleButton || this.rightButton) {
                this.leftButton = false;
                this.middleButton = false;
                this.rightButton = false;
                return;
            }
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    this.leftButton = true;
                break;
                case MouseEvent.BUTTON2:
                    this.middleButton = true;
                break;
                case MouseEvent.BUTTON3:
                    this.rightButton = true;
                break;
            }
        }
        synchronized (this.movements) {
            this.movements.clear();
            this.movements.addFirst(e.getPoint());
        }
        synchronized (this.selectB) {
            this.selectA.setLocation(e.getPoint());
            this.selectB.setLocation(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        synchronized(this) {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    if (!this.leftButton) {
                    }
                    this.leftButton = false;
                break;
                case MouseEvent.BUTTON2:
                    this.middleButton = false;
                break;
                case MouseEvent.BUTTON3:
                    this.rightButton = false;
                break;
            }
            if (this.leftButton || this.middleButton || this.rightButton) {
                this.rightButton = false;
                this.middleButton = false;
                this.rightButton = false;
                return;
            }
        }
        synchronized (this.movements) {
            this.movements.addFirst(e.getPoint());
        }
        synchronized(this.selectB) {
            this.selectA.setLocation(this.selectB);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        synchronized(this) {
            this.ignore = false;
        }
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.ignore = true;
        
        synchronized(this) {
            this.leftButton = false;
            this.rightButton = false;
            this.middleButton = false;
        }
        
        synchronized(this.movements) {
            this.movements.addFirst(e.getPoint());
        }
        synchronized(this.selectB) {
            this.selectA.setLocation(this.selectB);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        synchronized(this.movements) {
            this.movements.addFirst(e.getPoint());
        }
        synchronized(this.selectB) {
            this.selectB.setLocation(e.getPoint());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
}
