package es.eucm.eadandroid.ecore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView {

	GameThread gameThread = null;

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);

		
		setFocusable(true); // make sure we get key events

	}


}
