package es.eucm.eadandroid.homeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import es.eucm.eadandroid.R;

public class InitSplashActivity extends Activity {

	/** Called when Engine activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.init_splash_activity);

	}

	/** Starts GameLauncher when the user touches the screen */
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
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