package es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker;

public abstract class TaskNode {
	
	protected static final int MAX_PROGRESS_VALUE = 100;
	
	public abstract void notifyProgress(int progress, String progressMsg);
	public abstract void notifyFinished(String finishedMsg);
	public abstract void notifyError(String errorMsg);


}
