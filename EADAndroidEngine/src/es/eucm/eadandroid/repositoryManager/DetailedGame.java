package es.eucm.eadandroid.repositoryManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import es.eucm.eadandroid.R;

public class DetailedGame extends Activity {
	
	
	String[] datos;
	
	Activity aux;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		aux=this;

		setContentView(R.layout.juego_detalle);

		
		datos=(String[]) this.getIntent().getExtras().get("datos");
		
		for(int i=0;i<datos.length;i++)
	      {
	    	  Log.v("ficheros",datos[i]);
	      }
	
	
	
		ImageView img;
        img = (ImageView) findViewById(R.id.Viewdetallado);
        
        
        LoadImage s=new LoadImage();
        
        img.setImageBitmap(s.dameimagen(datos[0]));
        /*
        String filepath = "/sdcard/";
		File imagefile = new File(filepath + datos[0]);
		FileInputStream fis;
		try {
			fis = new FileInputStream(imagefile);
			Bitmap bitmap= BitmapFactory.decodeStream(fis);
	        img.setImageBitmap(bitmap);
	        
	        fis.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        */
        
        
        TextView txt1;
        txt1 = (TextView) findViewById(R.id.titulodetallado);
        txt1.setText(datos[1]);   
       
               
        TextView txt2;
        txt2 = (TextView) findViewById(R.id.textodetallado);
        txt2.setText(datos[2]); 
        

        Button button = (Button)findViewById(R.id.botondetallado);
        button.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent b = new Intent(aux,DownloadingGame.class);
				b.putExtra("datos", datos);
		 		startActivity(b);
			} 
			
        
        });

		
	}
	
		

}
