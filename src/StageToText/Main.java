package StageToText;

import java.io.FileNotFoundException;

import StageToText.tray.TrayItem;
import StageToText.util.ConfigReader;
import StageToText.util.ProPresenterConnector;
import StageToText.util.TextOutput;

public class Main {
	
	public static ProPresenterConnector ppSession;
	public static TextOutput PPText;
	public static String[] configArray = new String[3];
	
	
	public static void main(String[] args) throws FileNotFoundException {
		// Read from Config File
		
		ConfigReader config = new ConfigReader("config.txt");
		configArray = config.ReadFile();
		

		PPText = new TextOutput(config.getOutputLocation());
		
		// Create a new connection file for connection to ProPresenter
		ppSession = new ProPresenterConnector(configArray);
		// Create a system tray icon
		@SuppressWarnings("unused")
		TrayItem ti = new TrayItem();
		// Initiate a connection to ProPresenter
		ppSession.connect();
		
	}


}
