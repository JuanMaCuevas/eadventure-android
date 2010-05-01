package es.eucm.eadandroid.homeapp.localgames;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import es.eucm.eadandroid.homeapp.loadsavedgames.LoadSavedGames.SavedGamesHandlerMessages;
import es.eucm.eadandroid.homeapp.localgames.LocalGamesActivity.LGAHandlerMessages;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class DeletingGame extends Thread {
	
	Handler handler;
	String[] paths;
	
	public DeletingGame(Handler han,String[] paths) {
		super();
		handler=han;
	this.paths=paths;
	}

	@Override
	public void run() {
		
		 RepoResourceHandler.deleteFile(paths[0]);
    	 RepoResourceHandler.deleteFile(paths[1]);
    	 
    	 Message msg = handler.obtainMessage();
         Bundle b = new Bundle();
      
		msg.what = LGAHandlerMessages.DELETING_GAME;
		msg.setData(b);
			msg.sendToTarget();
	}

}
