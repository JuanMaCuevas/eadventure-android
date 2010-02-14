package es.eucm.eadventure.prototypes.gui;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
	

	
	
	  protected static int graphicConfig;

	/**
	 * GUI INSTANCE
	 */
	
	private static GUI instance;
	
	/**
	 * Canvas dimension
	 */
	public static int mCanvasHeight=320;
	public static int mCanvasWidth=480;
	
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
				
		Canvas canvas = null;
		
		
		 background.draw(canvascpy);
		 
		 for (int i=0;i<this.elementsToDraw.size();i++)
		 {
			 this.elementsToDraw.get(i).draw(canvascpy);
		 }
		 this.elementsToDraw.clear();
		//TODO tengo que ponerlo todo en una pila no en pilas separadas y tener un depth asi pinta todo ordenado
 		//xq si quiero poner un elemento encima de una mesa uqe es foreground 
		 this.foreground.draw(canvascpy);
		 
		 for (int i=0;i<this.textToDraw.size();i++)
		 {
			 this.textToDraw.get(i).draw(canvascpy);
		 }
		 this.textToDraw.clear();
		 
		
		
        try {
            canvas = mSurfaceHolder.lockCanvas(null);
            synchronized (mSurfaceHolder) {
        		 canvas.drawBitmap(bitmapcpy, 0, 0, null);

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
	public void addTextToDraw(String[] string, int x, int y, int textColor,int borderColor, int bubbleBkgColor, int bubbleBorderColor,
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

///////////////////////////////////////////////TODO lo tendre que cambiar	
	
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
	public static void drawStringOnto(Canvas g, String string, int x,int y, boolean centeredX, int textColor, int borderColor,boolean border) {

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
	g.drawText(string, 50, 60,mPaint);
	//	g.drawText(string, realX, realY,mPaint);
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
		
		int textBlockHeight = (int) (mPaint.getTextSize() * strings.length	- mPaint.getFontMetrics().leading);

		
		// This is the y lower position of the first line
		int realY = (int) (y - textBlockHeight + mPaint.ascent());
		if (realY < mPaint.ascent())
			realY = (int) mPaint.ascent();
		//realY=y;


		// Draw each line of the string array
		for (String line : strings) {
			drawStringOnto(g, line, x, realY, true, textColor, borderColor,true);
			realY =(int) (realY+ mPaint.getTextSize());
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
	
	



    /**
     * Destroy the singleton instance
     */
    public static void delete( ) {
//TODO habra que eliminar todo
       GUI.instance=null;
    
    };
    
    
    /*
    *//**
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
     *//*
    public static void drawString( Canvas g, String string, int x, int y ) {

        // Get the current text font metrics (width and hegiht)
        FontMetrics fontMetrics = g.getFontMetrics( );
        double width = fontMetrics.stringWidth( string );
        double height = fontMetrics.getAscent( );

        int realX = x;
        int realY = y;

        //Check if the text don't go out of the window horizontally
        //and if it do correct it so it's in the window
        if( realX + width / 2 > WINDOW_WIDTH ) {
            realX = (int) ( WINDOW_WIDTH - width / 2 );
        }
        else if( realX - width / 2 < 0 ) {
            realX = (int) ( width / 2 );
        }
        realX -= width / 2;

        //Check if the text don't go out of the window vertically
        //and if it do correct it so it's in the window
        if( realY + height / 2 > WINDOW_HEIGHT ) {
            realY = (int) ( WINDOW_HEIGHT - height / 2 );
        }
        else if( realY < 0 ) {
            realY = 0;
        }
        realY += height / 2;

        //Draw the string
        g.drawString( string, realX, realY );
    }

    *//**
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
     *//*
    public static void drawString( Canvas g, String[] strings, int x, int y ) {

        //Calculate the total height of the block text
        FontMetrics fontMetrics = g.getFontMetrics( );
        int textBlockHeight = fontMetrics.getHeight( ) * strings.length - fontMetrics.getLeading( ) - fontMetrics.getDescent( );

        // This is the y center position of the first line
        int realY = y - textBlockHeight / 2 + fontMetrics.getAscent( ) / 2;

        //Draw each line of the string array
        for( String line : strings ) {
            drawString( g, line, x, realY );
            realY += fontMetrics.getHeight( );
        }
    }

    *//**
     * Split a text in various lines using the font width and an max width for
     * each line
     * 
     * @param text
     *            String that contains all the text to be used
     * @return String[] with the various lines splited from the text
     *//*
    public String[] splitText( String text ) {

        ArrayList<String> lines = new ArrayList<String>( );
        String currentLine = text;
        boolean exit = false;
        String line;

        int width;
        FontMetrics fontMetrics = getGraphics( ).getFontMetrics( );

        do {
            width = fontMetrics.stringWidth( currentLine );

            if( width > MAX_WIDTH_IN_TEXT ) {
                int lineNumber = (int) Math.ceil( (double) width / (double) MAX_WIDTH_IN_TEXT );
                int index = currentLine.lastIndexOf( ' ', text.length( ) / lineNumber );

                if( index == -1 ) {
                    index = currentLine.indexOf( ' ' );
                }

                if( index == -1 ) {
                    index = currentLine.length( );
                    exit = true;
                }

                line = currentLine.substring( 0, index );
                currentLine = currentLine.substring( index ).trim( );

            }
            else {
                line = currentLine;
                exit = true;
            }

            lines.add( line );
        } while( !exit );
        return lines.toArray( new String[ 1 ] );
    }
*/
    
    public static void setGraphicConfig( int newGraphicConfig ) {

        graphicConfig = newGraphicConfig;
    }
    
    public void clearBackground( ) {

        this.background = null;
    }
    
/*
   
    *//**
     * Draws the scene buffer given a Graphics2D
     * 
     * @param g
     *            Graphics2D to be used by the scene buffer
     *//*
    public void drawScene( Graphics2D g, long elapsedTime ) {

        if( transition != null && !transition.hasStarted( ) ) {

            //gameFrame.paintAll((Graphics2D) transition.getGraphics());

            drawToGraphics( (Graphics2D) transition.getGraphics( ) );

            Graphics2D graph = (Graphics2D) transition.getGraphics();
            gameFrame.paintAll(graph);
            graph.dispose();

            //transition.setImage(gameFrame.createVolatileImage(800, 600));
            transition.start( this.getGraphics( ) );
        }
        else if( transition == null || transition.hasFinished( elapsedTime ) ) {
            transition = null;
            drawToGraphics( g );
        }
        else {
            transition.update( g );
        }
    }
    
    
    public void drawToGraphics( Graphics2D g ) {
        if( background != null ) {
            background.draw( g );
            //background = null;
        }
 
        for( ElementImage element : elementsToDraw )
            element.draw( g );
        recalculateInteractiveElementsOrder();
        elementsToDraw.clear( );

 
        if( foreground != null ) {
            foreground.draw( g );
            foreground = null;
        }


        TimerManager timerManager = TimerManager.getInstance( );
        if( timerManager != null ) {
            timerManager.draw( g );
        }


        if( showsOffsetArrows ) {
            g.setColor( Color.BLACK );
            int ytemp = GUI.WINDOW_HEIGHT / 2;
            Composite old = g.getComposite( );
            AlphaComposite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.6f );

            if( !this.moveOffsetLeft )
                g.setComposite( alphaComposite );
            g.fillOval( -30, ytemp - 30, 60, 60 );

            if( this.moveOffsetRight )
                g.setComposite( old );
            else
                g.setComposite( alphaComposite );
            g.fillOval( GUI.WINDOW_WIDTH - 30, ytemp - 30, 60, 60 );

            g.setComposite( old );
            g.setColor( Color.WHITE );
            Stroke oldStroke = g.getStroke( );
            g.setStroke( new BasicStroke( 5 ) );
            g.drawLine( 5, ytemp, 15, ytemp - 15 );
            g.drawLine( 5, ytemp, 15, ytemp + 15 );
            g.drawLine( GUI.WINDOW_WIDTH - 5, ytemp, GUI.WINDOW_WIDTH - 15, ytemp - 15 );
            g.drawLine( GUI.WINDOW_WIDTH - 5, ytemp, GUI.WINDOW_WIDTH - 15, ytemp + 15 );
            g.setStroke( oldStroke );
        }

        for( Text text : textToDraw )
            text.draw( g );
        textToDraw.clear( );

    }
    
    
    *//**
     * The last call to draw in the display.
     *//*
    public void endDraw( ) {

        BufferStrategy strategy = gameFrame.getBufferStrategy( );
        strategy.show( );
        Toolkit.getDefaultToolkit( ).sync( );
    }

    
    *//**
     * Creates a Color from the red, green and blue component
     * 
     * @param r
     *            Red int
     * @param g
     *            Green int
     * @param b
     *            Blue int
     * @return Color corresponding to R,G,B components
     *//*
    public Color getColor( int r, int g, int b ) {

        float[] hsbvals = Color.RGBtoHSB( r, g, b, null );
        return Color.getHSBColor( hsbvals[0], hsbvals[1], hsbvals[2] );
    }

    
    *//**
     * @return the elementsToDraw
     *//*
    public ArrayList<ElementImage> getElementsToDraw( ) {
    
        return elementsToDraw;
    }

    
    *//**
     * @return the elementsToInteract
     *//*
    public List<FunctionalElement> getElementsToInteract( ) {
    
        return elementsToInteract;
    }
    
    *//**
     * Returns the frame that is the window
     * 
     * @return Frame the main window
     *//*
    public Canvas getFrame( ) {

        return gameFrame;
    }
    
    *//**
     * Gets the graphics context for the display. The ScreenManager uses double
     * buffering, so applications must call update() to show any graphics drawn.
     * The application must dispose of the graphics object.
     *//*
    public Graphics2D getGraphics( ) {
//TODO
        
        
        BufferStrategy strategy = gameFrame.getBufferStrategy( );
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics( );
        //Graphics2D g = (Graphics2D)panel.getGraphics();

        if( g == null ) {
            //System.out.println( "Error: Graphics2D = null " );
        }
        else {
            // Load antialiasing if not loaded and is requested
            if( ANTIALIASING && !g.getRenderingHints( ).containsValue( RenderingHints.VALUE_ANTIALIAS_ON ) ) {
                g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            }
            // Unload antialiasing if loaded and is not requested 
            if( !ANTIALIASING && !g.getRenderingHints( ).containsValue( RenderingHints.VALUE_ANTIALIAS_OFF ) ) {
                g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF );
            }
            // Load antialiased text if not loaded and is requested
            if( ANTIALIASING_TEXT && !g.getRenderingHints( ).containsValue( RenderingHints.VALUE_TEXT_ANTIALIAS_ON ) ) {
                g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
            }
            // Unload antialiased text if loaded and is not requested
            if( !ANTIALIASING_TEXT && !g.getRenderingHints( ).containsValue( RenderingHints.VALUE_TEXT_ANTIALIAS_OFF ) ) {
                g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF );
            }
        }
        return g;
    }

    *//**
     * Gets the GraphicsConfiguration of the window
     * 
     * @return GraphicsConfiguration of the JFrame
     *//*
    public GraphicsConfiguration getGraphicsConfiguration( ) {

        return gameFrame.getGraphicsConfiguration( );
    }
    
    *//**
     * Returns the highest Y value of the ElementImage given as argument which
     * is not transparent in the column x. Useful to determine if the player
     * must be painted before the element
     * 
     * @param element
     * @param x
     * @return
     *//*
    public int getRealMinY( ElementImage element, int x ) {

        boolean isInside = false;

        //int mousex = (int)( x - ( this.x - getWidth( ) *scale/ 2 ) );
        //int mousey = (int)( y - ( this.y - getHeight( ) *scale) );
        int width = element.image.getWidth( null );
        int transformedX = ( x - element.x );
        int minY = element.originalY;
        if( transformedX >= 0 && transformedX < width ) {
            minY = element.image.getHeight( null ) - 1;
            try {
                BufferedImage bufferedImage = (BufferedImage) element.image;
                do {
                    int alpha = bufferedImage.getRGB( transformedX, minY ) >>> 24;
                    minY--;
                    isInside = alpha > 128;
                } while( !isInside && minY > 0 );
            }
            catch( Exception e ) {

            }
        }

        return minY;
    }

    *//**
     * Returns the number of lines of the response text block
     * 
     * @return Number of response lines
     *//*
    public int getResponseTextNumberLines( ) {

        return hud.getResponseTextNumberLines( );
    }
    
    *//**
     * Returns the X point of the response block text
     * 
     * @return X point of the response block text
     *//*
    public int getResponseTextX( ) {

        return hud.getResponseTextX( );
    }

    *//**
     * Returns the Y point of the response block text
     * 
     * @return Y point of the response block text
     *//*
    public int getResponseTextY( ) {

        return hud.getResponseTextY( );
    }
    
    public boolean hasTransition( ) {

        return transition != null && !transition.hasFinished( 0 );
    }
    
    
    public void loading( int percent ) {
        if (percent == 0) {
            
            // FIXME Chapucilla que huele a pipi
            //this.loadingImage =  new ImageIcon("gui/loading.jpg").getImage();
            this.loadingImage = MultimediaManager.getInstance( ).loadImage( "gui/loading.jpg", MultimediaManager.IMAGE_MENU );
            if (this.loadingImage == null ){
                this.loadingImage = MultimediaManager.getInstance( ).loadImageFromZip( "gui/loading.jpg", MultimediaManager.IMAGE_MENU );
            }
            this.loading = percent;
            loadingTimer = new Timer();
            loadingTask = new TimerTask() {
                private int cont = 20;
                private boolean contracting = false;
                
                @Override
                public void run( ) {
                    Graphics2D g = GUI.this.getGraphics( );
                    g.drawImage( loadingImage, 0, 0, null);
                    
                    g.setColor( new Color(250, 173, 6) );
                    g.fillRoundRect( 200, 300, loading * 4, 50, 10, 10 );
//                    g.setColor( Color.BLUE );
//                    g.fillArc( 350, 250, 50, 50, cont, 20 );
                    
                    g.setStroke( new BasicStroke(4.0f) );
                    g.setColor( new Color(90, 32, 2) );
                    g.drawRoundRect( 200, 300, 400, 50, 10, 10 );

                    g.setColor( new Color(247, 215, 105) );
                    g.fillOval( 400 - cont / 2, 100 - cont / 2, cont, cont );
                    
//                    g.setColor( Color.BLACK );
//                    g.drawOval( 350, 250, 50, 50 );

                    if (!contracting) {
                        cont += 1;
                        if (cont > 60)
                            contracting = true;
                    } else {
                        cont -= 1;
                        if (cont < 10)
                            contracting = false;
                    }
                    
                    GUI.this.endDraw( );
                }
            };
            loadingTimer.scheduleAtFixedRate( loadingTask, 20, 20 );
        }
        if (percent == 100) {
            loadingTimer.cancel( );
        }
        this.loading = percent;
        //TODO coge no se mu bien como la pantalla
        Graphics2D g = this.getGraphics( );
        g.drawImage( loadingImage, 0, 0, null);

        g.setColor( new Color(250, 173, 6) );
        g.fillRoundRect( 200, 300, loading * 4, 50, 10, 10 );
        
        g.setStroke( new BasicStroke(4.0f) );
        g.setColor( new Color(90, 32, 2) );
        g.drawRoundRect( 200, 300, 400, 50, 10, 10 );
        
        this.endDraw( );
    }
    
    
    
    
    public void setShowsOffestArrows( boolean showsOffsetArrows, boolean moveOffsetRight, boolean moveOffsetLeft ) {

        this.showsOffsetArrows = showsOffsetArrows;
        this.moveOffsetRight = moveOffsetRight;
        this.moveOffsetLeft = moveOffsetLeft;
    }
    
    public void setTransition( int transitionTime, int transitionType, long elapsedTime ) {

        if( transitionTime > 0 && transitionType > 0 ) {
            this.transition = new Transition( transitionTime, transitionType );
        }

    }
    
    *//**
     * Init the GUI class and also get focus for the mainwindow
     *//*
    @Override
    public void initGUI( int guiType, boolean customized ) {

        // Create the hud, depending on guiType
        if( guiType == DescriptorData.GUI_TRADITIONAL )
            hud = new TraditionalHUD( );
        else if( guiType == DescriptorData.GUI_CONTEXTUAL )
            hud = new ContextualHUD( );

        gameFrame.setEnabled( true );
        gameFrame.setVisible( true );
        gameFrame.setFocusable( true );
        // Double buffered painting
        // Set always on top only in full-screen mode
        bkgFrame.setAlwaysOnTop( graphicConfig != DescriptorData.GRAPHICS_WINDOWED );
        gameFrame.createBufferStrategy( 2 );
        gameFrame.validate( );
//TODO
        gameFrame.addFocusListener( this );
        gameFrame.requestFocusInWindow( );

        hud.init( );

        if( Game.getInstance( ).getGameDescriptor( ).getCursorPath( DEFAULT_CURSOR ) != null ) {
            try {
                defaultCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( Game.getInstance( ).getGameDescriptor( ).getCursorPath( DEFAULT_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "defaultCursor" );
            }
            catch( Exception e ) {
                DebugLog.general( "Cound't find default cursor " );
                defaultCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/nocursor.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "defaultCursor" );
            }
        }
        else {
            try {
                defaultCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/default.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "defaultCursor" );
            }
            catch( Exception e ) {
                DebugLog.general( "Cound't find custom default cursor " );
                defaultCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/nocursor.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "defaultCursor" );
            }
        }
        //TODO
        gameFrame.setCursor( defaultCursor );
    }
 
*/	
}
