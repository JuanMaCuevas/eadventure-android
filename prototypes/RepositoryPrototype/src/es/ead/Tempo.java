package es.ead;

import java.io.File;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Tempo extends Thread {
	 
	
	
	private Context context;
	private Handler handler;
	
	
	private boolean cargado=false;
	
	
	public boolean isCargado() {
		return cargado;
	}



	public void setCargado(boolean cargado) {
		this.cargado = cargado;
	}



	public Tempo(Context ctx, Handler ha) {
		context = ctx;
		handler = ha;
	}
	
	
	
public void run() {
		
		try {
			Tempo.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Message msg = new Message();
		
		
		
		while (!cargado)
		{
			try {
				Tempo.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		
		
		Bundle b = new Bundle();
		msg.setData(b);
		handler.sendMessage(msg);
		


	}
	
	

}
