/*******************************************************************************
 * <e-Adventure> Mobile for Android is a port of the <e-Adventure> research project to 	the Android platform.
 *     
 *      Copyright 2009-2012 <e-UCM> research group.
 *    
 *       <e-UCM> is a research group of the Department of Software Engineering
 *            and Artificial Intelligence at the Complutense University of Madrid
 *            (School of Computer Science).
 *    
 *            C Profesor Jose Garcia Santesmases sn,
 *            28040 Madrid (Madrid), Spain.
 *    
 *            For more info please visit:  <http://e-adventure.e-ucm.es/android> or
 *            <http://www.e-ucm.es>
 *    
 *    ****************************************************************************
 * 	This file is part of <e-Adventure> Mobile, version 1.0.
 * 
 * 	Main contributors - Roberto Tornero
 * 
 * 	Former contributors - Alvaro Villoria 
 * 						    Juan Manuel de las Cuevas
 * 						    Guillermo Martín 	
 * 
 *     	You can access a list of all the contributors to <e-Adventure> Mobile at:
 *            	http://e-adventure.e-ucm.es/contributors
 *    
 *    ****************************************************************************
 *         <e-Adventure> Mobile is free software: you can redistribute it and/or modify
 *        it under the terms of the GNU Lesser General Public License as published by
 *        the Free Software Foundation, either version 3 of the License, or
 *        (at your option) any later version.
 *    
 *        <e-Adventure> Mobile is distributed in the hope that it will be useful,
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *        GNU Lesser General Public License for more details.
 *    
 *        See <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
package es.eucm.eadandroid.common.loader.subparsers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadandroid.common.data.animation.Animation;
import es.eucm.eadandroid.common.data.animation.Frame;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;

public class FrameSubParser extends DefaultHandler {

    private Animation animation;

    private Frame frame;

    private Resources currentResources;

    public FrameSubParser( Animation animation ) {

        this.animation = animation;
        frame = new Frame( animation.getImageLoaderFactory( ) );
    }

    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        if( sName.equals( "frame" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "uri" ) )
                    frame.setUri( attrs.getValue( i ) );
                if( attrs.getLocalName( i ).equals( "type" ) ) {
                    if( attrs.getValue( i ).equals( "image" ) )
                        frame.setType( Frame.TYPE_IMAGE );
                    if( attrs.getValue( i ).equals( "video" ) )
                        frame.setType( Frame.TYPE_VIDEO );
                }
                if( attrs.getLocalName( i ).equals( "time" ) ) {
                    frame.setTime( Long.parseLong( attrs.getValue( i ) ) );
                }
                if( attrs.getLocalName( i ).equals( "waitforclick" ) )
                    frame.setWaitforclick( attrs.getValue( i ).equals( "yes" ) );
                if( attrs.getLocalName( i ).equals( "soundUri" ) )
                    frame.setSoundUri( attrs.getValue( i ) );
                if( attrs.getLocalName( i ).equals( "maxSoundTime" ) )
                    frame.setMaxSoundTime( Integer.parseInt( attrs.getValue( i ) ) );
            }
        }

        if( sName.equals( "resources" ) ) {
            currentResources = new Resources( );
            
            for (int i = 0; i < attrs.getLength( ); i++) {
                if (attrs.getLocalName( i ).equals( "name" ))
                    currentResources.setName( attrs.getValue( i ) );
            }

        }

        if( sName.equals( "asset" ) ) {
            String type = "";
            String path = "";

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "type" ) )
                    type = attrs.getValue( i );
                if( attrs.getLocalName( i ).equals( "uri" ) )
                    path = attrs.getValue( i );
            }

            // If the asset is not an special one
            //if( !AssetsController.isAssetSpecial( path ) )
            currentResources.addAsset( type, path );
        }
    }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        if( sName.equals( "frame" ) ) {
            animation.getFrames( ).add( frame );
        }

        if( sName.equals( "resources" ) ) {
            frame.addResources( currentResources );
        }
    }

}
