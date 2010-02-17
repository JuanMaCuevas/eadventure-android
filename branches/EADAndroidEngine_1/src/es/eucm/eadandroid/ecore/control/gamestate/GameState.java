package es.eucm.eadandroid.ecore.control.gamestate;

import java.util.ArrayList;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.ui.gestureDetectors.UIEventListener;

/**
 * A state of the game main loop
 */
public abstract class GameState {

	
	/**
	 * Instance of game
	 */
	protected Game game;
	
	/**
	 * List of UIEventListener to notify when an event is triggered - > Observer pattern based
	 */
	private ArrayList<UIEventListener> eventListeners = new ArrayList<UIEventListener>();

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

	
	
	
	public void registerUIEventListener(UIEventListener eventListener) {
		
		eventListeners.add(eventListener);
		
	}
	
	public void unregisterUIEventListener(UIEventListener eventListener) {
		
		eventListeners.remove(eventListener);
	}
	
	
	/**
	 * Called to process key events.
	 * @param event
	 * @return
	 */
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		boolean dispatched = false;
		
		for (UIEventListener evListener : eventListeners) {
			dispatched |= evListener.onKeyEvent(event);
		}
		
		return dispatched;
	}
	
	/**
	 * 	Called to process population of AccessibilityEvents.
	 * @param event
	 * @return
	 */
	public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
		
		boolean dispatched = false;
		
		for (UIEventListener evListener : eventListeners) {
			dispatched |= evListener.onPopulateAccessibilityEvent(event);
		}
		
		return dispatched;
		
	}

	/**
	 * 	Called to process touch screen events.
	 * @param event
	 * @return
	 */
	public boolean dispatchTouchEvent(MotionEvent event) {
		
		boolean dispatched = false;
		
		for (UIEventListener evListener : eventListeners) {
			dispatched |= evListener.onTouchEvent(event);
		}		
		return dispatched;		
	}

	/**
	 * 	Called to process trackball events.
	 * @param event
	 * @return
	 */
	public boolean dispatchTrackballEvent(MotionEvent event) {
		
		boolean dispatched = false;
		
		for (UIEventListener evListener : eventListeners) {
			dispatched |= evListener.onTrackballEvent(event);
		}
		return dispatched;	
	}

}
