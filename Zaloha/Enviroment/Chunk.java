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
    private ArrayList<GameObject> chunkCentredObj;
    private ArrayList<Chunk> linkedChunks;
    
    public Chunk(Integer ID, Integer size) {
        this.ID = ID;
        this.size = size;
        this.i = 0;
        this.chunkCentredObj = new ArrayList();
        this.linkedChunks = new ArrayList();
    }
    
    public synchronized void addObject(GameObject obj) {
        this.chunkCentredObj.add(obj);
    }
    
    public synchronized void addSortObject(GameObject obj) {
        int areaSize = obj.getAreaSize();
        for (int index = 0; index < this.getCount(); index++) {
            if (areaSize < this.chunkCentredObj.get(index).getAreaSize()) {
                this.chunkCentredObj.add((index), obj);
                return;
            }
        }        
        this.chunkCentredObj.add(obj);
    }
    
    public int getCount() {
        return this.chunkCentredObj.size();
    }
    
    public GameObject getNextObject() {
        GameObject obj;
        obj = this.chunkCentredObj.get(this.i);
        this.i++;
        if(this.i == this.getCount()) {
            this.i = 0;
        }
        return obj;
    }
    
    public GameObject getPrevObject() {
        GameObject obj;
        obj = this.chunkCentredObj.get(this.i);
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
        return this.chunkCentredObj.get(i);
    }
    
    public boolean getIsInChunk(GameObject obj) {
        return this.chunkCentredObj.contains(obj);
    }
    
    public GameObject getObjectHere(Point c) {
        for (GameObject o : this.chunkCentredObj) {
            // o.getPosition()
        }
        return null;
    }
    
    protected void sortObjectlist() {
        if (this.chunkCentredObj.isEmpty()) {
            return;
        }
        
        Stack<Integer> ls = new Stack();
        Stack<Integer> rs = new Stack();
        
        int left, right, pivot;
        left = 0; // first index of array
        right = this.chunkCentredObj.size(); // index must be < right 
        
        ls.push(left);
        rs.push(right);
        
        while (!ls.empty() && !rs.empty()) {
            // take indexes from stack
            left = ls.pop();
            right = rs.pop();
                    
            pivot = right - 1; // choese pivot, can be any index in bounds
            int index = left; // start with left most elemnt
            
            while (index < pivot) {
                // if element has higher value than pivot
                if (this.chunkCentredObj.get(index).getAreaSize() > this.chunkCentredObj.get(pivot).getAreaSize()) {
                    GameObject item = this.chunkCentredObj.remove(index);
                    --pivot; // by removing element is pivot shifted to the left
                    this.chunkCentredObj.add((pivot + 1), item); // put element to pivot's right
                }
                else {
                    ++index; // compare pivot with next item;
                }
            }
            
            // put indexes of left subarray on stack (lesser values)
            ls.push(left); // start from begining agin
            rs.push(pivot); // pivot is already at rightspot -> right border
            
            // put indexes of right subarray on stack (higher values)
            ls.push(pivot + 1); // pivot is already at rightspot -> left border
            rs.push(right); // sort the rest of sublist

        }
    }
    
    public void update() {
        this.sortObjectlist();
    }
}
