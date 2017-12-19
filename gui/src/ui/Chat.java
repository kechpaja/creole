package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import backend.Message;
import backend.SessionManager;
import utils.InsertionSortList;

public class Chat extends JPanel implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6053838067760788383L;
	private ChatHeader header_;
	private ChatHistoryArea historyArea_;
	private ChatTypingArea typingArea_;
	private ChatListEntry listEntry_;
	private ChatList chatList_;
	private String id_;
	private Set<String> usersInChat_;
	private boolean isPrioritized_;
	private InsertionSortList<Message> messages_;
	
	protected Chat(ChatList chatList) {
		this.id_ = SessionManager.getCurrentUser() + "-" + System.currentTimeMillis() + "-" + SessionManager.getSessionId();
		this.messages_ = new InsertionSortList<Message>();
		this.chatList_ = chatList;
		
		this.usersInChat_ = new HashSet<String>();
		this.usersInChat_.add(SessionManager.getCurrentUser());
		
		// TODO change border color based on whether user has focused window since new messages arrived. 
		
		this.setLayout(new BorderLayout());
		
		this.header_ = new ChatHeader(this);
		this.add(this.header_, BorderLayout.PAGE_START);
		
		this.historyArea_ = new ChatHistoryArea(this);
		this.wrapInScrollPaneAndAdd(this.historyArea_, BorderLayout.CENTER, false);
		
		this.typingArea_ = new ChatTypingArea(this);
		this.wrapInScrollPaneAndAdd(this.typingArea_, BorderLayout.PAGE_END, true);
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

		this.listEntry_ = new ChatListEntry(this);
		
		// Listeners
		this.addMouseListener(this);
		
		this.setOpaque(true);
		this.validate();
		
		this.redisplay();
	}
	
	
	public Chat fork() {
		Chat forked = this.chatList_.createNewChat();
		for (String user : this.usersInChat_) {
			forked.usersInChat_.add(user);
		}
		
		return forked;
	}
	
	protected ChatListEntry getListEntry() {
		return this.listEntry_;
	}
	
	protected String getId() {
		return this.id_;
	}
	
	protected String getConversationId() {
		return this.chatList_.getConversationId();
	}
	
	protected Conversation getConversation() {
		return this.chatList_.getConversation();
	}
	
	protected Set<String> getUsersInChat() {
		return this.usersInChat_;
	}
	
	protected List<String> getUsersInChatSorted() {
		List<String> users = new ArrayList<String>();
		for (String user : this.getUsersInChat()) {
			users.add(user);
		}
		users.sort(Comparator.comparing((String x) -> x));
		return users;
	}
	
	protected void addUser(String user) {
		this.usersInChat_.add(user);
		this.redisplay();
		this.listEntry_.redisplay();
	}
	
	protected void redisplay() {
		this.historyArea_.displayMessages(this.messages_);
		
		this.listEntry_.setHighlighted(this.isPrioritized_);
		if (this.isPrioritized_) {
			this.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));
		} else {
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		}
		
		this.header_.redisplay();
	}
	
	protected void deliver(Message message) {
		this.messages_.add(message);
		this.usersInChat_.addAll(message.getToUsers());
		this.id_ = message.getChatId();
		this.redisplay();
		this.chatList_.bumpToFront(this);
	}
	
	protected void setPrioritized() {
		for (Chat chat : this.chatList_.getChats()) {
			chat.isPrioritized_ = false;
		}
		
		this.isPrioritized_ = true;
		this.chatList_.setPrioritizedChat(this);
		
		for (Chat chat : this.chatList_.getChats()) {
			chat.redisplay();
		}
	}
	
	private void wrapInScrollPaneAndAdd(JTextArea area, String where, boolean hasUpperBorder) {
		JScrollPane pane = new JScrollPane(area);
		pane.setBorder(BorderFactory.createMatteBorder(hasUpperBorder ? 5 : 0, 0, 0, 0, Color.BLUE));
		this.add(pane, where);
	}


	/*
	 * Mouse Listener methods
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.setPrioritized();
		this.chatList_.redisplayConversation();
		this.typingArea_.requestFocusInWindow();
	}

	@Override public void mouseEntered(MouseEvent arg0) { /* Do nothing */ }
	@Override public void mouseExited(MouseEvent arg0) { /* Do nothing */ }
	@Override public void mousePressed(MouseEvent arg0) { /* Do nothing */ }
	@Override public void mouseReleased(MouseEvent arg0) { /* Do nothing */ }
}
