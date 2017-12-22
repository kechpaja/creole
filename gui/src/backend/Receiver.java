package backend;

import java.util.ArrayList;
import java.util.List;

import ui.ConversationsPanel;

public class Receiver implements Runnable {
	
	private ConversationsPanel conversations_;
	private NetworkUtilities networkUtilities_;
	private String serverHost_;
	private int serverPort_;
	
	private boolean shutdownFlag_;
	
	public Receiver(ConversationsPanel conversations, String serverHost, int serverPort) {
		this.conversations_ = conversations;
		this.networkUtilities_ = new NetworkUtilities();
		this.serverHost_ = serverHost;
		this.serverPort_ = serverPort;
	}

	@Override
	public void run() {
		this.networkUtilities_.init(this.serverHost_, this.serverPort_);
		
		String[] response = networkUtilities_.sendAndGetResponse(new String[] { "userlist" });
		
		if (response.length >= 1 && response[0].equals("users")) {
			List<String> users = new ArrayList<String>();
			for (int i = 1; i < response.length; i++) {
				users.add(response[i]);
			}
			this.conversations_.addUsers(users);
		} else {
			// TODO various error cases
		}
		
		while (!this.shutdownFlag_) {
			response = networkUtilities_.sendAndGetResponse(new String[] { "receive", SessionManager.getCurrentUser() });
			
			if (response.length >= 1 && response[0].equals("messages")) {
				List<Message> messages = new ArrayList<Message>();
				for (int i = 1; i < response.length; i++) {
					messages.add(Message.fromSendableString(response[i]));
				}
				this.conversations_.deliver(messages);
			} else {
				// TODO various error cases
			}
		}
		
		this.networkUtilities_.shutdown();
	}
	
	public void shutdown() {
		this.shutdownFlag_ = true;
	}

}