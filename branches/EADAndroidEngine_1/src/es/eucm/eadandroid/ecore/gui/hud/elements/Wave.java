package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.GUI;

public class Wave {
	

	private static int MAX_RADIUS = (int) (15 * GUI.DISPLAY_DENSITY_SCALE);
	private static final int MILI = 1000;
	private static long ANIM_TIME = 500; // in miliseconds

	private static int ANIM_COUNT = 3;
	
	Paint paint;
	int alpha;
	
	float radius;
	
	int posX,posY;
	
	int offsetScene;
	
	int offsetClick;
	
	long elapsedWaveTime;
	
	private boolean showing;
	
	private int count;
	
	public Wave(int color) {
		super();
		paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(3*GUI.DISPLAY_DENSITY_SCALE);
		paint.setStyle(Style.STROKE);
		this.elapsedWaveTime=0;
		radius=0;
		showing=false;
		count = 0;
		alpha=255;
		offsetScene=0;
		int offsetClick=0;
	}

	public void update(long elapsedTime) {

		if (showing) {

			offsetScene = offsetClick - (int) (Game.getInstance().getFunctionalScene().getOffsetX() * GUI.SCALE_RATIOX);

			elapsedWaveTime += elapsedTime;

			if (count < ANIM_COUNT) {

				if (elapsedWaveTime < ANIM_TIME) {

					radius += (float) (elapsedTime * MAX_RADIUS)
							/ (float) ANIM_TIME;

					alpha = 255 - (int) ((elapsedWaveTime * 255) / ANIM_TIME);

					paint.setAlpha(alpha);

				} else {
					elapsedWaveTime = 0;
					radius = 0;
					count++;
				}

			} else {
				count = 0;
				showing = false;
				elapsedWaveTime = 0;
				radius = 0;
			}

		}

	}
	
	
	public void doDraw(Canvas c) {
		
		if (showing) {
			c.drawCircle(posX + offsetScene,posY, radius, paint);
		}
		
	}

	public void updatePosition(int srcX, int srcY) {
		
		posX=srcX;
		posY=srcY;
		showing=true;
		count=0;
		elapsedWaveTime=0;
		radius=0;
		offsetClick= (int) (Game.getInstance().getFunctionalScene().getOffsetX() * GUI.SCALE_RATIOX);

	}
	
	public void hide() {
		showing=false;
		count=0;
		elapsedWaveTime=0;
		radius=0;
	}


}
