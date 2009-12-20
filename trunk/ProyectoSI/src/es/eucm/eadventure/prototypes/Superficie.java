package es.eucm.eadventure.prototypes;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

public class Superficie extends SurfaceView implements SurfaceHolder.Callback {
	
	class SuperficieThread extends Thread {
		
		
        private int mCanvasHeight = 1;      
        private int mCanvasWidth = 1;
        
		private Bitmap fondo;
		
		//private Drawable rocio;
		 /** Handle to the surface manager object we interact with */
        private SurfaceHolder mSurfaceHolder;
        /** Message handler used by thread to interact with TextView */
        private Handler mHandler;
		private boolean active;
		private Rect rec1= new Rect(0,0,0,0);
		private int frame,posX;
		
		private Bitmap[] pics;

		public SuperficieThread(SurfaceHolder holder,Context context,
				Handler handler)  {
			// get handles to some important objects
            mSurfaceHolder = holder;
            mHandler = handler;
            mContext = context;
            
            active=true;
            
            pics = new Bitmap[16];
            
            
            FileInputStream is;
            BufferedInputStream bis;
            int i;
            for (i=0;i<16;i++){
            	String number = String.format("%02d", i+1);


            	try {
            		is = new FileInputStream(new File("/sdcard/pics/Master_Idle_"+number+".png"));     //kitchen_bg.jpg"));
            		bis = new BufferedInputStream(is); 
            		
					pics[i]= BitmapFactory.decodeStream(is);
            		//fondo = BitmapFactory.decodeStream(is);  
            		
            		bis.close(); 
            		is.close();
            	} catch (FileNotFoundException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            		Log.d("thread","Archivo no encontrado");

            	} catch (IOException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            		Log.d("thread","Excepcion IO");
            	}


            }
            try {
        		is = new FileInputStream(new File("/sdcard/pics/kitchen_bg.jpg"));
        		bis = new BufferedInputStream(is); 
        		fondo = BitmapFactory.decodeStream(is);  
        		
        		bis.close(); 
        		is.close();
        	} catch (FileNotFoundException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        		Log.d("thread","Archivo no encontrado");

        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        		Log.d("thread","Excepcion IO");
        	}
        	//inicializo frame a mostrar del personaje y su posicion x inicial
        	frame=1;
        	posX=0;
			//Resources res = context.getResources();
			//fondo = //BitmapFactory.decodeResource(res,R.drawable.rocio_01);
		}
		
		@Override
        public void run() {
            while (true) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        if (active) 
                        doDraw(c);
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

		public void setSurfaceSize(int width, int height) {

            // synchronized to make sure these all change atomically
            synchronized (mSurfaceHolder) {
                mCanvasWidth = width;
                mCanvasHeight = height;

                // don't forget to resize the background image
                //fondo = fondo.createScaledBitmap(fondo, width, height, true);
            }
			
		}
		 private void doDraw(Canvas canvas) {
			 //pinta el fondo reescalando
			 int canvW=canvas.getWidth();
			 int canvH=canvas.getHeight();
			 rec1.set(0,0,canvW,canvH);
			 canvas.drawBitmap(fondo,null,rec1,null);
			 
			 rec1.set(10,10,canvW-10,canvH-10);
			 canvas.drawBitmap(fondo,null,rec1,null);
			 
			 rec1.set(20,20,canvW-20,canvH-20);
			 canvas.drawBitmap(fondo,null,rec1,null);
			 
			 rec1.set(30,30,canvW-30,canvH-30);
			 canvas.drawBitmap(fondo,null,rec1,null);
			 
			 //pinta el mu–eco
			 canvas.drawBitmap(pics[frame-1], posX,40, null);
			 //actualiza siguientes valores mu–eco
			 frame++;
			 if (frame==17) {frame=1;}
			 posX=posX+3;
			 if (posX>canvas.getWidth()) posX=0;
			/* try {
				sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		 }

		public void setRunning(boolean b) {
			// TODO Auto-generated method stub
			active=b;
		}
		
	     /**
         * Pauses the animation.
         */
        public void pause() {
            synchronized (mSurfaceHolder) {
                if (active) active=false;
            }
        }
        
        /**
         * Resumes from a pause.
         */
        public void unpause() {
        	active=true;
        }

		public void saveState(Bundle outState) {
			// TODO Auto-generated method stub
			
			active=true;
			
		}

		

	
		
	}
	 /** The thread that actually draws the animation */
    private SuperficieThread thread;
    
    /** Handle to the application context, used to e.g. fetch Drawables. */
    private Context mContext;
    
    /** Pointer to the text view to display "Paused.." etc. */
   // private TextView mStatusText;
    
	public Superficie(Context context, AttributeSet attrs) {
		super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new SuperficieThread(holder, context, new Handler() /*{
            @Override
            public void handleMessage(Message m) {
               // mStatusText.setVisibility(m.getData().getInt("viz"));
               // mStatusText.setText(m.getData().getString("text"));
            }
        }*/
        );

        setFocusable(true); // make sure we get key events
		// TODO Auto-generated constructor stub*/
	}

    

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
        thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
		
	}
	public SuperficieThread getThread() {
		return thread;
	}

	

}
