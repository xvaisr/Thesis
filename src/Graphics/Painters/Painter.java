/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics.Painters;

import Enviroment.EnvObjFeatures.DrawableGameObject;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author lennylinux
 */
public interface Painter {

    public void paint (Graphics g, DrawableGameObject o, Rectangle aov);
    
}
