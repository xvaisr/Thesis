/**
 * BP, anthill strategy game
 * Displays current upgrades for the team
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/20
 * @version 1
 * @file    graphic.TextPrices.java
 */
package graphic;

import Enviroment.Model;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Displays current upgrades for the team
 * @author Vojtech Simetka
 *
 */
public class TextUpgrades extends JPanel
{
	private ButtonUpgrade.UpgradeType type = ButtonUpgrade.UpgradeType.NewAnt;
	private String label;
	
	/**
	 * Text prices constructor
	 * @param type Type of the upgrade
	 */
	public TextUpgrades(ButtonUpgrade.UpgradeType type)
	{
		this.type = type;
	}
	
	/**
	 * Text prices constructor
	 * @param label Label of the price
	 */
	public TextUpgrades(String label)
	{
		this.label = label;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.black);
		
		switch(this.type)
		{
		case Armor:
			g.drawString("Armor: " + Model.getUserTeam().getArmor(), 10, this.getHeight()/2);
			break;
		case Attack:
			g.drawString("Attack: "+ Model.getUserTeam().getAttack(), 10, this.getHeight()/2);
			break;
		case Speed:
			g.drawString("Speed: " + Model.getUserTeam().getSpeed(), 10, this.getHeight()/2);
			break;
		default:
			g.drawString(this.label, 10, this.getHeight()/2);
			break;
		}
	}
}
