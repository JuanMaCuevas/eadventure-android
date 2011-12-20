package es.eucm.eadandroid.homeapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.loadsavedgames.LoadSavedGames;
import es.eucm.eadandroid.homeapp.localgames.LocalGamesActivity;
import es.eucm.eadandroid.homeapp.preferences.PreferencesActivity;
import es.eucm.eadandroid.homeapp.repository.RepositoryActivity;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class HomeTabActivity extends TabActivity {
	
	public static final int GAMES = 0;
	public static final int LOAD_GAMES = 1;
	public static final int REPOSITORY = 2;
	public static final int PREFERENCES = 3;


	private TabHost mTabHost;
	
	private String path_from = null;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		init();
		if (this.getIntent().getData() != null){
			String data = this.getIntent().getData().getPath();
			installEadGame(data);
		}
	}
	

	private void init() {

		mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("tab_games").setIndicator("Installed Games",
				getResources().getDrawable(R.drawable.monitor))
				.setContent(new Intent(this, LocalGamesActivity.class)));
		
		mTabHost.addTab(mTabHost.newTabSpec("load_games").setIndicator(
				"Load Saved Games",
				getResources().getDrawable(
						R.drawable.flag)).setContent(
				new Intent(this, LoadSavedGames.class)));

		mTabHost.addTab(mTabHost.newTabSpec("tab_downloads").setIndicator(
				"Repository",
				getResources().getDrawable(R.drawable.cloud))
				.setContent(new Intent(this, RepositoryActivity.class)));
		
		mTabHost.addTab(mTabHost.newTabSpec("tab_preferences").setIndicator(
				"Preferences",
				getResources().getDrawable(
						R.drawable.equalizer)).setContent(
				new Intent(this, PreferencesActivity.class)));
		
	}

	
	@Override
	protected void onNewIntent(Intent intent) {
				
		int current= intent.getExtras().getInt("tabstate");
		mTabHost.setCurrentTab(current);
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
	
}