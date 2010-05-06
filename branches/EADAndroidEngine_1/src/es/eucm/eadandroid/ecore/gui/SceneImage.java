package es.eucm.eadandroid.ecore.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

class SceneImage {

	/**
	 * Background image
	 */
	private Bitmap backgroundSrc;
	private Rect dst;

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

		this.backgroundSrc = background;
		this.offsetX = offsetX;
		this.dst = new Rect(0,0,GUI.WINDOW_WIDTH,GUI.WINDOW_HEIGHT);
	}

	/**
	 * Draw the background with the offset
	 * 
	 * @param c
	 *            canvas to draw the background
	 */
	public void draw(Canvas c) {

		Rect src = new Rect(offsetX,0,offsetX+GUI.WINDOW_WIDTH,GUI.WINDOW_HEIGHT);		
	    c.drawBitmap(backgroundSrc, src, dst,null);
	
	}
	

}
