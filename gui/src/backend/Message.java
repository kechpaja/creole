package backend;

public class Message implements Comparable<Message> {
	
	private String content_;
	private String sendingUser_;
	private long sendTime_;
	private String chatId_;
	private String conversationId_;
	private String messageId_;
	
	public Message(String content, String sendingUser, String threadId, String conversationId) {
		this.content_ = content.replaceAll("[^\\P{Cc}\\t\\r\\n]", ""); // https://stackoverflow.com/a/15520992
		this.sendingUser_ = sendingUser;
		this.sendTime_ = System.currentTimeMillis();
		this.chatId_ = threadId;
		this.conversationId_ = conversationId;
		this.messageId_ = SessionManager.getCurrentUser() + "-" + System.currentTimeMillis() + "-" + SessionManager.getSessionId(); 
	}
	
	
	public String getId() {
		return this.messageId_;
	}
	
	public String getContent() {
		return this.content_;
	}
	
	public String getSendingUser() {
		return this.sendingUser_;
	}
	
	public String getChatId() {
		return this.chatId_;
	}
	
	public String getConversationId() {
		return this.conversationId_;
	}
	
	public String toSendableString() {
		char unitSeparator = (char) 31;
		
		return this.messageId_ + unitSeparator + this.conversationId_ + unitSeparator + this.chatId_ 
				+ unitSeparator + this.sendingUser_ + unitSeparator + this.content_;
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
