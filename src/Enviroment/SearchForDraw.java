/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment;

import Enviroment.EnvObjects.GameObject;
import Enviroment.EnviromentalMap.Chunk;
import Enviroment.EnviromentalMap.ExtendedMapInterface;
import Graphics.Input.GameObjectCasher;
import Graphics.ViewAndDraw.ViewSpaceHolder;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class SearchForDraw implements Runnable, GameObjectCasher {
    private static final int OBJECT_CACHE_SIZE = 30;
    private static final int CHUNK_CACHE_SIZE = 130;
    
    private static final SearchForDraw instance = new SearchForDraw();
    
    private volatile ViewSpaceHolder view;
    private volatile ExtendedMapInterface map;
    private volatile boolean mapChanged;
    private volatile boolean viewChanged;
    private volatile boolean running;
    
    private final ArrayList<GameObject> items;
    private final ArrayList<Chunk> chunks;
    private volatile Rectangle searchArea;

    private SearchForDraw() {
        // cashed item lists ...
        this.items = new ArrayList(OBJECT_CACHE_SIZE);
        this.chunks = new ArrayList(CHUNK_CACHE_SIZE);
        
        // rectangle is intentionaly inicialized out of bounds of the map 
        this.searchArea = new Rectangle(-10, -10); // with 0 area
        
        // flags
        this.mapChanged = true;
        this.viewChanged = false;
        this.running = false;
    }
    
    public static SearchForDraw getInstance() {
        return SearchForDraw.instance;
    }
    
    @Override
    public void setMapChanged() {
        synchronized(this) {
            this.mapChanged = true;
            this.notifyAll();
        }
    }
    
    @Override
    public void setViewChanged() {
        synchronized(this) {
            this.viewChanged = true;
            this.notifyAll();
        }
    }
    
    public void stop() {
        synchronized(this) {
            this.running = false;
            this.notifyAll();
        }
    }
    
    @Override
    public void setNewViewSpaceHolder(ViewSpaceHolder view) {
        synchronized(this) {
            this.view = view;
            this.setViewChanged();
        }
    }
    
    @Override
    public void run() {
        boolean run = true;
        synchronized(this) {
            this.running = true;
        }
        this.map = Model.getCurrentMap();
        this.mapChanged = true;
        
        while(run) {
            
            
            if (this.viewChanged && this.view != null) {
                // if we can get correct searcharea, get it
                this.searchArea = this.view.getAreaOfView();
                synchronized(this.chunks) {
                    this.chunks.clear();
                    
                    // if there is no map, let chunk cache list be emplty
                    if (this.map != null) {
                        this.chunks.addAll(this.map.getChunksInArea(this.searchArea));
                    }
                }
            }
            
            // if view changed, but we don't have that view, it is pointless
            else if (this.viewChanged) {
                this.viewChanged = false;
            }
            
            if (this.mapChanged || this.viewChanged) {
                ArrayList<GameObject> objectList;
                
                // if we have map to our disposal, find everything in given area
                if (this.map != null) {
                    objectList = this.map.getGameObjectsInAreaStaticMap(searchArea);    
                }
                else { // no map -> no objects
                    objectList = new ArrayList();
                }
                
                // all replace old cashed list with new one
                synchronized(this.items) {
                    this.items.clear();
                    this.items.addAll(objectList);
                }
            }
            
            // reset flags
            this.mapChanged = false;
            this.viewChanged = false;
            
            // wait till another change occurence
            synchronized(this) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    // interupted, don't care
                }
            }
            
            // check if we should continue
            synchronized(this) {
                run = this.running;
            }
        }
        
    }

    @Override
    public ArrayList<GameObject> getVisibleObjects() {
        ArrayList<GameObject> objectList;
        ArrayList<Chunk> chunkList;
        
        // get static objets from cashe
        synchronized(this.items) {
            objectList = ((ArrayList<GameObject>) this.items.clone());
        }
        // get dynamic map chunks from cache
        synchronized(this.chunks) {
            chunkList = ((ArrayList<Chunk>) this.chunks.clone());
        }
        
        // get all objects from cashed chunks 
        for (Chunk chunk : chunkList) {
            objectList.addAll(chunk.getCopyInnerArrayList());
        }
        
        return objectList;
    }
}
