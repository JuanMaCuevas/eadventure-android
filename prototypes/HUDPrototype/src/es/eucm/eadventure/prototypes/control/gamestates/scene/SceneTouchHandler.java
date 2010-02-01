package es.eucm.eadventure.prototypes.control.gamestates.scene;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.util.Log;
import android.view.MotionEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.TouchListener;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.FlingEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.PressedEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.TapEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.UIEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.UnPressedEvent;

public class SceneTouchHandler implements TouchListener.CallBack{

	
	    protected Queue<UIEvent> vEvents;
		
		// swipe gesture constants
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 10;

		
		public SceneTouchHandler() {
			vEvents = new ConcurrentLinkedQueue<UIEvent>();
		}

		public boolean onTap(MotionEvent e) {
		
			Log.d("TOUCH","TAP");
			vEvents.add(new TapEvent(e));
			return true;
		}


		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
			Log.d("TOUCH","FLING");
			vEvents.add(new FlingEvent(e1,e2,velocityX,velocityY));
			
			return false;
		}


		public boolean onPressed(MotionEvent e) {
			
			Log.d("TOUCH","PRESSED");
			vEvents.add(new PressedEvent(e));
			return true;
		}


		public boolean onScrollPressed(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {

			Log.d("TOUCH","SCROLLPRESSED "+e1.getY());
			vEvents.add(new ScrollPressedEvent(e1,e2,distanceX,distanceY));
			
			return true;
		}


		public boolean onUnPressed(MotionEvent e) {

			Log.d("TOUCH","UNPRESSED");
			vEvents.add(new UnPressedEvent(e));
		
			return true;
		}
		
		
		protected Queue<UIEvent> getEventQueue() {
			return vEvents;
		}
	
}
