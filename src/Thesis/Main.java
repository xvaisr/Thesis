/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Thesis;

import Agents.Team.Team;
import Enviroment.Model;
import GraphicInterface.UserInterface;
import java.awt.Color;
import java.util.ArrayList;

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
        Model.addTeam(new Team(Color.MAGENTA));
        
        // generate map
        Model.setNewMap(40, 30);

        // generate agents
        ArrayList<Team> teams = Model.getTeamList();
        for (Team team : teams) {
            Model.createNewAgent(team);
        }

        // add aditional gameobjects to map - if required
        /*
          GameObject ob;
          ob = new GameObject
          Model.getCurrentMap().addGameObject(ob);
        */
        
        // start up user interface
        UserInterface ui = UserInterface.getInstance();
        // MenuInterfacce ui = MenuInterfacce.getInstance();
        Thread t  = new Thread(ui);
        t.start();       
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
