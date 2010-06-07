package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Bitmap;

public interface DataSet {

	int getItemCount();

	Object getItem(int i);
	
	Bitmap getNormalImageIcon(int i);
	
	Bitmap getPressedImageIcon(int i);
	
	String getItemName(int i);

}
