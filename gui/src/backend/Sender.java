package backend;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender implements Runnable {
	
	private BlockingQueue<Message> sendQueue_;
	private NetworkUtilities networkUtilities_;
	
	public Sender(NetworkUtilities networkUtilities) {
		this.sendQueue_ = new LinkedBlockingQueue<Message>();
		this.networkUtilities_ = networkUtilities;
	}

	
	public void queueMessage(Message message) {
		this.sendQueue_.add(message); // TODO question if this is correct method
	}

	@Override
	public void run() {
		// TODO figure out how to shut down gracefully
		while (true) {
			try {
				Message message = this.sendQueue_.take();
				String messageToServer = "send" + ((char) 30) + message.toSendableString();
				
				String[] response = this.networkUtilities_.sendToServer(messageToServer).split("" + ((char) 29));
				
				if (!response[0].equals("sent") || response.length < 2 || !response[1].equals(message.getId())) {
					// TODO various failure cases
				}
			} catch (InterruptedException e) {
				// TODO do we need to do anything here, or just try again?
			}
		}
	}
}
