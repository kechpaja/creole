package backend;

import java.util.List;

public class NetworkUtilities {
	
	// TODO add server information and stuff
	
	protected String sendToServer(String message) {
		// TODO actually send message to server, and return response
		
		return null;
	}
	
	protected List<Message> fetchUnseenMessages() {
		// TODO this will eventually take an argument, but I don't know what just yet. Probably a time, 
		// and the server will send back all messages that were not marked as seen before that time. 
		// It's also not clear where the last time at which messages were checked will be stored. 
		return null; 
	}

	public static String getSeparator() {
		return "" + ((char) 30);
	}
}
