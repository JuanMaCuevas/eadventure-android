package es.eucm.eadandroid.homeapp.apkinstalling;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class EngineResInstaller extends Thread {

	Context con;
	Handler han;

	public EngineResInstaller(Context con, Handler handler) {
		super();

		this.con = con;
		han = handler;

	}
	
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
	public void run() {
		this.init();

		Message msg = han.obtainMessage();
		Bundle b = new Bundle();
		msg.what = ActivityHandlerInstalling.FINISHISTALLING;
		msg.setData(b);
		msg.sendToTarget();

	}

	private void init() {
		if (!new File(Paths.eaddirectory.ROOT_PATH).exists()) {

			try {
				InputStream is = con.getAssets().open("EadAndroid.zip");
				BufferedOutputStream file;
				file = new BufferedOutputStream(new FileOutputStream(
						"/sdcard/EadAndroid.zip"));
				RepoResourceHandler.copyInputStream(is, file);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			RepoResourceHandler.unzip(Paths.device.EXTERNAL_STORAGE,
					Paths.device.EXTERNAL_STORAGE, "EadAndroid.zip", true);
		}
	}

}
