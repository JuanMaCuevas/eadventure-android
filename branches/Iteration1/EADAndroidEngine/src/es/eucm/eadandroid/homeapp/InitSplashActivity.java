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

public class InitSplashActivity extends Activity {
	
	private AnimationDrawable init_animation;
	
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