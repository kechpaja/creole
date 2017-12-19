package backend;

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
		
		while (!this.shutdownFlag_) {
			//this.conversations_.deliver(this.networkUtilities_.fetchUnseenMessages());
			// TODO actually write this stuff
		}
		
		this.networkUtilities_.shutdown();
	}
	
	public void shutdown() {
		this.shutdownFlag_ = true;
	}

}
