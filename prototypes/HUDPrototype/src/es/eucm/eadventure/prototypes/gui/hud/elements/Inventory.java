package es.eucm.eadventure.prototypes.gui.hud.elements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import es.eucm.eadventure.prototypes.gui.GUI;

public class Inventory {

	public static final int INVENTORY_DRAG_REGION_HEIGHT = 30;
	
	public static final float INVENTORY_UNDRAG_REGION_Y = GUI.WINDOW_HEIGHT-30;
	
	private static final int drag_offset = 15;

	
	int bottom ;
	int top;
	int width;
	int height;
	
	boolean animating;
	
	boolean anchored;
	
	private RectF r;
	private Paint p;
	private Paint p2;
	
	private Paint textP;
	
	public Inventory() {
		
		bottom = INVENTORY_DRAG_REGION_HEIGHT;
		top = INVENTORY_DRAG_REGION_HEIGHT - GUI.WINDOW_HEIGHT;
		animating = false;
		anchored = false;
		width = GUI.WINDOW_WIDTH;
		height = GUI.WINDOW_HEIGHT;
		
		p = new Paint();
		p.setColor(Color.BLACK);
		p.setStyle(Style.FILL);
		p.setAntiAlias(true);
		
		p2 = new Paint();
		p2.setColor(Color.WHITE);
		p2.setStyle(Style.STROKE);
		p2.setAntiAlias(true);

		
		textP = new Paint();
		textP.setColor(Color.WHITE);
		textP.setStrokeWidth(8);
		textP.setStyle(Style.FILL);
		textP.setAntiAlias(true);
		textP.setTextSize(20);
		textP.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));

		
		r = new RectF(0,0,width-80,height-60);
		
		
	//	r = new Rect(GUI.WINDOW_WIDTH);
		
	}
	
	public void doDraw(Canvas c) {
		
		c.clipRect(0, 0, width, bottom);
		c.drawARGB(150, 0, 0, 0);
		c.save();
		c.translate(40,top+18);
//		c.drawRect(r, p);
		c.drawRoundRect(r, 15f, 15f, p);
		c.drawRoundRect(r, 15f, 15f, p2);
		c.translate(30,30);
		c.drawText("Inventory", 0, 0, textP);
		c.restore();
	}

	public void updateDraggingPos(int y) {
		
		if (y>INVENTORY_DRAG_REGION_HEIGHT)
			{ bottom = y+drag_offset;
			  top = bottom - height; 
			}
		else {top = INVENTORY_DRAG_REGION_HEIGHT - height;}
		

	}
	
	public boolean isAnimating(int y) {
		if (bottom > height/2 )
			animating = true;
		return animating;
	}

	public void resetPos() {
	
		bottom = INVENTORY_DRAG_REGION_HEIGHT;
		
	}

	public void update(long elapsedTime) {
	
		if (animating) {
			if (bottom<height) {
			bottom+=15;
			top = bottom-height;
			}
			else {anchored = true;
				  animating = false;
			}
		}
		
	}

	public boolean isAnchored() {
		return anchored;
	}

	
}
