package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
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
	private Bitmap bmpmagaux;

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
	private static final int F_RADIUS = FINGER_REGION / 2;

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

	Paint textP;
	Paint pointPaint;
	
	int elementColor = Color.parseColor("#F4FA58");
	int combinedElementColor = Color.parseColor("#5858FA");
	int exitColor = Color.parseColor("#81F781");
	

	public Magnifier(int not_scaled_radius, int not_scaled_frameWidth, float zoom, Bitmap bmp) {

		this.radius = (int) (not_scaled_radius * GUI.DISPLAY_DENSITY_SCALE);
		CENTER_OFFSET = radius;

		magBounds = new Rect(0, 0, radius * 2, radius * 2);
		magBoundsIntersected = new Rect(0, 0, radius * 2, radius * 2);

		fBounds = new Rect(0, 0, FINGER_REGION, FINGER_REGION);

		windowBounds = new Rect(0, 0, GUI.FINAL_WINDOW_WIDTH,
				GUI.FINAL_WINDOW_HEIGHT);

		this.showing = false;
		this.bmpsrc = bmp;
		this.matrix = new Matrix();
		this.zoom = zoom;
		
		bmpmagaux = null;

		pFrame = new Paint(Paint.ANTI_ALIAS_FLAG);
		pFrame.setColor(0xFF000000);
		pFrame.setStyle(Paint.Style.STROKE);
		pFrame.setStrokeWidth(not_scaled_frameWidth * GUI.DISPLAY_DENSITY_SCALE);

		textP = new Paint(Paint.ANTI_ALIAS_FLAG);
		textP.setColor(0xFFFFFFFF);
		textP.setShadowLayer(4f, 0, 0, Color.BLACK);
		textP.setTextSize(20 * GUI.DISPLAY_DENSITY_SCALE);
		textP.setTypeface(Typeface.SANS_SERIF);
		textP.setTextAlign(Align.CENTER);
		
		pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pointPaint.setColor(0xFF000000);
		pointPaint.setStrokeWidth(4f * GUI.DISPLAY_DENSITY_SCALE);

		createMagnifier();

	}

	private void createMagnifier() {

		magBmp = Bitmap.createBitmap(magBounds.width(), magBounds.height(),
				Bitmap.Config.RGB_565);

		float t = (zoom * magBounds.width() - magBounds.width()) / 2;

		matrix.preTranslate(-t, -t);
		matrix.preScale(zoom, zoom);

		canvasMag = new Canvas(magBmp);
	}

	public void doDraw(Canvas c) {

		if (showing) {
			updateMagPicture();
			paintMagBmp(c);

		}
	}

	private void updateMagPicture() {
		
		 bmpmagaux = Bitmap.createBitmap(bmpsrc,
				magBoundsIntersected.left, magBoundsIntersected.top,
				magBoundsIntersected.width(), magBoundsIntersected.height());

		canvasMag.save();
		canvasMag.drawARGB(255, 0, 0, 0);
		canvasMag.translate(magBoundsIntersected.left - magBounds.left,
				magBoundsIntersected.top - magBounds.top);
		canvasMag.drawBitmap(bmpmagaux, matrix, null);
		canvasMag.restore();
		bmpmagaux = null;

	}

	private void paintMagBmp(Canvas c) {

		
		c.save();
		c.translate(magBounds.left, magBounds.top);

		c.save();

		c.translate(magBounds.width() / 2, -5 * GUI.DISPLAY_DENSITY_SCALE);

		FunctionalElement fe = Game.getInstance().getActionManager()
				.getElementOver();

		String exit = Game.getInstance().getActionManager().getExit();
		
		FunctionalElement feInCursor = Game.getInstance().getActionManager().getElementInCursor();

		
		
		if (feInCursor!=null && fe!=null) {
			
			pFrame.setColor(combinedElementColor);
			pointPaint.setColor(combinedElementColor);
		    c.drawText(feInCursor.getElement().getName()+" + "+fe.getElement().getName(), 0, 0, textP);
			
		}
		
		else if (fe != null) {

			pFrame.setColor(elementColor);
			pointPaint.setColor(elementColor);
			c.drawText(fe.getElement().getName(), 0, 0, textP);
		}

		else if (exit != null && exit !="") {
			pFrame.setColor(exitColor);
			pointPaint.setColor(exitColor);
			c.drawText(exit, 0, 0, textP);
		}

		else {
			pFrame.setColor(Color.BLACK);
			pointPaint.setColor(Color.BLACK);
//			pFrame.setShadowLayer(4f, -4, 4, Color.BLACK);
		}

		c.restore();

		mPath.reset();
		mPath.addCircle(radius, radius, radius, Path.Direction.CCW);
		c.clipPath(mPath, Region.Op.REPLACE);

		c.save();
		c.translate(magBoundsIntersected.left - magBounds.left,
				magBoundsIntersected.top - magBounds.top);
		c.drawBitmap(magBmp, 0, 0, null);
		c.restore();

		c.drawCircle(radius, radius, radius, pFrame);
		 c.drawPoint(radius, radius, pointPaint);

		c.restore();
	}

	public void updateMagPos(int xfocus, int yfocus) {

		magBounds.set(xfocus - radius, yfocus + 30 - 2 * radius, xfocus
				+ radius, yfocus + 30);

		magBoundsIntersected.set(magBounds);
		magBoundsIntersected.intersect(windowBounds);

		// fBounds.set(xfocus-F_RADIUS, yfocus- 2*F_RADIUS - radius , xfocus +
		// F_RADIUS, yfocus - radius);

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
