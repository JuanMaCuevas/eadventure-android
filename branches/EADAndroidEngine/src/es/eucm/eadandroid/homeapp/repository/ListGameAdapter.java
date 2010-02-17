package es.eucm.eadandroid.homeapp.repository;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;

public class ListGameAdapter extends BaseAdapter {

	private Context context;
	private List<GameInfo> games;

	public ListGameAdapter(Context context, List<GameInfo> games) {
		this.context = context;
		this.games = games;
	}

	public int getCount() {
		return games.size();
	}

	public Object getItem(int position) {
		return games.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		GameInfo game = games.get(position);
		return new ListGameItemView(this.context, game);
	}

}
