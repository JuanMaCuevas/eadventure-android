package es.eucm.eadandroid.homeapp.repository;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.repository.connection.RepositoryServices;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker.ProgressNotifier.ProgressMessage;
import es.eucm.eadandroid.utils.ActivityPipe;

public class DetailedGameActivity extends Activity {
	
	GameInfo game;	
	ProgressDialog pd ;
	
	private Handler DGAHandler = new Handler() {
		@Override
		/* Called when a message is sent to Engines Handler Queue */
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
				pd.dismiss();
				gameDownloaded();				
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
//
//		setContentView(R.layout.detailed_game_activity);
//		
//		pd = new ProgressDialog(this);
//		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//		pd.setMessage("Downloading game...");
//		pd.setCancelable(false);
//
//		String key = (String) this.getIntent().getExtras().get(GameInfo.TAG);
//
//		this.game = (GameInfo) ActivityPipe.remove(key);
//
//		ImageView img;
//		img = (ImageView) findViewById(R.id.Viewdetallado);
//
//		img.setImageBitmap(game.getImage());
//
//		TextView title;
//		title = (TextView) findViewById(R.id.titulodetallado);
//		title.setText(game.getGameTitle());
//
//		TextView details;
//		details = (TextView) findViewById(R.id.textodetallado);
//		details.setText(game.getGameDescription());
//
//		Button button = (Button) findViewById(R.id.botondetallado);
//		
//		button.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				buttonClicked();
//			}
//
//		});

	}

	private void buttonClicked() {
		
		RepositoryServices rs = new RepositoryServices() ;
		rs.downloadGame(this, DGAHandler, game);
		
	}
	
	private void gameDownloaded() {
		
		
	}
	
	
	@Override
	protected void onStop () {
		super.onStop();
		
		ActivityPipe.add(this.game);
		
	}

}
