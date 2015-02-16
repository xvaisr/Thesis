/**
 * BP, anthill strategy game
 * Thread responsible for creating of the new game
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2012/11/20
 * @version 1
 * @file    graphic.ThreadEvents.java
 */
package graphic;

import Enviroment.Model;

/**
 * Thread responsible for creating of the new game
 * @author Vojtech Simetka
 *
 */
public class ThreadStartGame extends Thread implements Runnable
{
	/**
	 * Overrides run method
	 */
	@Override
	public void run()
	{
		Model.getView().showMap();
	}
}
