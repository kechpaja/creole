package backend;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender {
	
	private BlockingQueue<Message> sendQueue_;
	
	public Sender() {
		this.sendQueue_ = new LinkedBlockingQueue<Message>();
	}

	
	public void queueMessage(Message message) {
		this.sendQueue_.add(message); // TODO question if this is correct method
	}
	
	// TODO send loop and all that jazz
}
