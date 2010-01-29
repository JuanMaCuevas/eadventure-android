package es.eucm.eadventure.prototypes.control;

import android.content.Context;
import android.hardware.SensorEvent;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import es.eucm.eadventure.prototypes.gui.GUI;

public class GameThread extends Thread {

		
	/** Context of the activity creating the thread */
	private Context mContext;
	
	
	public GameThread(SurfaceHolder mSurfaceHolder, Context context,
			Handler handler) {

		mContext = context;
		
		Game.create();
		GUI.create(mSurfaceHolder);

							
	}

	
	@Override
	public void run() {
		
		Game.getInstance().start();

	}
	
	
	public void pause() {
		
		Game.getInstance().pause();
		
	}
	
	public void unpause() {
	
		Game.getInstance().unpause();
		
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