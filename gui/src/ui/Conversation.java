package ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import backend.Message;
import backend.Sender;
import resources.Strings;
import utils.InsertionSortList;

public class Conversation extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6713163564873575070L;
	
	private static int count_ = 0;
	private String title_;
	private String id_;
	
	private ThreadList threadList_;
	private ChatArea chatArea_;
	private UserList userList_;
	
	private Sender sender_;
	
	protected Conversation(Sender sender) {
		this.title_ = Strings.getDefaultConversationTitle(count_++);
		this.id_ = System.currentTimeMillis() + "-" + Conversation.count_;
		this.sender_ = sender;
		
		this.threadList_ = new ThreadList(this);
		this.chatArea_ = new ChatArea(this);
		this.userList_ = new UserList();

		this.setLayout(new BorderLayout());
		
		this.add(this.threadList_, BorderLayout.LINE_START);
		this.add(this.chatArea_, BorderLayout.CENTER);
		this.add(this.userList_, BorderLayout.LINE_END);
		
		this.setOpaque(true);
		this.validate();
	}

	
	protected String getTitle() {
		return this.title_;
	}
	
	protected String getId() {
		return this.id_;
	}
	
	protected void redisplay() {
		this.threadList_.redisplay();
		this.chatArea_.redisplay();
	}
	
	protected InsertionSortList<Thread> getThreads() {
		return this.threadList_.getThreads();
	}
	
	protected void deliver(Message message) {
		this.threadList_.deliver(message);
	}
	
	protected Sender getSender() {
		return this.sender_;
	}
}
