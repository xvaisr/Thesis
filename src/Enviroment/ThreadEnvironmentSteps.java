/**
 * BP, anthill strategy game
 * Thread responsible for environment steps
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/11/20
 * @version 1
 * @file    graphic.ThreadEnvironmentSteps.java
 */
package Enviroment;

/**
 * Thread responsible for environment steps
 * @author Vojtech Simetka
 *
 */
public class ThreadEnvironmentSteps extends Thread implements Runnable
{
	private long time_between_frames = 10;
	private long frameStart;
	private int frameCount = 0;
	private long elapsedTime;
	private long totalElapsedTime = 0;
	private int reportedFramerate;
	private final int fpsUpdateTime = 1000;
	private final int miliseconds_in_second = 1000;
	
	private volatile boolean delete = false;
	private volatile boolean threadSuspended = false;
	private long game_length = -300;

	/**
	 * Constructor
	 */
	public ThreadEnvironmentSteps()
	{
		time_between_frames = miliseconds_in_second/Model.getConfiguration().getMove_speed();
	}
	
	/**
	 * Implements run function
	 */
	public void run()
	{	
		// While thread is not cancelled
		while (!delete)
		{
			// Saves start time
			this.frameStart = System.currentTimeMillis();
			
			// Repaints frame
			if (this.game_length >= 0)
				Model.getWorld().doOneStepForAllAgents();
			
			// Increases game length
			this.game_length ++;
			
			// Calculates time neccessary for repainting frame
			this.elapsedTime = System.currentTimeMillis() - this.frameStart;
			
			// Makes thread sleep for some time
			this.sleepThread();
			
			// Increase frame count
			this.frameCount++;
			
			// Saves total elapsed time
			this.totalElapsedTime += (System.currentTimeMillis() - this.frameStart);
			
			// Updates MPS display value if necessary
			if (this.totalElapsedTime > this.fpsUpdateTime)
				this.updateMPS();
		}
	}

	/**
	 * Updates moves per second display
	 */
	private void updateMPS()
	{
		this.reportedFramerate = (int)((float) this.frameCount / (float) this.totalElapsedTime * miliseconds_in_second);
		
		// Update FPS
		Model.getView().set_MPS(reportedFramerate);
		frameCount = 0;
		totalElapsedTime -= fpsUpdateTime;
	}

	/**
	 * Sleep thread for adequate time
	 */
	private void sleepThread()
	{
		try
		{
			// Makes sure that framerate milliseconds have passed this frame
			if(elapsedTime < time_between_frames)
				Thread.sleep(time_between_frames - elapsedTime);

			// Don't starve the garbage collector
			else
				Thread.sleep(5);
		
			// Pauses thread
			synchronized(this)
			{
				while (threadSuspended)
				{
					Model.getView().set_MPS(0);
					wait();
				}
			}
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * Reinitializes game speed
	 */
	public void reinitSpeed()
	{
		time_between_frames = miliseconds_in_second/Model.getConfiguration().getMove_speed();
	}
	
	/**
	 * Deletes the thread
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
	
	/**
	 * Gets current game time
	 * @return Current game time
	 */
	public long getGameLength()
	{
		return this.game_length;
	}
}
