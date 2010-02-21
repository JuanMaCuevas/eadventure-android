package es.eucm.eadandroid.homeapp.repository.connection;

import java.io.IOException;

import android.content.Context;
import android.os.Handler;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker.ProgressNotifier;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class DownloadGameThread extends Thread {

	private static final String REPO_FULLPATH = Paths.repository.DEFAULT_PATH
			+ Paths.repository.SOURCE_XML;
	private static final String EXTERNAL_STORAGE = Paths.device.EXTERNAL_STORAGE;

	private Context context;
	private Handler handler;
	private GameInfo game;
	private ProgressNotifier pn;

	public DownloadGameThread(Context c, Handler handler, GameInfo game) {
		
		this.context = c;
		this.handler = handler;
		this.game=game;
		
		pn = new ProgressNotifier(handler);
	//	this.pt = pn.createRootTask("Update repository database", "Update everything related with repository database");
		
	}

	@Override
	public void run() {

		// TODO check if needs update
		try {
			downloadGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void downloadGame() throws IOException {

		RepoResourceHandler.downloadFile(game.getEadUrl(), EXTERNAL_STORAGE, pn);
	
		RepoResourceHandler.unzip(game.getEadUrl());
	}

}
