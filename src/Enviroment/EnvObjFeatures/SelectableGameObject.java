/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures;

/**
 *
 * @author lennylinux
 */
public interface SelectableGameObject {
    public boolean getCanBeSelected();
    public boolean getIsSelected();    
    public void select();
    public void deselect();
}