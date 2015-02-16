/**
 * BP, anthill strategy game
 * Thread responsible for generating food randomly on the map
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/11/20
 * @version 1
 * @file    graphic.ThreadEvents.java
 */
package graphic;

import Enviroment.Model;
import java.util.Random;

/**
 * Thread responsible for generating food randomly on the map
 * @author Vojtech Simetka
 *
 */
public class ThreadEvents extends Thread
{
	Random rand;
	private volatile boolean threadSuspended = false;
	private volatile boolean delete = false;
	
	/**
	 * Constructor
	 */
	protected ThreadEvents()
	{
	  	this.rand = new Random();
	}

	/**
	 * Implements run function
	 */
	public void run()
	{	
		if (Model.getConfiguration().getExperiment() == EnumExperiments.Resources ||
			Model.getConfiguration().getExperiment() == EnumExperiments.DeathMatch)
			return;
		
		// While thread is not cancelled
		while (!delete)
		{
			// Adds food
			if (rand.nextFloat() < Model.getConfiguration().getEvents_food_chance()
					& Model.getWorld().getFood().size() < 15)
			{
				int x = rand.nextInt(Model.getWorld().getWidth()-1);
				int y = rand.nextInt(Model.getWorld().getHeight()-1);
				while (!Model.getWorld().isFree(x,y))
				{
					x = rand.nextInt(Model.getWorld().getWidth()-1);
					y = rand.nextInt(Model.getWorld().getHeight()-1);
				}
				Model.getWorld().addFood(
						x,
						y,
						rand.nextInt(3)+3);
			}
				
			// Adds water
			if (rand.nextFloat() < Model.getConfiguration().getEvents_water_chance()
				& Model.getWorld().getWater().size() < 15)
			{
				int x = rand.nextInt(Model.getWorld().getWidth()-1);
				int y = rand.nextInt(Model.getWorld().getHeight()-1);
				while (!Model.getWorld().isFree(x,y))
				{
					x = rand.nextInt(Model.getWorld().getWidth()-1);
					y = rand.nextInt(Model.getWorld().getHeight()-1);
				}
				Model.getWorld().addWater(
						x,
						y,
						rand.nextInt(3)+3);
			}
			
			try
			{
				// Sleeps thread
				Thread.sleep(Model.getConfiguration().getEvents_time_between_events());
				
				// Pauses thread
				synchronized(this)
				{
					while (threadSuspended)
						wait();
				}
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Cancels thread
	 */
	public void cancel()
	{
		this.interrupt();
	}

	/**
	 * Resumes paused thread
	 */
	public void resumeThread()
	{
		synchronized(this)
		{
			this.threadSuspended = false;
	        notify();
		}
	}
	
	/**
	 * Pauses thread
	 */
	public void pauseThread()
	{
		synchronized(this)
		{
			this.threadSuspended = true;
	        notify();
		}
	}
	
	/**
	 * Deletes thread
	 */
	public void delete()
	{
		synchronized(this)
		{
			this.threadSuspended = false;
			this.delete = true;
	        notify();
		}
	}
}
