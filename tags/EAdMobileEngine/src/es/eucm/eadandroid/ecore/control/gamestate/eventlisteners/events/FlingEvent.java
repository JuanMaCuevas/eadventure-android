package es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events;

import android.view.MotionEvent;

public class FlingEvent extends UIEvent{
	
	public MotionEvent eventSrc ;
	public MotionEvent eventDst;
	public float velocityX;
	public float velocityY ;
	
	public FlingEvent(MotionEvent eventSrc, MotionEvent eventDst,
			float velocityX, float velocityY) {
		super(UIEvent.FLING_ACTION);
		this.eventSrc = eventSrc;
		this.eventDst = eventDst;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}
	
	

}
