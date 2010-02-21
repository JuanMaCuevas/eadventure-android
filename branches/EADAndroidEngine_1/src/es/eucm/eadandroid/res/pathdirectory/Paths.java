package es.eucm.eadandroid.res.pathdirectory;

import android.os.Environment;

public final class Paths {

	public static final class repository {

		public static final String DEFAULT_PATH = "http://eadventure-android.googlecode.com/files";
		public static final String SOURCE_XML = "/clasificadorjuegos.xml";

	}

	public static final class eaddirectory {
		
		
		

		public static final String ROOT_PATH = Paths.device.EXTERNAL_STORAGE ;
		
		
		//public static final String UNZIP_PATH = ROOT_PATH + "";
		public static final String ZIPPED_PATH="/sdcard/";
		public static final String DIRECTORY_PATH="/EadAndroid/games/";  //por ahora no lo utilizo fuera de esta clase
		public static final String GAMES_PATH = ROOT_PATH +DIRECTORY_PATH ;
		
		
		
		//public static final String _PATH = ROOT_PATH + "";


	}

	public static final class device {

		public static final String EXTERNAL_STORAGE = Environment
				.getExternalStorageDirectory().toString();
	}

}
