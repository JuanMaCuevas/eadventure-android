package es.eucm.eadventure.prototypes.gui.hud.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;

public class Magnifier {

	/*
	 * Tama–o de dedo
	 */

	/**
	 * MAGNIFIER SATIC DESC
	 */

	private Bitmap magBmp;
	private int bmpEdgeSize;
	private Canvas canvasMag;
	private int radius;
	private int magFrameWidth;

	/**
	 * MAGNIFIER DINAMIC DESC
	 */

	private int centerX;
	private int centerY;

	// Bounds
	private int left;
	private int top;
	private int right;

	// Frame Paint

	Paint pFrame;

	/**
	 * FINGER REGION
	 */
	// Bounds
	private int frleft;
	private int frtop;
	private int frright;
	private int frradius;

	private boolean shown;

	/**
	 * MAGNIFIED BITMAP
	 */
	private Bitmap bmpsrc;
	private Matrix mBmp;
	float zoom;

	public Magnifier(int radius, int magFrameWidth, float zoom , Bitmap bmp) {

		this.magFrameWidth = magFrameWidth;
		this.radius = radius - magFrameWidth;
		this.frradius = radius - magFrameWidth;
		this.bmpEdgeSize = radius * 2;
		this.shown = false;
		this.bmpsrc = bmp;
		this.mBmp = new Matrix();
		this.zoom = zoom;
		centerX = 0;
		centerY = 0;
		left = 0;
		top = 0;
		right = 0;
		pFrame = new Paint(Paint.ANTI_ALIAS_FLAG);
		pFrame.setColor(0xFF000000);
		pFrame.setStyle(Paint.Style.STROKE);
		pFrame.setStrokeWidth(magFrameWidth);

		createMagnifier();

	}

	private void createMagnifier() {

		magBmp = Bitmap.createBitmap(bmpEdgeSize, bmpEdgeSize,
				Bitmap.Config.RGB_565);
		
		float t = (zoom*frradius*2-bmpEdgeSize)/2;
		
		mBmp.preTranslate(-t,-t);
		mBmp.preScale(zoom,zoom);

		
		canvasMag = new Canvas(magBmp);
	}

	public void doDraw(Canvas c) {

		if (shown) {
			updateMagPicture();
			paintMagBmp(c);

		}
	}

	private void updateMagPicture() {

		Bitmap bmpmagaux = Bitmap.createBitmap(bmpsrc, frleft, frtop,
				frradius * 2, frradius * 2);
		canvasMag.drawBitmap(bmpmagaux, mBmp,null);

	}

	private void paintMagBmp(Canvas c) {

		Path mPath = new Path();
		c.save();
		c.translate(left, top-20); // el 20 esta puesto a pelo demomento -> cambiara cuando se tenga en cuenta la densidad de pixeles
		mPath.reset();
		mPath.addCircle(bmpEdgeSize / 2, bmpEdgeSize / 2, radius,
				Path.Direction.CCW);
		c.clipPath(mPath, Region.Op.REPLACE);
		c.drawBitmap(magBmp, 0, 0, null);

		c.drawCircle(bmpEdgeSize / 2, bmpEdgeSize / 2, this.radius, pFrame);

		c.restore();
	}

	public void updateMagPos(int xfocus, int yfocus) {

		calculateCenterBounds(xfocus, yfocus);
		calculateFingerSlope(xfocus, yfocus);

	}

	private void calculateFingerSlope(int xfocus, int yfocus) {

		frtop = yfocus - frradius;
		frleft = xfocus - frradius;
		frright = xfocus + frradius;
	}

	private void calculateCenterBounds(int xfocus, int yfocus) {

		this.centerX = xfocus;
		this.centerY = yfocus - radius;
		top = centerY - bmpEdgeSize / 2;
		left = centerX - bmpEdgeSize / 2;
		right = centerX + bmpEdgeSize / 2;

	}

	public void toggle() {

		shown = !shown;

	}

	public void show() {

		shown = true;

	}

	public void hide() {
		shown = false;
	}

}
