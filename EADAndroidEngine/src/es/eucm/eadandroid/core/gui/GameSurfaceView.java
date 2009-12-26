package es.eucm.eadandroid.core.gui;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import es.eucm.eadandroid.core.control.GameThread;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	/** The thread that actually draws the animation */
	private GameThread thread;

	/** Pointer to the text view to display "Paused.." etc. */
	// private TextView mStatusText;

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		// create thread only; it's started in surfaceCreated()
		thread = new GameThread(holder, context, new Handler() /*
																	 * {
																	 * 
																	 * @Override
																	 * public
																	 * void
																	 * handleMessage
																	 * (Message
																	 * m) {
																	 * mStatusText
																	 * .
																	 * setVisibility
																	 * (
																	 * m.getData
																	 * (
																	 * ).getInt(
																	 * "viz"));
																	 * mStatusText
																	 * .
																	 * setText(m
																	 * .
																	 * getData()
																	 * .
																	 * getString
																	 * (
																	 * "text"));
																	 * } }
																	 */
		);

		setFocusable(true); // make sure we get key events
		// TODO Auto-generated constructor stub*/
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus)
			thread.pause();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		thread.setRunning(false);
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
