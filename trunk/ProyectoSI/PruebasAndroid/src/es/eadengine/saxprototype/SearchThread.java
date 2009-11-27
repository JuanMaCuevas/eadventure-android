package es.eadengine.saxprototype;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class SearchThread  extends Thread {

	     private Context myContext;
	     private Handler myHandler;

	     public SearchThread(Context ctx, Handler ha) {
	          myContext = ctx;
	          myHandler = ha;
	     }

	     @Override
	     public void run() {
	    	 
	    	 
	    	 try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	    	 Message msg = new Message(); 
	         msg.what = GlobalMessages.HANDLER_RESULT;
	         myHandler.sendMessage(msg);
	         
	} 
}
