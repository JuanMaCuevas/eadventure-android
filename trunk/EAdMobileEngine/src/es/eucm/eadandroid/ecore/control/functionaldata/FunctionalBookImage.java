package es.eucm.eadandroid.ecore.control.functionaldata;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import es.eucm.eadandroid.common.data.chapter.book.BookParagraph;
import es.eucm.eadandroid.multimedia.MultimediaManager;


/**
 * This is a image that can be put in a book scene
 */
public class FunctionalBookImage extends FunctionalBookParagraph {

    /**
     * The image book
     */
    private BookParagraph bookImage;

    /**
     * The image of the image book
     */
    private Bitmap image;

    /**
     * Creates a new FunctionalBookImage
     * 
     * @param image
     *            the image to be rendered
     */
    public FunctionalBookImage( BookParagraph image ) {

        //set the book image
        this.bookImage = image;
        //and loads the image 
        //OLD(FROM ZIP???)
        this.image = MultimediaManager.getInstance( ).loadImage( bookImage.getContent( ), MultimediaManager.IMAGE_SCENE );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#canBeSplitted()
     */
    @Override
    public boolean canBeSplitted( ) {

        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#draw(java.awt.Graphics2D, int, int)
     */
    @Override
    public void draw( Canvas c, int x, int y ) {

        //This book only draw a image
        c.drawBitmap( image, x, y + 5, null );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#getHeight()
     */
    @Override
    public int getHeight( ) {

        //The height of the book is the height of the image
        return (int) Math.ceil( ( image.getHeight() + 5 ) / (double) FunctionalTextBook.LINE_HEIGHT ) * FunctionalTextBook.LINE_HEIGHT;
    }

}

