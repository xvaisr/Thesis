/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics;

import Enviroment.Model;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.text.StyleConstants;

/**
 *
 * @author lennylinux
 */
public class GameWindow extends JFrame implements KeyListener, ActionListener{
    private static final String TITLE = "Anthill RTS game";
    private static final int MIN_HEIGHT = 1000;
    private static final int MIN_WIDTH = 1000;
    private int lastFrame;         // how long it took to paint last frame

    public GameWindow() {
        super(TITLE);
        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setLocation(0, 0);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setVisible(false);
        this.lastFrame = 0;
    }

    public int getLastFramePaintTime() {
        int lf;
        synchronized(this) {
            lf = this.lastFrame;
        }
        return lf;
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
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void repaint() {
        long start = System.currentTimeMillis();
        super.repaint();
        int frame = new Long (System.currentTimeMillis() - start).intValue() ;
        synchronized (this) {
            this.lastFrame = frame;
        }
    }
    
    @Override
    public void repaint(long tm) {
        long start = System.currentTimeMillis();
        super.repaint(tm);
        int frame = new Long (System.currentTimeMillis() - start).intValue() ;
        synchronized (this) {
            this.lastFrame = frame;
        }
    }
    
    @Override
    public void repaint(int x, int y, int width, int height) {
        long start = System.currentTimeMillis();
        super.repaint(x, y, width, height);
        int frame = new Long (System.currentTimeMillis() - start).intValue() ;
        synchronized (this) {
            this.lastFrame = frame;
        }
    }
    
    @Override
    public void repaint(long tm, int x, int y, int width, int height) {
        long start = System.currentTimeMillis();
        super.repaint(tm, x, y, width, height);
        int frame = new Long (System.currentTimeMillis() - start).intValue() ;
        synchronized (this) {
            this.lastFrame = frame;
        }
    }
            
}
