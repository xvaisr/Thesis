/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment;
import Enviroment.EnvObjects.EnumGameObjects;
import java.util.Random;
import Enviroment.EnvObjects.GameObject;
import Enviroment.EnvObjects.Shape;

/**
 *
 * @author lennylinux
 */
public class Model {
    /* Private constants - default values */
    private static final int OBSTICLE_COUNT = 20;
    private static final int MIN_VERTICES_COUNT = 3;
    private static final int MAX_VERTICES_COUNT = 7;
    private static final int VERTEX_RADIUS = 10;
    // preference: false - food, true - watter
    private static final boolean PREFERENCE = false; 
    
    /* Class fields */
    private static final Model m = new Model();
    private GameMap map;
    public static Random rand;
    
    /* Private auxiliary methods */ 
    
    /* Private Constructor - singleton pattern */
    private Model() {
        m.map = null;
        rand = new Random();
    }
    
    /**
     * Method proceduraly generates map using pased arguments
     * @param minv - minimal number of vertices per obsticle
     * @param maxv - maximal number of vertices per obsticle
     * @param radv - radius of vertices i.e. maximal distance of vertex from
     *               middle point on X-axis or Y-axis
     * @return randomly created obsticle sutable to placement ito map
    */
    private GameObject generateObsticle(int minv, int maxv, int radv) {
        int vertecisCount, median, x, y;
        Shape s = new Shape();
        GameObject o = new GameObject(EnumGameObjects.obsticle);
        
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
        o.setNewShape(s);
        return o;
    }
    
    /* Public methods for Model acces */
    
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
     * @return void
    */
    public static void setNewMap(int obst, int minv,
            int maxv, int radv, int res, boolean pref)
    {
        m.map = new GameMap();
    }
    
    /**
     * Method proceduraly generates map using pased arguments
     * @param w - width of the overall map
     * @param h - height of the overall map
     * @param chunk - size of square edged map's chunks
     * @param obst - number of obsticles
     * @param minv - minimal number of vertices per obsticle
     * @param maxv - maximal number of vertices per obsticle
     * @param radv - radius of vertices i.e. maximal distance of vertex from
     *               middle point on X-axis or Y-axis
     * @param res - resource count
     * @param pref - generator preference of one type of resource over an other
     *               while initial state of map is generated; otherwise equal
     *               chance; fale = food is prefered, true = watter is prefered
     * @return void
    */
    public static void setNewMap(int w, int h, int chunk, int obst, int minv,
            int maxv, int radv, int res, boolean pref)
    {
        m.map = new GameMap(chunk, w, h);
       
    }
    
}
