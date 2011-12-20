package es.eucm.eadandroid.homeapp.repository.connection;

import android.content.Context;
import android.os.Handler;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.homeapp.repository.database.RepositoryDatabase;

public class RepositoryServices {
	
	private UpdateDatabaseThread data_updater;
	private DownloadGameThread game_downloader;
	
	public RepositoryServices() {
		
	}

	public void updateDatabase(Context c, Handler handler, RepositoryDatabase rd) {

		data_updater = new UpdateDatabaseThread(handler, rd);
		data_updater.start();

	}

	public void downloadGame(Context c , Handler handler , GameInfo game) {
		
		game_downloader = new DownloadGameThread(c,handler,game);
		game_downloader.start();
		
	}
	
}
