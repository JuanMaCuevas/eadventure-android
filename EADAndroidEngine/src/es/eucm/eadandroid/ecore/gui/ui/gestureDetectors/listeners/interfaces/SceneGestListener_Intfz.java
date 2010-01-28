package es.eucm.eadandroid.ecore.gui.ui.gestureDetectors.listeners.interfaces;

import android.view.MotionEvent;

public interface SceneGestListener_Intfz {
	
	public boolean onClick(MotionEvent e);
		
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) ;
	
	public boolean onPressed(MotionEvent e); 
	
	public boolean onUnPressed(MotionEvent e); 

	public boolean onScrollPressed(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY);

}
