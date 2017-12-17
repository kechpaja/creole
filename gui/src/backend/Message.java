package backend;

import java.util.ArrayList;
import java.util.List;

public class Message implements Comparable<Message> {
	
	private String content_;
	private String sendingUser_;
	private long sendTime_;
	private String threadId_;
	private String conversationId_;
	private String messageId_; 
	private List<String> toUsers_;
	
	public Message(String content, String sendingUser, String threadId, String conversationId, List<String> toUsers) {
		// TODO strip out all non-printing characters
		this.content_ = content.replaceAll("" + ((char) 30), "").replaceAll("" + ((char) 31), "");
		this.sendingUser_ = sendingUser;
		this.sendTime_ = System.currentTimeMillis();
		this.threadId_ = threadId;
		this.conversationId_ = conversationId;
		
		this.toUsers_ = new ArrayList<String>();
		for (String s : toUsers) {
			this.toUsers_.add(s);
		}
		
		// TODO set message ID
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
	
	public String getThreadId() {
		return this.threadId_;
	}
	
	public String getConversationId() {
		return this.conversationId_;
	}
	
	public List<String> getToUsers() {
		return this.toUsers_;
	}
	
	public String toSendableString() {
		char unitSeparator = (char) 31;
		
		String toUsersString = "";
		for (String user : this.toUsers_) {
			toUsersString += unitSeparator + user;
		}
		
		return this.messageId_ + unitSeparator + this.conversationId_ + unitSeparator + this.threadId_ 
				+ unitSeparator + this.sendingUser_ + unitSeparator + this.content_ + toUsersString;
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
