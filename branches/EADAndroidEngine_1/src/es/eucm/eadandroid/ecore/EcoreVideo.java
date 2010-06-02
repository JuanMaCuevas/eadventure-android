package es.eucm.eadandroid.ecore;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import es.eucm.eadandroid.R;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.data.chapter.scenes.Videoscene;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.homeapp.HomeTabActivity;
import es.eucm.eadandroid.res.pathdirectory.Paths;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;
import es.eucm.eadandroid.utils.ActivityPipe;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class EcoreVideo extends Activity implements SurfaceHolder.Callback {

	// private GameThread gameThread;
	private VideoSurfaceView videoSurfaceView;
	private SurfaceHolder videoHolder;
	private MediaPlayer mediaPlayer;
	
	private boolean started=false;
	private int timeconsumed;
	

	/**
	 * Videoscene being played
	 */
	private Videoscene videoscene;
	


	/**
	 * activity Handler
	 */
	public Handler ActivityHandler = new Handler() {
		@Override
		/**    * Called when a message is sent to Engines Handler Queue **/
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case ActivityHandlerMessages.GAME_OVER:
				finishapplication(false);
				
				break;
			case ActivityHandlerMessages.LOAD_GAMES:
				finishapplication(true);
				
				break;
			}

		}

	};


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		videoscene = (Videoscene) Game.getInstance().getCurrentChapterData()
				.getGeneralScene(
						Game.getInstance().getNextScene().getNextSceneId());

		setContentView(R.layout.video_state);

		videoSurfaceView = (VideoSurfaceView) findViewById(R.id.video_surface);
		videoHolder = videoSurfaceView.getHolder();
		videoHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		videoHolder.addCallback(this);
		GameThread.getInstance().setHandler(ActivityHandler);

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		
	if (!started)
		{
		this.started=true;
		this.prepare();
	}else {
			mediaPlayer.setDisplay(this.videoHolder);
			try {
				mediaPlayer.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mediaPlayer.start();
		

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	public Resources createResourcesBlock() {

		// Get the active resources block
		Resources newResources = null;
		for (int i = 0; i < videoscene.getResources().size()
				&& newResources == null; i++)
			if (new FunctionalConditions(videoscene.getResources().get(i)
					.getConditions()).allConditionsOk())
				newResources = videoscene.getResources().get(i);

		// If no resource block is available, create a default, empty one
		if (newResources == null) {
			newResources = new Resources();
		}
		return newResources;
	}

	public void prepare() {
		try {

			this.mediaPlayer = new MediaPlayer();

			final Resources resources = createResourcesBlock();

			try {
				mediaPlayer
						.setDataSource(ResourceHandler
								.getInstance()
								.getMediaPath(
										resources
												.getAssetPath(Videoscene.RESOURCE_TYPE_VIDEO)));
				mediaPlayer.setDisplay(this.videoHolder);
				mediaPlayer.prepare();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				public void onCompletion(MediaPlayer mp) {
					mediaPlayer.release();
					mediaPlayer=null;
					changeactivity();

				}
			});

			// TODO creo q no lo vamos a necesitar
			// this.blockingPrefetch( );

			
			// mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		} catch (Exception e) {

		}
	}

	public void changeactivity() {
		Intent i = new Intent(this, ECoreActivity.class);
		i.putExtra("before_video", true);
		this.startActivity(i);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 3, 0, "Quit Game").setIcon(android.R.drawable.ic_menu_add);
		menu.add(0, 4, 0, "Load game").setIcon(android.R.drawable.ic_menu_directions);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 3:
			this.finishthread(false);
				return true;
		case 4:		
			this.finishthread(true);
				return true;
		}
		return false;
	}
	
	public String  time()
	{
		
		Date now = new Date();
		String day="none";
		 switch( now.getDay() ) {
		 case 0:
			 day="sun";
			 break;
		 case 1:
			 day="mon";
			 break;
		 case 2:
			 day="tues";
			 break;
		 case 3:
			 day="wed";
			 break;
		 case 4:
			 day="thurs";
			 break;
		 case 5:
			 day="fri";
			 break;
		 case 6:
			 day="sat";
			 break;
		 
		 }
		 int month=now.getMonth()+1;
         String time=new String("MONTH_"+month+"_DAY_"+day+"_HOUR_"+now.getHours()+"_MIN_"+now.getMinutes()+"_SEC_"+now.getSeconds());
		return time;
	}
	
	public void finishthread(boolean load)
	{
		
	
		  boolean retry = true;
		  if (GameThread.getInstance()!=null) {
			  GameThread.getInstance().finish(load);
		
		  }
		  
		 	
	}
	private void finishapplication(boolean loadgames)
	{
		if(!loadgames)
		{		Intent i = new Intent(this, HomeTabActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("tabstate", HomeTabActivity.GAMES);
		startActivity(i);
		}
		else 
		{
			Intent i = new Intent(this, HomeTabActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtra("tabstate", HomeTabActivity.LOAD_GAMES);
			startActivity(i);
		}
		
		mediaPlayer.release();
		mediaPlayer=null;
		
		this.finish();
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		//this is because on pause can be called when 
		//luego lo hare por ahora no
		if (this.mediaPlayer!=null)
		this.mediaPlayer.pause();
		
		this.videoSurfaceView=null;
		
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (videoSurfaceView==null)
		{
			videoSurfaceView = (VideoSurfaceView) findViewById(R.id.video_surface);
			videoHolder = videoSurfaceView.getHolder();
			videoHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			this.videoSurfaceView.setFocusable(true);
			videoHolder.addCallback(this);
			
		}
	}
}
