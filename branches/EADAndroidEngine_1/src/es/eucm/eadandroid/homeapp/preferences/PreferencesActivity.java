package es.eucm.eadandroid.homeapp.preferences;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

public class PreferencesActivity extends PreferenceActivity {

//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//        addPreferencesFromResource(R.xml.preferences);
//                    
//	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	     setPreferenceScreen(createPreferenceHierarchy());
    }

    private PreferenceScreen createPreferenceHierarchy() {
        // Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
        
        // Inline preferences 
        PreferenceCategory inlinePrefCat = new PreferenceCategory(this);
        inlinePrefCat.setTitle("Engine preferences");
        root.addPreference(inlinePrefCat);
        
        // Toggle preference
        CheckBoxPreference togglePref = new CheckBoxPreference(this);
        togglePref.setKey("enable_audio");
        togglePref.setTitle("Enable audio");
        togglePref.setSummary("Enable or disable audio");
        inlinePrefCat.addPreference(togglePref);
                
        
        // Launch preferences
        PreferenceCategory eadGamesPrefCat = new PreferenceCategory(this);
        eadGamesPrefCat.setTitle("<e-Adventure> games");
        root.addPreference(eadGamesPrefCat);
        
        // Intent preference
        PreferenceScreen intentPref = getPreferenceManager().createPreferenceScreen(this);
        
        Intent intent = new Intent(this, LaunchAndExplorerActivity.class);
        
        intentPref.setIntent(intent);
        
        intentPref.setTitle("Install from SDCard");
        intentPref.setSummary("Install ead games from external storage");
        eadGamesPrefCat.addPreference(intentPref);
        
        // Contact
        PreferenceCategory contactPrefCat = new PreferenceCategory(this);
        contactPrefCat.setTitle("Contact");
        root.addPreference(contactPrefCat);
        
     // Intent preference
        PreferenceScreen websitePref = getPreferenceManager().createPreferenceScreen(this);
        websitePref.setIntent(new Intent().setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse("http://e-adventure.e-ucm.es/")));
        websitePref.setTitle("<e-Adventure> website");
        websitePref.setSummary("Contact us in our website");
        contactPrefCat.addPreference(websitePref);

      
        return root;
    }
	
}
