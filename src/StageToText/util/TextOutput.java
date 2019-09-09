package StageToText.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


/**
 * @author Luke Bradtke
 * @version 1.0
 * @since 1.0
 */


public class TextOutput {
	
	String textFileLocation;
	PrintWriter writeFile;
	
	public TextOutput(String textFile) throws FileNotFoundException {
		this.textFileLocation = textFile;
		writeFile = new PrintWriter(textFileLocation);
	}
	
	
	public void writeOut(String content) throws FileNotFoundException {
		writeFile = new PrintWriter(textFileLocation);
		writeFile.println(content);
		writeFile.close();
	}
	
	public void writeOut(int content) throws FileNotFoundException {
		writeFile = new PrintWriter(textFileLocation);
		writeFile.println(content);
		writeFile.close();
	}
	
}
