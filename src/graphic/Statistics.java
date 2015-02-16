/**
 * BP, anthill strategy game
 * Statistics collecting class
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/25
 * @version 1
 * @file    graphic.Statistics.java
 */
package graphic;

import Enviroment.Model;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Statistics collecting class
 * @author Vojtech Simetka
 *
 */
public class Statistics
{
	// World resource statistics
	private ArrayList<Record> food = new ArrayList<Record>();
	private ArrayList<Record> water = new ArrayList<Record>();
	
	// Agents count statistics
	private ArrayList<Record> ant_a = new ArrayList<Record>();
	private ArrayList<Record> ant_b = new ArrayList<Record>();
	private ArrayList<Record> ant_c = new ArrayList<Record>();
	private ArrayList<Record> ant_d = new ArrayList<Record>();
	
	// Team resources
	private ArrayList<Record> team_a_water = new ArrayList<Record>();
	private ArrayList<Record> team_a_food = new ArrayList<Record>();
	private ArrayList<Record> team_b_water = new ArrayList<Record>();
	private ArrayList<Record> team_b_food = new ArrayList<Record>();
	private ArrayList<Record> team_c_water = new ArrayList<Record>();
	private ArrayList<Record> team_c_food = new ArrayList<Record>();
	private ArrayList<Record> team_d_water = new ArrayList<Record>();
	private ArrayList<Record> team_d_food = new ArrayList<Record>();
	
	// Team upgrades
	private ArrayList<Record> team_a_armor = new ArrayList<Record>();
	private ArrayList<Record> team_a_attack = new ArrayList<Record>();
	private ArrayList<Record> team_a_speed = new ArrayList<Record>();
	private ArrayList<Record> team_b_armor = new ArrayList<Record>();
	private ArrayList<Record> team_b_attack = new ArrayList<Record>();
	private ArrayList<Record> team_b_speed = new ArrayList<Record>();
	private ArrayList<Record> team_c_armor = new ArrayList<Record>();
	private ArrayList<Record> team_c_attack = new ArrayList<Record>();
	private ArrayList<Record> team_c_speed = new ArrayList<Record>();
	private ArrayList<Record> team_d_armor = new ArrayList<Record>();
	private ArrayList<Record> team_d_attack = new ArrayList<Record>();
	private ArrayList<Record> team_d_speed = new ArrayList<Record>();
	
	// Total length of the game
	private long game_length = 0;
	private long team_a_action = 0;
	private long team_b_action = 0;
	private long team_c_action = 0;
	private long team_d_action = 0;
	private long team_a_reason_action = 0;
	private long team_b_reason_action = 0;
	private long team_c_reason_action = 0;
	private long team_d_reason_action = 0;
	
	/**
	 * Adds new record about addition of food
	 * @param time Time when new food was added
	 * @param value Amount of food added
	 */
	public void addFood(long time, int value)
	{
		this.food.add(new Record(time,value));
	}
	
	/**
	 * Adds new record about removal of food
	 * @param time Time when food was removed
	 * @param value Amount of food removed
	 */
	public void removeFood(long time, int value)
	{
		this.food.add(new Record(time,-value));
	}
	
	/**
	 * Adds new record about addition of water
	 * @param time Time when new water was added
	 * @param value Amount of water added
	 */
	public void addWater(long time, int value)
	{
		this.water.add(new Record(time, value));
	}
	
	/**
	 * Adds new record about removal of water
	 * @param time Time when water was removed
	 * @param value Amount of water removed
	 */
	public void removeWater(long time, int value)
	{
		this.water.add(new Record(time, -value));
	}
	
	/**
	 * Adds new record about new agent for team A
	 * @param time Time when new agent was added to the environment
	 */
	public void addAgentA(long time)
	{
		this.ant_a.add(new Record(time, 1));
	}
	
	/**
	 * Adds new record about new agent for team B
	 * @param time Time when new agent was added to the environment
	 */
	public void addAgentB(long time)
	{
		this.ant_b.add(new Record(time, 1));
	}

	/**
	 * Adds new record about new agent for team C
	 * @param time Time when new agent was added to the environment
	 */
	public void addAgentC(long time)
	{
		this.ant_c.add(new Record(time, 1));
	}

	/**
	 * Adds new record about new agent for team D
	 * @param time Time when new agent was added to the environment
	 */
	public void addAgentD(long time)
	{
		this.ant_d.add(new Record(time, 1));
	}
	
	/**
	 * Adds new record about dying of an agent from team A
	 * @param time Time when agent was killed
	 */
	public void removeAgentA(long time)
	{
		this.ant_a.add(new Record(time, -1));
	}

	/**
	 * Adds new record about dying of an agent from team B
	 * @param time Time when agent was killed
	 */
	public void removeAgentB(long time)
	{
		this.ant_b.add(new Record(time, -1));
	}
	
	/**
	 * Adds new record about dying of an agent from team C
	 * @param time Time when agent was killed
	 */
	public void removeAgentC(long time)
	{
		this.ant_c.add(new Record(time, -1));
	}
	
	/**
	 * Adds new record about dying of an agent from team D
	 * @param time Time when agent was killed
	 */
	public void removeAgentD(long time)
	{
		this.ant_d.add(new Record(time, -1));
	}
	
	/**
	 * Adds new record about collecting water for the team A
	 * @param time Time when the water was collected
	 * @param amount Amount of collected water
	 */
	public void addWaterTeamA(long time, int amount)
	{
		this.team_a_water.add(new Record(time,amount));
	}
	
	/**
	 * Adds new record about collecting water for the team B
	 * @param time Time when the water was collected
	 * @param amount Amount of collected water
	 */
	public void addWaterTeamB(long time, int amount)
	{
		this.team_b_water.add(new Record(time,amount));
	}
	
	/**
	 * Adds new record about collecting water for the team C
	 * @param time Time when the water was collected
	 * @param amount Amount of collected water
	 */
	public void addWaterTeamC(long time, int amount)
	{
		this.team_c_water.add(new Record(time,amount));
	}
	
	/**
	 * Adds new record about collecting water for the team D
	 * @param time Time when the water was collected
	 * @param amount Amount of collected water
	 */
	public void addWaterTeamD(long time, int amount)
	{
		this.team_d_water.add(new Record(time,amount));
	}
	
	/**
	 * Adds new record about spending water for the team A
	 * @param time Time when the water was spent
	 * @param amount Amount of spent water
	 */
	public void removeWaterTeamA(long time, int amount)
	{
		this.team_a_water.add(new Record(time,-amount));
	}
	
	/**
	 * Adds new record about spending water for the team B
	 * @param time Time when the water was spent
	 * @param amount Amount of spent water
	 */
	public void removeWaterTeamB(long time, int amount)
	{
		this.team_b_water.add(new Record(time,-amount));
	}

	/**
	 * Adds new record about spending water for the team C
	 * @param time Time when the water was spent
	 * @param amount Amount of spent water
	 */
	public void removeWaterTeamC(long time, int amount)
	{
		this.team_c_water.add(new Record(time,-amount));
	}

	/**
	 * Adds new record about spending water for the team D
	 * @param time Time when the water was spent
	 * @param amount Amount of spent water
	 */
	public void removeWaterTeamD(long time, int amount)
	{
		this.team_d_water.add(new Record(time,-amount));
	}
	
	/**
	 * Adds new record about collecting food for the team A
	 * @param time Time when the food was collected
	 * @param amount Amount of collected food
	 */
	public void addFoodTeamA(long time, int amount)
	{
		this.team_a_food.add(new Record(time,amount));
	}

	/**
	 * Adds new record about collecting food for the team B
	 * @param time Time when the food was collected
	 * @param amount Amount of collected food
	 */
	public void addFoodTeamB(long time, int amount)
	{
		this.team_b_food.add(new Record(time,amount));
	}

	/**
	 * Adds new record about collecting food for the team C
	 * @param time Time when the food was collected
	 * @param amount Amount of collected food
	 */
	public void addFoodTeamC(long time, int amount)
	{
		this.team_c_food.add(new Record(time,amount));
	}

	/**
	 * Adds new record about collecting food for the team D
	 * @param time Time when the food was collected
	 * @param amount Amount of collected food
	 */
	public void addFoodTeamD(long time, int amount)
	{
		this.team_d_food.add(new Record(time,amount));
	}

	/**
	 * Adds new record about spending food for the team A
	 * @param time Time when the food was spent
	 * @param amount Amount of spent food
	 */
	public void removeFoodTeamA(long time, int amount)
	{
		this.team_a_food.add(new Record(time,-amount));
	}

	/**
	 * Adds new record about spending food for the team B
	 * @param time Time when the food was spent
	 * @param amount Amount of spent food
	 */
	public void removeFoodTeamB(long time, int amount)
	{
		this.team_b_food.add(new Record(time,-amount));
	}

	/**
	 * Adds new record about spending food for the team C
	 * @param time Time when the food was spent
	 * @param amount Amount of spent food
	 */
	public void removeFoodTeamC(long time, int amount)
	{
		this.team_c_food.add(new Record(time,-amount));
	}

	/**
	 * Adds new record about spending food for the team D
	 * @param time Time when the food was spent
	 * @param amount Amount of spent food
	 */
	public void removeFoodTeamD(long time, int amount)
	{
		this.team_d_food.add(new Record(time,-amount));
	}
	
	/**
	 * Sets total game length
	 * @param length Total game length to be set
	 */
	public void setGameLength(long length)
	{
		this.game_length = length;
	}

	/**
	 * Gets total game length
	 * @return Total game length
	 */
	public long getGameLength()
	{
		return this.game_length;
	}
	
	/**
	 * Gets environment water records
	 * @return Environment water records
	 */
	public ArrayList<Record> getWater()
	{
		return this.water;
	}
	
	/**
	 * Gets environment food records
	 * @return Environment food records
	 */
	public ArrayList<Record> getFood()
	{
		return this.food;
	}
	
	/**
	 * Gets records about ants from team A
	 * @return Records about ants from team A
	 */
	public ArrayList<Record> getAntsA()
	{
		return this.ant_a;
	}

	/**
	 * Gets records about ants from team B
	 * @return Records about ants from team B
	 */
	public ArrayList<Record> getAntsB()
	{
		return this.ant_b;
	}
	
	/**
	 * Gets records about ants from team C
	 * @return Records about ants from team C
	 */
	public ArrayList<Record> getAntsC()
	{
		return this.ant_c;
	}

	/**
	 * Gets records about ants from team D
	 * @return Records about ants from team D
	 */
	public ArrayList<Record> getAntsD()
	{
		return this.ant_d;
	}
	
	/**
	 * Gets records about water from team A
	 * @return Records about water from team A
	 */
	public ArrayList<Record> getTeamAWater()
	{
		return this.team_a_water;
	}

	/**
	 * Gets records about water from team B
	 * @return Records about water from team B
	 */
	public ArrayList<Record> getTeamBWater()
	{
		return this.team_b_water;
	}

	/**
	 * Gets records about water from team C
	 * @return Records about water from team C
	 */
	public ArrayList<Record> getTeamCWater()
	{
		return this.team_c_water;
	}

	/**
	 * Gets records about water from team D
	 * @return Records about water from team D
	 */
	public ArrayList<Record> getTeamDWater()
	{
		return this.team_d_water;
	}

	/**
	 * Gets records about food from team A
	 * @return Records about food from team A
	 */
	public ArrayList<Record> getTeamAFood()
	{
		return this.team_a_food;
	}

	/**
	 * Gets records about food from team B
	 * @return Records about food from team B
	 */
	public ArrayList<Record> getTeamBFood()
	{
		return this.team_b_food;
	}

	/**
	 * Gets records about food from team C
	 * @return Records about food from team C
	 */
	public ArrayList<Record> getTeamCFood()
	{
		return this.team_c_food;
	}

	/**
	 * Gets records about food from team D
	 * @return Records about food from team D
	 */
	public ArrayList<Record> getTeamDFood()
	{
		return this.team_d_food;
	}

	/**
	 * Adds new record about spending food for the team
	 * @param team Team who spent the food
	 * @param gameLength Time of the record
	 * @param amount Amount of food spent
	 */
	public void removeFood(EnumTeams team, long gameLength, int amount)
	{
		switch(team)
		{
		case a:
			this.removeFoodTeamA(gameLength, amount);
			break;
		case b:
			this.removeFoodTeamB(gameLength, amount);
			break;
		case c:
			this.removeFoodTeamC(gameLength, amount);
			break;
		case d:
			this.removeFoodTeamD(gameLength, amount);
			break;
		default:
			break;
		}
	}

	/**
	 * Adds new record about spending water for the team
	 * @param team Team who spent the water
	 * @param gameLength Time of the record
	 * @param amount Amount of water spent
	 */
	public void removeWater(EnumTeams team, long gameLength, int amount)
	{
		switch(team)
		{
		case a:
			this.removeWaterTeamA(gameLength, amount);
			break;
		case b:
			this.removeWaterTeamB(gameLength, amount);
			break;
		case c:
			this.removeWaterTeamC(gameLength, amount);
			break;
		case d:
			this.removeWaterTeamD(gameLength, amount);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Adds new record about collecting water for the team
	 * @param team Team who collected the water
	 * @param gameLength Time of the record
	 * @param amount Amount of water collected
	 */
	public void addWater(EnumTeams team, long gameLength, int amount)
	{
		switch(team)
		{
		case a:
			this.addWaterTeamA(gameLength, amount);
			break;
		case b:
			this.addWaterTeamB(gameLength, amount);
			break;
		case c:
			this.addWaterTeamC(gameLength, amount);
			break;
		case d:
			this.addWaterTeamD(gameLength, amount);
			break;
		default:
			break;
		}
	}

	/**
	 * Adds new record about collecting food for the team
	 * @param team Team who collected the food
	 * @param gameLength Time of the record
	 * @param amount Amount of food collected
	 */
	public void addFood(EnumTeams team, long gameLength, int amount)
	{

		switch(team)
		{
		case a:
			this.addFoodTeamA(gameLength, amount);
			break;
		case b:
			this.addFoodTeamB(gameLength, amount);
			break;
		case c:
			this.addFoodTeamC(gameLength, amount);
			break;
		case d:
			this.addFoodTeamD(gameLength, amount);
			break;
		default:
			break;
		}
	}

	/**
	 * Adds new record about armor upgrade
	 * @param team Team who done the upgrade
	 * @param gameLength Time of the record
	 * @param amount Upgrade change
	 */
	public void addArmor(EnumTeams team, long gameLength, int amount)
	{

		switch(team)
		{
		case a:
			this.team_a_armor.add(new Record(gameLength, amount));
			break;
		case b:
			this.team_b_armor.add(new Record(gameLength, amount));
			break;
		case c:
			this.team_c_armor.add(new Record(gameLength, amount));
			break;
		case d:
			this.team_d_armor.add(new Record(gameLength, amount));
			break;
		default:
			break;
		}
	}

	/**
	 * Adds new record about attack upgrade
	 * @param team Team who done the upgrade
	 * @param gameLength Time of the record
	 * @param amount Upgrade change
	 */
	public void addAttack(EnumTeams team, long gameLength, int amount)
	{

		switch(team)
		{
		case a:
			this.team_a_attack.add(new Record(gameLength, amount));
			break;
		case b:
			this.team_b_attack.add(new Record(gameLength, amount));
			break;
		case c:
			this.team_c_attack.add(new Record(gameLength, amount));
			break;
		case d:
			this.team_d_attack.add(new Record(gameLength, amount));
			break;
		default:
			break;
		}
	}
	/**
	 * Adds new record about speed upgrade
	 * @param team Team who done the upgrade
	 * @param gameLength Time of the record
	 * @param amount Upgrade change
	 */
	public void addSpeed(EnumTeams team, long gameLength, int amount)
	{
		switch(team)
		{
		case a:
			this.team_a_speed.add(new Record(gameLength, amount));
			break;
		case b:
			this.team_b_speed.add(new Record(gameLength, amount));
			break;
		case c:
			this.team_c_speed.add(new Record(gameLength, amount));
			break;
		case d:
			this.team_d_speed.add(new Record(gameLength, amount));
			break;
		default:
			break;
		}
	}
	
	/**
	 * Adds new record about environment action
	 * @param team Team who done the environment action
	 */
	public void addAction(EnumTeams team)
	{
		switch(team)
		{
		case a:
			this.team_a_action ++;
			break;
		case b:
			this.team_b_action ++;
			break;
		case c:
			this.team_c_action ++;
			break;
		case d:
			this.team_d_action ++;
			break;
		default:
			break;
		}
	}
	/**
	 * Adds new record about reasoning action
	 * @param team Team who done the reasoning action
	 */
	public void addReasoningAction(EnumTeams team)
	{
		switch(team)
		{
		case a:
			this.team_a_reason_action ++;
			break;
		case b:
			this.team_b_reason_action ++;
			break;
		case c:
			this.team_c_reason_action ++;
			break;
		case d:
			this.team_d_reason_action ++;
			break;
		default:
			break;
		}
	}

	/**
	 * Saves experiments to the folder
	 * @param directories Directory structure
	 */
	public void saveExperiment(String... directories)
	{
		String path = this.create_directories("experiments", directories);
		this.save(path);
	}

	/**
	 * Saves game's statistics to the directory 
	 * @param path Where the experiment should be saved
	 */
	public void save(String path)
	{
		path = path + this.experimentFolder(path) + "/";

		if (Model.getConfiguration().getAgent_count_a() != 0)
			this.saveStatisticsToFile(path, EnumTeams.a);
		
		if (Model.getConfiguration().getAgent_count_b() != 0)
			this.saveStatisticsToFile(path, EnumTeams.b);

		if (Model.getConfiguration().getAgent_count_c() != 0)
			this.saveStatisticsToFile(path, EnumTeams.c);
		
		if (Model.getConfiguration().getAgent_count_d() != 0)
			this.saveStatisticsToFile(path, EnumTeams.d);
		
		this.writeStatistics(path + "overall_food", this.food);
		this.writeStatistics(path + "overall_water", this.water);
		this.writeMaxArmy(path);
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path + "overall"));
			writer.write("Game length: " +  this.game_length);
			writer.newLine();
			writer.write("Maximal army: " + this.getMaxArmy());
			writer.newLine();
			writer.write("Total food created: " + this.getTotalWater());
			writer.newLine();
			writer.write("Total water created: " + this.getTotalFood());
			writer.newLine();
			this.writeWhoWon(writer);
			
			if (Model.getConfiguration().getAgent_count_a() != 0)
				this.writeTeamStats(writer, EnumTeams.a);

			if (Model.getConfiguration().getAgent_count_b() != 0)
				this.writeTeamStats(writer, EnumTeams.b);
				
			if (Model.getConfiguration().getAgent_count_c() != 0)
				this.writeTeamStats(writer, EnumTeams.c);

			if (Model.getConfiguration().getAgent_count_d() != 0)
				this.writeTeamStats(writer, EnumTeams.d);

			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes who won the game
	 * @param writer Writer reference
	 * @throws IOException Write exception
	 */
	private void writeWhoWon(BufferedWriter writer) throws IOException
	{
		this.writeIfWon(writer, this.ant_a, EnumTeams.a);
		this.writeIfWon(writer, this.ant_b, EnumTeams.b);
		this.writeIfWon(writer, this.ant_c, EnumTeams.c);
		this.writeIfWon(writer, this.ant_d, EnumTeams.d);
	}
	
	/**
	 * Writes true if the team won
	 * @param writer Writer reference
	 * @param source Source containing army reference
	 * @param team Team of the record
	 * @throws IOException Write exception
	 */
	private void writeIfWon(BufferedWriter writer, ArrayList<Record> source, EnumTeams team) throws IOException
	{
		int value = 0;
		
		for (Record rec: source)
		{
			if (rec != null)
				value += rec.getValue();
		}
			
		if (value != 0)
		{
			writer.write("winner: " + team);
			writer.newLine();
		}
	}
	
	/**
	 * Saves statistics to files for one team
	 * @param path Path where statistics will be saved
	 * @param team Team who's statistics will be saved
	 */
	private void saveStatisticsToFile(String path, EnumTeams team)
	{
		this.writeStatistics(path + "team_" + team + "_food", this.getFood(team));
		this.writeStatistics(path + "team_" + team + "_water", this.getWater(team));
		this.writeStatistics(path + "team_" + team + "_armor", this.getArmorUpgrades(team));
		this.writeStatistics(path + "team_" + team + "_attack", this.getAttackUpgrades(team));
		this.writeStatistics(path + "team_" + team + "_speed", this.getSpeedUpgrades(team));
		this.writeStatistics(path + "team_" + team + "_ants", this.getAnts(team));
		this.writeRateStatistics(path + "team_" + team + "_food_collection_rate", this.getFoodCollection(team));
		this.writeRateStatistics(path + "team_" + team + "_water_collection_rate", this.getWaterCollection(team));
	}

	/**
	 * Writes team statistics to the overall file
	 * @param writer Writer reference
	 * @param team Team which statistics will be saved
	 * @throws IOException
	 */
	private void writeTeamStats(BufferedWriter writer, EnumTeams team) throws IOException
	{
		writer.newLine();
		writer.write("___________ Team " + team.toString() + " - " + Model.getConfiguration().getAI(team) + " ___________");
		writer.newLine();
		writer.write("Score: " + this.getScore(team));
		writer.newLine();
		writer.write("Ants created: " +  this.getTotalAntsCreated(team));
		writer.newLine();
		writer.write("Average army: " + this.getAverageArmy(team));
		writer.newLine();
		writer.newLine();
		writer.write("Armor upgrades: " + this.getTotalArmorUpgrades(team));
		writer.newLine();
		writer.write("Attack upgrades: " + this.getTotalAttackUpgrades(team));
		writer.newLine();
		writer.write("Speed upgrades: " + this.getTotalSpeedUpgrades(team));
		writer.newLine();
		writer.write("Average upgrades: " + this.getAverageUpgrades(team));
		writer.newLine();
		writer.newLine();
		writer.write("Effective actions: " + this.getEA(team));
		writer.newLine();
		writer.write("Effective actions per minute: " + this.getEAPM(team));
		writer.newLine();
		writer.write("Reasoning actions: " + this.getRA(team));
		writer.newLine();
		writer.write("Reasoning actions per minute: " + this.getRAPM(team));
		writer.newLine();
		writer.newLine();
		writer.write("Average food collection rate: " + this.getFoodCollectionRate(team));
		writer.newLine();
		writer.write("Average water collection rate: " + this.getWaterCollectionRate(team));
		writer.newLine();
		writer.write("Average food unspent: " + this.getAverageUnspentFood(team));
		writer.newLine();
		writer.write("Average water unspent: " + this.getAverageUnspentWater(team));
		writer.newLine();
		writer.write("Unspent food/water ratio: " + this.getFoodWaterRatio(team));
		writer.newLine();
	}
	
	/**
	 * Calculates and returns score
	 * @param team Team which score will be retrieved
	 * @return Game score
	 */
	double getScore(EnumTeams team)
	{
		return this.getAverageArmy(team) +
			   this.getAverageUpgrades(team) +
			   this.getFoodCollectionRate(team) +
			   this.getWaterCollectionRate(team);
	}

	/**
	 * Returns average upgrades value
	 * @param team Team which upgrades will be retrieved
	 * @return average upgrades value
	 */
	double getAverageUpgrades(EnumTeams team)
	{
		ArrayList<Record> upgrades = new ArrayList<Record>();
		upgrades.addAll(this.getArmorUpgrades(team));
		upgrades.addAll(this.getAttackUpgrades(team));
		upgrades.addAll(this.getSpeedUpgrades(team));
		
		
		Collections.sort(upgrades, new Record(0, 0));
		return this.getAverageValue(upgrades);
	}
	
	/**
	 * Returns records about ants for team
	 * @param team Team which records will be retrieved
	 * @return records about ants for team
	 */
	public ArrayList<Record> getAnts(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.ant_a;
		case b:
			return this.ant_b;
		case c:
			return this.ant_c;
		case d:
			return this.ant_d;
		default:
			return null;
		}
	}
	
	/**
	 * Returns records about food collection for team  
	 * @param team Team which records will be retrieved
	 * @return records about food collection for team
	 */
	public ArrayList<Record> getFoodCollection(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.getCollectionRate(team_a_food);
		case b:
			return this.getCollectionRate(team_b_food);
		case c:
			return this.getCollectionRate(team_c_food);
		case d:
			return this.getCollectionRate(team_d_food);
		default:
			return null;
		}
	}
	
	/**
	 * Returns records about water collection for team
	 * @param team Team which records will be retrieved
	 * @return records about water collection for team
	 */
	public ArrayList<Record> getWaterCollection(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.getCollectionRate(team_a_water);
		case b:
			return this.getCollectionRate(team_b_water);
		case c:
			return this.getCollectionRate(team_c_water);
		case d:
			return this.getCollectionRate(team_d_water);
		default:
			return null;
		}
	}

	/**
	 * Returns records about armor upgrades for team
	 * @param team Team which records will be retrieved
	 * @return records about armor upgrades for team
	 */
	public ArrayList<Record> getArmorUpgrades(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.team_a_armor;
		case b:
			return this.team_b_armor;
		case c:
			return this.team_c_armor;
		case d:
			return this.team_d_armor;
		default:
			return null;
		}
	}
	
	/**
	 * Returns records about attack upgrades for team
	 * @param team Team which records will be retrieved
	 * @return records about attack upgrades for team
	 */
	public ArrayList<Record> getAttackUpgrades(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.team_a_attack;
		case b:
			return this.team_b_attack;
		case c:
			return this.team_c_attack;
		case d:
			return this.team_d_attack;
		default:
			return null;
		}
	}
	
	/**
	 * Returns records about speed upgrades for team
	 * @param team Team which records will be retrieved
	 * @return Records about speed upgrades for team
	 */
	public ArrayList<Record> getSpeedUpgrades(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.team_a_speed;
		case b:
			return this.team_b_speed;
		case c:
			return this.team_c_speed;
		case d:
			return this.team_d_speed;
		default:
			return null;
		}
	}

	/**
	 * Returns records about environment actions for team
	 * @param team Team which records will be retrieved
	 * @return Records about environment actions for team
	 */
	private long getEA(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.team_a_action;
		case b:
			return this.team_b_action;
		case c:
			return this.team_c_action;
		case d:
			return this.team_d_action;
		default:
			return 0;
		}
	}
	
	/**
	 * Returns records about reasoning actions for team
	 * @param team Team which records will be retrieved
	 * @return Records about reasoning actions for team
	 */
	private long getRA(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.team_a_reason_action;
		case b:
			return this.team_b_reason_action;
		case c:
			return this.team_c_reason_action;
		case d:
			return this.team_d_reason_action;
		default:
			return 0;
		}
	}
	
	/**
	 * Returns total food created
	 * @return Total food creates
	 */
	private int getTotalFood()
	{
		return this.getTotal(this.food);
	}
	
	/**
	 * Returns total water created
	 * @return Total water created
	 */
	private int getTotalWater()
	{
		return this.getTotal(this.water);
	}
	
	/**
	 * Returns total ants created of team
	 * @param team Team which records will be retrieved
	 * @return Total ants created of team
	 */
	private int getTotalAntsCreated(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.getTotal(this.ant_a);
		case b:
			return this.getTotal(this.ant_b);
		case c:
			return this.getTotal(this.ant_c);
		case d:
			return this.getTotal(this.ant_d);
		default:
			return 0;
		}
	}
	
	/**
	 * Returns total speed upgrades for team
	 * @param team Team which records will be retrieved
	 * @return Total speed upgrades for team
	 */
	private int getTotalSpeedUpgrades(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.getTotal(this.team_a_speed);
		case b:
			return this.getTotal(this.team_b_speed);
		case c:
			return this.getTotal(this.team_c_speed);
		case d:
			return this.getTotal(this.team_d_speed);
		default:
			return 0;
		}
	}

	/**
	 * Returns total attack upgrades for team
	 * @param team Team which records will be retrieved
	 * @return Total attack upgrades for team
	 */
	private int getTotalAttackUpgrades(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.getTotal(this.team_a_attack);
		case b:
			return this.getTotal(this.team_b_attack);
		case c:
			return this.getTotal(this.team_c_attack);
		case d:
			return this.getTotal(this.team_d_attack);
		default:
			return 0;
		}
	}

	/**
	 * Returns total armor upgrades for team
	 * @param team Team which records will be retrieved
	 * @return Total armor upgrades for team
	 */
	private int getTotalArmorUpgrades(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.getTotal(this.team_a_armor);
		case b:
			return this.getTotal(this.team_b_armor);
		case c:
			return this.getTotal(this.team_c_armor);
		case d:
			return this.getTotal(this.team_d_armor);
		default:
			return 0;
		}
	}

	/**
	 * Returns average army value  for team
	 * @param team Team which records will be retrieved
	 * @return Average army value  for team
	 */
	public double getAverageArmy(EnumTeams team) 
	{
		switch(team)
		{
		case a:
			return this.getAverageValue(this.ant_a);
		case b:
			return this.getAverageValue(this.ant_b);
		case c:
			return this.getAverageValue(this.ant_c);
		case d:
			return this.getAverageValue(this.ant_d);
		default:
			return 0;
		}
	}

	/**
	 * Gets food collection rate for team
	 * @param team Team which collection rate will be retrieved
	 * @return Food collection rate for team
	 */
	double getFoodCollectionRate(EnumTeams team)
	{
		return this.getRateAvarageValue(this.getCollectionRate(this.getFood(team)));
	}

	/**
	 * Returns records about food for team
	 * @param team Team which records will be retrieved
	 * @return Records about food for team
	 */
	private ArrayList<Record> getFood(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.team_a_food;
		case b:
			return this.team_b_food;
		case c:
			return this.team_c_food;
		case d:
			return this.team_d_food;
		default:
			return null;
		}
	}
	
	/**
	 * Gets water collection rate for team
	 * @param team Team which collection rate will be retrieved
	 * @return Water collection rate for team
	 */
	double getWaterCollectionRate(EnumTeams team)
	{
		return this.getRateAvarageValue(this.getCollectionRate(this.getWater(team)));
	}
	
	/**
	 * Returns records about water for team
	 * @param team Team which records will be retrieved
	 * @return Records about water for team
	 */
	private ArrayList<Record> getWater(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.team_a_water;
		case b:
			return this.team_b_water;
		case c:
			return this.team_c_water;
		case d:
			return this.team_d_water;
		default:
			return null;
		}
	}

	/**
	 * Gets food/water ratio for the team
	 * @param team Team which food/water ratio will be retrieved
	 * @return Food/water ratio for the team
	 */
	private double getFoodWaterRatio(EnumTeams team) 
	{
		double average_unspent_food = this.getAverageUnspentFood(team);
		double average_unspent_water = this.getAverageUnspentWater(team);

		if (average_unspent_food == 0)
			return 0;
		
		else if (average_unspent_water == 0)
			return Double.MAX_VALUE;
		
		return average_unspent_food/average_unspent_water;
	}

	/**
	 * Gets average unspent food for team
	 * @param team Team which average unspent food will be retrieved
	 * @return Average unspent food for team
	 */
	double getAverageUnspentFood(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.getAverageValue(this.team_a_food);
		case b:
			return this.getAverageValue(this.team_b_food);
		case c:
			return this.getAverageValue(this.team_c_food);
		case d:
			return this.getAverageValue(this.team_d_food);
		default:
			return 0;
		}
	}
	/**
	 * Gets average unspent water for team
	 * @param team Team which average unspent water will be retrieved
	 * @return Average unspent water for team
	 */
	double getAverageUnspentWater(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return this.getAverageValue(this.team_a_water);
		case b:
			return this.getAverageValue(this.team_b_water);
		case c:
			return this.getAverageValue(this.team_c_water);
		case d:
			return this.getAverageValue(this.team_d_water);
		default:
			return 0;
		}
	}

	/**
	 * Calculates and returns average value for the source
	 * @param source Source which average value will be calculated
	 * @return Average value for the source
	 */
	private double getAverageValue(ArrayList<Record> source)
	{
		int value = 0;
		double average_val = 0;
		long last_record_time = 0;
		
		for (Record rec: source)
		{
			if (rec == null)
				continue;
			
			average_val += value * (rec.getTime() - last_record_time);
			value += rec.getValue();
			last_record_time = rec.getTime();
		}
		average_val += value * (this.game_length - last_record_time);
		
		return (double)average_val/(this.game_length + 300);
	}
	
	/**
	 *  Calculates and returns average rate for the source
	 * @param source Source which average rate will be calculated
	 * @return Average rate for the source
	 */
	private double getRateAvarageValue(ArrayList<Record> source)
	{
		double average_val = 0;
		long last_record_time = -300;
		int previous_value = 0;
		
		for (Record rec: source)
		{
			if (rec == null)
				continue;
			
			average_val += previous_value * (rec.getTime() - last_record_time);
			previous_value = rec.getValue();
			last_record_time = rec.getTime();
		}
		average_val += previous_value * (this.game_length - last_record_time);
		
		return (double)average_val/(this.game_length + 300);
	}

	/**
	 * Gets environment actions per minute for a team
	 * @param team Team which EAPM will be retrieved
	 * @return Environment actions per minute for team
	 */
	public double getEAPM(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return (double)this.team_a_action/((double)this.game_length/6000);
		case b:
			return (double)this.team_b_action/((double)this.game_length/6000);
		case c:
			return (double)this.team_c_action/((double)this.game_length/6000);
		case d:
			return (double)this.team_d_action/((double)this.game_length/6000);
		default:
			break;
		}
		return 0;
	}
	
	/**
	 * Gets reasoning actions per minute for a team
	 * @param team Team which RAPM will be retrieved
	 * @return Reasoning actions per minute
	 */
	public double getRAPM(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return (double)this.team_a_reason_action/((double)this.game_length/6000);
		case b:
			return (double)this.team_b_reason_action/((double)this.game_length/6000);
		case c:
			return (double)this.team_c_reason_action/((double)this.game_length/6000);
		case d:
			return (double)this.team_d_reason_action/((double)this.game_length/6000);
		default:
			break;
		}
		return 0;
	}

	/**
	 * Gets total value of records in source
	 * @param source Source which total value will be calculated
	 * @return Total value of records in source
	 */
	private int getTotal(ArrayList<Record> source)
	{
		int total = 0;
		for (Record rec: source)
		{
			if (rec.getValue() > 0)
				total += rec.getValue();
		}
		return total;
	}

	/**
	 * Writes total army development to file
	 * @param path Where the file will be created
	 */
	private void writeMaxArmy(String path)
	{
		this.writeStatistics(path + "overall_army", this.getArmy());
	}
	
	/**
	 * Gets overall army
	 * @return Overall army
	 */
	public ArrayList<Record> getArmy()
	{
		ArrayList<Record> army = new ArrayList<Record>();
		army.addAll(this.ant_a);
		army.addAll(this.ant_b);
		army.addAll(this.ant_c);
		army.addAll(this.ant_d);
		
		
		Collections.sort(army, new Record(0, 0));
		
		return army;
	}
	
	/**
	 * Gets maximal army size
	 * @return Maximal army size
	 */
	private int getMaxArmy()
	{
		ArrayList<Record> army = new ArrayList<Record>();
		army.addAll(this.ant_a);
		army.addAll(this.ant_b);
		army.addAll(this.ant_c);
		army.addAll(this.ant_d);
		
		
		Collections.sort(army, new Record(0, 0));
		
		int max_army = 0;
		for (Record record: army)
		{
			if (record != null)
				max_army += record.getValue();
		}
		
		return max_army;
	}

	/**
	 * Create directory hierarchy and returns resulting path
	 * @param base Base for the directory hierarchy
	 * @param directories Names of directories
	 * @return Resulting relative path
	 */
	private String create_directories(String base, String... directories)
	{
		String path = base;
		this.create_directory(base);
		
		for (String directory: directories)
		{
			path += "/" + directory;
			this.create_directory(path);
		}
		
		return path + "/";
	}
	
	/**
	 * Creates folder for the experiment
	 * @param path Directory hierarchy
	 * @return Order of the experiment
	 */
	private int experimentFolder(String path)
	{
		int experiment_number = 1;
		{
			File dir = new File(path + experiment_number);
			while (dir.exists())
				dir = new File(path + ++experiment_number);
			
			this.create_directory(path + experiment_number);
		}
		
		return experiment_number;
	}
	
	/**
	 * Creates the directory
	 * @param path_and_name Direcotry name with a path
	 */
	private void create_directory(String path_and_name)
	{
		File dir = new File(path_and_name);
		
		// Directory already exist, no need to create one
		if (!dir.exists())
		{
			if(!dir.mkdir())
				System.out.println("Cannot create directory!");
		}
	}
	
	/**
	 * Writes statistics from source to the file
	 * @param file File to be created and written
	 * @param source Source of the statistics
	 */
	private void writeStatistics(String file, ArrayList<Record> source)
	{
		if (!source.isEmpty())
		{
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(file));
				this.writeSummedValuesToFile(source, writer);
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Writes rate statistics from source to the file
	 * @param file File to be created and written
	 * @param source Source of the statistics
	 */
	private void writeRateStatistics(String file, ArrayList<Record> source) {
		if (!source.isEmpty())
		{
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(file));
				for (Record record: source)
				{
					if (record == null)
						continue;
					
					writer.write(record.getTime() + "\t" + record.getValue());
					writer.newLine();
				}
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Writes summed values to a file
	 * @param source Source of the values
	 * @param writer Writer reference
	 * @throws IOException Writer exceptions
	 */
	private void writeSummedValuesToFile(ArrayList<Record> source, BufferedWriter writer) throws IOException
	{
		int current_value = 0;
		
		for (Record record: source)
		{
			if (record == null)
				continue;
			
			current_value += record.getValue();
			writer.write(record.getTime() + "\t" + current_value);
			writer.newLine();
		}
	}
	
	/**
	 * Calculates and returns collection rate for a values from source
	 * @param source Source of values
	 * @return Collection rate
	 */
	private ArrayList<Record> getCollectionRate(ArrayList<Record> source)
	{
		ArrayList<Record> rate = new ArrayList<Record>();
		int step = 500;
		int collected = 0;
		
		for (long i = step; i < this.game_length; i += step)
		{
			collected = 0;
			
			for (Record res: source)
			{
				if (res == null)
					continue;
				
				else if (res.getTime() < (i - 6000))
					continue;
					
				else if (res.getTime() > i)
					break;
				
				else if (res.getValue() > 0)
					collected += res.getValue();
			}
			rate.add(new Record(i, collected));
		}
		
		return rate;
	}

	/**
	 * Saves game statistics to log
	 */
	public void saveLog()
	{
		String path = this.create_directories("logs", "");
		this.save(path);
	}
	
	public boolean isLogged(EnumTeams team)
	{
		switch(team)
		{
		case a:
			return Model.getConfiguration().getAgent_count_a() != 0;
		case b:
			return Model.getConfiguration().getAgent_count_b() != 0;
		case c:
			return Model.getConfiguration().getAgent_count_c() != 0;
		case d:
			return Model.getConfiguration().getAgent_count_d() != 0;
		}
		return false;
	}
}
