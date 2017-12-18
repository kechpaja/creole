package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backend.Message;
import backend.SessionManager;
import resources.Strings;
import utils.InsertionSortList;

public class Chat extends JPanel implements KeyListener, MouseListener, Comparable<Chat> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6053838067760788383L;
	private static int count_ = 0;
	private JTextField titleField_;
	private ChatHistoryArea historyArea_;
	private ChatTypingArea typingArea_;
	private ChatListEntry listEntry_;
	private ChatList chatList_;
	private String id_;
	private long timeOfLastActivity_; // Time of last activity, in milliseconds
	private List<String> usersInChat_;
	private boolean isPrioritized_;
	
	// List of messages to be displayed
	private InsertionSortList<Message> messages_;
	
	protected Chat(ChatList chatList) {
		this.id_ = SessionManager.getCurrentUser() + "-" + System.currentTimeMillis() + "-" + Chat.count_++;
		this.messages_ = new InsertionSortList<Message>();
		this.chatList_ = chatList;
		
		// TODO actually add users to user list
		this.usersInChat_ = new ArrayList<String>();
		
		// TODO change border color based on whether user has focused window since new messages arrived. 
		
		this.init();
	}
	
	
	public Chat fork() {
		Chat forked = this.chatList_.createNewChat();
		for (String user : this.usersInChat_) {
			forked.usersInChat_.add(user);
		}
		
		// TODO consider including a reference to the chat that we are forked from, and a button to take the user there. 
		// That is a goal for later, though. 
		
		return forked;
	}
	
	
	/*
	 * Key Listener Methods
	 */
	
	@Override
	public void keyReleased(KeyEvent e) { /* Do nothing */ }
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (e.isShiftDown()) {
				this.typingArea_.append("\n");
			} else {
				if (!this.typingArea_.getText().equals("")) {
					Message message = new Message(this.typingArea_.getText(), SessionManager.getCurrentUser(), this.id_, this.chatList_.getConversationId());
					this.typingArea_.setText("");
					this.messages_.add(message);
					this.chatList_.getSender().queueMessage(message, this.usersInChat_);
					this.redisplay();
				}
				
				e.consume();
			}
		}
		
		this.updateTimeOfLastActivity();
	}

	@Override
	public void keyTyped(KeyEvent e) { /* Do nothing */ }
	
	
	@Override
	public int compareTo(Chat chat) {
		// Can't just subtract, because that would mean having to safely cast a long to int. 
		if (this.isPrioritized_) {
			return -1;
		} else if (chat.isPrioritized_) {
			return 1;
		} else if (chat.timeOfLastActivity_ < this.timeOfLastActivity_) {
			return -1;
		} else if (chat.timeOfLastActivity_ > this.timeOfLastActivity_) {
			return 1;
		} else {
			return 0;
		}
	}
	
	protected String getTitle() {
		return this.titleField_.getText();
	}
	
	protected ChatListEntry getListEntry() {
		return this.listEntry_;
	}
	
	protected String getId() {
		return this.id_;
	}
	
	protected void redisplay() {
		this.historyArea_.displayMessages(this.messages_);
		
		this.listEntry_.setHighlighted(this.isPrioritized_);
		if (this.isPrioritized_) {
			this.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));
		} else {
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		}
	}
	
	protected void deliver(Message message) {
		this.messages_.add(message);
	}
	
	protected void setPrioritized() {
		for (Chat chat : this.chatList_.getChats()) {
			chat.isPrioritized_ = false;
		}
		
		this.isPrioritized_ = true;
		
		for (Chat chat : this.chatList_.getChats()) {
			chat.redisplay();
		}
	}
	
	protected void redisplayConversation() {
		this.chatList_.redisplayConversation();
	}
	
	private void updateTimeOfLastActivity() {
		this.timeOfLastActivity_ = System.currentTimeMillis();
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		
		this.titleField_ = new JTextField(Strings.getDefaultChatTitle(Chat.count_));
		this.titleField_.setEditable(false);
		this.titleField_.addMouseListener(this);
		
		JPanel topPanel = new JPanel();
		topPanel.add(this.titleField_);
		topPanel.add(new ForkButton(this));
		this.add(topPanel, BorderLayout.PAGE_START);
		
		this.historyArea_ = new ChatHistoryArea(this);
		this.wrapInScrollPaneAndAdd(this.historyArea_, BorderLayout.CENTER, false);
		
		this.typingArea_ = new ChatTypingArea(this);
		this.wrapInScrollPaneAndAdd(this.typingArea_, BorderLayout.PAGE_END, true);
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

		this.listEntry_ = new ChatListEntry(this); // Depends on title field being set
		
		// Listeners
		this.addMouseListener(this);
		
		this.setOpaque(true);
		this.validate();
		
		this.updateTimeOfLastActivity();
		this.redisplay();
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
		this.redisplayConversation();
		this.typingArea_.requestFocusInWindow();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) { /* Do nothing */ }

	@Override
	public void mouseExited(MouseEvent arg0) { /* Do nothing */ }

	@Override
	public void mousePressed(MouseEvent arg0) { /* Do nothing */ }

	@Override
	public void mouseReleased(MouseEvent arg0) { /* Do nothing */ }
}
