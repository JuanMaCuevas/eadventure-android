package es.eucm.eadventure.prototypes.control.gamestates.scene;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.util.Log;
import android.view.MotionEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.TouchListener;

public class SceneTouchHandler implements TouchListener.CallBack{

	//**********************************************************************************************/
	//******************* METODOS DE INTERFAZ ON_SCENE_GESTURE_LISTENER ****************************/
	//**********************************************************************************************/

	
	    protected Queue<MotionEvent> vEvents;
		
		// swipe gesture constants
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 10;

		
		public SceneTouchHandler() {
			vEvents = new ConcurrentLinkedQueue<MotionEvent>();
		}

		public boolean onTap(MotionEvent e) {
		
			e.setAction(TouchListener.CallBack.TAP_ACTION);
			Log.d("TOUCH","TAP");
			vEvents.add(e);
			return true;
		}


		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
			e2.setAction(TouchListener.CallBack.FLING_ACTION);
			Log.d("TOUCH","FLING");
			vEvents.add(e2);
			
			return false;
		}


		public boolean onPressed(MotionEvent e) {
			
			e.setAction(TouchListener.CallBack.PRESSED_ACTION);
			Log.d("TOUCH","PRESSED");
			vEvents.add(e);
			return true;
		}


		public boolean onScrollPressed(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			
			e2.setAction(TouchListener.CallBack.SCROLL_PRESSED_ACTION);
			Log.d("TOUCH","SCROLLPRESSED");
			vEvents.add(e2);
			
			return true;
		}


		public boolean onUnPressed(MotionEvent e) {
			
			e.setAction(TouchListener.CallBack.UNPRESSED_ACTION);
			Log.d("TOUCH","UNPRESSED");
			vEvents.add(e);
		
			return true;
		}
		
		
		protected Queue<MotionEvent> getEventQueue() {
			return vEvents;
		}
	
}
