/**
 * BP, anthill strategy game
 * Creates menu dialog in the game
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/04/10
 * @version 1
 * @file    graphic.Configuration.java
 */
package graphic;

import Enviroment.Model;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

/**
 * Creates menu dialog in the game
 * @author Vojtech Simetka
 *
 */
public class DialogMenu extends JDialog
{
	/**
	 * DialogMenu constructor
	 */
	protected DialogMenu()
	{
		super(Model.getView(), true);
		
		InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		
	    KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
	    Action actionListener = new AbstractAction()
	    {
	    	public void actionPerformed(ActionEvent actionEvent)
	    	{
	    		DialogMenu.this.resumeGame();
	    	}
	    };
	    inputMap.put(stroke, "ESCAPE");
	    
	    KeyStroke stroke2 = KeyStroke.getKeyStroke("E");
	    Action actionListener2 = new AbstractAction() {
	    	public void actionPerformed(ActionEvent actionEvent)
	    	{
	    		DialogMenu.this.endGame();
	    	}
	    };
	    inputMap.put(stroke2, "S");
	    rootPane.getActionMap().put("ESCAPE", actionListener);
	    rootPane.getActionMap().put("S", actionListener2);
	    
	    Model.pauseGame();
	    
	    Container pane = this.getContentPane();
		
	    pane.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
	    Model.pauseGame();
			
		this.setSize(150, 120);
		this.setLocationRelativeTo(Model.getView());
		this.setUndecorated(true);
	    
	    Button end_game = null;
	    
	    if (Model.isGameEnd())
	    {
	    	end_game =  new Button("Score (S)", new ButtonActionListener()
		    {
		    	public void actionToBePerformed()
				{
		    		DialogMenu.this.endGame();
				}
		    });
	    }
	    else
	    {
		   end_game =  new Button("Surrender (S)", new ButtonActionListener()
		    {
		    	public void actionToBePerformed()
				{
		    		DialogMenu.this.endGame();
				}
		    });
	    }
	    
	    c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(15,15,15,15);
	    c.weightx = 1;
	    c.weighty = 1;
	    c.gridx = 0;
	    c.gridy = 0;
	    pane.add(end_game,c);
		
	    Button tlacitko = new Button("Resume game (Esc)", new ButtonActionListener()
	    {
	    	public void actionToBePerformed()
			{
	    		DialogMenu.this.resumeGame();
	    	}
	    });
	    
	    c.insets = new Insets(15,15,15,15);
	    c.weightx = 1;
	    c.weighty = 1;
	    c.gridx = 0;
	    c.gridy = 1; 
	    pane.add(tlacitko,c);
	    
		this.setVisible(true);
	}
	
	/**
	 * Ends the game
	 */
	protected void endGame()
	{
		this.setVisible(false);
		this.dispose();
        
        // Destroys world
    	Model.destroyWorld();
	
    	// Shows menu
		Model.getView().showStats();
	}

	/**
	 * Returns to the game
	 */
	private void resumeGame()
	{
		this.setVisible(false);
    	this.dispose();
    	
		Model.resumeGame();
		Model.getView().requestFocus();
	}
}
