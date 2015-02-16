/**
 * BP, anthill strategy game
 * Screen that creates the game 
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/25
 * @version 1
 * @file    graphic.ScreenCreateGame.java
 */
package graphic;

import Enviroment.Model;
import Enviroment.WorldsDatabase.Worlds;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Screen that creates the game 
 * @author Vojtech Simetka
 *
 */
public class ScreenCreateGame extends JPanel
{	
	private final EnumAlliances aliances[] = EnumAlliances.values();
	private final AIDatabase.EnumAI controls[]= AIDatabase.EnumAI.values();

	JComboBox agentCountA = new JComboBox();
    JComboBox allianceA = new JComboBox(aliances);
	JComboBox controlA = new JComboBox(controls);
	
	JComboBox agentCountB = new JComboBox();
    JComboBox allianceB = new JComboBox(aliances);
	JComboBox controlB = new JComboBox(controls);
	
	JComboBox agentCountC = new JComboBox();
	JComboBox allianceC = new JComboBox(aliances);
	JComboBox controlC = new JComboBox(controls);
	
	JComboBox agentCountD = new JComboBox();
    JComboBox allianceD = new JComboBox(aliances);
	JComboBox controlD = new JComboBox(controls);
	
	JComboBox scenario = new JComboBox(Worlds.values());
	JComboBox speed = new JComboBox();
	
	/**
	 * Screen create game constructor
	 */
	public ScreenCreateGame()
	{
		this.setLayout(new GridBagLayout());
		
		Model.getConfiguration().setNoGui(false);
		Model.getConfiguration().setFps(30);
		Model.getConfiguration().setFog(true);
	
		allianceA.setSelectedIndex(0);
		controlA.setSelectedIndex(0);
		for (int i = 0; i < 10; i++)
			agentCountA.addItem(i);
		agentCountA.setSelectedIndex(3);
		
		allianceB.setSelectedIndex(1);
		controlB.setSelectedIndex(1);
		for (int i = 0; i < 10; i++)
			agentCountB.addItem(i);
		agentCountB.setSelectedIndex(3);

        allianceC.setSelectedIndex(2);
		controlC.setSelectedIndex(2);
		for (int i = 0; i < 10; i++)
			agentCountC.addItem(i);
		agentCountC.setSelectedIndex(3);
		
        allianceD.setSelectedIndex(3);
		controlD.setSelectedIndex(4);
		for (int i = 0; i < 10; i++)
			agentCountD.addItem(i);
		agentCountD.setSelectedIndex(3);
		
		this.scenario.setSelectedIndex(0);
		
		for (int i = 50; i <= 200; i+=10)
			this.speed.addItem(i);
		this.speed.setSelectedIndex(7);

		Button start = new Button("Start game", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				Model.getConfiguration().reInit();
				Model.getConfiguration().setScenario((Worlds)(ScreenCreateGame.this.scenario.getSelectedItem()));
				Model.getConfiguration().setMove_speed(ScreenCreateGame.this.speed.getSelectedIndex()*10+50);
				Model.getConfiguration().setExperiment(EnumExperiments.None);
				
				Model.getConfiguration().addAI(EnumTeams.a, (AIDatabase.EnumAI)ScreenCreateGame.this.controlA.getSelectedItem());
				Model.getConfiguration().addAI(EnumTeams.b, (AIDatabase.EnumAI)ScreenCreateGame.this.controlB.getSelectedItem());
				Model.getConfiguration().addAI(EnumTeams.c, (AIDatabase.EnumAI)ScreenCreateGame.this.controlC.getSelectedItem());
				Model.getConfiguration().addAI(EnumTeams.d, (AIDatabase.EnumAI)ScreenCreateGame.this.controlD.getSelectedItem());
				
				Model.getConfiguration().addTeamToAlliance(EnumTeams.a, (EnumAlliances)ScreenCreateGame.this.allianceA.getSelectedItem());
				Model.getConfiguration().addTeamToAlliance(EnumTeams.b, (EnumAlliances)ScreenCreateGame.this.allianceB.getSelectedItem());
				Model.getConfiguration().addTeamToAlliance(EnumTeams.c, (EnumAlliances)ScreenCreateGame.this.allianceC.getSelectedItem());
				Model.getConfiguration().addTeamToAlliance(EnumTeams.d, (EnumAlliances)ScreenCreateGame.this.allianceD.getSelectedItem());
				
				Model.getConfiguration().setAgent_count_a(ScreenCreateGame.this.agentCountA.getSelectedIndex());
				Model.getConfiguration().setAgent_count_b(ScreenCreateGame.this.agentCountB.getSelectedIndex());
				Model.getConfiguration().setAgent_count_c(ScreenCreateGame.this.agentCountC.getSelectedIndex());
				Model.getConfiguration().setAgent_count_d(ScreenCreateGame.this.agentCountD.getSelectedIndex());
				
				(new ThreadStartGame()).start();
			}	
        });
	    
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH;
	    c.ipady = 0;
	    c.ipadx = 0;
	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.gridwidth = 1;
	    c.gridheight = 1;

	    c.gridheight = 2;
	    c.gridx = 1;
	    c.gridy = 1;
	    this.add(new JLabel("Team A"), c);

	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 1;
	    this.add(new JLabel("# ants"), c);
	    
	    c.gridx = 3;
	    c.gridy = 1;
	    this.add(new JLabel("Alliance"), c);

	    c.gridx = 4;
	    c.gridy = 1;
	    this.add(new JLabel("AI"), c);
	    
	    c.gridx = 2;
	    c.gridy = 2;
	    this.add(agentCountA, c);
	    
	    c.gridx = 3;
	    c.gridy = 2;
	    this.add(allianceA, c);

	    c.gridx = 4;
	    c.gridy = 2;
	    this.add(controlA, c);

	    c.gridheight = 2;
	    c.gridx = 1;
	    c.gridy = 3;
	    this.add(new JLabel("Team B"), c);

	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 3;
	    this.add(new JLabel("# ants"), c);
	    
	    c.gridx = 3;
	    c.gridy = 3;
	    this.add(new JLabel("Alliance"), c);

	    c.gridx = 4;
	    c.gridy = 3;
	    this.add(new JLabel("AI"), c);
	    
	    c.gridx = 2;
	    c.gridy = 4;
	    this.add(agentCountB, c);
	    
	    c.gridx = 3;
	    c.gridy = 4;
	    this.add(allianceB, c);

	    c.gridx = 4;
	    c.gridy = 4;
	    this.add(controlB, c);


	    c.gridheight = 2;
	    c.gridx = 1;
	    c.gridy = 5;
	    this.add(new JLabel("Team C"), c);

	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 5;
	    this.add(new JLabel("# ants"), c);
	    
	    c.gridx = 3;
	    c.gridy = 5;
	    this.add(new JLabel("Alliance"), c);

	    c.gridx = 4;
	    c.gridy = 5;
	    this.add(new JLabel("AI"), c);
	    
	    c.gridx = 2;
	    c.gridy = 6;
	    this.add(agentCountC, c);
	    
	    c.gridx = 3;
	    c.gridy = 6;
	    this.add(allianceC, c);

	    c.gridx = 4;
	    c.gridy = 6;
	    this.add(controlC, c);

	    c.gridheight = 2;
	    c.gridx = 1;
	    c.gridy = 7;
	    this.add(new JLabel("Team D"), c);

	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 7;
	    this.add(new JLabel("# ants"), c);
	    
	    c.gridx = 3;
	    c.gridy = 7;
	    this.add(new JLabel("Alliance"), c);

	    c.gridx = 4;
	    c.gridy = 7;
	    this.add(new JLabel("AI"), c);
	    
	    c.gridx = 2;
	    c.gridy = 8;
	    this.add(agentCountD, c);
	    
	    c.gridx = 3;
	    c.gridy = 8;
	    this.add(allianceD, c);

	    c.gridx = 4;
	    c.gridy = 8;
	    this.add(controlD, c);
	    
	    c.gridx = 0;
		c.gridy = 9;
	    this.add(Box.createGlue(), c);

	    c.gridwidth = 1;
	    c.gridx = 1;
		c.gridy = 10;
		this.add(new JLabel("Game speed"),c);
		
	    c.gridwidth = 1;
	    c.gridx = 3;
		c.gridy = 10;
		this.add(new JLabel("Scenario"),c);

	    c.gridwidth = 1;
	    c.gridx = 2;
		c.gridy = 10;
		this.add(this.speed,c);
		
	    c.gridwidth = 1;
	    c.gridx = 4;
		c.gridy = 10;
		this.add(this.scenario,c);

	    c.insets = new Insets(15,15,15,15);
		c.ipady = 10;
	    c.gridx = 0;
		c.gridy = 11;
	    this.add(Box.createGlue(), c);

	    c.gridx = 5;
		c.gridy = 12;
		this.add(start, c);

	    c.gridx = 0;
		c.gridy = 12;
		this.add(new Button("Return", new ButtonActionListener(){

			public void actionToBePerformed()
			{
				Model.getView().showMenu();
			}
			
		}), c);

	    c.ipadx = 70;
	    c.gridx = 0;
		c.gridy = 0;
	    this.add(Box.createGlue(), c);

	    c.gridx = 5;
		c.gridy = 0;
	    this.add(Box.createGlue(), c);
	}
}
