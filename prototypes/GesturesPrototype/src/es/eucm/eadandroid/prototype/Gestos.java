package es.eucm.eadandroid.prototype;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import es.eucm.eadandroid.prototype.gestureDetectors.GestureDetector_Intfz;
import es.eucm.eadandroid.prototype.gestureDetectors.SceneGestureDetector;
import es.eucm.eadandroid.prototype.gestureDetectors.listeners.SceneGestureListener;
import es.eucm.eadandroid.prototype.gestureDetectors.listeners.interfaces.SceneGestListener_Intfz;
import es.prototipo.R;

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