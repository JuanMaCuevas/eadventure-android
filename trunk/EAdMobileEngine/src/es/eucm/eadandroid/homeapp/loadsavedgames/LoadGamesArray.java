package es.eucm.eadandroid.homeapp.loadsavedgames;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class LoadGamesArray {	

	private ArrayList<InfoLoadGames> savedGames;
	
	public LoadGamesArray(){
		
		savedGames = new ArrayList<InfoLoadGames>();
	}
	
	public ArrayList<InfoLoadGames> getSavedGames() {
		return savedGames;
	}


	public void setSavedGames(ArrayList<InfoLoadGames> savedGames) {
		this.savedGames = savedGames;
	}
	
	public void addGame(String game, String saved, Bitmap bmp){
		
		InfoLoadGames info = new InfoLoadGames();
		info.setGame(game);
		info.setSaved(saved);
		info.setScreenShot(bmp);
		savedGames.add(info);
	}


	public class InfoLoadGames {

		private String game;
		private String saved;
		private Bitmap screenShot;

		public String getGame() {
			return game;
		}

		public void setGame(String game) {
			this.game = game;
		}

		public String getSaved() {
			return saved;
		}

		public void setSaved(String saved) {
			this.saved = saved;
		}

		public void setScreenShot(Bitmap screenShot) {
			this.screenShot = screenShot;
		}

		public Bitmap getScreenShot() {
			return screenShot;
		}

	}
}
