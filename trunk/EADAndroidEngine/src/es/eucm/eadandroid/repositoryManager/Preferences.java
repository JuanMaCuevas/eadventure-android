package es.eucm.eadandroid.repositoryManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import es.eucm.eadandroid.R;

public class Preferences extends Activity {
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.preferencias);
		
		
		
		TextView txt1;
        String str1 = "cambiar repositorio a: ";
        txt1 = (TextView) findViewById(R.id.texto2);
        txt1.setText(str1);
        
        
        
        Button button = (Button)findViewById(R.id.repo);
        button.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				//hacer accion
				
				
			} 
			
        
        });
	}

}
