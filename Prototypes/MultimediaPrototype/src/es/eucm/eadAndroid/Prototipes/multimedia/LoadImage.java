package es.eucm.eadAndroid.Prototipes.multimedia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LoadImage {
	
	Bitmap bit;

	public LoadImage() {
		// TODO Auto-generated constructor stub
	}
	
	public Bitmap dameimagen(String ruta)
	{
		
		String filepath = "";
		File imagefile = new File(filepath + ruta);
		FileInputStream fis;
		try {
			fis = new FileInputStream(imagefile);
			
			bit= BitmapFactory.decodeStream(fis);
	        
	        
	        fis.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bit;
		
	}

}
