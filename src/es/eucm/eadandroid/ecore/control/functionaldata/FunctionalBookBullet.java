package es.eucm.eadandroid.ecore.control.functionaldata;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import es.eucm.eadandroid.common.data.chapter.book.BookParagraph;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.pathdirectory.Paths;


/**
 * This is a item of a enumeration of text that can be put in a book scene
 */
public class FunctionalBookBullet extends FunctionalBookParagraph {

    /**
     * The bullet book
     */
    private BookParagraph bookBullet;

    /**
     * The text of the book
     */
    private ArrayList<String> textLines;

    /**
     * Image of the bullet
     */
    private Bitmap imgBullet;
    
    private Paint p;

    /**
     * Creates a new FunctionalBookBullet
     * 
     * @param bullet
     *            the bullet book to be rendered
     */
    public FunctionalBookBullet( BookParagraph bullet ) {

        this.bookBullet = bullet;
        this.p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(18);
        imgBullet = MultimediaManager.getInstance( ).loadImage(Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/bullet.png" , MultimediaManager.IMAGE_SCENE );
        this.init();
    }

    /**
     * Init the bullet book. Take the text of the book and reformat it to be
     * ready to be render with the correct format and size.
     */
    private void init( ) {

        textLines = new ArrayList<String>( );

        //Get the text of the book
        String text = bookBullet.getContent( );
        String word = "";
        String line = "";
        
        //while there is still text to be process
        while( !text.equals( "" ) ) {
            //get the first char
            char c = text.charAt( 0 );
            //and the rest of the text (without that char)
            text = text.substring( 1 );
            //If the first char is a new line
            if( c == '\n' ) {
                // get the width of the line and the word
                //if its width size don't go out of the line text width
                if( p.measureText(line + " " + word) < FunctionalTextBook.TEXT_WIDTH_BULLET ) {
                    //finish the line with the current word
                    line = line + word;
                    //add the line to the text of the bullet book
                    textLines.add( line );
                    //empy line and word
                    word = "";
                    line = "";
                }
                else {
                    textLines.add( line );
                    textLines.add( word.substring( 1 ) );
                    word = "";
                    line = "";
                }
            }
            //if its a white space
            else if( Character.isWhitespace( c ) ) {
                //get the width of the line and the word
                //if its width size don't go out of the line text width
            	if( p.measureText(line + " " + word) < FunctionalTextBook.TEXT_WIDTH_BULLET ) {
                    //add the word to the line
                    line = line + word;
                    word = " ";
                }
                //if it goes out
                else {
                    //and the line to the text of the bullet book
                    textLines.add( line );
                    //the line is now the word
                    line = word.substring( 1 ) + " ";
                    word = "";
                }
            }
            //else we add it to the current word
            else {
                if( p.measureText(line + word + c) < FunctionalTextBook.TEXT_WIDTH_BULLET )
                    word = word + c;
                else {
                    if( line != "" )
                        textLines.add( line );
                    line = "";
                    if( p.measureText( word + c ) < FunctionalTextBook.TEXT_WIDTH_BULLET )
                        word = word + c;
                    else {
                        textLines.add( word );
                        word = "" + c;
                    }
                }
            }
        }

        //All the text has been process except the last line and last word
        if( p.measureText(line + " " + word) < FunctionalTextBook.TEXT_WIDTH_BULLET ) {
            line = line + word;
        }
        else {
            textLines.add( line );
            line = word.substring( 1 );
        }
        textLines.add( line );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#canBeSplitted()
     */
    @Override
    public boolean canBeSplitted( ) {

        return true;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#draw(java.awt.Graphics2D, int, int)
     */
    @Override
    public void draw( Canvas c, int xIni, int yIni ) {

        //X and Y coordinates
        int x = xIni + ( FunctionalTextBook.TEXT_WIDTH - FunctionalTextBook.TEXT_WIDTH_BULLET );
        int y = yIni;
        //for each line of the bullet book
        for( int i = 0; i < textLines.size( ); i++ ) {
            //if its the first line, we draw the bullet
            if( i == 0 ) {
                c.drawBitmap( imgBullet, xIni, y - 3, null );
            }
            //the the string
            String line = textLines.get( i );
            
            if ( FunctionalTextBook.PAGE_TEXT_HEIGHT - ( y % FunctionalTextBook.PAGE_TEXT_HEIGHT ) < FunctionalTextBook.LINE_HEIGHT ){
                y += ( FunctionalTextBook.PAGE_TEXT_HEIGHT - ( y % FunctionalTextBook.PAGE_TEXT_HEIGHT ) );
            }
            
            c.drawText( line, x, y + FunctionalTextBook.LINE_HEIGHT - 9, p);
            
            //add the line height to the Y coordinate for the next line
            y = y + FunctionalTextBook.LINE_HEIGHT;
        }
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#getHeight()
     */
    @Override
    public int getHeight( ) {

        return textLines.size( ) * FunctionalTextBook.LINE_HEIGHT;
    }

}

