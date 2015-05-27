/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Enviroment.EnvObjFeatures.Emitors;

import Enviroment.EnvObjFeatures.DetectableGameObject;

public class SoundEmitor extends AbstractEmitor{
    private String message;

    public SoundEmitor(DetectableGameObject originator) {
        super(originator);
        this.message = "";
    }
    
    public void setEmitorMessage(String msg) {
        synchronized(this) {
            this.message = msg;
        }
    }
    
    public String getMessage() {
        String tmpMsg;
        synchronized(this) {
            tmpMsg = this.message;
        }
        return tmpMsg;
    }
}
