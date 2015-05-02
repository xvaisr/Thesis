/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw;

import Enviroment.EnvObjects.GameObject;
import Enviroment.EnviromentalMap.MapInterface;
import Enviroment.Model;
import Graphics.Input.GameObjectCasher;
import Graphics.ViewAndDraw.View;
import Graphics.Input.MouseBehavior;
import Graphics.ViewAndDraw.ViewComponents.Components.MapComponent;
import Graphics.ViewAndDraw.ViewComponents.Container;
import Graphics.ViewAndDraw.ViewComponents.UiComponent;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class MapView extends View implements GameObjectCasher {
    private final Rectangle selection;
    private final MouseBehavior mouseBehavior;

    public MapView() {
        super();
        this.selection = new Rectangle();
        this.mouseBehavior = new MouseBehavior();
        
        UiComponent map = new MapComponent(this);
        Container c = this.getContainer();
        c.addComponent(map);
        c.setVisible(true);
    }
    
    public MapView(int width, int height) {
        this(new Point(), width, height);
    }
    
    public MapView(Point p, int width, int height) {
        super(p, width, height);
        this.selection = new Rectangle();
        this.mouseBehavior = new MouseBehavior();
        
        UiComponent map = new MapComponent(this);
        Container c = this.getContainer();
        c.addComponent(map);
        c.setVisible(true);
    }
    
    public void setSelection (Rectangle r) {
        
        
        synchronized(this.selection) {
            if (r != null) {
                this.selection.setLocation(r.getLocation());
                this.selection.setSize(r.getSize());
            }
            else {
                this.selection.setLocation(0, 0);
                this.selection.setSize(0, 0);
            }
        }
    }
    
    @Override
    public ArrayList<GameObject> getVisibleObjects() {
        MapInterface map = Model.getCurrentMap();
        if (map == null) {
            return new ArrayList();
        }
        return map.getGameObjectsInArea(this.getAreaOfView());
    }
    /*
    
    @Override
    public void paintContent(Graphics g) {
        Rectangle tempAov;
        tempAov = this.getAreaOfView();
        this.drawBackground(g, tempAov);
        ArrayList<GameObject> visibleList = ModelBackup.getObjInArea(tempAov);
        for (GameObjectBackup obj : visibleList)
            this.paintObject(g, obj, tempAov);
        
        Rectangle select;
        synchronized(this.selection) {
            select = new Rectangle(this.selection);
        }
        this.drawSelection(g, select, tempAov);
    }
    
    
    private void paintObject(Graphics g, GameObjectBackup obj, Rectangle aov) {
        switch (obj.getType()) {
            case agent:
                this.drawAgent(g, obj, aov);
            break;
            case obsticle:
                this.drawObsicle(g, obj, aov);
            break;
            case resource:
                this.drawResource(g, obj, aov);
            break;
            case resourceBlock:
                this.drawResourceBlock(g, obj, aov);
            break;
            case feromonPath:
                this.drawPath(g, obj, aov);
            break;
            default:
                throw new AssertionError(obj.getType().name());
        }
    }
    
    private void drawAgent(Graphics g, GameObjectBackup obj, Rectangle aov) {
        int cy, ny;
        Color c;
        Point p;
        
        p = obj.getPosition();
        p.translate(-aov.x, -aov.y); // shift point to window from original place
        
        cy = p.y;
        ny = aov.height - cy;
        p.translate(0, ny); // switch y-axis direction
        
        if (obj.getClass() == Agent.class) {
            Agent ag = (Agent) obj;
            c = ag.getTeam().getColor();
        }
        else return;
        
        g.setColor(c);
        g.fillOval((p.x - 3), (p.y - 3), 7, 7);
        
        g.setColor(Color.BLACK);
        g.drawOval((p.x - 3), (p.y - 3), 7, 7);
    }        
    
    private void drawObsicle(Graphics g, GameObjectBackup obj, Rectangle aov) {
        ArrayList<Point> verticies;
        Polygon p;
        int cy, ny;
        
        p = new Polygon();
        
        verticies = obj.getVertices();
        for (Point v : verticies) {
            v.translate(-aov.x, -aov.y); // shift point to window from original place
            
            cy = v.y;
            ny = (aov.height - cy);
            v.translate(0, (ny - cy)); // switch y-axis direction
            
            p.addPoint(v.x, v.y);
        }
        
        g.setColor(new Color(85, 51, 17));
        g.fillPolygon(p);
        
        g.setColor(Color.BLACK);
        g.drawPolygon(p);
    }
    
    private void drawResource(Graphics g, GameObjectBackup obj, Rectangle aov) {
        int ny;
        Resource res;
        
        Rectangle r = obj.getRectangle();
        r.translate(-aov.x, -aov.y); // shift rectangle to window
        
        ny = aov.height - r.y - r.height;       
        r.translate(0, (ny - r.y)); // shif rectangle to it's correct position
        
        if (obj.getClass() == Resource.class) {
            res = (Resource) obj;
        }
        else return;
        
        switch(res.getResourceType()) {
            case food:
                g.setColor(Color.ORANGE.darker());
            break;    
            case water:
                g.setColor(Color.BLUE.brighter().brighter());
            break;
            default:
                g.setColor(Color.WHITE);
            break;
        }
        
        g.fillRect(r.x, r.y, r.width, r.height);
        if (g.getColor() == Color.WHITE) {
            g.setColor(Color.BLACK);
            g.drawRect(r.x, r.y, r.width, r.height);
        }
        
    }
    
    private void drawResourceBlock(Graphics g, GameObjectBackup obj, Rectangle aov) {
        int ny;
        ResourceBlock res;
        
        Rectangle r = obj.getRectangle();
        r.translate(-aov.x, -aov.y); // shift rectangle to window
        
        ny = aov.height - r.y - r.height;       
        r.translate(0, (ny - r.y)); // shif rectangle to it's correct position
        
        if (obj.getClass() == ResourceBlock.class) {
            res = (ResourceBlock) obj;
        }
        else return;null
        
        switch(res.getResourceType()) {
            case food:
                g.setColor(Color.ORANGE.darker());
            break;    
            case water:
                g.setColor(Color.BLUE.brighter().brighter());
            break;
            default:
                g.setColor(Color.WHITE);
            break;
        }
        
        g.fillRect(r.x, r.y, r.width, r.height);
        
        g.setColor(Color.BLACK);
        g.drawRect(r.x, r.y, r.width, r.height);
    }
    
    private void drawPath(Graphics g, GameObjectBackup obj, Rectangle aov) {
    }
    
    private void drawBackground(Graphics g, Rectangle aov) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, aov.width, aov.height);
    }
    
    private void drawSelection(Graphics g, Rectangle selection, Rectangle aov) {
        g.setColor(new Color(0, 255, 0, 200));
        g.fillRect(selection.x, selection.y, selection.width, selection.height);
        
        g.setColor(Color.GREEN);
        g.drawRect(selection.x, selection.y, selection.width, selection.height);
    }

    */
    
    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
