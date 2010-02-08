package es.eucm.eadventure.prototypes.control.gamestates.scene;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Queue;

import es.eucm.eadventure.prototypes.control.gamestates.GameState;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.TouchListener;
import es.eucm.eadventure.prototypes.gui.GUI;
import es.eucm.eadventure.prototypes.gui.hud.Magnifier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

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
	private Bitmap fondo2;
	private Bitmap[] pics;
	private Bitmap llaves;
	private Bitmap llavesciudad;

	SceneTouchHandler sch;
	
	//prueba de escritura
	String[] texto;

	public SceneGameState() {

		this.sch = new SceneTouchHandler();
		this.touchListener = new SceneTouchListener(sch);

		loadBitmaps();
		
		GUI.getInstance().addBackgroundToDraw(fondo, 0);
		
		GUI.getInstance().addforegroundToDraw(fondo2, 0);

		mFrame = 1;
		mPosX = 0;
		
		texto=new String[3];
		texto[0]="hola1";
		texto[1]="hola2";
		texto[2]="hola3";
		
		
		
		
		

	}

	@Override
	public void mainLoop(long elapsedTime, int fps) {

		handleUIEvents();
		updatePhysics();
		doDraw(GUI.getInstance().getGraphics());

	}

	private void loadBitmaps() {

		pics = new Bitmap[16];

		FileInputStream is;
		BufferedInputStream bis;
	
		for (int i = 0; i < 16; i++) {
			String number = String.format("%02d", i + 1);

			try {
				is = new FileInputStream(new File("/sdcard/Master_Idle_"
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

		Bitmap notScaled = BitmapFactory.decodeFile("/sdcard/clase_01.jpg");
		fondo = Bitmap.createScaledBitmap(notScaled, mCanvasWidth,mCanvasHeight, false);
		
		Bitmap notScaled2 = BitmapFactory.decodeFile("/sdcard/clase_masc_01.PNG");
		fondo2 = Bitmap.createScaledBitmap(notScaled2, mCanvasWidth,mCanvasHeight, false);
		
		
		Bitmap result=Bitmap.createBitmap(fondo2.getWidth(), fondo2.getHeight(), Bitmap.Config.ARGB_4444);
        int aux;
        
        for(int i=0;i<fondo2.getWidth();i++)
        {
     	   for(int j=0;j<fondo2.getHeight();j++)
     	   {
     		   aux=fondo2.getPixel(i, j);
     		   
     		   if(aux==Color.BLACK)
     			   result.setPixel(i, j,fondo.getPixel(i, j));
     		}
        }
        
        
        fondo2 = Bitmap.createScaledBitmap(result,mCanvasWidth,mCanvasHeight, false);
		
		
		
		llaves = BitmapFactory.decodeFile("/sdcard/llaves.png");
		
		llavesciudad = BitmapFactory.decodeFile("/sdcard/llavesciudad.png");


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

	//	c.drawBitmap(fondo, 0, 0, null);


		
		
		GUI.getInstance().addElementToDraw(llaves, 70, 140, 4, 0, null, null);
		
		//GUI.getInstance().addElementToDraw(llavesciudad,60,230, 11, 0,null, null);
		
		GUI.getInstance().addElementToDraw(llavesciudad,260,30, 11, 0,null, null);
		GUI.getInstance().addPlayerToDraw(pics[mFrame - 1], mPosX, 40, 10, 0);
		
		
	//	GUI.getInstance().addTextToDraw(texto, 260, 30, Color.WHITE, Color.BLACK);
		
		//GUI.getInstance().addTextToDraw(texto,140, 30, Color.WHITE, Color.BLACK, Color.GREEN,Color.BLUE, false);
		
	}

	protected void handleUIEvents() {
		MotionEvent e;
		Queue<MotionEvent> vEvents = sch.getEventQueue();
		while ((e = vEvents.poll()) != null) {
			switch (e.getAction()) {
			case TouchListener.CallBack.PRESSED_ACTION:
				GUI.getInstance().showHud();
				GUI.getInstance().updateHudPos((int) e.getX(), (int) e.getY());
				break;
			case TouchListener.CallBack.SCROLL_PRESSED_ACTION:
				GUI.getInstance().showHud();
				GUI.getInstance().updateHudPos((int) e.getX(), (int) e.getY());
				break;
			case TouchListener.CallBack.UNPRESSED_ACTION:
				GUI.getInstance().hideHud();
			}

		}
	}

}
