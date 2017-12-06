package ui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

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
		// TODO localize this
		title_ = "Conversation " + (count_++);
		
		this.threadList_ = new ThreadList(this);
		this.chatArea_ = new ChatArea();
		this.userList_ = new UserList();
		
		this.add(this.threadList_);
		this.add(this.chatArea_);
		this.add(this.userList_);
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setOpaque(true);
	}

	
	protected String getTitle() {
		return this.title_;
	}
	
	protected void addThread(Thread thread) {
		this.chatArea_.addThread(thread);
		this.threadList_.addThread(thread);
	}
	
}
