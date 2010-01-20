package es.eucm.eadAndroid.Prototypes.multimedia;



import java.io.IOException;

import android.media.MediaPlayer;
import android.widget.Toast;

public class SoundAndroidMp3 extends Sound {
	/*
	 * There are lot of options in android you can pause the reproduction and
	 * continue it afterwards, this options are not implemented but can be easily added
	 */
	
	
	private String path;
	private MediaPlayer mMediaPlayer;

	public SoundAndroidMp3(String filename,boolean loop) {
		super(loop);
		path=filename;
	
		
	}

@Override
	public void playOnce() {
		// TODO if the path is not correct I should disable the sound and send an error
		
		
        mMediaPlayer = new MediaPlayer();
        try {
        	
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.prepare();
	        mMediaPlayer.start();
	        
	        
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void stopPlaying( ) {
		mMediaPlayer.stop();
		 mMediaPlayer.release();
         mMediaPlayer = null;
		
		
	}
	
	@Override
	public synchronized void finalize() {
		  if (mMediaPlayer != null) {
	            mMediaPlayer.release();
	            mMediaPlayer = null;
		  }
		
	}
	

}
