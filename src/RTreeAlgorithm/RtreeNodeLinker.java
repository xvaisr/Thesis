/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
