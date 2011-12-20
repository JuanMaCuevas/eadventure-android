/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadandroid.ecore.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.PorterDuff.Mode;
import es.eucm.eadandroid.common.data.chapter.NextScene;



/**
 * This class creates and manages the graphic transitions between scenes of the
 * game
 * 
 * @author Eugenio Marchiori
 */
public class Transition {

    /**
     * Total time of the current transition
     */
    private int totalTime;

    /**
     * Type of the current transition
     */
    private int type;

    /**
     * Elapsed time for the current transition
     */
    private long elapsedTime;

    /**
     * True if the current transition has already started
     */
    private boolean started;

    /**
     * The bufferd image of the transition
     */
    private Bitmap transitionImage;
    
    private Canvas transitionCanvas;

    /**
     * Temporary image for the transition
     */
    private static Bitmap tempImage = Bitmap.createBitmap( GUI.WINDOW_WIDTH,GUI.WINDOW_HEIGHT, Bitmap.Config.ARGB_4444);
    private static Canvas tempCanvas = new Canvas(tempImage);
    
    /** FadeEffect **/
    
    private Xfermode xf;
    private Paint fadeInPaint;
    
    public Transition( int transitionTime, int transitionType ) {

        this.totalTime = transitionTime;
        this.type = transitionType;
        this.elapsedTime = 0;
        this.started = false;
        this.transitionImage = Bitmap.createBitmap( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT,Bitmap.Config.ARGB_4444);
        this.transitionCanvas = new Canvas(transitionImage);
        
        xf = new PorterDuffXfermode(Mode.SRC_OVER);           
        fadeInPaint = new Paint();
        fadeInPaint.setXfermode(xf);
    }

    public boolean hasFinished( long elapsedTime ) {

        this.elapsedTime += elapsedTime;
        if( started && this.elapsedTime > totalTime ) {
            return true;
        }
        return false;
    }

    public boolean hasStarted( ) {

        return started;
    }

    public Canvas getGraphics( ) {

        return transitionCanvas;
    }

    public void start( Canvas g ) {

        started = true;
        g.drawBitmap( transitionImage, 0, 0, null );
        this.elapsedTime = 0;
    }

    public void setImage( Bitmap image ) {

        transitionImage = image;
    }

    public void update( Canvas g ) {

        if( started ) {
            Canvas g2 = tempCanvas;
            g2.save();
            GUI.getInstance( ).drawToGraphics( g2 );
            g2.restore();

            float temp = (float) this.elapsedTime / (float) totalTime;
            if( type == NextScene.RIGHT_TO_LEFT ) {
                float temp2 = GUI.WINDOW_WIDTH * temp;
                g.drawBitmap( transitionImage, (int) ( -temp2 ), 0, null );
                g.drawBitmap( tempImage, (int) ( GUI.WINDOW_WIDTH - temp2 ), 0, null );
            }
            else if( type == NextScene.LEFT_TO_RIGHT ) {
                float temp2 = GUI.WINDOW_WIDTH * temp;
                g.drawBitmap( transitionImage, (int) ( temp2 ), 0, null );
                g.drawBitmap( tempImage, (int) ( temp2 - GUI.WINDOW_WIDTH ), 0, null );
            }
            else if( type == NextScene.TOP_TO_BOTTOM ) {
                float temp3 = GUI.WINDOW_HEIGHT * temp;
                g.drawBitmap( transitionImage, 0, (int) temp3, null );
                g.drawBitmap( tempImage, 0, (int) ( temp3 - GUI.WINDOW_HEIGHT ), null );
            }
            else if( type == NextScene.BOTTOM_TO_TOP ) {
                float temp3 = GUI.WINDOW_HEIGHT * temp;
                g.drawBitmap( transitionImage, 0, (int) -temp3, null );
                g.drawBitmap( tempImage, 0, (int) ( GUI.WINDOW_HEIGHT - temp3 ), null );
            }
            else if( type == NextScene.FADE_IN ) {
                g.drawBitmap( tempImage, 0, 0, null );
       
                fadeInPaint.setAlpha( (int) ((1-temp) * 255 ));
                
                g.drawBitmap( transitionImage, 0, 0, fadeInPaint );
            }
        }
    }

}
