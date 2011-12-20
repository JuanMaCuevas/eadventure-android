package es.eucm.eadandroid.ecore.control.gamestate;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;
import es.eucm.eadandroid.common.data.chapter.scenes.Cutscene;
import es.eucm.eadandroid.common.data.chapter.scenes.Videoscene;
import es.eucm.eadandroid.ecore.GameThread;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;

public class GameStateVideoscene extends GameState {

	/**
	 * Videoscene being played
	 */
	private Videoscene videoscene;

	/**
	 * Multimedia player
	 */
	private MediaPlayer mediaPlayer = null;

	/**
	 * Stores if the video has stopped
	 */
	private boolean stop;

	/**
	 * We need the surface holder to be able to paint
	 */
	SurfaceHolder holder = null;

	private boolean prefetched;

	/**
	 * Creates a new GameStateVideoscene
	 */
	public GameStateVideoscene() {

		super();
		videoscene = (Videoscene) game.getCurrentChapterData().getGeneralScene(	game.getNextScene().getNextSceneId());

		stop =false;;
		this.prefetched = false;
		
	        
        
        Handler handler=GameThread.getInstance().getHandler();
        Message msg = handler.obtainMessage();
               Bundle b = new Bundle();
			//b.putString("html", text);
			msg.what = ActivityHandlerMessages.VIDEO;
			msg.setData(b);
				msg.sendToTarget();
			stop=false;
		
		
		
		
	/*	

		try {

			this.holder = GUI.getInstance().getVideoSurfaceHolder();

			this.mediaPlayer = new MediaPlayer();

			final Resources resources = createResourcesBlock( );
			
			try {
				mediaPlayer.setDataSource(ResourceHandler.getInstance().getMediaPath(resources.getAssetPath( Videoscene.RESOURCE_TYPE_VIDEO)));
				mediaPlayer.setDisplay(holder);
				mediaPlayer.prepare();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				public void onCompletion(MediaPlayer mp) {
					mediaPlayer.release();
				}
			});

			// TODO creo q no lo vamos a necesitar
			// this.blockingPrefetch( );
			stop = false;

			mediaPlayer.start();
			// mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		} catch (Exception e) {
			loadNextScene();
		}
*/
	}

	private void loadNextScene() {


		if (videoscene.getNext() == Cutscene.ENDCHAPTER){
			game.goToNextChapter();
		}
		else if (videoscene.getNext() == Cutscene.NEWSCENE) {
			Exit exit = new Exit(videoscene.getTargetId());
			exit.setDestinyX(videoscene.getPositionX());
			exit.setDestinyY(videoscene.getPositionY());
			exit.setPostEffects(videoscene.getEffects());
			exit.setTransitionTime(videoscene.getTransitionTime());
			exit.setTransitionType(videoscene.getTransitionType());
			game.setNextScene(exit);
			game.setState(Game.STATE_NEXT_SCENE);
		} else {
			if (game.getFunctionalScene() == null) {
				/*
				 * JOptionPane.showMessageDialog( null, TC.get(
				 * "DesignError.Message" ), TC.get( "DesignError.Title" ),
				 * JOptionPane.ERROR_MESSAGE );
				 */
				// TODO tengo que lanzar un error
				game.goToNextChapter();

			}
			FunctionalEffects.storeAllEffects(new Effects());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop
	 * (long, int)
	 */
	@Override
	public void mainLoop(long elapsedTime, int fps) {

		if (stop) {// || !( mediaPlayer.getDuration( ).getNanoseconds( ) >
					// mediaPlayer.getMediaTime( ).getNanoseconds( ) ) ) {
			loadNextScene();
		}
	}

	/*
	 * @Override public void mouseClicked( MouseEvent e ) {
	 * 
	 * stop = true; }
	 */
/*	public synchronized void blockingPrefetch() {

		if (mediaPlayer != null) {
			// mediaPlayer.prefetch( );
			while (!prefetched) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.out
							.println("Interrupted while waiting on realize...exiting.");
					System.exit(1);
				}
			}
		}
	}*/

	/*
	 * public synchronized void controllerUpdate( ControllerEvent event ) {
	 * 
	 * if( event instanceof RealizeCompleteEvent ) { //realized = true; notify(
	 * ); } else if( event instanceof EndOfMediaEvent ) { //eomReached = true;
	 * loadNextScene( ); } else if( event instanceof StopEvent ) { //stoped =
	 * true; notify( ); } else if( event instanceof PrefetchCompleteEvent ) {
	 * prefetched = true; notify( ); } //(else if (event instanceof ) }
	 */
	
	
	/**
	 * Creates the current resource block to be used
	 */
/*	public Resources createResourcesBlock() {

		// Get the active resources block
		Resources newResources = null;
		for (int i = 0; i < videoscene.getResources().size()
				&& newResources == null; i++)
			if (new FunctionalConditions(videoscene.getResources().get(i)
					.getConditions()).allConditionsOk())
				newResources = videoscene.getResources().get(i);

		// If no resource block is available, create a default, empty one
		if (newResources == null) {
			newResources = new Resources();
		}
		return newResources;
	}
*/
	
	/*
	public void play()
	{
		try {

			this.holder = GUI.getInstance().getVideoSurfaceHolder();

			this.mediaPlayer = new MediaPlayer();

			final Resources resources = createResourcesBlock( );
			
			try {
				mediaPlayer.setDataSource(ResourceHandler.getInstance().getMediaPath(resources.getAssetPath( Videoscene.RESOURCE_TYPE_VIDEO)));
				mediaPlayer.setDisplay(holder);
				mediaPlayer.prepare();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				public void onCompletion(MediaPlayer mp) {
					mediaPlayer.release();
				}
			});

			// TODO creo q no lo vamos a necesitar
			// this.blockingPrefetch( );
			stop = false;

			mediaPlayer.start();
			// mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		} catch (Exception e) {
			loadNextScene();
		}
	}
	*/
	
	public void setstop(boolean stop)
	{
		this.stop=stop;
	}
}
