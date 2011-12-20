package es.eucm.eadandroid.homeapp.repository.connection;

import android.content.Context;
import android.os.Handler;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.ProgressNotifier;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class DownloadGameThread extends Thread {

	private GameInfo game;
	private ProgressNotifier pn;

	public DownloadGameThread(Context c, Handler handler, GameInfo game) {
		
		this.game = game;
		pn = new ProgressNotifier(handler);
		
	}

	@Override
	public void run() {


		RepoResourceHandler.downloadFileAndUnzip(game.getEadUrl(), Paths.eaddirectory.GAMES_PATH , game.getFileName() , pn);

		pn.notifityGameInstalled();
		
	}

}
