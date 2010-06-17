package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.gui.GUI;

public class Inventory {

	/**
	 * All pixels are in Dip's -> 1dip = 1px in an android default screen HVGA
	 * 480*320 - 160dpi
	 **/

	/** Folding - Unfolding region in dips **/

	public static final int UNFOLD_REGION_POSY = (int) (20 * GUI.DISPLAY_DENSITY_SCALE);
	public static final int FOLD_REGION_POSY = (int) ((GUI.FINAL_WINDOW_HEIGHT - 20 * GUI.DISPLAY_DENSITY_SCALE));

	private static final int drag_offset = (int) (15 * GUI.DISPLAY_DENSITY_SCALE);

	/** INVENTORY PAINT DEFINITION */

	Picture inventoryPicture;

	/** Inventory panel **/

	private static final int IPANEL_WIDTH = GUI.FINAL_WINDOW_WIDTH;
	private static final int IPANEL_HEIGHT = GUI.FINAL_WINDOW_HEIGHT;

	private static final int TRANSPARENT_PADDING = (int) (20 * GUI.DISPLAY_DENSITY_SCALE);

	/** Rounded rect panel */

	private static final int ROUNDED_RECT_STROKE_WIDTH = (int) (3 * GUI.DISPLAY_DENSITY_SCALE);
	private static final float ROUNDED_RECT_ROUND_RADIO = 15f * GUI.DISPLAY_DENSITY_SCALE;

	private static final int RPANEL_PADDING = (int) (10 * GUI.DISPLAY_DENSITY_SCALE);

	private RectF r;
	private Paint p;
	private Paint paintBorder;

	/** Grid panel */

	GridPanel gridPanel;


	/** INENTORY PANEL ANIMATION COORDINATES & VARIABLES */

	/** IPANEL **/

	int iPanelBottom;

	boolean animating;
	boolean anchored;

	public Inventory() {

		iPanelBottom = 0;
		animating = false;
		anchored = false;

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
				TRANSPARENT_PADDING + RPANEL_PADDING, IPANEL_WIDTH - TRANSPARENT_PADDING -RPANEL_PADDING,
				IPANEL_HEIGHT - TRANSPARENT_PADDING-RPANEL_PADDING);

		gridPanel = new GridPanel(gridPanelBounds,GridPanel.DEFAULT_ICON_HEIGHT,GridPanel.DEFAULT_ICON_WIDTH);

	}

	public void doDraw(Canvas c) {
		c.save();
		c.clipRect(0, 0, IPANEL_WIDTH, iPanelBottom);
		c.save();
		c.translate(0, iPanelBottom - IPANEL_HEIGHT);
		inventoryPicture.draw(c);
		c.restore();
		c.translate(0, iPanelBottom - IPANEL_HEIGHT);
		c.translate(TRANSPARENT_PADDING + RPANEL_PADDING, TRANSPARENT_PADDING
				+ RPANEL_PADDING);
		// FIXME estooo tiene que quitarse de aqui y ponerl en otro sitio ;D!!!
		gridPanel.setDataSet(Game.getInstance().getInventory());
		gridPanel.draw(c);
		c.restore();

	}

	public void updateDraggingPos(int y) {

		iPanelBottom = Math.min(IPANEL_HEIGHT, y + drag_offset);
	}

	public boolean isAnimating(int y) {
		if (iPanelBottom > IPANEL_HEIGHT / 2)
			animating = true;
		return animating;
	}

	public void resetPos() {

		iPanelBottom = 0;

	}

	public void update(long elapsedTime) {

		if (animating) {
			if (iPanelBottom < IPANEL_HEIGHT) {
				iPanelBottom = Math.min(IPANEL_HEIGHT, iPanelBottom + 15);
			} else {
				anchored = true;
				animating = false;
			}
		} else
			gridPanel.update(elapsedTime);

	}

	public boolean isAnchored() {
		return anchored;
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

	public Object selectItemFromGrid(int x, int y) {

		return gridPanel.selectItem(x, y);

	}

	public void setItemFocus(int posX, int posY) {
		gridPanel.setItemFocus(posX, posY);
		
	}

	public void resetItemFocus() {
		gridPanel.resetItemFocus();
		
	}

}
