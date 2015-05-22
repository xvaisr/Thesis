/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterface;

import Enviroment.Puppeteer;
import Enviroment.SearchForDraw;
import GraphicInterface.GUIEvents.GUIEvent;
import GraphicInterface.GUIEvents.GUIEventListener;
import GraphicInterface.InterfaceViews.MainMenuView;
import GraphicInterface.InterfaceViews.MapView;
import GraphicInterface.InterfaceViews.View;
import java.awt.Dimension;
/**
 *
 * @author lennylinux
 */
public class MenuInterfacce implements Runnable, GUIEventListener {
    private static final int H_LEFT = -1;
    private static final int H_RIGHT = 1;
    private static final int V_DOWN = -1;
    private static final int V_UP = 1;
    private static final int VH_NONE = 0;   
    private static final int STEP = 1;
    private static final int DELAY_MS = 16;
    private static final int ms2sec = 1000;
    
    private static MenuInterfacce self = new MenuInterfacce();
    
    private final View view; 
    private GameWindow window;
    private GameScreen screen;
    
    private int step;                   // pixels shifted per step
    private int delay;                  // how long to wait before next step 
    private boolean running;
    
    
    private MenuInterfacce() {
        this.view = new MainMenuView();
        this.window = new GameWindow();
        this.screen = new GameScreen(this.view);
        
        this.step = STEP;
        this.delay = DELAY_MS;
        this.running = false;
    }
    
    private void Init() {
        this.window.setScreen(this.screen);
        this.window.addGUIListener(this);
        this.screen.addGUIListener(this.view.getContainer());
        this.screen.addGUIListener(this);
        java.awt.EventQueue.invokeLater(this.window);
    }

    public static MenuInterfacce getInstance() {
        return MenuInterfacce.self;
    }
    
    public void setDelay(int ms) {
        synchronized(this) {
            if (ms < 0) {
                ms = 0;
            }
            this.delay = ms;
        }
    }
    
    public void setStep(int px) {
        synchronized(this) {
            if (px < 0) {
                px = 0;
            }
            this.step = px;
        }    
    }
    
    private void stop() {
        synchronized(this) {
            this.running = false;
        }
    }
    
    @Override
    public void run() {
        GUIEvent e;
        boolean empty = true;
        boolean moved = false;
        boolean run = true;
        int d = DELAY_MS;
        
        synchronized(this) {
            this.running = true;
        }
        
        this.Init();
        
        while (run) {
            
            synchronized(this) {
                d = this.delay;
            }
                
            try {
                Thread.sleep(d);
            }
            catch (InterruptedException ex) {}
            
            synchronized(this) {
                run = this.running;
            }
        }
    }

    @Override
    public void handleGUIEvent(GUIEvent e) {
        if (e.getType() == GUIEvent.type.componentResized) {
            Dimension d = e.getComponentEvent().getComponent().getSize();
            this.view.setWindowSize(d);
        }
    }
    
}
