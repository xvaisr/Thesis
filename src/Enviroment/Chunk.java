/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment;
import java.util.ArrayList;
import java.util.Stack;
import Enviroment.EnvObjects.*;
import java.awt.Point;

/**
 *
 * @author lennylinux
 */
public class Chunk {
    private final Integer ID;
    private final int size;
    private int i;
    private ArrayList<GameObject> eObjects;
    
    public Chunk(Integer ID, Integer size) {
        this.ID = ID;
        this.size = size;   
    }
    
    public synchronized void addObject(GameObject obj) {
        this.eObjects.add(obj);
    }
    
    public synchronized void addSortObject(GameObject obj) {
        int areaSize = obj.getAreaSize();
        for (int index = 0; index < this.getCount(); index++) {
            if (areaSize < this.eObjects.get(index).getAreaSize()) {
                this.eObjects.add((index), obj);
                return;
            }
        }        
        this.eObjects.add(obj);
    }
    
    public int getCount() {
        return this.eObjects.size();
    }
    
    public GameObject getNextObject() {
        GameObject obj;
        obj = this.eObjects.get(this.i);
        this.i++;
        if(this.i == this.getCount()) {
            this.i = 0;
        }
        return obj;
    }
    
    public GameObject getPrevObject() {
        GameObject obj;
        obj = this.eObjects.get(this.i);
        this.i--;
        if(this.i < 0 ) {
            this.i = this.getCount() - 1;
        }
        return obj;
    }
    
    public GameObject getObject(int i) {
        if(i < 0 || i > this.getCount()) {
            return null;
        }
        return this.eObjects.get(i);
    }
    
    public boolean getIsInChunk(GameObject obj) {
        return this.eObjects.contains(obj);
    }
    
    public GameObject getObjectHere(Point c) {
        
        return null;
    }
    
    protected void sortObjectlist() {
        if (this.eObjects.isEmpty()) {
            return;
        }
        
        Stack<Integer> left = new Stack<>();
        Stack<Integer> right = new Stack<>();
        int boundary;    
        int l;
        int r;
        left.push(0);
        right.push(this.getCount());
        
        while (!left.empty() && !right.empty()) {
            l = left.pop();
            r = right.pop();
        
            if (l < r) {
                boundary = l;
                for (int index = l + 1; index < r; index++) {
                    if (this.eObjects.get(index).getAreaSize() > 
                        this.eObjects.get(l).getAreaSize())
                    {
                        this.swapObjects(index, ++boundary);
                    }
                }
                
                this.swapObjects(l, boundary);
                
                // left side
                left.push(l);
                right.push(boundary);
                // right side
                left.push(boundary + 1);
                right.push(r);
                
            }        
        }
    }
    
    protected void swapObjects(int left, int right) {
        this.eObjects.set(left, this.eObjects.set(i, this.eObjects.set(left, null)));
    }
    
    public void update() {
        this.sortObjectlist();
    }
}
