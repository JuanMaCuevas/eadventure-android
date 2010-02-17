package es.eucm.eadandroid.ecore.gui;

import android.graphics.Canvas;

class Text {

	/**
	 * Array string
	 */
	private String[] text;

	/**
	 * X coordinate
	 */
	private int x;

	/**
	 * Y coordinate
	 */
	private int y;

	/**
	 * Color of the text
	 */
	private int textColor;

	/**
	 * Color of the borde of the text
	 */
	private int borderColor;

	private int bubbleBkgColor;

	private int bubbleBorderColor;

	private boolean showArrow = true;

	private boolean showBubble = false;

	/**
	 * Constructor of the class
	 * 
	 * @param text
	 *            Array string
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param textColor
	 *            Color of the text
	 * @param borderColor
	 *            Color of the borde of the text
	 */
	public Text(String[] text, int x, int y, int textColor, int borderColor) {

		this.text = text;
		this.x = x;
		this.y = y;
		this.textColor = textColor;
		this.borderColor = borderColor;
	}

	/**
	 * Constructor of the class
	 * 
	 * @param text
	 *            Array string
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param textColor
	 *            Color of the text
	 * @param borderColor
	 *            Color of the borde of the text
	 */
	public Text(String[] text, int x, int y, int textColor,
			int borderColor, int bubbleBkgColor, int bubbleBorderColor,
			boolean showArrow) {

		this.text = text;
		this.x = x;
		this.y = y;
		this.textColor = textColor;
		this.borderColor = borderColor;
		this.showBubble = true;
		this.bubbleBkgColor = bubbleBkgColor;
		this.bubbleBorderColor = bubbleBorderColor;
		this.showArrow = showArrow;
	}

	/**
	 * Draw the text onto the position
	 * 
	 * @param g
	 *            Graphics2D to draw the text
	 */
	public void draw(Canvas c) {

		if (showBubble)
			
			GUI.drawStringOnto(c, text, x, y, textColor, borderColor,bubbleBkgColor, bubbleBorderColor, showArrow);
		else
			GUI.drawStringOnto(c, text, x, y, textColor, borderColor);
	}

	/**
	 * Returns the Y coordinate
	 * 
	 * @return Y coordinate
	 */
	public int getY() {

		return y;
	}
}
