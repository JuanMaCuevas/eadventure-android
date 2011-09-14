package es.eucm.eadandroid.homeapp;

/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

import es.eucm.eadandroid.R;
import es.eucm.eadandroid.ecore.ECoreActivity;
import es.eucm.eadandroid.ecore.ECoreControl;
import es.eucm.eadandroid.homeapp.loadsavedgames.LoadGamesArray;
import es.eucm.eadandroid.homeapp.loadsavedgames.MyListAdapter;
import es.eucm.eadandroid.homeapp.loadsavedgames.Searchingsavedgames;
import es.eucm.eadandroid.homeapp.localgames.DeletingGame;
import es.eucm.eadandroid.homeapp.localgames.LocalGamesListAdapter;
import es.eucm.eadandroid.homeapp.localgames.SearchGamesThread;
import es.eucm.eadandroid.homeapp.preferences.PreferencesActivity;
import es.eucm.eadandroid.homeapp.repository.connection.RepositoryServices;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.homeapp.repository.database.RepositoryDatabase;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.ProgressNotifier.ProgressMessage;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;
import es.eucm.eadandroid.utils.ActivityPipe;
import es.eucm.eadandroid.utils.ViewPagerIndicator;

public class WorkspaceActivity extends FragmentActivity {
	
    public static final int NUM_ITEMS = 3;
    public static final int GAMES = 0;
	public static final int LOAD_GAMES = 1;
	public static final int REPOSITORY = 2;
	public static int tag = -1;
	protected static LoadGamesListFragment load_games;
	protected static RepositoryListFragment repository;

    private PagerAdapter mAdapter;
    protected ViewPager mPager;
    private ViewPagerIndicator indicator;
    private String path_from = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);
        
        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setHomeAction(new IntentAction(this, createIntent(this, WorkspaceActivity.class), R.drawable.launcher_icon3));
        actionBar.setTitle("eAdventure Mobile");
        actionBar.addAction(new IntentAction(this, createIntent(this, PreferencesActivity.class), android.R.drawable.ic_menu_preferences));

        mAdapter = new PagerAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
     // Find the indicator from the layout
        indicator = (ViewPagerIndicator) findViewById(R.id.indicator);
		
        // Set the indicator as the pageChangeListener
        mPager.setOnPageChangeListener(indicator);
        // Initialize the indicator. We need some information here:
        // * What page do we start on.
        // * How many pages are there in total
        // * A callback to get page titles
		indicator.init(0, NUM_ITEMS, mAdapter);
		Resources res = getResources();
		Drawable prev = res.getDrawable(R.drawable.indicator_prev_arrow);
		Drawable next = res.getDrawable(R.drawable.indicator_next_arrow);
		
		// Set images for previous and next arrows.
		indicator.setArrows(prev, next);
		
		if (this.getIntent().getData() != null){
			String data = this.getIntent().getData().getPath();
			installEadGame(data);
		}

    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if (repository != null && indicator.getFocusedPage() == REPOSITORY) 
    		return repository.onKeyDown(keyCode, event);       	
    	else return super.onKeyDown(keyCode, event);
	    
	}
    
    public static Intent createIntent(Context context, Class<?> c) {
        Intent i = new Intent(context, c);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }
	
	private String getPathFrom() {
		return path_from;
	}
	
	private void installEadGame(String path_from) {
		
		this.path_from = path_from;		
		this.showDialog(DIALOG_INSTALL_ID);
		
		Thread t = new Thread(new Runnable() {
			public void run()
			{					
				String path_from = getPathFrom();
				int last = path_from.lastIndexOf("/");
				String gameFileName = path_from.substring(last + 1);
				path_from= path_from.substring(0, last+1);
				RepoResourceHandler.unzip(path_from,Paths.eaddirectory.GAMES_PATH,gameFileName,false);
				dismissDialog(DIALOG_INSTALL_ID);
			}
		});
		
		t.start();					
	}	
	
	static final int DIALOG_INSTALL_ID = 0;

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_INSTALL_ID:
			ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setCancelable(false);		
			progressDialog.setTitle("Please wait");
			progressDialog.setIcon(R.drawable.dialog_icon);
			progressDialog.setMessage("Installing game...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			dialog = progressDialog;			
			break;
		default:
			dialog = null;
		}
		return dialog;
	}
	
	public void onDestroy(){
		
		System.gc();
		super.onDestroy();
	}
	

    public static class PagerAdapter extends FragmentPagerAdapter implements ViewPagerIndicator.PageInfoProvider {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
        	Fragment f = null;
            switch (position){
            	case GAMES: f = LocalGamesListFragment.newInstance();
            			break;
            	case LOAD_GAMES: load_games = LoadGamesListFragment.newInstance();
            			return load_games;  
            	case REPOSITORY: repository = RepositoryListFragment.newInstance();
    					return repository;
            }
            return f;            
        }

		public String getTitle(int position) {
			String title = null;
			switch (position){
        	case GAMES: title = "Installed games";
        			break;
        	case LOAD_GAMES: title = "Saved games";
					break;
        	case REPOSITORY: title = "Games repository";
					break;      
			}
			return title;
		}

    }
    
    public static class LocalGamesListFragment extends ListFragment {
    	
        private ArrayList<GameInfo> m_games;
        private LocalGamesListAdapter m_adapter;
        ProgressDialog dialog;
        private String[] advList = null;

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

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static LocalGamesListFragment newInstance() {
        	LocalGamesListFragment f = new LocalGamesListFragment();
            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.local_games_activity, container, false);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            registerForContextMenu(this.getListView());
            m_games = new ArrayList<GameInfo>();
            m_adapter = new LocalGamesListAdapter(getActivity(),
    				R.layout.local_games_activity_listitem, m_games);
            setListAdapter(m_adapter);
            searchForGames();            
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            
            GameInfo selectedAdventure = (GameInfo) this.getListView()
			.getItemAtPosition(position);
            Intent i = new Intent(getActivity(), ECoreControl.class);
            i.putExtra("AdventureName", selectedAdventure.getGameTitle());
            this.
            getActivity().startActivity(i);
        }
        
        @Override
    	public void onCreateContextMenu(ContextMenu menu, View v,
    			ContextMenuInfo menuInfo) {
        	tag = GAMES;
    		menu.setHeaderTitle("Options");
    		menu.setHeaderIcon(R.drawable.dialog_icon);
    		menu.add(0, 0, 0, "Play Game");
    		menu.add(0, 1, 0, "Uninstall Game");
    	}

    	@Override
    	public boolean onContextItemSelected(MenuItem item) {
    		
    		if (tag != GAMES) return super.onContextItemSelected(item);
    		
    		AdapterContextMenuInfo information = (AdapterContextMenuInfo) item
    				.getMenuInfo();    		

    		switch (item.getItemId()) {

    		case 0:

    			GameInfo selectedAdventure = (GameInfo) this.getListView()
    					.getItemAtPosition(information.position);

    			Intent i = new Intent(getActivity(), ECoreControl.class);
    			i.putExtra("AdventureName", selectedAdventure.getGameTitle());

    			getActivity().startActivity(i);
    			break;

    		case 1:
    			String[] paths = new String[2];
    			paths[0] = Paths.eaddirectory.GAMES_PATH
    					+ m_games.get(information.position).getGameTitle() + "/";
    			paths[1] = Paths.eaddirectory.SAVED_GAMES_PATH
    					+ m_games.get(information.position).getGameTitle() + "/";
    			DeletingGame instance = new DeletingGame(LGActivityHandler, paths);
    			instance.start();
    			
    			dialog = new ProgressDialog(getActivity());
    			dialog.setTitle("eAdventure");
    			dialog.setIcon(R.drawable.dialog_icon);
    			dialog.setMessage("Removing game...");
    			dialog.setIndeterminate(true);
    			dialog.show();

    			break;
    		}

    		return true;

    	}
    	
    	private void insertAdventuresToList(String[] advList) {
    		
    		m_games.clear();
    		
    		for (int i = 0; i < advList.length; i++)
    			m_games.add(new GameInfo(advList[i], "", "",BitmapFactory.decodeFile(Paths.eaddirectory.GAMES_PATH+advList[i]+"/icon.png")));

    		m_adapter.notifyDataSetChanged();

    	}

    	/** Starts SearchGamesThread -> searches for ead games */
    	private void searchForGames() {

    		m_games.clear();
    		SearchGamesThread t = new SearchGamesThread(LGActivityHandler);
    		t.start();

    	}

    	private void showAlert(String msg) {

    		new AlertDialog.Builder(this.getActivity()).setMessage(msg).setNeutralButton("OK",
    				null).setIcon(R.drawable.dialog_icon).setTitle("External Storage").show();

    	}
        
    }
    
    public static class LoadGamesListFragment extends ListFragment {
 
        
        private MyListAdapter mAdapter;
    	private LoadGamesArray info = null;

    	public class SavedGamesHandlerMessages {

    		public static final int GAMES = 0;
    		public static final int NOGAMES = 1;

    	}

    	public Handler ActivityHandler = new Handler() {
    		@Override
    		public void handleMessage(Message msg) {

    			switch (msg.what) {
    			case SavedGamesHandlerMessages.GAMES:
    				Bundle b = msg.getData();
    				String text = b.getString("loadingsavedgames");
    				info = (LoadGamesArray) ActivityPipe.remove(text);
    				createlist();
    				break;

    			case SavedGamesHandlerMessages.NOGAMES:
    				nogames();
    				break;
    			}
    		}
    	};


        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static LoadGamesListFragment newInstance() {
        	LoadGamesListFragment f = new LoadGamesListFragment();
            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        
        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.saved_games_activity, container, false);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState); 
        }
    	
    	private void createlist() {

    		mAdapter = new MyListAdapter(this.getActivity(), info.getSavedGames());
    		setListAdapter(mAdapter);
    		registerForContextMenu(this.getListView());

    	}

    	private void nogames() {
    		setListAdapter(null);
    	}

    	@Override
    	public void onListItemClick(ListView l, View v, int position, long id) {
    		
    		if ((!info.getSavedGames().get(position).getGame().equals("No Games"))
    				&& (!info.getSavedGames().get(position).getSaved().equals("Saved but deleted"))) {
    			Intent i = new Intent(this.getActivity(), ECoreActivity.class);
    			i.putExtra("AdventureName", info.getSavedGames().get(position).getGame());
    			i.putExtra("restoredGame", Paths.eaddirectory.SAVED_GAMES_PATH
    					+ info.getSavedGames().get(position).getGame() + "/"
    					+ info.getSavedGames().get(position).getSaved());
    			i.putExtra("savedgame", true);
    			this.startActivity(i);
    		
    		}

    	}

    	@Override
    	public void onCreateContextMenu(ContextMenu menu, View v,
    			ContextMenuInfo menuInfo) {
    		
    		tag = LOAD_GAMES;    		
    		menu.setHeaderTitle("Options");
    		menu.setHeaderIcon(R.drawable.dialog_icon);
    		menu.add(0, 0, 0, "Play");
    		menu.add(0, 1, 0, "Delete");
    	}

    	@Override
    	public boolean onContextItemSelected(MenuItem item) {
    		
    		if (tag != LOAD_GAMES) return super.onContextItemSelected(item);
    		
    		int position = item.getItemId();

    		switch (item.getItemId()) {
    			case 0:
    				if ((!info.getSavedGames().get(position).getGame().equals("No Games"))
    	    				&& (!info.getSavedGames().get(position).getSaved().equals("Saved but deleted"))) {
    					Intent i = new Intent(this.getActivity(), ECoreActivity.class);
    	    			i.putExtra("AdventureName", info.getSavedGames().get(position).getGame());
    	    			i.putExtra("restoredGame", Paths.eaddirectory.SAVED_GAMES_PATH
    	    					+ info.getSavedGames().get(position).getGame() + "/"
    	    					+ info.getSavedGames().get(position).getSaved());
    	    			i.putExtra("savedgame", true);
    	    			this.startActivity(i);
    				}
    				break;
    			case 1:
    				RepoResourceHandler
    						.deleteFile(Paths.eaddirectory.SAVED_GAMES_PATH
    								+ info.getSavedGames().get(position).getGame() + "/"
    								+ info.getSavedGames().get(position).getSaved());
    				RepoResourceHandler
    						.deleteFile(Paths.eaddirectory.SAVED_GAMES_PATH
    								+ info.getSavedGames().get(position).getGame() + "/"
    								+ info.getSavedGames().get(position).getSaved()
    								+ ".png");

    				Toast.makeText(this.getActivity(), "Saved game suscesfully removed",
    						Toast.LENGTH_SHORT).show();

    				refresh();
    				break;
    			}
    		
    		return true;
    	}

    	private void refresh() {
    		Searchingsavedgames gettingdata = new Searchingsavedgames (ActivityHandler);
    		gettingdata.start();
    	}
    	
    	@Override
		public void onResume(){
    		super.onResume();
    		refresh();
    	}
    	
    }

    public static class RepositoryListFragment extends ListFragment {
    	
    	static final int DIALOG_UPDATING_REPO_ID = 0;
    	static final int DIALOG_ERROR_ID = 1;

    	private RepositoryDatabase db;
    	private RepositoryServices rs;

    	private ProgressDialog pd;
    	private LocalGamesListAdapter m_adapter;
    	ViewFlipper mFlipper;
    	
    	private GameInfo selectedGame = null;
    		
    	ProgressDialog progressDialog;

    	private Handler RAHandler = new Handler() {
    		@Override
    		public void handleMessage(Message msg) {

    			String m = null;
    			int perc;
    			
    			ProgressDialog p = null;
    			
    			if (pd.isShowing())
    				p = pd;
    			else if (progressDialog.isShowing())
    				p = progressDialog;
    			
    			if (p!=null) {
    				 	 
    			switch (msg.what) {

    			case ProgressMessage.PROGRESS_PERCENTAGE:

    				p.setIndeterminate(false);
    				p.show();
    				m = msg.getData().getString("msg");
    				perc = msg.getData().getInt("ptg");
    				p.setProgress(perc);
    				p.setMessage(m);
    				break;

    			case ProgressMessage.PROGRESS_UPDATE_FINISHED:

    				p.setIndeterminate(false);
    				p.setProgress(100);
    				databaseUpdated();
    				p.dismiss();
    				break;

    			case ProgressMessage.PROGRESS_ERROR:

    				m = msg.getData().getString("msg");
    				p.setProgress(0);
    				p.setMessage(m);
    				p.dismiss();
    				break;

    			case ProgressMessage.INDETERMINATE:

    				p.setIndeterminate(true);
    				m = msg.getData().getString("msg");
    				p.setMessage(m);


    				break;
    				
    			case ProgressMessage.GAME_INSTALLED:

    				p.setIndeterminate(false);
    				p.dismiss();
    				
    				goToLocalGames();
    			
    				break;    				
    			}    			
    			}
    		}
    	};
    	
    	/**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static RepositoryListFragment newInstance() {
        	RepositoryListFragment f = new RepositoryListFragment();
            return f;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.repository_activity, container, false);
            return v;
        }
        
    	private void goToLocalGames() {
    		
    		((WorkspaceActivity)this.getActivity()).mPager.setCurrentItem(0);
    		
    	}

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            
        }
        
        @Override
		public void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		
    		pd = new ProgressDialog(this.getActivity());
    		pd.setTitle("eAdventure Repository");
    		pd.setIcon(R.drawable.dialog_icon);
    		pd.setMessage("Retrieving data...");
    		//pd.setIndeterminate(false);
    		pd.setCancelable(false);
//    		pd.setButton("Cancel", new DialogInterface.OnClickListener() {
//               public void onClick(DialogInterface dialog, int id) {
//                   RepositoryActivity.this.pd.dismiss();
//               }
//           });
    		pd.show();
    		
    		progressDialog = new ProgressDialog(this.getActivity());
    		progressDialog.setTitle("eAdventure Repository");
    		progressDialog.setIcon(R.drawable.dialog_icon);
    		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		progressDialog.setCancelable(false);

    		db = new RepositoryDatabase();
    		rs = new RepositoryServices();
    		
    		rs.updateDatabase(this.getActivity(), RAHandler, db);

    	}
    	
    		
    	private void databaseUpdated() {
    	
    		setLayout();    		
    		m_adapter.notifyDataSetChanged();

    	}
    	
    	private void setLayout() {

    		mFlipper = ((ViewFlipper) this.getActivity()
    				.findViewById(R.id.repository_activity_flipper));
    		mFlipper.setInAnimation(AnimationUtils.loadAnimation(this.getActivity(),
    				R.anim.zoom_enter));
    		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this.getActivity(),
    				R.anim.zoom_exit));
    		
    		m_adapter = new LocalGamesListAdapter(this.getActivity(),
    				R.layout.repository_activity_listitem, db.getRepoData());

    		setListAdapter(m_adapter);

    		AnimationSet set = new AnimationSet(true);

    		Animation animation = new AlphaAnimation(0.0f, 1.0f);
    		animation.setDuration(1500);
    		set.addAnimation(animation);
    		
    		Animation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
    		Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
    		-1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    		
    		animation2.setDuration(500);
    		set.addAnimation(animation2);

    		LayoutAnimationController controller = new LayoutAnimationController(
    				set, 0.5f);

    		getListView().setLayoutAnimation(controller);
    		getListView().setTextFilterEnabled(true);
    		
    		Button button = (Button) this.getActivity().findViewById(R.id.detailed_game_download_button);
    		
    		button.setOnClickListener(new OnClickListener(){

    			public void onClick(View arg0) {
    				downloadGame();
    			}
    			
    		});
    	}
    	
    	private void downloadGame() {
    		

    		progressDialog.setTitle("Please wait");
    		progressDialog.setMessage("Starting download");
    		progressDialog.show();
    		
    		RepositoryServices rs = new RepositoryServices() ;
    		rs.downloadGame(this.getActivity(), RAHandler, selectedGame);
    	}

    	@Override
		public void onListItemClick(ListView l, View v, int position, long id) {

    		GameInfo selectedGame = (GameInfo) this.getListView()
    				.getItemAtPosition(position);
    		
    		TextView title = (TextView)this.getActivity().findViewById(R.id.detailed_game_title);
    		TextView description = (TextView)this.getActivity().findViewById(R.id.detailed_game_description);
    		ImageView image = (ImageView)this.getActivity().findViewById(R.id.detailed_game_image_icon);
    		
    		title.setText(selectedGame.getGameTitle());
    		description.setText(selectedGame.getGameDescription());
    		if (selectedGame.getImageIcon()!=null)		
    			image.setImageBitmap(selectedGame.getImageIcon());
    		else image.setImageDrawable(this.getResources().getDrawable(R.drawable.icon));
    				
    		mFlipper.showNext();
    		
    		this.selectedGame = selectedGame;

    	}
    	
    	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	    if (keyCode == KeyEvent.KEYCODE_BACK && mFlipper.getDisplayedChild()==1) {
    	    	mFlipper.showPrevious();
    	        return true;
    	    }
    	    else return false;
    	}
    	
    	public boolean onCreateOptionsMenu(Menu menu) {

    		MenuInflater inflater = this.getActivity().getMenuInflater();
    		inflater.inflate(R.menu.title_icon, menu);

    		return true;
    	}

    	@Override
    	public boolean onOptionsItemSelected(MenuItem item) {

    		db.clear();
    		pd = ProgressDialog.show(this.getActivity(), "Please wait...", "Retrieving data ...",
    				true);
    		rs.updateDatabase(this.getActivity(), RAHandler, db);
    		
    		return true;

    	}
    	
    }
    
    
}
