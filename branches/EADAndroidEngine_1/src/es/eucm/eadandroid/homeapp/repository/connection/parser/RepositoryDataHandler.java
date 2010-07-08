package es.eucm.eadandroid.homeapp.repository.connection.parser;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import android.graphics.Bitmap;
import android.util.Log;
import es.eucm.eadandroid.homeapp.repository.RepositoryActivity;
import es.eucm.eadandroid.homeapp.repository.database.GameInfo;
import es.eucm.eadandroid.homeapp.repository.database.RepositoryDatabase;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.ProgressNotifier;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;

public class RepositoryDataHandler extends DefaultHandler {

	StringBuffer currentString = null;

	RepositoryDatabase repositoryInfo;
	ProgressNotifier pn ;
	
	RepositoryActivity aux;

	boolean hasTitle = false;
	boolean hasImageIcon = false;
	boolean hasDescription = false;
	boolean hasUrl = false;
	boolean hasImage;

	String tit, des, url;
	Bitmap imgIcon ;
	Bitmap image;

	/**
	 * Constructor
	 * @param pn 
	 * 
	 * @param testLayout
	 */
	public RepositoryDataHandler(RepositoryDatabase rd, ProgressNotifier pn) {

		currentString = new StringBuffer();
		repositoryInfo = rd;
		this.pn = pn;

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String namespaceURI, String sName, String qName,
			Attributes attrs) throws SAXException {

		if (sName.equals("title")) {
			hasTitle = true;
		}

		if (sName.equals("imageIcon")) {
			hasImageIcon = true;			
		}
		
		if (sName.equals("image")) {
			hasImage = true;
		}

		if (sName.equals("description")) {
			hasDescription = true;
		}

		if (sName.equals("url")) {
			hasUrl = true;
		}
	}

	@Override
	public void endElement(String namespaceURI, String sName, String qName)
			throws SAXException {

		Log.d("EndElement", "XMLNS : " + namespaceURI + " SNAME : " + sName
				+ " QNAME : " + qName);

		if (sName.equals("title")) {
			hasTitle = false;
		}

		if (sName.equals("imageIcon")) {
			hasImageIcon = false;
		}
		
		if (sName.equals("image")) {
			hasImage = false;
		}

		if (sName.equals("description")) {
			hasDescription = false;
		}

		if (sName.equals("url")) {
			hasUrl = false;
		}

		if (sName.equals("game")) {
			hasDescription = false;
			
			
			this.repositoryInfo.addGameInfo(new GameInfo(tit, des, url, imgIcon,image));
		}

	}

	public void characters(char[] buf, int offset, int len) throws SAXException {

		currentString.append(new String(buf, offset, len));


		if (hasTitle) {
			tit = new String(new String(buf, offset, len));
		}

		if (hasImageIcon) {
			String im = new String(new String(buf, offset, len));
			Log.d("Characters", "Text : " + new String(buf, offset, len));
			
			imgIcon = RepoResourceHandler.DownloadImage(im,pn);
			
		}
		
		if (hasImage) {
			String im = new String(new String(buf, offset, len));
			Log.d("Characters", "Text : " + new String(buf, offset, len));
			
			image = RepoResourceHandler.DownloadImage(im,pn);
			
		}

		if (hasDescription) {
			des = new String(new String(buf, offset, len));
		}

		if (hasUrl) {
			url = new String(new String(buf, offset, len));
		}

	}

	@Override
	public void error(SAXParseException exception) throws SAXParseException {

		// On validation, propagate exception
		exception.printStackTrace();
		Log.d("Error", "SAXParseException");
		throw exception;
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) {

		Log.d("resolveEntity", "PublicID : " + publicId + " SystemId : "
				+ systemId);

		return null;
	}

}
