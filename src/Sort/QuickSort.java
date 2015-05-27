/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Sort;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author lennylinux
 */
public class QuickSort {
    
    public static ArrayList <? extends Comparable> sortObjectlist(ArrayList<? extends Comparable> list) {
        if (list.isEmpty()) {
            return new ArrayList();
        }
        
        Stack<Integer> ls = new Stack();
        Stack<Integer> rs = new Stack();
        ArrayList<Comparable> sorted = (ArrayList<Comparable>) list.clone();
        
        int left, right, pivot;
        left = 0; // first index of array
        right = sorted.size(); // index must be < right 
        
        // inicialize sort for whole array (arraylist)
        ls.push(left);
        rs.push(right);
        
        while (!ls.empty() && !rs.empty()) {
            // take indexes from stack
            left = ls.pop();
            right = rs.pop();
                    
            pivot = right - 1; // choese pivot, can be any index in bounds
            int index = left; // start with left most elemnt
            
            if (left == right) {
                break;
            }
            
            while (index < pivot) {
                // if element has higher value than pivot
                if (sorted.get(index).compareTo(sorted.get(pivot)) > 0) {
                    Comparable item = sorted.remove(index);
                    --pivot; // by removing element is pivot shifted to the left
                    sorted.add((pivot + 1), item); // put element to pivot's right
                }
                else {
                    ++index; // compare pivot with next item;
                }
            }
            
            if (index < right) {
                // put indexes of left subarray on stack (lesser values)
                ls.push(left); // start from begining agin
                rs.push(pivot); // pivot is already at rightspot -> right border

                // put indexes of right subarray on stack (higher values)
                ls.push(pivot + 1); // pivot is already at rightspot -> left border
                rs.push(right); // sort the rest of sublist
            }

        }
        return sorted;
    }
    
    public static void insertObjectToList(ArrayList<Comparable> list, Comparable item) {
        if (list.isEmpty()) {
            list.add(item);
            return;
        }
        
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).compareTo(item) > 0) {
                list.add(i, item);
                break;
            }
        }
        
    }
    
}
