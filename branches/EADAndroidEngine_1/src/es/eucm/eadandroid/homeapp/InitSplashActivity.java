package es.eucm.eadandroid.homeapp;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.common.auxiliar.ReleaseFolders;
import es.eucm.eadandroid.common.gui.TC;
import es.eucm.eadandroid.ecore.control.config.ConfigData;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class InitSplashActivity extends Activity {
	
	private AnimationDrawable init_animation;
	
    private static String languageFile = ReleaseFolders.LANGUAGE_UNKNOWN;
	
	private class AnimationTimer extends TimerTask {
		 AnimationDrawable animation;

		 public AnimationTimer(AnimationDrawable animation) {
		  this.animation = animation;
		 }

		 @Override
		 public void run() {
		  animation.start();
		  this.cancel();
		 }
	}

	/** Called when Engine activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.init_splash_activity);
		
		ImageView animationImage = (ImageView) findViewById(R.id.init_animation);
		animationImage.setBackgroundResource(R.anim.splash_animation);
		init_animation = (AnimationDrawable) animationImage.getBackground();
		
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
	
	  /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();

        (new Timer(false)).schedule(new AnimationTimer(init_animation), 100);
    }

	/** Starts GameLauncher when the user touches the screen */
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			init_animation.stop();
			startEngineHomeActivity();
			return true;
		} else
			return false;

	}

	/** Starts GameLauncher and finishes current Engine Activity */
	private void startEngineHomeActivity() {

		Intent i = new Intent(this, HomeTabActivity.class);
		startActivity(i);

	}

}