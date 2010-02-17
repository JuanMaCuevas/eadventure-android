package es.eucm.eadandroid.ecore.gui.ui.gestureDetectors.listeners;

import android.view.MotionEvent;
import es.eucm.eadandroid.ecore.gui.ui.Vista;
import es.eucm.eadandroid.ecore.gui.ui.gestureDetectors.listeners.interfaces.SceneGestListener_Intfz;

public class SceneGestureListener implements SceneGestListener_Intfz{

	//**********************************************************************************************/
	//******************* METODOS DE INTERFAZ ON_SCENE_GESTURE_LISTENER ****************************/
	//**********************************************************************************************/
		
		
		// swipe gesture constants
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 10;


		public boolean onClick(MotionEvent e) {
		
			Vista.setText("Click : X: "+e.getX()+" Y: "+e.getY());
			
			return true;
		}


		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
		/*	if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
				Log.d("Gesto", "Gesto desechado");
				return false;
			} else {
				try {

					// right to left swipe
					if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

						Log.d("Gesto", "Gesto derecha");

					} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

						Log.d("Gesto", "Gesto izquierda");

					} else {
						Log.d("Gesto", "Gesto desechado");
					}
				} catch (Exception e) {
					// nothing
				}
				return true;
			}*/
			
			return false;
		}


		public boolean onPressed(MotionEvent e) {
			
			Vista.setText("Presionado : X: "+e.getX()+" Y: "+e.getY());
			
			return true;
		}


		public boolean onScrollPressed(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			
			Vista.setText("Scroll presionado : X: "+e2.getX()+" Y: "+e2.getY());
			
			return true;
		}


		public boolean onUnPressed(MotionEvent e) {
			
			Vista.setText("Despresionado : X: "+e.getX()+" Y: "+e.getY());
			
			return true;
		}
		
		
	
}
