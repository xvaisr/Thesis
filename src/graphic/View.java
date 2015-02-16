/**
 * BP, anthill strategy game
 * Class responsible for controlling what is displayed
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/09/25
 * @version 1
 * @file    graphic.MyView.java
 */

package graphic;

import Enviroment.Model;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

/**
 * Class responsible for controlling what is displayed
 * @author Vojtech Simetka
 *
 */
public class View extends JFrame
{
	// Variables
	protected final String game_title = "Anthill RTS game";
	private final int minimal_view_height = 600;
	private final int minimal_view_width = 800;
	int FPS = 0;
	int MPS = 0;
	
	/**
	 * MyView constructor
	 */
	public View()
	{
		// Sets minimum size
        this.setMinimumSize(new Dimension(this.minimal_view_width, this.minimal_view_height));
        
        this.setTitle(this.game_title);
        
        // Shows menu
        this.showMenu();
        
		// Displays frame
		this.setVisible(true);
	}
	
	/**
	 * Displays settings window
	 */
	public void showSettings()
	{	
		// Removes all elements and key listeners from frame
		this.cleanFrame();
		
		// Creates map texture
		ScreenSettings panel = new ScreenSettings();
		this.add(panel);
		
    	this.validate();
	}
	
	/**
	 * Displays menu window
	 */
	void showMenu()
	{
		// Removes all elements and key listeners from frame
		this.cleanFrame();
		
		this.add(new ScreenMenu());
		
    	this.validate();

    	this.repaint();
	}
	
	/**
	 * Sets FPS of game
	 * @param framerate FPS
	 */
	public void set_FPS(int framerate)
	{
		this.FPS = framerate;
	}

	/**
	 * Sets MPS of game
	 * @param framerate MPS
	 */
	public void set_MPS(int framerate)
	{
		this.MPS = framerate;
	}
	
	/**
	 * Displays map and creates world
	 */
	public synchronized void showMap()
	{
		// Multiple attempts to start game
		if (Model.isGame_running())
			return;

		// Removes all elements and key listeners from frame
		this.cleanFrame();
		
		this.add(new ScreenLoading());

    	this.validate();
    	this.repaint();
    	
    	if (Model.getConfiguration().isNoGui())
    	{
    		Model.startWorld(null);
	    	
	    	ScreenGameNoGui screen = new ScreenGameNoGui();

			this.cleanFrame();
			
			this.add(screen);
			this.addKeyListener(screen);
    	}
    	
    	else
    	{
    		PanelMap panel_map = new PanelMap();
			
	    	// Creates world
	    	Model.startWorld(panel_map);
	    	
			// Creates map screen
			ScreenGame map = new ScreenGame(panel_map);
	
			// Removes all elements and key listeners from frame
			this.cleanFrame();
			
			this.add(map);
			this.addKeyListener(map);
    	}
		
		// Validates frame
    	this.validate();
    	this.requestFocus();
	}

	/**
	 * Displays game creation screen
	 */
	public void showCreateGame()
	{
		// Removes all elements and key listeners from frame
		this.cleanFrame();
		
		ScreenCreateGame menu = new ScreenCreateGame();
		this.add(menu);
		
    	this.validate();
    	this.repaint();
	}

	/**
	 * Removes all elements and key listeners from frame
	 */
	private void cleanFrame()
	{
		// Removes all elements from frame
		this.getContentPane().removeAll();
		
		// Removes all key listeners from frame
		for (KeyListener listener: this.getKeyListeners())
			this.removeKeyListener(listener);
	}

	/**
	 * Displays statistics screen
	 */
	protected void showStats()
	{
		// Removes all elements and key listeners from frame
		this.cleanFrame();
		
		ScreenStatistics stats = new ScreenStatistics();
		this.add(stats);
		
    	this.validate();
    	this.repaint();
	}

	/**
	 * Displays experiments screen
	 */
	public void showExperiments()
	{
		// Removes all elements and key listeners from frame
		this.cleanFrame();
		
		this.add(new ScreenExperiments());
		
    	this.validate();
    	this.repaint();
	}
	
	/**
	 * Displays experiments are being saved screen
	 */
	public void showExperimentsSaving()
	{
		// Removes all elements and key listeners from frame
		this.cleanFrame();
		
		this.add(new ScreenExperimentsSaving());
		
    	this.validate();
    	this.repaint();
	}

	/**
	 * Removes all listeners from the frame
	 */
	public void removeAllListeners()
	{
		for (ComponentListener listener: this.getComponentListeners())
			this.removeComponentListener(listener);
		
		for (KeyListener listener: this.getKeyListeners())
			this.removeKeyListener(listener);
		
		for (MouseListener listener: this.getMouseListeners())
			this.removeMouseListener(listener);
	}
}
