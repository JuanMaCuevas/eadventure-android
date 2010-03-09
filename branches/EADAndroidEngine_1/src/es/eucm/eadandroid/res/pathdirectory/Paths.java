package es.eucm.eadandroid.res.pathdirectory;

import android.os.Environment;

public final class Paths {

	public static final class repository {

		public static final String DEFAULT_PATH = "http://eadventure-android.googlecode.com/files/";
		public static final String SOURCE_XML = "repository.xml";

	}

	public static final class eaddirectory {
		
		public static final String ROOT_PATH = Paths.device.EXTERNAL_STORAGE + "EadAndroid/" ;
		public static final String GAMES_PATH = ROOT_PATH + "games/" ;

	}

	public static final class device {

		public static final String EXTERNAL_STORAGE = Environment
				.getExternalStorageDirectory().toString() + "/";
	}

}
