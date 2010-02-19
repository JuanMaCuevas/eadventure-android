package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.gui.GUI;

public class ActionsPanel {
	
	/**
	 * All pixels are in Dip's -> 1dip = 1px in an android default screen HVGA
	 * 480*320 - 160dpi
	 **/

	/** INVENTORY PAINT DEFINITION */

	Picture inventoryPicture;

	/** Inventory panel **/

	private static final int IPANEL_WIDTH = GUI.FINAL_WINDOW_WIDTH;
	private static final int IPANEL_HEIGHT = GUI.FINAL_WINDOW_HEIGHT;

	private static final int TRANSPARENT_PADDING = (int) (20 * GUI.DISPLAY_DENSITY_SCALE);

	/** Rounded rect panel */

	private static final int ROUNDED_RECT_STROKE_WIDTH = (int) (1 * GUI.DISPLAY_DENSITY_SCALE);
	private static final float ROUNDED_RECT_ROUND_RADIO = 15f * GUI.DISPLAY_DENSITY_SCALE;

	private static final int RPANEL_PADDING = (int) (10 * GUI.DISPLAY_DENSITY_SCALE);

	private RectF r;
	private Paint p;
	private Paint paintBorder;

	/** Grid panel */

	GridPanel gridPanel ; 
	
	int gridPanelHeight = GridPanel.VERTICAL_ICON_SPACE;


	/** INENTORY PANEL ANIMATION COORDINATES & VARIABLES */

	/** IPANEL **/

	int iPanelBottom;


	public ActionsPanel() {

		iPanelBottom = IPANEL_HEIGHT;

		p = new Paint();
		p.setColor(Color.BLACK);
		p.setStyle(Style.FILL);
		p.setAntiAlias(true);

		paintBorder = new Paint();
		paintBorder.setColor(Color.WHITE);
		paintBorder.setStyle(Style.STROKE);
		paintBorder.setStrokeWidth(ROUNDED_RECT_STROKE_WIDTH);
		paintBorder.setAntiAlias(true);

		int roundRectWidth = IPANEL_WIDTH - 2 * TRANSPARENT_PADDING;
		int roundRectHeight = IPANEL_HEIGHT - 2 * TRANSPARENT_PADDING;

		r = new RectF(0, 0, roundRectWidth, roundRectHeight);

		inventoryPicture = new Picture();

		Canvas c = inventoryPicture.beginRecording(IPANEL_WIDTH, IPANEL_HEIGHT);

		c.drawARGB(150, 0, 0, 0);
		c.translate(TRANSPARENT_PADDING, TRANSPARENT_PADDING);
		c.drawRoundRect(r, ROUNDED_RECT_ROUND_RADIO, ROUNDED_RECT_ROUND_RADIO,
				p);
		c.drawRoundRect(r, ROUNDED_RECT_ROUND_RADIO, ROUNDED_RECT_ROUND_RADIO,
				paintBorder);
		c.translate(30 * GUI.DISPLAY_DENSITY_SCALE,
				30 * GUI.DISPLAY_DENSITY_SCALE);

		inventoryPicture.endRecording();

		Rect gridPanelBounds = new Rect(TRANSPARENT_PADDING + RPANEL_PADDING,
				IPANEL_HEIGHT - TRANSPARENT_PADDING - RPANEL_PADDING - gridPanelHeight, IPANEL_WIDTH - TRANSPARENT_PADDING -RPANEL_PADDING,
				IPANEL_HEIGHT - TRANSPARENT_PADDING-RPANEL_PADDING);

		gridPanel = new GridPanel(gridPanelBounds);

	}

	public void doDraw(Canvas c) {

		c.clipRect(0, 0, IPANEL_WIDTH, iPanelBottom);
		c.save();
		c.translate(0, iPanelBottom - IPANEL_HEIGHT);
		inventoryPicture.draw(c);
		c.restore();
		c.translate(0, iPanelBottom - IPANEL_HEIGHT);
		c.translate(TRANSPARENT_PADDING + RPANEL_PADDING, IPANEL_HEIGHT - TRANSPARENT_PADDING
				- RPANEL_PADDING - gridPanelHeight);
		gridPanel.draw(c);

	}



	public void update(long elapsedTime) {

			gridPanel.update(elapsedTime);

	}

	
	public boolean pointInGrid(int x, int y) {
		return gridPanel.getBounds().contains(x, y);

	}

	public void updateDraggingGrid(int x) {
		gridPanel.updateDragging(x);

	}

	public void gridSwipe(long initialTime, int velocityX) {

		gridPanel.swipe(initialTime, velocityX);

	}

	public FunctionalElement selectItemFromGrid(int x, int y) {

		return gridPanel.selectItem(x, y);

	}

}
