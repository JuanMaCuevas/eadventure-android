package es.eucm.eadandroid.homeapp.loadsavedgames;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import es.eucm.eadandroid.homeapp.loadsavedgames.LoadSavedGames.SavedGamesHandlerMessages;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.utils.ActivityPipe;

public class Searchingsavedgames extends Thread {

	Handler handler;

	public Searchingsavedgames(Handler han) {
		super();
		handler = han;

	}

	public void run() {

		InfoExpandabletable info = null;

		RepoResourceHandler.updatesavedgames();

		info = RepoResourceHandler.getexpandablelist();

		if (info.getChildren() == null) {

			Message msg = handler.obtainMessage();
			Bundle b = new Bundle();

			msg.what = SavedGamesHandlerMessages.NOGAMES;
			msg.setData(b);
			msg.sendToTarget();
			
		} else {

			String key = ActivityPipe.add(info); 

			Message msg = handler.obtainMessage();
			Bundle b = new Bundle();
			b.putString("loadingsavedgames", key);
			msg.what = SavedGamesHandlerMessages.GAMES;
			msg.setData(b);
			msg.sendToTarget();
		}

	}

}