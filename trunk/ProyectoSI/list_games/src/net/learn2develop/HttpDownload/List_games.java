package net.learn2develop.HttpDownload;

import android.app.Activity;
import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element; 
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import es.eadengine.saxprototype.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class List_games extends Activity {
    /** Called when the activity is first created. */
 
	/*
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }   
      
    
    */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
        
        Bitmap bitmap =DownloadImage("http://eadventure-android.googlecode.com/files/arroba.JPG");
        
        setContentView(R.layout.main);
        ImageView img;
        img = (ImageView) findViewById(R.id.icon);
        img.setImageBitmap(bitmap);
        
        TextView txt1;
        String str1 = "juego1";
        txt1 = (TextView) findViewById(R.id.firstLine);
        txt1.setText(str1);       
       
        
        TextView txt2;
        String str2 = DownloadText("http://eadventure-android.googlecode.com/files/juego1.txt");
        txt2 = (TextView) findViewById(R.id.secondLine);
        txt2.setText(str2);
       
        
         
    }

    
    
    
    
    
    
    
    
   
    
    
    private String DownloadText(String URL)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
        
        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
          String str = "";
          char[] inputBuffer = new char[BUFFER_SIZE];          
        try {
            while ((charRead = isr.read(inputBuffer))>0)
            {                    
                //---convert the chars to a String---
                String readString = 
                    String.copyValueOf(inputBuffer, 0, charRead);                    
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }    
        return str;        
    }

    
    private Bitmap DownloadImage(String URL)
    {        
        Bitmap bitmap = null;
        InputStream in = null;        
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return bitmap;                
    }
    
    

    
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
    
    
}
