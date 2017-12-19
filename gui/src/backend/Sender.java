package backend;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender implements Runnable {
	
	private BlockingQueue<Message> sendQueue_;
	private NetworkUtilities networkUtilities_;
	
	private boolean shutdownFlag_;
	
	public Sender(NetworkUtilities networkUtilities) {
		this.sendQueue_ = new LinkedBlockingQueue<Message>();
		this.networkUtilities_ = networkUtilities;
	}

	
	public void queueMessage(Message message) {
		this.sendQueue_.add(message);
	}

	@Override
	public void run() {
		while (!this.shutdownFlag_) {
			try {
				Message message = this.sendQueue_.take();
				String messageToServer = "send" + NetworkUtilities.getSeparator() + message.toSendableString();
				
				// TODO this logic probably needs to move into the NetworkUtilities class, but I don't have the energy to 
				// TODO do that right now. 
				String[] response = this.networkUtilities_.sendToServer(messageToServer).split(NetworkUtilities.getSeparator());
				
				if (!response[0].equals("sent") || response.length < 2 || !response[1].equals(message.getId())) {
					// TODO various failure cases
				}
			} catch (InterruptedException e) {
				// TODO do we need to do anything here, or just try again?
			}
		}
	}
	
	public void shutdown() {
		this.shutdownFlag_ = true;
	}
}
