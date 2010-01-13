package es.eucm.eadandroid.ecore.gui;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import es.eucm.eadandroid.common.data.animation.Transition;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalhighlights.FunctionalHighlight;



public class GUI {
	//FIXME perry
	public static int SCREEN_HEIGHT = 600;
	public static int SCREEN_WIDTH = 800;
	/**
     * Width of the window
     */
    public static final int WINDOW_WIDTH = 800;

    /**
     * Height of the window
     */
    public static final int WINDOW_HEIGHT = 600;
    /**
     * Max width of the text spoken in the game
     */
    public static final int MAX_WIDTH_IN_TEXT = 300;
    
    /**
     * Antialiasing of the game shapes
     */
    public boolean ANTIALIASING = true;

    /**
     * Antialiasing of the game text
     */
    public boolean ANTIALIASING_TEXT = true;

    /**
     * The frame/window of the game
     */
    protected Canvas gameFrame;

    /**
     * The HUE element
     */
   //OLD protected HUD hud;


    /**
     * Graphic configuration value specifies if the display is
     * fullscreen, window, etc.
     * 
     */    
    //OLD protected static int graphicConfig;

    /**
     * The GUI singleton class
     */
    protected static GUI instance = null;

    /**
     * The default cursor
     * No cursor in the android port
     */
    //OLD protected Cursor defaultCursor;

    /**
     * Background class that store the image of the background and a screen
     * offset
     */
    protected class SceneImage {

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
        public SceneImage( Bitmap background, int offsetX ) {

            this.background = background;
            this.offsetX = offsetX;
        }

        /**
         * Draw the background with the offset
         * 
         * @param c
         *            canvas to draw the background
         */
        public void draw( Canvas c ) {
        	
            //OLD g.drawImage( background, 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, offsetX, 0, offsetX + GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, gameFrame );
        	/**
        	 * Attention:
        	 * the order of the parameters source and destination rectangle differs
        	 * within the methods awt.Graphics2D.drawImage and android.graphics.Canvas.drawBitmap
        	 *
        	 */
        	
        	c.drawBitmap(background,new Rect(offsetX, 0, offsetX + GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT) ,new Rect(0,0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT), null);
        }
    }
    
    /**
     * Store the array string, its color and border and it's position to be draw
     * onto.
     */
    protected class Text {

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
        private Color textColor;

        /**
         * Color of the borde of the text
         */
        private Color borderColor;

        private Color bubbleBkgColor;

        private Color bubbleBorderColor;
        
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
        public Text( String[] text, int x, int y, Color textColor, Color borderColor ) {

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
        public Text( String[] text, int x, int y, Color textColor, Color borderColor, Color bubbleBkgColor, Color bubbleBorderColor, boolean showArrow ) {

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
        public void draw( Graphics2D g ) {

            if( showBubble )
                GUI.drawStringOnto( g, text, x, y, textColor, borderColor, bubbleBkgColor, bubbleBorderColor, showArrow );
            else
                GUI.drawStringOnto( g, text, x, y, textColor, borderColor );
        }

        /**
         * Returns the Y coordinate
         * 
         * @return Y coordinate
         */
        public int getY( ) {

            return y;
        }
    }

    
    /**
     * Background image of the scene.
     */
    protected SceneImage background;

    /**
     * Foreground image of the scene.
     */
    protected SceneImage foreground;
    
    protected class ElementImage {

        /**
         * Image
         */
        private Bitmap image;

        /**
         * X coordinate
         */
        private int x;

        /**
         * Y coordinate
         */
        private int y;

        /**
         * Original y, without pertinent transformations to fir the original
         * image to scene reference image.
         */
        private int originalY;

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
        public ElementImage( Bitmap image, int x, int y, int depth, int originalY ) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.depth = depth;
            this.originalY = originalY;
            this.highlight = null;
        }
        
        public ElementImage( Bitmap image, int x, int y, int depth, int originalY, FunctionalHighlight highlight, FunctionalElement fe) {
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
        public void draw( Canvas c ) {
            if (highlight != null) {
                c.drawBitmap(highlight.getHighlightedImage( image ), x + highlight.getDisplacementX( ), y + highlight.getDisplacementY( ), null);
                //OLDc.drawImage( highlight.getHighlightedImage( image ), x + highlight.getDisplacementX( ), y + highlight.getDisplacementY( ), null );
            } else {
            	 c.drawBitmap( image, x, y, null );
            }
        }

        /**
         * Returns the depth of the element.
         * 
         * @return Depth of the element
         */
        public int getDepth( ) {

            return depth;
        }

        /**
         * Returns the y of the element
         * 
         * @return The y element´s position
         */
        public int getY( ) {

            return y;
        }

        /**
         * Changes the element´s depth
         * 
         * @param depth
         */
        public void setDepth( int depth ) {

            this.depth = depth;
        }

        /**
         * Returns original Y position, without transformations.
         * 
         * @return
         */
        public int getOriginalY( ) {

            return originalY;
        }

        
        /**
         * @return the functionalElement
         */
        public FunctionalElement getFunctionalElement( ) {
        
            return functionalElement;
        }
    }

 

    	  

    /**
     * List of elements to be painted.
     */
    protected ArrayList<ElementImage> elementsToDraw;

    /**
     * List of texts to be painted.
     */
    protected ArrayList<Text> textToDraw;
    
    /**
     * Stores the static component for show (report,book,video, etc)
     */
    protected Component component;

    private Transition transition = null;

    private boolean showsOffsetArrows = false;

    private boolean moveOffsetRight = false;

    private boolean moveOffsetLeft = false;
    
    /**
     * This attribute store the interactive elements (which can be painted) in the same order
     * that they will be painted.
     */
    private List<FunctionalElement> elementsToInteract = null;
    
    private Image loadingImage;
    
    private int loading;
    
    private Timer loadingTimer;
    
    private TimerTask loadingTask;
}


