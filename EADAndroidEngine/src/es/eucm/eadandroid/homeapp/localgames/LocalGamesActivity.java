package es.eucm.eadandroid.homeapp.localgames;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.common.data.adventure.ChapterSummary;
import es.eucm.eadandroid.common.data.adventure.DescriptorData;
import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.loader.Loader;
import es.eucm.eadandroid.common.loader.incidences.Incidence;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

/**
 * @author Alvaro
 * 
 */
public class LocalGamesActivity extends ListActivity {


	public static final String TAG = "GameLauncher";

	ProgressDialog dialog;

	String[] advList = null;

	/**
	 * Local games activity handler messages . Handled by {@link
	 * LGActivityHandler}
	 * Defines the messages handled by this Activity
	 */
	public class LGAHandlerMessages {

		public static final int GAMES_FOUND = 0;
		public static final int NO_GAMES_FOUND = 1;
		public static final int NO_SD_CARD = 2;

	}

	/**
	 * Local games activity Handler
	 */
	private Handler LGActivityHandler = new Handler() {
		@Override
		/**    * Called when a message is sent to Engines Handler Queue **/
		public void handleMessage(Message msg) {

			dialog.dismiss();
			setContentView(R.layout.local_games_activity);

			switch (msg.what) {

			case LGAHandlerMessages.GAMES_FOUND: {
				Bundle b = msg.getData();
				advList = b.getStringArray("adventuresList");
				insertAdventuresToList(advList);
				break;
			}
			case LGAHandlerMessages.NO_GAMES_FOUND:
				break;
			case LGAHandlerMessages.NO_SD_CARD:
				showAlert("No SD card mounted");
				break;

			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT); // ?????

		// Inform the list we provide context menus for items
		this.getListView().setOnCreateContextMenuListener(this);

		searchForGames();

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		String adventureName = this.getListView().getItemAtPosition(position)
				.toString();

		Log.d(TAG, "AdventureName : " + adventureName);

		File sdCard = Environment.getExternalStorageDirectory();

		String adventureAbsPath = sdCard.getAbsolutePath();
		Log.d(TAG, "AdventureAbsolutePath : " + adventureAbsPath);

		String advPath = adventureAbsPath + "/" + adventureName;
		Log.d(TAG, "PathToFile : " + advPath);

		ResourceHandler.setRestrictedMode(false);
		ResourceHandler.getInstance().setZipFile(advPath);
		DescriptorData gameDescriptor = Loader
				.loadDescriptorData(ResourceHandler.getInstance());

		Log.d(TAG, "AdventuresDescription loaded : "
				+ gameDescriptor.getDescription().toString());

		int currentChapter = 0;

		// Extract the chapter
		ChapterSummary chapter = gameDescriptor.getChapterSummaries().get(
				currentChapter);

		Chapter gameData;

		// Load the script data
		gameData = Loader.loadChapterData(ResourceHandler.getInstance(),
				chapter.getChapterPath(), new ArrayList<Incidence>(), true);

		Log.d(TAG, "ChapterData loaded : " + gameData.getTitle());

		ResourceHandler.getInstance().closeZipFile();
		ResourceHandler.delete();

	}

	private void insertAdventuresToList(String[] advList) {

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, advList));
		getListView().setTextFilterEnabled(true);

	}

	/** Starts SearchGamesThread -> searches for ead games */
	private void searchForGames() {

		dialog = ProgressDialog.show(this, "", "Searching for ead games...",
				true);
		SearchGamesThread t = new SearchGamesThread(this, LGActivityHandler);
		t.start();

	}

	private void showAlert(String msg) {

		new AlertDialog.Builder(this).setMessage(msg).setNeutralButton("OK",
				null).show();

	}

}
