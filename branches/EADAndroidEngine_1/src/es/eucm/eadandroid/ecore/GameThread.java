package es.eucm.eadandroid.ecore;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class GameThread extends Thread {

	private String advPath;
	Handler handler;
	//boolean starteado=false;
	boolean loadActivityGames=false;
	
	
	private static GameThread instance = null;
	
	public static final String TAG ="GameThread";
	
	private GameThread(SurfaceHolder holder,Context context, Handler handler,String loadingGame)
	{
		this.handler =handler;
//		ResourceHandler.createInstance();
		Game.create(loadingGame);
		

		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		
		int landscapeHeight = displayMetrics.heightPixels;
		int landscapeWidth = displayMetrics.widthPixels;
		
		Log.w("Height",String.valueOf(landscapeHeight));
		
		Log.w("Width",String.valueOf(landscapeWidth));
		
		float scaleDensity = displayMetrics.density;
				
		GUI.create(holder);
		GUI.getInstance().init(landscapeHeight,landscapeWidth,scaleDensity);
		
		
	}

	public static void create(SurfaceHolder holder,Context context, Handler handler,String loadingGame) 
		{
	instance=new GameThread(holder,context,handler,loadingGame);
	
		}
	
	public void run() {
		
	//	Debug.startAllocCounting();
		//startMethodTracing("thread");


		Game.getInstance().setAdventurePath(advPath);
		ResourceHandler.getInstance().setGamePath(Game.getInstance().getAdventurePath());

		Game.getInstance().start();
		Game.delete();
		ResourceHandler.getInstance().closeZipFile();
		ResourceHandler.delete();
	//	ConfigData.storeToXML();
		
		finishThread();
		
		//Debug.stopMethodTracing();
	
	}
	
//last function it will be done from GameThread not activitythread
	private void finishThread() {
		
		//handler.dispatchMessage(new Message())
		
		//Handler handler=activity.ActivityHandler;
        
        Message msg = handler.obtainMessage();
        
        
        Bundle b = new Bundle();
		//b.putString("html", text);
        if (this.loadActivityGames)
        	msg.what = ActivityHandlerMessages.LOAD_GAMES;	
        else msg.what = ActivityHandlerMessages.GAME_OVER;
		msg.setData(b);
		this.instance=null;
		
		msg.sendToTarget();
		
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
	
	public String getAdventurePath() {
		return advPath;
	}

	public void pause() {
		// TODO Auto-generated method stub
		if (Game.getInstance()!=null) {
		Game.getInstance().pause();
		}
	}
	
	
	
	

	public static GameThread getInstance() {
		return instance;
	}

	public void unpause(SurfaceHolder canvasHolder) {
		
		if (Game.getInstance()!=null) {
		Game.getInstance().unpause(canvasHolder);
		}
	}
	
	public void finish(boolean loadactivitygames) {
		
		this.loadActivityGames=loadactivitygames;
		if(Game.getInstance()!=null)
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
	public void setSurfacevideo(SurfaceHolder videoholder) {
		GUI.getInstance().setCanvasSurfaceHolder(videoholder);
		// TODO Auto-generated method stub
		
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public Handler getHandler() {
		return handler;
	}

	public void resize(boolean onescaled) {
		// TODO Auto-generated method stub
		Game.getInstance().pause();
		GUI.getInstance().resize(onescaled);
		Game.getInstance().unpause();
		
	}
	
	
	

}