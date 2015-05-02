/**
 * BP, anthill strategy game
 * Thread responsible for repainting the game screen
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/11/20
 * @version 1
 * @file    graphic.ThreadEvents.java
 */
package graphic;

import Enviroment.Original.Model;

/**
 * Thread responsible for repainting the game screen
 * @author Vojtech Simetka
 *
 */
public class ThreadFPS extends Thread implements Runnable
{
	private long time_between_frames = 16;
	private long frameStart;
	private int frameCount = 0;
	private long elapsedTime;
	private long totalElapsedTime = 0;
	private int reportedFramerate;
	private final int fpsUpdateTime = 1000;
	private final int miliseconds_in_second = 1000;
	private volatile boolean threadSuspended = false;
	private volatile boolean delete = false;
	
	/**
	 * Constructor
	 */
	public ThreadFPS()
	{
		this.time_between_frames = this.miliseconds_in_second/Model.getConfiguration().getFps();
	}

	/**
	 * Implements run function
	 */
	public void run()
	{
		// While thread is not cancelled
		while (!this.delete)
		{
			// Saves start time
			this.frameStart = System.currentTimeMillis();
			
			// Repaints frame
			if (Model.getView() != null)
				Model.getView().repaint();
			
			// Calculates time neccessary for repainting frame
			this.elapsedTime = System.currentTimeMillis() - this.frameStart;
			
			// Makes thread sleep for some time
			this.sleepThread();
			
			// Increase frame count
			this.frameCount++;
			
			// Saves total elapsed time
			this.totalElapsedTime += (System.currentTimeMillis() - this.frameStart);
			
			// Updates FPS display value if necessary
			if (this.totalElapsedTime > this.fpsUpdateTime)
				this.updateFPS();
		}
	}

	private void updateFPS()
	{
		this.reportedFramerate = (int)((float) this.frameCount / (float) this.totalElapsedTime * miliseconds_in_second);
		
		// Update FPS
		Model.getView().set_FPS(reportedFramerate);
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
					wait();
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
}
