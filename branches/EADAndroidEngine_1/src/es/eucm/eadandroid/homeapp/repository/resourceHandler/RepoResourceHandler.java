package es.eucm.eadandroid.homeapp.repository.resourceHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker.ProgressNotifier;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class RepoResourceHandler {

	private static final int DOWNLOAD_BUFFER_SIZE = 1048576;
	

	//TODO no lo he comprobado que vaya a tirar
	public static Bitmap DownloadImage(String url_from, ProgressNotifier pn) {
		
			pn.notifyProgress(0, "Downloading image "+url_from);
	        Bitmap bitmap = null;
	        InputStream in = null;        
	        try {
	            in = OpenHttpConnection(url_from);
	            bitmap = BitmapFactory.decodeStream(in);
	            in.close();
	        } catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	        
			pn.notifyProgress(100, "Image downloaded");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			pn.notifyFinished("Image downloaded");
	        
	        return bitmap;                
	    }
// descarga el fichero
	public static void downloadFile(String url_from, String path_to,
			ProgressNotifier pt) throws IOException {
		

		
		int last = url_from.lastIndexOf("/");
		String fileName = url_from.substring(last + 1);

		/*
		 * StringTokenizer prueba = new StringTokenizer(ruta, "/", true); String
		 * aux1 = null; while (prueba.hasMoreTokens()) { aux1 =
		 * prueba.nextToken(); }
		 */
		
		
		URL u = new URL(url_from);
		HttpURLConnection c = (HttpURLConnection) u.openConnection();

		pt.notifyProgress(0, "Connection opened");

		c.setRequestMethod("GET");
		c.setDoOutput(true);
		c.connect();

		pt.notifyProgress(0, "Connection established");



		File file = new File(path_to, fileName);

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);

			InputStream in;
			try {
				
				float fileSize = c.getContentLength();
				
				Log.d("fileSize",String.valueOf(fileSize));

				float iterNum =  new Float(fileSize/(DOWNLOAD_BUFFER_SIZE) + 1).intValue();
				
				Log.d("numIter",String.valueOf(iterNum));
				
				float prIncrement = 100/iterNum ;
				
				Log.d("prIncrement",String.valueOf(prIncrement));
				
				Float progress = new Float(0);
								
				in = c.getInputStream();
				byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE];
				int len1 = 0;
				while ((len1 = in.read(buffer)) != -1) {
					fos.write(buffer, 0, len1);
					
					
					progress+=len1/fileSize*100;
					
					pt.notifyProgress(progress.intValue(), "Downloading " + fileName);

					Log.d("progress",String.valueOf(progress));
					
					
				}

				fos.close();
				in.close();
				
				
				
				
				
				
				
				
				//pt.notifyProgress(progress.intValue(), "Downloading " + fileName);
				
				
//TODO a pincho 
				pt.notifyFinished(fileName + " downloaded succesfully");

			} catch (IOException e) {

				pt.notifyError("Error while downloading");
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {

			pt.notifyError("Error , file not found");
			e.printStackTrace();
		}

	}
	
	  private static InputStream OpenHttpConnection(String urlString) 
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
	  
	 //////////////////// 
	 
	  

	  
	  
	  
	  //este string te da al url del juego yo cogere lo ultimo para la ruta
	  public static void unzip(String locfichero)
	  {
		  
		  
		  //me creo la ruta hasta donde dejamos los juegos
		  StringTokenizer numero=new StringTokenizer(Paths.eaddirectory.GAMES_PATH,"/",true);
	        String actual=null;
	        String total="";
	        total=numero.nextToken();
	        
	        while (numero.hasMoreElements())
	        {
	        	total=total+numero.nextToken();
	        	total=total+numero.nextToken();
	        	if (!new File(total).exists())
	        	{
	        		(new File(total)).mkdir();
	        	}
	         }
		  
		  
		  int last = locfichero.lastIndexOf("/");
			String fileName = locfichero.substring(last + 1);
		  
		  String pathfinal=Paths.eaddirectory.GAMES_PATH+fileName+"/";
		  (new File(pathfinal)).mkdir();
		  
		  
		  
		  Enumeration entries;
		    ZipFile zipFile;
	//aqui pondre pathout
		    
		    
		    
		    
		   

		    try {
		    	//aqui pathin
		      zipFile = new ZipFile(Paths.eaddirectory.ZIPPED_PATH+fileName);

		      entries = zipFile.entries();
		      
		      
		    
		   //   Vector<ZipEntry> elementos=ordena(entries);
		   
		      
		      BufferedOutputStream prueba;

		      while(entries.hasMoreElements()) {
		        ZipEntry entry = (ZipEntry)entries.nextElement();

		        numero=new StringTokenizer(entry.getName(),"/",true);
		        actual=null;
		        total="";
		        
		        while (numero.hasMoreElements())
		        {
		        	
		        	actual=numero.nextToken();
		        	total=total+actual;
		        	if (!new File(entry.getName()).exists())
		        	{
		        	
		        		if(numero.hasMoreElements())
		        		{
		        			total=total+numero.nextToken();
		        			(new File(pathfinal+total)).mkdir();
		        		}else
		        		{
		        			//fichero final
		        			
		               		prueba=new BufferedOutputStream(new FileOutputStream(pathfinal+total));
		               	 
		        			System.err.println("Extracting file: " + entry.getName());
		        			copyInputStream(zipFile.getInputStream(entry),prueba);
		          		}
		        	}else {total=total+numero.nextToken();}
		        }
		        
		        
		        
		        
		 
		      }

		      zipFile.close();
		    } catch (IOException ioe) {
		    ioe.printStackTrace();
		      return;
		    }
	  
		  
		    (new File(Paths.eaddirectory.ZIPPED_PATH+fileName)).delete();
		  
		  
	  }
	 			  
	  public static final void copyInputStream(InputStream in, OutputStream out)
	  throws IOException
	  {
	    byte[] buffer = new byte[1024];
	    int len;

	    while((len = in.read(buffer)) >= 0)
	      out.write(buffer, 0, len);

	    in.close();
	    out.close();
	  }
	  
	   
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  


}
