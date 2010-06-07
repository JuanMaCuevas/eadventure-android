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
package es.eucm.eadandroid.ecore.control.gamestate;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import android.graphics.Canvas;
import android.util.Log;
import es.eucm.eadandroid.common.data.adaptation.AdaptedState;
import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.OnDownEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.TapEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.GUI;

/**
 * A game main loop during the normal game
 */
public class GameStatePlaying extends GameState {

    /**
     * Constructor.
     */
    public GameStatePlaying( ) {
        super( );
        
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    @Override
    public synchronized void mainLoop( long elapsedTime, int fps ) {
 

    	handleUIEvents();

        // Update the time elapsed in the functional scene and in the GUI
        game.getFunctionalScene( ).update( elapsedTime );
        GUI.getInstance( ).update( elapsedTime );

        // Get the graphics and paint the whole screen in black
        Canvas c = GUI.getInstance( ).getGraphics( );
        // TODO check, though it should give no problems with non-transparent backgrounds
        //g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );

        // Draw the functional scene, and then the GUI
        game.getFunctionalScene( ).draw( );
        
        GUI.getInstance( ).drawScene( c, elapsedTime );
        
        //GUI.getInstance( ).drawHUD( g );
        

        // If there is an adapted state to be executed
        if( game.getAdaptedStateToExecute( ) != null ) {

            // If it has an initial scene, set it
            if( game.getAdaptedStateToExecute( ).getTargetId( ) != null ) {
                // check the scene is in chapter
                if( game.getCurrentChapterData( ).getScenes( ).contains( game.getAdaptedStateToExecute( ).getTargetId( ) ) ) {
                    game.setNextScene( new Exit( game.getAdaptedStateToExecute( ).getTargetId( ) ) );
                    game.setState( Game.STATE_NEXT_SCENE );
                    game.flushEffectsQueue( );
                }
            }

            // Set the flag values
            for( String flag : game.getAdaptedStateToExecute( ).getActivatedFlags( ) )
                if( game.getFlags( ).existFlag( flag ) )
                    game.getFlags( ).activateFlag( flag );
            for( String flag : game.getAdaptedStateToExecute( ).getDeactivatedFlags( ) )
                if( game.getFlags( ).existFlag( flag ) )
                    game.getFlags( ).deactivateFlag( flag );

            // Set the vars
            List<String> adaptedVars = new ArrayList<String>( );
            List<String> adaptedValues = new ArrayList<String>( );
            game.getAdaptedStateToExecute( ).getVarsValues( adaptedVars, adaptedValues );
            for( int i = 0; i < adaptedVars.size( ); i++ ) {
                String varName = adaptedVars.get( i );
                String varValue = adaptedValues.get( i );
                // check if it is a "set value" operation
                if( AdaptedState.isSetValueOp( varValue ) ) {
                    String val = AdaptedState.getSetValueData( varValue );
                    if( val != null )
                        game.getVars( ).setVarValue( varName, Integer.parseInt( val ) );
                }
                // it is "increment" or "decrement" operation, for both of them is necessary to 
                // get the current value of referenced variable
                else {
                    if( game.getVars( ).existVar( varName ) ) {
                        int currentValue = game.getVars( ).getValue( varName );
                        if( AdaptedState.isIncrementOp( varValue ) ) {
                            game.getVars( ).setVarValue( varName, currentValue + 1 );
                        }
                        else if( AdaptedState.isDecrementOp( varValue ) ) {
                            game.getVars( ).setVarValue( varName, currentValue - 1 );
                        }
                    }
                }
            }

        }


        
        // Update the data pending from the flags
        game.updateDataPendingFromState( true );
        

        // Ends the draw process
        GUI.getInstance( ).endDraw( );
  
    }

	private void handleUIEvents() {
		UIEvent e;
		Queue<UIEvent> vEvents = touchListener.getEventQueue();
		while ((e = vEvents.poll()) != null) {
			switch (e.getAction()) {
			case UIEvent.PRESSED_ACTION:
				
				game.getActionManager().setExitCustomized(null);
		        game.getActionManager( ).setElementOver( null );
				if (!GUI.getInstance().processPressed(e))
					game.getActionManager( ).pressed(e);
				break;
			case UIEvent.SCROLL_PRESSED_ACTION:

				
				game.getActionManager().setExitCustomized(null);
		        game.getActionManager( ).setElementOver( null );
				
				if (!GUI.getInstance().processScrollPressed(e)) 
					game.getActionManager( ).pressed(e);
				break;	
			case UIEvent.UNPRESSED_ACTION:

				if (!GUI.getInstance().processUnPressed(e))
					 game.getActionManager( ).unPressed(e);
				break;		
			case UIEvent.FLING_ACTION:

				GUI.getInstance().processFling(e);
				break;	
			case UIEvent.TAP_ACTION: 
				
				if (!GUI.getInstance().processTap(e)) {
					game.getActionManager().tap((TapEvent)e);
				}
				break;
			case UIEvent.ON_DOWN_ACTION:				
				GUI.getInstance().processOnDown((OnDownEvent)e);		
			}

		}
	}
    
}
