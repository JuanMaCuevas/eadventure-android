package es.eucm.eadandroid.ecore.gui.ui.gestureDetectors;

import java.util.EventListener;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

public interface UIEventListener extends EventListener{

	public boolean onTouchEvent(MotionEvent ev);
	
	public boolean onTrackballEvent(MotionEvent ev);
	
	public boolean onKeyEvent(KeyEvent ev);
	
	public boolean onPopulateAccessibilityEvent(AccessibilityEvent event);
	
}
