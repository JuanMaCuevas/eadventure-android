package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Align;
import android.graphics.drawable.GradientDrawable;
import es.eucm.eadandroid.ecore.gui.GUI;

public class AnimText {

	
	/**
	 *  MISC CONTENT
	 */
	
	int width;
	int MAX_WIDTH;
	Path path;
	
	/**
	 * TEXT PROPS
	 */
	
	String text;	
	
	/** PAINTING PROPS
	 */
	
	Paint textP;
	GradientDrawable rightGrad;
	
	private int GRADIENT_WIDTH = (int)(20*GUI.DISPLAY_DENSITY_SCALE);
	
	public AnimText(int max_width, String text, float size, int color, Align align) {
		super();
		this.text = text;
		this.MAX_WIDTH = max_width;
		
		this.textP = new Paint();
		textP.setColor(color);
		textP.setTextSize(size * GUI.DISPLAY_DENSITY_SCALE);
		textP.setTextAlign(align);
		
		this.width = Math.min((int) textP.measureText(text, 0, text.length()),max_width);
		
		init();
		

	
	}
	

	public AnimText(int max_width, String text, Paint textP) {
		super();
		this.text = text;
		this.textP = textP;
		this.MAX_WIDTH = max_width;
		this.width = Math.min((int) textP.measureText(text, 0, text.length()),max_width);
		
		init();
	}
	
	private void init() {
		
		rightGrad = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] {
						Color.argb(0, 0, 0, 0), Color.argb(255,0, 0, 0) });
		rightGrad.setShape(GradientDrawable.RECTANGLE);
		rightGrad.setBounds(0, 0, GRADIENT_WIDTH, (int) textP.getTextSize());
		
		path = new Path();
		path.lineTo(width,0);
	}
	
	public void draw(Canvas c) {
		
		c.save();
		
		switch (textP.getTextAlign()) {
			
		case LEFT : 
	        c.drawTextOnPath(text,path, 0, 0,textP);	
			break; 
		case CENTER:
			c.translate(- width/2,0);	
	        c.drawTextOnPath(text,path, 0, 0,textP);	
			break;
		}
	      
		c.restore();
		
		c.save();
		
		switch (textP.getTextAlign()) {
		
		case LEFT : 
			c.translate(MAX_WIDTH -GRADIENT_WIDTH,- (int) textP.getTextSize());
			break; 
		case CENTER:
			c.translate(MAX_WIDTH/2 -GRADIENT_WIDTH,- (int) textP.getTextSize());
			break;
		}
	      
		rightGrad.draw(c);
		
		c.restore();

	}





	
}
