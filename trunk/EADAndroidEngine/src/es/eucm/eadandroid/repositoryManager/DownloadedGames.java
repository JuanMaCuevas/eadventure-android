package es.eucm.eadandroid.repositoryManager;


import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import es.eucm.eadandroid.R;

public class DownloadedGames extends ListActivity{
	
	
	
	
	
	
	String[] d;
		
		public void onCreate(Bundle icicle) {
	        super.onCreate(icicle);
	        //setContentView(R.layout.main);
	        setContentView(R.layout.main_op);
	        
	        
	        
	        
	        String[] juegos_descargados=(String[]) this.getIntent().getExtras().get("pru");
	        
	        
	        if (juegos_descargados.length==0)
	        {
	        	 d=new String[1];
	        	 d[0]="no hay juegos descargados";
	        	 
	        }else{
	        	
	        	
	        	d=new String[juegos_descargados.length];
	        	
	        	
	        	for(int i=0;i<juegos_descargados.length;i++)
	        	{
	        		d[i]=juegos_descargados[i];
	        	}
	        	
	        }
	        
	       this.getListView().setOnCreateContextMenuListener(this);
	        
	       setListAdapter(new ArrayAdapter<String>(this,
			android.R.layout.simple_list_item_1, d));
			getListView().setTextFilterEnabled(true);
	        
	    }
		
		protected void onListItemClick(ListView l, View v, int position, long id) {
			
			
			String seleccionado = this.getListView().getItemAtPosition(position).toString();
			
			Log.v("juego","el juego seleccionado para jugar es "+seleccionado);
		}

}
