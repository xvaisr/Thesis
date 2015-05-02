/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw.ViewComponents.Components;

import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjects.GameObject;
import Graphics.Input.GameObjectCasher;
import Graphics.Input.MouseEventProcessor;
import Graphics.ViewAndDraw.EnumAlign;
import Graphics.ViewAndDraw.ViewSpaceHolder;
import Graphics.ViewAndDraw.ViewComponents.AbstractInteractiveComponent;
import Thesis.Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class MapComponentDebug extends AbstractInteractiveComponent {

    private GameObjectCasher casher;
    
    public MapComponentDebug(GameObjectCasher casher) {
        super();
        this.casher = casher;
        this.setAlign(EnumAlign.center);
        this.setVerticalAlign(EnumAlign.center);
        
        this.setHorizontalTrailing(true);
        this.setVerticalTrailing(true);
    }
    
    public MapComponentDebug(Dimension d) {
        this(null, d);
    }
    
    public MapComponentDebug(int width, int height) {
        this(null, width, height);
    }
    
    public MapComponentDebug(GameObjectCasher casher, Dimension d) {
        super(d);
        this.casher = casher;
        this.setAlign(EnumAlign.center);
        this.setVerticalAlign(EnumAlign.center);
        
        this.setHorizontalTrailing(true);
        this.setVerticalTrailing(true);
    }
    public MapComponentDebug(GameObjectCasher casher, int width, int height) {
        super(width, height);
        this.casher = casher;
        this.setAlign(EnumAlign.center);
        this.setVerticalAlign(EnumAlign.center);
        
        this.setHorizontalTrailing(true);
        this.setVerticalTrailing(true);
    }
    
    public void setNewGameObjectCasher(GameObjectCasher casher) {
        if (casher != null)
            this.casher = casher;
    }
    

    @Override
    public void paint(Graphics g) {
        if (!this.getVisible() || this.casher == null) {
            return;
        }
        
        Rectangle aov, box;
        ViewSpaceHolder vbox = this.getParent().getViewSpaceHolder();
        
        MouseEventProcessor mouse;
        mouse = this.getMouseEventProcessor();
                
        aov = vbox.getAreaOfView();
        box = this.getBox();
        
        Point p = this.getPosition();
        aov.translate((p.x - 0), (p.y - 0));
        aov.setSize(box.getSize());
        g.setClip(p.x, p.y, box.width, box.height);
        
        this.drawBackground(g);
        ArrayList<GameObject> visibleList = casher.getVisibleObjects();
        for (GameObject obj : visibleList) {
            try {
                if (obj instanceof DrawableGameObject) {
                    ((DrawableGameObject) obj).getPainter().paint(g, (DrawableGameObject) obj, aov);
                }
            }
            catch(Exception ex) {
                Main.debug(Main.WARNING, "Attempt to paint nonpaintable object." 
                           + "Condition is not sufficient !");
            }
        }
        
        drawSelectionRectangle(g);
        
        g.setClip(null);
    }
    
    private void drawBackground(Graphics g) {
        Rectangle bg = this.getBox();
        g.setColor(Color.WHITE);        
        g.fillRect(bg.x, bg.y, bg.width, bg.height);
    }

    private void  drawSelectionRectangle(Graphics g) {
        Rectangle r = this.getMouseEventProcessor().getSelectionRectangle();
        
        g.setColor(new Color(0, 255, 0, 150));
        g.fillRect(r.x, r.y, r.width, r.height);
        
        g.setColor(Color.GREEN);
        g.drawRect(r.x, r.y, r.width, r.height);
    }
}
