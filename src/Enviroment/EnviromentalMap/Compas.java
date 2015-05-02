/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnviromentalMap;
import Enviroment.Model;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author lennylinux
 */
public class Compas {
    
    public static String N = "N";   
    public static String S = "S";
    public static String W = "W";
    public static String E = "E";
    public static String NW = "NW";
    public static String NE = "NE";
    public static String SW = "SW";
    public static String SE = "SE";
    
    private static Compas compas = new Compas();
    private final HashMap<String, Point> directions;
    private final HashMap<Point, String> names;
    private  final ArrayList<Point> dirList;
    
    private Compas() {
        this.directions = new HashMap();
        this.names = new HashMap();
        this.dirList = new ArrayList();
        
        this.init();
    }
    
    private void init() {
        Point n, s, w, e, nw, ne, sw, se;
        n = new Point(0, 1);
        s = new Point(0, -1);
        w = new Point(-1, 0);
        e = new Point(1, 0);
        ne = new Point(1, 1);
        nw = new Point(-1, 1);
        se = new Point(1, -1);
        sw = new Point(-1, -1);
        
        this.directions.put(N, n);
        this.directions.put(S, s);
        this.directions.put(W, w);
        this.directions.put(E, e);
        this.directions.put(NE, ne);
        this.directions.put(NW, nw);
        this.directions.put(SE, se);
        this.directions.put(SW, sw);
        
        this.names.put(n, N);
        this.names.put(s, S);
        this.names.put(w, W);
        this.names.put(e, E);
        this.names.put(nw, NW);
        this.names.put(ne, NE);
        this.names.put(se, SE);
        this.names.put(sw, SW);
        
        this.dirList.addAll(this.names.keySet());
    }
    
    public static Point getDirectionByName(String direction) {
        return new Point(compas.directions.get(direction));
    }
    
    public static String getDirectionName(Point cp, Point np) {
        int x, y;
        x = np.x - cp.x;
        if (x < 0) x = -1;
        if (x > 0) x = 1;
        
        y = np.y - cp.y;
        if (y < 0) y = -1;
        if (y > 0) y = 1;
        
        Point p = new Point(x, y);
        return compas.names.get(p);
    }
    
    public static Point getRandomDirection() {
        int index = Model.rand.nextInt(compas.directions.size());
        return compas.dirList.get(index);
    }
}
