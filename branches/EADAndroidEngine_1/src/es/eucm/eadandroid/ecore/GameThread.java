package es.eucm.eadandroid.ecore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import es.eucm.eadandroid.common.data.adventure.ChapterSummary;
import es.eucm.eadandroid.common.data.assessment.AssessmentProfile;
import es.eucm.eadandroid.common.gui.JOptionPane;
import es.eucm.eadandroid.common.gui.TC;
import es.eucm.eadandroid.common.loader.Loader;
import es.eucm.eadandroid.debug.ReportDialog;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class GameThread extends Thread {

	
	private SurfaceHolder mSurfaceHolder;
	private Context mContext;
	private String advPath;

	public GameThread(SurfaceHolder holder, Context context, Handler handler) {
		mSurfaceHolder = holder;
		mContext = context;
	}
	
	public void run() {

	//	ResourceHandler.createInstance();
		Game.create(mSurfaceHolder);
		Game.getInstance().setAdventurePath(advPath);
		ResourceHandler.getInstance().setZipFile(Game.getInstance().getAdventurePath());

		Game.getInstance().start();
		Game.delete();
		ResourceHandler.getInstance().closeZipFile();
		ResourceHandler.delete();
	//	ConfigData.storeToXML();
		
	}

	public void setAdventurePath(String advPath) {
		this.advPath = advPath;
	}

	public void pause() {
		// TODO Auto-generated method stub
		
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