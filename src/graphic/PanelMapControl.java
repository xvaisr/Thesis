/**
 * BP, anthill strategy game
 * Implements map control panel
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/26
 * @version 1
 * @file    graphic.PanelMapControll.java
 */
package graphic;

import Enviroment.Model;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Implements map control panel
 * @author Vojtech Simetka
 *
 */
public class PanelMapControl extends JPanel
{
	/**
	 * PanelMapControl constructor
	 */
	PanelMapControl()
	{
		this.setBackground(Color.black);
	}
	
	/**
	 * Paint component
	 */
	protected void paintComponent(Graphics g)
	{
		
	    if (Model.getConfiguration().getAgent_count_a() > 0)
	    {
	    	if (Model.getEnvironment().getActive_agentsA().size() > 0)
	    		g.setColor(Model.getWorld().getTeam(EnumTeams.a).getColor());
	    	else
	    		g.setColor(Color.gray);
	    		
	    	g.drawString("	Team A", 0, this.getHeight()-120);
	    	g.drawString(Model.getConfiguration().getAI(EnumTeams.a).toString() + " AI", 0, this.getHeight()-100);
	    	g.drawString(Model.getConfiguration().getAlliance(EnumTeams.a).toString(), 0, this.getHeight()-80);
	    	g.drawString("Units: " + Integer.toString(Model.getEnvironment().getActive_agentsA().size()), 0, this.getHeight()-60);
	    	g.drawString(" Food: " + Model.getWorld().getTeam(EnumTeams.a).getFood(), 0, this.getHeight()-40);
	    	g.drawString("Water: " + Model.getWorld().getTeam(EnumTeams.a).getWater(), 0, this.getHeight()-20);
	    }
	    
	    if (Model.getConfiguration().getAgent_count_b() > 0)
	    {
	    	if (Model.getEnvironment().getActive_agentsB().size() > 0)
	    		g.setColor(Model.getWorld().getTeam(EnumTeams.b).getColor());
	    	else
	    		g.setColor(Color.gray);
	    	g.drawString("	Team B", 100, this.getHeight()-120);
	    	g.drawString(Model.getConfiguration().getAI(EnumTeams.b) + " AI".toString(), 100, this.getHeight()-100);
	    	g.drawString(Model.getConfiguration().getAlliance(EnumTeams.b).toString(), 100, this.getHeight()-80);
	    	g.drawString("Units: " + Integer.toString(Model.getEnvironment().getActive_agentsB().size()), 100, this.getHeight()-60);
	    	g.drawString(" Food: " + Model.getWorld().getTeam(EnumTeams.b).getFood(), 100, this.getHeight()-40);
	    	g.drawString("Water: " + Model.getWorld().getTeam(EnumTeams.b).getWater(), 100, this.getHeight()-20);
	    }
	    
	    if (Model.getConfiguration().getAgent_count_c() > 0)
	    {
	    	if (Model.getEnvironment().getActive_agentsC().size() > 0)
	    		g.setColor(Model.getWorld().getTeam(EnumTeams.c).getColor());
	    	else
	    		g.setColor(Color.gray);
	    	g.drawString("	Team C", 200, this.getHeight()-120);
	    	g.drawString(Model.getConfiguration().getAI(EnumTeams.c) + " AI".toString(), 200, this.getHeight()-100);
	    	g.drawString(Model.getConfiguration().getAlliance(EnumTeams.c).toString(), 200, this.getHeight()-80);
	    	g.drawString("Units: " + Integer.toString(Model.getEnvironment().getActive_agentsC().size()), 200, this.getHeight()-60);
	    	g.drawString(" Food: " + Model.getWorld().getTeam(EnumTeams.c).getFood(), 200, this.getHeight()-40);
	    	g.drawString("Water: " + Model.getWorld().getTeam(EnumTeams.c).getWater(), 200, this.getHeight()-20);
	    }
	    
	    if (Model.getConfiguration().getAgent_count_d() > 0)
	    {
	    	if (Model.getEnvironment().getActive_agentsD().size() > 0)
	    		g.setColor(Model.getWorld().getTeam(EnumTeams.d).getColor());
	    	else
	    		g.setColor(Color.gray);
	    	g.drawString("	Team D", 300, this.getHeight()-120);
	    	g.drawString(Model.getConfiguration().getAI(EnumTeams.d).toString(), 300, this.getHeight()-100);
	    	g.drawString(Model.getConfiguration().getAlliance(EnumTeams.d).toString(), 300, this.getHeight()-80);
	    	g.drawString("Units: " + Integer.toString(Model.getEnvironment().getActive_agentsD().size()), 300, this.getHeight()-60);
	    	g.drawString(" Food: " + Integer.toString(Model.getWorld().getTeam(EnumTeams.d).getFood()), 300, this.getHeight()-40);
	    	g.drawString("Water: " + Integer.toString(Model.getWorld().getTeam(EnumTeams.d).getWater()), 300, this.getHeight()-20);
	    }
	}
}
