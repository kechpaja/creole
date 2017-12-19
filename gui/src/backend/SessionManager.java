package backend;

public class SessionManager {
	
	public static String getCurrentUser() {
		return "kechpaja"; // TODO actually write this
	}
	
	public static String getSessionId() {
		return "sessionID"; // TODO actually do this
	}
	
	public static Sender getSender() {
		return new Sender(null); // TODO actually do this
	}
	
	public static void init() {
		// TODO set everything up. Take args if necessary. 
	}

}
