package es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events;

import android.view.MotionEvent;

public class TapEvent extends UIEvent{

	public MotionEvent event ;

	public TapEvent(MotionEvent event) {
		super(UIEvent.TAP_ACTION);
		this.event = event;
	}
	
}
