package es.eadengine.saxprototype;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SearchGamesThread  extends Thread {

	     private Context context;
	     private Handler handler;

	     
	     /** 
	      * @param ctx -> contains the system context
	      * @param ha -> Thread Handle Queue to send messages to.
	      */
	     public SearchGamesThread(Context ctx, Handler ha) {
	          context = ctx;
	          handler = ha;
	     }

	     /**
	      * Starts the thread and searches for ead games in the device ,
	      * when task is finished it sends a GAMES_LOADED message through "ha" handler .
	      */
	     @Override
	     public void run() {

			 try {
				 
			 Resources res = context.getResources();
			 AssetManager assMan = res.getAssets();
			
			 String adventures[] =  assMan.list("adventures");
		
			 
			 Log.d(this.getClass().getSimpleName(),"Searches for adventures in assets/adventures -> "+ adventures[0] + "....");
			 
			 Bundle b = new Bundle();
			 
			 b.putStringArray("adventuresList", adventures);

			 
			 
			 
			 Message msg = new Message(); 
	         msg.what = GlobalMessages.GAMES_LOADED;
	         msg.setData( b );
	         handler.sendMessage(msg);
		     
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     	    	 
	         
	} 
}
