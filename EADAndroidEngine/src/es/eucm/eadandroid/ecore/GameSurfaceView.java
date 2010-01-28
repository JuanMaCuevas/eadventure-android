package es.eucm.eadandroid.ecore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	GameThread gameThread = null;

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		
		setFocusable(true); // make sure we get key events

	}

	// el callback fuera de aqui! ;D xq no tiene sentido que lo trate la misma vista , que lo trate el control ..
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		gameThread.setSurfaceSize(width, height);
	}

	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus)
			gameThread.pause();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		gameThread.setRunning(false);
		while (retry) {
			try {
				gameThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}

	}
	
	public void setSurfaceObserver(GameThread gameThread) {
		
		this.gameThread = gameThread;
		
	}

}
