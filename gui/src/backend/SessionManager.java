package backend;

import ui.ChatPanel;

public class SessionManager {
	
	// Static information about current session
	private static String currentUser_;
	private static String sessionId_;
	private static Sender sender_;
	private static Receiver receiver_;
	
	
	public static String getCurrentUser() {
		return SessionManager.currentUser_;
	}
	
	public static String getSessionId() {
		return SessionManager.sessionId_;
	}
	
	public static Sender getSender() {
		return SessionManager.sender_;
	}
	
	public static void init(ChatPanel chatPanel, String username) {
		SessionManager.currentUser_ = username;
		SessionManager.sessionId_ = "sessionID"; // TODO get actual session ID
		
		SessionManager.sender_ = new Sender("localhost", 1234); // TODO get this info from somewhere
		SessionManager.receiver_ = new Receiver(chatPanel, "localhost", 1234);
		
		SessionManager.launchThread(SessionManager.sender_);
		SessionManager.launchThread(SessionManager.receiver_);
	}
	
	public static void shutdown() {
		SessionManager.sender_.shutdown();
		SessionManager.receiver_.shutdown();
	}
	
	private static void launchThread(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.start();
	}

}
