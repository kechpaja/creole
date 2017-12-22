package backend;

import java.util.HashSet;
import java.util.Set;

import ui.ChatPanel;

public class Receiver implements Runnable {
	
	private ChatPanel chatPanel_;
	private NetworkUtilities networkUtilities_;
	private String serverHost_;
	private int serverPort_;
	
	private boolean shutdownFlag_;
	
	public Receiver(ChatPanel chatPanel, String serverHost, int serverPort) {
		this.chatPanel_ = chatPanel;
		this.networkUtilities_ = new NetworkUtilities();
		this.serverHost_ = serverHost;
		this.serverPort_ = serverPort;
	}

	@Override
	public void run() {
		this.networkUtilities_.init(this.serverHost_, this.serverPort_);
		
		String[] response = networkUtilities_.sendAndGetResponse(new String[] { "userlist" });
		
		if (response.length >= 1 && response[0].equals("users")) {
			Set<String> users = new HashSet<String>();
			for (int i = 1; i < response.length; i++) {
				users.add(response[i]);
			}
			this.chatPanel_.addUsers(users);
		} else {
			// TODO various error cases
		}
		
		while (!this.shutdownFlag_) {
			response = networkUtilities_.sendAndGetResponse(new String[] { "receive", SessionManager.getCurrentUser() });
			
			if (response.length >= 1 && response[0].equals("messages")) {
				for (int i = 1; i < response.length; i++) {
					this.chatPanel_.deliver(Message.fromSendableString(response[i]));
				}
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