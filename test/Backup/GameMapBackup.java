/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnviromentalMap;
import Enviroment.EnviromentalMap.Chunk;
import java.awt.Point;
import java.util.ArrayList;
import Enviroment.EnvObjects.GameObjectBackup;
import RTreeAlgorithm.Rtree;
import java.awt.Dimension;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public class GameMapBackup {
    // GameMap constants
    public static final int DEFAULT_WIDTH = 3000;
    public static final int DEFAULT_HEIGHT = 3000;
    
    // GameMap parameters
    private Rtree<GameObjectBackup> staticMapTree;
    private Rtree<GameObjectBackup> smellMapTree;
    private Rtree<Chunk> agentMapTree;
    private int mapX;
    private int mapY;

    public GameMapBackup() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public GameMapBackup(int width, int height) {
        this.staticMapTree = new Rtree();
        this.mapX = (width > 0)? width : 1;
        this.mapY = (height > 0)? height : 1;
    }  
    
    public Dimension getMapSize() {
        return new Dimension(mapX, mapY);
    }
    
    public int getMapWidth() {
        return mapX;
    }
    
    public int getMapHeight() {
        return mapY;
    }
    
    public boolean addGameObject(GameObjectBackup o) {
        return this.addGameObject(o, o.getPosition());
    }
    
    public boolean addGameObject(GameObjectBackup o, int x, int y) {
        return this.addGameObject(o, new Point(x, y));
    }
    
    public boolean addGameObject(GameObjectBackup o, Point p) {       
        if ((0 > p.x || this.mapX < p.x) ||
            (0 > p.y || this.mapY < p.y)) 
        {
            return false;
        }
        if (!p.equals(o.getPosition())) {
            o.setPosition(p);
        }
        switch(o.getType()) {
            case agent:
            case resource:
                this.staticMapTree.Insert(o, o.getRectangle());
            break;
            case feromonPath:
                this.staticMapTree.Insert(o, o.getRectangle());
            break;
            case resourceBlock:    
                this.staticMapTree.Insert(o, o.getRectangle());
            default:
                this.staticMapTree.Insert(o, o.getRectangle());
            break;
        }
        this.staticMapTree.Insert(o, o.getRectangle());
        return true;
    }
    
    public ArrayList<GameObjectBackup> checkArea(Rectangle r) {
        return this.staticMapTree.Find(r);
    }
    
    public ArrayList<GameObjectBackup> getGameObjectsAt(Point p) {
        ArrayList<GameObjectBackup> list = this.staticMapTree.Find(p);
        for (GameObjectBackup o : list) {
            if (!o.getPosition().equals(p)) {
                list.remove(o);
            }
        }
        return list;
    }
    
    public void removeGameObject(GameObjectBackup o) {
        this.staticMapTree.Delete(o, o.getPosition());
    }
    
}
