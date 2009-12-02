package dd.d;

import android.app.Activity;
import android.os.Bundle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TestUpload extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.doFileUpload();
    }
   
    private void doFileUpload(){

       HttpURLConnection conn = null;
       DataOutputStream dos = null;
       DataInputStream inStream = null;

     
      String exsistingFileName = "/sdcard/prueba2.php";
       // Is this the place are you doing something wrong.

       String lineEnd = "\r\n";
       String twoHyphens = "--";
       String boundary =  "*****";


       int bytesRead, bytesAvailable, bufferSize;

       byte[] buffer;

       int maxBufferSize = 1*1024*1024;

       String responseFromServer = "";

       String urlString = "http://192.168.0.1/prueba2.php";


       try
       {
        //------------------ CLIENT REQUEST
     
       Log.e("MediaPlayer","Inside second Method");

   //   FileInputStream fileInputStream = new FileInputStream(new File(exsistingFileName) );

        // open a URL connection to the Servlet
      Log.e("prueba","mierda1");
      
      
      
        URL url = new URL(urlString);


        // Open a HTTP connection to the URL

        conn = (HttpURLConnection) url.openConnection();
        
        

        // Allow Inputs
        conn.setDoInput(true);
      
        Log.e("prueba","mierda2");
        // Allow Outputs
        conn.setDoOutput(true);
     
        // Don't use a cached copy.
        conn.setUseCaches(false);

       // Use a post method.
        conn.setRequestMethod("GET");
   
        conn.setRequestProperty("Connection", "Keep-Alive");
        Log.e("prueba","mierda2");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
        Log.e("prueba","mierda2");
        
       if (conn.getDoOutput())
       {
    	   
       }else{
    	   
       }
        Log.e("prueba","mierda2");
        
        
     
        dos = new DataOutputStream( conn.getOutputStream() );
        Log.e("prueba","mierda2");
        DataInputStream pru= new DataInputStream(conn.getInputStream());
        
       // Log.e("texto1",dos.);
        
        Log.e("prueba","mierda2");
        Log.e("texto2",pru.readLine());
       


        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + exsistingFileName +"\"" + lineEnd);
        dos.writeBytes(lineEnd);

        Log.e("MediaPlayer","Headers are written");

        // create a buffer of maximum size

      //  bytesAvailable = fileInputStream.available();
    /*    bufferSize = Math.min(bytesAvailable, maxBufferSize);
       buffer = new byte[bufferSize];

        // read file and write it into form...

        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0)
        {
       //  dos.write(buffer, 0, bufferSize);
         bytesAvailable = fileInputStream.available();
         bufferSize = Math.min(bytesAvailable, maxBufferSize);
         bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        // send multipart form data necesssary after file data...

        
  */      
        dos.writeBytes(lineEnd);
       dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        // close streams
        Log.e("MediaPlayer","File is written");
      //  fileInputStream.close();
        dos.flush();
      dos.close();


       }
       catch (MalformedURLException ex)
       {
            Log.e("MediaPlayer", "error: " + ex.getMessage()+"primero", ex);
       }

       catch (IOException ioe)
       {
            Log.e("MediaPlayer", "error: " + ioe.getMessage()+"segundo", ioe);
       }


       //------------------ read the SERVER RESPONSE


       try {
             inStream = new DataInputStream ( conn.getInputStream() );
             String str;
           
             while (( str = inStream.readLine()) != null)
             {
                  Log.e("MediaPlayer","Server Response"+str);
             }
             inStream.close();

       }
       catch (IOException ioex){
            Log.e("MediaPlayer", "error: " + ioex.getMessage(), ioex);
       }

     }
} 