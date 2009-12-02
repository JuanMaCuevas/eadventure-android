package es.eadengine.saxprototype;

import java.io.File;
import java.io.FilenameFilter;


/**
 * A filter for ead files
 */
public class EADFileFilter implements FilenameFilter {


	@Override
	public boolean accept(File f, String name) {
		
	  return ( f.isDirectory( ) && ( name.endsWith( ".ead" ) == true )) ;
		
	}
}