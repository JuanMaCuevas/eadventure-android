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

import android.graphics.Canvas;
import es.eucm.eadandroid.common.data.chapter.effects.PlayAnimationEffect;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.animations.Animation;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;

/**
 * An effect that plays a sound
 */
public class FunctionalPlayAnimationEffect extends FunctionalEffect {

    private Animation animation;

    /**
     * Creates a new PlaySoundEffect
     * 
     * @param background
     *            whether to play the sound in background
     * @param path
     *            path to the sound file
     */
    public FunctionalPlayAnimationEffect( PlayAnimationEffect effect ) {

        super( effect );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    @Override
    public void triggerEffect( ) {

        animation = MultimediaManager.getInstance( ).loadAnimation( ( (PlayAnimationEffect) effect ).getPath( ), false, MultimediaManager.IMAGE_SCENE );
        animation.start( );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    @Override
    public boolean isInstantaneous( ) {

        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.ecore.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    @Override
    public boolean isStillRunning( ) {

        return animation.isPlayingForFirstTime( );
    }

    public void update( long elapsedTime ) {

        animation.update( elapsedTime );
    }

    public void draw( Canvas g) {

        GUI.getInstance( ).addElementToDraw( animation.getImage( ), Math.round( ( (PlayAnimationEffect) effect ).getX( ) - ( animation.getImage( ).getWidth() / 2 ) ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), Math.round( ( (PlayAnimationEffect) effect ).getY( ) - ( animation.getImage( ).getHeight() / 2 ) ), Math.round( ( (PlayAnimationEffect) effect ).getY( ) ), Math.round( ( (PlayAnimationEffect) effect ).getY( ) ), null, null );
    }

}
