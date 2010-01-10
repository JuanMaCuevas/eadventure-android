package es.eucm.eadandroid.homeapp;

import java.io.File;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TabHost;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.localgames.LocalGamesActivity;
import es.eucm.eadandroid.homeapp.preferences.PreferencesActivity;
import es.eucm.eadandroid.homeapp.repository.RepositoryActivity;
import es.eucm.eadandroid.res.filefilters.EADFileFilter;

public class HomeTabActivity extends TabActivity {

	String adventures[];
	private TabHost mTabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		setContentView(R.layout.home_tab_activity);

		mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("tab_games").setIndicator("Games",
				getResources().getDrawable(android.R.drawable.ic_menu_gallery))
				.setContent(new Intent(this, LocalGamesActivity.class)));

		mTabHost.addTab(mTabHost.newTabSpec("tab_downloads").setIndicator(
				"Download Games",
				getResources().getDrawable(android.R.drawable.ic_menu_add))
				.setContent(new Intent(this, RepositoryActivity.class)));

		mTabHost.addTab(mTabHost.newTabSpec("tab_preferences").setIndicator(
				"Preferences",
				getResources().getDrawable(
						android.R.drawable.ic_menu_preferences)).setContent(
				new Intent(this, PreferencesActivity.class)));

		mTabHost.setCurrentTab(0);

		File sdCard = Environment.getExternalStorageDirectory();
		adventures = sdCard.list(new EADFileFilter());

	}

	
}