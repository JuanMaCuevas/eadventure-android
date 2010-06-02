package es.eucm.eadandroid.homeapp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.loadsavedgames.LoadSavedGames;
import es.eucm.eadandroid.homeapp.localgames.LocalGamesActivity;
import es.eucm.eadandroid.homeapp.preferences.PreferencesActivity;
import es.eucm.eadandroid.homeapp.repository.RepositoryActivity;

public class HomeTabActivity extends TabActivity {
	
	public static final int GAMES = 0;
	public static final int LOAD_GAMES = 1;
	public static final int REPOSITORY = 2;
	public static final int PREFERENCES = 3;


	private TabHost mTabHost;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	

	private void init() {


		mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("tab_games").setIndicator("Games",
				getResources().getDrawable(R.drawable.monitor))
				.setContent(new Intent(this, LocalGamesActivity.class)));
		
		mTabHost.addTab(mTabHost.newTabSpec("load_games").setIndicator(
				"Load game",
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
	
	


	
}