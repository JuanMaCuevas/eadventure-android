package es.eucm.eadandroid.homeapp.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import es.eucm.eadandroid.R;

public class PreferencesActivity extends PreferenceActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        
	}

}
