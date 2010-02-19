package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.Inventory;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.gui.GUI;

public class GridPanel {

	/** ICON_HEIGHT in dips **/

	private static final int ICON_HEIGHT = (int) (48 * GUI.DISPLAY_DENSITY_SCALE);
	private static final int ICON_WIDTH = (int) (80 * GUI.DISPLAY_DENSITY_SCALE);

	private static final int TEXT_HEIGHT = (int) (13 * GUI.DISPLAY_DENSITY_SCALE);

	private static final int HORIZONTAL_ICON_SEPARATION = (int) (20 * GUI.DISPLAY_DENSITY_SCALE);

	private static final int VERTICAL_ICON_SEPARATION = (int) (30 * GUI.DISPLAY_DENSITY_SCALE);

	private static final int VERTICAL_TEXT_SEPARATION = (int) (5 * GUI.DISPLAY_DENSITY_SCALE);

	private static final int GRADIENT_TRANSPARENCY_WIDTH = (int) (40 * GUI.DISPLAY_DENSITY_SCALE);

	public static final int VERTICAL_ICON_SPACE = 2 * VERTICAL_ICON_SEPARATION
			+ ICON_HEIGHT + TEXT_HEIGHT + VERTICAL_TEXT_SEPARATION;

	public static final int HORIZONTAL_ICON_SPACE = 2
			* HORIZONTAL_ICON_SEPARATION + ICON_WIDTH;

	private static final float HORIZONTAL_SEP_PERCENTAGE = (float)HORIZONTAL_ICON_SEPARATION
			/ (float)HORIZONTAL_ICON_SPACE;

	private static final float VERTICAL_SEP_PERCENTAGE = (float)VERTICAL_ICON_SEPARATION
			/ (float)VERTICAL_ICON_SPACE;

	int numRows;
	int numColums;
	private int centerOffset;
	
	
	private Rect bounds;

	private int height;
	private int width;

	private int leftLimit;
	private int rightLimit;

	Paint textP;

	GradientDrawable rightGrad, leftGrad;

	Inventory invModel;

	/** SWIPE ANIMATION **/

	boolean swipeAnimating;
	int currentVelocityX;
	long elapsedSwipeTime;
	int direction;
	int swipeCorner;
	int lastDistance;

	private static final int MILI = 1000;

	private static final float DECCELERATION = 0.007f;
	
	/** ITEM SELECTION ANIMATION */
	
	RectF selectedItem ;
	boolean itemSelected;
	int selColumn , selRow;
	Paint pSelItem;

	private static final float ROUNDED_SELECT_ITEM_RADIO = 10f * GUI.DISPLAY_DENSITY_SCALE;

	public GridPanel(Rect r) {
		
	    bounds = r;
		this.height = r.bottom -r.top ;
		this.width = r.right - r.left;
		swipeAnimating = false;
		currentVelocityX = 0;
		direction = 0;
		lastDistance = 0;
		selectedItem = new RectF();
		itemSelected = false;
		
		pSelItem = new Paint();
		pSelItem.setColor(Color.argb(190, 255, 255, 255));
		pSelItem.setStyle(Style.FILL);
		pSelItem.setAntiAlias(true);

		calculateGrid();

		textP = new Paint();
		textP.setColor(Color.WHITE);
		textP.setStrokeWidth(6);
		textP.setStyle(Style.FILL);
		textP.setAntiAlias(true);
		textP.setTextSize(TEXT_HEIGHT);
		textP.setTextAlign(Paint.Align.CENTER);
		textP
				.setTypeface(Typeface.create(Typeface.SANS_SERIF,
						Typeface.NORMAL));

		rightGrad = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] {
						Color.argb(0, 0, 0, 0), Color.argb(255, 0, 0, 0) });
		rightGrad.setShape(GradientDrawable.RECTANGLE);
		rightGrad.setBounds(0, 0, GRADIENT_TRANSPARENCY_WIDTH, height);

		leftGrad = new GradientDrawable(
				GradientDrawable.Orientation.RIGHT_LEFT, new int[] {
						Color.argb(0, 0, 0, 0), Color.argb(255, 0, 0, 0) });
		leftGrad.setShape(GradientDrawable.RECTANGLE);
		leftGrad.setBounds(0, 0, GRADIENT_TRANSPARENCY_WIDTH, height);

		

	}
	
	public Rect getBounds(){
		return bounds;
	}

	public void draw(Canvas c) {

		invModel = Game.getInstance().getInventory();

		c.clipRect(0, 0, width, height);
		c.drawRoundRect(selectedItem,ROUNDED_SELECT_ITEM_RADIO,ROUNDED_SELECT_ITEM_RADIO, pSelItem);
		c.save();
		c.translate(leftLimit, 0);
		c.save();
		c.translate(0, centerOffset);
		c.save();

		numColums = (invModel.getItemCount() / numRows) + 1;
		boolean moreElems = invModel.getItemCount() > 0;

		for (int j = 0; j < numColums; j++) {
			int i = 0;
			c.translate(HORIZONTAL_ICON_SEPARATION, 0);

			while (moreElems && i < numRows) {

				c.translate(0, VERTICAL_ICON_SEPARATION);

				c.drawBitmap(
						invModel.getItem((j * numRows) + i).getIconImage(), 0,
						0, null);

				c.translate(ICON_WIDTH / 2, ICON_HEIGHT
						+ VERTICAL_TEXT_SEPARATION + TEXT_HEIGHT);
				c.drawText("Prueba", 0, 0, textP);

				c.translate(-(ICON_WIDTH / 2), VERTICAL_ICON_SEPARATION);

				i++;
				moreElems = ((j * numRows) + i) < invModel.getItemCount();

			}
			c.restore();
			c.translate((ICON_WIDTH + 2 * HORIZONTAL_ICON_SEPARATION), 0);
			c.save();

		}
		c.restore();
		c.restore();
		c.restore();
		
		leftGrad.draw(c);
		c.translate(width - GRADIENT_TRANSPARENCY_WIDTH, 0);
		rightGrad.draw(c);
		
		
		

	}

	private void calculateGrid() {

		leftLimit = 0;

		numRows = (height / VERTICAL_ICON_SPACE);

		centerOffset = (height - numRows * VERTICAL_ICON_SPACE) / 2;

		rightLimit = 0;

	}

	public void updateDragging(int x) {

		swipeAnimating = false;
		currentVelocityX = 0;

		int colWidth = numColums * (HORIZONTAL_ICON_SPACE);

		if ((leftLimit + x) > 0)
			leftLimit = 0;
		else if ((rightLimit + x) < width) {
			rightLimit = width;
			leftLimit = Math.min(rightLimit - colWidth, 0);
		} else
			leftLimit += x;

		updateSelectedItem();

	}

	public void swipe(long initialTime, int velocityX) {

		swipeAnimating = true;
		currentVelocityX = velocityX;

		if (velocityX >= 0)
			direction = 1;
		else
			direction = -1;

		elapsedSwipeTime = 0;

		swipeCorner = leftLimit;
		lastDistance = 0;

	}

	/** Formula => D = v0 * t +1/2 (a * t^2) **/

	public void update(long elapsedTime) {

		int colWidth = numColums * (HORIZONTAL_ICON_SPACE);

		rightLimit = Math.max(leftLimit + colWidth, width);

		if (swipeAnimating) {

			elapsedSwipeTime += elapsedTime;

			int distance = (int) ((currentVelocityX / MILI) * elapsedSwipeTime + (-direction
					* DECCELERATION * elapsedSwipeTime * elapsedSwipeTime) / 2);

			if (distance >= lastDistance) {
				if (direction == -1)
					swipeAnimating = false;
			} else if (direction == 1)
				swipeAnimating = false;

			if (swipeAnimating)
				if ((swipeCorner + distance) > 0) {
					leftLimit = 0;
					swipeAnimating = false;
				} else if ((swipeCorner + distance + colWidth) < width) {
					rightLimit = width;
					leftLimit = Math.min(rightLimit - colWidth, 0);
					swipeAnimating = false;
				} else
					leftLimit = swipeCorner + distance;

			lastDistance = distance;
			
			updateSelectedItem();

		}
	}

	public FunctionalElement selectItem(int posX, int posY) {

		FunctionalElement element = null;
		
		posX-=bounds.left;
		posY-= bounds.top ;

		int absoluteX = -leftLimit + posX ;
		int absoluteY = posY;
		
		 int column = (absoluteX / HORIZONTAL_ICON_SPACE) + 1;

		Log.w("column",String.valueOf(column));
		
	
		float precisionX = (absoluteX % HORIZONTAL_ICON_SPACE)/ (float)(HORIZONTAL_ICON_SPACE);

		
		if (precisionX >= HORIZONTAL_SEP_PERCENTAGE
				&& precisionX <= 1f - HORIZONTAL_SEP_PERCENTAGE) {

			int row = (absoluteY / VERTICAL_ICON_SPACE) + 1;

			float precisionY = (absoluteY % VERTICAL_ICON_SPACE)/(float)(VERTICAL_ICON_SPACE);
			
			Log.w("row",String.valueOf(row));
			

			if (precisionY >= VERTICAL_SEP_PERCENTAGE
					&& precisionY <= 1f - VERTICAL_SEP_PERCENTAGE) {

				int index = (column-1) * numRows + (row-1);
				
				Log.w("index",String.valueOf(index));

				if (index < invModel.getItemCount()) {
					
					selColumn = column;
					selRow = row;
					updateSelectedItem();					
					itemSelected = true;
					
					element = invModel.getItem(index);
				}

			}

		}

		return element;
	}
	
	private void updateSelectedItem(){
		int left =  ((selColumn-1) * HORIZONTAL_ICON_SPACE) + leftLimit;
		int right = (left + HORIZONTAL_ICON_SPACE);
		int top = (selRow-1) * VERTICAL_ICON_SPACE + centerOffset;
		int bottom = top + VERTICAL_ICON_SPACE;
		selectedItem.set(left, top, right, bottom);
		
		Log.w("left",String.valueOf(left));
		Log.w("right",String.valueOf(right));
		Log.w("top",String.valueOf(top));
		Log.w("bottom",String.valueOf(bottom));
	}
	



}
