package es.ead;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class NewGames extends ListActivity {
	
	private static final int MENU_NEW_GAME = 0;
	private static final int MENU_QUIT = 0;
	private static final int MENU_QUIT2 = 0;
	Vector<InfoGame> archivos=new Vector<InfoGame>();
	ProgressDialog dialog;
	
	
	
	
	private Handler cargador = new Handler() {
		@Override
		/* * Called when a message is sent to Engines Handler Queue */
		public void handleMessage(Message msg) {

			dialog.dismiss();

		}

	};
	
	
public void onCreate(Bundle icicle) {
	       super.onCreate(icicle);
	       
	       
	       
	      setContentView(R.layout.nuevos_juegos);
	      Tempo t = new Tempo(this, cargador);
			t.start();
	       dialog = ProgressDialog.show(this, "", "juegos online",true);
	      String[] juegos_descargados=(String[]) this.getIntent().getExtras().get("pru");
	      
	      Vector<String> juegos=new Vector<String>();
	      for(int i=0;i<juegos_descargados.length;i++)
	      {
	    	  StringTokenizer nombre= new StringTokenizer(juegos_descargados[i],".",true);
	    	  juegos.add(nombre.nextToken());
	       }
	      
	      
	      File sdCard = Environment.getExternalStorageDirectory();
	      String[] imagenes_descargadas= sdCard.list(new JPGFilter());
	      Vector<String> imagenes=new Vector<String>();
	            
	      
	      for(int i=0;i<imagenes_descargadas.length;i++)
	      imagenes.add(imagenes_descargadas[i]);
	      
	      
	      try {

				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				Downloadfile a=new Downloadfile("http://eadventure-android.googlecode.com/files/clasificadorjuegos.xml");
				FileInputStream fIn = new FileInputStream("/sdcard/clasificadorjuegos.xml");
				Extractxml dh = new Extractxml(this);
				saxParser.parse(fIn, dh);

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
			
			
			ArrayList<InfoGame> jueguitos = new ArrayList<InfoGame>();
			
			for(int i=0;i<archivos.size();i++)
	        {
	        	
	        	if(!juegos.contains(archivos.elementAt(i).getTittle()))
	        	{
	        		if(imagenes.contains(archivos.elementAt(i).getStringIcon()))
	        		{
	        			
	        			String filepath = "/sdcard/";
	            		File imagefile = new File(filepath + archivos.elementAt(i).getStringIcon());
	            		FileInputStream fis;
						try {
							
							fis = new FileInputStream(imagefile);
							archivos.elementAt(i).setIcon(BitmapFactory.decodeStream(fis));
							fis.close();
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
	        		}else
	        			{
	        			archivos.elementAt(i).setIcon(this.DownloadImage(archivos.elementAt(i).getImagen()));
	        			}
	        		jueguitos.add(archivos.elementAt(i));
	        	}
	        	
	        }
	        
	        Games_adapter lista = new Games_adapter(this,jueguitos);
			setListAdapter(lista);
	        this.getListView().setOnCreateContextMenuListener(this);
	        
	        
	        t.setCargado(true);
	        
	       	        
	    }
	
	/*
	private InputStream OpenHttpConnection(String urlString) 
    throws IOException
    {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        URLConnection conn = url.openConnection();
                 
        if (!(conn instanceof HttpURLConnection))                     
            throw new IOException("Not an HTTP connection");
        
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 

            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }                     
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");            
        }
        return in;     
    }

*/
	
	
	
	private Bitmap DownloadImage(String URL)
    { 
		Bitmap bitmap=null;
		
		try {
			Downloadfile des=new Downloadfile(URL);
			
			
			StringTokenizer prueba= new StringTokenizer(URL,"/",true);
			  String aux1=null;
			    while (prueba.hasMoreTokens())
			    {
			    	aux1=prueba.nextToken();
			    }
			    
			    LoadImage s=new LoadImage();
			    bitmap=s.dameimagen(aux1);			    
			   			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return bitmap;
		
	/*	
		
		StringTokenizer prueba= new StringTokenizer(URL,"/",true);
		  String aux1=null;
		    while (prueba.hasMoreTokens())
		    {
		    	aux1=prueba.nextToken();
		    }
       		
        Bitmap bitmap = null;
        InputStream in = null; 
        InputStream in2 = null; 
        try {
            in = OpenHttpConnection(URL);
            
                     
            
            File root = Environment.getExternalStorageDirectory();
            File file = new File(root,aux1);
            
            FileOutputStream fos = new FileOutputStream(file);
            
            
    		
            byte[] buffer = new byte[1024];
    		int len1 = 0;
    		while ( (len1 = in.read(buffer)) != -1 ) {
    		  fos.write(buffer,0, len1);
    		}

    		fos.close();
    		in.close();
    	
    		String filepath = "/sdcard/";
    		File imagefile = new File(filepath + aux1);
    		FileInputStream fis = new FileInputStream(imagefile);
    		bitmap= BitmapFactory.decodeStream(fis);
    		
    		
    		fis.close();
    	
    		
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return bitmap;   
        
        
        */
    }
	
	
protected void onListItemClick(ListView l, View v, int position, long id) {
		
		
		InfoGame prueba = (InfoGame) this.getListView().getItemAtPosition(position);
		
		Intent i = new Intent( this ,DetailedGame.class);
		
		String[] datos=new String[4];
		
		datos[0]=prueba.getStringIcon();
		datos[1]=prueba.getTittle();
		datos[2]=prueba.getText();
		datos[3]=prueba.getCodigo();
		
		i.putExtra("datos", datos);
 		startActivity(i);      
				
	}


public void setArchivos(Vector<InfoGame> archivos) {
	this.archivos = archivos;
}
	
}
