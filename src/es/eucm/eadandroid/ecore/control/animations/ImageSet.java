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
package es.eucm.eadandroid.ecore.control.animations;

import android.graphics.Bitmap;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

/**
 * This class implements a set of images, that can be used as a set of slides.
 */
public class ImageSet implements Animation {

    /**
     * Set of images.
     */
    protected Bitmap[] imageSet;
    
    /**
     * Set of images.
     */
    protected String[] imagePathSet;

    /**
     * Index of the current frame.
     */
    protected int currentFrameIndex;
    
    /**
     * Bitmap of the current frame.
     */
    protected Bitmap currentFrame;

    /**
     * Constructor.
     */
    public ImageSet( ) {

        imageSet = null;
        currentFrameIndex = 0;
        currentFrame = null;
    }

    /**
     * Adds an image to the animation with the specified duration (time to
     * display the image).
     */
    public void setImages( Bitmap[] imageSet ) {

        this.imageSet = imageSet;
        if( imageSet.length == 0 )
            this.imageSet = getNoAnimationAvailableImageSet( );
    }
    
    /**
     * Adds an image to the animation with the specified duration (time to
     * display the image).
     */
    public void setImagesPath( String[] imagePathSet ) {

        this.imagePathSet = imagePathSet;
        if( imagePathSet.length == 0 )
            this.imageSet = getNoAnimationAvailableImageSet( );
    }

    /**
     * Starts this animation over from the beginning.
     */
    public synchronized void start( ) {

        currentFrameIndex = 0;
    }

    public boolean nextImage( ) {

        boolean noMoreFrames = false;

        currentFrameIndex++;
        
        if( imageSet == null ){
        	if( currentFrameIndex >= imagePathSet.length ) {
         	   currentFrameIndex %= imagePathSet.length;
        	    noMoreFrames = true;
     	   }else currentFrame = null;
		}else if( currentFrameIndex >= imageSet.length ) {
         	   currentFrameIndex %= imageSet.length;
        	    noMoreFrames = true;
     	}
			
		
        return noMoreFrames;
    }

    /**
     * Returns the current image from the set.
     */
    public Bitmap getImage( ) {
    	if( imageSet == null ){
    		if (currentFrame == null){
    			Bitmap temp = MultimediaManager.getInstance().loadImage(imagePathSet[currentFrameIndex], MultimediaManager.IMAGE_SCENE);
    			currentFrame = MultimediaManager.getInstance().getFullscreenImage(temp);
    			temp = null;
    		}
    	}
    	else currentFrame = imageSet[currentFrameIndex];
    	return currentFrame;
    }

    public boolean isPlayingForFirstTime( ) {

        return true;
    }

    public void update( long elapsedTime ) {

    }

    /**
     * Dirty fix: Needed for avoiding null pointer exception when no available
     * resources block
     * 
     * @return
     */
    protected Bitmap[] getNoAnimationAvailableImageSet( ) {

        Bitmap[] array = new Bitmap[ 1 ];
        array[0] = MultimediaManager.getInstance( ).loadImage( ResourceHandler.DEFAULT_SLIDES, MultimediaManager.IMAGE_SCENE );
        return array;
    }
}
