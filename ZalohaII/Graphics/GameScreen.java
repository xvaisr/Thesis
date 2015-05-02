/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics;

import Enviroment.EnvObjects.GameObject;
import Enviroment.Model;
import Graphics.GUIEvents.GUIEvent;
import Graphics.GUIEvents.GUIEventCaster;
import Graphics.GUIEvents.GUIEventListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author lennylinux
 */
public class GameScreen extends JPanel implements MouseListener, 
                        MouseMotionListener, ComponentListener, GUIEventCaster,
                        KeyListener
{
    private final ArrayList<GUIEventListener> GUIListeners;
    
    public GameScreen() {
        this.GUIListeners = new ArrayList();
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        ArrayList<GameObject> visibleList = Model.getObjInArea()
        for (GameObject obj : visibleList) {
            this.drawGameObject(g, obj);
        }
        
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
    public void componentResized(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addGUIListener(GUIEventListener listener) {
        if (listener != null)                
            this.GUIListeners.add(listener);
    }

    @Override
    public void removeGUIListener(GUIEventListener listener) {
        this.GUIListeners.remove(listener);
    }

    @Override
    public void removeAllGUIListeners() {
        this.GUIListeners.clear();
    }

    @Override
    public void fireGUIEvent(GUIEvent event) {
        for(GUIEventListener l : this.GUIListeners) {
            l.processGUIEvent(event);
        }
    }

    private void drawGameObject(Graphics g, GameObject obj) {
        
    }
    
    
}
