package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JPanel;

import backend.Message;
import backend.SessionManager;
import resources.Strings;

public class Conversation extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6713163564873575070L;
	
	private static int count_ = 0;
	private String title_;
	private String id_;
	
	private ChatList chatList_;
	private ChatArea chatArea_;
	private UserList userList_;
	
	protected Conversation() {
		this.title_ = Strings.getDefaultConversationTitle(count_++);
		this.id_ = SessionManager.getCurrentUser() + "-" + System.currentTimeMillis() + "-" + Conversation.count_;
		
		this.chatList_ = new ChatList(this);
		this.chatArea_ = new ChatArea(this);
		this.userList_ = new UserList();

		this.setLayout(new BorderLayout());
		
		this.add(this.chatList_, BorderLayout.LINE_START);
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
		this.chatList_.redisplay();
		this.chatArea_.redisplay();
	}
	
	protected Vector<Chat> getChats() {
		return this.chatList_.getChats();
	}
	
	protected Chat getPrioritizedChat() {
		return this.chatList_.getPrioritizedChat();
	}
	
	protected Set<String> getUsers() {
		return this.userList_.getUsers();
	}
	
	protected List<String> getUsersSorted() {
		List<String> users = new ArrayList<String>();
		users.addAll(this.getUsers());
		users.sort(Comparator.comparing((String x) -> x));
		return users;
	}
	
	protected void deliver(Message message) {
		this.chatList_.deliver(message);
	}
}
