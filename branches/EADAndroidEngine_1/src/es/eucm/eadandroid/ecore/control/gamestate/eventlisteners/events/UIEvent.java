package es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events;

public abstract class UIEvent {

	public static final int UNPRESSED_ACTION = 0;
	public static final int PRESSED_ACTION = 1;
	public static final int FLING_ACTION = 2;
	public static final int TAP_ACTION = 3;
	public static final int SCROLL_PRESSED_ACTION = 4;
	
	private int action ;
	
	public UIEvent(int action) {
		this.action = action;
	}

	public int getAction() {
		return action;
	}
	
}
