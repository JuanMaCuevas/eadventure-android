package es.eucm.eadandroid.repositoryManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ProgressBar extends Thread {
	
	 
	private Context context;
	private Handler handler;
	private String url;
	
	int progreso=0;
	 
	
	public ProgressBar(Context ctx, Handler ha,String a) {
		context = ctx;
		handler = ha;
		url=a;
		
		Log.v("se_descarga_de",a); 
	}	
	
	
	
	
	
	
	
	
	public void run() {
    	
    	File root = Environment.getExternalStorageDirectory();
		URL u;
		try {
						
			u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
    		c.setRequestMethod("GET");
    		c.setDoOutput(true);
    		c.connect();
    		
    		
    		 StringTokenizer prueba= new StringTokenizer(url,"/",true);
   		  String aux1=null;
   		    while (prueba.hasMoreTokens())
   		    {
   		    	aux1=prueba.nextToken();
   		    }
    		
    		
    		
    		
    		File file = new File(root,aux1);
    		
    		FileOutputStream fos = new FileOutputStream(file);
    		int r=c.getContentLength();
    		InputStream in = c.getInputStream();
    		int subo=r/1024;
    		int veces=subo/100;
    		veces=veces-1;
    		int num=0;

    		byte[] buffer = new byte[1024];
    		int len1 = 0;
    		while ( (len1 = in.read(buffer)) != -1 ) {
    			
    			
    			if (num==veces)
    			{
    				progreso =progreso +1;
    				num=0;
    			}else num=num+1;
    			
    			
    			
    			Message msg = new Message();
    			Bundle b = new Bundle();
    			
    			b.putInt("valor", progreso);
    			msg.setData(b);
    			handler.sendMessage(msg);
    		   
    		   fos.write(buffer,0, len1);
    		  
    		}

    		fos.close();
    		in.close();
    		
    } catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
   

}
