package dd.d;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class TestUpload extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try {
			doFileDownload();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doFileDownload() throws Exception {

		String url = "http://eadventure-android.googlecode.com/files/ProtocoloDeIncendios.ead";

		File root = Environment.getExternalStorageDirectory();

		URL u = new URL(url);
		HttpURLConnection c = (HttpURLConnection) u.openConnection();
		c.setRequestMethod("GET");
		c.setDoOutput(true);
		c.connect();
		
		
		
		File file = new File(root,"ProtocoloDeIncendios2.ead");
		
		Log.d("TestUpload",Environment.getExternalStorageState());
		
		FileOutputStream fos = new FileOutputStream(file);
		

		InputStream in = c.getInputStream();

		byte[] buffer = new byte[1024];
		int len1 = 0;
		while ( (len1 = in.read(buffer)) != -1 ) {
		  fos.write(buffer,0, len1);
		}

		fos.close();
		in.close();
		
		}
		
		

	

}