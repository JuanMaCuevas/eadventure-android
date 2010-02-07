package es.eucm.eadventure.prototypes.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
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
	public static int WINDOW_HEIGHT ;
	public static int WINDOW_WIDTH ;
	
	public static float EAD_SCALE;
	public static int EAD_DESKTOP_HEIGHT = 600;
	
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
				
	}

	public static GUI getInstance() {

		return instance;
	}
	
	public void init(int landscapeHeight, int landscapeWidth) {
		
		WINDOW_HEIGHT = landscapeHeight;
		WINDOW_WIDTH = landscapeWidth;
		
		EAD_SCALE = (float)WINDOW_HEIGHT / (float)EAD_DESKTOP_HEIGHT;
		
		bitmapcpy = Bitmap.createBitmap(WINDOW_WIDTH, WINDOW_HEIGHT, Bitmap.Config.RGB_565);
		canvascpy = new Canvas(bitmapcpy);
		
		hud = new HUD();
		
		mPaint = new Paint();
		mPaint.setTextSize(15);
		mPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL));
        mPaint.setStrokeWidth(4);
        mPaint.setColor(0XFFFFFFFF);
		
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
