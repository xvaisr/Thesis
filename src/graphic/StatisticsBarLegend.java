/**
 * BP, anthill strategy game
 * Legend for screen
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2014/04/23
 * @version 1
 * @file    graphic.StatisticsBarLegend.java
 */
package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 * Displays overall score screen legend
 * @author Vojtech Simetka
 */
public class StatisticsBarLegend extends JPanel implements ComponentListener
{
	private Color color;
	private Font font = new Font("", Font.PLAIN, 0);
	private int pdng;
	private int t_width;
	private int t_height;
	
	/**
	 * Legend constructor
	 */
	public StatisticsBarLegend()
	{
		this.calculateVariables();
		
		this.setBackground(Color.black);
		this.addComponentListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.white);
		g2d.setFont(this.font);
		
		FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds("Score", g2d);
		
		g2d.drawString("Score", (int)((this.t_width-rect.getWidth())/2), (int)(this.t_height + fm.getDescent()));
		
		rect = fm.getStringBounds("Army", g2d);
		g2d.drawString("Army", (int)(this.t_width + this.pdng +(this.t_width - rect.getWidth())/2), (int)(this.t_height + fm.getDescent()));
		
		rect = fm.getStringBounds("Resources", g2d);
		g2d.drawString("Resources", (int)(2* (this.t_width + this.pdng) +(this.t_width - rect.getWidth())/2), (int)(this.t_height + fm.getDescent()));
		
		rect = fm.getStringBounds("Upgrades", g2d);
		g2d.drawString("Upgrades", (int)(3* (this.t_width + this.pdng) +(this.t_width - rect.getWidth())/2), (int)(this.t_height + fm.getDescent()));
		
		rect = fm.getStringBounds("EAPM", g2d);
		g2d.drawString("EAPM", (int)(4* (this.t_width + this.pdng) +(this.t_width - rect.getWidth())/2), (int)(this.t_height + fm.getDescent()));
	}
	
	/**
	 * Calculates variables for positioning
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
