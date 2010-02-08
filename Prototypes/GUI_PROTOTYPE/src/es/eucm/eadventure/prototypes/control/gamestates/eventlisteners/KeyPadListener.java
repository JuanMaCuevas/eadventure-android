package es.eucm.eadventure.prototypes.control.gamestates.eventlisteners;

import android.view.KeyEvent;

public interface KeyPadListener {

	boolean processKeyEvent(KeyEvent event);

	public interface CallBack {
		
		abstract boolean onKeyDown(int keyCode, KeyEvent event);

		abstract boolean onKeyLongPress(int keyCode, KeyEvent event);

		abstract boolean onKeyMultiple(int keyCode, int count, KeyEvent event);

		abstract boolean onKeyUp(int keyCode, KeyEvent event);

	}

}
