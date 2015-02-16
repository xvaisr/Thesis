/**
 * BP, anthill strategy game
 * Displays graphs about the finished game
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/25
 * @version 1
 * @file    graphic.Environment.java
 */
package graphic;

import Enviroment.Model;
import Enviroment.Resources;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Displays graphs about finished game
 * @author Vojtech Simetka
 *
 */
public class Graph extends JPanel implements ComponentListener
{	
	private int max;
	private boolean display_rate = false;
	
	private double values_to_height = 0;
	private double game_time_to_width = 0;
	
	private ArrayList<Record> records1;
	private ArrayList<Record> records2;
	private ArrayList<Record> records3;
	private ArrayList<Record> records4;
	
	private Color color1 = Color.black;
	private Color color2 = Color.black;
	private Color color3 = Color.black;
	private Color color4 = Color.black;
	
	/**
	 * Graph constructor
	 * @param record1 Records source 1
	 * @param record2 Records source 2
	 * @param record3 Records source 3
	 * @param record4 Records source 4
	 * @param rate If records displays rate or not
	 */
	Graph(ArrayList<Record> record1, ArrayList<Record> record2, ArrayList<Record> record3, ArrayList<Record> record4, boolean rate)
	{
		this.setBackground(Color.black);
		
		this.records1 = record1;
		this.records2 = record2;
		this.records3 = record3;
		this.records4 = record4;
		
		this.display_rate = rate;
		
		if (rate)
		{
			this.findMax(record1);
			this.findMax(record2);
			this.findMax(record3);
			this.findMax(record4);
		}
		
		else
		{
			this.calculateMax(record1);
			this.calculateMax(record2);
			this.calculateMax(record3);
			this.calculateMax(record4);
		}
		
		this.addComponentListener(this);
		this.recalculateVariables();
	}

	/**
	 * Finds maximum in source containing rate
	 * @param source Source where to find maximum
	 */
	private void findMax(ArrayList<Record> source)
	{
		if (source == null)
			return;
			
		for (Record record: source)
		{
			if (record == null)
				continue;
			
			if (this.max < record.getValue())
				this.max = record.getValue();
		}
	}

	/**
	 * Finds maximum in source
	 * @param source SOurce where to find maximal value
	 */
	private void calculateMax(ArrayList<Record> source)
	{
		if (source == null)
			return;
		
		int record_current_value = 0;
			
		for (Record record: source)
		{
			if (record == null)
				continue;
			record_current_value += record.getValue();
			if (this.max < record_current_value)
				this.max = record_current_value;
		}
	}
	
	/**
	 * Paints the graph
	 * @param g Graphic reference
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		this.recalculateVariables();
		
		if (this.display_rate)
		{
			this.drawCurveRate(g, this.color1, this.records1);
			this.drawCurveRate(g, this.color2, this.records2);
			this.drawCurveRate(g, this.color3, this.records3);
			this.drawCurveRate(g, this.color4, this.records4);
		}
		else
		{
			this.drawCurve(g, this.color1, this.records1);
			this.drawCurve(g, this.color2, this.records2);
			this.drawCurve(g, this.color3, this.records3);
			this.drawCurve(g, this.color4, this.records4);
		}
	}
	
	/**
	 * Draws rate curve
	 * @param g Graphic reference
	 * @param color Color of the curve
	 * @param source Source of the curve
	 */
	private void drawCurveRate(Graphics g, Color color, ArrayList<Record> source)
	{
		if (source == null)
			return;
		
		Point previous = new Point(0, this.getHeight());
		
		g.setColor(color);
		
		for (Record record: source)
		{
			Point current = new Point((int)((record.getTime()+300)*this.game_time_to_width), this.getHeight() - (int)(record.getValue() * this.values_to_height));
			g.drawLine(previous.x, previous.y, current.x, previous.y);
			g.drawLine(current.x, previous.y, current.x, current.y);
			previous = current;
		}
		g.drawLine(previous.x, previous.y, this.getWidth(), previous.y);
	}
	
	/**
	 * Draws curve
	 * @param g Graphic reference
	 * @param color Color of the curve
	 * @param source Source of the curve
	 */
	private void drawCurve(Graphics g, Color color, ArrayList<Record> source)
	{
		if (source == null)
			return;
		
		Point previous = new Point(0, this.getHeight());
		int current_value = 0;
		
		g.setColor(color);
		
		for (Record record: source)
		{
			current_value += record.getValue();
			Point current = new Point((int)((record.getTime()+300)*this.game_time_to_width), this.getHeight() - (int)(current_value * this.values_to_height));
			g.drawLine(previous.x, previous.y, current.x, previous.y);
			g.drawLine(current.x, previous.y, current.x, current.y);
			previous = current;
		}
		g.drawLine(previous.x, previous.y, this.getWidth(), previous.y);
	}
	
	/**
	 * Recalculates all variables of the graph
	 */
	private void recalculateVariables()
	{
		this.values_to_height = (double)(this.getHeight())/this.max;
		this.game_time_to_width = (double)(this.getWidth())/(Model.getStatistics().getGameLength() + 300);
	}

	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e)
	{
		this.recalculateVariables();
	}
	public void componentShown(ComponentEvent e) {}

	public void switchTo(Resources type)
	{
		
	}
	
	/**
	 * Sets graph's colors
	 * @param color1 Color 1
	 * @param color2 Color 2
	 * @param color3 Color 3
	 * @param color4 Color 4
	 */
	public void setColors(Color color1, Color color2, Color color3, Color color4)
	{
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
		this.color4 = color4;
	}
}
