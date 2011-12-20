package es.eucm.eadandroid.multimedia;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class SoundAndroidMp3 extends Sound {
	/*
	 * There are lot of options in android you can pause the reproduction and
	 * continue it afterwards, this options are not implemented but can be
	 * easily added
	 */

	private String path;
	private MediaPlayer mMediaPlayer;

	public SoundAndroidMp3(String filename, boolean loop) {
		super(loop);
		path = filename;
		mMediaPlayer = null;

	}

	@Override
	public void playOnce() {
		// TODO if the path is not correct I should disable the sound and send
		// an error
		
		try {

			// ResourceHandler.getInstance( ).getResourceAsStreamFromZip(path);
			if (mMediaPlayer == null) {
				mMediaPlayer = new MediaPlayer();
				Log.d("EL PATH ES XXXXXXXXX        ", path + "        XXXXXXXXXXXXXXXXXXX");
				String media = ResourceHandler.getInstance().getMediaPath(path);
				mMediaPlayer.setDataSource(media);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			} else mMediaPlayer.start();

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

	@Override
	public synchronized void stopPlaying() {
		 stop = true;
	}

	@Override
	public synchronized void finalize() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

	}

}
