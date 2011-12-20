package es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events;

import android.view.MotionEvent;

public class OnDownEvent extends UIEvent {

	public MotionEvent event;

	public OnDownEvent(MotionEvent event) {
		super(UIEvent.ON_DOWN_ACTION);
		this.event = event;
	}

}
