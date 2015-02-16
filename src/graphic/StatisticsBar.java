/**
 * BP, anthill strategy game
 * Score for an anthill
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2014/04/23
 * @version 1
 * @file    graphic.StatisticsBar.java
 */
package graphic;

import Enviroment.Model;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

/**
 * Displays score for an anthill
 * @author Vojtech Simetka
 */
public class StatisticsBar extends JPanel implements ComponentListener
{
	private static double max_army;
	private static double max_resources;
	private static double max_upgrades;
	private static double max_score;
	private static double max_eapm;
	private double army;
	private double resources;
	private double upgrades;
	private double score;
	private double eapm;
	private Color color;
	private Font font = new Font("", Font.PLAIN, 0);
	private int pdng;
	private int t_width;
	private int t_height;
	
	/**
	 * StatisticsBar constructor
	 * @param team Team for who the score should be displayed
	 */
	public StatisticsBar(EnumTeams team)
	{
		this.army = Model.getStatistics().getAverageArmy(team);
		this.resources = Model.getStatistics().getFoodCollectionRate(team)
					   + Model.getStatistics().getWaterCollectionRate(team); 
		this.upgrades = Model.getStatistics().getAverageUpgrades(team);
		this.score = Model.getStatistics().getScore(team);
		this.eapm = Model.getStatistics().getEAPM(team);
		
		if (this.army > StatisticsBar.max_army)
			StatisticsBar.max_army = this.army;
		
		if (this.resources > StatisticsBar.max_resources)
			StatisticsBar.max_resources = this.resources;

		if (this.upgrades > StatisticsBar.max_upgrades)
			StatisticsBar.max_upgrades = this.upgrades;

		if (this.score > StatisticsBar.max_score)
			StatisticsBar.max_score = this.score;	
		
		if (this.eapm > StatisticsBar.max_eapm)
			StatisticsBar.max_eapm = this.eapm;
		
		this.color = Model.getConfiguration().getColor(team);
		this.calculateVariables();
		
		this.setBackground(Color.black);
		this.addComponentListener(this);
	}
	
	/**
	 * Resets stattic members for statistics bar
	 */
	public static void reset()
	{
		StatisticsBar.max_army = 0;
		StatisticsBar.max_upgrades = 0;
		StatisticsBar.max_score = 0;
		StatisticsBar.max_resources = 0;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(color);
		g2d.setFont(this.font);
		
        DecimalFormat df = new DecimalFormat("#.00");
        String str = df.format(this.score);
		
		FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(str, g2d);
		
		g2d.setColor(color);
		g2d.fillRect(0, this.t_height/2, (int)(this.score/StatisticsBar.max_score * t_width), this.t_height);
		
        g2d.setColor(Color.white);
		g2d.drawString(str, (int)((this.t_width-rect.getWidth())/2), (int)(this.t_height + fm.getDescent()));
		
		g2d.setColor(new Color(90,150,190));
		g2d.drawRect(0, this.t_height/2, this.t_width, this.t_height);

		str = df.format(this.army);
		rect = fm.getStringBounds(str, g2d);
		
		g2d.setColor(color);
		g2d.fillRect(this.t_width + this.pdng, this.t_height/2, (int)(this.army/StatisticsBar.max_army * t_width), this.t_height);
		
        g2d.setColor(Color.white);
		g2d.drawString(str, (int)(this.t_width + this.pdng +(this.t_width - rect.getWidth())/2), (int)(this.t_height + fm.getDescent()));
		
		g2d.setColor(new Color(90,150,190));
		g2d.drawRect(this.t_width + this.pdng, this.t_height/2, this.t_width, this.t_height);

		str = df.format(this.resources);
		rect = fm.getStringBounds(str, g2d);
		
		g2d.setColor(color);
		g2d.fillRect(2*(this.t_width + this.pdng), this.t_height/2, (int)((StatisticsBar.max_resources != 0) ? (this.resources/StatisticsBar.max_resources * t_width) : t_width), this.t_height);
		
        g2d.setColor(Color.white);
		g2d.drawString(str, (int)(2* (this.t_width + this.pdng) +(this.t_width - rect.getWidth())/2), (int)(this.t_height + fm.getDescent()));
		
		g2d.setColor(new Color(90,150,190));
		g2d.drawRect(2*(this.t_width + this.pdng), this.t_height/2, this.t_width, this.t_height);

		str = df.format(this.upgrades);
		rect = fm.getStringBounds(str, g2d);
		
		g2d.setColor(color);
		g2d.fillRect(3*(this.t_width + this.pdng), this.t_height/2, (int)((StatisticsBar.max_upgrades != 0) ? (this.upgrades/StatisticsBar.max_upgrades * t_width) : t_width), this.t_height);
		
        g2d.setColor(Color.white);
		g2d.drawString(str, (int)(3* (this.t_width + this.pdng) +(this.t_width - rect.getWidth())/2), (int)(this.t_height + fm.getDescent()));
		
		g2d.setColor(new Color(90,150,190));
		g2d.drawRect(3*(this.t_width + this.pdng), this.t_height/2, this.t_width, this.t_height);
		
		str = df.format(this.eapm);
		rect = fm.getStringBounds(str, g2d);
		
		g2d.setColor(color);
		g2d.fillRect(4*(this.t_width + this.pdng), this.t_height/2, (int)((StatisticsBar.max_eapm != 0) ? (this.eapm/StatisticsBar.max_eapm * t_width) : t_width), this.t_height);
		
        g2d.setColor(Color.white);
		g2d.drawString(str, (int)(4* (this.t_width + this.pdng) +(this.t_width - rect.getWidth())/2), (int)(this.t_height + fm.getDescent()));
		
		g2d.setColor(new Color(90,150,190));
		g2d.drawRect(4*(this.t_width + this.pdng), this.t_height/2, this.t_width, this.t_height);
	}
	
	/**
	 * Calculates positioning for the component
	 */
	private void calculateVariables()
	{
		this.font = new Font("", Font.PLAIN, (int)(this.getHeight()/4));
		this.t_width = this.getWidth()/6;
		this.t_height = (int)(this.getHeight()/2);
		this.pdng = this.t_width/5;
		
		this.repaint();
	}

	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}

	public void componentResized(ComponentEvent arg0)
	{	
		this.calculateVariables();
	}
}
