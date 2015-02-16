/**
 * BP, anthill strategy game
 * Overall score screen
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2014/04/23
 * @version 1
 * @file    graphic.StatisticsTable.java
 */
package graphic;

import Enviroment.Model;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;

/**
 * Displays overall score screen
 * @author Vojtech Simetka
 */
public class StatisticsTable extends JPanel
{	
	List<EnumTeams> teams = new ArrayList<EnumTeams>();
	
	/**
	 * Statistics constructor
	 */
	public StatisticsTable()
	{
		StatisticsBar.reset();
		int anthills = 1;

		this.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
	    c.fill = GridBagConstraints.BOTH;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 0;
	    c.gridy = 0;
	    
	    this.add(new StatisticsBarLegend(), c);
	    
	    c.gridy = anthills;
		
		if (Model.getStatistics().isLogged(EnumTeams.a))
		{
			this.add(new StatisticsBar(EnumTeams.a),c);
			anthills++;
		}
			
	    c.gridy = anthills;
		
		if (Model.getStatistics().isLogged(EnumTeams.b))
		{
			this.add(new StatisticsBar(EnumTeams.b),c);
			anthills++;
		}
	    c.gridy = anthills;
	    
		if (Model.getStatistics().isLogged(EnumTeams.c))
		{
			this.add(new StatisticsBar(EnumTeams.c),c);
			anthills++;
		}
	    c.gridy = anthills;
		
		if (Model.getStatistics().isLogged(EnumTeams.d))
		{
			this.add(new StatisticsBar(EnumTeams.d),c);
			anthills++;
		}
	    c.gridy = anthills;
	    c.weighty = 8-anthills;
	    this.add(Box.createGlue(),c);
		
		this.setBackground(Color.black);
	}
}
