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
package es.eucm.eadandroid.ecore.control.functionaldata;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import es.eucm.eadandroid.common.auxiliar.ReportDialog;
import es.eucm.eadandroid.common.data.chapter.book.BookPage;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class FunctionalBookPage {

    private static final long serialVersionUID = 1L;

    private BookPage bookPage;

    private boolean isValid;

    private Bitmap background, currentArrowLeft, currentArrowRight;
    
    private Point previousPage, nextPage;

    private Bitmap image;
    
    private FunctionalStyledBook fBook;

   // private BookEditorPane editorPane;

    public FunctionalBookPage( Bitmap background ) {

        this.background = background;
    }

    public FunctionalBookPage( BookPage bookPage, FunctionalStyledBook fBook, Bitmap background, Point previousPage, Point nextPage,  boolean listenHyperLinks ) {

        
   //     editorPane = new BookEditorPane( bookPage );
        isValid = true;
        this.bookPage = bookPage;
        this.fBook = fBook;
        this.background = background;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        
    /*   FunctionalBookMouseListener bookListener = new FunctionalBookMouseListener( );
        this.addMouseListener( bookListener );
        this.addMouseMotionListener( bookListener );*/
        
        
        /*
        if( bookPage.getType( ) == BookPage.TYPE_URL ) {
            URL url = null;
            try {
                url = new URL( bookPage.getUri( ) );
                url.openStream( ).close( );
            }
            catch( Exception e ) {
                isValid = false;
                //System.out.println( "[LOG] FunctionalBookPage - Constructor - Error creating URL "+bookPage.getUri( ) );
            }

            try {
                if( isValid ) {
                    editorPane.setPage( url );
                    editorPane.setEditable( false );
                    if( listenHyperLinks )
                        editorPane.addHyperlinkListener( new BookHyperlinkListener( ) );
                    if( !( editorPane.getEditorKit( ) instanceof HTMLEditorKit ) && !( editorPane.getEditorKit( ) instanceof RTFEditorKit ) ) {
                        isValid = false;
                        //System.out.println( "[LOG] FunctionalBookPage - Constructor - Type of page not valid "+bookPage.getUri( ) );
                    }
                    else {
                        //System.out.println( "[LOG] FunctionalBookPage - Constructor - Page OK "+bookPage.getUri( ) );
                    }

                }
            }
            catch( IOException e ) {
            }

        }
        else if( bookPage.getType( ) == BookPage.TYPE_RESOURCE ) {
            String uri = bookPage.getUri( );
            String ext = uri.substring( uri.lastIndexOf( '.' ) + 1, uri.length( ) ).toLowerCase( );
            if( ext.equals( "html" ) || ext.equals( "htm" ) || ext.equals( "rtf" ) ) {

                //Read the text
                StringBuffer textBuffer = new StringBuffer( );
                InputStream is = ResourceHandler.getInstance( ).getResourceAsStreamFromZip( uri );//null;
                try {
                    int c;
                    while( ( c = is.read( ) ) != -1 ) {
                        textBuffer.append( (char) c );
                    }
                }
                catch( IOException e ) {
                    isValid = false;
                }
                finally {
                    if( is != null ) {
                        try {
                            is.close( );
                        }
                        catch( IOException e ) {
                            isValid = false;
                        }
                    }
                }

                //Set the proper content type
                if( ext.equals( "html" ) || ext.equals( "htm" ) ) {
                    editorPane.setContentType( "text/html" );
                    ProcessHTML processor = new ProcessHTML( textBuffer.toString( ) );
                    String htmlProcessed = processor.start( );
                    editorPane.setText( htmlProcessed );
                }
                else {
                    editorPane.setContentType( "text/rtf" );
                    editorPane.setText( textBuffer.toString( ) );
                }
                isValid = true;

            }

        }
        else*/
        if( bookPage.getType( ) == BookPage.TYPE_IMAGE ) {
            image = MultimediaManager.getInstance( ).loadImage( bookPage.getUri( ), MultimediaManager.IMAGE_SCENE );
        }

        
    }




    public void paint( Canvas c ) {
    	
        if( image != null )
            c.drawBitmap(image, 0, 0,null);    
    }

    /**
     * @return the bookPage
     */
    public BookPage getBookPage( ) {

        return bookPage;
    }

    /**
     * @param bookPage
     *            the bookPage to set
     */
    public void setBookPage( BookPage bookPage ) {

        this.bookPage = bookPage;
    }

  

    /**
     * @param isValid
     *            the isValid to set
     */
    public void setValid( boolean isValid ) {

        this.isValid = isValid;
    }

	public boolean isValid() {
		// TODO Auto-generated method stub
		return isValid;
	}


       

      
    

   

}
