/**
 * BP, anthill strategy game
 * Anthill information class
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/01/20
 * @version 1
 * @file    graphic.Team.java
 */
package graphic;

import Enviroment.Original.Model;
import java.awt.Color;
import java.awt.Point;

/**
 * Anthill information class
 * @author Vojtech Simetka
 *
 */
public class Team
{
	private int food;
	private int water;
	private Point anthill;
	private int armor;
	private int attack;
	private final Color color;
	private final AIDatabase.EnumAI ai;
	private float speed;
	private final EnumTeams team;
	private final String name;
	private final EnumAlliances alliance;
	
	private final Point armor_upgrade_cost = new Point();
	private final Point attack_upgrade_cost = new Point();
	private final Point speed_upgrade_cost = new Point();
	
	/**
	 * Team constructor
	 * @param team Team enumerator
	 */
	public Team(EnumTeams team)
	{
		this.food = 0;
		this.water = 0;
		this.armor = 0;
		this.attack = 0;
		this.color = Model.getConfiguration().getColor(team);
		this.ai = Model.getConfiguration().getAI(team);
		this.team = team;
		this.name = "anthill" + team.toString();
		this.alliance = Model.getConfiguration().getAlliance(team);
		this.speed = 1;
		
		// Calculates upgrade prices
		this.calculate_next_armor_upgrade_price();
		this.calculate_next_attack_upgrade_price();
		this.calculate_next_speed_upgrade_price();
	}

	/**
	 * Adds food for current team
	 * @param amount Amount of food to be added
	 */
	public void addFood(int amount)
	{
		this.food += amount;
		Model.getStatistics().addFood(this.team, Model.getAgentsMovenentThread().getGameLength(), amount);
	}

	/**
	 * Adds water for current team
	 * @param amount Amount of water to be added
	 */
	public void addWater(int amount)
	{
		this.water += amount;
		Model.getStatistics().addWater(this.team, Model.getAgentsMovenentThread().getGameLength(), amount);
	}

	/**
	 * Gets current food count
	 * @return Food count
	 */
	public int getFood()
	{
		return food;
	}

	/**
	 * Gets current water count
	 * @return Water count
	 */
	public int getWater()
	{
		return water;
	}

	/**
	 * Gets coordinates of teams anthill
	 * @return Coordinates of anthill for this team
	 */
	public Point getAnthill()
	{
		return anthill;
	}

	/**
	 * Sets anthill position for this team
	 * @param x Coordinate x
	 * @param y Coordinate y
	 */
	public void setAnthill(int x, int y)
	{
		this.anthill = new Point(x,y);
	}

	/**
	 * Gets attack bonus for this team
	 * @return Attack bonus for the team
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * Sets attack bonus for this team
	 * @param attack Attack bonus
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * Gets armor bonus for this team
	 * @return Armor bonus
	 */
	public int getArmor() {
		return armor;
	}

	/**
	 * Sets armor bonuse for this team
	 * @param armor Armor bonus
	 */
	public void setArmor(int armor) {
		this.armor = armor;
	}

	/**
	 * Decreases amount of the food by i
	 * @param i How much food should be removed
	 */
	public void removeFood(int i)
	{
		this.food -= i;
		Model.getStatistics().removeFood(this.team, Model.getAgentsMovenentThread().getGameLength(), i);
	}
	
	/**
	 * Decreases amount of the water
	 * @param amount How much water should be removes
	 */
	public void removeWater(int amount)
	{
		this.water -= amount;
		Model.getStatistics().removeWater(this.team, Model.getAgentsMovenentThread().getGameLength(), amount);
	}

	/**
	 * Gets team collor
	 * @return Color of the team
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Gets artificial intelligence of the team
	 * @return Artificial intelligence of the team
	 */
	public AIDatabase.EnumAI getAI()
	{
		return this.ai;
	}

	/**
	 * Gets current speed of team's ants
	 * @return Current speed of team's ants
	 */
	public float getSpeed()
	{
		return this.speed;
	}
	
	/**
	 * Increases team's speed
	 */
	public void increaseSpeed()
	{
		if (this.speed + 0.01 >= Model.getConfiguration().getSpeedCap())
			this.speed = Model.getConfiguration().getSpeedCap();
		else
			this.speed += 0.01;
	}

	/**
	 * Gets name of the anthill
	 * @return Name of the anthill
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Gets team of the anthill
	 * @return Team of the anthill
	 */
	public EnumTeams getTeam()
	{
		return this.team;
	}

	/**
	 * Gets alliance of the team
	 * @return Alliance of the team
	 */
	public EnumAlliances getAlliance()
	{
		return this.alliance;
	}
	
	/**
	 * Gets food cost of next armor upgrade
	 * @return Food cost of next armor upgrade
	 */
	public int getArmorUpgradePriceFood()
	{
		return this.armor_upgrade_cost.x;
	}

	/**
	 * Gets water cost of next armor upgrade
	 * @return Water cost of next armor upgrade
	 */
	public int getArmorUpgradePriceWater()
	{
		return this.armor_upgrade_cost.y;
	}

	/**
	 * Gets food cost of next attack upgrade
	 * @return Food cost of next attack upgrade
	 */
	public int getAttackUpgradePriceFood()
	{
		return this.attack_upgrade_cost.x;
	}
	
	/**
	 * Gets water cost of next attack upgrade
	 * @return water cost of next attack upgrade
	 */
	public int getAttackUpgradePriceWater()
	{
		return this.attack_upgrade_cost.y;
	}
	
	/**
	 * Gets food cost of next speed upgrade
	 * @return Food cost of next speed upgrade
	 */
	public int getSpeedUpgradePriceFood()
	{
		return this.speed_upgrade_cost.x;
	}

	/**
	 * Gets water cost of next speed upgrade
	 * @return water cost of next speed upgrade
	 */
	public int getSpeedUpgradePriceWater()
	{
		return this.speed_upgrade_cost.y;
	}

	/**
	 * If there is enough resources upgrades attack
	 */
	public void upgradeAttack()
	{
		synchronized(this)
		{
		if (this.isAttackUpgradable())
			{
				this.removeFood(this.getAttackUpgradePriceFood());
				this.removeWater(this.getAttackUpgradePriceWater());
				this.attack++;
				this.calculate_next_attack_upgrade_price();
				Model.getStatistics().addAttack(this.team, Model.getAgentsMovenentThread().getGameLength(), 1);
			}
		}
	}
	
	/**
	 * If there is enough resources upgrades armour
	 */
	public void upgradeArmor()
	{
		synchronized(this)
		{
		if (this.isArmorUpgradable())
			{
				this.removeFood(this.getArmorUpgradePriceFood());
				this.removeWater(this.getArmorUpgradePriceWater());
				this.armor++;
				this.calculate_next_armor_upgrade_price();
				Model.getStatistics().addArmor(this.team, Model.getAgentsMovenentThread().getGameLength(), 1);
			}
		}
	}
	
	/**
	 * If there is enough resources upgrades speed
	 */
	public void upgradeSpeed()
	{
		synchronized(this)
		{
		if (this.isSpeedUpgradable())
			{
				this.removeFood(this.getSpeedUpgradePriceFood());
				this.removeWater(this.getSpeedUpgradePriceWater());
				this.increaseSpeed();
				this.calculate_next_speed_upgrade_price();
				Model.getStatistics().addSpeed(this.team, Model.getAgentsMovenentThread().getGameLength(), 1);
			}
		}
	}
	
	/**
	 * Calculates next armour upgrade price
	 */
	private void calculate_next_armor_upgrade_price()
	{
		this.armor_upgrade_cost.x = this.armor + Model.getConfiguration().armor_upgrade_food;
		this.armor_upgrade_cost.y = this.armor + Model.getConfiguration().armor_upgrade_water;
	}
	
	/**
	 * Calculates next attack upgrade price
	 */
	private void calculate_next_attack_upgrade_price()
	{
		this.attack_upgrade_cost.x = this.attack + Model.getConfiguration().attack_upgrade_food;
		this.attack_upgrade_cost.y = this.attack + Model.getConfiguration().attack_upgrade_water;
	}
	
	/**
	 * Calculates next speed upgrade price
	 */
	private void calculate_next_speed_upgrade_price()
	{
		this.speed_upgrade_cost.x = (int)((this.speed-1) * 100) + Model.getConfiguration().speed_upgrade_food;
		this.speed_upgrade_cost.y = (int)((this.speed-1) * 100) + Model.getConfiguration().speed_upgrade_water;
	}

	/**
	 * Returns true if there is enough resources to upgrade armour
	 * @return True if there is enough resources to upgrade armour
	 */
	public boolean isArmorUpgradable()
	{
		if (this.food >= this.getArmorUpgradePriceFood() &&
			this.water >= this.getArmorUpgradePriceWater())
			return true;
		
		return false;
	}
	
	/**
	 * Returns true if there is enough resources to upgrade attack
	 * @return True if there is enough resources to upgrade attack
	 */
	public boolean isAttackUpgradable()
	{
		if (this.food >= this.getAttackUpgradePriceFood() &&
			this.water >= this.getAttackUpgradePriceWater())
			return true;
		
		return false;
	}
	
	/**
	 * Returns true if there is enough resources to upgrade speed
	 * @return True if there is enough resources to upgrade speed
	 */
	public boolean isSpeedUpgradable()
	{
		if (this.food >= this.getSpeedUpgradePriceFood() &&
			this.water >= this.getSpeedUpgradePriceWater() &&
			this.speed < Model.getConfiguration().getSpeedCap())
			return true;
		
		return false;
	}

	public Point getAttackPrice()
	{
		return this.attack_upgrade_cost;
	}

	public Point getSpeedPrice()
	{
		return this.speed_upgrade_cost;
	}
	
	public Point getArmourPrice()
	{
		return this.armor_upgrade_cost;
	}
}
