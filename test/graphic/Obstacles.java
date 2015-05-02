/**
 * BP, anthill strategy game
 * Obstacle representation
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/03/13
 * @version 1
 * @file    graphic.Obstacles.java
 */
package graphic;

import java.awt.Point;
import java.util.ArrayDeque;

/**
 * Obstacle representation
 * @author Vojtech Simetka
 *
 */
public class Obstacles
{
	private final int x[];
	private final int y[];
	private final Point points[];
	private final Point min;
	private final Point max;
	private final boolean area[][];
	
	// Creates obstacle from points
	public Obstacles(Point... points)
	{
		this.x = new int[points.length];
		this.y = new int[points.length];
		this.min = new Point(points[0].x, points[0].y);
		this.max = new Point(points[0].x, points[0].y);
		this.points = points;
		
		// Stores x and y coordinates separately and creates determines minimal and maximal points in both directions 
		for (int i = 0; i < points.length; i++)
		{
			if (this.min.x > points[i].x)
				this.min.x = points[i].x;
			
			else if (this.max.x < points[i].x)
				this.max.x = points[i].x;

			if (this.min.y > points[i].y)
				this.min.y = points[i].y;
			
			else if (this.max.y < points[i].y)
				this.max.y = points[i].y;
			
			this.x[i] = points[i].x;
			this.y[i] = points[i].y;
		}
		
		// Allocates enough space for the obstacle area
		this.area = new boolean[max.y - min.y+2][max.x - min.x+2];

		// Uses line inversion fill
		ArrayDeque<Line> ls = new ArrayDeque<Line>();
		
		// Puts together the list of line segments
		for(int a = 0; a < points.length; a++)
		{
			Point p2;
			Point p1 = points[a];
			
			// Gets second point
			if(a < points.length-1)
				p2 = points[a+1];
			
			// Second point of the last segment is first point
			else
				p2 = points[0]; 
		
			// Checks for horizontal line
			if(p1.y != p2.y) 
			{
				// Add the line to the list, sort points by y
				if(p1.y < p2.y)
					ls.push(new Line(p1, p2));
				else
					ls.push(new Line(p2, p1));
			}
		}
		
		// Cycles through all segments
		for(Line line: ls) 
		{
			// For every row
			for(int b = line.p1.y; b < line.p2.y; b++) 
			{
				// Line parameter
				double a = (double)(b-line.p1.y)/(line.p2.y-line.p1.y);
				
				// X intersection
				int I = (int) (a*(line.p2.x-line.p1.x)+line.p1.x+0.5);
		
				// Invert the mask
				for(int c = I; c < this.max.x; c++) 
				{
					if(area[b-this.min.y][c-this.min.x] == false)
					   area[b-this.min.y][c-this.min.x] = true;
					else
					   area[b-this.min.y][c-this.min.x] = false;
				}
			}
		}
		
		// Adds boundary lines to the area map
		for(int a = 0; a < points.length; a++)
		{
			// All points except for the last one, second point is after first point
			if(a < points.length-1)
				this.removeLine(points[a], points[a+1]);
			
			// Last point, second point is first in list
			else
				this.removeLine(points[a], points[0]);
		}
	}

	/**
	 * Gets Y coordinates of obstacle
	 * @return Coordinates Y of obstacle
	 */
	public int[] getYs()
	{
		return this.y;
	}
	
	/**
	 * Gets X coordinated of obstacle
	 * @return Coordinates X of obstacle
	 */
	public int[] getXs()
	{
		return this.x;
	}
	
	/**
	 * Gets number of points for rectangle
	 * @return Rectangle's points count
	 */
	public int getLength()
	{
		return this.y.length;
	}
	
	/**
	 * Decides if point p is inside rectangle
	 * @param p Point p for which we are determining whether it lies within obstacle
	 * @return True if point p lies within obstacle
	 */
	public boolean isInside(Point p)
	{
		if (p.x > min.x &&
			p.x < max.x &&
			p.y > min.y &&
			p.y < max.y)
		{
			return area[p.y-min.y][p.x-min.x];
		}
		return false;
	}
	
	/**
	 * Adds line to obstacle map, implements Bresenham line rasterization algorithm
	 * @param p1 Line's starting point
	 * @param p2 Line's ending point
	 */
	private void removeLine(Point p1, Point p2)
	{
		// Stores both points
		int y1 = p1.y - this.min.y;
		int y2 = p2.y - this.min.y;
		int x1 = p1.x - this.min.x;
		int x2 = p2.x - this.min.x;

		// There is no point of drawing line with starting point same as ending
		if ( x1 == x2 && y1 == y2 )
			return;
		
		// Calculates delta x and y
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		
		// Swap of x and y, slope of the line is greater then 1
		boolean xy_swap = (dy > dx);
		if (xy_swap)
		{
			// Swaps x1 with y1
			int tmp = x1;
			x1 = y1;
			y1 = tmp;
			
			// Swaps x2 with y2
			tmp = x2;
			x2 = y2;
			y2 = tmp;
			
			// Swaps dx with dy
			tmp = dx;
			dx = dy;
			dy = tmp;
		}
		
		// Starting point's x should be greater then ending point's, swaps P1 for P2
		if (x1 > x2)
		{
			// Swap x1 with x2
			int tmp = x1;
			x1 = x2;
			x2 = tmp;
			
			// Swap y1 with y2
			tmp = y1;
			y1 = y2;
			y2 = tmp;
		}
		
		// Direction on y axis
		int step_y;
		if (y1 > y2)
			step_y = -1;
		else
			step_y = 1;
		
		// Constants for Bresenham algorithm
		int k1 = 2 * dy;
		int k2 = 2 * (dy - dx);
		int p  = 2 * dy - dx;
		
		// Initialization of x and y
		int x = x1;
		int y = y1;
		
		// Rendering cycle
		while (x <= x2)
		{
			if (!xy_swap)
			{
				this.area[y][x] = false;
				this.area[y+1][x] = false;
				this.area[y][x+1] = false;
				this.area[y+1][x+1] = false;
				if (y > 0)
				{
					this.area[y-1][x] = false;
					this.area[y-1][x+1] = false;
				}
				if (x > 0)
				{
					this.area[y][x-1] = false;
					this.area[y+1][x-1] = false;
				}
				if (x > 0 && y > 0)
				{
					this.area[y-1][x-1] = false;
				}
			}
			else
			{
				this.area[x][y] = false;
				this.area[x+1][y] = false;
				this.area[x][y+1] = false;
				this.area[x+1][y+1] = false;
				if (x > 0)
				{
					this.area[x-1][y] = false;
					this.area[x-1][y+1] = false;
				}
				if (y > 0)
				{
					this.area[x][y-1] = false;
					this.area[x+1][y-1] = false;
				}
				if (x > 0 && y > 0)
				{
					this.area[x-1][y-1] = false;
				}
			}
			
			x++;
			if (p < 0)
				p += k1;
			
			else
			{
				p += k2;
				y += step_y;
			}
		}
	}

	/**
	 * Gets the obstacle area map
	 * @return The obstacle area map
	 */
	public boolean[][] getArea()
	{
		return this.area;
	}

	/**
	 * Returns point with minimal x and y coordinates on this obstacle
	 * @return point with minimal x and y coordinates on this obstacle
	 */
	public Point getMin()
	{
		return this.min;
	}
	
	/**
	 * Returns point with maximal x and y coordinates on this opstacle
	 * @return point with maximal x and y coordinates on this opstacle
	 */
	public Point getMax()
	{
		return this.max;
	}
	
	/**
	 * Gets all vertexes of this obstacle
	 * @return All vertexes of the obstacle
	 */
	public Point[] getPoints()
	{
		return this.points;
	}
}
