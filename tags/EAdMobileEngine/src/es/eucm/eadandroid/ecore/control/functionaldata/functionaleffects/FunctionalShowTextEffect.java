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
package es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects;


import java.util.Timer;
import java.util.TimerTask;

import es.eucm.eadandroid.common.data.chapter.effects.ShowTextEffect;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.Options;
import es.eucm.eadandroid.ecore.gui.GUI;

/**
 * An effect that shows the given text in current scene.
 */
public class FunctionalShowTextEffect extends FunctionalEffect {

    /**
     * The text that will be shown
     */
    private String[] text;

    /**
     * The time that text will be shown
     */
    private int timeShown;

    /**
     * Boolean to control is effect is running
     */
    private boolean isStillRunning;

    /**
     * The timer which controls the time that text is shown
     */
    private Timer timer;
    
    private TimerTask task;
    
    /**
     * Value to control when the user skip the text
     */
    private boolean skipByUser;

    /**
     * Constructor
     * 
     * @param effect
     */
    FunctionalShowTextEffect( ShowTextEffect effect ) {

        super( effect );
        // split the text if don't fit in the screen
        this.text = GUI.getInstance( ).splitText( effect.getText( ) );

        // obtain the text speed
        float multiplier = 1;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_SLOW )
            multiplier = 1.5f;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_FAST )
            multiplier = 0.8f;

        // calculate the time that text will be shown
        timeShown = (int) ( 300 * effect.getText( ).split( " " ).length * multiplier );
        if( timeShown < (int) ( 1400 * multiplier ) )
            timeShown = (int) ( 1400 * multiplier );

        this.isStillRunning = false;
        this.skipByUser = false;
    }

    @Override
    public boolean isInstantaneous( ) {

        return false;
    }

    @Override
    public boolean isStillRunning( ) {

        return isStillRunning;
    }

    @Override
    public void triggerEffect( ) {

        // FIXME: Convendría cambiar esto para que no se use un timer
    	
    	timer = new Timer();
    	
    	task = new TimerTask() {

			@Override
			public void run() {			
                finish();				
			}
    		
    	};
    	
    	timer.schedule(task, timeShown * 2);    	

        isStillRunning = true;

    }

    public void draw( ) {

        GUI.getInstance( ).addTextToDraw( text, ( (ShowTextEffect) effect ).getX( ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), ( (ShowTextEffect) effect ).getY( ), ( (ShowTextEffect) effect ).getRgbFrontColor( ) , ((ShowTextEffect) effect ).getRgbBorderColor( ) );

    }
    
    private void finish() {
    	
        if (!Game.getInstance( ).getGameDescriptor( ).isKeepShowing( ) || skipByUser){
        	isStillRunning = false;
        	timer.cancel();
        }
        
        else triggerEffect();
        
    }
    
    @Override
    public boolean canSkip( ) {
        return true;
    }

    @Override
    public void skip( ) {
       skipByUser = true;
       finish();
    }
}
