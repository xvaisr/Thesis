/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;
import java.util.ArrayList;
import java.awt.Color;

/**
 *
 * @author lennylinux
 */
public class Team {
    private final int MEMBERS_MIN = 30;
    private final Color color;
    private ArrayList<Agent> members;
    
    public Team(int id) {
        this(new Color(id));
    }
    
    public Team(Color c) {
        this.color = c;
        this.members.ensureCapacity(MEMBERS_MIN);
    }
    
    public int getID() {
        return this.color.getRGB();
    }
    
    public void setMember(Agent a) {
        this.members.add(a);
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
    
}
