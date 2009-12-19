package es.prototipo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.TextView;
import android.widget.Toast;

public class MyGestureDetector extends SimpleOnGestureListener {
	
	// swipe gesture constants
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 10;

	private int mCurX;
	private int mCurY;
	
	TextView t = null;
	
	boolean invSel = false;

	
	private void setCursorValues(MotionEvent e){
		
         mCurX = (int)e.getX();
         mCurY = (int)e.getY();
         
	};
	
	
	public boolean onTouchEvent(MotionEvent event){
		return false;		
	}
	
	 public void onShowPress(MotionEvent e) {
		
         setCursorValues(e);
		 
		 if (mCurY<=10) {
			 t.setText("Seleccionado el inventario: "+mCurX+" Y : "+mCurY);
			 invSel=true;
		 }
		// else t.setText("Buscando X: "+mCurX+" Y : "+mCurY);
         

	 }
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		
		t.setText("Deteccion de gestos");
		invSel=false;
		
		Log.d("Velocidad","X: "+Math.abs(velocityX)+" Y: "+Math.abs(velocityY));
	// int delta = 0;
	if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)		 {
		Log.d("Gesto","Gesto desechado");
	return false;
	}
	else{
	try { 
		
	// right to left swipe
	if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		
		
		Log.d("Gesto","Gesto derecha");
/*	if(canFlipRight()){
	flipper.setInAnimation(slideLeftIn);
	flipper.setOutAnimation(slideLeftOut); 
	flipper.showNext();
	}else{ 
	return false;
	}*/
	//left to right swipe
	} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

		Log.d("Gesto","Gesto izquierda");
		
   /*	if(canFlipLeft()){
	flipper.setInAnimation(slideRightIn);
	flipper.setOutAnimation(slideRightOut); 
	flipper.showPrevious();
	}else{
	return false;
	}*/
	} 
	else {
		//Log.d("Gesto","Gesto desechado");
	}
	} catch (Exception e) {
	// nothing
	}
	return true;
	}
	} 
	
	public void setView(Activity c) {
		
		t = (TextView)c.findViewById(R.id.texto);
		
		
	}
	
	
    public boolean onSingleTapUp(MotionEvent e)
    {	setCursorValues(e);
    	Log.d("Gesto","onSingleTapUP:"+mCurX);
		return false;
    }

    public void onLongPress(MotionEvent e)
    {	setCursorValues(e);
    	Log.d("Gesto","onLongPress"+mCurX);
    	Gestos.setIsLongPressed(true);
    	t.setText("EmpiezaBusqueda: "+mCurX+" Y : "+mCurY);
    }
    
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {   	
    	setCursorValues(e2);	
    	Log.d("Gesto"," onScroll X:"+mCurX+" Y: "+mCurY);
    	if (invSel==true)
        t.setText("Bajando inventario X: "+mCurX+" Y : "+mCurY);
    	else t.setText("Buscando X: "+mCurX+" Y : "+mCurY);
		return false;
    }

    public boolean onDown(MotionEvent e)
    {	setCursorValues(e);
    	Log.d("Gesto","onDown"+mCurX);
    
		return false;
    }

    public boolean onDoubleTap(MotionEvent e)
    {	setCursorValues(e);
    	Log.d("Gesto","onDoubleTap"+mCurX);
    	t.setText("Doble click confirmado X: "+mCurX+" Y : "+mCurY);
		return false;
    }

    public boolean onDoubleTapEvent(MotionEvent e)
    {	setCursorValues(e);
    	Log.d("Gesto","onDoubleTapEvent"+mCurX);
		return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent e)
    {	setCursorValues(e);
    	Log.d("Gesto","onSingleTapConfirmed"+mCurX);
    	t.setText("Click confirmado X: "+mCurX+" Y : "+mCurY);
		return false;
    }

}
