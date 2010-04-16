package es.eucm.eadandroid.homeapp.loadsavedgames;



import es.eucm.eadandroid.ecore.ECoreActivity;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;



/**
 * Demonstrates expandable lists using a custom {@link ExpandableListAdapter}
 * from {@link BaseExpandableListAdapter}.
 */
public class LoadSavedGames extends ExpandableListActivity  {

    ExpandableListAdapter mAdapter;
    InfoExpandabletable info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        
        
        RepoResourceHandler handler=new RepoResourceHandler();
              
         info=new InfoExpandabletable();
        handler.getexpandablelist(info);
        
        mAdapter = new MyExpandableListAdapter(this,info);
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
    
     
        
       
        
    }
    @Override
    public boolean onChildClick (ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
    {
    	Log.d("vamos", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    	Log.d("padre", info.getGroup()[groupPosition]);
    	Log.d("padre", info.getChildren()[groupPosition][childPosition]);
    	
    	if((!info.getGroup()[groupPosition].equals("No Games"))&&(!info.getChildren()[groupPosition][childPosition].equals("Saved but deleted")))
    	{Intent i = new Intent(this, ECoreActivity.class);
		i.putExtra("AdventureName",info.getGroup()[groupPosition] );
		i.putExtra("restoredGame",Paths.eaddirectory.SAVED_GAMES_PATH+info.getGroup()[groupPosition]+"/"+info.getChildren()[groupPosition][childPosition] );
		i.putExtra("savedgame", true);
		

		this.startActivity(i);
    	}
    	
		return false;
    	
    }
/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sample menu");
        menu.add(0, 0, 0,"option");
    }
  */  
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();
        Log.d(title, "XXXXXXXXXXXXXXXXXXXXXX");
        
        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); 
            Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        
        return false;
    }

    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids. 
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        private String[] groups;
        private String[][] children;;
        
        Context con;
        
        public MyExpandableListAdapter(Context con, InfoExpandabletable info) {
			super();
			this.con=con;
			children=info.getChildren();
			groups=info.getGroup();
			// TODO Auto-generated constructor stub
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
        
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
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

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            TextView textView = getGenericView();
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