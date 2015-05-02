/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics;

import Graphics.GUIEvents.GUIEvent;
import Graphics.GUIEvents.GUIEventCaster;
import Graphics.GUIEvents.GUIEventListener;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author lennylinux
 */
public class GameWindow extends JFrame
                        implements Runnable, WindowListener, GUIEventCaster                
{
    private static final String TITLE = "Anthill RTS game";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 800;
    private GameScreen screen;
    
    // Gui listener
    private final ArrayList<GUIEventListener> GUIListeners;
    

    public GameWindow() {
        super(TITLE);
        this.GUIListeners = new ArrayList();
    }
    
    public void setScreen(GameScreen screen) {
        if (screen == null) {
            throw new NullPointerException("Screen parametr must not be NULL!");
        }
        this.screen = screen;
        this.screen.setWindow(this);
        this.init();
    }    
    private void init() {
        this.addWindowListener(this);
        Dimension size = new Dimension(MIN_WIDTH, MIN_HEIGHT);
        
        this.screen.setMinimumSize(size);
        this.screen.setPreferredSize(size);
        this.add(this.screen);
        
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        pack();
        
    }

    @Override
    public void run() {
        if (this.screen == null) {
            return;
        }
        this.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        this.screen.start();
        this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.windowOpened, e));
    }

    @Override
    public void windowClosing(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

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
            l.handleGUIEvent(event);
        }
    }

    
    
}
