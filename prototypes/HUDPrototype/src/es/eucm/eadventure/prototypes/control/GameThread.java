package es.eucm.eadventure.prototypes.control;

import android.content.Context;
import android.hardware.SensorEvent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import es.eucm.eadventure.prototypes.gui.GUI;

public class GameThread extends Thread {

		
	/** Context of the activity creating the thread */
	private Context mContext;
	
	
	public GameThread(Context context,
			Handler handler) {

		mContext = context;
		Game.create();
							
	}

	
	@Override
	public void run() {
		
		Game.getInstance().start();

	}
	
	
	public void pause() {
		if (Game.getInstance()!=null) {
		Game.getInstance().pause();
		}
		
	}
	
	public void unpause() {
	
		if (Game.getInstance()!=null) {
		Game.getInstance().unpause();
		}
	}
	
	public void finish() {
		Game.getInstance().finish();
	}


	public boolean processTouchEvent(MotionEvent e) {
		return Game.getInstance().processTouchEvent(e);
	}
	
	public boolean processTrackballEvent(MotionEvent e) {
		return Game.getInstance().processTrackballEvent(e);
	}
	
	public boolean processKeyEvent(KeyEvent e) {
		return Game.getInstance().processKeyEvent(e);
	}
		
	public boolean processSensorEvent(SensorEvent e) {
		return Game.getInstance().processSensorEvent(e);
	}

	
}