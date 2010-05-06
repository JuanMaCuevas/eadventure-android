package es.eucm.eadandroid.homeapp.repository;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.HomeTabActivity;
import es.eucm.eadandroid.homeapp.localgames.LocalGamesListAdapter;
import es.eucm.eadandroid.homeapp.repository.connection.RepositoryServices;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.homeapp.repository.database.RepositoryDatabase;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker.ProgressNotifier.ProgressMessage;

public class RepositoryActivity extends ListActivity {

	static final int DIALOG_UPDATING_REPO_ID = 0;
	static final int DIALOG_ERROR_ID = 1;

	private RepositoryDatabase db;
	private RepositoryServices rs;

	private ProgressDialog pd;

	private LocalGamesListAdapter m_adapter;

	ViewFlipper mFlipper;
	
	private GameInfo selectedGame = null;
		
	ProgressDialog progressDialog;

	private Handler RAHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			String m = null;
			int perc;
			
			ProgressDialog p = null;
			
			if (pd.isShowing())
				p = pd;
			else if (progressDialog.isShowing())
				p = progressDialog;
				 
				 
			switch (msg.what) {

			case ProgressMessage.PROGRESS_PERCENTAGE:

				p.setIndeterminate(false);
				p.show();
				m = msg.getData().getString("msg");
				perc = msg.getData().getInt("ptg");
				p.setProgress(perc);
				p.setMessage(m);
				break;

			case ProgressMessage.PROGRESS_FINISHED:

				p.setIndeterminate(false);
				p.setProgress(100);
				databaseUpdated();
				p.dismiss();
				break;

			case ProgressMessage.PROGRESS_ERROR:

				m = msg.getData().getString("msg");
				p.setProgress(0);
				p.setMessage(m);
				p.dismiss();

				break;

			case ProgressMessage.INDETERMINATE:

				p.setIndeterminate(true);
				m = msg.getData().getString("msg");
				p.setMessage(m);


				break;
				
			case ProgressMessage.FINAL_FINISH:

				p.setIndeterminate(false);
				p.dismiss();
				
				goToLocalGames();
			
				break;
				
			}

		}

	};
	
	private void goToLocalGames() {
		
		Intent i = new Intent(this, HomeTabActivity.class);
		i.putExtra("tabstate", HomeTabActivity.GAMES);
		startActivity(i);
		
		this.finish();
		
	}


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		pd = ProgressDialog.show(this, "Please wait...", "Retrieving data ...",
				false);
		

		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);

		db = new RepositoryDatabase();
		rs = new RepositoryServices();
		
		rs.updateDatabase(this, RAHandler, db);

	}
	
		
	private void databaseUpdated() {
	
		setLayout();
		
		m_adapter.notifyDataSetChanged();

	}
	
	private void setLayout() {

		setContentView(R.layout.repository_activity);

		mFlipper = ((ViewFlipper) this
				.findViewById(R.id.repository_activity_flipper));
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.zoom_enter));
		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.zoom_exit));



		m_adapter = new LocalGamesListAdapter(this,
				R.layout.local_games_actvitiy_listitem, db.getRepoData());

		setListAdapter(m_adapter);

		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(100);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);

		getListView().setLayoutAnimation(controller);
		getListView().setTextFilterEnabled(true);
		
		Button button = (Button) this.findViewById(R.id.detailed_game_download_button);
		
		button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				downloadGame();
			}
			
		});
	}
	
	private void downloadGame() {
		

		progressDialog.setTitle("Please wait");
		progressDialog.setMessage("Starting donwload");
		progressDialog.show();
		
		RepositoryServices rs = new RepositoryServices() ;
		rs.downloadGame(this, RAHandler, selectedGame);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {

		GameInfo selectedGame = (GameInfo) this.getListView()
				.getItemAtPosition(position);
		
		TextView title = (TextView)this.findViewById(R.id.detailed_game_title);
		TextView description = (TextView)this.findViewById(R.id.detailed_game_description);
		ImageView image = (ImageView)this.findViewById(R.id.detailed_game_image_icon);
		
		title.setText(selectedGame.getGameTitle());
		description.setText(selectedGame.getGameDescription());
		if (selectedGame.getImageIcon()!=null)		
			image.setImageBitmap(selectedGame.getImageIcon());
		else image.setImageDrawable(this.getResources().getDrawable(R.drawable.icon));
				
		mFlipper.showNext();
		
		this.selectedGame = selectedGame;

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK && mFlipper.getDisplayedChild()==1) {
	    	mFlipper.showPrevious();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.title_icon, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		db.clear();
		pd = ProgressDialog.show(this, "Please wait...", "Retrieving data ...",
				true);
		rs.updateDatabase(this, RAHandler, db);
		
		return true;

	}
	
	

}
