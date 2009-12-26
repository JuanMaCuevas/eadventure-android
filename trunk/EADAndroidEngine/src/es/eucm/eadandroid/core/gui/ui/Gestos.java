package es.eucm.eadandroid.core.gui.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.core.gui.ui.gestureDetectors.GestureDetector_Intfz;
import es.eucm.eadandroid.core.gui.ui.gestureDetectors.SceneGestureDetector;
import es.eucm.eadandroid.core.gui.ui.gestureDetectors.listeners.SceneGestureListener;

public class Gestos extends Activity {


	private GestureDetector_Intfz gestureDetector;
	private SceneGestureListener sgl;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		
		
		
		Vista.setView((TextView) this.findViewById(R.id.texto));
		
		sgl = new SceneGestureListener();

		gestureDetector = new SceneGestureDetector(sgl);
						

	}


	public boolean dispatchTouchEvent(MotionEvent event) {

		return gestureDetector.onTouchEvent(event);

	}
	
	public boolean  onKeyUp(int keyCode, KeyEvent event) {
		
		if (keyCode==KeyEvent.KEYCODE_MENU){
			Vista.setText("Boton Menu");
			return true;
		}
		else return false;
	
	}
	
	
	/* Deteccion boton menu */
	
	
	

}