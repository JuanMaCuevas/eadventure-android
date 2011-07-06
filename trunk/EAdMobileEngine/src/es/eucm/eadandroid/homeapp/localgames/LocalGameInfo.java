package es.eucm.eadandroid.homeapp.localgames;

import android.graphics.Bitmap;

public class LocalGameInfo {
	
	public static final String TAG = "LocalGameInfo";
	
	/* Repository GameInfo */	
	private String gameTitle = "";
	private String gameDescription = "";
	private Bitmap imageIcon;
	private String gameFileName;
	
	
	public LocalGameInfo(String gameTitle, String gameDescription,
			Bitmap imageIcon, String gameFileName) {
		super();
		this.gameTitle = gameTitle;
		this.gameDescription = gameDescription;
		this.imageIcon = imageIcon;
		this.gameFileName = gameFileName;
	}
	
	

}
