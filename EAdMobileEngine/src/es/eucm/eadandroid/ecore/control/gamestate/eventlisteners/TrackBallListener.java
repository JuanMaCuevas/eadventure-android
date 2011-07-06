package es.eucm.eadandroid.ecore.control.gamestate.eventlisteners;

import android.view.MotionEvent;

public interface TrackBallListener {

	public boolean processTrackballEvent(MotionEvent ev) ;
	
	public interface CallBack {
		
		public boolean onClick(MotionEvent e);

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) ;

		public boolean onPressed(MotionEvent e);

		public boolean onScrollPressed(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) ;
		
		public boolean onUnPressed(MotionEvent e) ;
		
	}
	
}
