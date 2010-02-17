/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author LÛpez MaÒas, E., PÈrez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fern·ndez-ManjÛn, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadandroid.common.auxiliar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Utility for creating virtual images. That is, images that are not stored
 * physically on the hard disk.
 * 
 * @author Javier
 * 
 */
public class CreateImage {

	public static final int CENTER = 0;

	public static final int LEFT = 1;

	public static final int RIGHT = 2;

	public static final int TOP = 1;

	public static final int BOTTOM = 2;

	public static Bitmap createImage(int width, int height, String text) {

		// PORTCOMMENT Aqui tenia puesto a 12 el tamaño de la fuente ... en typeface no se
		// puede especificar tamaño

		return createImage(width, height, text, Typeface.SANS_SERIF,Typeface.NORMAL);
	}

	public static Bitmap createImage(int width, int height, String text,
			Typeface typeface , int style) {

		return createImage(width, height, Color.GREEN, 5, Color.LTGRAY, text,
				Color.RED, CENTER, CENTER, typeface , style);
	}

	public static Bitmap createImage(int width, int height,
			int backgroundColor, int borderThickness, int borderColor,
			String text, int textColor, int alignX, int alignY, Typeface typeface , int style) {

		Bitmap im = Bitmap
		.createBitmap(width, height, Bitmap.Config.ARGB_8888);	
		Canvas canvas = new Canvas(im);
		
		int textThickness = 1;
		int textSize = 12;
		
		Paint mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
	
		// Paints the background
		canvas.drawColor(backgroundColor);
				
		// Paints the border
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderThickness);
        canvas.drawRect(0, 0, width, height, mPaint);
	
        //Paints the text
                
        float centerX = width * 0.5f;
        float centerY = height * 0.5f;
        
        mPaint.setColor(0x80FF0000);
        canvas.drawLine(centerX,0, centerX,2*centerY, mPaint);
        
        canvas.translate(centerX,centerY);
		mPaint.setTextSize(textSize);
		mPaint.setTypeface(Typeface.create(typeface, style));
        mPaint.setStrokeWidth(textThickness);
        mPaint.setColor(textColor);
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, 0, 0, mPaint);
      
		
     /*   // TODO align text inside bitmap
             // Calculate x & y according to alignment int x = 0; int y = 0; 
        if(alignX == CENTER ) { x = (int) ( ( width - textWidth ) / 2.0 ); }
		  else if( alignX == LEFT ) { if( width > 5 ) x = 5; else x = 0; } else
		  if( alignX == RIGHT ) { if( width > textWidth + 5 ) x = (int) ( width
		  - textWidth - 5.0 ); else x = 0; }
		  
		  if( alignY == CENTER ) { y = (int) ( ( height - textHeight ) / 2.0 );
		  } else if( alignY == TOP ) { if( height > 5 ) y = 5; else y = 0; }
		  else if( alignY == BOTTOM ) { if( height > textHeight + 5 ) y = (int)
		  ( height - textHeight - 5.0 ); else y = 0; }
		  
		  gr.drawString( text, x, y ); gr.dispose( );	
		*/
        
		return im;
        
	}



	// HELPCODE

	/*
	 * //
	 * http://dev.android.com/guide/samples/ApiDemos/src/com/example/android/apis
	 * /graphics/TextAlign.html
	 * 
	 * 
	 * draw some text using STROKE style
	 * 
	 * paint.setStyle(Paint.Style.STROKE); paint.setStrokeWidth(1);
	 * paint.setColor(Color.MAGENTA); paint.setTextSize(30);
	 * canvas.drawText(”Text”, 75, 75, paint);
	 * 
	 * // draw some text using FILL style paint.setStyle(Paint.Style.FILL);
	 * //turn antialiasing on paint.setAntiAlias(true); paint.setTextSize(30);
	 * canvas.drawText(”More Text”, 75, 110, paint);
	 * 
	 * / draw bounding rect before rotating text Rect rect = new Rect();
	 * paint.getTextBounds(str2rotate, 0, str2rotate.length(), rect);
	 * canvas.translate(x, y); paint.setStyle(Paint.Style.FILL); // draw
	 * unrotated text canvas.drawText(”!Rotated”, 0, 0, paint);
	 * paint.setStyle(Paint.Style.STROKE); canvas.drawRect(rect, paint); // undo
	 * the translate canvas.translate(-x, -y);
	 * 
	 * // rotate the canvas on center of the text to draw canvas.rotate(-45, x +
	 * rect.exactCenterX(), y + rect.exactCenterY()); // draw the rotated text
	 * paint.setStyle(Paint.Style.FILL); canvas.drawText(str2rotate, x, y,
	 * paint);
	 * 
	 * //undo the rotate canvas.restore(); canvas.drawText(”After
	 * canvas.restore()”, 50, 300, paint);
	 * 
	 * }
	 * 
	 * // draw some rotated text // get text width and height // set desired
	 * drawing location x = 75; y = 135; paint.setColor(Color.GRAY);
	 * paint.setTextSize(25); String str2rotate = “Rotated!”;
	 */

	// PORTCOMMENT
	/*
	 * public static void main( String[] args ) {
	 * 
	 * JFrame frame = new JFrame( ); Image image = CreateImage.createImage( 400,
	 * 300, "Marihuanhell" ); ImageIcon ic = new ImageIcon( image ); JLabel
	 * label = new JLabel( ic ); frame.setLayout( new BorderLayout( ) );
	 * frame.add( label, BorderLayout.CENTER ); frame.pack( );
	 * frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); frame.setVisible(
	 * true ); }
	 */
}
