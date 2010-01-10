package es.eucm.eadandroid.res.filefilters;

import java.io.File;
import java.io.FilenameFilter;

public class JPGFilter implements FilenameFilter {

	public boolean accept(File f, String name) {

		return (f.isDirectory() && (name.endsWith(".JPG") == true));

	}

}
