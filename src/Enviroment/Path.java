/**
 * BP, anthill strategy game
 * Stores series of points as a path
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/20
 * @version 1
 * @file    graphic.Path.java
 */
package Enviroment;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * Stores series of points as a path
 * @author Vojtech Simetka
 *
 */
public class Path implements Comparable<Path>
{
	private double distance = 0;
	private final ArrayDeque<Point> path;
	
	/**
	 * Path constructor
	 * @param points Series of points for the path
	 */
	Path(Point... points)
	{	
		this.path = new ArrayDeque<Point>(Arrays.asList(points));
		
		for (int i = 0; i < points.length - 1; i++)
		{
			int dx = points[i].x - points[i+1].x;
			int dy = points[i].y - points[i+1].y;
			this.distance += Math.sqrt(dx * dx + dy * dy);
		}
	}
	/**
	 * Merges two paths into one
	 * @param first_path First path
	 * @param second_path Second path
	 */
	Path(Path first_path, Path second_path)
	{
		if (first_path.getPath().getFirst() == first_path.path.getLast())
			this.path = new ArrayDeque<Point>(second_path.path);
		
		else
		{
			this.path = new ArrayDeque<Point>(first_path.getPath());
			this.path.removeLast();
			this.path.addAll(second_path.getPath());
		}
		
		this.distance = first_path.getDistance() + second_path.getDistance();
	}

	/**
	 * Returns all points of the path
	 * @return All points of the path
	 */
	public final ArrayDeque<Point> getPath()
	{
		return new ArrayDeque<Point>(this.path);
	}
	
	/**
	 * Gets path length
	 * @return Length of the path
	 */
	public final double getDistance()
	{
		return this.distance;
	}

	/**
	 * Compares two paths
	 */
	public int compareTo(Path o)
	{
		if (this.distance > o.distance)
			return 1;
		else if (this.distance == o.distance)
			return 0;
		else
			return -1;
	}
}
