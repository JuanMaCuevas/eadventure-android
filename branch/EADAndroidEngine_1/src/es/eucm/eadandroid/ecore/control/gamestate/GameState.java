package es.eucm.eadandroid.ecore.control.gamestate;

import android.hardware.SensorEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.KeyPadListener;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.TouchListener;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.TrackBallListener;
import es.eucm.eadandroid.ecore.gui.GUI;

/**
 * A state of the game main loop
 */
public abstract class GameState {

	/**
	 * Instance of game
	 */
	 protected Game game;


	protected TouchListener touchListener;
	protected KeyPadListener keyListener;
	protected TrackBallListener tballListener;

	/**
	 * Creates a new GameState
	 */
	public GameState() {


		 this.game = Game.getInstance();
	}

	/**
	 * Perform an iteration of the game main loop
	 * 
	 * @param elapsedTime
	 *            the elapsed time from the last iteration
	 * @param fps
	 *            current frames per second
	 */
	public abstract void mainLoop(long elapsedTime, int fps);

	/**
	 * Called to process key events.
	 * 
	 * @param event
	 * @return
	 */
	public boolean processKeyEvent(KeyEvent event) {

		if (keyListener != null)
			return keyListener.processKeyEvent(event);
		else
			return false;
	}

	/**
	 * Called to process touch screen events.
	 * 
	 * @param event
	 * @return
	 */
	public boolean processTouchEvent(MotionEvent event) {
		if (touchListener != null)
			return touchListener.processTouchEvent(event);
		return false;
	}

	/**
	 * Called to process trackball events.
	 * 
	 * @param event
	 * @return
	 */
	public boolean processTrackballEvent(MotionEvent event) {
		if (tballListener != null)
			return tballListener.processTrackballEvent(event);
		else
			return false;
	}

	public boolean processSensorEvent(SensorEvent e) {

		return false;

	}

	public void registerTouchListener(TouchListener t) {
		this.touchListener = t;
		
	}
	

}
