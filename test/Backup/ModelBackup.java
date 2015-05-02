/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment;
import Enviroment.EnviromentalMap.GameMapBackup;
import Enviroment.EnvObjects.Agents.Agent;
import Agents.Team.Team;
import Enviroment.EnvObjects.GameObjectBackup;
import java.util.Random;
import Enviroment.EnvObjects.Obsticles.Obsticle;
import Enviroment.EnvObjects.Resources.Resource;
import Enviroment.EnvObjects.Resources.ResourceBlock;
import Enviroment.EnvObjects.Resources.ResourceTypes;
import Enviroment.EnvObjects.ObjectParts.Shape;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author lennylinux
 */
public class ModelBackup {
    /* Private constants - default values */
    private static final int OBSTICLE_COUNT = 20;
    private static final int MIN_VERTICES_COUNT = 3;
    private static final int MAX_VERTICES_COUNT = 7;
    private static final int VERTEX_RADIUS = 200;
    private static final Double PROBABILITY = 0.6;
    // preference: false - food, true - watter
    private static final boolean PREFERENCE = false; 
    
    /* Class fields */
    private static final ModelBackup m = new ModelBackup();

    private GameMapBackup map;
    public final static Random rand = new Random();
    private final ArrayList<Obsticle> obsticles;
    private final ArrayList<ResourceBlock> resourceBlocks;
    private final ArrayList<Resource> resources;
    private final HashMap<String, Agent> agents;
    private final ArrayList<Team> teamList;
    
    /* Private auxiliary methods */ 
    
    /* Private Constructor - singleton pattern */
    private ModelBackup() {
        this.map = null;
        this.agents = new HashMap();
        this.obsticles = new ArrayList();
        this.resourceBlocks = new ArrayList();
        this.resources = new ArrayList();
        this.teamList = new ArrayList();
    }
    
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
        
        vertecisCount = ModelBackup.rand.nextInt(maxv + 1);
        if (vertecisCount < minv) vertecisCount = minv;
        median = vertecisCount / 2;
        
        for (int i = 0; i < vertecisCount; i++) {
            x = ModelBackup.rand.nextInt(radv);
            y = ModelBackup.rand.nextInt(radv);
            
            if (i >= median) {
                if (ModelBackup.rand.nextBoolean()) {
                    x = x * -1;
                }
            }
            if (ModelBackup.rand.nextBoolean()) {
                    y = y * -1;
            }
            s.addVertex(x, y);
        }

        return new Obsticle(s);
    }
    
    /* Public methods for ModelBackup acces */
    
    /**
     * Method proceduraly generates map using pased arguments
     * @param obst - number of obsticles
     * @param res - resource count
    */
    public static void setNewMap(int obst, int res)
    {
        ModelBackup.setNewMap(GameMapBackup.DEFAULT_WIDTH, GameMapBackup.DEFAULT_HEIGHT,
                obst, ModelBackup.MIN_VERTICES_COUNT,
                ModelBackup.MAX_VERTICES_COUNT, ModelBackup.VERTEX_RADIUS, res, ModelBackup.PREFERENCE);               
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
        ModelBackup.setNewMap(GameMapBackup.DEFAULT_WIDTH, GameMapBackup.DEFAULT_HEIGHT, obst,
                                                   minv, maxv, radv, res, pref);
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
        m.obsticles.clear();
        m.resourceBlocks.clear();
        m.resources.clear();
        
        m.map = new GameMapBackup(w, h);
        for(int i = 0; i < obst; i++) {
            Obsticle o = ModelBackup.generateObsticle(minv, maxv, radv);
            int x = ModelBackup.rand.nextInt(GameMapBackup.DEFAULT_WIDTH);
            int y = ModelBackup.rand.nextInt(GameMapBackup.DEFAULT_HEIGHT);
            o.setPosition(x, y);
            m.map.addGameObject(o);
            m.obsticles.add(o);
        }
        
        int limit = (int) Math.floor(res * PROBABILITY);
        for(int i = 0; i < res; i++) {           
            ResourceTypes type = ((i >  limit)? ResourceTypes.food : ResourceTypes.water);
            ResourceBlock r = new ResourceBlock(type);
            int x = ModelBackup.rand.nextInt(GameMapBackup.DEFAULT_WIDTH);
            int y = ModelBackup.rand.nextInt(GameMapBackup.DEFAULT_HEIGHT);
            r.setPosition(x, y);
            m.map.addGameObject(r);
            m.resourceBlocks.add(r);
        }
       
    }
    
    public static ArrayList<GameObjectBackup> getObjInArea(Rectangle aov) {
        return m.map.checkArea(aov);
    }
    
    public static Dimension getMapSize() {
        return m.map.getMapSize();
    }
    
    public static int getMapWidth() {
        return m.map.getMapWidth();
    }
    
    public static int getMapHeight() {
        return m.map.getMapHeight();
    }
    
    public static void addObjectToMap(GameObjectBackup obj, int x, int y) {
        m.map.addGameObject(obj, x, y);
    }
    
    public static void addResource(Resource r) {
        
    }
    
    public static void removeResource(Resource r) {
        
    }
    
    public static ArrayList<Resource> getResourceList() {
        return (ArrayList<Resource>) m.resources.clone();
    }
    
    public static void addResourceBlock(ResourceBlock r) {
        
    }
    
    public static void removeResourceBlock(ResourceBlock r) {
        
    }
    
    public static ArrayList<ResourceBlock> getResourceBlockList() {
        return (ArrayList<ResourceBlock>) m.resourceBlocks.clone();
    }
    
    public static void addTeam(Team t) {
        
    }
    
    public static void removeTeam(Team t) {
        
    }
    
    public static ArrayList<Team> getTeamList() {
        return (ArrayList<Team>) m.teamList.clone();
    }
    
    public static Agent getAgentByName(String name ) {                          // TODO  interface pro Agenty ...
        return null;
    }
    
}
