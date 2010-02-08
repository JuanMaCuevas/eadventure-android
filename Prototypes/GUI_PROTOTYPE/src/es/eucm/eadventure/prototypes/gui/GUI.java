package es.eucm.eadventure.prototypes.gui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import android.graphics.Typeface;
import android.view.SurfaceHolder;
import es.eucm.eadventure.prototypes.gui.hud.Magnifier;
import es.eucm.eadventure.prototypes.control.FunctionalData.*;

public class GUI {

	/**
	 * GUI INSTANCE
	 */
	
	private static GUI instance;
	
	/**
	 * Canvas dimension
	 */
	private static int mCanvasHeight=320;
	private static int mCanvasWidth=480;
	
	/** Handle to the surface manager object we interact with */
	private SurfaceHolder mSurfaceHolder;
	/** Context of the activity creating the thread */
	private Context mContext;


	/**
	 * BITMAP COPY & ITS CANVAS (VIEW)
	 */

	private static Bitmap bitmapcpy;
	private static Canvas canvascpy;

	/**
	 * MAGNIFIER
	 */

	private static Magnifier magnifier;
	
	/**
	 * TEXT PAINT
	 */	
	private static Paint mPaint;
	
	
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
	
	
	FPS fps=new FPS();
	
	public FPS getfps()
	{
		return fps;
	}
	
	private GUI(SurfaceHolder mSurfaceHolder) {
		this.mSurfaceHolder = mSurfaceHolder;
		elementsToDraw = new ArrayList<ElementImage>();
		 textToDraw=new ArrayList<Text>();
	}

	public static void create(SurfaceHolder mSurfaceHolder) {

		instance = new GUI(mSurfaceHolder);
		bitmapcpy = Bitmap.createBitmap(mCanvasWidth, mCanvasHeight, Bitmap.Config.RGB_565);
		canvascpy = new Canvas(bitmapcpy);
		
		magnifier = new Magnifier(45,4,bitmapcpy);
		
		mPaint = new Paint();
		mPaint.setTextSize(15);
		mPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL));
        mPaint.setStrokeWidth(4);
        mPaint.setColor(0XFFFFFFFF);
        
       

	}

	public static GUI getInstance() {

		return instance;
	}
	
	public Canvas getGraphics() {
		return canvascpy;
	}
	
	
	private void drawHUD(Canvas canvas) {

		magnifier.setBmpsrc(bitmapcpy);
		magnifier.doDraw(canvas);
		
	}
	
	public void doPushDraw() {
				
		 /* Canvas canvas = null;
          try {
              canvas = mSurfaceHolder.lockCanvas(null);
              synchronized (mSurfaceHolder) {
          		 canvas.drawBitmap(bitmapcpy, 0, 0, null);
            	 drawHUD(canvas);
              }
          } finally {
              // do this in a finally so that if an exception is thrown
              // during the above, we don't leave the Surface in an
              // inconsistent state
              if (canvas != null) {
                  mSurfaceHolder.unlockCanvasAndPost(canvas);
              }
          }
		*/
		
		Canvas canvas = null;
        try {
            canvas = mSurfaceHolder.lockCanvas(null);
            synchronized (mSurfaceHolder) {
        		 canvas.drawBitmap(bitmapcpy, 0, 0, null);
        		 this.background.draw(canvas);
        		 
        		 for (int i=0;i<this.elementsToDraw.size();i++)
        		 {
        			 this.elementsToDraw.get(i).draw(canvas);
        		 }
        		 this.elementsToDraw.clear();
        		//TODO tengo que ponerlo todo en una pila no en pilas separadas y tener un depth asi pinta todo ordenado
         		//xq si quiero poner un elemento encima de una mesa uqe es foreground 
        		 this.foreground.draw(canvas);
        		 
        		 for (int i=0;i<this.textToDraw.size();i++)
        		 {
        			 this.textToDraw.get(i).draw(canvas);
        		 }
        		 
        		fps.draw(canvas);
        		 
          	 drawHUD(canvas);
            }
        } finally {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
            if (canvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

		
	}

	public void updateHudPos(int x, int y) {
		magnifier.updateMagPos(x,y);
	}
	

	public void toggleHud() {
		
		magnifier.toggle();
		
	}

	public void showHud() {
		magnifier.show();
		
	}

	public void hideHud() {
	
	    magnifier.hide();
		
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
			if (depth <= elementsToDraw.get(i).getDepth()) {
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
			//element.setDepth(elementsToDraw.size() - 1);
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
	public void addforegroundToDraw(Bitmap background, int offsetX) {

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
	public void addTextToDraw(String[] string, int x, int y, int textColor,int borderColor) {
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
			int borderColor, int bubbleBkgColor, int bubbleBorderColor,
			boolean showArrow) {

		boolean added = false;
		int i = 0;
		Text text = new Text(string, x, y, textColor, borderColor,bubbleBkgColor, bubbleBorderColor, showArrow);
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
	public static void drawStringOnto(Canvas g, String string, int x,
			int y, boolean centeredX, int textColor, int borderColor,boolean border) {

		// Get the current text font metrics (width and hegiht)
		
		double width = mPaint.measureText(string);
		double height = mPaint.ascent();
		int realX = x;
		int realY = y;

		// If the text is centered in its X coordinate
		if (centeredX) {
			// Check if the text don't go out of the window horizontally
			// and if it do correct it so it's in the window
			if (realX + width / 2 > mCanvasWidth) {
				realX = (int) (mCanvasWidth - width / 2);
			} else if (realX - width / 2 < 0) {
				realX = (int) (width / 2);
			}
			realX -= width / 2;
			// Check if the text don't go out of the window vertically
			// and if it do correct it so it's in the window
			if (realY > mCanvasHeight) {
				realY = mCanvasHeight;
			} else if (realY - height < 0) {
				realY = (int) height;
			}
			// if it's not centered
		} else {
			// Check if the text don't go out of the window horizontally
			// and if it do correct it so it's in the window
			// FIXME nuevo, a ver si funciona

			if (realX + width > mCanvasWidth) {
				realX = (int) (mCanvasWidth - width);
			} else if (realX < 0) {
				realX = 0;
			}

			if (realX + width > mCanvasWidth) {
				realX = 0;
				// To know the width of one character
				double w = mPaint.measureText(new String("A"));
				int position = (int) (mCanvasWidth / w) + 18;
				string = string.substring(0, position);
				string = string + "...";
			}
			// Check if the text don't go out of the window vertically
			// and if it do correct it so it's in the window
			if (realY > mCanvasHeight) {
				realY = mCanvasHeight;
			} else if (realY - height < 0) {
				realY = (int) height;
			}
		}
		// If the text has border, draw it
		mPaint.setColor(textColor);
		if (border) {
			
			//FIXME no lo entiendo intenta darle efecto 3d al texto, si lo hago es q directamente me va a 1 el texto
			//g.setColor(borderColor);
			
			//mPaint.setColor(borderColor);
			//g.drawText(string, realX - 1, realY - 1,mPaint);
			//g.drawText(string, realX - 1, realY + 1,mPaint);
			//g.drawText(string, realX + 1, realY - 1,mPaint);
			//g.drawText(string, realX + 1, realY + 1,mPaint);
			
			//g.setColor(textColor);
			
			//FIXME el color CORRECTO
			//mPaint.setColor(Color.RED);
			//g.drawRoundRect(rect, rx, ry, paint)
		}
		// Draw the text
	mPaint.setAntiAlias(true);
		g.drawText(string, realX, realY,mPaint);
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
	public static void drawStringOnto(Canvas g, String[] strings, int x,int y, int textColor, int borderColor) {

		// Calculate the total height of the block text
	
		int textBlockHeight = (int) (mPaint.getTextSize() * strings.length	+ mPaint.getFontMetrics().leading);

		
		// This is the y lower position of the first line
		int realY = (int) (y - textBlockHeight + mPaint.ascent());
		///if (realY < mPaint.ascent())
		//	realY = (int) mPaint.ascent();
		realY=y;


		// Draw each line of the string array
		for (String line : strings) {
			drawStringOnto(g, line, x, realY, true, textColor, borderColor,true);
			realY =(int) (realY+ mPaint.getTextSize()+ mPaint.getFontMetrics().leading);
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
	public static void drawStringOnto(Canvas g, String[] strings, int x,
			int y, int textColor, int borderColor, int bkgColor,
			int bubbleBorder, boolean showArrow) {

		
		int textBlockHeight = (int) (mPaint.getTextSize()* strings.length +  mPaint.getFontMetrics().leading);

		int maxWidth = 25;
		for (String line : strings)
			maxWidth = (mPaint.measureText(line) > maxWidth ? (int) mPaint.measureText(line)
					: maxWidth);

		int tempX = x;
		int tempY = y;
		if (tempX - maxWidth / 2 < 0)
			tempX = maxWidth / 2;
		if (tempY - textBlockHeight < 0)
			tempY = textBlockHeight;
		if (tempX + maxWidth / 2 > mCanvasWidth)
			tempX = mCanvasWidth - maxWidth / 2;
		
		
		
		Paint paint = new Paint();
        paint.setFilterBitmap(false);
		paint.setStyle(Paint.Style.STROKE);
        paint.setShader(null);
        paint.setColor(borderColor);
       RectF rectanguloG=new RectF();
		rectanguloG.set((tempX - maxWidth / 2 - 5)-0.5f,(tempY - textBlockHeight - 5)-0.5f, ((tempX - maxWidth / 2 - 5)+maxWidth + 10)+0.5f,((tempY - textBlockHeight - 5)+ textBlockHeight + 10)+0.5f);
		g.drawRoundRect(rectanguloG, 20, 20,paint);
		
	
		RectF rectangulo=new RectF();
		rectangulo.set(tempX - maxWidth / 2 - 5,tempY - textBlockHeight - 5, (tempX - maxWidth / 2 - 5)+maxWidth + 10,(tempY - textBlockHeight - 5)+ textBlockHeight + 10);
		
		
		Paint propiedades=new Paint();
		propiedades.setColor(bkgColor);
		g.drawRoundRect(rectangulo, 20, 20,propiedades);
		

	
		/*
		AlphaComposite alphaComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.8f);
		Composite temp = g.getComposite();
		g.setComposite(alphaComposite);
		g.setColor(bkgColor);
		g.fillRoundRect(tempX - maxWidth / 2 - 5, tempY - textBlockHeight - 5,
				maxWidth + 10, textBlockHeight + 10, 20, 20);

		g.setComposite(temp);
		g.setColor(bubbleBorder);
		g.drawRoundRect(tempX - maxWidth / 2 - 5, tempY - textBlockHeight - 5,
				maxWidth + 10, textBlockHeight + 10, 20, 20);

		if (showArrow) {
			g.setComposite(alphaComposite);
			g.setColor(bkgColor);
			int x_p[] = new int[] { tempX - 10, tempX + 10, tempX };
			int y_p[] = new int[] { tempY + 5, tempY + 5, tempY + 15 };
			g.fillPolygon(x_p, y_p, 3);

			g.setComposite(temp);
			g.setColor(bubbleBorder);
			g.drawLine(x_p[0], y_p[0], x_p[2], y_p[2]);
			g.drawLine(x_p[1], y_p[1], x_p[2], y_p[2]);
		}
*/
		drawStringOnto(g, strings, x, y, textColor, borderColor);
	}
	
	
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
		public SceneImage(Bitmap background, int offsetX) {

			this.background = background;
			this.offsetX = offsetX;
		}

		/**
		 * Draw the background with the offset
		 * 
		 * @param c
		 *            canvas to draw the background
		 */
		public void draw(Canvas c) {

			// OLD g.drawImage( background, 0, 0, GUI.WINDOW_WIDTH,
			// GUI.WINDOW_HEIGHT, offsetX, 0, offsetX + GUI.WINDOW_WIDTH,
			// GUI.WINDOW_HEIGHT, gameFrame );
			/**
			 * Attention: the order of the parameters source and destination
			 * rectangle differs within the methods awt.Graphics2D.drawImage and
			 * android.graphics.Canvas.drawBitmap
			 * 
			 */

			c.drawBitmap(background, 0, 0, null);
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
	}
	
	
}
