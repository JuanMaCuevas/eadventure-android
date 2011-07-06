package es.eucm.eadandroid.homeapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.apkinstalling.EngineResInstaller;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class SplashScreenActivity extends Activity{
	
		private Runnable endSplash;
		private boolean installing = false;
		private Uri data;

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
					dialog.dismiss();
					break;
				}

			}

		};

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			//DEBUG
			Log.e("Inicio aplicacion",String.valueOf(Debug.getNativeHeapAllocatedSize()));
			
			setContentView(R.layout.splash_screen);

			if (!RepoResourceHandler.checkEadDirectory(Paths.eaddirectory.ROOT_PATH)) {
				installing = true;
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
			
			if (this.getIntent().getData() != null){
				data = this.getIntent().getData();
			}
			
			if (!installing){
				endSplash = new Runnable() {
					public void run() {
						startEngineHomeActivity();
					}
				};
		    
				ActivityHandler.postDelayed(endSplash, 3000);
			}
		}		
			
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			startEngineHomeActivity();
			ActivityHandler.removeCallbacks(endSplash);
			return true;
		}
		

		public void startEngineHomeActivity() {

			Intent i = new Intent(this, HomeTabActivity.class);
			i.putExtra("tabstate",HomeTabActivity.GAMES);
			if (data != null)
				i.setData(data);			
			startActivity(i);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			this.finish();

		}
	
}
