/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures.Emitors;

import Enviroment.EnvObjFeatures.DetectableGameObject;

/**
 *
 * @author lennylinux
 */
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
