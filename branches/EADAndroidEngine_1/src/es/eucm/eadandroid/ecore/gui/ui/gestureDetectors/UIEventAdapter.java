package es.eucm.eadandroid.ecore.gui.ui.gestureDetectors;

import java.util.EventListener;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

public class UIEventAdapter implements UIEventListener , EventListener {

	public boolean onKeyEvent(KeyEvent ev) {
		return false;
	}

	public boolean onPopulateAccessibilityEvent(AccessibilityEvent event) {
		return false;
	}

	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}

	public boolean onTrackballEvent(MotionEvent ev) {
		return false;
	}

}
