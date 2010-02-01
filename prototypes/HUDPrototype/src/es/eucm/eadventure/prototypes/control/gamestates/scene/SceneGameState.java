package es.eucm.eadventure.prototypes.control.gamestates.scene;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Queue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import es.eucm.eadventure.prototypes.control.gamestates.GameState;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.TouchListener;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.UIEvent;
import es.eucm.eadventure.prototypes.gui.GUI;

public class SceneGameState extends GameState {

	/**
	 * Save-State variable keys
	 */
	private static final String KEY_POS_X = "mPosX";
	private static final String KEY_FRAME = "mFrame";

	/**
	 * Canvas dimension
	 */
	private int mCanvasHeight = 320;
	private int mCanvasWidth = 480;

	/**
	 * Current frame index from pics , and frame x coordinate position
	 */
	private int mFrame, mPosX;

	/**
	 * STATIC BITMAPS to paint
	 */

	private Bitmap fondo;
	private Bitmap[] pics;

	SceneTouchHandler sch;

	public SceneGameState() {

		this.sch = new SceneTouchHandler();
		this.touchListener = new SceneTouchListener(sch);

		loadBitmaps();

		mFrame = 1;
		mPosX = 0;

	}

	@Override
	public void mainLoop(long elapsedTime, int fps) {

		handleUIEvents();
		updatePhysics();
		GUI.getInstance().update(elapsedTime);
		doDraw(GUI.getInstance().getGraphics());

	}

	private void loadBitmaps() {

		pics = new Bitmap[16];

		FileInputStream is;
		BufferedInputStream bis;
		int i;
		for (i = 0; i < 16; i++) {
			String number = String.format("%02d", i + 1);

			try {
				is = new FileInputStream(new File("/sdcard/pics/Master_Idle_"
						+ number + ".png")); // kitchen_bg.jpg"));
				bis = new BufferedInputStream(is);

				pics[i] = BitmapFactory.decodeStream(is);
				// fondo = BitmapFactory.decodeStream(is);

				bis.close();
				is.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("thread", "Archivo no encontrado");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("thread", "Excepcion IO");
			}

		}

		Bitmap notScaled = BitmapFactory
				.decodeFile("/sdcard/pics/kitchen_bg.jpg");
		fondo = Bitmap.createScaledBitmap(notScaled, mCanvasWidth,
				mCanvasHeight, false);

	}

	private void updatePhysics() {
		// long now = System.currentTimeMillis();
		// actualiza siguientes valores mu–eco
		mFrame++;
		if (mFrame == 17) {
			mFrame = 1;
		}
		mPosX = mPosX + 3;
		if (mPosX > mCanvasWidth)
			mPosX = 0;
		


	}

	private void doDraw(Canvas c) {

		c.drawBitmap(fondo, 0, 0, null);

		c.drawBitmap(pics[mFrame - 1], mPosX, 40, null);

	}

	protected void handleUIEvents() {
		UIEvent e;
		Queue<UIEvent> vEvents = sch.getEventQueue();
		while ((e = vEvents.poll()) != null) {
			switch (e.getAction()) {
			case UIEvent.PRESSED_ACTION:
				GUI.getInstance().processPressed(e);
				break;
			case UIEvent.SCROLL_PRESSED_ACTION:
				GUI.getInstance().processScrollPressed(e);
				break;
			case UIEvent.UNPRESSED_ACTION:
				GUI.getInstance().processUnPressed(e);
			}

		}
	}

}
