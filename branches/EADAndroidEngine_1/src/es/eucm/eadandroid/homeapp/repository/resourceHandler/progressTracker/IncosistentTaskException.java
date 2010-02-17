package es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker;

public class IncosistentTaskException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IncosistentTaskException(String errorMessage) {
		super(errorMessage);
	}

}
