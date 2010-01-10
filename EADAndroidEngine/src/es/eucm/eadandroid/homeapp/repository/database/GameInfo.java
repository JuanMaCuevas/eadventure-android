package es.eucm.eadandroid.homeapp.repository.database;

import java.io.Serializable;

import android.graphics.Bitmap;
import es.eucm.eadandroid.res.pathdirectory.FilePaths;

public class GameInfo implements Comparable<GameInfo> , Serializable {

	public static final String TAG = "GameInfoCache";
	private static final long serialVersionUID = 1L;
	
	/* Repository GameInfo */	
	private String gameTitle = "";
	private String gameDescription = "";
	private String eadUrl;
	private Bitmap image;
	
	/* Local repository gameinfo cache */
	
	private static final String REPO_CACHE_PATH = FilePaths.eaddirectory.repodatacache.ABSOLUTE_PATH;	
	private String GAMEINFO_CACHE_PATH ;


	public GameInfo(String ti, String desc, String eadURL, Bitmap imageBitmap) {

		gameDescription = desc;
		gameTitle = ti;
		eadUrl = eadURL;
		image = imageBitmap;
		
	//	GAMEINFO_CACHE_PATH = GameInfoCache.REPO_CACHE_PATH+gameTitle+"/";

	}
	
	

	public int compareTo(GameInfo other) {
		if (this.gameDescription != null)
			return this.gameDescription.compareTo(other.gameDescription);
		else
			throw new IllegalArgumentException();
	}



	public String getGameTitle() {
		return gameTitle;
	}



	public String getGameDescription() {
		return gameDescription;
	}



	public String getEadUrl() {
		return eadUrl;
	}



	public Bitmap getImage() {
		return image;
	}

	
}