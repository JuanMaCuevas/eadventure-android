package es.eucm.eadventure.prototypes.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import es.eucm.eadventure.prototypes.gui.hud.Magnifier;

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
	
	private GUI(SurfaceHolder mSurfaceHolder) {
		this.mSurfaceHolder = mSurfaceHolder;
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

		magnifier.doDraw(canvas);
		
	}
	
	public void doPushDraw() {
				
		  Canvas canvas = null;
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

	public void drawFPS(int calcFPS) {
	
		canvascpy.drawText(String.valueOf((calcFPS)), 10, 10, mPaint);
		
	}

}
