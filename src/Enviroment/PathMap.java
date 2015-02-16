/**
 * BP, anthill strategy game
 * Path HashMap, stores shortest paths between two points
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/20
 * @version 1
 * @file    graphic.Path.java
 */
package Enviroment;

import java.awt.Point;
import java.util.HashMap;

/**
 * Path HashMap, stores shortest paths between two points
 * @author Vojtech Simetka
 *
 */
public class PathMap extends HashMap<Point, HashMap<Point, Path>>
{
	/**
	 * Puts new value to the map, if it exists it stores one that is closer
	 * @param key1 Starting point
	 * @param key2 Ending point
	 * @param value Know path
	 */
	public void put(Point key1, Point key2, Path value)
	{
		HashMap<Point, Path> current = this.get(key1);
		
		// If there is no path added, this creates inner HashMap
		if (current == null)
		{
		    current = new HashMap<Point, Path>();
		    super.put(key1, current);
		}
		
		Path stored_path = this.get(key1).get(key2);
		if (stored_path != null)
		{
			// New path is closer, remove old path
			if (stored_path.getDistance() > value.getDistance())
				current.remove(key2);
			
			// Closest path is already stored
			else
				return;
		}
		// Add new path
		current.put(key2, value);
	}
	
	/**
	 * Gets best known path from star_point to final_point
	 * @param start_point Starting point
	 * @param final_point Ending point
	 * @return Known minimal path from start_point to final_point
	 */
	public final Path get(Point start_point, Point final_point)
	{
		HashMap<Point, Path> current = this.get(start_point);
		if (current == null)
			return null;
		return this.get(start_point).get(final_point);
	}
}
