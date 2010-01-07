package es.eucm.eadAndroid.Prototipes.multimedia;

/**
 * Author: Guillermo
 * para darme cuenta que lo he tocado
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * This class manages various aspects related to multimedia in eAdventure.
 */
public class MultimediaManager {
	
	//TODO delete this class because his code will be duplicated in other name differently ask alvaro 
	LoadImage load=new LoadImage();
	
	
	/**
     * Cached scene image category
     */
    public static final int IMAGE_SCENE = 0;

    /**
     * Cached menu image category
     */
    public static final int IMAGE_MENU = 1;

    /**
     * Cached player image category
     */
    public static final int IMAGE_PLAYER = 2;
	
	
	private HashMap<String, Bitmap>[] imageCache;

    /**
     * Mirrored images cache
     */
    private HashMap<String, Bitmap>[] mirrorImageCache;

    /**
     * Sounds cache
     */
    private HashMap<Long, Sound> soundCache;

    /**
     * Background music's id
     */
    private long musicSoundId;
    
    /**
     * Instance for the singleton
     */
    private static MultimediaManager instance = new MultimediaManager( );
	
   
    //TODO for animation
    //private HashMap<String, Animation> animationCache;
	
    /**
     * Returns the MultimediaManager instance. Notice MultimediaManager is a
     * singleton class.
     * 
     * @return the MultimediaManager singleton instance
     */
    public static MultimediaManager getInstance( ) {

        return instance;
    }
    
    /**
     * Empty constructor
     */
    @SuppressWarnings ( "unchecked")
    private MultimediaManager( ) {

        imageCache = new HashMap[ 3 ];
        for( int i = 0; i < 3; i++ )
            imageCache[i] = new HashMap<String, Bitmap>( );
        mirrorImageCache = new HashMap[ 3 ];
        for( int i = 0; i < 3; i++ )
            mirrorImageCache[i] = new HashMap<String, Bitmap>( );

        soundCache = new HashMap<Long, Sound>( );

      //  animationCache = new HashMap<String, Animation>( );

        musicSoundId = -1;
    }
    
    
    /**
     * Returns an Image for imagePath. If the image file does not exists, a
     * FileNotFoundException is thrown.
     * 
     * @param imagePath
     * @param category
     *            image category for caching
     * @return an Image for imagePath.
     */
    public Bitmap loadImage( String bitmapPath, int category ) {
    	Bitmap image = imageCache[category].get( bitmapPath );	
    	
    	if( image == null ) {
            // Load the image and store it in cache
    		//TODO delete LoadImage of the multimedia package because it will be duplicated
    		image=load.dameimagen(bitmapPath);    		
    		//TODO scale
            //image = getScaledImage( ResourceHandler.getInstance( ).getResourceAsImage( imagePath ), 1, 1 );
            if( image != null )
                imageCache[category].put( bitmapPath, image );
        }
        return image;
    	
    }
    
    /**
     * Returns an Image for imagePath. If the image file does not exists, a
     * FileNotFoundException is thrown.
     * 
     * @param imagePath
     *            Image path
     * @param category
     *            Category for the image
     * @return an Image for imagePath.
     */
    public Bitmap loadImageFromZip( String imagePath, int category ) {
    	//TODO ask how to extract from a zip
    	return null;
    }    
    
    
    /**
     * Returns an Image for imagePath after mirroring it. If the image file does
     * not exists, a FileNotFoundException is thrown.
     * 
     * @param imagePath
     *            Image path
     * @param category
     *            Category for the image
     * @return an Image for imagePath.
     */
    public Bitmap loadMirroredImageFromZip( String imagePath, int category ) {

        Bitmap image = mirrorImageCache[category].get( imagePath );
        // If the image is in cache, don't load it
        if( image == null ) {
            // Load the image and store it in cache
        	
        	image=load.dameimagen(imagePath); 
        	Matrix temp1=new Matrix();
        	temp1.preScale(-1.0f,1.0f);
        	image = Bitmap.createBitmap(image , 0, 0,image.getWidth(),image.getHeight(), temp1, false);
        	//TODO scale image
           // image = getScaledImage( loadImageFromZip( imagePath, category ), -1, 1 );
            if( image != null ) {
                mirrorImageCache[category].put( imagePath, image );
            }
        }
        return image;
    }

    /**
     * Clear the image cache of the given category
     * 
     * @param category
     *            Image category to clear
     */
    public void flushImagePool( int category ) {

        imageCache[category].clear( );
        mirrorImageCache[category].clear( );
    }

    /**
     * Returns a scaled image of the given image. The new width is x times the
     * old width. The new height is y times the old height.
     * 
     * @param image
     *            the image to be scaled
     * @param x
     *            width scale factor
     * @param y 
     *            height scale factor
     * @return a scaled image of the given image.
     */
    
    //TODO we will not use it 
    /*
    private Image getScaledImage( Image image, float x, float y ) {

        Image newImage = null;

        if( image != null ) {

            if( x != 1.0f || y != 1.0f ) {
                // set up the transform
                AffineTransform transform = new AffineTransform( );
                transform.scale( x, y );
                transform.translate( ( x - 1 ) * image.getWidth( null ) / 2, ( y - 1 ) * image.getHeight( null ) / 2 );

                // create a transparent (not translucent) image
                newImage = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( image.getWidth( null ), image.getHeight( null ), Transparency.BITMASK );

                // draw the transformed image
                Graphics2D g = (Graphics2D) newImage.getGraphics( );

                g.drawImage( image, transform, null );
                g.dispose( );
            }
            else
                return image;
        }
        return newImage;
    }
*/
    /**
     * Returns a scaled image that fits in the game screen.
     * 
     * @param image
     *            the image to be scaled.
     * @return a scaled image that fits in the game screen.
     */
    //TODO we will not use it
    /*
    private Image getFullscreenImage( Image image ) {

        // set up the transform
        AffineTransform transform = new AffineTransform( );
        transform.scale( GUI.WINDOW_WIDTH / (double) image.getWidth( null ), GUI.WINDOW_HEIGHT / (double) image.getHeight( null ) );

        // create a transparent (not translucent) image
        Image newImage = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, Transparency.BITMASK );

        // draw the transformed image
        Graphics2D g = (Graphics2D) newImage.getGraphics( );
        g.drawImage( image, transform, null );
        g.dispose( );

        return newImage;
    }
*/
    /**
     * Returns an Id of the sound of type soundType for the file in soundPath,
     * and sets whether it has to be played or not in a loop.
     * 
     * @param soundType
     *            the type of the sound to be created
     * @param soundPath
     *            the path to the sound to be created
     * @param loop
     *            whether or not the sound must be played in a loop
     * @return an Id that represents the sound with the given configuration.
     */
    public long loadSound( int soundType, String soundPath, boolean loop ) {
// to start with we will only use mp3 sound
        Sound sound = null;
        long soundId = 1;
               
        sound=new SoundAndroidMp3(soundPath, loop);
        if( sound != null ) {
            soundCache.put( new Long( sound.getId( ) ), sound );
            soundId = sound.getId( );
        }
        return soundId;
    }

    /**
     * Returns an Id of the sound for the file in soundPath, and sets whether it
     * has to be played or not in a loop. The sound's type is guessed from its
     * soundPath's extension.
     * 
     * @param soundPath
     *            the path to the sound to be created
     * @param loop
     *            whether or not the sound must be played in a loop
     * @return an Id that represents the sound with the given configuration.
     */
    
    
    public long loadSound( String soundPath, boolean loop ) {
/*
 *
        String type = soundPath.substring( soundPath.lastIndexOf( "." ) + 1 ).toLowerCase( );
        int soundType = -1;
        
        if( type.equals( "mp3" ) )
            soundType = MP3;
        else if( type.equals( "midi" ) )
            soundType = MIDI;
        else if( type.equals( "mid" ) )
            soundType = MIDI;
        return loadSound( soundType, soundPath, loop );
        */
    	return loadSound( 1, soundPath, loop );
    }
    

    /**
     * Returns an Id of the music of type musicType for the file in musicPath,
     * and sets whether it has to be played or not in a loop.
     * 
     * @param soundType
     *            the type of the music to be created
     * @param soundPath
     *            the path to the music to be created
     * @param loop
     *            whether or not the music must be played in a loop
     * @return an Id that represents the music with the given configuration.
     */
    public long loadMusic( int musicType, String musicPath, boolean loop ) {

        musicSoundId = loadSound( musicType, musicPath, loop );
        return musicSoundId;
    }

    /**
     * Returns an Id of the music for the file in musicPath, and sets whether it
     * has to be played or not in a loop. The sound's type is guessed from its
     * musicPath's extension.
     * 
     * @param soundPath
     *            the path to the music to be created
     * @param loop
     *            whether or not the music must be played in a loop
     * @return an Id that represents the music with the given configuration.
     */
    public long loadMusic( String musicPath, boolean loop ) {

        musicSoundId = loadSound( musicPath, loop );
        return musicSoundId;
    }

    /**
     * Plays the sound from the cache that have soundId as id
     * 
     * @param soundId
     *            Id of the sound to be played
     */
    public void startPlaying( long soundId ) {

        if( soundCache.containsKey( soundId ) ) {
            Sound sound = soundCache.get( soundId );
            if( sound != null )
                sound.startPlaying( );
        }
    }

    /**
     * Stops the sound from the cache that have soundId as id
     * 
     * @param soundId
     *            Id of the sound to be stopped
     */
    public void stopPlaying( long soundId ) {

        if( soundCache.containsKey( soundId ) ) {
            Sound sound = soundCache.get( soundId );
            if( sound != null )
                sound.stopPlaying( );
        }
    }

    public void stopPlayingMusic( ) {

        Collection<Sound> sounds = soundCache.values( );
        for( Sound sound : sounds ) {
            if( sound.getId( ) == musicSoundId ) {
                sound.stopPlaying( );
            }
        }
    }

    public void stopPlayingInmediately( long soundId ) {

        if( soundCache.containsKey( soundId ) ) {
            Sound sound = soundCache.get( soundId );
            if( sound != null ) {
                sound.stopPlaying( );
                sound.finalize( );
            }
        }

    }

    /**
     * Given the soundId, it returns whether the sound is playing or not
     * 
     * @param soundId
     *            long The soundId
     * @return true if the sound is playing
     */
    public boolean isPlaying( long soundId ) {

        boolean playing = false;
        if( soundCache.containsKey( soundId ) ) {
            Sound sound = soundCache.get( soundId );
            if( sound != null )
                playing = sound.isPlaying( );
        }
        return playing;
    }

    /**
     * Update the status of all sounds cache and if they have finished, they are
     * removed from the cache
     * 
     * @throws InterruptedException
     */
    public void update( ) throws InterruptedException {

        Collection<Sound> sounds = soundCache.values( );
        ArrayList<Sound> soundsToRemove = new ArrayList<Sound>( );
        for( Sound sound : sounds ) {
            if( sound.getId( ) != musicSoundId && !sound.isAlive( ) ) {
                soundsToRemove.add( sound );
            }
        }
        for( int i = 0; i < soundsToRemove.size( ); i++ ) {
            Sound sound = soundsToRemove.get( i );
            sound.join( );
            sounds.remove( sound );
        }
    }

    /**
     * Stops and deletes all sounds currently in the cache, except the music
     */
    public void stopAllSounds( ) {

        Collection<Sound> sounds = soundCache.values( );
        ArrayList<Sound> soundsToRemove = new ArrayList<Sound>( );
        for( Sound sound : sounds ) {
            if( sound.getId( ) != musicSoundId ) {
                soundsToRemove.add( sound );
            }
        }
        for( int i = 0; i < soundsToRemove.size( ); i++ ) {
            Sound sound = soundsToRemove.get( i );
            try {
                sound.join( );
            }
            catch( InterruptedException e ) {
            }
            sounds.remove( sound );
        }
    }

    /**
     * Stops and deletes all sounds currently in the cache even the music
     */
    public void deleteSounds( ) {

        Collection<Sound> sounds = soundCache.values( );
        for( Sound sound : sounds ) {
            if( sound.getId( ) != musicSoundId ) {
                sound.stopPlaying( );
                try {
                    sound.join( );
                }
                catch( InterruptedException e ) {
                }
            }
        }
        sounds.clear( );
    }

    /**
     * Returns a animation from a path.
     * <p>
     * The animation can be generated from an eaa describing the animation or
     * with frames animationPath_xy.jpg, with xy from 01 to the last existing
     * file with that format (the extension can also be .png).
     * <p>
     * For example, loadAnimation( "path" ) will return an animation with frames
     * path_01.jpg, path_02.jpg, path_03.jpg, if path_04.jpg doesn't exists.
     * 
     * @param animationPath
     *            base path to the animation frames
     * @param mirror
     *            whether or not the frames must be mirrored
     * @param category
     *            Category of the animation
     * @return an Animation with frames animationPath_xy.jpg
     */
    
    /*TODO animation later
    public Animation loadAnimation( String animationPath, boolean mirror, int category ) {

        Animation temp = animationCache.get( animationPath + ( mirror ? "t" : "f" ) );
        if( temp != null )
            return temp;

        if( animationPath != null && animationPath.endsWith( ".eaa" ) ) {
            FrameAnimation animation = new FrameAnimation( Loader.loadAnimation( ResourceHandler.getInstance( ), animationPath, new EngineImageLoader() ) );
            animation.setMirror( mirror );
            temp = animation;
        }
        else {
            int i = 1;
            List<Image> frames = new ArrayList<Image>( );
            Image currentFrame = null;
            boolean end = false;
            while( !end ) {
                if( mirror )
                    currentFrame = loadMirroredImageFromZip( animationPath + "_" + leadingZeros( i ) + ".png", category );
                else
                    currentFrame = loadImageFromZip( animationPath + "_" + leadingZeros( i ) + ".png", category );

                if( currentFrame != null ) {
                    frames.add( currentFrame );
                    i++;
                }
                else
                    end = true;
            }
            ImageAnimation animation = new ImageAnimation( );
            animation.setImages( frames.toArray( new Image[] {} ) );
            temp = animation;
        }
        animationCache.put( animationPath + ( mirror ? "t" : "f" ), temp );
        return temp;
    }
*/
    /**
     * Returns a animation with frames animationPath_xy.jpg, with xy from 01 to
     * the last existing file with that format (also the extension can be .png).
     * <p>
     * For example, loadAnimation( "path" ) will return an animation with frames
     * path_01.jpg, path_02.jpg, path_03.jpg, if path_04.jpg doesn't exists.
     * 
     * @param slidesPath
     *            base path to the animation frames
     * @param category
     *            Category of the animation
     * @return an Animation with frames animationPath_xy.jpg
     */
    //TODO animation later
    /*
    public Animation loadSlides( String slidesPath, int category ) {

        ImageSet imageSet = null;
        if( slidesPath.endsWith( ".eaa" ) ) {
            FrameAnimation animation = new FrameAnimation( Loader.loadAnimation( ResourceHandler.getInstance( ), slidesPath, new EngineImageLoader()) );
            animation.setFullscreen( true );
            return animation;
        }
        else {
            int i = 1;
            List<Image> slides = new ArrayList<Image>( );
            Image currentSlide = null;
            boolean end = false;

            while( !end ) {
                currentSlide = loadImageFromZip( slidesPath + "_" + leadingZeros( i ) + ".jpg", category );

                if( currentSlide != null ) {
                    slides.add( getFullscreenImage( currentSlide ) );
                    i++;
                }
                else
                    end = true;
            }

            imageSet = new ImageSet( );
            imageSet.setImages( slides.toArray( new Image[] {} ) );
        }

        return imageSet;
    }
    
    */

    /**
     * @param n
     *            number to convert to a String
     * @return a 2 character string with value n
     */
    private String leadingZeros( int n ) {

        String s;
        if( n < 10 )
            s = "0";
        else
            s = "";
        s = s + n;
        return s;
    }
/*
    public void flushAnimationPool( ) {

        animationCache.clear( );
    }
	*/
	
	

}
