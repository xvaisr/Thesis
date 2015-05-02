/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.ViewAndDraw;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public interface ViewSpaceHolder {    
    public Rectangle getAreaOfView();
    public Point getViewPosition();
    public boolean shiftWindow(int horizontaly, int verticaly);
}
