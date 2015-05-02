/**
 * BP, anthill strategy game
 * Custom button used in the game
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/15
 * @version 1
 * @file    graphic.ButtonAction.java
 */

package graphic;

/**
 * Buttons used in the game
 * @author Vojtech  Simetka
 *
 */
public class ButtonAction extends Button
{

	/**
	 * Action constructor
	 * @param type Type of the action
	 * @param map Map reference
	 */
	public ButtonAction(EnumActions type, PanelMap map)
	{
		super(type.toString() + "(" + type.toString().charAt(0) + ")", ButtonAction.createListener(type, map));
	}
	
	/**
	 * Creates adequate action listener
	 * @param type Type of the action
	 * @param map Map reference
	 * @return Adequate action listener
	 */
	private static ButtonActionListener createListener(EnumActions type, final PanelMap map)
	{
		switch(type)
		{
		case Attack:
			return new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					map.setAttack();
				}
			};
		case Gather:
			return new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					map.setGather();
				}
			};
		case Hold:
			return new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					map.setHold();
				}
			};
		case Move:
			return new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					map.setMove();
				}
			};
		case Stop:
			return new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					map.stopSelectedAgents();
				}
			};
		default:
			return null;
		}
	}
}
