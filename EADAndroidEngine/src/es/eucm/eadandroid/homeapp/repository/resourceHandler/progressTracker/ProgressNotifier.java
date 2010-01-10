package es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ProgressNotifier extends TaskNode {

	private int progress;

	public class ProgressMessage {

		public static final int PROGRESS_ERROR = -1;
		public static final int PROGRESS_PERCENTAGE = 0;
		public static final int PROGRESS_FINISHED = 1;

	}

	protected Handler handler;

	public ProgressNotifier(Handler handler) {
		super();
		this.handler = handler;
		this.progress = 0;
	}

	public ProgressTask createRootTask(String name, String desc) {
		return new ProgressTask(this, name, desc);
	}

	public void notifyProgress(int nprogress, String currentOpMsg) {

		// this avoids activity handler to dispatch to many messages
		if (nprogress - this.progress >= 1) {

			this.progress = nprogress;
			handler.removeMessages(ProgressMessage.PROGRESS_PERCENTAGE);
			Message msg = handler.obtainMessage();
			msg.what = ProgressMessage.PROGRESS_PERCENTAGE;
			Bundle b = new Bundle();
			b.putString("msg", currentOpMsg);
			b.putInt("ptg", nprogress);
			msg.setData(b);

			handler.sendMessage(msg);

		}

	}

	public void notifyFinished(String progressFinishedMsg) {

		handler.removeMessages(ProgressMessage.PROGRESS_PERCENTAGE);
		Message msg = handler.obtainMessage();
		msg.what = ProgressMessage.PROGRESS_FINISHED;
		Bundle b = new Bundle();
		b.putString("msg", progressFinishedMsg);

		msg.setData(b);

		handler.sendMessage(msg);

	}

	public void notifyError(String progressErrorMsg) {

		handler.removeMessages(ProgressMessage.PROGRESS_PERCENTAGE);
		Message msg = handler.obtainMessage();
		msg.what = ProgressMessage.PROGRESS_ERROR;
		Bundle b = new Bundle();
		b.putString("msg", progressErrorMsg);

		msg.setData(b);

		handler.sendMessage(msg);

	}

}
