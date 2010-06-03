package es.eucm.eadandroid.ecore.control;

import java.util.List;
import java.util.Vector;

import android.location.Location;
import android.util.Log;
import es.eucm.eadandroid.common.data.chapter.GpsRule;
import es.eucm.eadandroid.common.data.chapter.QrcodeRule;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;

public class QrcodeManager {

	
	private static QrcodeManager singleton = null;
	private Vector<QrcodeRule> qrcodeActive;
	private Vector<QrcodeRule> allqrrules;


	public static QrcodeManager getInstance() {

		return singleton;
	}

	private QrcodeManager()  {
		qrcodeActive = new Vector<QrcodeRule>();
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
		for(int i=0;i<list.size();i++)
		{
			if (list.get(i).getSceneName().equals(""))
				this.qrcodeActive.add(list.get(i));
		}
		

	}

	public void addqrrules(QrcodeRule rule) {
		allqrrules.add(rule);
	}
	
	public void changeOfScene(String scene)
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
		
	}
	


	public void updateQRcode(String password) {
		for (int i = 0; i < qrcodeActive.size(); i++) {

			if (new FunctionalConditions(qrcodeActive.elementAt(i).getEndCond()).allConditionsOk())
			{
				if (qrcodeActive.elementAt(i).getPassword().equals(password)) {
					FunctionalEffects.storeAllEffects(qrcodeActive.elementAt(i)
							.getEffects());
					this.qrcodeActive.remove(i);

				}
			}

		}

	}

}
