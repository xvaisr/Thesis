/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Thesis;

import Agents.Team.Team;
import Enviroment.EnvObjects.Obstacles.Obstacle;
import Enviroment.EnvObjects.ObjectParts.Shape;
import Enviroment.Model;
import GraphicInterface.MenuInterfacce;
import GraphicInterface.UserInterface;
import GraphicInterface.UserInterface;
import java.awt.Color;
import java.util.ArrayList;
// import KeyEventFixer.KeyEventsFixer;

/**
 *
 * @author lennylinux
 */
public class Main {
    
    public static final int INFO = 1;
    public static final int WARNING = 2;
    public static final int SEVERE = 3;
    
    public static boolean debug = true;

    public Main() {
    }
    
    /**
     * @param args the command line arguments, not used
     */
    public static void main(String[] args) {
        // KeyEventsFixer fixer = new KeyEventsFixer();
        // fixer.install();

        /* 
           as first step set up tems for the game
           as second step generate map - anthils are generated during this stage
           as third step generate agnets - agents need both map and anthil,
                     otherwise NUllPointerException will occure
           as forth step add to map what ever objects you wish to add
           as fifth step you can fire up user interface - it will handle the rest
         */
        
        // setup teams
        Model.addTeam(new Team(Color.red));
      //  Model.addTeam(new Team(Color.MAGENTA));
        
        // generate map
        Model.setNewMap(40, 30);

        // generate agents
        ArrayList<Team> teams = Model.getTeamList();
        for (Team team : teams) {
            Model.createNewAgent(team);
        }

        // add aditional gameobjects to map - no longer needed
       /* Shape s = new Shape();
        s.addVertex(-20, -20);
        s.addVertex(10, -30);
        s.addVertex(60, -10);
        s.addVertex(20, 10);
        s.addVertex(0, 40);
        s.addVertex(-40, 20);
        
        Obstacle ob = new Obstacle(400, 400);
        ob.setNewShape(s);
        ob.setPainter(Model.getPainter(Model.POLYGON_PAINTER_INDEX));
        
        Model.getCurrentMap().addGameObject(ob);
        
        s = new Shape();
        s.addVertex(10, 20);
        s.addVertex(-10, 0);
        s.addVertex(-20, 30);
        s.addVertex(20, -20);
        s.addVertex(-40, 0);
        
        ob = new Obstacle(420, 400);
        ob.setNewShape(s);
        ob.setPainter(Model.getPainter(Model.POLYGON_PAINTER_INDEX));
        
        Model.getCurrentMap().addGameObject(ob);
        */
        
        // start up user interface
        UserInterface ui = UserInterface.getInstance();
        // MenuInterfacce ui = MenuInterfacce.getInstance();
        Thread t  = new Thread(ui);
        t.start();
 // */      
        
        
     
    }
    
    public static void debug(int level, String msg) {
        switch (level) {
        default:
        case INFO:
            if (debug) {
                System.out.println("[INFO] " + msg);
            }
            break;
        case WARNING:
            System.out.println("[WARNING] " + msg);
            break;
        case SEVERE:
            System.out.println("[SEVERE]" + msg);
            System.exit(1);
            break;
        }
    }
    
}
