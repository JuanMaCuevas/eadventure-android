package es.eucm.eadventure.prototypes.control.gamestates.eventlisteners;

import android.view.MotionEvent;

public interface TouchListener {
	
	public boolean processTouchEvent(MotionEvent ev) ;
	
	public interface CallBack {
				
		public boolean onTap(MotionEvent e);

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) ;

		public boolean onPressed(MotionEvent e);

		public boolean onScrollPressed(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) ;
		
		public boolean onUnPressed(MotionEvent e) ;
		
	}

}
