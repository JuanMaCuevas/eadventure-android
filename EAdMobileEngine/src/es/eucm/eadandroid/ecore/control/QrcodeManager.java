package es.eucm.eadandroid.ecore.control;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import es.eucm.eadandroid.common.data.chapter.QrcodeRule;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.GameThread;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadandroid.ecore.control.gamestate.GameStatePlaying;

public class QrcodeManager {

	
	private static QrcodeManager singleton = null;
	//private Vector<QrcodeRule> qrcodeActive;
	private Vector<QrcodeRule> allqrrules;


	public static QrcodeManager getInstance() {

		return singleton;
	}

	private QrcodeManager()  {
		//qrcodeActive = new Vector<QrcodeRule>();
		allqrrules = new Vector<QrcodeRule>();
	
	}

	public static void create() {
		singleton = new QrcodeManager();
	}
	
	public void addAllQRRules(List<QrcodeRule> list)
	{
		this.allqrrules.removeAllElements();
		
		for(int i=0;i<list.size();i++)
		{
			this.allqrrules.add(list.get(i));
		}
		
		
		//loads global qrrules
		/*for(int i=0;i<list.size();i++)
		{
			if (list.get(i).getSceneName().equals(""))
				this.qrcodeActive.add(list.get(i));
		}*/
		

	}

	public void addqrrules(QrcodeRule rule) {
		allqrrules.add(rule);
	}
	
/*	public void changeOfScene(String scene)
	{
	//first removes from gpsActive all qr related to other scenes
		for (int i=this.qrcodeActive.size()-1;i>=0;i=i-1)
		{
			if(!this.qrcodeActive.elementAt(i).getSceneName().equals(""))
				qrcodeActive.remove(i);
		}
//then adds gpsrules from new scene		
		for (int i=0;i<this.allqrrules.size();i++)
		{
			if (this.allqrrules.elementAt(i).getSceneName().equals(scene))
				qrcodeActive.add(allqrrules.elementAt(i));
		}
		
	}*/
	


	public void updateQRcode(String password) {
		for (int i = 0; i < allqrrules.size(); i++) {

			if (new FunctionalConditions(allqrrules.elementAt(i).getInitCond()).allConditionsOk())
			{
				if (allqrrules.elementAt(i).getCode().equals(password)) {
					
					Handler handler = GameThread.getInstance().getHandler();
					String text=new String("QRCode found ");
					Message msg = handler.obtainMessage();
					Bundle b = new Bundle();
					b.putString("toast", text);
					msg.what = ActivityHandlerMessages.SHOW_TOAST;
					msg.setData(b);

					msg.sendToTarget();
					
					FunctionalEffects.storeAllEffects(allqrrules.elementAt(i)
							.getEffects());
					this.allqrrules.remove(i);

				}
			}

		}

	}
	
	public boolean isGameStatePlaying()
	{
		boolean isGamStatePlaying=false;
		if (Game.getInstance().getCurrentState() instanceof GameStatePlaying)
		{
			isGamStatePlaying=true;
		}
		
		return isGamStatePlaying;
	}

}
