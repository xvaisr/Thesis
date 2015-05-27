/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package RTreeAlgorithm;
import java.awt.Point;
import java.awt.Rectangle;
import static java.lang.Math.abs;
import java.util.ArrayDeque;
import java.util.ArrayList;


/**
 *
 * @author lennylinux
 * @param <T>
 */
public class Rtree<T>{
    private RtreeNode<T> root;
    
    public Rtree() {
        this.root = new RtreeNode();
    }
    
    public Rtree(int min, int max) {
        this();
        this.root.setMinEnteries(min);
        this.root.setMaxEnteries(max);
    }
    
    public ArrayList<T> Find(Point p) {
        ArrayList<T> objects = new ArrayList();
        ArrayList<RtreeNode> enteryList = this.Search(p);
        for(RtreeNode<T> node : enteryList) {
            objects.add(node.getContent());
        }
        return objects;
    }
    
    public ArrayList<T> Find(Rectangle r) {
        ArrayList<T> objects = new ArrayList();
        ArrayList<RtreeNode> enteryList = this.Search(r);
        for(RtreeNode<T> node : enteryList) {
            objects.add(node.getContent());
        }
        return objects;
    }
    
    public void Insert(T object, Point p) {
        RtreeNode<T> entery = new RtreeNode();
        entery.setContent(object, p);
        this.Insert(entery, -1);
    }
    
    public void Insert(T object, Rectangle r) {
        RtreeNode<T> entery = new RtreeNode();
        entery.setContent(object, r);
        this.Insert(entery, -1);
    }
    
    public void Delete(T object, Point p) {
        this.Delete(object, new Rectangle((p.x - 1), (p.y - 1), 2, 2));
    }
    
    public void Delete(T object, Rectangle r) {                                 // delete
        /*
          D1 [Find node containg record] Invoke FindLeaf  to locate leaf L
          containing E. Stop if record was not found.
         */
        RtreeNode node = this.FindLeaf(object, r);
        
        /*
          D2 [Remove entery] Remove E from L
         */
        
        this.DeleteEntery(node, object);
        
        /*
          D3 [Propagate changes] Invoke CondenseTree.
        
          CT1 [Inicialize] Set N = L and Let Q to be set of eliminated nodes. 
          Set Q to be empty.
         */
        ArrayList<RtreeNode> Q = new ArrayList();
        while(node != null) {
            /*
              CT2 [Find parent entry] If N is a root skyp to CT6. Otherwise let
              P to be parent of N and let En be N's entry in P.
            
              CT3 [eliminate under-full node] If N has fewer than m enteries
              delete En from P and add N to set Q
                    -- already known that node is underflowing
             */
            
            if (node.getUnderflow() && !node.getIsRoot()) {
                Q.addAll(node.getChildList());
                this.DeleteNode(node);           
            }
            
            /*
              CT4 [Adjust covering rectangle] If N has not been eliminated,
              adjust En's I to tightly contain all enteries in N
              P to be parent of N and let En be N's entry in P.
                  -- all rectangles are adjusted on change of node
                  -- parent rectangles need to be adjusted
             */
            
            else {
                node.adjustRectangle();
            }
            
            /*
              CT5 [Move up one level in tree] set N = P and repeat from CT2
             */
            
            // node's parent is adjusted by delete operation;
            node = node.getParent();
            
        }
        
        /*
          CT6 [Re-Insert] reinsert All Enteries of nodes in set Q. Enteries
          from eliminated leaf nodes are reinserted in tree leaves as described
          in Insert algorithm, but enteries from higher-level nodes must be
          placed higher in the tree so that leaves of their dependent subtrees
          will be on the same levels as the leaves of the main tree.
         */
        
        for (RtreeNode orphan : Q) {
            this.Insert(orphan, (orphan.getLevel() - 1));            
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="supporting (sub)algorithms - only private methods">
    
    private void Insert(RtreeNode entery, int level) {
        
        // System.out.println("Vkladam : ".concat(entery.toString()));
        
        /*
          Inserting index records for new data touples is similar to insetion
          in B-tree in that new index records are added to the leaves, nodes
          that overflows are split, and splits prpagate up the tree.
         */
        RtreeNode node;
        Pair<RtreeNode, RtreeNode> split = null;
        
        /*
          I1 [Find position for new record] Inwoke ChoeseLeaf to select node L
          in which to place entery
         */
        node = this.ChooseSubtree(entery, level);
        // System.out.println("Vybran : ".concat(node.toString()));
        
        /*
          I2 [Add record to the leaf node] If L has room for another entery,
          insatall E. Otherwise  invoke Split node to obtain L and LL containing
          E and all old enteries.
         */
        node.addChildnode(entery);
        while (node.getOwerflow()) { 
            /*
              AT1 Set N = L (L == leaf) if N was split previously, set NN to be
              resulting second node.
                  -- here represented by Pair split 
              AT2 [Check if done] If N is root -> stop;
                  -- already known that split was done
                  -- split is performed even on root node
             */
            split = this.SplitNode(node);
            // System.out.println("Split : ".concat(split.getA().toString().concat(" a ").concat(split.getB().toString())));            
            
            RtreeNode parent = node.getParent();
            
            if (node.getIsRoot()) {
                /*
                  I4 [ Grow tree taller] If node propagation caused root to split
                  create new root whose  childrean are the two resulting nodes.
                 */
                this.root = new RtreeNode();
                this.root.setMinEnteries(node.getMinEnteries());
                this.root.setMaxEnteries(node.getMaxEnteries());
                this.root.addChildnode(split.getA());
                this.root.addChildnode(split.getB());
                node = this.root;
            }
            
            /*
              I3 [Propagate changes upward] Inwoke AdjustTree on L also passing 
              LL if a split was performed.
            
              AT3 [Adjust covering rectangle in parent entry] 
                  -- rectangle is adjusted every time node is added. If existing
                     node has been changed, paretn is notified.

              AT4 [Propagate node split upward] If N has a partner NN resulting from
              node split, creante new entery Enn-pointer pointing to NN and
              Enn-rectangle enclosing all rectangles in NN. Add NN to P (N's parent)
              if there is a room;
             */
            else {               
                parent.removeChild(node);
                parent.addChildnode(split.getA());
                parent.addChildnode(split.getB());
                
                /*
                  AT5 [Move up to next level] Set N = P and set NN = PP if slpit occured.
                  Repeat from AT2
                 */

                node = parent;
            }
            

            
        }
        
        // adjist the rest of the tree;
        while(node != null) {
            node.adjustRectangle();
            node = node.getParent();
        }
    }
    
    private RtreeNode ChooseSubtree(RtreeNode entery, int level) {              // Insert
        int enlargement = Integer.MAX_VALUE;
        int area = Integer.MAX_VALUE;
        if (level < 0) {
            level = Integer.MAX_VALUE;
        }
        // placing entery E - object; EI - r
        
        // CL1 Set N to be the root node
        RtreeNode node = this.root;
        
        // CL2 if N is leaf, return N
        while (node.getLevel() < level && !node.getIsLeaf()) {
            ArrayList<RtreeNode> childSet, nodeSet;
            childSet = node.getChildList();
            if (childSet.isEmpty()) {
                break;
            }
            nodeSet = new ArrayList();
            
            
            /* CL3 if N is not a leaf, let F be entery in N whose rectangle
               FI needs least enlargement to include EI.                */
            for (RtreeNode n : childSet) {
                int e = n.getEnlargement(entery.getRectangle());
                if (e < enlargement) {
                    nodeSet.clear(); // clear larger enteries
                    nodeSet.add(n);
                    enlargement = e; // decrese maximum enlargement
                }
                else if (e == enlargement) {
                    nodeSet.add(n);
                }
            }
            
            // Resolve ties by smallest area rectangle;
            for (RtreeNode n : nodeSet) {
                int a = n.getArea();
                if (a <= area) {
                    childSet.clear(); // clear larger enteries
                    childSet.add(n);
                    area = a;         // decrese maximum area
                }
                else if (a == area) {
                    childSet.add(n);
                }
            }
            
            // we need at least one child node selected
            if (childSet.isEmpty()) {
                return null; // Oops !!! error :(
            }
            
            // if these are still equale, pick one
            node = childSet.get(0); 
        } // while end - continue from CL2
        
        
        return node;
    }

    private Pair<RtreeNode, RtreeNode> SplitNode(RtreeNode node) {              // node spliting
        /*
          divide a set of M + 1 index enteries in two groups
          Quadratic split and linear split are the same, but use different 
          PickSeeds algorithm 
         */
        
        /*
          QS1 [Pick first entery of each group] Apply algorithm PickSeeds to
          choese two enteries to be first elements of groups. Assign each to
          a group
         */
        
        ArrayList<RtreeNode> childList = node.getChildList();
        Pair<RtreeNode, RtreeNode> pair = QadraticPickSeeds(childList);
        
        RtreeNode groupA = new RtreeNode();
        groupA.addChildnode(pair.getA());
        
        RtreeNode groupB = new RtreeNode();
        groupB.addChildnode(pair.getB());
        
        /*
          QS2.1 [check if done] If all enteries have been assigned stop.
         */
        
        while (!childList.isEmpty()) {
        
            /*
              QS3 [select entery to be assigned] Invoke algoritm PickNext to choese
              next entery to assign. Add it to the group with rectangle which will
              have to enlarge least to acomodate it.
             */
            
            node = Rtree.PickNext(childList, groupA, groupB);
            int da = groupA.getEnlargement(node.getRectangle());
            int db = groupB.getEnlargement(node.getRectangle());

            if (da < db) {
                groupA.addChildnode(node);
            }
            else if (da > db) {
                groupB.addChildnode(node);
            }
            else {
                /* 
                  resolve ties by adding entery into group:
                                - with smaller area 
                                - with fewer enteries
                                - oh, it doesn't matter any more ... pick one!
                 */
                
                // smaller area tie resolving
                if (groupA.getArea() < groupB.getArea()) {
                    groupA.addChildnode(node);
                }
                else if(groupA.getArea() > groupB.getArea()) {
                    groupB.addChildnode(node);
                }
                
                else {
                    
                    // fewer enteries resolving
                    if (groupA.getChildnodeCount() < groupB.getChildnodeCount()) {
                        groupA.addChildnode(node);
                    }
                    else {
                         // b has fewer child nodes or it doesnt'n matter anymore
                        groupB.addChildnode(node);
                    }
                }
            }
            
            if(childList.isEmpty()) break;

            /*
              QS2.2 [check if done] if one group has so few enteries that all
              the rest must be assignet to it in order to have minimum number
              m, asign them and stop
             */
            
            if (groupA.getUnderflow() && !groupB.getUnderflow()) {
                int required = groupA.getMinEnteries() - groupA.getChildnodeCount();
                if (required >= childList.size()) {
                    for(RtreeNode m : childList) {
                        groupA.addChildnode(m);
                    }
                    break;
                }
                
            }
            
            if (!groupA.getUnderflow() && groupB.getUnderflow()) {
                int required = groupB.getMinEnteries() - groupB.getChildnodeCount();
                if (required >= childList.size()) {
                    for(RtreeNode m : childList) {
                        groupB.addChildnode(m);
                    }
                    break;
                }
            }
            
        // repeat from QS2
        }
        
        return new Pair(groupA, groupB);
    }
    
    private static Pair<RtreeNode, RtreeNode> 
        QadraticPickSeeds(ArrayList<RtreeNode> list) {                          // node spliting
        /*
          QPS1 [Calculate inefficiency of grouping enteries together]
          For each pair of enteries E1 and E2 compose rectangle J.
          Calculate area D = J.getArea - E1.getArea - E2.getArea
        
          QPS2 [Choese the most wasteful pair] choese pair with largest D
        */
        Pair<RtreeNode, RtreeNode> p = null;
        int d = Integer.MIN_VALUE; // negative numbers mean overlap
        
        for (RtreeNode a : list) {
            for(RtreeNode b: list) {
                if (a != b) {
                    Rectangle j = a.getRectangle().union(b.getRectangle());
                    /*
                     If A nad B are overlaping and J is tightly closing them,
                     sum of their areaa is greater than area of J
                     */
                    int area = (j.height * j.width) - a.getArea() - b.getArea();
                    if (area > d) {
                        d = area;
                        p = new Pair(a, b);
                    }
                }
            }
        }
        
        if (p != null) {
            list.remove(p.getA());
            list.remove(p.getB());
        }
        return p;
    }
    
    private static Pair<RtreeNode, RtreeNode>
        LinearPickSeeds(ArrayList<RtreeNode> list) {                            // node spliting  *** yet to be implemented
            return null;                                                        // not used
    }
    
    private static RtreeNode // Pair<RtreeNode, RtreeNode> - for future optimalization
        PickNext(ArrayList<RtreeNode> list, RtreeNode groupA, RtreeNode groupB) // node spliting
    {
        /*
          PN1 [Determine cost of putting each entery in each group]
          For each entery E not yet in group calculate D1 and D2
          - area increase required to include enteri into group A 
          (group 1) and group B (group 2) 
         */
        
        int diff;
        diff = -1; // intentionaly lesser than zero
        RtreeNode chosenOne = null;
        for (RtreeNode node : list) {
           int d1 = groupA.getEnlargement(node.getRectangle());
           int d2 = groupB.getEnlargement(node.getRectangle());
           int df = abs(d1 - d2);
           
           /*
             PN2 [Find the entery with greatis preference for the group]
             choese any entery with maximum diference between D1 and D2
            */
           
           if (df > diff) {
               chosenOne = node;
               diff = df;
           }
        }
        list.remove(chosenOne);
        return chosenOne;   
    }
    
    private ArrayList<RtreeNode> Search(Rectangle r) {
        ArrayList<RtreeNode> enteries = new ArrayList();
        ArrayDeque<RtreeNode> nodes = new ArrayDeque();
        nodes.add(this.root);
        
        while (!nodes.isEmpty()) {
            RtreeNode n = nodes.poll();
            ArrayList<RtreeNode> children;
            if (n != null) {
                children = n.getChildList();
            }
            else {
                children = new ArrayList();
            }
            for(RtreeNode node : children) {
                if (r.intersects(node.getRectangle())) {
                    nodes.add(node);
                    if (node.getContent() != null) {
                        enteries.add(node);
                    }
                }
                
            }
        }
        
        return enteries;
    }
    
    private ArrayList<RtreeNode> Search(Point p) {
        ArrayList<RtreeNode> enteries = new ArrayList();
        ArrayDeque<RtreeNode> nodes = new ArrayDeque();
        nodes.add(this.root);
        
        while (!nodes.isEmpty()) {
            RtreeNode n = nodes.poll();
            ArrayList<RtreeNode> children;
            if (n != null) {
                children = n.getChildList();
            }
            else {
                children = new ArrayList();
            }
            for(RtreeNode node : children) {
                if (node.getRectangle().contains(p)) {
                    nodes.add(node);
                    if (node.getContent() != null) {
                        enteries.add(node);
                    }
                }
                
            }
        }
        
        return enteries;
    }
        
    private RtreeNode<T> FindEntry(T object, Rectangle r) {                     // finds node entry containing object
        ArrayList<RtreeNode> list = this.Search(r);
        RtreeNode<T> result = null;
        for (RtreeNode<T> node : list) {
            if (node.getContent() == object) {
                result = node;
                break;
            }
        }
        return result;
    }
    
    private RtreeNode<T> FindLeaf(T object, Rectangle r) {                      // find leaf containing entry with given object
        RtreeNode node = this.FindEntry(object, r);
        if (node != null) {
            node = node.getParent();
        }
        return node;
    }
    
    private void DeleteEntery(RtreeNode node, T object) {
        if (node == null) {
            return;
        }
        ArrayList<RtreeNode> list = node.getChildList(); 
        
        for (RtreeNode<T> entery : list) {
            if(entery.getContent() == object) {
                node.removeChild(entery);
                break;
            }
        }
        if (!node.getIsRoot()) {
            node.getParent().adjustRectangle();
        }
    }
    
    private void DeleteNode(RtreeNode node) {
        // if node is not root, remove it from it's parent
        if (!node.getIsRoot()) {
            node.getParent().removeChild(node);
            node.getParent().adjustRectangle();
        }
        // throw node away ...
    }
    // </editor-fold>
    
}

// <editor-fold defaultstate="collapsed" desc="unused methods, commnted out">

/*
private RtreeNode ChooseLeaf(T object, Rectangle r) {                           // Insert
        int enlargement = Integer.MAX_VALUE;
        int area = Integer.MAX_VALUE;
        // placing entery E - object; EI - r
        
        // CL1 Set N to be the root node
        RtreeNode node = this.root;
        
        // CL2 if N is leaf, return N
        while (!node.getIsLeaf()) {
            ArrayList<RtreeNode> childSet, nodeSet;
            childSet = node.getChildList();
            nodeSet = new ArrayList();
            
            
            /* CL3 if N is not a leaf, let F be entery in N whose rectangle
               FI needs least enlargement to include EI.                * /
            for (RtreeNode n : childSet) {
                int e = n.getEnlargement(r);
                if (e < enlargement) {
                    nodeSet.clear(); // clear larger enteries
                    nodeSet.add(n);
                    enlargement = e; // decrese maximum enlargement
                }
                else if (e == enlargement) {
                    nodeSet.add(n);
                }
            }
            
            // Resolve ties by smallest area rectangle;
            for (RtreeNode n : nodeSet) {
                int a = n.getArea();
                if (a <= area) {
                    childSet.clear(); // clear larger enteries
                    childSet.add(n);
                    area = a;         // decrese maximum area
                }
                else if (a == area) {
                    childSet.add(n);
                }
            }
            
            // we need at least one child node selected
            if (childSet.isEmpty()) {
                return null; // Oops !!! error :(
            }
            
            // if these are still equale, pick one
            node = childSet.get(0); 
        } // while end - continue from CL2
        
        
        return node;
    }


*/

// </editor-fold>