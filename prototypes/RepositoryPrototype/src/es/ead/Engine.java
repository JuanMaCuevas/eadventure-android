package es.ead;


import java.io.File;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;




public class Engine extends Activity {

	public static final String VERSION = "1.0b";

	
	String adventures[];
	
  
	/** Called when Engine activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		
		File sdCard = Environment.getExternalStorageDirectory();
		adventures = sdCard.list(new EADFileFilter());
	
	}
	

	/** Starts GameLauncher when the user touches the screen*/

	/*
	public boolean onTouchEvent (MotionEvent event) {
				
		if (event.getAction()==MotionEvent.ACTION_DOWN) {
		
			
		startGameSelectActivity();
		return true;
		
		}
		
		else return false;
	
	}
	*/
	
	
    /** Starts GameLauncher and finishes current Engine Activity */
	
	
	private void nuevosjuegos() {
    	
 		Intent i = new Intent( this ,NewGames.class);
 		i.putExtra("pru", adventures);
 	
		startActivity(i);        		
	   
    	
    }
	
	
private void juegosdescargados() {
    	
 		Intent i = new Intent( this ,DownloadedGames.class);
 		i.putExtra("pru", adventures);
 	
		startActivity(i);        		
	   
    	
    }

private void preferencias() {
	
		Intent i = new Intent( this ,Preferences.class);
		startActivity(i);        		
}
	
	 /* Creates the menu items */
		 public boolean onCreateOptionsMenu(Menu menu) {
			
		     menu.add(0, 0, 0, "Play games").setIcon(android.R.drawable.ic_menu_gallery);
		     menu.add(0, 1, 0, "new games").setIcon(android.R.drawable.ic_menu_add);
		     menu.add(0, 2, 0, "preferences").setIcon(android.R.drawable.ic_menu_preferences);
		     Drawable s;
		     return true;
		 }

		 
		 
		 
		 /* Handles item selections */
		 public boolean onOptionsItemSelected(MenuItem item) {
			  switch (item.getItemId()) {
		     case 0:
		    	 juegosdescargados();
		         return true;
		     case 1:
		    	 nuevosjuegos();
		         return true;
		     case 2:
		        preferencias();
		         return true;
		     }
		     
		     return false;
		 }

		 
		 
		
	  
	  
	  
	  

}