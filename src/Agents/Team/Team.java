/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Agents.Team;
import Enviroment.EnvObjects.Agents.Agent;
import Enviroment.EnvObjects.Agents.AntHill;
import java.util.ArrayList;
import java.awt.Color;

/**
 *
 * @author lennylinux
 */
public class Team {
    private final int MEMBERS_MIN = 30;
    private final int DEFAULT_ATTAK = 5;
    private final int DEFAULT_ARMOR = 1;
    private final int DEFAULT_SPEED = 5;
    private final String DEFAULT_HILL_AI = "../JasonCode/swarm.asl";
    private final String DEFAULT_ANT_AI = "/res/swarm.asl";
    
    private AntHill hill;
    private final Color color;
    private ArrayList<Agent> members;
    private String pathHillAI;
    private String pathAntAI;
    
    private int attak;
    private int armour;
    private int speed;
    
    
    public Team(int id) {
        this(new Color(id));
    }
    
    public Team(Color c) {
        this.color = c;
        this.members = new ArrayList();
        this.members.ensureCapacity(MEMBERS_MIN);
        
        this.pathHillAI = DEFAULT_HILL_AI;
        this.pathAntAI = DEFAULT_ANT_AI;
        
        this.attak = DEFAULT_ATTAK;
        this.armour = DEFAULT_ARMOR;
        this.speed = DEFAULT_SPEED;
    }
    
    public int getID() {
        return this.color.getRGB();
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setMember(Agent a) {
        if (a == null) {
            return;
        }
        
        this.members.add(a);
        a.setColor(this.color);
        a.setSpeed(this.speed);
    }
    
    public int getMemberCount() {
        return this.members.size();
    }
    
    public boolean getIsMember(Agent a) {
        return this.members.contains(a);
    }
    
    public void removeMember(Agent a) {
        this.members.remove(a);
    }
    
    public ArrayList<Agent> getMemberList() {
        return (ArrayList<Agent>) this.members.clone();
    }
    
    public void setAnthillObject(AntHill hill) {
        if (hill == null) {
            return;
        }
        this.hill = hill;
        this.hill.setTeam(this);
    }
    
    public AntHill getHill() {
        return this.hill;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        for (Agent agent : members) {
            agent.setSpeed(speed);
        }
    }

    public void setAttak(int attak) {
        this.attak = attak;
    }

    public int getArmour() {
        return armour;
    }

    public int getAttack() {
        return attak;
    }

    public int getSpeed() {
        return speed;
    }

    public String getAntAiPath() {
        return this.pathAntAI;
    }
    
    public void setAntAiPath(String path) {
        if (path == null || path.isEmpty())  {
            return;
        }
        this.pathAntAI = path;
    }
    
    public String getHillAiPath() {
        return this.pathHillAI;
    }
    
    public void setHillAiPath(String path) {
        if (path == null || path.isEmpty())  {
            return;
        }
        this.pathHillAI = path;
    }
    
    
    
    
}
