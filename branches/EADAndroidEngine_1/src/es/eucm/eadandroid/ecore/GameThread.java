package es.eucm.eadandroid.ecore;

import android.content.Context;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.control.ContextServices;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;


public class GameThread extends Thread {

	private String advPath;
	private String advName;
	Handler handler;
	boolean loadActivityGames=false;
	
	private Context context;

	private static GameThread instance = null;
	
	public static final String TAG ="GameThread";
	
	private GameThread(SurfaceHolder holder,Context context, Handler handler,String loadingGame)
	{
		this.handler =handler;
		this.context = context;
		Game.create(loadingGame);

		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		
		int landscapeHeight = displayMetrics.heightPixels;
		int landscapeWidth = displayMetrics.widthPixels;
		
		float scaleDensity = displayMetrics.density;	
				
		GUI.create(holder);
		ContextServices.create(context);
		GUI.getInstance().init(landscapeHeight, landscapeWidth, scaleDensity);						
	}



	public static void create(SurfaceHolder holder,Context context, Handler handler,String loadingGame) 
		{
	          instance=new GameThread(holder,context,handler,loadingGame);
	
		}
	
	public void run() {
		
		Game.getInstance().setAdventurePath(advPath);
		Game.getInstance().setAdventureName(advName);
		Game.getInstance().setPrefs(PreferenceManager.getDefaultSharedPreferences(context));
		ResourceHandler.getInstance().setGamePath(Game.getInstance().getAdventurePath());

		Game.getInstance().start();
		Game.delete();
		ResourceHandler.getInstance().closeZipFile();
		ResourceHandler.delete();
	//	ConfigData.storeToXML();
		
		finishThread();
		
	}
	
//last function it will be done from GameThread not activitythread
	private void finishThread() {
        
        Message msg = handler.obtainMessage();        
        
        Bundle b = new Bundle();

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
	
	public void setAdventureName(String advName){
		this.advName = advName;
	}

	public void pause() {
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

	public void setSurfacevideo(SurfaceHolder videoholder) {
		GUI.getInstance().setCanvasSurfaceHolder(videoholder);		
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