package es.eucm.eadandroid.homeapp.repository.resourceHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import es.eucm.eadandroid.homeapp.loadsavedgames.InfoExpandabletable;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker.ProgressNotifier;
import es.eucm.eadandroid.res.filefilters.EADFileFilter;
import es.eucm.eadandroid.res.filefilters.TxtFilter;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class RepoResourceHandler {

	private static final int DOWNLOAD_BUFFER_SIZE = 1048576;

	public static Bitmap DownloadImage(String url_from, ProgressNotifier pn) {

		// pn.notifyProgress(0, "Downloading image " + url_from);
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			in = OpenHttpConnection(url_from);
			bitmap = BitmapFactory.decodeStream(in);
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// pn.notifyProgress(100, "Image downloaded");

		// pn.notifyFinished("Image downloaded");

		return bitmap;
	}

	public static void downloadFile(String url_from, String path_to,
			String fileName, ProgressNotifier pt) {

		URL u = null;

		try {

			u = new URL(url_from);

			HttpURLConnection c = (HttpURLConnection) u.openConnection();

			pt.notifyProgress(0, "Connection opened");

			c.setRequestMethod("GET");
			c.setDoOutput(true);
			c.connect();

			pt.notifyProgress(0, "Connection established");

			File file = new File(path_to, fileName);

			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);

				InputStream in;
				try {

					float fileSize = c.getContentLength();

					Log.d("fileSize", String.valueOf(fileSize));

					float iterNum = new Float(fileSize / (DOWNLOAD_BUFFER_SIZE)
							+ 1).intValue();

					Log.d("numIter", String.valueOf(iterNum));

					float prIncrement = 100 / iterNum;

					Log.d("prIncrement", String.valueOf(prIncrement));

					Float progress = new Float(0);

					in = c.getInputStream();
					byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE];
					int len1 = 0;
					while ((len1 = in.read(buffer)) != -1) {
						fos.write(buffer, 0, len1);

						progress += len1 / fileSize * 100;

						pt.notifyProgress(progress.intValue(), "Downloading "
								+ fileName);

						Log.d("progress", String.valueOf(progress));

					}

					fos.close();
					in.close();

				} catch (IOException e) {

					pt.notifyError("Error while downloading");
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {

				pt.notifyError("Error , file not found");
				e.printStackTrace();
			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	public static void downloadFileAndUnzip(String url_from, String path_to,
			String fileName, ProgressNotifier pn) {

		downloadFile(url_from, path_to, fileName, pn);
		pn.notifyIndeterminate("Insalling " + fileName);
		unzip(path_to, fileName);

	}

	public static void getexpandablelist(InfoExpandabletable info) {
		String path = Paths.eaddirectory.SAVED_GAMES_PATH;
		String games[] = null;
		String[][] finalarray = null;

		if (!new File(Paths.eaddirectory.SAVED_GAMES_PATH).exists()) {
			new File(Paths.eaddirectory.SAVED_GAMES_PATH).mkdir();
		} else {
			File gamesfolders = new File(Paths.eaddirectory.SAVED_GAMES_PATH);
			games = gamesfolders.list(new EADFileFilter());

			if (games.length>0)
			{
				//aqui ya se que hay un resultado xq no puede haber una carpeta sin un archivo guardado
				finalarray = new String[games.length][];
				for (int i = 0; i < games.length; i++) {
						String files[] = null;
							File gamefolder = new File(Paths.eaddirectory.SAVED_GAMES_PATH
									+ games[i] + "/");
			//	if (gamefolder.exists())
							files = gamefolder.list(new TxtFilter());
							finalarray[i] = new String[files.length];
							for (int j = 0; j < files.length; j++)
								finalarray[i][j] = files[j];
				

			}
			}
		}

		info.setChildren(finalarray);
		info.setGroup(games);

	}

	public static void unzip(String filePath, String name) {
		// TODO la ruta a las carpetas me las tengo que crear cuando instalo
		// pero por ahora lo dejo aqui

		StringTokenizer separator = new StringTokenizer(name, ".", true);
		String game_name = separator.nextToken();

		separator = new StringTokenizer(filePath + game_name, "/", true);

		String partial_path = null;
		String total_path = separator.nextToken();

		while (separator.hasMoreElements()) {

			partial_path = separator.nextToken();
			total_path = total_path + partial_path;
			if (!new File(total_path).exists()) {

				if (separator.hasMoreElements())
					total_path = total_path + separator.nextToken();
				else
					(new File(total_path)).mkdir();

			} else
				total_path = total_path + separator.nextToken();

		}

		Enumeration<? extends ZipEntry> entries = null;
		ZipFile zipFile = null;

		try {
			String location_ead = filePath + name;
			zipFile = new ZipFile(location_ead);

			entries = zipFile.entries();

			BufferedOutputStream file;

			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();

				separator = new StringTokenizer(entry.getName(), "/", true);
				partial_path = null;
				total_path = "";

				while (separator.hasMoreElements()) {

					partial_path = separator.nextToken();
					total_path = total_path + partial_path;
					if (!new File(entry.getName()).exists()) {

						if (separator.hasMoreElements()) {
							total_path = total_path + separator.nextToken();
							(new File(filePath + game_name + "/" + total_path))
									.mkdir();
						} else {

							file = new BufferedOutputStream(
									new FileOutputStream(filePath + game_name
											+ "/" + total_path));

							System.err.println("Extracting file: "
									+ entry.getName());
							copyInputStream(zipFile.getInputStream(entry), file);
						}
					} else {
						total_path = total_path + separator.nextToken();
					}
				}

			}

			zipFile.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		}

		(new File(filePath + name)).delete();

	}

	public static final void copyInputStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
	}

	private static InputStream OpenHttpConnection(String urlString)

	throws IOException {
		InputStream in = null;
		int response = -1;

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect();

			response = httpConn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (Exception ex) {
			throw new IOException("Error connecting");
		}
		return in;
	}

	public static boolean doesfileexists(String path) {
		if (new File(path).exists()) {
			return true;
		} else
			return false;
	}

	public static void updatesavedgames() {

		String[] gameswithsaved = new File(Paths.eaddirectory.SAVED_GAMES_PATH)
				.list();

		for (int i = 0; i < gameswithsaved.length; i++) {

			if (new File(Paths.eaddirectory.SAVED_GAMES_PATH
					+ gameswithsaved[i]).list().length == 0)
				new File(Paths.eaddirectory.SAVED_GAMES_PATH
						+ gameswithsaved[i]).delete();
		}
	}

	public static void deleteFile(String path) {

		File f = new File(path);

		if (f.exists())

			if (!f.isDirectory())
				f.delete();
			else
				removeDirectory(f);

	}

	
	
	public static boolean removeDirectory(File directory) {

		if (directory == null)
			return false;
		if (!directory.exists())
			return true;
		if (!directory.isDirectory())
			return false;

		String[] list = directory.list();

		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File entry = new File(directory, list[i]);

				if (entry.isDirectory()) {
					if (!removeDirectory(entry))
						return false;
				} else {
					if (!entry.delete())
						return false;
				}
			}
		}

		return directory.delete();
	}


}
