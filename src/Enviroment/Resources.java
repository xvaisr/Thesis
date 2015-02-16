/**
 * BP, anthill strategy game
 * Implements resources
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/25
 * @version 1
 * @file    graphic.Record.java
 */
package Enviroment;

/**
 * Implements resources
 * @author Vojtech Simetka
 *
 */
public class Resources {
	private final int x;
	private final int y;
	private int amount;
	private boolean selected;
	private boolean visible = false;
	
	/**
	 * Resources constructor
	 * @param x Coordinate x
	 * @param y Coordinate y
	 * @param amount Number of resources
	 */
	protected Resources(int x, int y, int amount)
	{
		this.x = x;
		this.y = y;
		this.amount = amount;
		this.selected = false;
	}

	/**
	 * Resources constructor
	 * @param resource Resources to be duplicated
	 */
	public Resources(Resources resource)
	{
		this.x = resource.x;
		this.y = resource.y;
		this.amount = resource.amount;
		this.selected = false;
		this.visible = true;
	}

	/**
	 * Returns resource's coordinate x
	 * @return Resource's coordinate x
	 */
	public final int getY()
	{
		return this.y;
	}
	
	/**
	 * Returns resources's coordinate y
	 * @return Resources's coordinate y
	 */
	public final int getX()
	{
		return this.x;
	}
	
	/**
	 * Gets amount of resources in the resource field 
	 * @return Amount of resources in the resource field
	 */
	public final int getAmout()
	{
		return this.amount;
	}
	
	/**
	 * Sets new amount of resources
	 * @param amount New amount of the resources
	 */
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
	
	/**
	 * Returns true if the resource is selected
	 * @return True if the resource is selected
	 */
	public boolean isSelected()
	{
		return this.selected;
	}
	
	/**
	 * Deselects resource
	 */
	public void deselect()
	{
		this.selected = false;
	}
	
	/**
	 * Selects resource
	 */
	public void select()
	{
		this.selected = true;
	}

	/**
	 * Sets the resource as indirectly visible
	 */
	public void indirectlyVisible()
	{
		this.visible  = false;
	}
	
	/**
	 * Sets the resource as directly visible
	 */
	public void directlyVisible()
	{
		this.visible = true;
	}
	
	/**
	 * Returns true if the resource is directly visible
	 * @return True if the resource is directly visible
	 */
	public boolean isDirectlyVisible()
	{
		return this.visible;
	}
}
