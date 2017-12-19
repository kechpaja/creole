package backend;

import ui.ConversationsPanel;

public class Receiver implements Runnable {
	
	private ConversationsPanel conversations_;
	private NetworkUtilities networkUtilities_;
	
	private boolean shutdownFlag_;
	
	public Receiver(ConversationsPanel conversations, NetworkUtilities networkUtilities) {
		this.conversations_ = conversations;
		this.networkUtilities_ = networkUtilities;
	}

	@Override
	public void run() {
		while (!this.shutdownFlag_) {
			this.conversations_.deliver(this.networkUtilities_.fetchUnseenMessages());
		}
	}
	
	public void shutdown() {
		this.shutdownFlag_ = true;
	}

}
