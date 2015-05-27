/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package GraphicInterface.Input;

import GraphicInterface.GUIEvents.GUIEvent;
import GraphicInterface.GUIEvents.GUIEventListener;
import GraphicInterface.ViewComponents.InteractiveComponent;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;

public class MouseBehavior implements MouseEventProcessor, GUIEventListener {
    private final static int minQueLenght = 20;
    
    public final Object lock;
    private final ArrayDeque<Point> movements;
    
    private InteractiveComponent parent;
    private Point cp;
    private Point np;
    
    private volatile int button;
    private volatile int lclickCounter;
    private volatile int rclickCounter;

    public MouseBehavior() {
        this(null);
    }

    public MouseBehavior(InteractiveComponent parent) {
        this.lock = new Object();
        this.movements = new ArrayDeque(minQueLenght);
        this.parent = parent;
        this.cp = new Point();
        this.np = new Point();
        
        this.button = MouseEvent.NOBUTTON;
        this.lclickCounter = 0;
        this.rclickCounter = 0;
    }
    
    @Override
    public Object getLock() {
        return this.lock;
    }
    
    private boolean getIgnore(MouseEvent e) {
        Point p = e.getPoint();
        if (this.parent == null) {
            return true;
        }
        boolean ignore = !this.parent.getBox().contains(p);
        if (ignore) {
            reset();
        }
        return ignore;
        
    }
    
    @Override
    public void reset() {
        synchronized(this.lock) {
            this.cp.setLocation(0,0);
            this.np.setLocation(0,0);
        
            this.button = MouseEvent.NOBUTTON;
            this.lclickCounter = 0;
            this.rclickCounter = 0;
            
            this.movements.clear();
        }
    }
    
    @Override
    public void setParentComponent(InteractiveComponent component) {
        this.parent = component;
    }
    
    @Override
    public InteractiveComponent getParentComponent() {
        return this.parent;
    }

    @Override
    public boolean getLeftClick() {
        boolean click;
        synchronized(this.lock) {
            click = this.lclickCounter > 0;
            this.lclickCounter = 0;
            this.rclickCounter = 0;
        }
        return click;
    }
    
    @Override
    public boolean getRightClick() {
        boolean click;
        synchronized(this.lock) {
            click = this.rclickCounter > 0;
            this.lclickCounter = 0;
            this.rclickCounter = 0;
        }
        return click;
    }

    @Override
    public Point getShiftVector() {
        int x, y;
        x = 0;
        y = 0;
        
        synchronized (this.lock) {
            if(getShiftAction()) {
                Point a, b;
                a = this.movements.pollLast();
                while (!this.movements.isEmpty()) {
                    b = this.movements.pollLast();
                    x += (b.x - a.x); // Math.abs ? mayhebs
                    y += (b.y - a.y);
                    a = b;
                }
                this.movements.addFirst(a);
            }
        }
        return new Point(x, -y);
    }
    
    @Override
    public boolean getShiftAction() {
        boolean shifted;
        synchronized (this.lock) {
            // at least tvo points are required to make shift
            shifted = this.getRightMouse() && this.movements.size() >= 2;
        }
        return shifted;
    }

    @Override
    public boolean getSelectAction() {
        boolean selection;
        synchronized (this.lock) {
            selection = !(this.cp.equals(this.np)) && this.button == MouseEvent.BUTTON3;
        }
        return selection;
    }

    @Override
    public boolean getHoverAction() {
        if (this.parent == null) {
            return false;
        }
        return this.parent.getBox().contains(this.cp);
    }

    @Override
    public boolean getLeftMouse() {
        return this.button == MouseEvent.BUTTON1;
    }

    @Override
    public boolean getRightMouse() {
        return this.button == MouseEvent.BUTTON3;
    }

    @Override
    public Rectangle getSelectionRectangle() {
        int x, y, h, w;
        
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        
        synchronized (this.lock) {
            if (this.getLeftMouse()) {
                x = Math.min(this.cp.x, this.np.x);
                y = Math.min(this.cp.y, this.np.y);
                w = Math.max(this.cp.x, this.np.x) - x;
                h = Math.max(this.cp.y, this.np.y) - y;
            }
        }
        return new Rectangle(x, y, w, h);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        synchronized(this.lock) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                this.lclickCounter++;
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
                this.rclickCounter++;
            }            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        synchronized(this.lock) {
            this.cp.setLocation(e.getPoint());
            this.np.setLocation(e.getPoint());
            if (this.getLeftMouse() || this.getRightMouse()) {
                this.button = MouseEvent.NOBUTTON;
            }
            else {
                this.button = e.getButton();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        synchronized(this.lock) {
            this.button = MouseEvent.NOBUTTON;
            this.cp.setLocation(e.getPoint());
            this.np.setLocation(e.getPoint());
            this.movements.clear();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!this.getIgnore(e)) {
            synchronized(this.lock) {
                this.np.setLocation(e.getPoint());
                    
                if (this.getRightMouse()) {
                    this.movements.add(e.getPoint());
                }
            }
        }
        else {
            reset();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!this.getIgnore(e)) {
            synchronized(this.lock) {
                this.cp.setLocation(e.getPoint());
                this.np.setLocation(e.getPoint());
            }
        }
    }

    @Override
    public void handleGUIEvent(GUIEvent e) {
        switch(e.getType()) {
            case mouseClicked:
                this.mouseClicked(e.getMouseEvent());
            break;
            case mousePressed:
                this.mousePressed(e.getMouseEvent());
            break;
            case mouseReleased:
                this.mouseReleased(e.getMouseEvent());
            break;
            case mouseEntered:
                this.mouseEntered(e.getMouseEvent());
            break;
            case mouseExited:
                this.mouseExited(e.getMouseEvent());
            break;
            case mouseDragged:
                this.mouseDragged(e.getMouseEvent());
            break;
            case mouseMoved:
                this.mouseMoved(e.getMouseEvent());
            break;
            default:
            break;
        }
    }
}
