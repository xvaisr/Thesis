/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics;

import Graphics.ViewAndDraw.MapView;
import Graphics.GUIEvents.GUIEvent;
import Graphics.GUIEvents.GUIEventCaster;
import Graphics.GUIEvents.GUIEventListener;
import Graphics.ViewAndDraw.View;
import Thesis.Main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;



/**
 *
 * @author lennylinux
 */
public class GameScreen extends Canvas implements MouseListener, 
                        MouseMotionListener, ComponentListener, GUIEventCaster,
                        KeyListener, Runnable
{   // constants
    private static final int bufferSize = 4;
    private static final int framerate = 60;
    private static final int sec2ms = 1000;
    private static final int sleepTimeMs = sec2ms / framerate;
    
    private static volatile long lastFrame = 0;
    
    // Gui listener
    private final ArrayList<GUIEventListener> GUIListeners;
    
    // drawing and windoew
    private GameWindow window;
    private View view;    
    private Thread thread;

    // curent state
    private volatile boolean run;
    private boolean inProgress;
    private volatile int frames;
    private volatile int fps;
    
    
    public GameScreen() {
        this(null);
    }
    
    public GameScreen(View v) {
        super();
        this.setIgnoreRepaint(true);
        this.GUIListeners = new ArrayList();
        this.thread = new Thread(this, "repaint");
        this.view = v;
        this.window = null;
        
        this.inProgress = false;
        this.run = false;
        
        this.frames = 0;
        this.fps = 0;
        
    }
    
    public void setNewView(MapView v) {
        synchronized(this) {
            this.view = v;
        }
    }
    
    public void setWindow(GameWindow w) {
        if (w == null) {
            throw new NullPointerException("Window parametr must not be NULL!");
        }
        this.window = w;
    }
    
    public void init() {
        this.run = true;
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);
        this.createBufferStrategy(bufferSize);
    }
    
    public int getFPS() {
        return this.fps;
    }
    
    public static int getDelta() {
        long currentTime = System.currentTimeMillis();
        return (int) (currentTime - lastFrame);
    }
    
    public void render () {
        boolean progress;
        synchronized(this) {
            progress = this.inProgress;
        }
        if (progress || this.view == null) {
            return;
        }
        Graphics g = null;
        BufferStrategy buffer = this.getBufferStrategy();
        try {
            synchronized(this) {
               this.inProgress = true;
            }
            g = buffer.getDrawGraphics();
            this.view.paintContent(g);
        }
        finally {
            this.frames++;
            if (g != null) {
                g.dispose();
            }
            buffer.show();
            Toolkit.getDefaultToolkit().sync(); 
            synchronized(this) {
               this.inProgress = false;
            } 
        }
        
    }
    
    void start() {
        if (this.window == null) {
            return;
        }
        this.init();
        this.requestFocus();
        this.thread.start();
    }
    
    private void stop() {
        synchronized(this) {
            this.run = false;
        }
    }

    @Override
    public void run() {
        long stFrame, now, delta;
        boolean running = true;
        
        if (!run) {
            return;
        }
        now = System.currentTimeMillis();
        stFrame = now;
        
        int avgrender, avgsleep;
        
        avgrender = 0;
        avgsleep = 0;
        
        while (running) {
            
            lastFrame = now;
            this.render();
            now = System.currentTimeMillis();
            
            delta = now - lastFrame;
            avgrender += delta;
            if (delta < sleepTimeMs) {
                try {
                    avgsleep += sleepTimeMs - delta;
                    Thread.sleep(sleepTimeMs - delta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            if (System.currentTimeMillis() - stFrame >= sec2ms) {
                stFrame += sec2ms;
                this.fps = this.frames;
                avgrender = (avgrender/frames);
                avgsleep = (avgsleep/frames);
                Main.debug(Main.INFO, frames + " frames, " + avgrender + " ms avg render, " + avgsleep + " ms avg sleep" );
                frames = 0;
            }
            
            synchronized(this) {
                running = this.run;
            }
        }
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.componentResized, e));
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        // this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.componentMoved, e));
    }

    @Override
    public void componentShown(ComponentEvent e) {
        // this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.componentShown, e));
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.componentHidden, e));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.keyTyped, e));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.keyPressed, e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.keyReleased, e));
    }        

    @Override
    public void mouseClicked(MouseEvent e) {
        this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.mouseClicked, e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.mousePressed, e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.mouseReleased, e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.mouseEntered, e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.mouseExited, e));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.mouseDragged, e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.fireGUIEvent(new GUIEvent(this, GUIEvent.type.mouseMoved, e));
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
            l.handleGUIEvent(event);
        }
    }
}
