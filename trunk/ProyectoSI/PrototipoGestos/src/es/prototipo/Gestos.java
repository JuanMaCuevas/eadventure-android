package es.prototipo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.TextView;

public class Gestos extends Activity {

	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;

	private GestureDetector gestureDetector;
	
	private static boolean lp = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main);
	    
		/*
		 * slideLeftIn = AnimationUtils.loadAnimation(this,
		 * R.anim.slide_left_in); slideLeftIn.setAnimationListener(new
		 * ScrollLeft()); slideLeftOut = AnimationUtils.loadAnimation(this,
		 * R.anim.slide_left_out); slideRightIn =
		 * AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
		 * slideRightIn.setAnimationListener(new ScrollRight()); slideRightOut =
		 * AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
		 */

		MyGestureDetector myGD = new MyGestureDetector();
		myGD.setView(this);
		

		
		gestureDetector = new GestureDetector(myGD);
		
	//	gestureDetector.setIsLongpressEnabled (false);
	

		/*
		TextView textView = (TextView)findViewById(R.id.textview);

		textView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});*/

	}
	
	   public boolean  onTouchEvent  (MotionEvent event) {
		
		   gestureDetector.onTouchEvent(event); 
		   
		   return false;
		   
		   
	   
	   }
	
	   public boolean dispatchTouchEvent(MotionEvent event) { 
		/*      ... you may want to handle the MotionEvent.ACTION_UP yourself 
		first. ... 
		      ... but always call this at the end: */
		   
		   Log.d("Accion",new Integer(event.getAction()).toString());
		   
		   if (lp){ 
		   
			/*   MotionEvent aux = MotionEvent.obtainNoHistory(event);
			   aux.setAction(1);
			   gestureDetector.onTouchEvent(aux);
			   aux = MotionEvent.obtainNoHistory(event);
			   aux.setAction(0);
			   gestureDetector.onTouchEvent(aux);
		*/	   
			   gestureDetector.onTouchEvent(event);
			   
			   Gestos.lp=false;
			   
			   
		   }
		   else  gestureDetector.onTouchEvent(event); 
		   
		   return false;
		   } 
   
	   
	   public static void setIsLongPressed(boolean lp) {
		   
		   Gestos.lp=lp;
		   
	   }
	   
	   
}