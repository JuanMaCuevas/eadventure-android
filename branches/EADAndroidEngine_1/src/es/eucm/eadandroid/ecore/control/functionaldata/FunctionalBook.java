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
 */
package es.eucm.eadandroid.ecore.control.functionaldata;




import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import es.eucm.eadandroid.utils.Dimension;
import es.eucm.eadandroid.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadandroid.common.data.chapter.book.Book;
import es.eucm.eadandroid.common.data.chapter.resources.Asset;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

/**
 * This class manages the eGame "bookscenes".
 */

public abstract class FunctionalBook {

    /**
     * Position of the upper left corner of the next page button
     */
    protected Point nextPage;

    /**
     * Position of the upper left corner of the previous page button
     */
    protected Point previousPage;

    /**
     * Book with the information
     */
    protected Book book;

    /**
     * Current page.
     */
    protected int currentPage;

    /**
     * Image for background
     */
    protected Bitmap background;

    /**
     * Number of pages.
     */
    protected int numPages;

   

    protected FunctionalBook( Book b ) {

        this.book = b;
        // Create necessaries resources to display the book
        Resources r = createResourcesBlock( book );
        // Load images and positions
        loadImages( r );

        // Load arrows position
        this.previousPage = book.getPreviousPagePoint( );
        this.nextPage = book.getNextPagePoint( );

    }

   

    /**
     * Returns the book's data (text and images)
     * 
     * @return the book's data
     */
    public Book getBook( ) {

        return book;
    }

    /**
     * Returns whether the book is in its last page
     * 
     * @return true if the book is in its last page, false otherwise
     */
    public abstract boolean isInLastPage( );

    /**
     * Returns whether the book is in its first page
     * 
     * @return true if the book is in its first page, false otherwise
     */
    public abstract boolean isInFirstPage( );

    /**
     * Changes the current page to the next one
     */
    public abstract void nextPage( );

    /**
     * Changes the current page to the previous one
     */
    public abstract void previousPage( );

    /**
     * Load the necessaries images for displaying the book. This method is
     * pretty much the same as "loadImages" from BookPagePreviewPanel.
     */
    protected void loadImages( Resources r ) {

        background = MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );

    }


    /**
     * Creates the current resource block to be used
     */
    protected Resources createResourcesBlock( Book b ) {

        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < b.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( b.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = b.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if( newResources == null ) {
            newResources = new Resources( );
            newResources.addAsset( new Asset( Book.RESOURCE_TYPE_BACKGROUND, ResourceHandler.DEFAULT_BACKGROUND ) );
        }
        return newResources;
    }

    public void draw( Canvas c ) {
    	c.drawBitmap(background, 0, 0, null);
        //g.drawImage( background, 0, 0, background.getWidth( null ), background.getHeight( null ), null );

    }

}
