package es.eucm.eadandroid.homeapp;


import es.eucm.eadandroid.R;
import es.eucm.eadandroid.res.pathdirectory.Paths;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class ActivityVideoIntro extends Activity implements
		SurfaceHolder.Callback {
	VideoView surfacevideo = null;
	MediaPlayer video = null;
	SurfaceHolder holder = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
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
				if (video.isPlaying())
					video.stop();
				
				startEngineHomeActivity();
				video.release();
			}
		});

	}

	public void surfaceCreated(SurfaceHolder holder2) {
		video = new MediaPlayer();
		video.setScreenOnWhilePlaying(true);
		try {
			video.setDataSource(Paths.eaddirectory.ROOT_PATH+"intro_ead.mp4");
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
		startActivity(i);
		
		this.finish();
		
	//	overridePendingTransition(R.anim.fade, R.anim.hold);
	//	overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);

	}
}