package es.eadengine.saxprototype;

import es.eadengine.saxprototype.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;



/* Carga del lenguaje del motor 
 Carga de codecs de video 
 Establece Look&Feel 

 * Game Launcher lista de juegos disponibles par su carga GameLauncher
 * gameLauncher = new GameLauncher(); File file = new File("");
 * gameLauncher.init(file); new Thread(gameLauncher).start();
 


 * En un nuevo thread carga el juego , lo ejecuta y queda a la espera de
 * que se carge una nueva aventura : flags end y load ...
 


 * while( !end ) { try {
 * 
 * if( load ) { // Launch the selected adventure, if any load = false;
 * 
 * this.setVisible( false ); 
 * String adventurePath = getAdventurePath( );
 * String adventureName = getAdventureName( ); 
 * if( adventureName.length() > 0 ) { ResourceHandler.setRestrictedMode( false ); -> ya se crea
 * la instancia Singleton .. 
 * ResourceHandler.getInstance( ).setZipFile(adventurePath + adventureName + ".ead" ); 
 * Game.create( );
 * Game.getInstance( ).setAdventurePath( adventurePath );
 * Game.getInstance( ).setAdventureName( adventureName );
 * Game.getInstance( ).run( ); 
 * Game.delete( );
 * ResourceHandler.getInstance( ).closeZipFile( );
 * ResourceHandler.delete( ); 
 * this.setVisible( !initGameLoad ); }
 * if(initGameLoad ) 
 * System.exit( 0 ); }
 *  Thread.sleep( 10 ); } 
 * catch(
 * InterruptedException e ) { } }
 * 
 * ConfigData.storeToXML( );
 * 
 * this.setEnabled( false );
 * this.setVisible( false );
 * this.setFocusable( false );
 * 
 * if( !initGameLoad ) System.exit( 0 );
 */



public class Engine extends Activity {

	public static final String VERSION = "1.0b";
	
  
	/** Called when Engine activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
			
	}
	
	
	/** Starts GameLauncher when the user touches the screen*/
	public boolean onTouchEvent (MotionEvent event) {
				
		if (event.getAction()==MotionEvent.ACTION_DOWN) {
		
		startGameSelectActivity();
		return true;
		
		}
		
		else return false;
	
	}
	
	
	
    /** Starts GameLauncher and finishes current Engine Activity */
    private void startGameSelectActivity() {
    	
 		Intent i = new Intent( this , GameLauncher.class);   			
		startActivity(i);        		
	   
    	
    }
	

}