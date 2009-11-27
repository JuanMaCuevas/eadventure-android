package es.eucm.eadventure.common.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class JOptionPane {

	public static final int ERROR_MESSAGE = 0;

	public static void showMessageDialog(Object object, String errorMessage,
			String errorTitle, int errorType) {
		
	    AlertDialog.Builder builder = new AlertDialog.Builder(null); /* Tengo que a–adirle el contexto */
	    builder.setMessage(errorMessage)
	           .setCancelable(true)
	           .setNeutralButton("OK",new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	               }
	           });
	          
	    AlertDialog alert = builder.create();
		
		
	}

}
