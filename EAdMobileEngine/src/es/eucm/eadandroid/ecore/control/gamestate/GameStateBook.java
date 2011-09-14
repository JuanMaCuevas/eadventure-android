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




import android.graphics.Canvas;
import android.view.MotionEvent;
import es.eucm.eadandroid.common.data.chapter.book.Book;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalBook;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalStyledBook;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalTextBook;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadandroid.ecore.gui.GUI;

/**
 * A game main loop when a "bookscene" is being displayed
 */
public class GameStateBook extends GameState {

    /**
     * Functional book to be displayed
     */
    private FunctionalBook book;

    /**
     * Creates a new GameStateBook
     */
    public GameStateBook( ) {

        super( );
        if( game.getBook( ).getType( ) == Book.TYPE_PARAGRAPHS ) {
            //System.out.println( "[LOG] GameStateBook - Constructor - Paragraphs Book" );
            book = new FunctionalTextBook( game.getBook( ) );
        	//Log.d("GameStateBook","Paragraph books not supported, yet");
        }
        else if( game.getBook( ).getType( ) == Book.TYPE_PAGES ) {
            //System.out.println( "[LOG] GameStateBook - Constructor - Pages Book" );
            book = new FunctionalStyledBook( game.getBook( ) );
        }
        
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    @Override
    public void mainLoop( long elapsedTime, int fps ) {

    	if( book != null && book.getBook( ).getType( ) == Book.TYPE_PARAGRAPHS ) {
    		
    		Canvas c = GUI.getInstance( ).getGraphics( );
            //c.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
            

            ( (FunctionalTextBook) book ).draw( c );

            //c.drawColor( Color.WHITE );
            //g.drawString(Integer.toString( fps ), 780, 14);

            GUI.getInstance( ).endDraw( );


    	}
    	
    	else if( book != null && book.getBook( ).getType( ) == Book.TYPE_PAGES ) {
            
    		Canvas c = GUI.getInstance( ).getGraphics( );
            //c.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
            

            ( (FunctionalStyledBook) book ).draw( c );

            //c.drawColor( Color.WHITE );
            //g.drawString(Integer.toString( fps ), 780, 14);

            GUI.getInstance( ).endDraw( );

           // c.dispose( );
    	}

    	else {
    		book.clearBookBitmap();
            FunctionalEffects.storeAllEffects( new Effects( ) );
            game.setState( Game.STATE_RUN_EFFECTS ); 
    	}
    }
    
    
    /**
	 * Called to process touch screen events.
	 * 
	 * @param event
	 * @return
	 */
    @Override
	public boolean processTouchEvent(MotionEvent event) {
    	
    	if((event.getAction()==MotionEvent.ACTION_UP)) {
		
    	if( book.isInLastPage( ) ) {
           // GUI.getInstance( ).restoreFrame( );
            // this method also change the state to run effects
            FunctionalEffects.storeAllEffects( new Effects( ) );
            game.setState( Game.STATE_RUN_EFFECTS );
        }
        else
            book.nextPage( );
			
    	}
			
			/*
			if( book.isInPreviousPage( e.getX( ), e.getY( ) ) )
                book.previousPage( );

            else if( book.isInNextPage( e.getX( ), e.getY( ) ) ) {

                if( book.isInLastPage( ) ) {
                    GUI.getInstance( ).restoreFrame( );
                    // this method also change the state to run effects
                    FunctionalEffects.storeAllEffects( new Effects( ) );
                    //game.setState( Game.STATE_RUN_EFFECTS );
                }
                else
                    book.nextPage( );
            }*/
			
			
			

		return true;
	}
 
    
}
