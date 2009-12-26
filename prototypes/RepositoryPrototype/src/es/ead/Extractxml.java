package es.ead;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class Extractxml extends DefaultHandler {
	

	StringBuffer currentString = null;
	
	Vector<InfoGame> archivos;
	
	NewGames aux;
	
	
	boolean titulo=false;
	boolean imagen=false;
	boolean descripcion=false;
	boolean codigo=false;
	
	String tit,im,des,cod;
	
	
    /**
     * Constructor
     * @param testLayout 
     */
    public Extractxml(NewGames aux) {

        currentString = new StringBuffer( );
        archivos=new Vector<InfoGame>();

        this.aux=aux;
    }

   
	/*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

    	
    	if (sName.equals("titulo"))
    	{
    	titulo=true;	
    	}
    	
    	if (sName.equals("imagen"))
    	{
    	imagen=true;	
    	}
    	
    	if (sName.equals("descripcion"))
    	{
    	descripcion=true;	
    	}
    	
    	if (sName.equals("codigo"))
    	{
    	codigo=true;	
    	}
 }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {
    	
    	 Log.d("EndElement", "XMLNS : "+namespaceURI+" SNAME : "+sName+" QNAME : "+qName);

    	
    	if (sName.equals("titulo"))
    	{
    	titulo=false;	
    	}
    	
    	if (sName.equals("imagen"))
    	{
    	imagen=false;	
    	}
    	
    	if (sName.equals("descripcion"))
    	{
    	descripcion=false;	
    	}
    	
    	if (sName.equals("codigo"))
    	{
    	codigo=false;	
    	}
    	 
    	
    	if (sName.equals("juego"))
    	{
    	descripcion=false;	
    	
    	 Log.d(tit, "Text : "+des);
    	 this.archivos.add(new InfoGame(tit,des,cod,im));
    	}
    	 
    	if (sName.equals("motor"))
    	{
    	aux.setArchivos(archivos);	
    	}
    	 
    	 
    	 
   
    }

public void characters( char[] buf, int offset, int len ) throws SAXException {

        currentString.append( new String( buf, offset, len ) );
        
        Log.d("Characters", "Text : "+new String( buf, offset, len ));
        
        
        
        if (titulo)
    	{
    	tit=new String(new String( buf, offset, len ));	
    	}
    	
    	if (imagen)
    	{
    		im=new String(new String( buf, offset, len ));		
    	}
    	
    	if (descripcion)
    	{
    		des=new String(new String( buf, offset, len ));
    	}
    	
    	if (codigo)
    	{
    		cod=new String(new String( buf, offset, len ));
    	}
        
        
        
        
    }

     @Override
    public void error( SAXParseException exception ) throws SAXParseException {

        // On validation, propagate exception
        exception.printStackTrace( );
        Log.d("Error", "SAXParseException");
        throw exception;
    }


    @Override
    public InputSource resolveEntity( String publicId, String systemId ) {
    	
    	Log.d("resolveEntity", "PublicID : "+publicId+" SystemId : "+systemId);
    	
          return null;
    }

}
