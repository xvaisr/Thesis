/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics;

import Enviroment.EnvObjects.GameObject;
import java.awt.Graphics;

/**
 *
 * @author lennylinux
 */
public class GameObjectPainter {

    public GameObjectPainter() {
    }
    
    public void paintObject(Graphics g, GameObject obj) {
        switch (obj.getType()) {
            case agent:
                this.drawAgent(g, obj);
            break;
            case obsticle:
                this.drawObsicle(g, obj);
            break;
            case resource:
                this.drawResource(g, obj);
            break;
            case resourceBlock:
                this.drawResourceBlock(g, obj);
            break;
            case feromonPath:
                this.drawPath(g, obj);
            break;
            default:
                throw new AssertionError(obj.getType().name());
        }
    }
    
    private void drawAgent(Graphics g, GameObject obj) {
    }        
    
    private void drawObsicle(Graphics g, GameObject obj) {
    }
    
    private void drawResource(Graphics g, GameObject obj) {
    }
    
    private void drawResourceBlock(Graphics g, GameObject obj) {
    }
    
    private void drawPath(Graphics g, GameObject obj) {
    }
    
}
