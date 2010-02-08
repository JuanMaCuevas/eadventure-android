package video.es;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

 
public class Test extends Activity implements SurfaceHolder.Callback { 
    SurfaceView sf = null; 
    MediaPlayer mp0 = null; 
    SurfaceHolder holder = null; 
@Override 
public void onCreate(Bundle savedInstanceState) { 
	
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
            this.sf = (SurfaceView)findViewById(R.id.pru); 
            this.holder = sf.getHolder(); 
            holder.addCallback(this); 
            
            mp0 = new MediaPlayer(); 
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
} 

public void surfaceCreated(SurfaceHolder holder2) { 
    Log.d("surfaceCreated()", "surfaceCreated..."); 
            mp0 = new MediaPlayer(); 
          
            try { 
                    mp0.setDataSource("/sdcard/1.3gp"); 
                    mp0.setDisplay(holder); 
                    mp0.prepare(); 
            } 
            catch(Exception e){ 
            } 
          //  mp0.setOnPreparedListener(this); 
            mp0.setOnCompletionListener(new OnCompletionListener(){

				@Override
				public void onCompletion(MediaPlayer mp) {
					mp0.release();	
					}}); 
            mp0.start(); 
            mp0.setAudioStreamType(AudioManager.STREAM_MUSIC); 
    } 


    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, 
int k) { 
            Log.d("surfaceChanged()", "surfaceChanged..."); 
} 
    public void surfaceDestroyed(SurfaceHolder surfaceholder) { 
            Log.d("surfaceDestroyed()", "surfaceDestroyed..."); 
    } 
    
	 public boolean onCreateOptionsMenu(Menu menu) {
			
	     menu.add(0, 0, 0, "pause").setIcon(android.R.drawable.ic_menu_gallery);
	     menu.add(0, 1, 0, "continue").setIcon(android.R.drawable.ic_menu_add);
	     menu.add(0, 2, 0, "finish").setIcon(android.R.drawable.ic_menu_preferences);
	     Drawable s;
	     return true;
	 } 
	 
	 
	 
	 
	 
	 
	 /* Handles item selections */
	 public boolean onOptionsItemSelected(MenuItem item) {
		  switch (item.getItemId()) {
	     case 0:
	    	mp0.pause();
	         return true;
	     case 1:
	    	 mp0.start();
	         return true;
	     case 2:
	       mp0.stop();
	       mp0.release();
	       mp0=null;
	         return true;
	     }
	     
	     return false;
	 }

@Override 
protected void onDestroy() 
{ 
    super.onDestroy(); 
    if (mp0 != null) { 
        mp0.release(); 
        mp0 = null; 
    } 
     
} 
}