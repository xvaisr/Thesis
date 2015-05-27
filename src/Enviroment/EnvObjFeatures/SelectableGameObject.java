/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures;

public interface SelectableGameObject {
    public boolean getCanBeSelected();
    public boolean getIsSelected();    
    public void select();
    public void deselect();
}
