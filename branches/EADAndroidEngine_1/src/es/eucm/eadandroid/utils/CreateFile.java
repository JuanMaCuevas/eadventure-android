package es.eucm.eadandroid.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class CreateFile {
	
	
	
	
	
	
	
	FileWriter fstream;
BufferedWriter out;


PrintStream file;


///////////////////////////////////
String year;
String month;
String day;
String hour;
String minute;
String hourfinish;
String minutefinish;
///////////////////////////////////////////////

public CreateFile(String write) throws IOException {
	
/*	PrintStream file = new PrintStream( new FileOutputStream( write ) );

	
	 file.println("vamos a ver si funciona");
	 */
	 fstream = new FileWriter(write);
		out = new BufferedWriter(fstream);
		out.write("vamos a ver si funciona");
		out.newLine();

}


public void dowork(String data) {
	
	try {
		out.write(data);
		out.newLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

	//file.println(data);

}


public void finishwork() {

	/*file.println( );
	file.close( );
*/
	
	try {
		out.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	
}	
	
	
	
	
	

}
