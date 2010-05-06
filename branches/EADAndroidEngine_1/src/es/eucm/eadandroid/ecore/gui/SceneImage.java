package es.eucm.eadandroid.ecore.gui;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.Matrix;
import android.util.Log;


class SceneImage {

	/**
	 * Background image
	 */
	private Bitmap background;

	/**
	 * Offset of the background
	 */
	private int offsetX;

	/**
	 * Constructor of the class
	 * 
	 * @param background
	 *            Background image
	 * @param offsetX
	 *            Offset
	 */
	public SceneImage(Bitmap background, int offsetX) {

		this.background = background;
		this.offsetX = offsetX;
	}

	/**
	 * Draw the background with the offset
	 * 
	 * @param c
	 *            canvas to draw the background
	 */
	public void draw(Canvas c) {
		
		

		
		
		//Log.d("ancho imagen", ""+background.getWidth());
		//Log.d("alto imagen", ""+background.getHeight());
		
		 Rect dst = new Rect(0,0,GUI.WINDOW_WIDTH,GUI.WINDOW_HEIGHT);
         Rect src = new Rect(offsetX,0,offsetX+GUI.WINDOW_WIDTH,GUI.WINDOW_HEIGHT);
         
     c.drawBitmap(background, src, dst,null);
     /*
		Bitmap backgroungfinal=Bitmap.createBitmap(GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT,
				Bitmap.Config.RGB_565);;
		//c.drawBitmap(background, 0, 0, null);
		 backgroungfinal=Bitmap.createBitmap(background, offsetX, 0, GUI.WINDOW_WIDTH+ offsetX, GUI.WINDOW_HEIGHT,null,false);
		c.drawBitmap(backgroungfinal, 0, 0, null);
		
	*/

		
		
	}
	

}
