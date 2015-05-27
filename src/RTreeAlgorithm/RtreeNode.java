/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package RTreeAlgorithm;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 * @param <T>
 */
public class RtreeNode<T> {
    private final int MIN_ENTERIES = 4;
    private final int MAX_ENTERIES = 30;
    private final int MIN_ROOT_ENTERIES = 1;
    private final int MAX_ROOT_ENTERIES = 2;
    
    private RtreeNode parentNode;
    private ArrayList<RtreeNode> children;
    private Rectangle r;
    private T object;
    private int minEnteries;
    private int maxEnteries;
    private boolean change;
    private int level;

    public RtreeNode() {
        this.parentNode = null;
        this.r = null;
        this.object = null;
        this.children = new ArrayList();
        this.minEnteries = MIN_ENTERIES;
        this.maxEnteries = MAX_ENTERIES;
        this.change = false;
        this.level = 0;
    }
    
    public RtreeNode(ArrayList<RtreeNode> list) {
        this();
        this.children.addAll(list);
        calculateRectangle();
    }

    // <editor-fold defaultstate="collapsed" desc="methods - node itself"> 
    public void setParent(RtreeNode parent) {
        if (parent == null) return;
        
        int newLevel = parent.getLevel();
            newLevel++;
            
        if (parent != this.parentNode || newLevel != this.level) {
            this.level = newLevel;
            this.parentNode = parent;
            this.minEnteries = this.parentNode.getMinEnteries();
            this.maxEnteries = this.parentNode.getMaxEnteries();
            
            for (RtreeNode node : this.children) {
                node.setParent(this);
            }
            this.change = false;
        }
    }
    
    public RtreeNode getParent() {
        return this.parentNode;
    }

    public boolean getIsLeaf() {
        return ((this.children.isEmpty() && this.object == null) ||
                ((this.children.size() >= 1) &&
                 (this.children.get(0).getContent() != null))
               );
    }
    
    public boolean getIsRoot() {
        return this.object == null && this.parentNode == null;
    }
    
    public Rectangle getRectangle() {
        this.adjustRectangle();
        return (this.r == null)? new Rectangle() : this.r;
    }
    
    public void clearNode() {
        this.r = null;
        this.children.clear();
    }
    
    public void setMinEnteries(int e) {
        this.minEnteries = e;
    }
    
    public void setMaxEnteries(int e) {
        this.maxEnteries = e;
    }
    
    public int getMinEnteries() {
        return this.minEnteries;
    }
    
    public int getMaxEnteries() {
        return this.maxEnteries;
    }
    
    public void setChildnodeChange() {
        this.change = true;
    }
    
    public int getEnlargement(Rectangle r) {
        // If node doesn't have rectangle enlargement equals enlarging rectangle
        if (this.r == null) {
            return (r.height * r.width);
        }
        
        // otherwise enlarge and calculate enlargement
        Rectangle u = this.r.union(r);
        return (u.width * u.height) - (this.r.width * this.r.height);
    }
    
    public int getArea() {
        Rectangle u = this.getRectangle();
        return (u.width * u.height);
    }
    
    public boolean getOwerflow() {
        return (this.children.size() > this.maxEnteries) ||
                (this.getIsRoot() && this.children.size() > MAX_ROOT_ENTERIES);
    }
    
    public boolean getUnderflow() {
        return (this.children.size() < this.minEnteries) ||
                (this.getIsRoot() && this.children.size() < MIN_ROOT_ENTERIES);
    }
    
    public int  getLevel() {
        return this.level;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="methods for data consistenci">
    public void adjustRectangle() {
        if (this.change) {
            this.calculateRectangle();
        }
    }
    
    private void calculateRectangle() {
        if (this.children.isEmpty()) {
            this.r = null;
            return;
        }
        
        Rectangle s = new Rectangle();
        for (RtreeNode n : this.children) {
            s = s.union(n.getRectangle());
        }
        if (!s.equals(this.r)) {
            if (this.parentNode != null)
                this.parentNode.setChildnodeChange();
            this.r = s;
        }
        this.change = false;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="methods for child nodes management">
    public ArrayList<RtreeNode> getChildList() {
        return (ArrayList<RtreeNode>) this.children.clone();
    }

    public void replaceChildList(ArrayList<RtreeNode> list) {
        this.clearNode();
        this.children.clear();
        for (RtreeNode node : list) {
            this.children.add(node);
            node.setParent(this);
        }
        this.adjustRectangle();
    }
    
    public void addChildnode(RtreeNode n) {
        Rectangle s;
        if (this.children.contains(n)) {
            return;
        }
        this.children.add(n);
        if (this.r == null) {
            s = n.getRectangle();
        }
        else {
            s = this.r.union(n.getRectangle());
        }

        n.setParent(this);
        if (!s.equals(r) && parentNode != null) {
            this.parentNode.setChildnodeChange();
        }
        
        this.r = s;
        this.change = false;            
    }
    
    public int getChildnodeCount() {
        return this.children.size();
    }
    
    public void removeChild(RtreeNode child) {
        this.children.remove(child);
        this.setChildnodeChange();
        this.adjustRectangle();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="methods related to object contained by node">
    
    public T getContent() {
        return this.object;
    }
    
    public boolean setContent(T obj, Rectangle r) {
        this.r = r;
        this.object = obj;
        if (this.parentNode != null)
            this.parentNode.setChildnodeChange();
        
        if (obj instanceof RtreeNodeLinker) {
            ((RtreeNodeLinker) obj).setNode(this);
            this.r = ((RtreeNodeLinker) obj).getBoundingBox();
        }
        return true;
    }
    
    public boolean setContent(T obj, Point p) {
        this.r = new Rectangle(p);
        this.r.grow(1, 1);
        this.object = obj;
        if (this.parentNode != null)
            this.parentNode.setChildnodeChange();
        
        if (obj instanceof RtreeNodeLinker) {
            ((RtreeNodeLinker) obj).setNode(this);
        }
        
        return true;
    }
    
    public boolean getIsEntery() {
        return (this.object != null);
    }
    
    // </editor-fold>
    
    @Override
    public String toString() {
        String s = new String();
        if (this.getIsEntery()) {
            s = s.concat("entery ");
        }
        else if (this.getIsRoot()) {
            s = s.concat("root ");
        }
        else if (this.getIsLeaf()) {
            s = s.concat("leaf ");
        }
        else {
            s = s.concat("node ");
        }
        Integer c = this.children.size();
        s = s.concat("with ".concat(c.toString()).concat(" child nodes;"));
        
        return s;
    }
}
