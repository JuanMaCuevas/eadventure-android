package es.eucm.eadandroid.homeapp;


import es.eucm.eadandroid.R;
import es.eucm.eadandroid.common.auxiliar.ReleaseFolders;
import es.eucm.eadandroid.common.gui.TC;
import es.eucm.eadandroid.ecore.control.config.ConfigData;
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
	
    private static String languageFile = ReleaseFolders.LANGUAGE_UNKNOWN;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		

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
				if (video != null){
					if (video.isPlaying())
						video.stop();
					video.release();
				}

				

				video.release();
				startEngineHomeActivity();
			}
		});
		
		
        // Load the configuration
        ConfigData.loadFromXML(ReleaseFolders.configFileEngineRelativePath( ) );

        /* We«e got to set the language from the device locale ;D */       
        setLanguage(ReleaseFolders.getLanguageFromPath( ConfigData.getLanguangeFile( ) ) );

	}
	
    /**
     * Sets the current language of the editor. Accepted values are
     * {@value #LANGUAGE_ENGLISH} & {@value #LANGUAGE_ENGLISH}. This method
     * automatically updates the about, language strings, and loading image
     * parameters.
     * 
     * The method will reload the main window if reloadData is true
     * 
     * @param language
     */
    public static void setLanguage( String language ) {

        if( true ) {
            ConfigData.setLanguangeFile(ReleaseFolders.getLanguageFilePath( language ),ReleaseFolders.getAboutFilePath( language ) );
            languageFile = language;
            TC.loadStrings( ReleaseFolders.getLanguageFilePath4Engine( languageFile ) );
        }
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
		i.putExtra("tabstate", 0);
		startActivity(i);
		
		this.finish();

	}
}