/**
 * BP, anthill strategy game
 * Implements record of an event
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/20
 * @version 1
 * @file    graphic.Record.java
 */
package graphic;

import java.util.Comparator;

/**
 * Implements record of an event
 * @author Vojtech Simetka
 *
 */
public class Record implements Comparator<Record>
{
	private final long time;
	private final int value;
	
	/**
	 * Record constructor
	 * @param time Time of the record
	 * @param value Value of the record
	 */
	Record(long time, int value)
	{
		this.time = time;
		this.value = value;
	}
	
	/**
	 * Gets time of the record
	 * @return Time of the record
	 */
	public long getTime()
	{
		return this.time;
	}
	
	/**
	 * Gets value of the record
	 * @return Value of the record
	 */
	public int getValue()
	{
		return this.value;
	}

	/**
	 * Compares two records
	 */
	public int compare(Record o1, Record o2)
	{
		return (int)(o1.time - o2.time);
	}
}
