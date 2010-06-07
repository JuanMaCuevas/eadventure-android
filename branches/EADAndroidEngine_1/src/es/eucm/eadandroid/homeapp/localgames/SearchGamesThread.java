package es.eucm.eadandroid.homeapp.localgames;

import java.io.File;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import es.eucm.eadandroid.homeapp.localgames.LocalGamesActivity.LGAHandlerMessages;
import es.eucm.eadandroid.res.filefilters.EADFileFilter;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class SearchGamesThread extends Thread {

	private Context context;
	private Handler handler;

	/**
	 * @param ctx
	 *            -> contains the system context
	 * @param ha
	 *            -> Thread Handle Queue to send messages to.
	 */
	public SearchGamesThread(Context ctx, Handler ha) {
		context = ctx;
		handler = ha;
	}

	/**
	 * Starts the thread and searches for ead games in externalstorage SDCard ,
	 * when task is finished it sends a GAMES_FOUND message through "ha" handler
	 * .
	 */

	@Override
	public void run() {


		Message msg = handler.obtainMessage();
		
		
	//	if (!new File(entry.getName()).exists())

		Log.d("SearcgGamesThread", "SDCard state : "
				+ Environment.getExternalStorageState().toString());

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			
			String adventures[]=null;
			File games = new File(Paths.eaddirectory.GAMES_PATH);
			
			if(games.exists())
			adventures = games.list(new EADFileFilter());

			if (adventures != null && adventures.length > 0) {
				Log.d("SearchGamesThread", "EAD files in sdCard : "
						+ adventures[0]);

				Bundle b = new Bundle();
				b.putStringArray("adventuresList", adventures);
				msg.what = LGAHandlerMessages.GAMES_FOUND;
				msg.setData(b);

			}

			else {

				msg.what = LGAHandlerMessages.NO_GAMES_FOUND;

			}

		}

		else {

			msg.what = LGAHandlerMessages.NO_SD_CARD;
		}
		
		msg.sendToTarget();

	}
}
