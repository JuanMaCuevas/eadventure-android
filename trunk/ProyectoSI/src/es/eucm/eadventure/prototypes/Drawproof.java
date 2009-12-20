package es.eucm.eadventure.prototypes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import es.eucm.eadventure.prototypes.Superficie.SuperficieThread;

public class Drawproof extends Activity {
    

	private static final String TAG ="DrawProof";
	
	private Superficie mSuperficie;
	private SuperficieThread mThread;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // tell system to use the layout defined in our XML file
         
        setContentView(R.layout.main); 
        // get handles to the view from XML, and its LunarThread
        mSuperficie = (Superficie)findViewById(R.id.lunar);
        mThread = mSuperficie.getThread();     
    //	mSuperficie.getThread().unpause();
   
      
            
        
        
    }

    

    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSuperficie.getThread().pause(); // pause game when Activity pauses
        
    }
    
    /**
     * Notification that something is about to happen, to give the Activity a
     * chance to save state.
     * 
     * @param outState a Bundle into which this Activity should save its state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        mThread.saveState(outState);
        Log.w(this.getClass().getName(), "SIS called");
    }
   

}
