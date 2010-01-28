package es.eucm.eadandroid.homeapp.repository.connection;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.Context;
import android.os.Handler;
import es.eucm.eadandroid.homeapp.repository.connection.parser.RepositoryDataHandler;
import es.eucm.eadandroid.homeapp.repository.database.RepositoryDatabase;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker.ProgressNotifier;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker.ProgressTask;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class UpdateDatabaseThread extends Thread {


	private static final String REPO_FULLPATH = Paths.repository.DEFAULT_PATH
			+ Paths.repository.SOURCE_XML;
	private static final String EXTERNAL_STORAGE = Paths.device.EXTERNAL_STORAGE;
	
	private static final String LOCAL_REPO_XML = Paths.device.EXTERNAL_STORAGE+Paths.repository.SOURCE_XML;

	private Context context;
	private Handler handler;
	private RepositoryDatabase rd;
	private ProgressNotifier pn;

	/**
	 * @param ctx
	 *            -> contains the system context
	 * @param ha
	 *            -> Thread Handle Queue to send messages to.
	 */
	public UpdateDatabaseThread(Context ctx, Handler ha, RepositoryDatabase rd) {
		this.context = ctx;
		this.handler = ha;
		this.rd = rd;
		
		pn = new ProgressNotifier(handler);
	//	this.pt = pn.createRootTask("Update repository database", "Update everything related with the repository database");

	}

	@Override
	public void run() {

		
		// TODO check if needs update
		try {
			downloadXML();
			parseXML();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void downloadXML() throws IOException {

//		ProgressTask downloadPt = pt.createChildTask("Downlaod file", "Download "+REPO_FULLPATH+" to "+EXTERNAL_STORAGE,70);
		
		RepoResourceHandler.downloadFile(REPO_FULLPATH, EXTERNAL_STORAGE, pn);

	}

	private void parseXML() {

	//	ProgressTask parserPt = pt.createChildTask("Parse repository XML", "Parsing repository XML task",30);
		
		try {
			FileInputStream fIn = new FileInputStream(LOCAL_REPO_XML);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			RepositoryDataHandler rsaxh = new RepositoryDataHandler(rd,pn);
			saxParser.parse(fIn, rsaxh);

			pn.notifyFinished("Repository xml parsed");
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
