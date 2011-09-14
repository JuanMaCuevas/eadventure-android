package es.eucm.eadandroid.debug;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import es.eucm.eadandroid.common.auxiliar.CreateImage;

public class tests extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new SampleView(this));
			
	}
	
	 private static class SampleView extends View {
	        private Bitmap b;
	        
	        public SampleView(Context context) {
	            super(context);
	            setFocusable(true);
	            
	            b = CreateImage.createImage(100, 100, "Pruebaaaaaaa");
	        }
	        
	        
	        @Override protected void onDraw(Canvas canvas) {
	        	canvas.drawBitmap(b, 0, 0, null);
	        }
	    }

}
