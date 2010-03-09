package es.eucm.eadandroid.homeapp.repository.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class RepositoryDatabase implements Iterable<GameInfo> {
	
	private ArrayList<GameInfo> repoGames = new ArrayList<GameInfo>();
	
	
	public ArrayList<GameInfo> getRepoData() {
		return repoGames;		
	}

	public void addGameInfo(GameInfo ginfo){
		repoGames.add(ginfo);
	}
	
	public void removeGameInfo(GameInfo ginfo){
		repoGames.remove(ginfo);
	}
	
	public Iterator<GameInfo> iterator() {
		return repoGames.iterator();
	}

	public void clear() {
		repoGames.clear();	
	}
	
}
