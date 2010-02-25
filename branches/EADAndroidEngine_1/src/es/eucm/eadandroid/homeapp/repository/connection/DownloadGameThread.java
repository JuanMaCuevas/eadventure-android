package es.eucm.eadandroid.homeapp.repository.connection;

import android.content.Context;
import android.os.Handler;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker.ProgressNotifier;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class DownloadGameThread extends Thread {

	private GameInfo game;
	private ProgressNotifier pn;

	public DownloadGameThread(Context c, Handler handler, GameInfo game) {
		
		this.game=game;
		
		pn = new ProgressNotifier(handler);
	//	this.pt = pn.createRootTask("Update repository database", "Update everything related with repository database");
		
	}

	@Override
	public void run() {


		RepoResourceHandler.downloadFileAndUnzip(game.getEadUrl(),Paths.eaddirectory.GAMES_PATH , game.getFileName() , pn);

//		RepoResourceHandler.downloadFile(game.getEadUrl(),EXTERNAL_STORAGE, game.getFileName() , pn);
//				RepoResourceHandler.unzip(game.getEadUrl());
		
	}

}
