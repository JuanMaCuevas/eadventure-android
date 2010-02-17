package es.eucm.eadandroid.homeapp.repository.database;

import java.io.Serializable;

import android.graphics.Bitmap;

public class GameInfo implements Comparable<GameInfo> , Serializable {

	public static final String TAG = "GameInfoCache";
	private static final long serialVersionUID = 1L;
	
	/* Repository GameInfo */	
	private String gameTitle = "";
	private String gameDescription = "";
	private String eadUrl;
	private Bitmap image;


	public GameInfo(String ti, String desc, String eadURL, Bitmap imageBitmap) {

		gameDescription = desc;
		gameTitle = ti;
		eadUrl = eadURL;
		image = imageBitmap;


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