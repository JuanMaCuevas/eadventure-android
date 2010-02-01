package es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events;

import android.view.MotionEvent;

public class FlingEvent extends UIEvent{
	
	public MotionEvent eventScr ;
	public MotionEvent eventDst;
	public float velocityX;
	public float velocityY ;
	
	public FlingEvent(MotionEvent eventScr, MotionEvent eventDst,
			float velocityX, float velocityY) {
		super(UIEvent.FLING_ACTION);
		this.eventScr = eventScr;
		this.eventDst = eventDst;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}
	
	

}
