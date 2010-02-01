package es.eucm.eadventure.prototypes.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.UIEvent;
import es.eucm.eadventure.prototypes.gui.hud.HUD;

public class GUI {

	/**
	 * GUI INSTANCE
	 */
	
	private static GUI instance;
	
	/**
	 * Canvas dimension
	 */
	public static int WINDOW_HEIGHT=320;
	public static int WINDOW_WIDTH=480;
	
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
	 * HUD
	 */
	
	private HUD hud;
	
	/**
	 * TEXT PAINT
	 */
	
	private static Paint mPaint;
	
	private GUI(SurfaceHolder mSurfaceHolder) {
		this.mSurfaceHolder = mSurfaceHolder;
	}

	public static void create(SurfaceHolder mSurfaceHolder) {

		instance = new GUI(mSurfaceHolder);
		bitmapcpy = Bitmap.createBitmap(WINDOW_WIDTH, WINDOW_HEIGHT, Bitmap.Config.RGB_565);
		canvascpy = new Canvas(bitmapcpy);
				
		mPaint = new Paint();
		mPaint.setTextSize(15);
		mPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL));
        mPaint.setStrokeWidth(4);
        mPaint.setColor(0XFFFFFFFF);

	}

	public static GUI getInstance() {

		return instance;
	}
	
	public void init() {
		
		hud = new HUD();
		
	}
	
	public Canvas getGraphics() {
		return canvascpy;
	}
	
	
	public void doPushDraw() {
				
		  Canvas canvas = null;
          try {
              canvas = mSurfaceHolder.lockCanvas(null);
              synchronized (mSurfaceHolder) {
          		 canvas.drawBitmap(bitmapcpy, 0, 0, null);
            	 hud.doDraw(canvas);
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

	public void drawFPS(int calcFPS) {
	
		canvascpy.drawText(String.valueOf((calcFPS)), 10, 10, mPaint);
		
	}

	public Bitmap getBmpCpy() {
		return bitmapcpy;
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

}
