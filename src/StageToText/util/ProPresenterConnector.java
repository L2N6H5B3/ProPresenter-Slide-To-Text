package StageToText.util;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import StageToText.Main;
import StageToText.model.*;

import static StageToText.util.ThreadUtil.sleep;


/**
 * @author Luke Bradtke
 * @version 1.0
 * @since 1.0
 */


public class ProPresenterConnector {

	private String HOST = "localhost";
	private String PORT = "53333";
	private String PASSWORD = "stage";
	private String RESPONSE_TIME_MILLIS = "10";
		
	
    private static final String SUCCESSFUL_LOGIN = "<StageDisplayLoginSuccess />";
    private static final String SUCCESSFUL_LOGIN_WINDOWS = "<StageDisplayLoginSuccess>";
    private static final XmlDataReader xmlDataReader = new XmlDataReader();
    private static final XmlParser xmlParser = new XmlParser();
	
    private volatile boolean running = true;
    private volatile boolean activeConnection = true;
    private volatile boolean loginFailed = false;
    private volatile boolean connectionFailed = false;
    
	public ProPresenterConnector(String[] configArray) {
		this.HOST = configArray[0];
		this.PORT = configArray[1];
		this.PASSWORD = configArray[2];
	}

	public void connect() throws FileNotFoundException {
	        while (running) {
	            try (Socket socket = new Socket(HOST.toString(), Integer.parseInt(PORT))) {
	                try (
	                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))
	                ) {
	                    activeConnection = true;
	                    out.println(getLoginString());
	                    connectionFailed = false;

	                    String loginResponse = in.readLine();
	                    if (SUCCESSFUL_LOGIN.equals(loginResponse) || SUCCESSFUL_LOGIN_WINDOWS.equals(loginResponse)) {
	                        loginFailed = false;
	                    } else {
	                        loginFailed = true;
	                    }
	                    while (running && activeConnection && socket.isConnected()) {
	                        activeConnection = update(in);
	                        sleep(Integer.parseInt(RESPONSE_TIME_MILLIS));
	                    }
	                }
	            } catch (IOException e) {
	                connectionFailed = true;
	                Main.PPText.writeOut(" ");
	            }
	            sleep(500);
	        }
	}
	
	private boolean update(BufferedReader in) throws IOException {
		
        String xmlRawData = xmlDataReader.readXmlData(in);
        if (xmlRawData == null) {
        	Main.PPText.writeOut(" ");
            return false;
        }

        StageDisplay stageDisplay = xmlParser.parse(xmlRawData);
        String slide = stageDisplay.getData("CurrentSlide");
        Main.PPText.writeOut(slide.toUpperCase());

        return true;
    }
	
	
	public String connectionStatus() {
		String status = null; 
		if (!connectionFailed && !loginFailed) {
			status = "Successfully connected to ProPresenter @ \""+HOST+":"+PORT+"\" with password \""+PASSWORD+"\".";
		} else {
			status = "Connection to ProPresenter @ \""+HOST+":"+PORT+"\" with password \""+PASSWORD+"\" failed.";
		}
		return status;
	}
	
	
    private String getLoginString() {
        return "<StageDisplayLogin>" + PASSWORD + "</StageDisplayLogin>\n\r";
    }
}
