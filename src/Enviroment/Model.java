/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment;

import Agents.Team.Team;
import Enviroment.EnvObjects.Agents.AntHill;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnvObjects.Obsticles.Obsticle;
import Enviroment.EnvObjects.Resources.ResourceBlock;
import Enviroment.EnvObjects.Resources.ResourceTypes;
import Enviroment.EnvObjects.ObjectParts.Shape;
import Enviroment.EnviromentalMap.GameMap;
import Enviroment.EnviromentalMap.MapInterface;
import Agents.Actions.AgentAction;
import Enviroment.EnvObjFeatures.AgentInterface;
import Enviroment.EnvObjFeatures.DrawableGameObject;
import Enviroment.EnvObjects.Agents.Agent;
import Enviroment.EnvObjects.RectangleObject;
import Enviroment.EnviromentalMap.Chunk;
import Graphics.Painters.Painter;
import Graphics.Painters.*;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author lennylinux
 */
public class Model {
    // Model must be singleton -> private instance and constructor
    private static final Model instance = new Model();
    // Expected minimal counts of agents, teams, actions etc. ...
    private static final int EXPECTED_AGENT_COUNT = 100;
    private static final int EXPECTED_TEAM_COUNT = 4;
    private static final int EXPECTED_ACTIOONS_COUNT = 30;
    private static final int EXPECTED_PAINTER_COUNT = 5;
    
    // Private constants for generating map - default values
    private static final int OBSTICLE_COUNT = 20;
    private static final int MIN_VERTICES_COUNT = 3;
    private static final int MAX_VERTICES_COUNT = 7;
    private static final int VERTEX_RADIUS = 200;
    private static final Double PROBABILITY = 0.6;
    // preference: false - food, true - watter
    private static final boolean PREFERENCE = false; 
    //public final static Random rand = new Random();
    public final static Random rand = new Random(15);
    
    // publc painter index constants
    public static final int AGENT_PAINTER_INDEX = 0;
    public static final int ELIPTICAL_PAINTER_INDEX = 1;
    public static final int POLYGON_PAINTER_INDEX = 2;
    public static final int RECTANGULAR_PAINTER_INDEX = 3;
    public static final int RECTANGLE_PAINTER_INDEX = 4;
    
    // need to contain everything enviromentaly related
    private final HashMap<String, GameObject> agents;
    private final HashMap<String, AgentAction> actions;
    private final ArrayList<Team> teams;
    private final ArrayList<Painter> painters;
    private GameMap map;
    private long gameStart;
    private Chunk chunk;
    
    private Model() {
        this.agents = new HashMap(EXPECTED_AGENT_COUNT);
        this.actions = new HashMap(EXPECTED_ACTIOONS_COUNT);
        this.teams = new ArrayList(EXPECTED_TEAM_COUNT);
        this.painters = new ArrayList(EXPECTED_PAINTER_COUNT);
        this.map = null;
        
        this.initPainters();
        this.initGame();
    }
    
    /* Public methods for Model acces */
    
    public static Model getInstance() {
        return Model.instance;
    }
    
    public static AgentAction getActionByName(String name) {
        return Model.getInstance().actions.get(name);
    }
    
    public static GameObject getAgentByName(String name) {
        return Model.getInstance().agents.get(name);
    }
    
    public static ArrayList<Team> getTeamList() {
        return (ArrayList<Team>) Model.getInstance().teams.clone();
    }

    public static GameMap getCurrentMap() {
        return Model.instance.map;
    }
    
    public static Painter getPainter(int index) {
        return Model.getInstance().painters.get(index);
    }

    public static long getGameStart() {
        return Model.instance.gameStart;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Agent movement testing">
    
    public static void setCurrnetChunk(Chunk c) {
        instance.chunk = c;
    }
    
    public static Chunk getCurrnetChunk() {
        return instance.chunk;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Map setup">
    
    /**
     * Method proceduraly generates map using pased arguments
     * @param obst - number of obsticles
     * @param res - resource count
    */
    public static void setNewMap(int obst, int res)
    {
        Model.setNewMap(MapInterface.DEFAULT_WIDTH, MapInterface.DEFAULT_HEIGHT,
                obst, Model.MIN_VERTICES_COUNT, Model.MAX_VERTICES_COUNT,
                Model.VERTEX_RADIUS, res, Model.PREFERENCE);                
    }
    
    /**
     * Method proceduraly generates map using pased arguments
     * @param obst - number of obsticles
     * @param minv - minimal number of vertices per obsticle
     * @param maxv - maximal number of vertices per obsticle
     * @param radv - radius of vertices i.e. maximal distance of vertex from
     *               middle point on X-axis or Y-axis
     * @param res - resource count
     * @param pref - generator preference of one type of resource over an other
     *               while initial state of map is generated; otherwise equal
     *               chance; fale = food is prefered, true = watter is prefered
    */
    public static void setNewMap(int obst, int minv,
            int maxv, int radv, int res, boolean pref)
    {
        Model.setNewMap(MapInterface.DEFAULT_WIDTH, MapInterface.DEFAULT_HEIGHT,
                                            obst, minv, maxv, radv, res, pref);
    }
    
    /**
     * Method proceduraly generates map using pased arguments
     * @param w - width of the overall map
     * @param h - height of the overall map
     * @param obst - number of obsticles
     * @param minv - minimal number of vertices per obsticle
     * @param maxv - maximal number of vertices per obsticle
     * @param radv - radius of vertices i.e. maximal distance of vertex from
     *               middle point on X-axis or Y-axis
     * @param res - resource count
     * @param pref - generator preference of one type of resource over an other
     *               while initial state of map is generated; otherwise equal
     *               chance; fale = food is prefered, true = watter is prefered
    */
    public static void setNewMap(int w, int h,int obst, int minv, int maxv,
                                                int radv, int res, boolean pref)
    {
        Model.getInstance().CreateMap(w, h, obst, minv, maxv, radv, res, pref);
    }
        
    public static void addTeam(Team team) {
        if (team == null) {
            return;
        }
        Model.instance.teams.add(team);
    }
    
    public static boolean createNewAgent(Team team) {
        if (team == null) return false;
        
        return Model.createNewAgent(team, null);
    }
    
    public static boolean createNewAgent(Team team, String name) {
        if (team == null) return false;
        
        if (name == null || name.isEmpty()) {
            name = AgentInterface.DEFAULT_AGENT_NAME + team.getColor().getRGB() 
                      + team.getMemberCount();
        }
        
        Model.getInstance().createAgent(team, name);
        return true;
    }    
   
    /* Private methods for underlying operations */

    /**
     * Method proceduraly generates map using pased arguments
     * @param minv - minimal number of vertices per obsticle
     * @param maxv - maximal number of vertices per obsticle
     * @param radv - radius of vertices i.e. maximal distance of vertex from
     *               middle point on X-axis or Y-axis
     * @return randomly created obsticle sutable to placement ito map
    */
    private static Obsticle generateObsticle(int minv, int maxv, int radv) {
        int vertecisCount, median, x, y;
        Shape s = new Shape();
        
        vertecisCount = Model.rand.nextInt(maxv + 1);
        if (vertecisCount < minv) vertecisCount = minv;
        median = vertecisCount / 2;
        
        for (int i = 0; i < vertecisCount; i++) {
            x = Model.rand.nextInt(radv);
            y = Model.rand.nextInt(radv);
            
            if (i >= median) {
                if (Model.rand.nextBoolean()) {
                    x = x * -1;
                }
            }
            if (Model.rand.nextBoolean()) {
                    y = y * -1;
            }
            s.addVertex(x, y);
        }

        Obsticle obs = new Obsticle();
        obs.setNewShape(s);
        obs.setPainter(Model.getPainter(POLYGON_PAINTER_INDEX));
        return obs;
    }
    
    // </editor-fold>
    
    private void CreateMap(int w, int h,int obst, int minv, int maxv,
                                                int radv, int res, boolean pref)
    {
        // create new map
        this.map = new GameMap(w, h);
        /*
        int chunkSize = 75;
        int horizontal, vertical, modulo;
        horizontal = this.map.getMapWidth() / chunkSize;
        modulo = this.map.getMapWidth() % chunkSize;
        if (modulo != 0) {
            horizontal++;
        }
        vertical = this.map.getMapHeight()/ chunkSize;
        modulo = this.map.getMapHeight()/ chunkSize;
        if (modulo != 0) {
            vertical++;
        }
        
        for (int i = 0; i < horizontal; i++) {
            for (int j = 0; j < vertical; j++) {
                Rectangle r = new Rectangle(i*chunkSize, j*chunkSize, chunkSize, chunkSize);
                DrawableGameObject o = new RectangleObject(r);
                o.setPainter(Model.getPainter(Model.RECTANGULAR_PAINTER_INDEX));
                this.map.addGameObject(o);
            }
        } // */
        
        //*
        // prepare anthils for given teams
        for (Team team : this.teams) {
            AntHill hill = new AntHill();
            hill.setName("Anthill " + team.getColor().toString());
            this.agents.put(hill.getName(), hill);
            team.setAnthillObject(hill);
            
            hill.setPainter(Model.getPainter(RECTANGLE_PAINTER_INDEX));
            
            boolean added = false;
            do {
                int x = Model.rand.nextInt(this.map.getMapWidth());
                int y = Model.rand.nextInt(this.map.getMapHeight());
                hill.setPosition(x, y);
                added = this.map.addGameObject(hill);
            } while (!added);
            
            for (Agent ag : team.getMemberList()) {
                this.map.addGameObject(ag, hill.getPosition());
            }
        }  //  */    
        // create obsticles
        for(int i = 0; i < obst; i++) {
            Obsticle obs = Model.generateObsticle(minv, maxv, radv);
            boolean added = false;
            do {
                int x = Model.rand.nextInt(this.map.getMapWidth());
                int y = Model.rand.nextInt(this.map.getMapHeight());
                obs.setPosition(x, y);
                added = this.map.addGameObject(obs);
            } while (!added);
        }
        // creat resources
        int limit = (int) Math.floor(res * PROBABILITY);
        for(int i = 0; i < res; i++) {           
            ResourceTypes type = ((i >  limit)? ResourceTypes.food : ResourceTypes.water);
            ResourceBlock r = new ResourceBlock(type);
            
            r.setPainter(Model.getPainter(RECTANGLE_PAINTER_INDEX));
            
            boolean added = false;
            do {
                int x = Model.rand.nextInt(this.map.getMapWidth());
                int y = Model.rand.nextInt(this.map.getMapHeight());
                r.setPosition(x, y);
                added = this.map.addGameObject(r);
            } while (!added);
        } // */
       
    }
    
    private void initPainters() {
        Painter p = new AgentPainter();
        this.painters.add(AGENT_PAINTER_INDEX, p);
        p = new ElipticalPainter();
        this.painters.add(ELIPTICAL_PAINTER_INDEX, p);
        p = new PolygonPainter();
        this.painters.add(POLYGON_PAINTER_INDEX, p);
        p = new RectanglePainter();
        this.painters.add(RECTANGULAR_PAINTER_INDEX, p);
        p = new RectangularPainter();
        this.painters.add(RECTANGLE_PAINTER_INDEX, p);
    }
    
    private void initActions() {
    }

    private boolean createAgent(Team team, String name) {
        Agent ag = new Agent();
        ag.setName(name);
        ag.setPainter(Model.getPainter(AGENT_PAINTER_INDEX));
        ag.setTeam(team);
        
        this.agents.put(name, ag);
        this.map.addGameObject(ag, team.getHill().getPosition());
        return true;
    }
    
    private void initGame() {
        this.gameStart = System.currentTimeMillis();
    }
}
