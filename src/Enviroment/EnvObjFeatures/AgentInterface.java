/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjFeatures;

import Agents.Team.Team;
import Enviroment.EnvObjects.GameObject;

/**
 *
 * @author lennylinux
 */
public interface AgentInterface extends GameObject {
    public static final String DEFAULT_AGENT_NAME = "AgentSteev";
    
    public String getName();
    public void setName(String name);
    public Team getTeam();
    public void setTeam(Team team);
    
}
