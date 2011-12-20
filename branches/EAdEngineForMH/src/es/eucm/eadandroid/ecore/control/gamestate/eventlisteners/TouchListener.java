package es.eucm.eadandroid.ecore.control.gamestate.eventlisteners;

import java.util.Queue;

import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import android.view.MotionEvent;

public interface TouchListener {
	
	public boolean processTouchEvent(MotionEvent ev) ;
	
	public Queue<UIEvent> getEventQueue();
	
	public interface CallBack {
				
		public boolean onTap(MotionEvent e);

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) ;

		public boolean onPressed(MotionEvent e);
		
		public boolean onLongPressed(MotionEvent e);

		public boolean onScrollPressed(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) ;
		
		public boolean onUnPressed(MotionEvent e) ;
		
		public boolean onDown(MotionEvent e) ;
				
	}

}
