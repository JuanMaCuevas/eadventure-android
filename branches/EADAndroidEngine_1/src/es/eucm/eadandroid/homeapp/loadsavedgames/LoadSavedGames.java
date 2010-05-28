package es.eucm.eadandroid.homeapp.loadsavedgames;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.ecore.ECoreActivity;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;
import es.eucm.eadandroid.utils.ActivityPipe;

public class LoadSavedGames extends ExpandableListActivity {

	ExpandableListAdapter mAdapter;
	InfoExpandabletable info = null;
	int groupPos;

	public class SavedGamesHandlerMessages {

		public static final int GAMES = 0;
		public static final int NOGAMES = 1;

	}

	public Handler ActivityHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case SavedGamesHandlerMessages.GAMES:
				Bundle b = msg.getData();
				String text = b.getString("loadingsavedgames");
				info = (InfoExpandabletable) ActivityPipe.remove(text);
				createlist();
				break;

			case SavedGamesHandlerMessages.NOGAMES:
				nogames();
				break;
			}
		}
	};

	private void createlist() {

		mAdapter = new MyExpandableListAdapter(this, info);
		setListAdapter(mAdapter);
		registerForContextMenu(getExpandableListView());

	}

	private void nogames() {

		setListAdapter(null);
		// Toast.makeText(this, "no games", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Searchingsavedgames gettingdata = new Searchingsavedgames(
				ActivityHandler);
		gettingdata.start();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		if ((!info.getGroup()[groupPosition].equals("No Games"))
				&& (!info.getChildren()[groupPosition][childPosition]
						.equals("Saved but deleted"))) {
			Intent i = new Intent(this, ECoreActivity.class);
			i.putExtra("AdventureName", info.getGroup()[groupPosition]);
			i.putExtra("restoredGame", Paths.eaddirectory.SAVED_GAMES_PATH
					+ info.getGroup()[groupPosition] + "/"
					+ info.getChildren()[groupPosition][childPosition]);
			i.putExtra("savedgame", true);
			this.startActivity(i);
			return true;
		}

		return false;

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("Options");
		menu.add(0, 0, 0, "Play");
		menu.add(0, 1, 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListContextMenuInfo information = (ExpandableListContextMenuInfo) item
				.getMenuInfo();

		int type = ExpandableListView
				.getPackedPositionType(information.packedPosition);
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {

			groupPos = ExpandableListView
					.getPackedPositionGroup(information.packedPosition);
			int childPos = ExpandableListView
					.getPackedPositionChild(information.packedPosition);

			switch (item.getItemId()) {
			case 0:
				if ((!info.getGroup()[groupPos].equals("No Games"))
						&& (!info.getChildren()[groupPos][childPos]
								.equals("Saved but deleted"))) {
					Intent i = new Intent(this, ECoreActivity.class);
					i.putExtra("AdventureName", info.getGroup()[groupPos]);
					i.putExtra("restoredGame",
							Paths.eaddirectory.SAVED_GAMES_PATH
									+ info.getGroup()[groupPos] + "/"
									+ info.getChildren()[groupPos][childPos]);
					i.putExtra("savedgame", true);
					this.startActivity(i);
				}
				break;
			case 1:
				RepoResourceHandler
						.deleteFile(Paths.eaddirectory.SAVED_GAMES_PATH
								+ info.getGroup()[groupPos] + "/"
								+ info.getChildren()[groupPos][childPos]);
				RepoResourceHandler
						.deleteFile(Paths.eaddirectory.SAVED_GAMES_PATH
								+ info.getGroup()[groupPos] + "/"
								+ info.getChildren()[groupPos][childPos]
								+ ".png");

				Toast.makeText(this, "Saved game suscesfully removed",
						Toast.LENGTH_SHORT).show();

				refresh();
				break;
			}
			return true;
		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			groupPos = ExpandableListView
					.getPackedPositionGroup(information.packedPosition);

			switch (item.getItemId()) {
			case 0:

				Toast
						.makeText(
								this,
								"You have to select a specific game to play not a group of games",
								Toast.LENGTH_SHORT).show();
				break;
			case 1:

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(
						"Are you sure you want to delete all saved games?")
						.setCancelable(false).setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										int a = info.getChildren()[groupPos].length;
										for (int i = 0; i < info.getChildren()[groupPos].length; i++) {
											RepoResourceHandler
													.deleteFile(Paths.eaddirectory.SAVED_GAMES_PATH
															+ info.getGroup()[groupPos]
															+ "/"
															+ info
																	.getChildren()[groupPos][i]);
											RepoResourceHandler
													.deleteFile(Paths.eaddirectory.SAVED_GAMES_PATH
															+ info.getGroup()[groupPos]
															+ "/"
															+ info
																	.getChildren()[groupPos][i]
															+ ".png");

										}
										refresh();
									}

								}).setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
				break;
			}
		}

		return false;
	}

	protected void refresh() {
		Searchingsavedgames gettingdata = new Searchingsavedgames(
				ActivityHandler);
		gettingdata.start();

	}

	/**
	 * A simple adapter which maintains an ArrayList of photo resource Ids. Each
	 * photo is displayed as an image. This adapter supports clearing the list
	 * of photos and adding a new photo.
	 * 
	 */
	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		// Sample data set. children[i] contains the children (String[]) for
		// groups[i].
		private String[] groups;
		private String[][] children;
		private Bitmap[][] icons;

		Context con;

		public MyExpandableListAdapter(Context con, InfoExpandabletable info) {
			super();
			this.con = con;
			children = info.getChildren();
			groups = info.getGroup();
			icons = info.getScreenShots();
		}

		public Object getChild(int groupPosition, int childPosition) {
			return children[groupPosition][childPosition];
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return children[groupPosition].length;
		}

		public TextView getGenericView() {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 64);

			TextView textView = new TextView(con);
			textView.setLayoutParams(lp);
			// Center the text vertically
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			// Set the text starting position
			textView.setPadding(52, 0, 0, 0);
			return textView;

		}

		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TextView textView = getGenericView();
			// textView.setText(getChild(groupPosition,
			// childPosition).toString());
			// return textView;

			View v = convertView;

			if (v == null) {
				LayoutInflater vi = (LayoutInflater) con
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.load_games_activity_listitem, null);
			}

			TextView tt = (TextView) v.findViewById(R.id.toptext);
			TextView bt = (TextView) v.findViewById(R.id.bottomtext);
			ImageView iv = (ImageView) v.findViewById(R.id.icon);

			if (tt != null) {
				tt.setText(getChild(groupPosition, childPosition).toString());
			}

			if (bt != null) {
				bt.setText("");
			}

			if (iv != null) {

				if (icons[groupPosition][childPosition] != null)

					iv.setImageBitmap(icons[groupPosition][childPosition]);
			}

			return v;

		}

		public Object getGroup(int groupPosition) {
			return groups[groupPosition];
		}

		public int getGroupCount() {
			return groups.length;
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView textView = (TextView) getGenericView();
			textView.setTextSize(20f);
			textView.setText(getGroup(groupPosition).toString());
			return textView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public boolean hasStableIds() {
			return true;
		}

	}
}