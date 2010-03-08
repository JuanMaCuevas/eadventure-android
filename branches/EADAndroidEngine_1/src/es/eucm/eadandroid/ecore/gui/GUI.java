package es.eucm.eadandroid.ecore.gui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import es.eucm.eadandroid.ecore.control.TimerManager;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalhighlights.FunctionalHighlight;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.hud.HUD;


public class GUI {

	private static final double MAX_WIDTH_IN_TEXT = 300;
	
	/**
     * Number of response lines to display
     */
    private static final int RESPONSE_TEXT_NUMBER_LINES = 9;
    
	private Transition transition;

	protected static int graphicConfig;

	/**
	 * GUI INSTANCE
	 */

	private static GUI instance;

	/**
	 * Canvas dimension
	 */
	public static int WINDOW_HEIGHT = 600;
	public static int WINDOW_WIDTH = 800;

	public static int FINAL_WINDOW_HEIGHT;
	public static int FINAL_WINDOW_WIDTH;

	public static float DISPLAY_DENSITY_SCALE;
	
	public static int CENTER_OFFSET;

	/**
	 * HUD
	 */

	private HUD hud;

	/**
	 * TEXT PAINT
	 */

	private static Paint mPaint;

	/**
	 * scaleRatio
	 */
	public static float SCALE_RATIO;

	/**
	 * scaleMatrix
	 */
	public static Matrix scaleMatrix;

	/** Handle to the surface manager object we interact with */
	private SurfaceHolder canvasSurfaceHolder;
	private SurfaceHolder videoSurfaceHolder;
	
	/** Context of the activity creating the thread */
	private Context mContext;

	/**
	 * BITMAP COPY & ITS CANVAS (VIEW)
	 */

	private static Bitmap bitmapcpy;
	private static Canvas canvascpy;

	/**
	 * finalbmp bitmap scaled to the device screen size
	 */
	private static Bitmap finalBmp;
	private static Canvas finalCanvas;

	/**
	 * List of elements to be painted.
	 */
	protected ArrayList<ElementImage> elementsToDraw;

	/**
	 * This attribute store the interactive elements (which can be painted) in
	 * the same order that they will be painted.
	 */
	private List<FunctionalElement> elementsToInteract = null;

	/**
	 * Background image of the scene.
	 */
	protected SceneImage background;

	/**
	 * Foreground image of the scene.
	 */
	protected SceneImage foreground;

	/**
	 * List of texts to be painted.
	 */
	protected ArrayList<Text> textToDraw;

	FPS fps = new FPS();

	private boolean showsOffsetArrows;

	private boolean moveOffsetRight;

	private boolean moveOffsetLeft;

	/**
	 * The GraphicsConfiguration class
	 */
	private GraphicsConfiguration graphicsConf = new GraphicsConfiguration();;

	private int loading;


	public FPS getfps() {
		return fps;
	}

	private GUI(SurfaceHolder mSurfaceHolder, SurfaceHolder videoHolder) {
		this.canvasSurfaceHolder = mSurfaceHolder;
		this.videoSurfaceHolder = videoHolder;
		elementsToDraw = new ArrayList<ElementImage>();
		textToDraw = new ArrayList<Text>();
	}

	public static void create(SurfaceHolder mSurfaceHolder, SurfaceHolder videoHolder) {

		instance = new GUI(mSurfaceHolder,videoHolder);

	}

	public void init(int landscapeHeight, int landscapeWidth, float scaleDensity) {

		FINAL_WINDOW_HEIGHT = landscapeHeight;
		FINAL_WINDOW_WIDTH = landscapeWidth;
		
		DISPLAY_DENSITY_SCALE = scaleDensity;

		bitmapcpy = Bitmap.createBitmap(WINDOW_WIDTH, WINDOW_HEIGHT,
				Bitmap.Config.RGB_565);
		canvascpy = new Canvas(bitmapcpy);

		finalBmp = Bitmap.createBitmap(FINAL_WINDOW_WIDTH, FINAL_WINDOW_HEIGHT,
				Bitmap.Config.RGB_565);
		finalCanvas = new Canvas(finalBmp);

		// Set the scale
		SCALE_RATIO = (float) FINAL_WINDOW_HEIGHT / (float) WINDOW_HEIGHT;
		scaleMatrix = new Matrix();
		scaleMatrix.setScale(SCALE_RATIO, SCALE_RATIO);
		
		if ((WINDOW_WIDTH * SCALE_RATIO) < FINAL_WINDOW_WIDTH)
			CENTER_OFFSET = (FINAL_WINDOW_WIDTH - (int)(WINDOW_WIDTH * SCALE_RATIO))/2;
		else CENTER_OFFSET = 0;

		mPaint = new Paint();
		mPaint.setTextSize(25);
		mPaint.setTypeface(Typeface
				.create(Typeface.SANS_SERIF, Typeface.NORMAL));
		mPaint.setStrokeWidth(4);
		mPaint.setColor(0XFFFFFFFF);

	}
	
	public void initHUD() {
		hud = new HUD();
	}

	public static GUI getInstance() {

		return instance;
	}

	public Canvas getGraphics() {
		return canvascpy;
	}
	public SurfaceHolder getVideoSurfaceHolder() {
		return videoSurfaceHolder;
	}

	public void endDraw() {

		// reescale the drawn bitmap to fit the sreen size
		finalCanvas.drawColor(Color.BLACK);
		finalCanvas.translate(CENTER_OFFSET, 0);
		finalCanvas.drawBitmap(bitmapcpy, scaleMatrix, null);
		finalCanvas.translate(-CENTER_OFFSET, 0);
		
		
		Canvas canvas = null;
		
		try {
			canvas = canvasSurfaceHolder.lockCanvas(null);
			synchronized (canvasSurfaceHolder) {
				
				canvas.drawBitmap(finalBmp, 0, 0, null);

				fps.draw(canvas);
				hud.doDraw(canvas);
			}
		} finally {
			// do this in a finally so that if an exception is thrown
			// during the above, we don't leave the Surface in an
			// inconsistent state
			if (canvas != null) {
				canvasSurfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
		
		

	}

	private void recalculateInteractiveElementsOrder() {
		elementsToInteract = new ArrayList<FunctionalElement>();
		for (ElementImage ei : elementsToDraw) {
			if (ei.getFunctionalElement() != null)
				elementsToInteract.add(ei.getFunctionalElement());
		}
	}

	/**
	 * Adds the image to the array image buffer sorted by its Y coordinate, or
	 * its layer, depending on the element has layer or not.
	 * 
	 * @param image
	 *            Image
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param depth
	 *            Depth of the image
	 */
	public void addElementToDraw(Bitmap image, int x, int y, int depth,
			int originalY, FunctionalHighlight highlight, FunctionalElement fe) {

		boolean added = false;
		int i = 0;

		// Create the image to store it
		ElementImage element = new ElementImage(image, x, y, depth, originalY,
				highlight, fe);

		// While the element has not been added, and
		// we haven't checked every previous element
		while (!added && i < elementsToDraw.size()) {

			// Insert the element in the correct position
			if (depth <= elementsToDraw.get(i).getDepth()) {
				elementsToDraw.add(i, element);
				added = true;
			}
			i++;
		}

		// If the element wasn't added, add it in the last position
		if (!added)
			elementsToDraw.add(element);
	}

	/**
	 * Adds the image to the array image buffer sorted by its Y coordinate, or
	 * its layer, depending on the element has layer or not. This method compare
	 * the depth parameter with all elements in elementsToDraw y position. This
	 * method is use to insert player without layer in a scene where the rest
	 * has layer or not.
	 * 
	 * @param image
	 *            Image
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param depth
	 *            Depth of the image
	 */
	public void addPlayerToDraw(Bitmap image, int x, int y, int depth,
			int originalY) {

		boolean added = false;
		int i = 0;

		// Create the image to store it
		ElementImage element = new ElementImage(image, x, y, depth, originalY);
		// prepareVirtualElemetsToDraw();
		// While the element has not been added, and
		// we haven't checked every previous element
		while (!added && i < elementsToDraw.size()) {

			// Insert the element in the correct position
			// TODO: Hey guys, watch this carefully!! I've changed this line
			if (depth <= elementsToDraw.get(i).getOriginalY()) {
				// if (depth <= elementsToDraw.get(i).getDepth()) { //TODO esto
				// lo entiendo pero me imagino que el euge lo pilota
				// if( depth <= getRealMinY(elementsToDraw.get( i ),
				// x)+elementsToDraw.get( i ).y) {
				element.setDepth(i);
				elementsToDraw.add(i, element);
				added = true;
			}
			i++;
		}

		// If the element wasn't added, add it in the last position
		if (!added) {
			// element.setDepth(elementsToDraw.size() - 1);
			elementsToDraw.add(element);

		}
	}

	/**
	 * Set the background image
	 * 
	 * @param background
	 *            Background image
	 * @param offsetX
	 *            Offset of the background
	 */
	public void addBackgroundToDraw(Bitmap background, int offsetX) {

		this.background = new SceneImage(background, offsetX);
	}

	/**
	 * Set the background image
	 * 
	 * @param background
	 *            Background image
	 * @param offsetX
	 *            Offset of the background
	 */
	public void addForegroundToDraw(Bitmap background, int offsetX) {

		this.foreground = new SceneImage(background, offsetX);
	}

	/**
	 * Adds the string to the array text buffer sorted by its Y coordinate
	 * 
	 * @param string
	 *            Array string
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param textColor
	 *            Color of the string
	 * @param borderColor
	 *            Color if the border of the string
	 */
	public void addTextToDraw(String[] string, int x, int y, int textColor,
			int borderColor) {
		boolean added = false;
		int i = 0;
		Text text = new Text(string, x, y, textColor, borderColor);
		while (!added && i < textToDraw.size()) {
			if (y <= textToDraw.get(i).getY()) {
				textToDraw.add(i, text);
				added = true;
			}
			i++;
		}
		if (!added)
			textToDraw.add(text);
	}

	/**
	 * Adds the string to the array text buffer sorted by its Y coordinate
	 * 
	 * @param string
	 *            Array string
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param textColor
	 *            Color of the string
	 * @param borderColor
	 *            Color if the border of the string
	 * @param bubbleBkgColor
	 *            Color of the bubbles background
	 * @param bubbleBorderColor
	 *            Color of the bubbles border
	 */
	public void addTextToDraw(String[] string, int x, int y, int textColor,
			int borderColor, int bubbleBkgColor, int bubbleBorderColor) {

		boolean added = false;
		int i = 0;
		Text text = new Text(string, x, y, textColor, borderColor,
				bubbleBkgColor, bubbleBorderColor);
		while (!added && i < textToDraw.size()) {
			if (y <= textToDraw.get(i).getY()) {
				textToDraw.add(i, text);
				added = true;
			}
			i++;
		}
		if (!added)
			textToDraw.add(text);
	}

	// /////////////////////////////////////////////TODO lo tendre que cambiar

	/**
	 * Draw the text array with a border, given the lower-left coordinate if
	 * centeredX is false or the lower-middle if it is true
	 * 
	 * @param g
	 *            Graphics2D where make the painting
	 * @param string
	 *            String to be drawn
	 * @param x
	 *            Int coordinate of the left (or middle) of the string to be
	 *            drawn
	 * @param y
	 *            Int coordinate of the bottom of the string to be drawn
	 * @param centeredX
	 *            boolean if the x is middle
	 * @param textColor
	 *            Color of the text
	 * @param borderColor
	 *            Color of the border of the text
	 * @param border
	 *            whether to paint a border on the text
	 */
	public static void drawStringOnto(Canvas g, String string, int x, int y,
			boolean centeredX, int textColor, int borderColor, boolean border) {

		// Get the current text font metrics (width and hegiht)

		double width = mPaint.measureText(string);
		double height = mPaint.ascent();
		int realX = x;
		int realY = y;

		// If the text is centered in its X coordinate
		if (centeredX) {
			// Check if the text don't go out of the window horizontally
			// and if it do correct it so it's in the window
			if (realX + width / 2 > WINDOW_WIDTH) {
				realX = (int) (WINDOW_WIDTH - width / 2);
			} else if (realX - width / 2 < 0) {
				realX = (int) (width / 2);
			}
			realX -= width / 2;
			// Check if the text don't go out of the window vertically
			// and if it do correct it so it's in the window
			if (realY > WINDOW_HEIGHT) {
				realY = WINDOW_HEIGHT;
			} else if (realY - height < 0) {
				realY = (int) height;
			}
			// if it's not centered
		} else {
			// Check if the text don't go out of the window horizontally
			// and if it do correct it so it's in the window
			// FIXME nuevo, a ver si funciona

			if (realX + width > WINDOW_WIDTH) {
				realX = (int) (WINDOW_WIDTH - width);
			} else if (realX < 0) {
				realX = 0;
			}

			if (realX + width > WINDOW_WIDTH) {
				realX = 0;
				// To know the width of one character
				double w = mPaint.measureText(new String("A"));
				int position = (int) (WINDOW_WIDTH / w) + 18;
				string = string.substring(0, position);
				string = string + "...";
			}
			// Check if the text don't go out of the window vertically
			// and if it do correct it so it's in the window
			if (realY > WINDOW_HEIGHT) {
				realY = WINDOW_HEIGHT;
			} else if (realY - height < 0) {
				realY = (int) height;
			}
		}
		// If the text has border, draw it
		mPaint.setColor(textColor);
		if (border) {

			// FIXME no lo entiendo intenta darle efecto 3d al texto, si lo hago
			// es q directamente me va a 1 el texto
			// g.setColor(borderColor);

			// mPaint.setColor(borderColor);
			// g.drawText(string, realX - 1, realY - 1,mPaint);
			// g.drawText(string, realX - 1, realY + 1,mPaint);
			// g.drawText(string, realX + 1, realY - 1,mPaint);
			// g.drawText(string, realX + 1, realY + 1,mPaint);

			// g.setColor(textColor);

			// FIXME el color CORRECTO
			// mPaint.setColor(Color.RED);
			// g.drawRoundRect(rect, rx, ry, paint)
		}
		// Draw the text
		mPaint.setAntiAlias(true);
		g.drawText(string, realX,realY, mPaint);
		// g.drawText(string, realX, realY,mPaint);
	}

	/**
	 * Draw the text array with a border, given the lower-middle position of the
	 * text
	 * 
	 * @param g
	 *            Graphics2D where to draw
	 * @param strings
	 *            String[] of Strings to be drawn
	 * @param x
	 *            Int coordinate of the middle of the string to be drawn
	 * @param y
	 *            Int coordinate of the bottom of the string to be drawn
	 * @param textColor
	 *            Color of the text
	 * @param borderColor
	 *            Color of the border of the text
	 */
	public static void drawStringOnto(Canvas g, String[] strings, int x, int y,
			int textColor, int borderColor) {

		// Calculate the total height of the block text

		int textBlockHeight = (int) (mPaint.getTextSize() * strings.length - mPaint
				.getFontMetrics().leading);

		// This is the y lower position of the first line
		int realY = (int) (y - textBlockHeight + mPaint.ascent());
		if (realY < mPaint.ascent())
			realY = (int) mPaint.ascent();
		// realY=y;

		// Draw each line of the string array
		for (String line : strings) {
			drawStringOnto(g, line, x, realY, true, textColor, borderColor,
					true);
			realY = (int) (realY + mPaint.getTextSize());
		}
	}

	/**
	 * Draw the text array with a border, given the lower-middle position of the
	 * text with a speech bubble of the given colors as background
	 * 
	 * @param g
	 *            Graphics2D where to draw
	 * @param strings
	 *            String[] of the strings to be drawn
	 * @param x
	 *            int coordinate of the middle of the string to be drawn
	 * @param y
	 *            int coordinate of the bottom of the string to be drawn
	 * @param textColor
	 *            Color of the text
	 * @param borderColor
	 *            Color of the border of the text
	 * @param bkgColor
	 *            Color of the background of the bubble
	 * @param bubbleBorder
	 *            Color of the border of the bubble
	 */
	public static void drawStringOnto(Canvas g, String[] strings, int x, int y,
			int textColor, int borderColor, int bkgColor, int bubbleBorder,
			boolean showArrow) {

		int textBlockHeight = (int) (mPaint.getTextSize() * strings.length + mPaint
				.getFontMetrics().leading);

		int maxWidth = 25;
		for (String line : strings)
			maxWidth = (mPaint.measureText(line) > maxWidth ? (int) mPaint
					.measureText(line) : maxWidth);

		int tempX = x;
		int tempY = y;
		if (tempX - maxWidth / 2 < 0)
			tempX = maxWidth / 2;
		if (tempY - textBlockHeight < 0)
			tempY = textBlockHeight;
		if (tempX + maxWidth / 2 > WINDOW_WIDTH)
			tempX = WINDOW_WIDTH - maxWidth / 2;

		Paint paint = new Paint();
		paint.setFilterBitmap(false);
		paint.setStyle(Paint.Style.STROKE);
		paint.setShader(null);
		paint.setColor(borderColor);
		RectF rectanguloG = new RectF();
		rectanguloG.set((tempX - maxWidth / 2 - 5) - 0.5f, (tempY
				- textBlockHeight - 5) - 0.5f, ((tempX - maxWidth / 2 - 5)
				+ maxWidth + 10) + 0.5f, ((tempY - textBlockHeight - 5)
				+ textBlockHeight + 10) + 0.5f);
		g.drawRoundRect(rectanguloG, 20, 20, paint);

		RectF rectangulo = new RectF();
		rectangulo.set(tempX - maxWidth / 2 - 5, tempY - textBlockHeight - 5,
				(tempX - maxWidth / 2 - 5) + maxWidth + 10, (tempY
						- textBlockHeight - 5)
						+ textBlockHeight + 10);

		Paint propiedades = new Paint();
		propiedades.setColor(bkgColor);
		g.drawRoundRect(rectangulo, 20, 20, propiedades);

		/*
		 * AlphaComposite alphaComposite = AlphaComposite.getInstance(
		 * AlphaComposite.SRC_OVER, 0.8f); Composite temp = g.getComposite();
		 * g.setComposite(alphaComposite); g.setColor(bkgColor);
		 * g.fillRoundRect(tempX - maxWidth / 2 - 5, tempY - textBlockHeight -
		 * 5, maxWidth + 10, textBlockHeight + 10, 20, 20);
		 * 
		 * g.setComposite(temp); g.setColor(bubbleBorder); g.drawRoundRect(tempX
		 * - maxWidth / 2 - 5, tempY - textBlockHeight - 5, maxWidth + 10,
		 * textBlockHeight + 10, 20, 20);
		 * 
		 * if (showArrow) { g.setComposite(alphaComposite);
		 * g.setColor(bkgColor); int x_p[] = new int[] { tempX - 10, tempX + 10,
		 * tempX }; int y_p[] = new int[] { tempY + 5, tempY + 5, tempY + 15 };
		 * g.fillPolygon(x_p, y_p, 3);
		 * 
		 * g.setComposite(temp); g.setColor(bubbleBorder); g.drawLine(x_p[0],
		 * y_p[0], x_p[2], y_p[2]); g.drawLine(x_p[1], y_p[1], x_p[2], y_p[2]);
		 * }
		 */
		drawStringOnto(g, strings, x, y, textColor, borderColor);
	}

	/**
	 * Destroy the singleton instance
	 */
	public static void delete() {
		// TODO habra que eliminar todo
		GUI.instance = null;

	};

	/**
	 * Draws the string specified centered (in X and Y) in the given position
	 * 
	 * @param g
	 *            Graphics2D where make the painting
	 * @param string
	 *            String to be drawed
	 * @param x
	 *            Center X position of the text
	 * @param y
	 *            Center Y position of the text
	 */

	public static void drawString(Canvas g, String string, int x, int y) {

		// Get the current text font metrics (width and hegiht)

		double width = mPaint.measureText(string);
		;
		double height = mPaint.ascent();

		int realX = x;
		int realY = y;

		// Check if the text don't go out of the window horizontally
		// and if it do correct it so it's in the window
		if (realX + width / 2 > WINDOW_WIDTH) {
			realX = (int) (WINDOW_WIDTH - width / 2);
		} else if (realX - width / 2 < 0) {
			realX = (int) (width / 2);
		}
		realX -= width / 2;

		// Check if the text don't go out of the window vertically
		// and if it do correct it so it's in the window
		if (realY + height / 2 > WINDOW_HEIGHT) {
			realY = (int) (WINDOW_HEIGHT - height / 2);
		} else if (realY < 0) {
			realY = 0;
		}
		realY += height / 2;

		// Draw the string

		g.drawText(string, realX, realY, mPaint);
	}

	/**
	 * Draws the given block text centered in the given position
	 * 
	 * @param g
	 *            Graphics2D where make the painting
	 * @param strings
	 *            Array of strings to be painted
	 * @param x
	 *            Centered X position of the text block
	 * @param y
	 *            Centered Y position of the text block
	 */
	public static void drawString(Canvas g, String[] strings, int x, int y) {

		// Calculate the total height of the block text

		int textBlockHeight = (int) (mPaint.getTextSize() * strings.length
				- mPaint.getFontMetrics().leading - mPaint.descent());

		// This is the y center position of the first line
		int realY = (int) (y - textBlockHeight / 2 + mPaint.ascent() / 2);

		// Draw each line of the string array
		for (String line : strings) {
			drawString(g, line, x, realY);
			realY =(int) (realY + mPaint.getTextSize());
		}
	}

	/**
	 * Split a text in various lines using the font width and an max width for
	 * each line
	 * 
	 * @param text
	 *            String that contains all the text to be used
	 * @return String[] with the various lines splited from the text
	 */
	public String[] splitText(String text) {

		ArrayList<String> lines = new ArrayList<String>();
		String currentLine = text;
		boolean exit = false;
		String line;

		int width;

		do {
			width = (int) mPaint.measureText(currentLine);

			if (width > MAX_WIDTH_IN_TEXT) {
				int lineNumber = (int) Math.ceil((double) width
						/ (double) MAX_WIDTH_IN_TEXT);
				int index = currentLine.lastIndexOf(' ', text.length()
						/ lineNumber);

				if (index == -1) {
					index = currentLine.indexOf(' ');
				}

				if (index == -1) {
					index = currentLine.length();
					exit = true;
				}

				line = currentLine.substring(0, index);
				currentLine = currentLine.substring(index).trim();

			} else {
				line = currentLine;
				exit = true;
			}

			lines.add(line);
		} while (!exit);
		return lines.toArray(new String[1]);
	}

	public static void setGraphicConfig(int newGraphicConfig) {

		graphicConfig = newGraphicConfig;
	}

	public void clearBackground() {

		this.background = null;
	}

	/**
	 * Draws the scene buffer given a Graphics2D
	 * 
	 * @param g
	 *            Graphics2D to be used by the scene buffer
	 */
	public void drawScene(Canvas g, long elapsedTime) {

		if (transition != null && !transition.hasStarted()) {

			drawToGraphics(transition.getGraphics());
			transition.start(this.getGraphics());
		} else if (transition == null || transition.hasFinished(elapsedTime)) {
			transition = null;
			drawToGraphics(g);
		} else {
			transition.update(g);
		}
	}

	public void drawToGraphics(Canvas g) {

		if (background != null) {
			background.draw(canvascpy);
		}

		for (ElementImage ei : elementsToDraw)
			ei.draw(canvascpy);

		recalculateInteractiveElementsOrder();

		elementsToDraw.clear();
		// TODO tengo que ponerlo todo en una pila no en pilas separadas y tener
		// un depth asi pinta todo ordenado
		// xq si quiero poner un elemento encima de una mesa uqe es foreground

		if (foreground != null) {
			this.foreground.draw(canvascpy);
			foreground = null;
		}

		for (int i = 0; i < this.textToDraw.size(); i++) {
			this.textToDraw.get(i).draw(canvascpy);
		}
		this.textToDraw.clear();

		TimerManager timerManager = TimerManager.getInstance();
		if (timerManager != null) {
			timerManager.draw(canvascpy);
		}

		// TODO arrows

		/*
		 * if( showsOffsetArrows ) { g.setColor( Color.BLACK ); int ytemp =
		 * GUI.WINDOW_HEIGHT / 2; Composite old = g.getComposite( );
		 * AlphaComposite alphaComposite = AlphaComposite.getInstance(
		 * AlphaComposite.SRC_OVER, 0.6f );
		 * 
		 * if( !this.moveOffsetLeft ) g.setComposite( alphaComposite );
		 * g.fillOval( -30, ytemp - 30, 60, 60 );
		 * 
		 * if( this.moveOffsetRight ) g.setComposite( old ); else
		 * g.setComposite( alphaComposite ); g.fillOval( GUI.WINDOW_WIDTH - 30,
		 * ytemp - 30, 60, 60 );
		 * 
		 * g.setComposite( old ); g.setColor( Color.WHITE ); Stroke oldStroke =
		 * g.getStroke( ); g.setStroke( new BasicStroke( 5 ) ); g.drawLine( 5,
		 * ytemp, 15, ytemp - 15 ); g.drawLine( 5, ytemp, 15, ytemp + 15 );
		 * g.drawLine( GUI.WINDOW_WIDTH - 5, ytemp, GUI.WINDOW_WIDTH - 15, ytemp
		 * - 15 ); g.drawLine( GUI.WINDOW_WIDTH - 5, ytemp, GUI.WINDOW_WIDTH -
		 * 15, ytemp + 15 ); g.setStroke( oldStroke ); }
		 */

	}

	/**
	 * Creates a Color from the red, green and blue component
	 * 
	 * @param r
	 *            Red int
	 * @param g
	 *            Green int
	 * @param b
	 *            Blue int
	 * @return Color corresponding to R,G,B components
	 */
	public int getColor(int r, int g, int b) {

		return Color.rgb(r, g, b);

	}

	/**
	 * @return the elementsToDraw
	 */
	public ArrayList<ElementImage> getElementsToDraw() {

		return elementsToDraw;
	}

	/**
	 * @return the elementsToInteract
	 */
	public List<FunctionalElement> getElementsToInteract() {

		return elementsToInteract;
	}

	/**
	 * Gets the GraphicsConfiguration of the window
	 * 
	 * @return GraphicsConfiguration of the JFrame
	 */
	public GraphicsConfiguration getGraphicsConfiguration() {

		return graphicsConf;
	}

	/**
	 * Returns the highest Y value of the ElementImage given as argument which
	 * is not transparent in the column x. Useful to determine if the player
	 * must be painted before the element
	 * 
	 * @param element
	 * @param x
	 * @return
	 */
	public int getRealMinY(ElementImage element, int x) {

		boolean isInside = false;

		// int mousex = (int)( x - ( this.x - getWidth( ) *scale/ 2 ) );
		// int mousey = (int)( y - ( this.y - getHeight( ) *scale) );
		int width = element.image.getWidth();
		int transformedX = (x - element.x);
		int minY = element.originalY;
		if (transformedX >= 0 && transformedX < width) {
			minY = element.image.getHeight() - 1;
			try {
				Bitmap bufferedImage = element.image;
				do {
					// TODO esto puede cascar
					int alpha = bufferedImage.getPixel(transformedX, minY) >>> 24;
					minY--;
					isInside = alpha > 128;
				} while (!isInside && minY > 0);
			} catch (Exception e) {

			}
		}

		return minY;
	}

	
	
	/**
	 * Returns the number of lines of the response text block
	 * 
	 * @return Number of response lines
	 */
	
	  public int getResponseTextNumberLines( ) {
	  
	  return RESPONSE_TEXT_NUMBER_LINES; }
	 
	
	/**
	 * Returns the X point of the response block text
	 * 
	 * @return X point of the response block text
	 */
	/*
	 * public int getResponseTextX( ) {
	 * 
	 * return hud.getResponseTextX( ); }
	 *//**
	 * Returns the Y point of the response block text
	 * 
	 * @return Y point of the response block text
	 */
	/*
	 * public int getResponseTextY( ) {
	 * 
	 * return hud.getResponseTextY( ); }
	 */
	public boolean hasTransition() {

		return transition != null && !transition.hasFinished(0);
	}

	public void loading(int percent) {
		// Timer loadingTimer = null;
		// if (percent == 0) {
		//            
		// // FIXME Chapucilla que huele a pipi
		// //this.loadingImage = new ImageIcon("gui/loading.jpg").getImage();
		// // this.loadingImage = MultimediaManager.getInstance( ).loadImage(
		// "gui/loading.jpg", MultimediaManager.IMAGE_MENU );
		// // if (this.loadingImage == null ){
		// // this.loadingImage = MultimediaManager.getInstance(
		// ).loadImageFromZip( "gui/loading.jpg", MultimediaManager.IMAGE_MENU
		// );
		// // }
		// this.loading = percent;
		// loadingTimer = new Timer();
		// TimerTask loadingTask = new TimerTask() {
		// private int cont = 20;
		// private boolean contracting = false;
		//                
		//               
		// public void run( ) {
		// // Graphics2D g = GUI.this.getGraphics( );
		// // g.drawImage( loadingImage, 0, 0, null);
		//                	
		// //TODO haces esto bien q no tengo ni warra
		// GUI.canvascpy.drawText("0", 50, 60,mPaint);
		// // g.setColor( new Color(250, 173, 6) );
		// // g.fillRoundRect( 200, 300, loading * 4, 50, 10, 10 );
		// // g.setColor( Color.BLUE );
		// // g.fillArc( 350, 250, 50, 50, cont, 20 );
		//                    
		// // g.setStroke( new BasicStroke(4.0f) );
		// // g.setColor( new Color(90, 32, 2) );
		// // g.drawRoundRect( 200, 300, 400, 50, 10, 10 );
		//
		// // g.setColor( new Color(247, 215, 105) );
		// // g.fillOval( 400 - cont / 2, 100 - cont / 2, cont, cont );
		//                    
		// // g.setColor( Color.BLACK );
		// // g.drawOval( 350, 250, 50, 50 );
		//
		// if (!contracting) {
		// cont += 1;
		// if (cont > 60)
		// contracting = true;
		// } else {
		// cont -= 1;
		// if (cont < 10)
		// contracting = false;
		// }
		//                    
		// GUI.this.endDraw( );
		// }
		// };
		// loadingTimer.scheduleAtFixedRate( loadingTask, 20, 20 );
		// }
		// if (percent == 100) {
		// loadingTimer.cancel( );
		// }
		// this.loading = percent;
		// //TODO coge no se mu bien como la pantalla
		// // Graphics2D g = this.getGraphics( );
		// // g.drawImage( loadingImage, 0, 0, null);
		//
		// // g.setColor( new Color(250, 173, 6) );
		// // g.fillRoundRect( 200, 300, loading * 4, 50, 10, 10 );
		//        
		// //g.setStroke( new BasicStroke(4.0f) );
		// //g.setColor( new Color(90, 32, 2) );
		// // g.drawRoundRect( 200, 300, 400, 50, 10, 10 );
		//        
		// this.endDraw( );
	}

	public void setShowsOffestArrows(boolean showsOffsetArrows,
			boolean moveOffsetRight, boolean moveOffsetLeft) {

		this.showsOffsetArrows = showsOffsetArrows;
		this.moveOffsetRight = moveOffsetRight;
		this.moveOffsetLeft = moveOffsetLeft;
	}

	public void setTransition(int transitionTime, int transitionType,
			long elapsedTime) {

		if (transitionTime > 0 && transitionType > 0) {
			this.transition = new Transition(transitionTime, transitionType);
		}

	}

	public void drawFPS(int calcFPS) {

		fps.setFPS(calcFPS);

	}

	public Bitmap getBmpCpy() {
		return finalBmp;
	}

	public void update(long elapsedTime) {
		hud.update(elapsedTime);
	}

	public boolean processPressed(UIEvent e) {
		return hud.processPressed(e);

	}

	public boolean processScrollPressed(UIEvent e) {

		return hud.processScrollPressed(e);

	}

	public boolean processUnPressed(UIEvent e) {

		return hud.processUnPressed(e);

	}

	public boolean processFling(UIEvent e) {
		return hud.processFling(e);

	}

	public boolean processTap(UIEvent e) {
		return hud.processTap(e);
	}

	public int getGameAreaWidth() {
		return WINDOW_WIDTH;
	}

	public int getGameAreaHeight() {
		return WINDOW_HEIGHT;
	}

	public int getAscent() {
		// TODO Auto-generated method stub
		return (int) mPaint.ascent();
	}

//	public void toggleHud(boolean b) {
//
//	}

}
