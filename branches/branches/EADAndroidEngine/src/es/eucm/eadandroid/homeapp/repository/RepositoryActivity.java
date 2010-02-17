package es.eucm.eadandroid.homeapp.repository;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.repository.connection.RepositoryServices;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.homeapp.repository.database.RepositoryDatabase;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker.ProgressNotifier.ProgressMessage;
import es.eucm.eadandroid.utils.ActivityPipe;

public class RepositoryActivity extends ListActivity {
	
	static final int DIALOG_UPDATING_REPO_ID = 0;
	static final int DIALOG_ERROR_ID = 1;

	private RepositoryDatabase db;
	private RepositoryServices rs;
	
	private  ProgressDialog pd;

	private Handler RAHandler = new Handler() {
		@Override
		/*   * Called when a message is sent to Engines Handler Queue */
		public void handleMessage(Message msg) {

			String m = null;
			int p;

			switch (msg.what) {

			case ProgressMessage.PROGRESS_PERCENTAGE:

				pd.show();
				m = msg.getData().getString("msg");
				p = msg.getData().getInt("ptg");
				Log.d("progressDialog", String.valueOf(p));
				pd.setProgress(p);
				pd.setMessage(m);
				break;

			case ProgressMessage.PROGRESS_FINISHED:

				m = msg.getData().getString("msg");
				pd.setProgress(100);
				pd.setMessage(m);
				databaseUpdated();
				pd.dismiss();
				break;

			case ProgressMessage.PROGRESS_ERROR:

				m = msg.getData().getString("msg");
				pd.setProgress(100);
				pd.setMessage(m);
				pd.dismiss();

				break;

			}

		}

	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		this.showDialog(RepositoryActivity.DIALOG_UPDATING_REPO_ID);
			
		db = new RepositoryDatabase();
		rs = new RepositoryServices();

		rs.updateDatabase(this, RAHandler, db);

	}

	private void databaseUpdated() {

		setContentView(R.layout.repository_activity);

		ListGameAdapter listAd = new ListGameAdapter(this, db.getRepoData());
		
		setListAdapter(listAd);
		
	//	this.getListView().setOnCreateContextMenuListener(this);

	}

	protected void onListItemClick(ListView l, View v, int position, long id) {

		GameInfo selectedGame = (GameInfo) this.getListView()
				.getItemAtPosition(position);
	
		String key = ActivityPipe.add(selectedGame);

		Intent i = new Intent(this, DetailedGameActivity.class);
		i.putExtra(GameInfo.TAG,key);

		startActivity(i);

	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_UPDATING_REPO_ID:
			dialog = new ProgressDialog(this);
			break;
		case DIALOG_ERROR_ID:
			
			break;
		default:
			dialog = null;
		}
		return dialog;
	}
	
	protected void onPrepareDialog (int id, Dialog dialog) {
		switch (id) {
		case DIALOG_UPDATING_REPO_ID:
			((ProgressDialog) dialog).setProgressStyle(ProgressDialog.STYLE_SPINNER);
			((ProgressDialog) dialog).setMessage("Updating Repository...");
			((ProgressDialog) dialog).setCancelable(false);
			pd = (ProgressDialog) dialog;
			break;
		case DIALOG_ERROR_ID:
			
			break;
		}
		
	}

}
