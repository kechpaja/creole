package backend;

public class Message implements Comparable<Message> {
	
	private String content_;
	private String sendingUser_;
	private long sendTime_;
	private String threadId_;
	private String conversationId_;
	
	public Message(String content, String sendingUser, String threadId, String conversationId) {
		this.content_ = content;
		this.sendingUser_ = sendingUser;
		this.sendTime_ = System.currentTimeMillis();
		this.threadId_ = threadId;
		this.conversationId_ = conversationId;
	}
	
	
	public String getContent() {
		return this.content_;
	}
	
	public String getSendingUser() {
		return this.sendingUser_;
	}


	@Override
	public int compareTo(Message message) {
		// Can't just subtract, because then you have to deal with casting long safely to int
		if (this.sendTime_ < message.sendTime_) {
			return -1;
		} else if (this.sendTime_ > message.sendTime_) {
			return 1;
		} else {
			return 0;
		}
	}

}
