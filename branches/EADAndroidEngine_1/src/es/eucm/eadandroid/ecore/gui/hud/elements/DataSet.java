package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Bitmap;

public interface DataSet {

	int getItemCount();

	Object getItem(int i);
	
	Bitmap getItemImageIcon(int i);
	
	String getItemName(int i);

}
