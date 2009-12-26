package es.eucm.eadandroid.core;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.core.control.GameThread;
import es.eucm.eadandroid.core.gui.GameSurfaceView;

public class GameActivity extends Activity {

	private static final String TAG = "DrawProof";

	private GameSurfaceView mSuperficie;
	private GameThread mThread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// turn off the window's title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// tell system to use the layout defined in our XML file

		setContentView(R.layout.gamecanvas);
		// get handles to the view from XML, and its LunarThread
		mSuperficie = (GameSurfaceView) findViewById(R.id.canvas_surface);
		mThread = mSuperficie.getThread();
		// mSuperficie.getThread().unpause();

		if (savedInstanceState == null) {
			// we were just launched: set up a new game
			mThread.setState(GameThread.STATE_READY);
			Log.w(this.getClass().getName(), "SIS is null");
		} else {
			// we are being restored: resume a previous game
			mThread.restoreState(savedInstanceState);
			mThread.setRunning(true);// .resume();
			Log.w(this.getClass().getName(), "SIS is nonnull");
		}

	}

	/**
	 * Invoked when the Activity loses user focus.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mSuperficie.getThread().pause(); // pause game when Activity pauses

	}

	/**
	 * Notification that something is about to happen, to give the Activity a
	 * chance to save state.
	 * 
	 * @param outState
	 *            a Bundle into which this Activity should save its state
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// just have the View's thread save its state into our Bundle
		super.onSaveInstanceState(outState);
		mThread.saveState(outState);
		Log.w(this.getClass().getName(), "SIS called");
	}

	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			mThread.unpause();
			/*
			 * switch (mThread.getState()) { case STATE_READY:
			 * mThread.unpause(); return true; case mThread.STATE_PAUSE:
			 * mThread.unpause(); return true; case mThread.STATE_RUNNING:
			 * mThread.pause() return true; }
			 */

		}

		return false;

	}

}
