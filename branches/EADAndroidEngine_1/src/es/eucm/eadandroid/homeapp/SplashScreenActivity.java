package es.eucm.eadandroid.homeapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
			Log.e("Inicio aplicaci—n",String.valueOf(Debug.getNativeHeapAllocatedSize()));
			
			setContentView(R.layout.splash_screen);

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
	
		
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			startEngineHomeActivity();
			return true;
		}




		public void startEngineHomeActivity() {

			Intent i = new Intent(this, HomeTabActivity.class);
			i.putExtra("tabstate",HomeTabActivity.GAMES);
			startActivity(i);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			this.finish();

		}
	
}
