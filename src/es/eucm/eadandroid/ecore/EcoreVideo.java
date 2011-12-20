package es.eucm.eadandroid.ecore;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.data.chapter.scenes.Videoscene;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.homeapp.WorkspaceActivity;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class EcoreVideo extends Activity {

	// private GameThread gameThread;
	/*private VideoSurfaceView videoSurfaceView;
	private SurfaceHolder videoHolder;
	private MediaPlayer mediaPlayer;*/
	
	private boolean started=false;
	private int timeconsumed;
	
	private Resources resources;
	
	private final static int STATIC_INTEGER_VALUE = 5;

	/**
	 * Videoscene being played
	 */
	private Videoscene videoscene;
	


	/**
	 * activity Handler
	 */
	public Handler ActivityHandler = new Handler() {
		@Override
		
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


	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		videoscene = (Videoscene) Game.getInstance().getCurrentChapterData().getGeneralScene(Game.getInstance().getNextScene().getNextSceneId());

		resources = createResourcesBlock();
		/*setContentView(R.layout.video_state);

		videoSurfaceView = (VideoSurfaceView) findViewById(R.id.video_surface);
		
		videoHolder = videoSurfaceView.getHolder();
		videoHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		videoHolder.addCallback(this);*/
		GameThread.getInstance().setHandler(ActivityHandler);
		
		this.play();

	}
	

	/*public void surfaceCreated(SurfaceHolder holder) {
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
		
		if (Game.getInstance().getFunctionalScene()!=null)
			Game.getInstance().getFunctionalScene().playBackgroundMusic();

	}*/

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
	
	public void play() {
		
		 String destAddr = ResourceHandler.getInstance().getMediaPath(resources.getAssetPath(Videoscene.RESOURCE_TYPE_VIDEO));	
		 String pack="com.redirectin.rockplayer.android.unified.lite";
		 Uri uri = Uri.parse(destAddr.toString());
		 Intent i = new Intent("android.intent.action.VIEW");		 
		 i.setPackage(pack);
		 i.setDataAndType(uri, "video/*");		 
		 startActivityForResult(i, STATIC_INTEGER_VALUE);
		 
		 if (Game.getInstance().getFunctionalScene()!=null)
				Game.getInstance().getFunctionalScene().playBackgroundMusic();
	}
	
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
	  super.onActivityResult(requestCode, resultCode, data); 
	  switch(requestCode) { 
	    case (STATIC_INTEGER_VALUE) : { 
	    	this.changeActivity();
	      break; 
	    } 
	  } 
	}


	/*public void prepare() {
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
	}*/

	public void changeActivity() {
		Intent i = new Intent(this, ECoreActivity.class);
		i.putExtra("before_video", true);
		this.startActivity(i);
		this.finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 3, 0, "Quit Game").setIcon(android.R.drawable.ic_menu_add);
		menu.add(0, 4, 0, "Load game").setIcon(android.R.drawable.ic_menu_directions);

		return true;
	}

	@Override
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
	
	/*public String  time()
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
	}*/
	
	public void finishthread(boolean load)
	{
		
		  if (GameThread.getInstance()!=null) {
			  GameThread.getInstance().finish(load);
		
		  }
		  
		 	
	}
	private void finishapplication(boolean loadgames)
	{
		
		Intent i = new Intent(this, WorkspaceActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);		
		
		this.finish();
	}

	
	
	
	
	@Override
	protected void onPause() {
		super.onPause();
		//this is because on pause can be called when 
		//luego lo hare por ahora no
		/*if (this.mediaPlayer!=null)
		this.mediaPlayer.pause();
		
		this.videoSurfaceView=null;*/
		
		
		
	}
	
	
	
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		/*if (videoSurfaceView==null)
		{
			videoSurfaceView = (VideoSurfaceView) findViewById(R.id.video_surface);
			videoSurfaceView.setFocusable(true);
		//	gameSurfaceView.setFocusable(true);
			videoHolder = videoSurfaceView.getHolder();
			videoHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			
		
			videoHolder.addCallback(this);
			
			
			
			
			
		}*/
	}
}
