package es.eucm.eadandroid.ecore.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.FloatMath;
import android.util.Log;

import es.eucm.eadandroid.common.data.chapter.GpsRule;
import es.eucm.eadandroid.ecore.GameThread;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadandroid.utils.ActivityPipe;
//import es.eucm.eadandroid.utils.CreateFile;

public class GpsManager {

	private static GpsManager singleton = null;
	private Vector<GpsRule> gpss;
	private Vector<GpsRule> finishedpgss;
	GpsListener listener;

	public static GpsManager getInstance() {

		return singleton;
	}

	private GpsManager()  {
		gpss = new Vector<GpsRule>();
		finishedpgss = new Vector<GpsRule>();
		listener=new GpsListener(this);
	}

	public static void create() {
		singleton = new GpsManager();
	}

	public void addgpsrules(GpsRule rule) {
		gpss.add(rule);
	}
	
	public void flushGpsRules() {
		gpss.removeAllElements();
	}

	public void updategps(Location location) {
		for (int i = 0; i < gpss.size(); i++) {
			if ((gpss.elementAt(i).isUsesEndCondition() && new FunctionalConditions(
					gpss.elementAt(i).getEndCond()).allConditionsOk())
					|| (!gpss.elementAt(i).isUsesEndCondition() && (new FunctionalConditions(
							gpss.elementAt(i).getInitCond()).allConditionsOk()))) {

				double distance = distance(location.getLatitude(), location
						.getLongitude(), gpss.elementAt(i).getLatitude(), gpss
						.elementAt(i).getLongitude());

				Log.d("DISTANCIA", "" + distance);

				if (distance < gpss.elementAt(i).getRadio()) {
					this.finishedpgss.add(this.gpss.elementAt(i));
					
					FunctionalEffects.storeAllEffects(gpss.elementAt(i)
							.getEffects());
					this.gpss.remove(i);

				}
			}

		}

	}
	
	public GpsListener getListener() {
		return listener;
	}
	
	public void finishgps(){
		listener.finish();
	};

	// ///////////////////////////////////////////////////
	// to calculate de distance between two gsp locations

	

	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		dist = dist * 1000;

		return (dist);
		// return 40;
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts decimal degrees to radians : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts radians to decimal degrees : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

}
