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

	/** Inventory panel **/

	private static final int APANEL_WIDTH = GUI.FINAL_WINDOW_WIDTH;
	private static final int APANEL_HEIGHT = GUI.FINAL_WINDOW_HEIGHT;

	private static final int TRANSPARENT_PADDING = (int) (50 * GUI.DISPLAY_DENSITY_SCALE);

	/** Rounded rect panel */

	private static final int ROUNDED_RECT_STROKE_WIDTH = (int) (1 * GUI.DISPLAY_DENSITY_SCALE);
	private static final float ROUNDED_RECT_ROUND_RADIO = 15f * GUI.DISPLAY_DENSITY_SCALE;

	private static final int RPANEL_PADDING = (int) (10 * GUI.DISPLAY_DENSITY_SCALE);
	


	private RectF r;
	private Paint p;
	private Paint paintBorder;

	/** Grid panel */

	private GridPanel gridPanel ; 
	
	int gridPanelHeight = GridPanel.VERTICAL_ICON_SPACE;


	/** ACTIONS PANEL ANIMATION COORDINATES & VARIABLES */

	/** APANEL **/

	int aPanelBottom;
	
	/** ELEMENT MODEL */
	
	private FunctionalElement functionalElement = null;
	
	/** BUTTONS **/
	private ActionButtons buttons;
	
	private Bitmap closeButton;
	
	private final static String closeButtonPath = Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/close1.png";
	
	private final static int closeButtonWidth = (int) (10 * GUI.DISPLAY_DENSITY_SCALE);
	
	private static final int CLOSE_X = TRANSPARENT_PADDING;
	private static int CLOSE_Y ;
	private static final int CLOSE_WIDTH = closeButtonWidth*2;
	private static final int CLOSE_BOUNDS_OFFSET = (int) (5 * GUI.DISPLAY_DENSITY_SCALE);
	
	private Rect closeBounds ;
	
    /*
     * Default action buttons, so they don't have to be generated each time
     */
    private ActionButton handButton;

    private ActionButton mouthButton;

    private ActionButton eyeButton;

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
	//	int roundRectHeight = APANEL_HEIGHT - 2 * TRANSPARENT_PADDING;
		
		int roundRectHeight = 2 * gridPanelHeight;

		r = new RectF(0, 0, roundRectWidth, roundRectHeight);

		Rect gridPanelBounds = new Rect(TRANSPARENT_PADDING + RPANEL_PADDING,
				APANEL_HEIGHT - TRANSPARENT_PADDING - RPANEL_PADDING - gridPanelHeight, APANEL_WIDTH - TRANSPARENT_PADDING -RPANEL_PADDING,
				APANEL_HEIGHT - TRANSPARENT_PADDING-RPANEL_PADDING);

		gridPanel = new GridPanel(gridPanelBounds);
		
		buttons = new ActionButtons();

        handButton = new ActionButton( ActionButton.HAND_BUTTON );
        mouthButton = new ActionButton( ActionButton.MOUTH_BUTTON );
        eyeButton = new ActionButton( ActionButton.EYE_BUTTON );
        
		gridPanel.setDataSet(buttons);
		
		textP = new Paint(Paint.ANTI_ALIAS_FLAG);
		textP .setColor(0xFFFFFFFF);
		textP.setTextSize(25 * GUI.DISPLAY_DENSITY_SCALE);
		textP.setTypeface(Typeface.SANS_SERIF);
		textP.setTextAlign(Align.LEFT);
		
		closeP = new Paint(Paint.ANTI_ALIAS_FLAG);
		closeP.setColor(Color.RED);
		closeP.setStyle(Style.FILL_AND_STROKE);

		CLOSE_Y =  APANEL_HEIGHT - TRANSPARENT_PADDING - RPANEL_PADDING - (int)r.height();
		
		Bitmap auxCloseButton = MultimediaManager.getInstance( ).loadImage(closeButtonPath , MultimediaManager.IMAGE_MENU );
		
    	closeButton = Bitmap.createScaledBitmap(auxCloseButton , closeButtonWidth *2  ,  closeButtonWidth *2  , false);

    	closeBounds = new Rect(CLOSE_X - CLOSE_BOUNDS_OFFSET,CLOSE_Y - CLOSE_BOUNDS_OFFSET ,CLOSE_X + CLOSE_WIDTH + CLOSE_BOUNDS_OFFSET , CLOSE_Y + CLOSE_WIDTH + CLOSE_BOUNDS_OFFSET);

	}
	
	public void setElementInfo(FunctionalElement fe) {
	
		functionalElement = fe;
		
        buttons.clear( );
        
        if( functionalElement instanceof FunctionalItem ) {
            FunctionalItem item = (FunctionalItem) functionalElement;
            addDefaultObjectButtons( item );
            addCustomActionButtons( ( (FunctionalItem) functionalElement ).getItem( ).getActions( ), functionalElement );
        }
        if( functionalElement instanceof FunctionalNPC ) {
            addDefaultCharacterButtons( ( (FunctionalNPC) functionalElement ) );
            addCustomActionButtons( ( (FunctionalNPC) functionalElement ).getNPC( ).getActions( ), functionalElement );
        }

		doPanelPicture();
        
	}
	
    /** Method that adds the necessary custom action buttons to the list of
    * buttons.
    * 
    * @param actions
    *            the actions of the element
    * @param functionalElement
    *            the functional element with the actions
    */
   private void addCustomActionButtons( List<Action> actions, FunctionalElement functionalElement ) {

       List<CustomAction> added = new ArrayList<CustomAction>( );
       boolean drag_to = false;
       for( Action action : actions ) {
//           if( buttons.size( ) >= 8 )
//               return;
           if( action.getType( ) == Action.DRAG_TO && !drag_to) {
               buttons.add( new ActionButton(ActionButton.DRAG_BUTTON) );
               drag_to = true;
           }
           if( action.getType( ) == Action.CUSTOM ) {
               boolean add = functionalElement.getFirstValidCustomAction( ( (CustomAction) action ).getName( ) ) != null;
               for( CustomAction customAction : added ) {
                   if( customAction.getName( ).equals( ( (CustomAction) action ).getName( ) ) )
                       add = false;
               }
               if( add ) {
                   buttons.add( new ActionButton( (CustomAction) action ) );
                   added.add( (CustomAction) action );
               }
           }
           else if( action.getType( ) == Action.CUSTOM_INTERACT /*&& functionalElement.isInInventory( )*/ ) {
               boolean add = functionalElement.getFirstValidCustomInteraction( ( (CustomAction) action ).getName( ) ) != null;
               for( CustomAction customAction : added ) {
                   if( customAction.getName( ).equals( ( (CustomAction) action ).getName( ) ) )
                       add = false;
               }
               if( add ) {
                   buttons.add( new ActionButton( (CustomAction) action ) );
                   added.add( (CustomAction) action );
               }
           }
       }
   }
	
	
	  /**
     * Adds the default buttons for a character element
     * 
     * @param functionalNPC
     */
    private void addDefaultCharacterButtons( FunctionalNPC functionalNPC ) {

        buttons.add( eyeButton );
        buttons.add( mouthButton );
        boolean use = functionalNPC.getFirstValidAction( Action.USE ) != null;
        if( use ) {
            handButton.setName( TC.get( "ActionButton.Use" ) );
            buttons.add( handButton );
        }
    }

    /**
     * Adds the default buttons for non-character elements
     */
    private void addDefaultObjectButtons( FunctionalItem item ) {

        buttons.add( eyeButton );

        boolean addHandButton = false;
        
        if( !item.isInInventory( ) ) {
            handButton.setName( TC.get( "ActionButton.Grab" ) );
            if (item.getFirstValidAction( Action.GRAB ) != null)
                addHandButton = true;
            if( item.getFirstValidAction( Action.USE ) != null ) {
                handButton.setName( TC.get( "ActionButton.Use" ) );
                addHandButton = true;
            }
        }
        else {
            boolean useAlone = item.canBeUsedAlone( );
            boolean giveTo = item.getFirstValidAction( Action.GIVE_TO ) != null;
            boolean useWith = item.getFirstValidAction( Action.USE_WITH ) != null;
            addHandButton = useAlone || giveTo || useWith; 
            if( useAlone && !giveTo && !useWith ) {
                handButton.setName( TC.get( "ActionButton.Use" ) );
            }
            else if( !useAlone && giveTo && !useWith ) {
                handButton.setName( TC.get( "ActionButton.GiveTo" ) );
            }
            else if( !useAlone && !giveTo && useWith ) {
                handButton.setName( TC.get( "ActionButton.UseWith" ) );
            }
            else if( !useAlone && giveTo && useWith ) {
                handButton.setName( TC.get( "ActionButton.UseGive" ) );
            }
            else {
                handButton.setName( TC.get( "ActionButton.Use" ) );
            }
        }
        if (addHandButton)
            buttons.add( handButton );
    }
	


	private void doPanelPicture() {
		
		actionsPicture = new Picture();

		Canvas c = actionsPicture.beginRecording(APANEL_WIDTH, APANEL_HEIGHT);

		c.drawARGB(150, 0, 0, 0);
		c.translate(TRANSPARENT_PADDING, APANEL_HEIGHT - TRANSPARENT_PADDING - RPANEL_PADDING - r.height());
		c.drawRoundRect(r, ROUNDED_RECT_ROUND_RADIO, ROUNDED_RECT_ROUND_RADIO,
				p);
		c.drawRoundRect(r, ROUNDED_RECT_ROUND_RADIO, ROUNDED_RECT_ROUND_RADIO,
				paintBorder);
		c.translate(-closeButtonWidth/2,-closeButtonWidth/2);
		c.drawBitmap(closeButton, 0, 0, null);
		c.translate(closeButtonWidth/2, closeButtonWidth/2);
	//	c.drawCircle(0, 0, 15f * GUI.DISPLAY_DENSITY_SCALE, closeP);
		c.translate(10 * GUI.DISPLAY_DENSITY_SCALE, 20 * GUI.DISPLAY_DENSITY_SCALE);
		
		if( functionalElement instanceof FunctionalItem ) 

			c.drawBitmap(((FunctionalItem)functionalElement).getIconImage(), 0, 0, null);
		
	    c.translate(80 * GUI.DISPLAY_DENSITY_SCALE,20 * GUI.DISPLAY_DENSITY_SCALE);
		 
	    c.drawText(functionalElement.getElement().getName(), 0, 0, textP);
	     
		actionsPicture.endRecording();
		
	}
	
	public void doDraw(Canvas c) {

		c.clipRect(0, 0, APANEL_WIDTH, aPanelBottom);
		c.save();
		c.translate(0, aPanelBottom - APANEL_HEIGHT);
		actionsPicture.draw(c);
		c.restore();
		c.translate(0, aPanelBottom - APANEL_HEIGHT);
		c.translate(TRANSPARENT_PADDING + RPANEL_PADDING, APANEL_HEIGHT - TRANSPARENT_PADDING
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

	public Object selectItemFromGrid(int x, int y) {

		return gridPanel.selectItem(x, y);

	}

	public FunctionalElement getElementInfo() {
		return functionalElement;
	}

	public boolean isInCloseButton(int srcX, int srcY) {

		return closeBounds.contains(srcX, srcY);
	}
	
}
