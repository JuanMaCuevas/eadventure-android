package es.eucm.eadandroid.ecore.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;

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

		c.drawBitmap(background, 0, 0, null);
	}
	

}
