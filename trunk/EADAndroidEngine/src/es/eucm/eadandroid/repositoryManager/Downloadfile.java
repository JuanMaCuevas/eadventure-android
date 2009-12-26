package es.eucm.eadandroid.repositoryManager;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import android.os.Environment;
import android.util.Log;

public class Downloadfile {

	public Downloadfile(String ruta) throws IOException {
		// TODO Auto-generated constructor stub
		
		String url = ruta;
		File root = Environment.getExternalStorageDirectory();

		URL u = new URL(url);
		HttpURLConnection c = (HttpURLConnection) u.openConnection();
		c.setRequestMethod("GET");
		c.setDoOutput(true);
		c.connect();
		
		
		  StringTokenizer prueba= new StringTokenizer(ruta,"/",true);
		  String aux1=null;
		    while (prueba.hasMoreTokens())
		    {
		    	aux1=prueba.nextToken();
		    }
        
		
		
		
		File file = new File(root,aux1);
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			
			
			InputStream in;
			try {
				in = c.getInputStream();
				byte[] buffer = new byte[1024];
				int len1 = 0;
				while ( (len1 = in.read(buffer)) != -1 ) {
				  fos.write(buffer,0, len1);
				}

				fos.close();
				in.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
		
		
		
	
		
		
		
	
		
		
		
		
		}
		
		
		
		
		
		


}
