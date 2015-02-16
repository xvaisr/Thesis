/**
 * BP, anthill strategy game
 * Implements coordinates system used in this game
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/28
 * @version 1
 * @file    graphic.Configuration.java
 */
package graphic;

/**
 * Implements coordinates system used in this game
 * @author Vojtech Simetka
 *
 */
public class Coordinates
{
	public double x;
	public double y;
	
	/**
	 * Coordinates constructor
	 * @param x coordinate x
	 * @param y coordinate y
	 */
	protected Coordinates(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
}
