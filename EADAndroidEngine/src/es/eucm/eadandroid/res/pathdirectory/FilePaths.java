package es.eucm.eadandroid.res.pathdirectory;

import android.os.Environment;

public final class FilePaths {

	public static final class repository {

		public static final String DEFAULT_PATH = "http://eadventure-android.googlecode.com/files/";
		public static final String XML_NAME = "clasificadorjuegos.xml";

	}

	public static final class eaddirectory {

		public static final String PARENT_ABSOLUTE_PATH = FilePaths.device.EXTERNAL_STORAGE + "/eadventure_engine/";
		public static final String GAMES_ABSOLUTE_PATH = PARENT_ABSOLUTE_PATH + "Local_games/";

		public static final class repodatacache {

			public static final String ABSOLUTE_PATH = PARENT_ABSOLUTE_PATH + "Repository/";

		}

	}

	public static final class device {

		public static final String EXTERNAL_STORAGE = Environment
				.getExternalStorageDirectory().toString()+"/";
	}

}
