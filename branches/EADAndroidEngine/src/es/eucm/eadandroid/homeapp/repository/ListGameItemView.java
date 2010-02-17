package es.eucm.eadandroid.homeapp.repository;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;

class ListGameItemView extends LinearLayout {

	GameInfo game;

	public ListGameItemView(Context context, GameInfo info) {
		super(context);
		game = info;

		this.setOrientation(HORIZONTAL);

		LinearLayout.LayoutParams imagen = new LinearLayout.LayoutParams(50, 50);
		ImageView Iimagen = new ImageView(context);
		Iimagen.setImageBitmap(info.getImage());

		addView(Iimagen, imagen);

		LinearLayout texto = new LinearLayout(context);

		texto.setOrientation(VERTICAL);

		LinearLayout.LayoutParams titulo = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titulo.setMargins(1, 1, 1, 1);

		TextView ttitulo = new TextView(context);
		ttitulo.setText(info.getGameTitle());
		ttitulo.setTextSize(20f);
		ttitulo.setTextColor(Color.WHITE);

		texto.addView(ttitulo, titulo);

		LinearLayout.LayoutParams descripcion = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		descripcion.setMargins(1, 1, 1, 1);

		TextView tdescripcion = new TextView(context);

		String infor = info.getGameDescription();
		String infobuena = "";

		Log.v("la info ", info.getGameTitle() + " " + infor.length());

		if (infor.length() > 30) {
			Log.v("fallooooo", "la linea es mas larga de lo debido");
			for (int j = 0; j < 30; j++) {
				infobuena = infobuena + infor.charAt(j);
			}
			infobuena = infobuena + "...";
		} else
			infobuena = infor;

		tdescripcion.setText(infobuena);
		tdescripcion.setTextSize(14f);
		tdescripcion.setTextColor(Color.WHITE);

		texto.addView(tdescripcion, descripcion);

		addView(texto);

	}


}