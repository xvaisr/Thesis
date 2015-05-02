/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures;

import Enviroment.EnvObjects.GameObject;
import Graphics.Painters.Painter;
import java.awt.Color;

/**
 *
 * @author lennylinux
 */
public interface DrawableGameObject extends GameObject {
    
    public void setColor(Color c);
    public Color getColor();
    public void setPainter(Painter p);
    public Painter getPainter();
    
}
