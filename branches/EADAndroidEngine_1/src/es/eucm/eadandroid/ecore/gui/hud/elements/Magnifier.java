package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.gui.GUI;

public class Magnifier {



	/**
	 * MAGNIFIER SATIC DESC
	 */

	private Bitmap magBmp;
	private Canvas canvasMag;
	
	Paint pFrame;

	/**
	 * MAGNIFIER DINAMIC DESC
	 */

	private boolean showing;
	private Rect magBounds;
	private int radius;
	public static int CENTER_OFFSET;


	/**
	 * FINGER REGION
	 */
	

	private static final int FINGER_REGION = (int) (60f * GUI.DISPLAY_DENSITY_SCALE);
	private static final int F_RADIUS = FINGER_REGION / 2 ;

	private Rect fBounds;

	

	/**
	 * MAGNIFIED BITMAP
	 */
	private Bitmap bmpsrc;
	private Matrix matrix;
	float zoom;
	Path mPath = new Path();
	private Rect magBoundsIntersected;
	
	/**
	 * WINDOW BOUNDS
	 */
	
	Rect windowBounds;
	
	/**
	 * ITEM DESCRIPTION
	 */
	
	Paint textP ;

	public Magnifier(int radius, int frameWidth, float zoom , Bitmap bmp) {

		this.radius = radius;
		CENTER_OFFSET = radius;
		
		magBounds = new Rect(0,0, radius * 2, radius * 2);
		magBoundsIntersected = new Rect(0,0, radius * 2, radius * 2);
		
		fBounds = new Rect(0,0,FINGER_REGION,FINGER_REGION);
		
		windowBounds  = new Rect(0,0,GUI.FINAL_WINDOW_WIDTH,GUI.FINAL_WINDOW_HEIGHT);
		
		
		this.showing = false;
		this.bmpsrc = bmp;
		this.matrix = new Matrix();
		this.zoom = zoom;
	
		pFrame = new Paint(Paint.ANTI_ALIAS_FLAG);
		pFrame.setColor(0xFF000000);
		pFrame.setStyle(Paint.Style.STROKE);
		pFrame.setStrokeWidth(frameWidth);
		
		textP = new Paint(Paint.ANTI_ALIAS_FLAG);
		textP .setColor(0xFFFFFFFF);
		textP.setTextSize(20 * GUI.DISPLAY_DENSITY_SCALE);
		textP.setTypeface(Typeface.SANS_SERIF);
		textP.setTextAlign(Align.CENTER);
		

		createMagnifier();

	}

	private void createMagnifier() {

		magBmp = Bitmap.createBitmap(magBounds.width(), magBounds.height(),
				Bitmap.Config.RGB_565);
		
	//	float t = (zoom*fBounds.width()-magBounds.width())/2;
		
		float t = (zoom*magBounds.width()-magBounds.width())/2;
		
		matrix.preTranslate(-t,-t);
		matrix.preScale(zoom,zoom);

		
		canvasMag = new Canvas(magBmp);
	}

	public void doDraw(Canvas c) {

		if (showing) {
			updateMagPicture();
			paintMagBmp(c);

		}
	}

	private void updateMagPicture() {


//		Bitmap bmpmagaux = Bitmap.createBitmap(bmpsrc,fBounds.left,fBounds.top,
//				fBounds.width(), fBounds.height());
		
	    Bitmap bmpmagaux = Bitmap.createBitmap(bmpsrc,magBoundsIntersected.left,magBoundsIntersected.top,
		magBoundsIntersected.width(), magBoundsIntersected.height());
		
	    canvasMag.save();
	    canvasMag.drawARGB(255, 0, 0, 0);
	    canvasMag.translate(magBoundsIntersected.left - magBounds.left,magBoundsIntersected.top - magBounds.top);
		canvasMag.drawBitmap(bmpmagaux, matrix,null);
		canvasMag.restore();

	}

	private void paintMagBmp(Canvas c) {
		

		c.save();
		c.translate(magBounds.left, magBounds.top); 
		
		c.save();
		
		c.translate(magBounds.width() / 2, -5 * GUI.DISPLAY_DENSITY_SCALE);
		
		FunctionalElement fe = Game.getInstance().getActionManager().getElementOver();
		
		String exit = Game.getInstance().getActionManager().getExit();
		
		if (fe != null) {
			c.drawText(fe.getElement().getName(), 0, 0, textP );
		}
		
		if (exit!=null) {
			c.drawText(exit, 0, 0, textP );
		}
		
		c.restore();
		
		mPath.reset();
		mPath.addCircle(radius, radius , radius,
				Path.Direction.CCW);
		c.clipPath(mPath, Region.Op.REPLACE);

		c.save();
		c.translate(magBoundsIntersected.left - magBounds.left,magBoundsIntersected.top -  magBounds.top);
		c.drawBitmap(magBmp, 0, 0, null);
		c.restore();

		c.drawCircle(radius,radius,radius, pFrame);		
		c.drawPoint(radius, radius, pFrame);
		
		c.restore();
	}

	public void updateMagPos(int xfocus, int yfocus) {

		magBounds.set(xfocus - radius, yfocus +30 - 2*radius, xfocus + radius, yfocus + 30 );
		
		magBoundsIntersected.set(magBounds);
		magBoundsIntersected.intersect(windowBounds);
		
//		fBounds.set(xfocus-F_RADIUS, yfocus- 2*F_RADIUS - radius , xfocus + F_RADIUS, yfocus - radius);


	}
	

	public void toggle() {

		showing = !showing;

	}

	public void show() {

		showing = true;

	}

	public void hide() {
		showing = false;
	}

}
