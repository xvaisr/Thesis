/**
 * BP, anthill strategy game
 * Button used for in-game upgrades
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/15
 * @version 1
 * @file    graphic.ButtonUpgrade.java
 */
package graphic;

import Enviroment.Original.Model;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Button used for in-game upgrades
 * @author Vojtech Simetka
 *
 */
public class ButtonUpgrade extends Button
{	
	/**
	 * Button upgrade types enumerator
	 */
	protected enum UpgradeType
	{
		Armor,
		Attack,
		Speed,
		NewAnt
	}
	
	private final UpgradeType type;
	
	/**
	 * ButtonUpgrade constructor
	 * @param type
	 */
	public ButtonUpgrade(UpgradeType type)
	{
		super("", ButtonUpgrade.createListener(type));
		
		this.type = type;
		
		// Sets label
		if (type == UpgradeType.NewAnt)
			super.setLabel("New Ant");
		else
			super.setLabel("Upgrade");
		
		this.addComponentListener(this);
		this.addMouseListener(this);
	}

	/**
	 * Paint component method
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		
		Graphics2D g2d = (Graphics2D)g;
		
		// If the upgrade can not be done, paint grey
		super.setPaintGrey(this.isPaintGray());
		
		super.paintComponent(g2d);
	}

	/**
	 * Decides if button should be painted gray
	 * @return True if button should be painted gray
	 */
	private boolean isPaintGray()
	{
		switch(this.type)
		{
		case Armor:
			if (!Model.getUserTeam().isArmorUpgradable())
				return true;
			
			return false;
		case Attack:
			if (!Model.getUserTeam().isAttackUpgradable())
				return true;
			
			return false;
		case Speed:
			if (!Model.getUserTeam().isSpeedUpgradable())
				return true;
			
			return false;
		case NewAnt:
			if (!Model.getEnvironment().isNewAntCreatable(Model.getUserTeam()))
				return true;
			
			return false;
			
		default:
			return false;
			
		}
	}
	
	/**
	 * Creates adequate action listener and returns it based on type
	 * @param type Type of the action
	 * @return Action listener
	 */
	private static ButtonActionListener createListener(UpgradeType type)
	{
		switch(type)
		{
		case Armor:
			return new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					Model.getUserTeam().upgradeArmor();
				}	
	        };
		case Attack:
			return new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					Model.getUserTeam().upgradeAttack();
				}	
	        };
		case Speed:
			return new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					Model.getUserTeam().upgradeSpeed();
				}	
	        };
		case NewAnt:
			return new ButtonActionListener()
			{
				public void actionToBePerformed()
				{
					Model.getEnvironment().createNewAnt(Model.getUserTeam());
				}	
			};
		default:
			return null;
		
		}
	}

}
