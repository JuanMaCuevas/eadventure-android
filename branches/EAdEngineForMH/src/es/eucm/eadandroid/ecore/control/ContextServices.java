package es.eucm.eadandroid.ecore.control;

import android.content.Context;
import android.os.Vibrator;

public class ContextServices {
    private static ContextServices INSTANCE = null;
    
    private static Context context;
 
    // Private constructor suppresses 
    private ContextServices() {}
 

    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new ContextServices();
        }
    }
 
    public static ContextServices getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }


	public static void create(Context cntxt) {
		context = cntxt;
	}
	
	public Vibrator getServiceVibrator(){
		return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);		
	}
    
    

}
