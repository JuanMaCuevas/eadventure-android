package es.eucm.eadandroid.homeapp;

import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.apkinstalling.EngineResInstaller;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

public class ActivityVideoIntro extends Activity implements
		SurfaceHolder.Callback {
	VideoView surfacevideo = null;
	MediaPlayer video = null;
	SurfaceHolder holder = null;

	public class ActivityHandlerInstalling {

		public static final int FINISHISTALLING = 0;

	}
	
	
	ProgressDialog dialog;
	public Handler ActivityHandler = new Handler() {
		@Override
		/**    * Called when a message is sent to Engines Handler Queue **/
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case ActivityHandlerInstalling.FINISHISTALLING:
				dialog.setIndeterminate(false);
				//startactivity();
				dialog.dismiss();
				break;
			}

		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//DEBUG
		Log.e("Inicio aplicaci—n",String.valueOf(Debug.getNativeHeapAllocatedSize()));
		
		startactivity();

		if (!RepoResourceHandler.checkEadDirectory(Paths.eaddirectory.ROOT_PATH)) {
			EngineResInstaller is = new EngineResInstaller(this,
					ActivityHandler);
			is.start();
			
			dialog = new ProgressDialog(this);
			dialog.setTitle("Welcome to eAdventure");
			dialog.setIcon(R.drawable.dialog_icon);
			dialog.setMessage("Please wait...\nSetting up engine resources");
			dialog.setIndeterminate(true);
			dialog.show();
			
		} 

	}

	private void startactivity() {
		
		setContentView(R.layout.introduction_video);
		
		this.surfacevideo = (VideoView) findViewById(R.id.VideoView01);
		this.holder = surfacevideo.getHolder();
		holder.addCallback(this);
		video = new MediaPlayer();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		ImageButton playbutton = (ImageButton) findViewById(R.id.ImageButton02);
		playbutton.setBackgroundResource(android.R.drawable.ic_media_play);
		playbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				video.start();
				arg0.setVisibility(View.INVISIBLE);
				surfacevideo.setBackgroundColor(View.INVISIBLE);

			}
		});

		Button skip = (Button) findViewById(R.id.ImageButton01);
		skip.setText("Skip intro");
		skip.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (video != null) {
					if (video.isPlaying())
						video.stop();
					video.release();
				}

				video.release();
				startEngineHomeActivity();
			}
		});



	}

	
	public void surfaceCreated(SurfaceHolder holder2) {
		video = new MediaPlayer();
		video.setScreenOnWhilePlaying(true);
		try {
			video.setDataSource(Paths.eaddirectory.ROOT_PATH + "intro_ead.mp4");
			video.setDisplay(holder);
			video.prepare();

		} catch (Exception e) {
		}

		video.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer mp) {
				video.release();
			}
		});

		video.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		Log.d("surfaceChanged()", "surfaceChanged...");
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		Log.d("surfaceDestroyed()", "surfaceDestroyed...");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (video != null) {
			video.release();
			video = null;
		}
		// llama al siguiente activity

	}

	public void startEngineHomeActivity() {

		Intent i = new Intent(this, HomeTabActivity.class);
		i.putExtra("tabstate",HomeTabActivity.GAMES);
		startActivity(i);
 //       overridePendingTransition(R.anim.fade, R.anim.hold);
		this.finish();

	}
}