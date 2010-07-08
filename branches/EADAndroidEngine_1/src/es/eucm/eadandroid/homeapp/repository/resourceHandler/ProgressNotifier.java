package es.eucm.eadandroid.homeapp.repository.resourceHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ProgressNotifier {

	private int progress;

	public class ProgressMessage {

		public static final int PROGRESS_ERROR = -1;
		public static final int PROGRESS_PERCENTAGE = 0;
		public static final int PROGRESS_UPDATE_FINISHED = 1;
		public static final int INDETERMINATE = 2;
		public static final int GAME_INSTALLED = 3;

	}

	protected Handler handler;

	public ProgressNotifier(Handler handler) {
		super();
		this.handler = handler;
		this.progress = 0;
	}

	public void notifyProgress(int nprogress, String currentOpMsg) {

		// this avoids activity handler to dispatch to many messages
		if (nprogress - this.progress >= 1) {

			this.progress = nprogress;
			removeHandlerMessages();
			Message msg = handler.obtainMessage();
			msg.what = ProgressMessage.PROGRESS_PERCENTAGE;
			Bundle b = new Bundle();
			b.putString("msg", currentOpMsg);
			b.putInt("ptg", nprogress);
			msg.setData(b);

			handler.sendMessage(msg);
		}

	}

	public void notifyUpdateFinished(String progressFinishedMsg) {

		removeHandlerMessages();
		Message msg = handler.obtainMessage();
		msg.what = ProgressMessage.PROGRESS_UPDATE_FINISHED;
		Bundle b = new Bundle();
		b.putString("msg", progressFinishedMsg);

		msg.setData(b);

		handler.sendMessage(msg);

	}

	public void notifyError(String progressErrorMsg) {

		removeHandlerMessages();
		Message msg = handler.obtainMessage();
		msg.what = ProgressMessage.PROGRESS_ERROR;
		Bundle b = new Bundle();
		b.putString("msg", progressErrorMsg);

		msg.setData(b);

		handler.sendMessage(msg);

	}

	public void notifyIndeterminate(String string) {
		
		removeHandlerMessages();
		Message msg = handler.obtainMessage();
		msg.what = ProgressMessage.INDETERMINATE;
		Bundle b = new Bundle();
		b.putString("msg", string);

		msg.setData(b);

		handler.sendMessage(msg);
		
	}
	
	public void notifityGameInstalled(){
		
		removeHandlerMessages();
		Message msg = handler.obtainMessage();
		msg.what = ProgressMessage.GAME_INSTALLED;
		Bundle b = new Bundle();

		msg.setData(b);

		handler.sendMessage(msg);
		
	}
	
	private void removeHandlerMessages() {
		

		handler.removeMessages(ProgressMessage.PROGRESS_PERCENTAGE);
		handler.removeMessages(ProgressMessage.PROGRESS_ERROR);
		handler.removeMessages(ProgressMessage.PROGRESS_UPDATE_FINISHED);
		handler.removeMessages(ProgressMessage.GAME_INSTALLED);
		handler.removeMessages(ProgressMessage.INDETERMINATE);
		
	}

}
