package es.eucm.eadandroid.ecore.gui;


import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;



public class GUI {
	
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
   //TODO protected HUD hud;


    /**
     * Graphic configuration value specifies if the display is
     * fullscreen, window, etc.
     * 
     */    
    //protected static int graphicConfig;

    /**
     * The GUI singleton class
     */
    protected static GUI instance = null;

    /**
     * The default cursor
     * No cursor in the android port
     */
    //protected Cursor defaultCursor;

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
        	
            //g.drawImage( background, 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, offsetX, 0, offsetX + GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, gameFrame );
        	/**
        	 * Attention
        	 * the order of the source and destination rectangle differs
        	 * in the methods awt.Graphics2D.drawImage and android.graphics.Canvas.drawBitmap
        	 *
        	 */
        	c.drawBitmap(background,new Rect(offsetX, 0, offsetX + GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT) ,new Rect(0,0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT), null);
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


