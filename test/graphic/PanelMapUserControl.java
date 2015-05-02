/**
 * BP, anthill strategy game
 * Implements user's map control panel
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/26
 * @version 1
 * @file    graphic.PanelMapControll.java
 */
package graphic;

import Enviroment.Original.Model;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Point;

import javax.swing.Box;
import javax.swing.JPanel;

/**
 * Implements user's map control panel
 * @author Vojtech Simetka
 *
 */
public class PanelMapUserControl extends JPanel
{
	private ButtonAction move;
	private ButtonAction attack;
	private ButtonAction stop;
	private ButtonAction gather;
	private ButtonAction hold;

	/**
	 * Panel user constructor
	 * @param map Map reference
	 */
	public PanelMapUserControl(PanelMap map)
	{
		this.move = new ButtonAction(EnumActions.Move, map);
		this.stop = new ButtonAction(EnumActions.Stop, map);
		this.attack = new ButtonAction(EnumActions.Attack, map);
		this.gather = new ButtonAction(EnumActions.Gather, map);
		this.hold = new ButtonAction(EnumActions.Hold, map);
		
		map.setUserControlPanel(this);
		
		this.setLayout(new GridBagLayout());
		
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH;
	    
	    c.weightx = 2.0;
	    c.weighty = 1.0;
	    c.gridwidth = 2;
	    c.gridheight = 1;
	    c.gridx = 0;
	    c.gridy = 1;
	    this.add(new TextUpgrades("Upgrade status:"), c);
	    
	    c.weightx = 2.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 0;
	    c.gridy = 3;
	    this.add(new TextUpgrades(ButtonUpgrade.UpgradeType.Armor), c);
	    
	    c.weightx = 2.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 0;
	    c.gridy = 5;
	    this.add(new TextUpgrades(ButtonUpgrade.UpgradeType.Attack), c);
	    
	    c.weightx = 2.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 0;
	    c.gridy = 7;
	    this.add(new TextUpgrades(ButtonUpgrade.UpgradeType.Speed), c);
	    
	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;
	    c.gridy = 3;
	    this.add(new ButtonUpgrade(ButtonUpgrade.UpgradeType.Armor), c);
	    
	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;
	    c.gridy = 5;
	    this.add(new ButtonUpgrade(ButtonUpgrade.UpgradeType.Attack), c);
	    
	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;
	    c.gridy = 7;
	    this.add(new ButtonUpgrade(ButtonUpgrade.UpgradeType.Speed), c);
	    
	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;
	    c.gridy = 9;
	    this.add(new ButtonUpgrade(ButtonUpgrade.UpgradeType.NewAnt), c);

	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 3;
	    c.gridy = 1;
	    this.add(this.move, c);

	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 3;
	    c.gridy = 3;
	    this.add(this.stop, c);

	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 3;
	    c.gridy = 5;
	    this.add(this.attack, c);

	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 3;
	    c.gridy = 7;
	    this.add(this.gather, c);

	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 3;
	    c.gridy = 9;
	    this.add(this.hold, c);
	    
	    c.weightx = 2;
	    c.weighty = 0.2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 2;
	    this.add(Box.createGlue(), c);
	    
	    c.weightx = 2;
	    c.weighty = 0.2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 4;
	    this.add(Box.createGlue(), c);
	    
	    c.weightx = 2;
	    c.weighty = 0.2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 6;
	    this.add(Box.createGlue(), c);
	    
	    c.weightx = 2;
	    c.weighty = 0.2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 8;
	    this.add(Box.createGlue(), c);
	    
	    c.weightx = 2;
	    c.weighty = 0.2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 3;
	    this.add(new TextPrices(Model.getUserTeam().getArmourPrice()), c);
	    
	    c.weightx = 2;
	    c.weighty = 0.2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 5;
	    this.add(new TextPrices(Model.getUserTeam().getAttackPrice()), c);
	    
	    c.weightx = 2;
	    c.weighty = 0.2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 7;
	    this.add(new TextPrices(Model.getUserTeam().getSpeedPrice()), c);
	    
	    c.weightx = 2;
	    c.weighty = 0.2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 9;
	    this.add(new TextPrices(new Point(Model.getConfiguration().new_ant_food, Model.getConfiguration().new_ant_water)), c);
	    
	    this.hideButtons();
	}
	
	/**
	 * Hides all buttons
	 */
	public void hideButtons()
	{
		this.attack.hideButton();
		this.stop.hideButton();
		this.move.hideButton();
		this.gather.hideButton();
		this.hold.hideButton();
	}
	
	/**
	 * Shows all buttons
	 */
	public void showButtons()
	{
		this.attack.showButton();
		this.stop.showButton();
		this.move.showButton();
		this.gather.showButton();
		this.hold.showButton();
	}
	
	/**
	 * Activates attack button
	 */
	public void enableAttack()
	{
		this.attack.activate();
	}
	
	/**
	 * Deactivates attack button
	 */
	public void disableAttack()
	{
		this.attack.deactivate();
	}
	
	/**
	 * Activates move button
	 */
	public void enableMove()
	{
		this.move.activate();
	}

	/**
	 * Deactivates move button
	 */
	public void disableMove()
	{
		this.move.deactivate();
	}
	
	/**
	 * Activates gathering button
	 */
	public void enableGather()
	{
		this.gather.activate();
	}

	/**
	 * Deactivates gather button
	 */
	public void disableGather()
	{
		this.gather.deactivate();
	}
	
	/**
	 * Activates hold button
	 */
	public void enableHold()
	{
		this.hold.activate();
	}
	
	/**
	 * Deactivates hold button
	 */
	public void disableHold()
	{
		this.hold.deactivate();
	}
	
	/**
	 * Deactivates all buttons
	 */
	public void disableAll()
	{
		this.hold.deactivate();
		this.attack.deactivate();
		this.move.deactivate();
		this.gather.deactivate();
	}

	/**
	 * Toggles hold button
	 */
	public void toogleHold()
	{
		this.hold.toogle();
	}
}
