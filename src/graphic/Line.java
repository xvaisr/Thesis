/**
 * BP, anthill strategy game
 * Implements lines used for obstacles
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/03/09
 * @version 1
 * @file    graphic.Line.java
 */
package graphic;

import java.awt.Point;

/**
 * Implements lines used for obstacles
 * @author Vojtech Simetka
 *
 */
public class Line
{
	Point p1;
	Point p2;
	
	/**
	 * Creates line from point p1 to point p2
	 * @param p1 First point of the line
	 * @param p2 Second point of the line
	 */
	Line (Point p1, Point p2)
	{
		this.p1 = new Point(p1);
		this.p2 = new Point(p2);
	}
}
