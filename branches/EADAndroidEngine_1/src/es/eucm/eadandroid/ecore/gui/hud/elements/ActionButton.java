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
package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Bitmap;
import es.eucm.eadandroid.common.data.adventure.DescriptorData;
import es.eucm.eadandroid.common.data.chapter.CustomAction;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.gui.TC;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class ActionButton {

    /**
     * Width of an action button
     */
    public static final int ACTIONBUTTON_WIDTH = (int) ( 80 * GUI.DISPLAY_DENSITY_SCALE );

    /**
     * Height of an action button
     */
    public static final int ACTIONBUTTON_HEIGHT = (int) ( 48 * GUI.DISPLAY_DENSITY_SCALE );

    /**
     * Constant that represent the hand button
     */
    public static final int HAND_BUTTON = 0;

    /**
     * Constant that represents the eye button
     */
    public static final int EYE_BUTTON = 1;

    /**
     * Constant that represents the mouth button
     */
    public static final int MOUTH_BUTTON = 2;
    
    /**
     * Constant that represents the drag button
     */
    public static final int DRAG_BUTTON = 3;
    
    /**
     * Constant that represents the custom button
     */
    public static final int CUSTOM_BUTTON = 4;
    

    /**
     * Image of the button in it's normal state
     */
    private Bitmap buttonNormal;

    /**
     * Image of the button when it's pressed
     */
    private Bitmap buttonPressed;

    /**
     * Image of the button when it has the mouse over
     */
    private Bitmap buttonOver;

  
    /**
     * Name of the action represented by the button
     */
    private String actionName;
    
    /**
     * Custom action of the button
     */
    private CustomAction customAction;

    /**
     * The type of the button
     */
    private int type;

    /**
     * Construct a button form it's type
     * 
     * @param type
     *            the type of the button
     */
    public ActionButton( int type ) {

        switch( type ) {
            case HAND_BUTTON:
                loadButtonImages( DescriptorData.USE_GRAB_BUTTON, "btnHand" );
                actionName = TC.get( "ActionButton.GrabGiveUse" );
                break;
            case EYE_BUTTON:
                loadButtonImages( DescriptorData.EXAMINE_BUTTON, "btnEye" );
                actionName = TC.get( "ActionButton.Examine" );
                break;
            case MOUTH_BUTTON:
                loadButtonImages( DescriptorData.TALK_BUTTON, "btnMouth" );
                actionName = TC.get( "ActionButton.Talk" );
                break;
            case DRAG_BUTTON:
                loadButtonImages( DescriptorData.USE_GRAB_BUTTON, "btnHand" );
                actionName = TC.get( "ActionButton.Drag" );
                break;
        }
        this.type = type;
    }

    private void loadButtonImages( String type, String name ) {

        DescriptorData descriptor = Game.getInstance( ).getGameDescriptor( );
        String customNormalPath = null;
        String customHighlightedPath = null;
        String customPressedPath = null;

        customNormalPath = descriptor.getButtonPath( type, DescriptorData.NORMAL_BUTTON );
        customHighlightedPath = descriptor.getButtonPath( type, DescriptorData.HIGHLIGHTED_BUTTON );
        customPressedPath = descriptor.getButtonPath( type, DescriptorData.PRESSED_BUTTON );

        buttonNormal = loadImage( customNormalPath, Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/"  + name + ".png" );
        buttonOver = loadImage( customHighlightedPath, Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/"  + name + "Highlighted.png" );
        buttonPressed = loadImage( customPressedPath, Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/"  + name + "Pressed.png" );
        
        
    }

    private Bitmap loadImage( String customPath, String defaultPath ) {

        Bitmap temp = null;
        if( customPath == null )
            temp = MultimediaManager.getInstance( ).loadScaledImage( defaultPath,(int)GUI.DISPLAY_DENSITY_SCALE,(int)GUI.DISPLAY_DENSITY_SCALE,MultimediaManager.IMAGE_MENU );
        else {
            try {
                temp = MultimediaManager.getInstance( ).loadImageFromZip( customPath, MultimediaManager.IMAGE_MENU );
            }
            catch( Exception e ) {
                temp = MultimediaManager.getInstance( ).loadImage( Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/btnError.png", MultimediaManager.IMAGE_MENU );
            }
        }
        return temp;
    }

    /**
     * Construct a button from a custom action
     * 
     * @param action
     *            the custom action
     */
    public ActionButton( CustomAction action ) {

        actionName = action.getName( );
        customAction = action;

        Resources resources = null;
        for( int i = 0; i < action.getResources( ).size( ) && resources == null; i++ )
            if( new FunctionalConditions( action.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                resources = action.getResources( ).get( i );

        buttonNormal = loadImage( resources.getAssetPath( "buttonNormal" ), Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/btnError.png" );
        buttonOver = loadImage( resources.getAssetPath( "buttonOver" ), Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/btnError.png" );
        buttonPressed = loadImage( resources.getAssetPath( "buttonPressed" ), Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/btnError.png" );

        buttonNormal = scaleButton( buttonNormal );
        buttonOver = scaleButton( buttonOver );
        buttonPressed = scaleButton( buttonPressed );

        this.type = CUSTOM_BUTTON;
    }

    /**
     * Scale an image of a button
     * 
     * @param button
     *            the image to scale
     * @return the scaled image
     */
    private Bitmap scaleButton( Bitmap button ) {

        if( button.getWidth() > ActionButtons.MAX_BUTTON_WIDTH )
            button = Bitmap.createScaledBitmap(button, ActionButtons.MAX_BUTTON_WIDTH, button.getHeight(),false);
        if( button.getWidth() < ActionButtons.MIN_BUTTON_WIDTH )
            button = Bitmap.createScaledBitmap(button, ActionButtons.MIN_BUTTON_WIDTH, button.getHeight(),false);
        if( button.getHeight() > ActionButtons.MAX_BUTTON_HEIGHT )
            button = Bitmap.createScaledBitmap(button,button.getWidth(), ActionButtons.MAX_BUTTON_HEIGHT, false);
        if( button.getHeight() < ActionButtons.MIN_BUTTON_HEIGHT )
            button = Bitmap.createScaledBitmap(button,button.getWidth(), ActionButtons.MIN_BUTTON_HEIGHT, false);
        return button;
    }

    /**
     * @return the buttonNormal
     */
    public Bitmap getButtonNormal( ) {

        return buttonNormal;
    }

    /**
     * @return the buttonPressed
     */
    public Bitmap getButtonPressed( ) {

        return buttonPressed;
    }

    /**
     * @return the buttonOver
     */
    public Bitmap getButtonOver( ) {

        return buttonOver;
    }


    public String getName( ) {

        return actionName;
    }

    public int getType( ) {

        return type;
    }
    
    public void setName( String string ) {

        actionName = string;
    }
    
    public CustomAction getCustomAction( ) {

        return customAction;
    }

}
