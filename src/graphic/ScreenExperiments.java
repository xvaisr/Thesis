/**
 * BP, anthill strategy game
 * Screen that creates the experiments
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/04/25
 * @version 1
 * @file    graphic.ScreenExperiments.java
 */
package graphic;

import Enviroment.Original.Model;
import Enviroment.Original.WorldsDatabase;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JPanel;

/**
 * Screen that creates the experiments
 * @author Vojtech Simetka
 *
 */
public class ScreenExperiments extends JPanel
{
	/**
	 * Screen constructor
	 */
	public ScreenExperiments()
	{
		this.setLayout(new GridBagLayout());
		
	    GridBagConstraints c = new GridBagConstraints();

	    c.fill = GridBagConstraints.BOTH;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.gridwidth = 1;
	    c.gridheight = 6;
	    c.gridx = 0;
	    c.gridy = 0;
		this.add(Box.createGlue(),c);

	    c.weightx = 1;
	    c.weighty = 1;
	    c.gridwidth = 1;
	    c.gridheight = 6;
	    c.gridx = 2;
	    c.gridy = 0;
		this.add(Box.createGlue(),c);
		
	    c.weightx = 1;
	    c.weighty = 0.85;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;
	    c.gridy = 0;
		this.add(Box.createGlue(),c);
		
	    c.weightx = 1;
	    c.weighty = 0.85;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 1;
	    c.gridy = 6;
		this.add(Box.createGlue(),c);
		
		this.setBackground(Color.black);
	    

		Button experment_resource = new Button("Resource Collection", new ButtonActionListener()
		{
			public void actionToBePerformed()
			{
				Model.getConfiguration().reInit();
				Model.getConfiguration().setExperiment(EnumExperiments.Resources);
				Model.getConfiguration().setScenario(WorldsDatabase.Worlds.ResourceExperiment1);
				Model.getConfiguration().setMove_speed(200);
				Model.getConfiguration().setPps(100);
				Model.getConfiguration().setFps(1);
				Model.getConfiguration().setNoGui(true);
				
				Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.basic);
				
				Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
				
				Model.getConfiguration().setAgent_count_a(1);
				
				Model.resetFinishedExperimentsCounter();
				
				(new ThreadStartGame()).start();
			}	
        });
	    c.insets = new Insets(5,5,5,5);
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 1;
	    c.weighty = 0.1;
	    c.gridx = 1;
	    c.gridy = 1;
	    this.add(experment_resource,c);
	    
	    Button experiment_death_match = new Button("Death Match", new ButtonActionListener()
	    {
			public void actionToBePerformed()
			{
				Model.getConfiguration().reInit();
				Model.getConfiguration().setExperiment(EnumExperiments.DeathMatch);
				Model.getConfiguration().setScenario(WorldsDatabase.Worlds.DeathMatch);
				Model.getConfiguration().setMove_speed(500);
				Model.getConfiguration().setPps(40);
				Model.getConfiguration().setFps(1);
				Model.getConfiguration().setNoGui(true);
				
				Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.basic);
				Model.getConfiguration().addAI(EnumTeams.b, AIDatabase.EnumAI.cooperative);
				
				Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
				Model.getConfiguration().addTeamToAlliance(EnumTeams.b, EnumAlliances.Alliance_2);
				
				Model.getConfiguration().setAgent_count_a(2);
				Model.getConfiguration().setAgent_count_b(1);
				
				Model.resetFinishedExperimentsCounter();
				
				(new ThreadStartGame()).start();
			}	
	    });
	    c.insets = new Insets(5,5,5,5);
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 1;
	    c.weighty = 0.1;
	    c.gridx = 1;
	    c.gridy = 2;
	    this.add(experiment_death_match,c);
		
		Button settings = new Button("Time Complexity", new ButtonActionListener()
	    {
			public void actionToBePerformed()
			{
				Model.getConfiguration().reInit();
				Model.getConfiguration().setExperiment(EnumExperiments.TimeComplexity);
				Model.getConfiguration().setScenario(WorldsDatabase.Worlds.DeathMatch);
				Model.getConfiguration().setMove_speed(300);
				Model.getConfiguration().setPps(70);
				Model.getConfiguration().setFps(1);
				Model.getConfiguration().setNoGui(true);
				Model.getConfiguration().setEvents_time_between_events(2500);
				
				Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.basic);
				Model.getConfiguration().addAI(EnumTeams.b, AIDatabase.EnumAI.cooperative);
				Model.getConfiguration().addAI(EnumTeams.c, AIDatabase.EnumAI.advanced);
				
				Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
				Model.getConfiguration().addTeamToAlliance(EnumTeams.b, EnumAlliances.Alliance_2);
				Model.getConfiguration().addTeamToAlliance(EnumTeams.c, EnumAlliances.Alliance_3);
				
				Model.getConfiguration().setAgent_count_a(1);
				Model.getConfiguration().setAgent_count_b(1);
				Model.getConfiguration().setAgent_count_c(1);
				
				Model.resetFinishedExperimentsCounter();
				
				(new ThreadStartGame()).start();
			}
        });
		c.insets = new Insets(5,5,5,5);
	    c.weightx = 1;
	    c.weighty = 0.1;
	    c.gridx = 1;
	    c.gridy = 3;
		this.add(settings,c);
		
		Button exit = new Button("Performance", new ButtonActionListener()
	    {
	    	public void actionToBePerformed()
			{
	    		Model.getConfiguration().reInit();
				Model.getConfiguration().setExperiment(EnumExperiments.Performance);
				Model.getConfiguration().setScenario(WorldsDatabase.Worlds.DeathMatch);
				Model.getConfiguration().setMove_speed(200);
				Model.getConfiguration().setPps(100);
				Model.getConfiguration().setFps(1);
				Model.getConfiguration().setNoGui(true);
				Model.getConfiguration().setEvents_time_between_events(2500);
				
				Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.basic);
				Model.getConfiguration().addAI(EnumTeams.b, AIDatabase.EnumAI.basic);
				Model.getConfiguration().addAI(EnumTeams.c, AIDatabase.EnumAI.basic);
				
				Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
				Model.getConfiguration().addTeamToAlliance(EnumTeams.b, EnumAlliances.Alliance_2);
				Model.getConfiguration().addTeamToAlliance(EnumTeams.c, EnumAlliances.Alliance_3);
				
				Model.getConfiguration().setAgent_count_a(1);
				Model.getConfiguration().setAgent_count_b(1);
				Model.getConfiguration().setAgent_count_c(1);
				
				Model.resetFinishedExperimentsCounter();
				
				(new ThreadStartGame()).start();
			}
				
        });
		c.insets = new Insets(5,5,5,5);
	    c.weightx = 1;
	    c.weighty = 0.1;
	    c.gridx = 1;
	    c.gridy = 4;
		this.add(exit,c);
		
		Button perf2 = new Button("Performance 2", new ButtonActionListener()
	    {
	    	public void actionToBePerformed()
			{
	    		Model.getConfiguration().reInit();
				Model.getConfiguration().setExperiment(EnumExperiments.Performance2);
				Model.getConfiguration().setScenario(WorldsDatabase.Worlds.Random);
				Model.getConfiguration().setMove_speed(200);
				Model.getConfiguration().setPps(100);
				Model.getConfiguration().setFps(30);
				Model.getConfiguration().setNoGui(false);
				Model.getConfiguration().setEvents_time_between_events(2500);
				Model.getConfiguration().setWorldSize(400);
    			Model.getConfiguration().setFog(true);
				
				Model.getConfiguration().addAI(EnumTeams.a, AIDatabase.EnumAI.basic);
				Model.getConfiguration().addAI(EnumTeams.b, AIDatabase.EnumAI.basic);
				Model.getConfiguration().addAI(EnumTeams.c, AIDatabase.EnumAI.basic);
				Model.getConfiguration().addAI(EnumTeams.d, AIDatabase.EnumAI.basic);
				
				Model.getConfiguration().addTeamToAlliance(EnumTeams.a, EnumAlliances.Alliance_1);
				Model.getConfiguration().addTeamToAlliance(EnumTeams.b, EnumAlliances.Alliance_2);
				Model.getConfiguration().addTeamToAlliance(EnumTeams.c, EnumAlliances.Alliance_3);
				Model.getConfiguration().addTeamToAlliance(EnumTeams.d, EnumAlliances.Alliance_4);
				
				Model.getConfiguration().setAgent_count_a(2);
				Model.getConfiguration().setAgent_count_b(2);
				Model.getConfiguration().setAgent_count_c(2);
				Model.getConfiguration().setAgent_count_d(2);
				
				Model.resetFinishedExperimentsCounter();
				
				(new ThreadStartGame()).start();
			}
				
        });
		c.insets = new Insets(5,5,5,5);
	    c.weightx = 1;
	    c.weighty = 0.1;
	    c.gridx = 1;
	    c.gridy = 5;
		this.add(perf2,c);
	}
}
