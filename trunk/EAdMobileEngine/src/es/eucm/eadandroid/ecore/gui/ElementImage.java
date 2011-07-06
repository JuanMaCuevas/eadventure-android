package es.eucm.eadandroid.ecore.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalhighlights.FunctionalHighlight;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.pathdirectory.Paths;


class ElementImage {

	/**
	 * Image
	 */
	protected Bitmap image;
	protected Bitmap temp;
	protected Bitmap hand;
	protected Bitmap dest;

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
			if (this.functionalElement != null && this.functionalElement.getDragging()){
				hand = MultimediaManager.getInstance( ).loadImage(Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/drag.png" , MultimediaManager.IMAGE_SCENE);
				temp = Bitmap.createScaledBitmap(image, (int)(this.functionalElement.getWidth()*this.getFunctionalElement().getScale()*1.075*GUI.DISPLAY_DENSITY_SCALE), (int)(this.functionalElement.getHeight()*this.getFunctionalElement().getScale()*1.075*GUI.DISPLAY_DENSITY_SCALE), true);
				dest = temp.copy(Bitmap.Config.ARGB_4444, true);
				Paint p = new Paint(Paint.DITHER_FLAG * Paint.ANTI_ALIAS_FLAG);
				p.setAlpha(150); 
		        c.drawBitmap(dest, x, y, p);
		        c.drawBitmap(hand, x + 10 * GUI.DISPLAY_DENSITY_SCALE, y + (3 * this.functionalElement.getHeight()*this.getFunctionalElement().getScale())/4, null);
		        temp = null;
			}				
			else c.drawBitmap(image, x, y, null);
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