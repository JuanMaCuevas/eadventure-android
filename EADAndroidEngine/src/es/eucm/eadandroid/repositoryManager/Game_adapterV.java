package es.eucm.eadandroid.repositoryManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class Game_adapterV extends LinearLayout { 
	
	InfoGame aux;
	
	
	
    public Game_adapterV(Context context, 
							InfoGame info ) {
        super( context );
        aux=info;

        this.setOrientation(HORIZONTAL);   
        
        
   
       
        
        
        
        LinearLayout.LayoutParams imagen = 
            new LinearLayout.LayoutParams(50, 50);
        ImageView Iimagen = new ImageView( context );
        Iimagen.setImageBitmap(info.getIcon());
       
		addView( Iimagen, imagen );
		
		
        
        
        
        
        
        LinearLayout texto=new LinearLayout(context);
        
        
        texto.setOrientation(VERTICAL);
        
        
        
        
        
        LinearLayout.LayoutParams titulo = 
            new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titulo.setMargins(1, 1, 1, 1);

        TextView ttitulo = new TextView( context );
        ttitulo.setText( info.getTittle() );
        ttitulo.setTextSize(20f);
        ttitulo.setTextColor(Color.WHITE);
        
        
        
        
       
        
        
       texto. addView( ttitulo, titulo);    
       
       LinearLayout.LayoutParams descripcion = 
            new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        descripcion.setMargins(1, 1, 1, 1);

        TextView tdescripcion = new TextView(context);
        
        String infor=info.getText();
        String infobuena="";
        
        Log.v("la info ",info.getTittle()+" "+infor.length());
        
        if (infor.length()>30)
        {
        	Log.v("fallooooo","la linea es mas larga de lo debido");
        	for(int j=0;j<30;j++)
        	{
        	infobuena=infobuena+infor.charAt(j);
        	}
        	infobuena=infobuena+"...";
        }else infobuena=infor;
        
        
        tdescripcion.setText(infobuena);
        tdescripcion.setTextSize( 14f );
        tdescripcion.setTextColor(Color.WHITE);
        
       
        texto.addView( tdescripcion, descripcion);            

       
        addView(texto);
		
		
    }



	public InfoGame getAux() {
		return aux;
	}

	

}