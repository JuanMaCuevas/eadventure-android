package es.eucm.eadandroid.repositoryManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import es.eucm.eadandroid.R;

public class DownloadingGame extends Activity {
	
	String[] datos;
	
	private ProgressBar mProgress;
	
	int progreso=0;
	
	Button volver;
	
	Activity aux;
	
	
	private Handler mHandler = new Handler();
	
	
	Handler habilitar;
   

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		aux=this;
		
		
		
		
		
setContentView(R.layout.descarga_juego_detalle);

volver=(Button) findViewById(R.id.descargamenu);



habilitar=new Handler() {
	@Override
	/* * Called when a message is sent to Engines Handler Queue */

public void handleMessage(Message msg) {
		
		
		volver.setEnabled(true); 
		Log.v("boton volver","se tiene que poder ver");
		
	}

};







//barra de progresion
new Thread(new Runnable() {

	public void run() {
		
		
		
		
		File root = Environment.getExternalStorageDirectory();
		URL u;
		try {
						
			u = new URL(datos[3]);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
    		c.setRequestMethod("GET");
    		c.setDoOutput(true);
    		c.connect();
    		
    		
    		 StringTokenizer prueba= new StringTokenizer(datos[3],"/",true);
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
    			
    			
    		/*	
    			Message msg = new Message();
    			Bundle b = new Bundle();
    			
    			b.putInt("valor", progreso);
    			msg.setData(b);
    			handler.sendMessage(msg);
    		   */
    			
    			 mHandler.post(new Runnable() {
                     public void run() {
                         mProgress.setProgress(progreso);
                     }
                 });
    			
    			
    		   fos.write(buffer,0, len1);
    		  
    		}

    		fos.close();
    		in.close();
    		
    		
    		Message msg = new Message();
    		Bundle b = new Bundle();
    		msg.setData(b);
    		
    		habilitar.sendMessage(msg);
    		
    } catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	
	
}).start();
		
//se acaba la barra de progresion		
		
datos=(String[]) this.getIntent().getExtras().get("datos");
		
		for(int i=0;i<datos.length;i++)
	      {
	    	  Log.v("ficheros",datos[i]);
	      }
	
	
	
		ImageView img;
        img = (ImageView) findViewById(R.id.Viewdetallado2);
        LoadImage s=new LoadImage();
        img.setImageBitmap(s.dameimagen(datos[0]));
        
        /*
        
        String filepath = "/sdcard/";
		File imagefile = new File(filepath + datos[0]);
		FileInputStream fis;
		try {
			fis = new FileInputStream(imagefile);
			Bitmap bitmap= BitmapFactory.decodeStream(fis);
	        img.setImageBitmap(bitmap);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        */
        
        
        TextView txt1;
        txt1 = (TextView) findViewById(R.id.titulodetallado2);
        txt1.setText(datos[1]);   
       
               
        TextView txt2;
        txt2 = (TextView) findViewById(R.id.textodetallado2);
        txt2.setText(datos[2]);
        
        
        mProgress = (ProgressBar) findViewById(R.id.progreso2);
        
        
        
        
        volver.setOnClickListener(new OnClickListener(){

    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				
    				Intent b = new Intent(aux,Engine.class);
    				startActivity(b);
    			}
    			
            
            });
        
        
        
		
		
		
	}


}
