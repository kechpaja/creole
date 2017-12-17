package ui;

import java.awt.BorderLayout;
import java.util.concurrent.PriorityBlockingQueue;

import javax.swing.JPanel;

import resources.Strings;

public class Conversation extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6713163564873575070L;
	
	private static int count_ = 0;
	private String title_;
	
	private ThreadList threadList_;
	private ChatArea chatArea_;
	private UserList userList_;
	
	protected Conversation() {
		title_ = Strings.getDefaultConversationTitle(count_++);
		
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
	
	protected void redisplay() {
		this.threadList_.redisplay();
		this.chatArea_.redisplay();
	}
	
	protected PriorityBlockingQueue<Thread> getThreads() {
		return this.threadList_.getThreads();
	}
}
