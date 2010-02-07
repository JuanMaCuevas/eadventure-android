package es.eucm.eadventure.prototypes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import es.eucm.eadventure.prototypes.control.GameThread;

public class GameSurfaceView extends SurfaceView {

	/** The thread that actually draws the animation */
	private GameThread thread;

	/** Handle to the application context, used to e.g. fetch Drawables. */
	private Context mContext;

	/** Pointer to the text view to display "Paused.." etc. */
	// private TextView mStatusText;

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setFocusable(true); // make sure we get key events

	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
/*		if (!hasWindowFocus)
			thread.pause();*/
	}



}
