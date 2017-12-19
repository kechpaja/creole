package backend;

import resources.Strings;

public class SessionManager {
	
	// Static information about current session
	private static String currentUser_;
	private static String sessionId_;
	private static Sender sender_;
	
	
	public static String getCurrentUser() {
		return SessionManager.currentUser_;
	}
	
	public static String getSessionId() {
		return SessionManager.sessionId_;
	}
	
	public static Sender getSender() {
		return SessionManager.sender_;
	}
	
	public static void init() {
		// TODO set everything up. Take args if necessary. 
		// Everything currenly here is just for testing. 
		
		SessionManager.currentUser_ = "kechpaja";
		SessionManager.sessionId_ = "sessionID";
		SessionManager.sender_ = new Sender(null);
		
		Strings.setLocalizationLanguage("epo");
	}
	
	public static void shutdown() {
		// TODO do stuff to shut down the program
		SessionManager.sender_.shutdown();
	}

}
