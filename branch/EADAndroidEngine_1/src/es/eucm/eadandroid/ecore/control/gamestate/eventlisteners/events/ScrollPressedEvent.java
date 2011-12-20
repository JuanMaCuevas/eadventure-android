package es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events;

import android.view.MotionEvent;

public class ScrollPressedEvent extends UIEvent{

	public MotionEvent eventSrc ;
	public MotionEvent eventDst ;
	public float distanceX;
	public float distanceY;
	
	
	public ScrollPressedEvent(MotionEvent eventSrc, MotionEvent eventDst,
			float distanceX, float distanceY) {
		super(UIEvent.SCROLL_PRESSED_ACTION);
		this.eventSrc = eventSrc;
		this.eventDst = eventDst;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
	}
	
	
}
