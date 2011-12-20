package es.eucm.eadandroid.ecore.control.functionaldata.functionalhighlights;

import android.graphics.Bitmap;



public abstract class FunctionalHighlight {
    
    protected boolean animated;
    
    protected long time = System.currentTimeMillis( );
    
    protected int displacementX = 0;
    
    protected int displacementY = 0;
    
    protected float scale = 1.0f;
    
    protected static final int TIME_CONST = 600;
    
    public abstract Bitmap getHighlightedImage(Bitmap image);
    
    protected Bitmap oldImage;
    
    protected Bitmap newImage;
    
    public int getDisplacementX() {
        return displacementX;
    }
    
    public int getDisplacementY() {
        return displacementY;
    }
    
    public boolean isAnimated() {
        return animated;
    }
    
    protected void calculateDisplacements(int width, int height) {
        long elapsedTime = System.currentTimeMillis( ) - time;
        float temp = ( elapsedTime % (TIME_CONST * 2) );
        if (temp < TIME_CONST / 2)
            temp = -temp; 
        else if (temp < TIME_CONST)
            temp = temp - TIME_CONST;
        else if (temp < TIME_CONST * 1.5f)
            temp = temp - TIME_CONST;
        else
            temp = TIME_CONST*2 - temp;
        scale = 1f + (temp / TIME_CONST) * 0.2f; 
        displacementY = (int) ((height - (height * scale)) / 2);
        displacementX = (int) ((width - (width * scale)) / 2);
    }
}
