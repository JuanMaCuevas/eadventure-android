package es.ead;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.List;



public class Games_adapter extends BaseAdapter {

    private Context context;
    private List<InfoGame> juegos;

    public Games_adapter(Context context, List<InfoGame> juegos ) { 
        this.context = context;
        this.juegos = juegos;
    }

    public int getCount() {                        
        return juegos.size();
    }

    public Object getItem(int position) {     
        return juegos.get(position);
    }

    public long getItemId(int position) {  
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	InfoGame ex= juegos.get(position);
    	return new Game_adapterV(this.context, ex );
    }

}
