package backend;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender implements Runnable {
	
	private BlockingQueue<Message> sendQueue_;
	private NetworkUtilities networkUtilities_;
	private String serverHost_;
	private int serverPort_;
	
	private boolean shutdownFlag_;
	
	public Sender(String serverHost, int serverPort) {
		this.sendQueue_ = new LinkedBlockingQueue<Message>();
		this.networkUtilities_ = new NetworkUtilities();
		this.serverHost_ = serverHost;
		this.serverPort_ = serverPort;
	}

	
	public void queueMessage(Message message) {
		this.sendQueue_.add(message);
	}

	@Override
	public void run() {
		this.networkUtilities_.init(this.serverHost_, this.serverPort_);
		
		while (!this.shutdownFlag_) {
			try {
				Message message = this.sendQueue_.take();
				
				String[] request = new String[message.getToUsers().size() + 2];
				request[0] = "send";
				request[1] = message.toSendableString();
				int i = 2;
				for (String toUser : message.getToUsers()) {
					request[i] = toUser;
					i++;
				}
				
				String[] response = networkUtilities_.sendAndGetResponse(request);
				
				if (response.length < 2  || !response[0].equals("sent")) {
					// TODO various failure cases; possibly try again
				}
			} catch (InterruptedException e) {
				// Do nothing; just spin around and start waiting again
			}
		}
		
		this.networkUtilities_.shutdown();
	}
	
	public void shutdown() {
		this.shutdownFlag_ = true;
	}
}
