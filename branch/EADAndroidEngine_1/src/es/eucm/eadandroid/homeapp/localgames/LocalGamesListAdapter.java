package es.eucm.eadandroid.homeapp.localgames;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;

public class LocalGamesListAdapter  extends ArrayAdapter<GameInfo> {

        private ArrayList<GameInfo> items;


        public LocalGamesListAdapter(Context context, int textViewResourceId, ArrayList<GameInfo> items) {
                super(context, textViewResourceId, items);
                this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.local_games_activity_listitem, null);
                }
                GameInfo game = items.get(position);
                if (game != null) {
                        TextView tt = (TextView) v.findViewById(R.id.toptext);
                        TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                        ImageView iv = (ImageView) v.findViewById(R.id.icon);
                        if (tt != null) {
                              tt.setText(game.getGameTitle());                            }
                        if(bt != null){
                              bt.setText(game.getGameDescription());
                        }
                        if(iv != null){
                        	if (game.getImageIcon()!=null)
                             iv.setImageBitmap(game.getImageIcon());
                        }
                                             
                }
                return v;
        }
        
}
	

