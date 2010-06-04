/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadandroid.ecore.control;

import java.util.Iterator;

import android.content.SharedPreferences;
import android.util.Log;
import es.eucm.eadandroid.ecore.data.GameText;
import es.eucm.eadandroid.homeapp.preferences.PreferencesActivity;

/**
 * This class stores the options of the game
 */
public class Options {
	
	public static final String TAG = "Options";

    /**
     * Number of speeds for the text speed parameter
     */
    public static final int TEXT_NUM_SPEEDS = 3;

    /**
     * Slow speed for text display
     */
    public static final int TEXT_SLOW = 0;

    /**
     * Normal speed for text display
     */
    public static final int TEXT_NORMAL = 1;

    /**
     * Fast speed for text display
     */
    public static final int TEXT_FAST = 2;

    /**
     * Texts for the options of the text speed.
     */
    public static final String[] TEXT_SPEED_PRINT_VALUES = { GameText.TEXT_SLOW, GameText.TEXT_NORMAL, GameText.TEXT_FAST };

    /**
     * Options of the game stored in a Properties structure
     */
    
    private SharedPreferences prefs;
    
    private boolean audioEnabled=true;
    private boolean debugEnabled=true;
    private boolean vibrateEnabled=true;
    
 

    /**
     * Constructor, sets the default options
     */
    public Options(SharedPreferences prefs) {
    	
    	this.prefs = prefs;
    	audioEnabled = prefs.getBoolean(PreferencesActivity.AUDIO_PREF, true);
    	debugEnabled = prefs.getBoolean(PreferencesActivity.DEBUG_PREF, false);
    	vibrateEnabled = prefs.getBoolean(PreferencesActivity.VIBRATE_PREF, true);
    }

   

    /**
     * Returns the state of the music
     * 
     * @return True if the music is active, false otherwise
     */
    public boolean isMusicActive( ) {
    	
        return audioEnabled;
    }
    
    /**
     * Returns the state of the music
     * 
     * @return True if the music is active, false otherwise
     */
    public boolean isDebugActive( ) {
    	
        return debugEnabled;
    }
    
    /**
     * Returns the state of the vibration
     * 
     * @return True if the music is active, false otherwise
     */
    public boolean isVibrationActive( ) {
    	
        return vibrateEnabled;
    }

    /**
     * Returns the state of the effects
     * 
     * @return True if the effects are active, false otherwise
     */
    public boolean isEffectsActive( ) {

      //  return prefs.getProperty( "FunctionalEffects" ).equals( "On" );
  	
    	return true;
    }

    /**
     * Returns the speed of the text
     * 
     * @return TEXT_SLOW, TEXT_NORMAL or TEXT_FAST
     */
    public int getTextSpeed( ) {

//        return Integer.parseInt( prefs.getProperty( "TextSpeed" ) );
    	return TEXT_NORMAL;
    }

    /**
     * Sets the state of the music
     * 
     * @param active
     *            True if the music must be activated, false if deactivated
     */
    public void setMusicActive( boolean active ) {

    	audioEnabled = active;
    }

    /**
     * Sets the state of the effects
     * 
     * @param active
     *            True if the effects must be activated, false if deactivated
     */
    public void setEffectsActive( boolean active ) {

    //    prefs.setProperty( "FunctionalEffects", ( active ? "On" : "Off" ) );
 
    }

    /**
     * Sets the speed of the text
     * 
     * @param textSpeed
     *            TEXT_SLOW, TEXT_NORMAL or TEXT_FAST
     */
    public void setTextSpeed( int textSpeed ) {

    //    prefs.setProperty( "TextSpeed", String.valueOf( textSpeed ) );
    }
}
