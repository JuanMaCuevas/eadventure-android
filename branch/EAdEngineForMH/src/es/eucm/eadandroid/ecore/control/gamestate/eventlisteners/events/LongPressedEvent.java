package es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events;

import android.view.MotionEvent;

public class LongPressedEvent extends UIEvent{

	public MotionEvent event ;

	public LongPressedEvent(MotionEvent event) {
		super(UIEvent.LONG_PRESSED_ACTION);
		this.event = event;
	}
	
}
