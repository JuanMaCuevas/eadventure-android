package es.eucm.eadAndroid.Prototipes;


import java.io.File;

import es.eucm.eadAndroid.Prototipes.multimedia.MultimediaManager;
import es.eucm.eadAndroid.Prototipes.multimedia.Sound;
import es.eucm.eadAndroid.Prototipes.multimedia.SoundAndroidMp3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MultimediaPrototipe extends Activity {
    /** Called when the activity is first created. */
	
	 
	 long pru=0;
	 Sound s;
	 
	 
	 private Handler cargador = new Handler() {
		 
		 
		 public void handleMessage(Message msg) {
			 ImageView img = (ImageView) findViewById(R.id.ImageView01);
			 Bitmap p= (Bitmap) Information.getInstance().getobject(msg.getData().getString("valor"));
			 img.setImageBitmap(p);
		 }
		 
	 }; 
	 
	 
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
       
        
     
     
        }
    
    
    /* Creates the menu items */
	 public boolean onCreateOptionsMenu(Menu menu) {
		
	     menu.add(0, 0, 0, "finish music").setIcon(android.R.drawable.ic_menu_gallery);
	     menu.add(0, 1, 0, "music").setIcon(android.R.drawable.ic_menu_gallery);
	     menu.add(0, 2, 0, "image").setIcon(android.R.drawable.ic_menu_gallery);
	     menu.add(0, 3, 0, "flipimage").setIcon(android.R.drawable.ic_menu_gallery);
	     menu.add(0, 4, 0, "delete image").setIcon(android.R.drawable.ic_menu_gallery);
	    
	     return true;
	 }

	 public void play()
	 {
		
	      pru=MultimediaManager.getInstance().loadMusic("/sdcard/test.mp3", true);
	      
	      MultimediaManager.getInstance().startPlaying(pru);
	 }
	 
	 
	 /* Handles item selections */
	 public boolean onOptionsItemSelected(MenuItem item) {
		  switch (item.getItemId()) {
	     case 0:
	    	 MultimediaManager.getInstance().stopPlaying(pru); 
	    	    return true;
	     case 1:
	    	 play();
	         return true;
	     case 2:
	    	putimage();
	         return true;
	     case 3:
	    	this.putreverseimage();
	         return true;
	    }
	     
	     return false;
	 }
	 
	 
	 public void putimage()
	 {
		 Information.getInstance().addobject("valor",MultimediaManager.getInstance().loadImage("/sdcard/ProtocoloDeIncendios.JPG",1));
		  Message msg = new Message();
			Bundle b = new Bundle();
			b.putString("valor", "valor");
			msg.setData(b);
			cargador.sendMessage(msg);
	 }
	 
	 public void putreverseimage()
	 {
		 Information.getInstance().addobject("valor2",MultimediaManager.getInstance().loadMirroredImageFromZip("/sdcard/ProtocoloDeIncendios.JPG",1));
		  Message msg = new Message();
			Bundle b = new Bundle();
			b.putString("valor", "valor2");
			msg.setData(b);
			cargador.sendMessage(msg);
	 }



}