package es.eucm.eadandroid.ecore.gui;

import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalhighlights.FunctionalHighlight;
import android.graphics.Bitmap;
import android.graphics.Canvas;


class ElementImage {

	/**
	 * Image
	 */
	protected Bitmap image;

	/**
	 * X coordinate
	 */
	protected int x;

	/**
	 * Y coordinate
	 */
	protected int y;

	/**
	 * Original y, without pertinent transformations to fir the original
	 * image to scene reference image.
	 */
	protected int originalY;

	/**
	 * Depth of the image (to be painted).
	 */
	private int depth;

	private FunctionalHighlight highlight;

	private FunctionalElement functionalElement;

	/**
	 * Constructor of the class
	 * 
	 * @param image
	 *            Image
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param depth
	 *            Depth to draw the image
	 */
	
	public ElementImage(Bitmap image, int x, int y, int depth, int originalY) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.depth = depth;
		this.originalY = originalY;
		this.highlight = null;
	}

	public ElementImage(Bitmap image, int x, int y, int depth,
			int originalY, FunctionalHighlight highlight,
			FunctionalElement fe) {
		this(image, x, y, depth, originalY);
		this.highlight = highlight;
		this.functionalElement = fe;
	}

	/**
	 * Draw the image in the position
	 * 
	 * @param g
	 *            Graphics2D to draw the image
	 */
	public void draw(Canvas c) {
		if (highlight != null) {
			c.drawBitmap(highlight.getHighlightedImage(image), x
					+ highlight.getDisplacementX(), y
					+ highlight.getDisplacementY(), null);
			// OLDc.drawImage( highlight.getHighlightedImage( image ), x +
			// highlight.getDisplacementX( ), y +
			// highlight.getDisplacementY( ), null );
		} else {
			c.drawBitmap(image, x, y, null);
		}
	}

	/**
	 * Returns the depth of the element.
	 * 
	 * @return Depth of the element
	 */
	public int getDepth() {

		return depth;
	}

	/**
	 * Changes the element´s depth
	 * 
	 * @param depth
	 */
	public void setDepth(int depth) {

		this.depth = depth;
	}



	/**
	 * @return the functionalElement
	 */
	public FunctionalElement getFunctionalElement() {

		return functionalElement;
	}

	public int getOriginalY() {
		// TODO Auto-generated method stub
		return this.originalY;
	}
}