package es.eucm.eadventure.prototypes;

import es.eucm.eadventure.prototypes.control.GameThread;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewConfiguration;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	/** The thread that actually draws the animation */
	private GameThread thread;

	/** Handle to the application context, used to e.g. fetch Drawables. */
	private Context mContext;

	/** Pointer to the text view to display "Paused.." etc. */
	// private TextView mStatusText;

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		// create thread only; it's started in surfaceCreated()
		thread = new GameThread(holder, context, null);

		setFocusable(true); // make sure we get key events

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	//	thread.setSurfaceSize(width, height);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread.start();
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
/*		if (!hasWindowFocus)
			thread.pause();*/
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		thread.finish();
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}

	}

	public GameThread getThread() {
		return thread;
	}

}
