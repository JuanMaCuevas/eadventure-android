package es.eucm.eadandroid.ecore.gui.hud.elements;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import es.eucm.eadandroid.common.data.chapter.Action;
import es.eucm.eadandroid.common.data.chapter.CustomAction;
import es.eucm.eadandroid.common.gui.TC;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalItem;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalNPC;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class ActionsPanel {

	/**
	 * All pixels are in Dip's -> 1dip = 1px in an android default screen HVGA
	 * 480*320 - 160dpi
	 **/

	/** ACTIONS PANEL PAINT DEFINITION */

	Picture actionsPicture;

	/** Actions panel **/

	private static final int APANEL_WIDTH = GUI.FINAL_WINDOW_WIDTH;
	private static final int APANEL_HEIGHT = GUI.FINAL_WINDOW_HEIGHT;

	private static final int TRANSPARENT_PADDING = (int) (50 * GUI.DISPLAY_DENSITY_SCALE);

	/** Rounded rect panel */

	private static final int ROUNDED_RECT_STROKE_WIDTH = (int) (3 * GUI.DISPLAY_DENSITY_SCALE);
	private static final float ROUNDED_RECT_ROUND_RADIO = 15f * GUI.DISPLAY_DENSITY_SCALE;

	private static final int RPANEL_PADDING = (int) (10 * GUI.DISPLAY_DENSITY_SCALE);

	private RectF r;
	private Paint p;
	private Paint paintBorder;

	/** Grid panel */

	private GridPanel gridPanel;

	int gridPanelHeight;

	/** ACTIONS PANEL ANIMATION COORDINATES & VARIABLES */

	/** APANEL **/

	int aPanelBottom;

	/** ELEMENT MODEL */

	private FunctionalElement functionalElement = null;
	
	/** IMAGEICON */
	
	private Rect iconRect;
	
	private static final int ICON_WIDTH = (int)(80 * GUI.DISPLAY_DENSITY_SCALE);
	private static final int ICON_HEIGHT = (int)(48 * GUI.DISPLAY_DENSITY_SCALE);

	/** BUTTONS **/
	private ActionButtons buttons;

	private Bitmap closeButton;

	private final static String closeButtonPath = Paths.eaddirectory.ROOT_PATH
			+ "gui/hud/contextual/close1.png";

	private final static int closeButtonWidth = (int) (10 * GUI.DISPLAY_DENSITY_SCALE);

	private static final int CLOSE_X = TRANSPARENT_PADDING;
	private static int CLOSE_Y;
	private static final int CLOSE_WIDTH = closeButtonWidth * 2;
	private static final int CLOSE_BOUNDS_OFFSET = (int) (5 * GUI.DISPLAY_DENSITY_SCALE);

	private Rect closeBounds;

	/*
	 * Default action buttons, so they don't have to be generated each time
	 */
	private ActionButton grabButton;

	private ActionButton talkButton;

	private ActionButton examineButton;
	
	// Nuevos
	
	private ActionButton useButton;
	
	private ActionButton useWithButton;
	
	private ActionButton giveToButton;
	
	private ActionButton dragButton;
	
	

	private Paint textP;

	private Paint closeP;

	public ActionsPanel() {

		aPanelBottom = APANEL_HEIGHT;

		p = new Paint();
		p.setColor(Color.BLACK);
		p.setStyle(Style.FILL);
		p.setAntiAlias(true);

		paintBorder = new Paint();
		paintBorder.setColor(Color.WHITE);
		paintBorder.setStyle(Style.STROKE);
		paintBorder.setStrokeWidth(ROUNDED_RECT_STROKE_WIDTH);
		paintBorder.setAntiAlias(true);

		int roundRectWidth = APANEL_WIDTH - 2 * TRANSPARENT_PADDING;
		 int roundRectHeight = APANEL_HEIGHT - 2 * TRANSPARENT_PADDING;

		//int roundRectHeight = 2 * gridPanelHeight;
		 
		int buttonHeight = 58;
		int buttonWidth = 96;
		 
		gridPanelHeight = 150 ;
		

		r = new RectF(0, 0, roundRectWidth, roundRectHeight);

		Rect gridPanelBounds = new Rect(TRANSPARENT_PADDING + RPANEL_PADDING,
				APANEL_HEIGHT - TRANSPARENT_PADDING - RPANEL_PADDING
						- gridPanelHeight, APANEL_WIDTH - TRANSPARENT_PADDING
						- RPANEL_PADDING, APANEL_HEIGHT - TRANSPARENT_PADDING
						- RPANEL_PADDING);		
		
		gridPanel = new GridPanel(gridPanelBounds,buttonHeight,buttonWidth);

		buttons = new ActionButtons();

		grabButton = new ActionButton(ActionButton.GRAB_BUTTON);
		talkButton = new ActionButton(ActionButton.TALK_BUTTON);
		examineButton = new ActionButton(ActionButton.EXAMINE_BUTTON);
		useButton = new ActionButton(ActionButton.USE_BUTTON);
		useWithButton = new ActionButton(ActionButton.USE_WITH_BUTTON);
		giveToButton = new ActionButton(ActionButton.GIVE_TO_BUTTON);
		dragButton = new ActionButton(ActionButton.DRAG_BUTTON);

		gridPanel.setDataSet(buttons);

		textP = new Paint(Paint.ANTI_ALIAS_FLAG);
		textP.setColor(0xFFFFFFFF);
		textP.setTextSize(25 * GUI.DISPLAY_DENSITY_SCALE);
		textP.setTypeface(Typeface.SANS_SERIF);
		textP.setTextAlign(Align.LEFT);

		closeP = new Paint(Paint.ANTI_ALIAS_FLAG);
		closeP.setColor(Color.WHITE);
		closeP.setStyle(Style.STROKE);
		closeP.setStrokeWidth(ROUNDED_RECT_STROKE_WIDTH);

		CLOSE_Y = APANEL_HEIGHT - TRANSPARENT_PADDING - RPANEL_PADDING
				- (int) r.height();

		Bitmap auxCloseButton = MultimediaManager.getInstance().loadImage(
				closeButtonPath, MultimediaManager.IMAGE_MENU);

		closeButton = Bitmap.createScaledBitmap(auxCloseButton,
				closeButtonWidth * 2, closeButtonWidth * 2, false);

		closeBounds = new Rect(CLOSE_X - CLOSE_BOUNDS_OFFSET, CLOSE_Y
				- CLOSE_BOUNDS_OFFSET, CLOSE_X + CLOSE_WIDTH
				+ CLOSE_BOUNDS_OFFSET, CLOSE_Y + CLOSE_WIDTH
				+ CLOSE_BOUNDS_OFFSET);
		
		iconRect = new Rect(0,0,ICON_WIDTH,ICON_HEIGHT);

	}

	public void setElementInfo(FunctionalElement fe) {

		functionalElement = fe;

		buttons.clear();

		if (functionalElement instanceof FunctionalItem) {
			FunctionalItem item = (FunctionalItem) functionalElement;
			addDefaultObjectButtons(item);
			addCustomActionButtons(((FunctionalItem) functionalElement)
					.getItem().getActions(), functionalElement);
		}
		if (functionalElement instanceof FunctionalNPC) {
			addDefaultCharacterButtons(((FunctionalNPC) functionalElement));
			addCustomActionButtons(((FunctionalNPC) functionalElement).getNPC()
					.getActions(), functionalElement);
		}

		doPanelPicture();

	}

	/**
	 * Method that adds the necessary custom action buttons to the list of
	 * buttons.
	 * 
	 * @param actions
	 *            the actions of the element
	 * @param functionalElement
	 *            the functional element with the actions
	 */
	private void addCustomActionButtons(List<Action> actions,
			FunctionalElement functionalElement) {

		List<CustomAction> added = new ArrayList<CustomAction>();
		boolean drag_to = false;
		for (Action action : actions) {
			// if( buttons.size( ) >= 8 )
			// return;
			if (action.getType() == Action.DRAG_TO && !drag_to) {
				buttons.add(new ActionButton(ActionButton.DRAG_BUTTON));
				drag_to = true;
			}
			if (action.getType() == Action.CUSTOM) {
				boolean add = functionalElement
						.getFirstValidCustomAction(((CustomAction) action)
								.getName()) != null;
				for (CustomAction customAction : added) {
					if (customAction.getName().equals(
							((CustomAction) action).getName()))
						add = false;
				}
				if (add) {
					buttons.add(new ActionButton((CustomAction) action));
					added.add((CustomAction) action);
				}
			} else if (action.getType() == Action.CUSTOM_INTERACT /*
																 * &&
																 * functionalElement
																 * .
																 * isInInventory
																 * ( )
																 */) {
				boolean add = functionalElement
						.getFirstValidCustomInteraction(((CustomAction) action)
								.getName()) != null;
				for (CustomAction customAction : added) {
					if (customAction.getName().equals(
							((CustomAction) action).getName()))
						add = false;
				}
				if (add) {
					buttons.add(new ActionButton((CustomAction) action));
					added.add((CustomAction) action);
				}
			}
		}
	}

	/**
	 * Adds the default buttons for a character element
	 * 
	 * @param functionalNPC
	 */
	private void addDefaultCharacterButtons(FunctionalNPC functionalNPC) {

		buttons.add(examineButton);
		buttons.add(talkButton);
		boolean use = functionalNPC.getFirstValidAction(Action.USE) != null;
		if (use) {
			useButton.setName(TC.get("ActionButton.Use"));
			buttons.add(useButton);
		}
	}

	/**
	 * Adds the default buttons for non-character elements
	 */
	private void addDefaultObjectButtons(FunctionalItem item) {

		buttons.add(examineButton);

		if (!item.isInInventory()) {
			grabButton.setName(TC.get("ActionButton.Grab"));
			if (item.getFirstValidAction(Action.GRAB) != null)
				buttons.add(grabButton);
			if (item.getFirstValidAction(Action.USE) != null) {
				useButton.setName(TC.get("ActionButton.Use"));
				buttons.add(useButton);
			}
		} else {
			boolean useAlone = item.canBeUsedAlone();
			boolean giveTo = item.getFirstValidAction(Action.GIVE_TO) != null;
			boolean useWith = item.getFirstValidAction(Action.USE_WITH) != null;
			if (useAlone && !giveTo && !useWith) {
				useButton.setName(TC.get("ActionButton.Use"));
				buttons.add(useButton);
			} else if (!useAlone && giveTo && !useWith) {
				giveToButton.setName(TC.get("ActionButton.GiveTo"));
				buttons.add(giveToButton);
			} else if (!useAlone && !giveTo && useWith) {
				useWithButton.setName(TC.get("ActionButton.UseWith"));
				buttons.add(useWithButton);
			} else if (!useAlone && giveTo && useWith) {
				useButton.setName(TC.get("ActionButton.UseGive"));
				buttons.add(useButton);				
			} else {
				useButton.setName(TC.get("ActionButton.Use"));
				buttons.add(useButton);
			}
		}
	}

	private void doPanelPicture() {

		actionsPicture = new Picture();

		Canvas c = actionsPicture.beginRecording(APANEL_WIDTH, APANEL_HEIGHT);

		c.drawARGB(150, 0, 0, 0);
		c.translate(TRANSPARENT_PADDING, TRANSPARENT_PADDING);
		c.drawRoundRect(r, ROUNDED_RECT_ROUND_RADIO, ROUNDED_RECT_ROUND_RADIO,
				p);
		c.drawRoundRect(r, ROUNDED_RECT_ROUND_RADIO, ROUNDED_RECT_ROUND_RADIO,
				paintBorder);
		c.translate(-closeButtonWidth / 2, -closeButtonWidth / 2);
		c.drawBitmap(closeButton, 0, 0, null);
		c.translate(closeButtonWidth / 2, closeButtonWidth / 2);
//		c.drawCircle(r.width()-ROUNDED_RECT_ROUND_RADIO,ROUNDED_RECT_ROUND_RADIO,ROUNDED_RECT_ROUND_RADIO, closeP);
		c.translate(35 * GUI.DISPLAY_DENSITY_SCALE,
				30 * GUI.DISPLAY_DENSITY_SCALE);

		if (functionalElement instanceof FunctionalItem) {

			Bitmap image = ((FunctionalItem) functionalElement).getIconImage();
			if (image != null)
				c.drawBitmap(image, null, iconRect, null);
		}

		c.translate(iconRect.width() + 20 * GUI.DISPLAY_DENSITY_SCALE,
				iconRect.height()/2 + textP.getTextSize()/2);

		new AnimText(350,functionalElement.getElement().getName(),textP).draw(c);
		
//		c.drawText(functionalElement.getElement().getName(), 0, 0, textP);

		actionsPicture.endRecording();

	}

	public void doDraw(Canvas c) {

		c.save();
		c.save();
		actionsPicture.draw(c);
		c.restore();
		c.translate(0, aPanelBottom - APANEL_HEIGHT);
		c.translate(TRANSPARENT_PADDING + RPANEL_PADDING, APANEL_HEIGHT
				- TRANSPARENT_PADDING - RPANEL_PADDING - gridPanelHeight);
		gridPanel.draw(c);
		c.restore();

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

	public Object selectItemFromGrid(int x, int y) {

		return gridPanel.selectItem(x, y);

	}

	public FunctionalElement getElementInfo() {
		return functionalElement;
	}

	public boolean isInCloseButton(int srcX, int srcY) {

		return closeBounds.contains(srcX, srcY);
	}

	public void setItemFocus(int srcX, int srcY) {
		
		gridPanel.setItemFocus(srcX,srcY);
			
	}

	public void resetItemFocus() {
		gridPanel.resetItemFocus();
		
	}
	

}
