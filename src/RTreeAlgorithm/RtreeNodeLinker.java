/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package RTreeAlgorithm;

import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public interface RtreeNodeLinker {
    
    public void setNode(RtreeNode node);
    public RtreeNode getNode();
    public Rectangle getBoundingBox();
}
