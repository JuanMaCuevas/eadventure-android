package es.eucm.eadventure.prototypes;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import es.eucm.eadventure.prototypes.control.GameThread;
import es.eucm.eadventure.prototypes.gui.GUI;

public class GameActivity extends Activity implements SurfaceHolder.Callback{

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
		
		SurfaceHolder holder = mSuperficie.getHolder();
		// register our interest in hearing about changes to our surface
		holder.addCallback(this);
						
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int landscapeHeight = displaymetrics.heightPixels;
		int landscapeWidth = displaymetrics.widthPixels;
				
		GUI.create(holder);
		GUI.getInstance().init(landscapeHeight,landscapeWidth);
		
		// create thread only; it's started in surfaceCreated()
		mThread = new GameThread(this, null);

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
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	//	thread.setSurfaceSize(width, height);
	}

	public void surfaceCreated(SurfaceHolder holder) {	

		mThread.start();
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		mThread.finish();
		while (retry) {
			try {
				mThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}

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
