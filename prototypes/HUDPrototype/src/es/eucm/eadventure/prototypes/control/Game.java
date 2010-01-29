package es.eucm.eadventure.prototypes.control;

import android.hardware.SensorEvent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import es.eucm.eadventure.prototypes.control.gamestates.GameState;
import es.eucm.eadventure.prototypes.control.gamestates.scene.SceneGameState;
import es.eucm.eadventure.prototypes.gui.GUI;

public class Game {


	/**
	 * Global Game state
	 */
	public static final int STATE_PAUSE = 0;
	public static final int STATE_RUNNING = 1;

	/**
	 * GAME INSTANCE
	 */
	private static Game instance = null;

	/**
	 * Game running
	 */

	private boolean mRun = false;

	/** Global Game State variable -> paused or running */
	private int mMode;

	/**
	 * GAMESTATE ( CONTROL)
	 */

	private GameState currentGameState;




	private Game() {

		mRun = true ;
		mMode = STATE_RUNNING;
		
		currentGameState = new SceneGameState();
		
	}

	public static void create() {

		instance = new Game();

	}

	public static Game getInstance() {

		return instance;
	}
	


	public void start() {

        long totalTime = 0;
        long elapsedTime = 0;
        long oldTime = System.currentTimeMillis( );
        long lastFps = 0;
        long time;
        int fps = 0;
        int oldFps = 0;

		while (mRun) {

		  if (mMode == STATE_RUNNING) {
			  
			  time = System.currentTimeMillis( );
              elapsedTime = time - oldTime;
              oldTime = time;
              totalTime += elapsedTime;
              if( time - lastFps < 1000 ) {
                  fps++;
              }
              else {
                  lastFps = time;
                  oldFps = fps;
                  fps = 1;
                  //if( !GUI.getInstance( ).getFrame( ).isFocusOwner( ) ) {
                  //    GUI.getInstance( ).getFrame( ).requestFocusInWindow( );
                  //}
              }
              
              
			  
			  currentGameState.mainLoop(elapsedTime, oldFps);
			 
			  GUI.getInstance().drawFPS((int)oldFps);
			  
			  GUI.getInstance().doPushDraw();
			  
		  }
		  
		  }
			
	}
	

	public void pause() {

		mMode = STATE_PAUSE;

	}

	public void unpause() {

		mMode = STATE_RUNNING;

	}
	
	public void finish() {
		mRun = false;
	}
	
	
	
	public boolean processTouchEvent(MotionEvent e) {
		return currentGameState.processTouchEvent(e);
	}
	
	public boolean processTrackballEvent(MotionEvent e) {
		return currentGameState.processTrackballEvent(e);
	}
	
	public boolean processKeyEvent(KeyEvent e) {
		return currentGameState.processKeyEvent(e);
	}
		
	public boolean processSensorEvent(SensorEvent e) {
		return currentGameState.processSensorEvent(e);
	}

}
