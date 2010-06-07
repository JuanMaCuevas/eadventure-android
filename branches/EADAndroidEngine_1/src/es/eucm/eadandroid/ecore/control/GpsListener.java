package es.eucm.eadandroid.ecore.control;

import es.eucm.eadandroid.ecore.GameThread;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GpsListener implements LocationListener {

	GpsManager manager;
	
	


	public GpsListener(GpsManager manager) {
		super();
		this.manager=manager;
		Log.d("GpsListener", " XXXXXXXXXXXXXXXXXXXXXXXXX");
	}

	public void onLocationChanged(Location location) {
		
		manager.updategps(location);
		Log.d("onLocationChanged", " XXXXXXXXXXXXXXXXXXXXXXXXX");
	}

	public void onProviderDisabled(String provider) {
		Log.d("onProviderDisabled", " XXXXXXXXXXXXXXXXXXXXXXXXX");
		
	}

	public void onProviderEnabled(String provider) {
		
		Log.d("onProviderEnabled", " XXXXXXXXXXXXXXXXXXXXXXXXX");
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		
		Log.d("onStatusChanged", " XXXXXXXXXXXXXXXXXXXXXXXXX  "+status);
		
		switch (status)
		{
		case LocationProvider.AVAILABLE:
			
			if (Game.getInstance()==null)
			{
				manager.setActiveGps(true);
			}else{
				//TODO tengo que cambiar a que continue
				
				Handler handler = GameThread.getInstance().getHandler();
				Message msg = handler.obtainMessage();

				Bundle b = new Bundle();
				msg.what = ActivityHandlerMessages.FINISH_DIALOG;
				msg.setData(b);
				msg.sendToTarget();
				
				if(Game.getInstance().ispause())
				{
					Game.getInstance().unpause();
				}
			}
			
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			
			
			
			
			break;	
		case LocationProvider.OUT_OF_SERVICE:
			
			if (Game.getInstance()==null)
			{
				Game.getInstance().pause();
				Handler handler = GameThread.getInstance().getHandler();
				String text=new String("gps service is currently out of service");

				Message msg = handler.obtainMessage();

				Bundle b = new Bundle();
				b.putString("content", text);
				msg.what = ActivityHandlerMessages.SHOW_DIALOG;
				msg.setData(b);
				msg.sendToTarget();
			}
			break;
		}
		
		
		
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
