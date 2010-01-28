package es.eucm.eadandroid.ecore.gui.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.ecore.gui.ui.gestureDetectors.SceneGestureDetector;
import es.eucm.eadandroid.ecore.gui.ui.gestureDetectors.UIEventListener;
import es.eucm.eadandroid.ecore.gui.ui.gestureDetectors.listeners.SceneGestureListener;

public class Gestos extends Activity {


	private UIEventListener gestureDetector;
	private SceneGestureListener sgl;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

	//	setContentView(R.layout.main);
		
		
		
		Vista.setView((TextView) this.findViewById(R.id.texto));
		
		sgl = new SceneGestureListener();

		gestureDetector = new SceneGestureDetector(sgl);
						

	}


	// WINDOWS.CALLBACK implementation
	
	public boolean dispatchTouchEvent(MotionEvent event) {

		return gestureDetector.onTouchEvent(event);

	}
	
	// KEYEVENT.CALLBAK implementation
	
	public boolean  onKeyUp(int keyCode, KeyEvent event) {
		
		if (keyCode==KeyEvent.KEYCODE_MENU){
			Vista.setText("Boton Menu");
			return true;
		}
		else return false;
	
	}
	
	
	/* Deteccion boton menu */
	
	
	

}