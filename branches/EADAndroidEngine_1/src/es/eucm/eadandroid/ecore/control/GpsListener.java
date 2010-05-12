package es.eucm.eadandroid.ecore.control;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GpsListener implements LocationListener {

	GpsManager manager;
	
	public GpsListener(GpsManager manager) {
		super();
		this.manager=manager;
	}

	public void onLocationChanged(Location location) {
		
		manager.updategps(location);
	}

	public void onProviderDisabled(String provider) {
		
		
	}

	public void onProviderEnabled(String provider) {
		
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		
		
	}
	
	public void finish(){
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
