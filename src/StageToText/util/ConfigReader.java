package StageToText.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Luke Bradtke
 * @version 1.0
 * @since 1.0
 */


public class ConfigReader {
	
	String currentLine = null;
	String configArray[] = new String[4];
	String configLocation;
	int counter;
	
	public ConfigReader(String configFile) {
		this.configLocation = configFile;
	}
	public String[] ReadFile() throws FileNotFoundException {
		
		counter = 0;
		
		File config = new File(configLocation);
		Scanner sc = new Scanner(config);
		
		// Pull first line into variable.
		currentLine = sc.nextLine();
		
		// Loops until no more lines are found.
		while (sc.hasNextLine()) {
			if ('[' == currentLine.charAt(0)) {
				currentLine = sc.nextLine();	
			} else {
				configArray[counter] = currentLine;
				counter++;
				currentLine = sc.nextLine();
			}
		}
		sc.close();
		
		return configArray;
		
	}
	
	public String getOutputLocation() {
		return configArray[3];
	}
	
}
