package es.eucm.eadandroid.homeapp.preferences;

import java.io.File;
import java.net.URI;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.HomeTabActivity;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.ProgressNotifier.ProgressMessage;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class LaunchAndExplorerActivity extends PreferenceActivity {
	
	
	private static String TAG = "AndExplorerActivity";
	private static int PICK_REQUEST_CODE = 0;
	
	private String path_from = null;
		
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final boolean andExplorerAvailable = isIntentAvailable(this,
				Intent.ACTION_PICK);

		if (andExplorerAvailable) {
		
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		Uri startDir = Uri.fromFile(new File("/sdcard"));
		// Files and directories
		intent.setDataAndType(startDir,
				"vnd.android.cursor.dir/lysesoft.andexplorer.file");
		// Optional filtering on file extension.
		intent.putExtra("browser_filter_extension_whitelist", "*.ead");
		// Title
		intent.putExtra("explorer_title", "Select a file");
		// Optional colors
		intent.putExtra("browser_title_background_color", "440000AA");
		intent.putExtra("browser_title_foreground_color", "FFFFFFFF");
		intent.putExtra("browser_list_background_color", "66000000");
		// Optional font scale
		intent.putExtra("browser_list_fontscale", "140%");
		// Optional 0=simple list, 1 = list with filename and size, 2 = list
		// with filename, size and date.
		intent.putExtra("browser_list_layout", "1");
		startActivityForResult(intent, PICK_REQUEST_CODE);
		}

		else this.showAndNavigatorAppNotInstalled();

	}

	public static boolean isIntentAvailable(Context context, String action ) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		Uri startDir = Uri.fromFile(new File("/sdcard"));  
		// Files and directories  
		intent.setDataAndType(startDir, "vnd.android.cursor.dir/lysesoft.andexplorer.file");  
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size()   > 0;
	}

	public void showAndNavigatorAppNotInstalled() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder
				.setMessage(
						"You need to install AndExplorer in order to install ead games from SDCard")
				.setCancelable(false).setPositiveButton("Install",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent i = new Intent(
										Intent.ACTION_VIEW,
										Uri.parse("market://search?q=pname:lysesoft.andexplorer"));
								startActivity(i);
								finish();
							}
						}).setNegativeButton("Quit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								finish();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


		Uri uri = null;
		String path = null;

		if (requestCode == PICK_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				uri = intent.getData();
				String type = intent.getType();
				Log.i(TAG, "Pick completed: " + uri + " " + type);
				if (uri != null) {
					path = uri.toString();
					if (path.toLowerCase().startsWith("file://") && path.toLowerCase().endsWith(".ead")) {
						path = (new File(URI.create(path))).getAbsolutePath();
						Log.i(TAG, "Pick completed: " + path);
						if (path==null)
							Toast.makeText(this, "You have not selected a game to install", Toast.LENGTH_LONG);
					}
					else Toast.makeText(this, "You have not selected a valid eadventure game file",Toast.LENGTH_LONG);

				}
			} else
				Log.i(TAG, "Back from pick with cancel status");
		}
		 
		if (path!=null) {
			installEadGame(path);
		}
		else this.finish();
 
	}
	
	private String getPathFrom() {
		return path_from;
	}
	
	private void installEadGame(String path_from) {
		
		this.path_from = path_from;
		
		this.showDialog(DIALOG_INSTALL_ID);
		

		Thread t = new Thread(new Runnable() {
			public void run()
			{	
				
				String path_from = getPathFrom();
				int last = path_from.lastIndexOf("/");
				String gameFileName = path_from.substring(last + 1);
				path_from= path_from.substring(0, last+1);
				Log.d(TAG,"PathFrom: " + path_from);
				Log.d(TAG,"FileName: " + gameFileName);
				RepoResourceHandler.unzip(path_from,Paths.eaddirectory.GAMES_PATH,gameFileName,false);
				dismissDialog(DIALOG_INSTALL_ID);
				
				startGamesTabActivity();
				finish();
			}
		});
		t.start();
					
	}
	
	private void startGamesTabActivity() {
		Intent i = new Intent(this, HomeTabActivity.class);
		i.putExtra("tabstate", HomeTabActivity.GAMES);
		i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
//		overridePendingTransition(R.anim.fade, R.anim.hold);
	}

	
	
	static final int DIALOG_INSTALL_ID = 0;

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_INSTALL_ID:
			ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setCancelable(false);		
			progressDialog.setTitle("Please wait");
			progressDialog.setIcon(R.drawable.dialog_icon);
			progressDialog.setMessage("Installing game...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			dialog = progressDialog;			
			break;
		default:
			dialog = null;
		}
		return dialog;
	}

}
