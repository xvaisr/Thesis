/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment;
import java.awt.Point;
import java.util.Map;
import java.util.ArrayList;
import Enviroment.EnvObjects.GameObject;

/**
 *
 * @author lennylinux
 */
public class GameMap {
    // GameMap constants
    private static final int SHIFT_LENGHT = 8;
    private static final int CHUNK_SIZE = 16;
    private static final int MAX_CHUNK_SIZE = 256;
    private static final int DEFAULT_MAP_X = 16;
    private static final int DEFAULT_MAP_Y = 16;
    
    // GameMap parameters
    Map<Integer, Chunk> chunks;
    private int chunkSize;
    private int mapX;
    private int mapY;
    private int mask;

    public GameMap() {
        this(CHUNK_SIZE, DEFAULT_MAP_X, DEFAULT_MAP_Y);
    }
    
    public GameMap(int chunkSize) {
        this(chunkSize, DEFAULT_MAP_X, DEFAULT_MAP_Y);
    }
    
    public GameMap(int chunkSize, int width, int height) {
        
        if(chunkSize > MAX_CHUNK_SIZE) {
            chunkSize = CHUNK_SIZE;
        }
        
        if ((chunkSize % 2) != 0) {
            chunkSize++;
        }
        
        this.mask = ~(Integer.MAX_VALUE << 
                    (Integer.SIZE - Integer.numberOfLeadingZeros(chunkSize)));
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Integer chunkID = GameMap.getChunkID(i, j, this.mask);
                Chunk c = new Chunk(chunkID, this.chunkSize);
                chunks.put(chunkID, c);
            }
        }
    }
    
    protected static Integer getChunkID(int x, int y, int mask) {
        int ID = 0;
        ID += (x ^ mask) << SHIFT_LENGHT;
        ID += (y ^ mask);
        return ID;
    }
    
    public Chunk getChunk(Point c) {
        Double x = c.getX();
        Double y = c.getY();
        return this.getChunk(GameMap.getChunkID(x.intValue(), y.intValue(), this.mask));
    }
    
    public Chunk getChunk(Integer ID) {
        return this.chunks.get(ID);
    }  
    
    public void addGameObject(GameObject o, int x, int y) {
        this.addGameObject(o, new Point(x, y));
    }
    
    public void addGameObject(GameObject o, Point p) {
        o.setPosition(p);
        ArrayList<Point> vertexList = o.getVertices();
        for (Point v : vertexList) {
            Chunk c = this.getChunk(v);
            
        }
        this.getChunk(p).addSortObject(o);
    }
    
     
    
}
