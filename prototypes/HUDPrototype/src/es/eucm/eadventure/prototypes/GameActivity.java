package es.eucm.eadventure.prototypes;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import es.eucm.eadventure.prototypes.control.GameThread;

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
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		SensorManager sm = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
		sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// tell system to use the layout defined in our XML file

		setContentView(R.layout.main);
		// get handles to the view from XML, and its LunarThread
		mSuperficie = (GameSurfaceView) findViewById(R.id.lunar);
		mThread = mSuperficie.getThread();

	}

	@Override
	protected void onResume() {
		super.onResume();
		mThread.unpause();
	}

	/**
	 * Invoked when the Activity loses user focus.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mThread.pause(); // pause game when Activity pauses

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#dispatchKeyEvent(android.view.KeyEvent)
	 */
	public boolean dispatchKeyEvent(KeyEvent event) {
		return mThread.processKeyEvent(event);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
	 */
	public boolean dispatchTouchEvent(MotionEvent event) {
		boolean dispatched = mThread.processTouchEvent(event);
			
			// don't allow more than 60 motion events per second
			try {
			Thread.sleep(30);
			} catch (InterruptedException e) {
			}
		return dispatched;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.Activity#dispatchTrackballEvent(android.view.MotionEvent)
	 */
	public boolean dispatchTrackballEvent(MotionEvent event) {
		return mThread.processTrackballEvent(event);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.Activity#dispatchTrackballEvent(android.view.MotionEvent)
	 */
	public boolean dispatchSensorEvent(SensorEvent event) {
		return mThread.processSensorEvent(event);
	}

}
