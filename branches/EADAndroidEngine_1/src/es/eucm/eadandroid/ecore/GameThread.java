package es.eucm.eadandroid.ecore;

import android.content.Context;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class GameThread extends Thread {

	

	private String advPath;

	public GameThread(SurfaceHolder holder, Context context, Handler handler) {

//		ResourceHandler.createInstance();
		Game.create();
		
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		
		int landscapeHeight = displayMetrics.heightPixels;
		int landscapeWidth = displayMetrics.widthPixels;
		
		Log.w("Height",String.valueOf(landscapeHeight));
		
		Log.w("Width",String.valueOf(landscapeWidth));
		
		float scaleDensity = displayMetrics.density;
				
		GUI.create(holder);
		GUI.getInstance().init(landscapeHeight,landscapeWidth,scaleDensity);
		
	}
	
	public void run() {


		Game.getInstance().setAdventurePath(advPath);
		ResourceHandler.getInstance().setZipFile(Game.getInstance().getAdventurePath());

		Game.getInstance().start();
		Game.delete();
		ResourceHandler.getInstance().closeZipFile();
		ResourceHandler.delete();
	//	ConfigData.storeToXML();
		
	}
	

	public boolean processTouchEvent(MotionEvent e) {
		return Game.getInstance().processTouchEvent(e);
	}
	
	public boolean processTrackballEvent(MotionEvent e) {
		return Game.getInstance().processTrackballEvent(e);
	}
	
	public boolean processKeyEvent(KeyEvent e) {
		return Game.getInstance().processKeyEvent(e);
	}
		
	public boolean processSensorEvent(SensorEvent e) {
		return Game.getInstance().processSensorEvent(e);
	}


	public void setAdventurePath(String advPath) {
		this.advPath = advPath;
	}

	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	public void unpause() {
		
		if (Game.getInstance()!=null) {
		Game.getInstance().unpause();
		}
	}
	
	public void finish() {
		Game.getInstance().finish();
	}


	public void saveState(Bundle outState) {
		// TODO Auto-generated method stub
		
	}


	public void setRunning(boolean b) {
		// TODO Auto-generated method stub
		
	}


	public void setSurfaceSize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	

}