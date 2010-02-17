package es.eucm.eadandroid.ecore.gui.hud;

import android.graphics.Canvas;

public class AndroidHUD {
	
	 /**
     * Width of the playable area of the screen
     */
    private final int GAME_AREA_WIDTH = 800;

    /**
     * Height of the playable area of the screen
     */
    private final int GAME_AREA_HEIGHT = 600;

    /**
     * Left most point of the response text block
     */
    private static final int RESPONSE_TEXT_X = 10;

    /**
     * Upper most point of the upper response text block
     */
    private static final int UPPER_RESPONSE_TEXT_Y = 10;

    /**
     * Upper most point of the bottom response text block
     */
    private static final int BOTTOM_RESPONSE_TEXT_Y = 480;

    /**
     * Number of response lines to display
     */
    private static final int RESPONSE_TEXT_NUMBER_LINES = 5;
    
    /**
     * Actions menu
     */
    private ActionsMenu actionsMenu;

    /**
     * Inventory
     */
    private InventoryMenu inventoryMenu;

	private boolean showInventoryMenu;

	private boolean showActionsMenu;
    
    /**
     * Function that initializes the HUD class
     */
    public void init( ) {

        showInventoryMenu = false;        
        showActionsMenu = false;
        
    }
    

    public int getGameAreaWidth( ) {

        return GAME_AREA_WIDTH;
    }

    public int getGameAreaHeight( ) {

        return GAME_AREA_HEIGHT;
    }

    public int getResponseTextX( ) {

        return RESPONSE_TEXT_X;
    }
    
    public int getResponseTextY( ) {
/*TODO
        int responseTextY;
        FunctionalPlayer player = Game.getInstance( ).getFunctionalPlayer( );

        // Show the response block in the upper or bottom of the screen, depending on the player's position
        if( player.getY( ) - player.getHeight( ) > GUI.WINDOW_HEIGHT / 2 )
            responseTextY = UPPER_RESPONSE_TEXT_Y;
        else
            responseTextY = BOTTOM_RESPONSE_TEXT_Y;

        return responseTextY;*/
    	return 0;
    }

    public int getResponseTextNumberLines( ) {

        return RESPONSE_TEXT_NUMBER_LINES;
    }
    
    public void newActionSelected( ) {

    }
    
    //// Tratar EVENTOS de ENTRADA ////
    
    /// Procesar EVENTOS ENTRADA /////
    
    /// Pintado /////////
    
    public void doDraw(Canvas c){  	
    }
    
    public void update( long elapsedTime ) {
    }
    
    public void toggleHud( boolean show ) {

    }

}
