package es.eadengine.saxprototype;

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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import es.eadengine.common.data.adventure.ChapterSummary;
import es.eadengine.common.data.adventure.DescriptorData;
import es.eadengine.common.data.chapter.Chapter;
import es.eadengine.common.loader.Loader;
import es.eadengine.common.loader.incidences.Incidence;
import es.eadengine.resourcehandler.ResourceHandler;


public class GameLauncher extends ListActivity {

	// Menu item ids
	public static final int MENU_ITEM_INSTALL = Menu.FIRST;
	public static final int MENU_ITEM_UNINSTALL = Menu.FIRST + 1;
	
	public static final String TAG = "GameLauncher";

	private String adventureName;
	private String adventurePath;

	ProgressDialog dialog;

	String[] advList = null;

	/**
	 * GameLauncher Handler Queue
	 */
	private Handler GLActivityHandler = new Handler() {
		@Override
		/* * Called when a message is sent to Engines Handler Queue */
		public void handleMessage(Message msg) {

			dialog.dismiss();
			setContentView(R.layout.game_launcher);

			switch (msg.what) {

			case GlobalMessages.GAMES_FOUND: {
				Bundle b = msg.getData();
				advList = b.getStringArray("adventuresList");
				insertAdventuresToList(advList);
				break;
			}
			case GlobalMessages.NO_GAMES_FOUND:
				break;
			case GlobalMessages.NO_SD_CARD:
				showAlert("No SD card mounted");
				break;

			}
		}

	};

	private void showAlert(String msg) {

		new AlertDialog.Builder(this).setMessage(msg).setNeutralButton("OK",
				null).show();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT); // ?????

		// Inform the list we provide context menus for items
		this.getListView().setOnCreateContextMenuListener(this);

		searchForGames();
		

	}

	/** Starts SearchGamesThread -> searches for ead games */
	private void searchForGames() {

		dialog = ProgressDialog.show(this, "", "Searching for ead games...",
				true);
		SearchGamesThread t = new SearchGamesThread(this, GLActivityHandler);
		t.start();

	}

	private void insertAdventuresToList(String[] advList) {

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, advList));
		getListView().setTextFilterEnabled(true);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		
		String adventureName = this.getListView().getItemAtPosition(position).toString();
		
		Log.d(TAG,"AdventureName : "+adventureName);

		File sdCard = Environment.getExternalStorageDirectory();
		
		String adventureAbsPath = sdCard.getAbsolutePath();		
		Log.d(TAG,"AdventureAbsolutePath : "+adventureAbsPath);
		
		String advPath = adventureAbsPath+"/"+adventureName;		
		Log.d(TAG,"PathToFile : "+advPath);
			
		 ResourceHandler.setRestrictedMode(false);
		 ResourceHandler.getInstance().setZipFile(advPath); 
		 DescriptorData gameDescriptor = Loader.loadDescriptorData(ResourceHandler.getInstance());
		 
		 Log.d(TAG,"AdventuresDescription loaded : "+gameDescriptor.getDescription().toString());
		 
		 int currentChapter = 0 ;
		 
		// Extract the chapter
	     ChapterSummary chapter = gameDescriptor.getChapterSummaries( ).get( currentChapter );
	     
	     Chapter gameData;

	        // Load the script data
	     gameData = Loader.loadChapterData( ResourceHandler.getInstance( ), chapter.getChapterPath( ), new ArrayList<Incidence>( ), true );
	     
	     Log.d(TAG,"ChapterData loaded : "+gameData.getTitle());

	     
	     
		 ResourceHandler.getInstance().closeZipFile();
		 ResourceHandler.delete();
		 
		 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// This is our one standard application action -- inserting a
		// new note into the list.
		menu.add(0, MENU_ITEM_INSTALL, 0, R.string.menu_install).setShortcut(
				'3', 'a').setIcon(android.R.drawable.ic_menu_add);

		menu.add(0, MENU_ITEM_UNINSTALL, 0, R.string.menu_uninstall)
				.setShortcut('3', 'a').setIcon(
						android.R.drawable.ic_menu_delete);

		/*
		 * // Generate any additional actions that can be performed on the //
		 * overall list. In a normal install, there are no additional // actions
		 * found here, but this allows other applications to extend // our menu
		 * with their own actions. Intent intent = new Intent(null,
		 * getIntent().getData());
		 * intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		 * menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0, new
		 * ComponentName(this, NotesList.class), null, intent, 0, null);
		 */
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		/*
		 * final boolean haveItems = this.getListAdapter().getCount() > 0;
		 * 
		 * // If there are any games in the list (which implies that one of //
		 * them is selected), then we need to generate the actions that // can
		 * be performed on the current selection. This will be a combination //
		 * of our own specific actions along with any extensions that can be //
		 * found. if (haveItems) { // This is the selected item. Uri uri =
		 * ContentUris.withAppendedId(getIntent().getData(),
		 * getSelectedItemId());
		 * 
		 * // Build menu... always starts with the EDIT action... Intent[]
		 * specifics = new Intent[1]; specifics[0] = new
		 * Intent(Intent.ACTION_EDIT, uri); MenuItem[] items = new MenuItem[1];
		 * 
		 * // ... is followed by whatever other actions are available... Intent
		 * intent = new Intent(null, uri);
		 * intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		 * menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0, null,
		 * specifics, intent, 0, items);
		 * 
		 * // Give a shortcut to the edit action. if (items[0] != null) {
		 * items[0].setShortcut('1', 'e'); } } else {
		 * menu.removeGroup(Menu.CATEGORY_ALTERNATIVE); }
		 */
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ITEM_INSTALL:
			// Launch activity to insert a new item
			// startActivity(new Intent(Intent.ACTION_INSERT,
			// getIntent().getData()));
			return true;
		case MENU_ITEM_UNINSTALL:
			// Launch activity to insert a new item
			// startActivity(new Intent(Intent.ACTION_INSERT,
			// getIntent().getData()));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		} catch (ClassCastException e) {
			Log.e(this.getClass().getSimpleName(), "bad menuInfo", e);
			return;
		}

		String advName = (String) getListAdapter().getItem(info.position);

		// Setup the menu header
		menu.setHeaderTitle(advName);

		// Add a menu item to delete the note
		menu.add(0, MENU_ITEM_UNINSTALL, 0, R.string.menu_uninstall);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		} catch (ClassCastException e) {
			Log.e(this.getClass().getSimpleName(), "bad menuInfo", e);
			return false;
		}

		switch (item.getItemId()) {
		case MENU_ITEM_UNINSTALL: {
			// Delete the note that the context menu is for
			/*
			 * Uri noteUri = ContentUris.withAppendedId(getIntent().getData(),
			 * info.id); getContentResolver().delete(noteUri, null, null);
			 */
			return true;
		}
		}
		return false;
	}

}
