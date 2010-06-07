package es.eucm.eadandroid.homeapp.localgames;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.ecore.ECoreActivity;
import es.eucm.eadandroid.ecore.ECoreControl;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.res.pathdirectory.Paths;

/**
 * @author Alvaro
 * 
 */
public class LocalGamesActivity extends ListActivity {

	public static final String TAG = "LocalGamesActivity";

	private String[] advList = null;

	private ArrayList<GameInfo> m_games;
	private LocalGamesListAdapter m_adapter;

	LayoutAnimationController controller;

	ProgressDialog dialog;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("Options");
		menu.setHeaderIcon(R.drawable.dialog_icon);
		menu.add(0, 0, 0, "Play");
		menu.add(0, 1, 0, "Uninstall");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo information = (AdapterContextMenuInfo) item
				.getMenuInfo();
		

		switch (item.getItemId()) {

		case 0:

			GameInfo selectedAdventure = (GameInfo) this.getListView()
					.getItemAtPosition(information.position);

			Intent i = new Intent(this, ECoreActivity.class);
			i.putExtra("AdventureName", selectedAdventure.getGameTitle());

			this.startActivity(i);
			break;

		case 1:
			String[] paths = new String[2];
			paths[0] = Paths.eaddirectory.GAMES_PATH
					+ m_games.get(information.position).getGameTitle() + "/";
			paths[1] = Paths.eaddirectory.SAVED_GAMES_PATH
					+ m_games.get(information.position).getGameTitle() + "/";
			DeletingGame instance = new DeletingGame(LGActivityHandler, paths);
			instance.start();
			
			dialog = new ProgressDialog(LocalGamesActivity.this);
			dialog.setTitle("eAdventure");
			dialog.setIcon(R.drawable.dialog_icon);
			dialog.setMessage("Removing game...");
			dialog.setIndeterminate(true);
			dialog.show();

			break;

		}

		return true;

	}

	/**
	 * Local games activity handler messages . Handled by
	 * {@link LGActivityHandler} Defines the messages handled by this Activity
	 */
	public class LGAHandlerMessages {

		public static final int GAMES_FOUND = 0;
		public static final int NO_GAMES_FOUND = 1;
		public static final int NO_SD_CARD = 2;
		public static final int DELETING_GAME = 3;

	}

	/**
	 * Local games activity Handler
	 */
	private Handler LGActivityHandler = new Handler() {
		@Override
		/**    * Called when a message is sent to Engines Handler Queue **/
		public void handleMessage(Message msg) {

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
			case LGAHandlerMessages.DELETING_GAME:
				dialog.setIndeterminate(false);
				dialog.dismiss();
				searchForGames();
				break;

			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout();
	}

	@Override
	protected void onResume() {
		super.onResume();
		searchForGames();
		
	}

	private void setLayout() {
		setContentView(R.layout.local_games_activity);

		m_games = new ArrayList<GameInfo>();
		m_adapter = new LocalGamesListAdapter(this,
				R.layout.local_games_activity_listitem, m_games);
		setListAdapter(m_adapter);

		AnimationSet set = new AnimationSet(true);


//		Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//				(float) - 10, Animation.RELATIVE_TO_SELF, 0.0f);
		
		Animation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
		Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
		0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		
		animation2.setDuration(400);
		set.addAnimation(animation2);
		
//		Animation animation = new AlphaAnimation(0.0f, 1.0f);
//		animation.setDuration(1000);
//		set.addAnimation(animation);

		controller = new LayoutAnimationController(set, 0.5f);

		getListView().setLayoutAnimation(controller);
		getListView().setTextFilterEnabled(true);
		registerForContextMenu(getListView());

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		GameInfo selectedAdventure = (GameInfo) this.getListView()
				.getItemAtPosition(position);
		Intent i = new Intent(this, ECoreControl.class);
		i.putExtra("AdventureName", selectedAdventure.getGameTitle());

		this.startActivity(i);

	}

	private void insertAdventuresToList(String[] advList) {

		for (int i = 0; i < advList.length; i++)
			m_games.add(new GameInfo(advList[i], "", "", null, null));

		m_adapter.notifyDataSetChanged();

	}

	/** Starts SearchGamesThread -> searches for ead games */
	private void searchForGames() {

		m_games.clear();
		SearchGamesThread t = new SearchGamesThread(this, LGActivityHandler);
		t.start();

	}

	private void showAlert(String msg) {

		new AlertDialog.Builder(this).setMessage(msg).setNeutralButton("OK",
				null).setIcon(R.drawable.dialog_icon).setTitle("External Storage").show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.title_icon, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		searchForGames();
		return true;

	}

}
