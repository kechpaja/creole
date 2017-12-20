package core;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Router {
	
	private ConcurrentHashMap<String, Vector<String>> data_;
	
	public Router() {
		this.data_ = new ConcurrentHashMap<String, Vector<String>>();
	}
	
	public void routeMessageToUser(String message, String user) {
		if (!this.data_.containsKey(user)) {
			this.data_.put(user, new Vector<String>());
		}
		
		Vector<String> messageList = this.data_.get(user);
		synchronized (messageList) {
			messageList.add(message);
		}
	}
	
	public Vector<String> getMessagesForUser(String user) {
		Vector<String> messages = new Vector<String>();
		
		if (this.data_.containsKey(user)) {
			Vector<String> messageList = this.data_.get(user);
			
			synchronized (messageList) {
				while (messageList.size() > 0) {
					messages.add(messageList.remove(0));
				}
			}
		}
		
		return messages;
	}

}
