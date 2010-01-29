
package es.eucm.eadventure.prototypes.control.gamestates.scene;

import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.TouchListener;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;


public class SceneTouchListener implements TouchListener{


    private static final int TOUCH_SLOP_SQUARE = ViewConfiguration.getTouchSlop()
            * ViewConfiguration.getTouchSlop();

    // constants for Message.what used by GestureHandler below
    private static final int LONG_PRESS = 2;

    private final Handler mHandler;
    private final TouchListener.CallBack mListener;

    private boolean mPressedOrAndMoved;
    private boolean mAlwaysInTapRegion;

    private MotionEvent mCurrentDownEvent;
    private MotionEvent mCurrentUpEvent;

    private float mLastMotionY;
    private float mLastMotionX;


    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker mVelocityTracker;

    private class GestureHandler extends Handler {
        GestureHandler() {
            super();
        }

        GestureHandler(Handler handler) {
            super(handler.getLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                
            case LONG_PRESS:
                dispatchLongPress();
                break;

            default:
                throw new RuntimeException("Unknown message " + msg); //never                
            }
        }
    }


    public SceneTouchListener(TouchListener.CallBack listener, Handler handler) {
        mHandler = new GestureHandler(handler);
        mListener = listener;
        init();        
    }


    public SceneTouchListener(TouchListener.CallBack listener) {
        mHandler = new GestureHandler();
        mListener = listener;
        init();
    }

    private void init() {
        if (mListener == null) {
            throw new NullPointerException("OnGestureListener must not be null");
        }
    }

    public boolean processTouchEvent(MotionEvent ev) {
   
    	final long tapTime = ViewConfiguration.getTapTimeout();   	
        final long longpressTime = ViewConfiguration.getLongPressTimeout();       
        
        final int action = ev.getAction();
        final float y = ev.getY();
        final float x = ev.getX();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        boolean handled = false;

        switch (action) {
        case MotionEvent.ACTION_DOWN:
            mLastMotionX = x;
            mLastMotionY = y;
            mCurrentDownEvent = MotionEvent.obtain(ev);
            mAlwaysInTapRegion = true;
            mPressedOrAndMoved = false;
            
            mHandler.removeMessages(LONG_PRESS);
            mHandler.sendEmptyMessageAtTime(LONG_PRESS, mCurrentDownEvent.getDownTime()
                        + tapTime + longpressTime);
        
           // handled = mListener.onDown(ev);
            
            handled = true;
            
            break;

        case MotionEvent.ACTION_MOVE:
        	
        	
            final float scrollX = mLastMotionX - x;
            final float scrollY = mLastMotionY - y;
            if (mAlwaysInTapRegion) {
                final int deltaX = (int) (x - mCurrentDownEvent.getX());
                final int deltaY = (int) (y - mCurrentDownEvent.getY());
                int distance = (deltaX * deltaX) + (deltaY * deltaY);
                if (distance > TOUCH_SLOP_SQUARE) {
                    handled = mListener.onScrollPressed(mCurrentDownEvent, ev, scrollX, scrollY);
                    mLastMotionX = x;
                    mLastMotionY = y;
                    mAlwaysInTapRegion = false;
                    
                    mPressedOrAndMoved=true;
                    
                    mHandler.removeMessages(LONG_PRESS);
                }
            } else if ((Math.abs(scrollX) >= 1) || (Math.abs(scrollY) >= 1)) {
                handled = mListener.onScrollPressed(mCurrentDownEvent, ev, scrollX, scrollY);
                mLastMotionX = x;
                mLastMotionY = y;
            }
            break;

        case MotionEvent.ACTION_UP:
            mCurrentUpEvent = MotionEvent.obtain(ev);
            
            
            if(mPressedOrAndMoved) handled = mListener.onUnPressed(ev);
            
            else  if (mAlwaysInTapRegion) {
                 	handled = mListener.onTap(ev);
                   }
            else {

                    // A fling must travel the minimum tap distance
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000);
                    final float velocityY = velocityTracker.getYVelocity();
                    final float velocityX = velocityTracker.getXVelocity();

                    if ((Math.abs(velocityY) > ViewConfiguration.getMinimumFlingVelocity())
                            || (Math.abs(velocityX) > ViewConfiguration.getMinimumFlingVelocity())){
                        handled = mListener.onFling(mCurrentDownEvent, mCurrentUpEvent, velocityX, velocityY);
                    }
                }


            mVelocityTracker.recycle();
            mVelocityTracker = null;
            mHandler.removeMessages(LONG_PRESS);
            mPressedOrAndMoved = false;
            break;
        case MotionEvent.ACTION_CANCEL:
            mHandler.removeMessages(LONG_PRESS);
            mVelocityTracker.recycle();
            mVelocityTracker = null;
            mPressedOrAndMoved=false;
        }
        return handled;
    }

    private void dispatchLongPress() {
    	
    	mPressedOrAndMoved=true;
        mListener.onPressed(mCurrentDownEvent);
        
    }

}