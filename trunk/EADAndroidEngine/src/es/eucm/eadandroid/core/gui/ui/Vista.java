package es.eucm.eadandroid.core.gui.ui;

import android.app.Activity;
import android.widget.TextView;

public class Vista {
	
	private static TextView tv ;
	
	public static void setView(TextView tv) {
		Vista.tv = tv;
	}

	public static void setText(String text) {
		
		tv.setText(text);
	}
	
	
}
