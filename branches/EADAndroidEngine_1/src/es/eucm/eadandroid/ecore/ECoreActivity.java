package es.eucm.eadandroid.ecore;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.common.data.adventure.ChapterSummary;
import es.eucm.eadandroid.common.data.adventure.DescriptorData;
import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.loader.Loader;
import es.eucm.eadandroid.common.loader.incidences.Incidence;
import es.eucm.eadandroid.res.pathdirectory.Paths;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class ECoreActivity extends Activity {

	public static String TAG = "ECoreActivity";

	private GameSurfaceView surfaceView;
	private GameThread gameThread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// turn off the window's title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// tell system to use the layout defined in our XML file
		setContentView(R.layout.game_activity_canvas);

		// get handles to the view from XML, and its LunarThread

		surfaceView = (GameSurfaceView) findViewById(R.id.canvas_surface);

		gameThread = new GameThread(surfaceView.getHolder(), this, null);

		surfaceView.setSurfaceObserver(gameThread);

		String adventureName = (String) this.getIntent().getExtras().get(
				"adventureName");
		String advPath = Paths.eaddirectory.GAMES_PATH + "/" + adventureName;
		Log.d(TAG, "PathToFile : " + advPath);

		gameThread.setAdventurePath(advPath);

		gameThread.start();

	}
	/**
	 * Invoked when the Activity loses user focus.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		gameThread.pause(); // pause game when Activity pauses

	}

	/**
	 * Notification that something is about to happen, to give the Activity a
	 * chance to save state.
	 * 
	 * @param outState
	 *            a Bundle into which this Activity should save its state
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// just have the View's thread save its state into our Bundle
		super.onSaveInstanceState(outState);
		gameThread.saveState(outState);
	}

}
