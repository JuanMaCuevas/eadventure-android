package es.eucm.eadventure.prototypes.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class FPS {

	public FPS() {
		// TODO Auto-generated constructor stub
		
		
		mPaint = new Paint();
		mPaint.setTextSize(15);
		mPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL));
        mPaint.setStrokeWidth(4);
        mPaint.setColor(0XFFFFFFFF);
	}
	
	int FPS;
	Paint mPaint; 

	public int getFPS() {
		return FPS;
	}

	public void setFPS(int fPS) {
		FPS = fPS;
	}
	
	public void draw(Canvas a)
	{
		a.drawText(String.valueOf((FPS)), 10, 10, mPaint);
	}

}
