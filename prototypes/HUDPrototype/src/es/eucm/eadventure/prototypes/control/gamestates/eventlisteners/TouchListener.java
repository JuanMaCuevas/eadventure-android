package es.eucm.eadventure.prototypes.control.gamestates.eventlisteners;

import android.view.MotionEvent;

public interface TouchListener {
	
	public boolean processTouchEvent(MotionEvent ev) ;
	
	public interface CallBack {
		
		public static int UNPRESSED_ACTION = 0;
		public static int PRESSED_ACTION = 1;
		public static int FLING_ACTION = 2;
		public static int TAP_ACTION = 3;
		public static int SCROLL_PRESSED_ACTION = 4;
		
		public boolean onTap(MotionEvent e);

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) ;

		public boolean onPressed(MotionEvent e);

		public boolean onScrollPressed(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) ;
		
		public boolean onUnPressed(MotionEvent e) ;
		
	}

}
