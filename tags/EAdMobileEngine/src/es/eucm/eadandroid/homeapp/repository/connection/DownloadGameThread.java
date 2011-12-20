package es.eucm.eadandroid.homeapp.repository.connection;

import android.content.Context;
import android.os.Handler;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.ProgressNotifier;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;

/**
 * A thread in charge of downloading games from their url
 * 
 * @author Roberto Tornero
 */
public class DownloadGameThread extends Thread {

	/**
	 * The information of the game where its url is located
	 */
	private GameInfo game;
	/**
	 * A notifier to show the progress of the download process 
	 */
	private ProgressNotifier pn;

	/**
	 * Constructor
	 */
	public DownloadGameThread(Context c, Handler handler, GameInfo game) {
		
		this.game = game;
		this.pn = new ProgressNotifier(handler);		
	}

	/**
	 * Starts the download process of the game from its url
	 */
	@Override
	public void run() {

		RepoResourceHandler.downloadFileAndUnzip(game.getEadUrl(), Paths.eaddirectory.GAMES_PATH, 
				game.getFileName(), pn);
		pn.notifityGameInstalled();		
	}

}
