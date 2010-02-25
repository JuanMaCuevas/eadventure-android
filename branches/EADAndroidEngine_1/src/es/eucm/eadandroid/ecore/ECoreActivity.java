package es.eucm.eadandroid.ecore;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.common.auxiliar.ReleaseFolders;
import es.eucm.eadandroid.common.gui.TC;
import es.eucm.eadandroid.ecore.control.config.ConfigData;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class ECoreActivity extends Activity implements SurfaceHolder.Callback{

	public static String TAG = "ECoreActivity";
	
	private GameSurfaceView surfaceView;
	private GameThread gameThread;

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
		setContentView(R.layout.game_activity_canvas);
		
		surfaceView = (GameSurfaceView) findViewById(R.id.canvas_surface);
		
		SurfaceHolder holder = surfaceView.getHolder();
				
		// register our interest in hearing about changes to our surface
		holder.addCallback(this);
		
								
		gameThread = new GameThread(holder, this, null);
	
		String adventureName = (String) this.getIntent().getExtras().get(
				"AdventureName");
		String advPath = Paths.eaddirectory.GAMES_PATH + adventureName +"/";	
		
		gameThread.setAdventurePath(advPath);

	}
	
		
	@Override
	protected void onResume() {
		super.onResume();
		gameThread.unpause();
	}

	/**
	 * Invoked when the Activity loses user focus.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		gameThread.pause(); // pause game when Activity pauses

	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
    
	}

	public void surfaceCreated(SurfaceHolder holder) {	

		gameThread.start();
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		gameThread.finish();
		while (retry) {
			try {
				gameThread.join();
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
		
		return gameThread.processKeyEvent(event);

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
	 */
	public boolean dispatchTouchEvent(MotionEvent event) {
		boolean dispatched = gameThread.processTouchEvent(event);
			
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
		return gameThread.processTrackballEvent(event);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.Activity#dispatchTrackballEvent(android.view.MotionEvent)
	 */
	public boolean dispatchSensorEvent(SensorEvent event) {
		return gameThread.processSensorEvent(event);
	}
}
