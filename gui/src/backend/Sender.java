package backend;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender implements Runnable {
	
	private class MessageFrame {
		private Message message_;
		private List<String> recipients_;
		
		private MessageFrame(Message message, List<String> recipients) {
			this.message_ = message;
			this.recipients_ = recipients;
		}
		
		private String toSendableString() {
			Iterator<String> it = this.recipients_.iterator();
			String acc = "";
			while (it.hasNext()) {
				acc += it.next();
				
				if (it.hasNext()) {
					acc += ((char) 31); // Unit separator
				}
			}
			
			return acc + ((char) 30) + this.message_.toSendableString();
		}
		
		private Message getMessage() {
			return this.message_;
		}
	}
	
	private BlockingQueue<MessageFrame> sendQueue_;
	private NetworkUtilities networkUtilities_;
	
	public Sender(NetworkUtilities networkUtilities) {
		this.sendQueue_ = new LinkedBlockingQueue<MessageFrame>();
		this.networkUtilities_ = networkUtilities;
	}

	
	public void queueMessage(Message message, List<String> toUsers) {
		this.sendQueue_.add(new MessageFrame(message, toUsers)); // TODO question if this is correct method
	}

	@Override
	public void run() {
		// TODO figure out how to shut down gracefully
		while (true) {
			try {
				MessageFrame messageFrame = this.sendQueue_.take();
				String messageToServer = "send" + ((char) 29) + messageFrame.toSendableString();
				
				String[] response = this.networkUtilities_.sendToServer(messageToServer).split("" + ((char) 29));
				
				if (!response[0].equals("sent") || response.length < 2 || !response[1].equals(messageFrame.getMessage().getId())) {
					// TODO various failure cases
				}
			} catch (InterruptedException e) {
				// TODO do we need to do anything here, or just try again?
			}
		}
	}
}
