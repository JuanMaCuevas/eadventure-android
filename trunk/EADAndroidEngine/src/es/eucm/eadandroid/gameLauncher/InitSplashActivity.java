package es.eucm.eadandroid.gameLauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import es.eucm.saxprototype.R;

public class InitSplashActivity extends Activity {

	public static final String VERSION = "1.0b";
	
  
	/** Called when Engine activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
			
	}
	
	
	/** Starts GameLauncher when the user touches the screen*/
	public boolean onTouchEvent (MotionEvent event) {
				
		if (event.getAction()==MotionEvent.ACTION_DOWN) {
		
		startGameSelectActivity();
		return true;
		
		}
		
		else return false;
	
	}
	
	
	
    /** Starts GameLauncher and finishes current Engine Activity */
    private void startGameSelectActivity() {
    	
 		Intent i = new Intent( this , GameLauncherActivity.class);   			
		startActivity(i);        		
	   
    	
    }
	

}