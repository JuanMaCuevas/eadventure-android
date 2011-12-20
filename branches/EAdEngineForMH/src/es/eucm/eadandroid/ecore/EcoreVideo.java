package es.eucm.eadandroid.ecore;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.data.chapter.scenes.Videoscene;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.homeapp.HomeTabActivity;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class EcoreVideo extends Activity {


	
	private Resources resources;
	
	private final static int STATIC_INTEGER_VALUE = 0;

	/**
	 * Videoscene being played
	 */
	private Videoscene videoscene;
	


	/**
	 * activity Handler
	 */
	public Handler ActivityHandler = new Handler() {
		@Override
		
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case ActivityHandlerMessages.GAME_OVER:
				finishapplication(false);
				
				break;
			case ActivityHandlerMessages.LOAD_GAMES:
				finishapplication(true);
				
				break;
			}

		}

	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		videoscene = (Videoscene) Game.getInstance().getCurrentChapterData().getGeneralScene(Game.getInstance().getNextScene().getNextSceneId());

		resources = createResourcesBlock();

		GameThread.getInstance().setHandler(ActivityHandler);
		
		this.play();

	}
	

	public Resources createResourcesBlock() {

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
	
	public void play() {
		
		 String destAddr = ResourceHandler.getInstance().getMediaPath(resources.getAssetPath(Videoscene.RESOURCE_TYPE_VIDEO));	
		 String pack= "com.redirectin.rockplayer.android.unified.lite";
		 Uri uri = Uri.parse(destAddr.toString());
		 Intent i = new Intent("android.intent.action.VIEW");		 
		 i.setPackage(pack);
		 i.setDataAndType(uri, "video/*");		 
		 startActivityForResult(i, STATIC_INTEGER_VALUE);
		 
		 if (Game.getInstance().getFunctionalScene()!=null)
				Game.getInstance().getFunctionalScene().playBackgroundMusic();
	}
	
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
	  super.onActivityResult(requestCode, resultCode, data); 
	  
	  switch(requestCode) { 
	    case (STATIC_INTEGER_VALUE) : { 
	    	this.changeActivity();
	      break; 
	    }
	  
	  } 
	  
	}

	public void changeActivity() {
		Intent i = new Intent(this, ECoreActivity.class);
		i.putExtra("before_video", true);
		this.startActivity(i);
		this.finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 3, 0, "Quit Game").setIcon(android.R.drawable.ic_menu_add);
		menu.add(0, 4, 0, "Load game").setIcon(android.R.drawable.ic_menu_directions);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 3:
			this.finishthread(false);
				return true;
		case 4:		
			this.finishthread(true);
				return true;
		}
		return false;
	}
	
	public void finishthread(boolean load){
		
		  if (GameThread.getInstance()!=null) 
			  GameThread.getInstance().finish(load);
		
		  
	}
	
	private void finishapplication(boolean loadgames){
		if(!loadgames)
		{		Intent i = new Intent(this, HomeTabActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("tabstate", HomeTabActivity.GAMES);
				startActivity(i);
		}
		else 
		{
			Intent i = new Intent(this, HomeTabActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtra("tabstate", HomeTabActivity.LOAD_GAMES);
			startActivity(i);
		}
		
		this.finish();
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();		
		
		
	}
		
	
	@Override
	protected void onResume() {
		super.onResume();

	}
	

}
