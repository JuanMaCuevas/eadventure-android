package es.eucm.eadandroid.homeapp.repository.database;

import java.util.Iterator;
import java.util.Vector;

public class RepositoryDatabase implements Iterable<GameInfo> {
	
	private Vector<GameInfo> repoGames = new Vector<GameInfo>();
	
	
	public Vector<GameInfo> getRepoData() {
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
	
}
