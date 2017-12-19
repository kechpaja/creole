package backend;

import java.util.HashSet;
import java.util.Set;

public class Message implements Comparable<Message> {
	
	private String content_;
	private String sendingUser_;
	private long sendTime_;
	private String chatId_;
	private String conversationId_;
	private String messageId_;
	private Set<String> toUsers_;
	
	public Message(String content, String sendingUser, String chatId, String conversationId, Set<String> toUsers) {
		this.content_ = content.replaceAll("[^\\P{Cc}\\t\\r\\n]", ""); // https://stackoverflow.com/a/15520992
		this.sendingUser_ = sendingUser;
		this.sendTime_ = System.currentTimeMillis();
		this.chatId_ = chatId;
		this.conversationId_ = conversationId;
		this.messageId_ = SessionManager.getCurrentUser() + "-" + System.currentTimeMillis() + "-" + SessionManager.getSessionId();
		this.toUsers_ = toUsers;
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
	
	public Set<String> getToUsers() {
		return this.toUsers_;
	}
	
	public String toSendableString() {
		return this.messageId_ + Message.getSeparator() + this.conversationId_ + Message.getSeparator() + this.chatId_ 
				+ Message.getSeparator() + this.sendingUser_ + Message.getSeparator() + this.content_ + Message.getSeparator() 
				+ String.join(Message.getSeparator(), this.toUsers_);
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
	
	
	public static Message fromSendableString(String messageString) {
		String[] messageArray = messageString.split(Message.getSeparator());
		
		if (messageArray.length < 6) {
			// TODO some sort of error indicating that the message was not complete
		}
		
		Set<String> toUsers = new HashSet<String>();
		for (int i = 5; i < messageArray.length; i++) {
			toUsers.add(messageArray[i]);
		}
		
		Message message = new Message(messageArray[1], messageArray[2], messageArray[3], messageArray[4], toUsers);
		message.messageId_ = messageArray[0];
		return message;
	}
	
	private static String getSeparator() {
		return "" + ((char) 31);
	}

}
