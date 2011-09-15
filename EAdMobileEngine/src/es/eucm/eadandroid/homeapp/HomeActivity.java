package es.eucm.eadandroid.homeapp;

import com.markupartist.android.widget.ActionBar;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.preferences.PreferencesActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.home_grid);
	    
	    final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	    actionBar.setHomeLogo(R.drawable.logo_home);

	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter());

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	
	        	Intent i = createIntent(HomeActivity.this, WorkspaceActivity.class);
	            
	        	switch(position){
	        		case 0: i.putExtra("Tab", 0);
	        				break;
	        		case 1: i.putExtra("Tab", 1);
    						break;
	        		case 2: i.putExtra("Tab", 2);
    						break;
	        		case 3: i = createIntent(HomeActivity.this, PreferencesActivity.class);
    						break;
	        	}
	        	
	        	startActivity(i);
	        }
	    });
	    
	    ImageView imview = (ImageView) findViewById(R.id.web_image);
	    imview.setImageResource(R.drawable.header);
	    
	    imview.setOnClickListener(new OnClickListener() {
	    	
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i= new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse("http://e-adventure.e-ucm.es/"));
				startActivity(i);
			}
	    });
	    
	    overridePendingTransition(R.anim.fade, R.anim.hold);
	}
	
	@Override
    protected void onStart() {
    	
    	super.onStart();
   		overridePendingTransition(R.anim.fade, R.anim.hold);
    } 
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	this.finish();
	        return true;
	    }
	    else return false;
	}
	
	public static Intent createIntent(Context context, Class<?> c) {
        Intent i = new Intent(context, c);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return i;
    }
	
	public class ImageAdapter extends BaseAdapter {

	    public ImageAdapter() {
	        super();
	    }

	    public int getCount() {
	        return mThumbIds.length;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        
	        View myView = convertView;
	        
	        //if (convertView == null){
	            
	        	//Inflate the layout
	        	LayoutInflater li = getLayoutInflater();
	        	myView = li.inflate(R.layout.home_grid_item, null);
	            
	        	// Add The Image          
	        	ImageView iv = (ImageView)myView.findViewById(R.id.grid_item_image);
	        	iv.setImageResource(mThumbIds[position]);
	            
	        	// Add The Text
	        	TextView tv = (TextView)myView.findViewById(R.id.grid_item_text);
	        	tv.setText(mThumbStrings[position]);
	        //}
	         
	        return myView;

	    }
	    

	    // references to our images
	    private Integer[] mThumbIds = {R.drawable.folder2, R.drawable.diskette,
	            R.drawable.connect_to_network, R.drawable.settings1};
	    
	 // references to our images
	    private String[] mThumbStrings = {"Installed Games", "Saved Games", "Repository", "Preferences"};
	    
	}
}
