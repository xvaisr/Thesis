/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package Enviroment.EnvObjFeatures;

import Agents.Team.Team;
import Enviroment.EnvObjects.GameObject;

public interface AgentInterface extends GameObject {
    public static final String DEFAULT_AGENT_NAME = "AgentSteev";
    
    public String getName();
    public void setName(String name);
    public Team getTeam();
    public void setTeam(Team team);
    
}
