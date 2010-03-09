package es.eucm.eadandroid.ecore;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.common.auxiliar.ReleaseFolders;
import es.eucm.eadandroid.common.gui.TC;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.config.ConfigData;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.homeapp.localgames.LocalGamesActivity.LGAHandlerMessages;
import es.eucm.eadandroid.res.pathdirectory.Paths;
//TODO esto a lo mejor aqui no
import es.eucm.eadandroid.ecore.control.gamestate.GameStateVideoscene;

public class ECoreActivity extends Activity implements SurfaceHolder.Callback{

	public static String TAG = "ECoreActivity";
	
	private GameSurfaceView gameSurfaceView;
	private VideoSurfaceView videoSurfaceView;
	private GameThread gameThread;
	
	
	private WindowManager window;
	
	
	
	private View assesmentLayout;
    private View mbutton;
    private WebView webview;
   // private View buttoncontainer;
    
 /**
	 * Local games activity handler messages . Handled by {@link
	 * LGActivityHandler}
	 * Defines the messages handled by this Activity
	 */
	public class ActivityHandlerMessages {

		public static final int ASSESSMENT = 0;
		public static final int VIDEO = 1;
		public static final int GAME_OVER = 2;

	}

	/**
	 *  activity Handler
	 */
	public Handler ActivityHandler = new Handler() {
		@Override
		/**    * Called when a message is sent to Engines Handler Queue **/
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case ActivityHandlerMessages.ASSESSMENT: {
				Bundle b = msg.getData();
				String text = b.getString("html");
				webview.loadData(text, "text/html", "utf-8");
				assesmentLayout.setVisibility(View.VISIBLE);
				mbutton.setVisibility(View.VISIBLE);

				break;
			}
			case ActivityHandlerMessages.VIDEO:

				setContentView(R.layout.game_activity_canvas);
				window.removeViewImmediate(gameSurfaceView);
				// gameSurfaceView.setFocusable(false);
				// gameSurfaceView=null;
				videoSurfaceView.setZOrderMediaOverlay(true);
				videoSurfaceView.setZOrderOnTop(true);
				videoSurfaceView.bringToFront();

				// SurfaceHolder canvasHolder = gameSurfaceView.getHolder();
				// canvasHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
				// GUI.getInstance().setCanvasSurfaceHolder(canvasHolder);
				GameStateVideoscene videoscene;
				videoscene = (GameStateVideoscene) Game.getInstance()
						.getCurrentState();
				videoscene.play();
				break;

			case ActivityHandlerMessages.GAME_OVER:
				finish();
			}

		}

	};
    
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
		
		gameSurfaceView = (GameSurfaceView) findViewById(R.id.canvas_surface);
		
		SurfaceHolder canvasHolder = gameSurfaceView.getHolder();
				
		// register our interest in hearing about changes to our surface
		//TODO tengo que descomentar esta linea 
		canvasHolder.addCallback(this);
		
		videoSurfaceView = (VideoSurfaceView) findViewById(R.id.video_surface);
		SurfaceHolder videoHolder = videoSurfaceView.getHolder();
		videoHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		
	    gameThread = new GameThread(canvasHolder,videoHolder,this,ActivityHandler );
			String adventureName = (String) this.getIntent().getExtras().get(
				"AdventureName");
		String advPath = Paths.eaddirectory.GAMES_PATH + adventureName +"/";	
		
		gameThread.setAdventurePath(advPath);
		
		
		assesmentLayout= findViewById(R.id.hidecontainer);
        mbutton=findViewById(R.id.hideme1);
        webview=(WebView) findViewById(R.id.webview);
		webview.setVerticalScrollBarEnabled(true);
		webview.setVerticalScrollbarOverlay(true);
		assesmentLayout.setBackgroundColor(Color.BLACK);
		//webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		 mbutton.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					
					Game.getInstance().getAssessmentEngine().statedone();
					assesmentLayout.setVisibility(View.INVISIBLE);
					 mbutton.setVisibility(View.INVISIBLE);
					
					
				}});
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
		if (gameThread!=null) {
		gameThread.finish();
		while (retry) {
			try {
				gameThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
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
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	public boolean onTouchEvent(MotionEvent event)
	{
	
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
