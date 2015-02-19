/**
 * BP, anthill strategy game
 * Screen displaying game statistics
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/25
 * @version 1
 * @file    graphic.ScreenSettings.java
 */
package graphic;

import Enviroment.Original.Model;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Screen displaying game statistics
 * @author vojtechsimetka
 *
 */
public class ScreenStatistics extends JPanel
{
	private Button left_button1 = new Button();
	private Button left_button2 = new Button();
	private Button left_button3 = new Button();
	private Button left_button4 = new Button();
	
	private Button score_table = new Button("Score", new ButtonActionListener()
	{
		public void actionToBePerformed()
		{
			ScreenStatistics.this.displayScore();
		}	
    });
	private Button overall = new Button("Overall", new ButtonActionListener()
	{
		public void actionToBePerformed()
		{
			ScreenStatistics.this.displayOverallArmy();
		}
    });
	private Button team_economy = new Button("Economy", new ButtonActionListener()
	{
		public void actionToBePerformed()
		{
			ScreenStatistics.this.displayEconomyFood();
		}
    });
	private Button team_army = new Button("Army", new ButtonActionListener()
	{
		public void actionToBePerformed()
		{
			ScreenStatistics.this.displayArmyAnts();
		}
    });
	
	private StatisticsTable overall_table;
	
	private Graph overall_resources;
	private Graph overall_army;
	
	private Graph team_water;
	private Graph team_food;
	private Graph team_water_collection_rate;
	private Graph team_food_collection_rate;

	private Graph team_ants;
	private Graph team_speed;
	private Graph team_attack;
	private Graph team_armour;
	
	
	/**
	 * Screen statistics constructor
	 */
	ScreenStatistics()
	{
		Model.getStatistics().saveLog();
		
		this.setBackground(Color.black);
		
		this.displayScore();
	}

	/**
	 * Displays screen with team armies
	 */
	protected void displayArmyAnts()
	{
		if (this.team_ants == null)
		{
			this.team_ants = new Graph(Model.getStatistics().getAntsA(),
									   Model.getStatistics().getAntsB(),
									   Model.getStatistics().getAntsC(),
									   Model.getStatistics().getAntsD(),
									   false);
			
			this.team_ants.setColors(Model.getConfiguration().getColor(EnumTeams.a),
									 Model.getConfiguration().getColor(EnumTeams.b),
									 Model.getConfiguration().getColor(EnumTeams.c),
									 Model.getConfiguration().getColor(EnumTeams.d));
		}

		this.switchButtonsToArmy();
		
		this.team_army.activate();
		this.left_button1.activate();
		
		this.addElements(this.team_ants,
						 this.left_button1,
						 this.left_button2,
						 this.left_button3,
						 this.left_button4);
	}
	
	/**
	 * Displays screen with team armour upgrades
	 */
	protected void displayArmyArmour()
	{
		if (this.team_armour == null)
		{
			this.team_armour = new Graph(Model.getStatistics().getArmorUpgrades(EnumTeams.a),
										 Model.getStatistics().getArmorUpgrades(EnumTeams.b),
										 Model.getStatistics().getArmorUpgrades(EnumTeams.c),
										 Model.getStatistics().getArmorUpgrades(EnumTeams.d),
										 false);
			
			this.team_armour.setColors(Model.getConfiguration().getColor(EnumTeams.a),
									 Model.getConfiguration().getColor(EnumTeams.b),
									 Model.getConfiguration().getColor(EnumTeams.c),
									 Model.getConfiguration().getColor(EnumTeams.d));
		}

		this.switchButtonsToArmy();
		
		this.team_army.activate();
		this.left_button2.activate();
		
		this.addElements(this.team_armour,
						 this.left_button1,
						 this.left_button2,
						 this.left_button3,
						 this.left_button4);
	}
	
	/**
	 * Displays screen with team army attack
	 */
	protected void displayArmyAttack()
	{
		if (this.team_attack == null)
		{
			this.team_attack = new Graph(Model.getStatistics().getAttackUpgrades(EnumTeams.a),
										 Model.getStatistics().getAttackUpgrades(EnumTeams.b),
										 Model.getStatistics().getAttackUpgrades(EnumTeams.c),
										 Model.getStatistics().getAttackUpgrades(EnumTeams.d),
										 false);
			
			this.team_attack.setColors(Model.getConfiguration().getColor(EnumTeams.a),
									   Model.getConfiguration().getColor(EnumTeams.b),
									   Model.getConfiguration().getColor(EnumTeams.c),
									   Model.getConfiguration().getColor(EnumTeams.d));
		}

		this.switchButtonsToArmy();
		
		this.team_army.activate();
		this.left_button3.activate();
		
		this.addElements(this.team_attack,
						 this.left_button1,
						 this.left_button2,
						 this.left_button3,
						 this.left_button4);
	}
	
	/**
	 * Displays screeen with team army speed
	 */
	protected void displayArmySpeed()
	{
		if (this.team_speed == null)
		{
			this.team_speed = new Graph(Model.getStatistics().getSpeedUpgrades(EnumTeams.a),
										Model.getStatistics().getSpeedUpgrades(EnumTeams.b),
										Model.getStatistics().getSpeedUpgrades(EnumTeams.c),
										Model.getStatistics().getSpeedUpgrades(EnumTeams.d),
										   false);
			
			this.team_speed.setColors(Model.getConfiguration().getColor(EnumTeams.a),
									  Model.getConfiguration().getColor(EnumTeams.b),
									  Model.getConfiguration().getColor(EnumTeams.c),
									  Model.getConfiguration().getColor(EnumTeams.d));
		}

		this.switchButtonsToArmy();
		
		this.team_army.activate();
		this.left_button4.activate();
		
		this.addElements(this.team_speed,
						 this.left_button1,
						 this.left_button2,
						 this.left_button3,
						 this.left_button4);
	}

	/**
	 * Displays screen with team water
	 */
	protected void displayEconomyWater()
	{
		if (this.team_water == null)
		{
			this.team_water = new Graph(Model.getStatistics().getTeamAFood(),
									    Model.getStatistics().getTeamBFood(),
									    Model.getStatistics().getTeamCFood(),
									    Model.getStatistics().getTeamDFood(),
										   false);
			
			this.team_water.setColors(Model.getConfiguration().getColor(EnumTeams.a),
									  Model.getConfiguration().getColor(EnumTeams.b),
									  Model.getConfiguration().getColor(EnumTeams.c),
									  Model.getConfiguration().getColor(EnumTeams.d));
		}
		
		this.switchButtonsToEconomy();
		
		this.team_economy.activate();
		this.left_button2.activate();
			
		this.addElements(this.team_water,
						 this.left_button1,
						 this.left_button2,
						 this.left_button3,
						 this.left_button4);
	}
	
	/**
	 * Displays screen with team food
	 */
	protected void displayEconomyFood()
	{
		if (this.team_food == null)
		{
			this.team_food = new Graph(Model.getStatistics().getTeamAWater(),
									    Model.getStatistics().getTeamBWater(),
									    Model.getStatistics().getTeamCWater(),
									    Model.getStatistics().getTeamDWater(),
										   false);
			
			this.team_food.setColors(Model.getConfiguration().getColor(EnumTeams.a),
									  Model.getConfiguration().getColor(EnumTeams.b),
									  Model.getConfiguration().getColor(EnumTeams.c),
									  Model.getConfiguration().getColor(EnumTeams.d));
		}

		this.switchButtonsToEconomy();
		
		this.team_economy.activate();
		this.left_button1.activate();
			
		this.addElements(this.team_food,
						 this.left_button1,
						 this.left_button2,
						 this.left_button3,
						 this.left_button4);
	}
	
	/**
	 * Displays screen with team food rate collection
	 */
	protected void displayEconomyFoodRate()
	{
		if (this.team_food_collection_rate == null)
		{
			this.team_food_collection_rate = new Graph(Model.getStatistics().getFoodCollection(EnumTeams.a),
										Model.getStatistics().getFoodCollection(EnumTeams.b),
										Model.getStatistics().getFoodCollection(EnumTeams.c),
										Model.getStatistics().getFoodCollection(EnumTeams.d),
										true);
			
			this.team_food_collection_rate.setColors(Model.getConfiguration().getColor(EnumTeams.a),
									  Model.getConfiguration().getColor(EnumTeams.b),
									  Model.getConfiguration().getColor(EnumTeams.c),
									  Model.getConfiguration().getColor(EnumTeams.d));
		}

		this.switchButtonsToEconomy();
		
		this.team_economy.activate();
		this.left_button3.activate();
			
		this.addElements(this.team_food_collection_rate,
						 this.left_button1,
						 this.left_button2,
						 this.left_button3,
						 this.left_button4);
	}
	
	/**
	 * Displays screen with team water rate collection
	 */
	protected void displayEconomyWaterRate()
	{
		if (this.team_water_collection_rate == null)
		{
			this.team_water_collection_rate = new Graph(Model.getStatistics().getWaterCollection(EnumTeams.a),
													    Model.getStatistics().getWaterCollection(EnumTeams.b),
													    Model.getStatistics().getWaterCollection(EnumTeams.c),
													    Model.getStatistics().getWaterCollection(EnumTeams.d),
													    true);
			
			this.team_water_collection_rate.setColors(Model.getConfiguration().getColor(EnumTeams.a),
									  Model.getConfiguration().getColor(EnumTeams.b),
									  Model.getConfiguration().getColor(EnumTeams.c),
									  Model.getConfiguration().getColor(EnumTeams.d));
		}

		this.switchButtonsToEconomy();
		
		this.team_economy.activate();
		this.left_button4.activate();
			
		this.addElements(this.team_water_collection_rate,
						 this.left_button1,
						 this.left_button2,
						 this.left_button3,
						 this.left_button4);
	}
	
	/**
	 * Displays overall army
	 */
	protected void displayOverallArmy()
	{
		if (this.overall_army == null)
		{
			this.overall_army = new Graph(Model.getStatistics().getArmy(),
											   null,
											   null,
											   null,
											   false);

			this.overall_army.setColors(Color.white,
				 					  Color.cyan,
				 					  Color.black,
									  Color.black);
		}

		this.switchButtonsToOverall();
		
		this.overall.activate();
		this.left_button1.activate();
		
		this.addElements(this.overall_army,
				 this.left_button1,
				 this.left_button2);
	}

	/**
	 * Displays overall resources
	 */
	protected void displayOverallResources()
	{
		if (this.overall_resources == null)
		{
			this.overall_resources = new Graph(Model.getStatistics().getFood(),
											   Model.getStatistics().getWater(),
											   null,
											   null,
											   false);

			this.overall_resources.setColors(Color.yellow,
				 					  Color.cyan,
				 					  Color.black,
									  Color.black);
		}

		this.switchButtonsToOverall();
		
		this.overall.activate();
		this.left_button2.activate();
		
		this.addElements(this.overall_resources,
				 this.left_button1,
				 this.left_button2);
	}

	/**
	 * Displays score table
	 */
	protected void displayScore()
	{
		if (this.overall_table == null)
			this.overall_table = new StatisticsTable();

		
		this.deactivateAllButtons();
		
		this.score_table.activate();
		
		this.addElements(this.overall_table);
	}
	
	/**
	 * Displays elements
	 * @param graph Graph to be displayed
	 * @param buttons Buttons to be displayed
	 */
	private void addElements(JComponent graph, Button ...buttons)
	{
		this.removeAll();
		
		this.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
	    int number_of_rows = buttons.length;
	    int current_column = 1;
	    
	    c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(0,5,5,0);
	    
	    for (Button button: buttons)
	    {
		    c.weightx = 0;
		    c.weighty = 0.05;
		    c.gridheight = 1;
		    c.gridx = 0;
		    c.gridy = current_column++;
			this.add(button,c);
	    }
		
	    // Adds glue
	    c.insets = new Insets(0,0,0,0);
	    c.weightx = 0.2;
	    c.weighty = 0.8 - number_of_rows * 0.05;
	    c.gridx = 0;
	    c.gridy = current_column;
		this.add(Box.createGlue(),c);
		
	    c.insets = new Insets(15,15,15,15);
	    c.weightx = 1;
	    c.weighty = 0.8;
	    c.gridwidth = 5;
	    c.gridheight = number_of_rows + 1;
	    c.gridx = 1;
	    c.gridy = 1;
	    this.add(graph, c);
		
	    c.insets = new Insets(15,15,15,15);
	    c.weightx = 0.25;
	    c.weighty = 0.05;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 0;
	    c.gridy = 0;
	    this.add(this.score_table, c);
	    
	    c.insets = new Insets(15,15,15,15);
	    c.weightx = 0.25;
	    c.weighty = 0.05;
	    c.gridwidth = 1;
	    c.gridx = 1;
	    c.gridy = 0;
	    this.add(this.overall, c);
	    
	    c.insets = new Insets(15,15,15,15);
	    c.weightx = 0.25;
	    c.weighty = 0.05;
	    c.gridwidth = 1;
	    c.gridx = 2;
	    c.gridy = 0;
	    this.add(this.team_economy, c);
	    
	    c.insets = new Insets(15,15,15,15);
	    c.weightx = 0.25;
	    c.weighty = 0.05;
	    c.gridwidth = 1;
	    c.gridx = 3;
	    c.gridy = 0;
	    this.add(this.team_army, c);
	    
		Button done = new Button("Done", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				Model.getView().showMenu();
			}
				
        });
		
	    c.insets = new Insets(15,15,15,15);
	    c.weightx = 0.25;
	    c.weighty = 0.05;
	    c.gridwidth = 1;
	    c.gridx = 4;
	    c.gridy = number_of_rows + 2;
	    this.add(done, c);
	    
	    this.revalidate();
	    this.repaint();
	}
	
	/**
	 * Deactivates all buttons
	 */
	private void deactivateAllButtons()
	{
		this.left_button1.deactivate();
		this.left_button2.deactivate();
		this.left_button3.deactivate();
		this.left_button4.deactivate();
		
		this.overall.deactivate();
		this.score_table.deactivate();
		this.team_economy.deactivate();
		this.team_army.deactivate();
	}

	/**
	 * Switches left buttons to economy buttons
	 */
	private void switchButtonsToEconomy()
	{
		this.left_button1 = new Button("Unspent food", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				ScreenStatistics.this.displayEconomyFood();
			}
			
		});
		
		this.left_button2 = new Button("Unspent water", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				ScreenStatistics.this.displayEconomyWater();
			}
			
		});
		
		this.left_button3 = new Button("Collection rate food", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				ScreenStatistics.this.displayEconomyFoodRate();
			}
			
		});
		
		this.left_button4 = new Button("Collection rate water", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				ScreenStatistics.this.displayEconomyWaterRate();
			}
			
		});
		
		this.deactivateAllButtons();
	}
	
	/**
	 * Switches left buttons to army buttons
	 */
	private void switchButtonsToArmy()
	{
		this.left_button1 = new Button("Army value", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				ScreenStatistics.this.displayArmyAnts();
			}
			
		});
		
		this.left_button2 = new Button("Armour upgrades", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				ScreenStatistics.this.displayArmyArmour();
			}
			
		});
		
		this.left_button3 = new Button("Attack upgrades", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				ScreenStatistics.this.displayArmyAttack();
			}
			
		});
		
		this.left_button4 = new Button("Speed upgrades", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				ScreenStatistics.this.displayArmySpeed();
			}
			
		});
		
		this.deactivateAllButtons();
	}
	
	/**
	 * Switches left buttons to overall
	 */
	private void switchButtonsToOverall()
	{
		this.left_button1 = new Button("Army value", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				ScreenStatistics.this.displayOverallArmy();
			}
			
		});
		
		this.left_button2 = new Button("Resources", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				ScreenStatistics.this.displayOverallResources();
			}
			
		});
		
		this.deactivateAllButtons();
	}
}
