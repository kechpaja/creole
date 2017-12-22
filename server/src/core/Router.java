package core;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Router {
	
	private ConcurrentHashMap<String, Vector<String>> data_;
	
	public Router() {
		this.data_ = new ConcurrentHashMap<String, Vector<String>>();
		this.data_.put("admin", new Vector<String>());
	}
	
	private void addUser(String user) {
		String userJoinedMessage = "admin:admin:admin:";
		userJoinedMessage += "User\\s\"" + user + "\"\\sjoined\\sthe\\sserver!:"; // TODO localize this string! Possibly on client. 
		userJoinedMessage += String.join(":", this.data_.keySet());
			
		for (Vector<String> messageList : this.data_.values()) {
			messageList.add(userJoinedMessage);
		}
	}
	
	public void routeMessageToUser(String message, String user) {
		synchronized (this.data_) {
			if (!this.data_.containsKey(user)) {
				this.data_.put(user, new Vector<String>());
			}
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
		} else {
			synchronized (this.data_) {
				if (!this.data_.containsKey(user)) {
					this.data_.put(user, new Vector<String>());
					addUser(user);
				}
			}
		}
		
		return messages;
	}
	
	public Vector<String> getUserList() {
		Vector<String> userList = new Vector<String>();
		
		for (String user : this.data_.keySet()) {
			userList.add(user);
		}
		
		return userList;
	}

}
