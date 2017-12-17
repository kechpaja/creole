package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backend.Message;
import resources.Strings;
import utils.InsertionSortList;

public class Chat extends JPanel implements KeyListener, FocusListener, Comparable<Chat> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6053838067760788383L;
	private static int count_ = 0;
	private JTextField titleField_;
	private JTextArea chatArea_;
	private JTextArea typingArea_;
	private ChatListEntry listEntry_;
	private ChatList threadList_;
	private String id_;
	private long timeOfLastActivity_; // Time of last activity, in milliseconds
	private List<String> usersInThread_;
	
	// List of messages to be displayed
	private InsertionSortList<Message> messages_;
	
	protected Chat(ChatList threadList) {
		this.id_ = System.currentTimeMillis() + "-" + Chat.count_++; // TODO work in user ID?
		this.messages_ = new InsertionSortList<Message>();
		this.threadList_ = threadList;
		
		// TODO actually add users to user list
		this.usersInThread_ = new ArrayList<String>();
		
		// TODO advanced: user list; clone button
		// TODO change border color based on whether user has focused window since new messages arrived. 
		

		this.setLayout(new BorderLayout());
		
		this.titleField_ = new JTextField(Strings.getDefaultThreadTitle(Chat.count_));
		this.titleField_.setEditable(false);
		this.titleField_.addFocusListener(this);
		this.add(this.titleField_, BorderLayout.PAGE_START);
		
		this.chatArea_ = new JTextArea(5, 3);
		this.chatArea_.setEditable(false);
		this.chatArea_.setLineWrap(true);
		this.chatArea_.setWrapStyleWord(true);
		this.chatArea_.addFocusListener(this);
		JScrollPane chatAreaScrollPane = new JScrollPane(this.chatArea_);
		chatAreaScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
		this.add(chatAreaScrollPane, BorderLayout.CENTER);
		
		this.typingArea_ = new JTextArea(5, 3);
		this.typingArea_.setEditable(true);
		this.typingArea_.setLineWrap(true);
		this.typingArea_.setWrapStyleWord(true);
		this.typingArea_.addKeyListener(this);
		this.typingArea_.addFocusListener(this);
		JScrollPane typingAreaScrollPane = new JScrollPane(this.typingArea_);
		typingAreaScrollPane.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.BLUE));
		this.add(typingAreaScrollPane, BorderLayout.PAGE_END);
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

		this.listEntry_ = new ChatListEntry(this); // Depends on title field being set
		
		// Listeners
		this.addFocusListener(this);
		
		this.setOpaque(true);
		this.validate();
		
		this.updateTimeOfLastActivity();
		this.redisplay();
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
					// TODO Get username of this user
					Message message = new Message(this.typingArea_.getText(), "me", this.id_, this.threadList_.getConversationId());
					this.typingArea_.setText("");
					this.messages_.add(message);
					this.threadList_.getSender().queueMessage(message, this.usersInThread_);
					this.redisplay();
				}
				
				e.consume();
			}
		}
		
		this.updateTimeOfLastActivity();
	}

	@Override
	public void keyTyped(KeyEvent e) { /* Do nothing */ }
	
	
	/*
	 * Focus Listener methods
	 */
	
	@Override
	public void focusGained(FocusEvent arg0) {
		this.listEntry_.setHighlighted(true);
		this.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));
		this.validate();
		this.updateTimeOfLastActivity();
	}


	@Override
	public void focusLost(FocusEvent arg0) {
		this.listEntry_.setHighlighted(false);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		this.validate();
	}
	
	
	@Override
	public int compareTo(Chat thread) {
		// Can't just subtract, because that would mean having to safely cast a long to int. 
		if (this.isFocusOwner() || thread.timeOfLastActivity_ < this.timeOfLastActivity_) {
			return -1;
		} else if (thread.isFocusOwner() || thread.timeOfLastActivity_ > this.timeOfLastActivity_) {
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
		String chatAreaContents = "";
		
		for (Message message : this.messages_) {
			chatAreaContents += message.getSendingUser() + ": " + message.getContent() + "\n";
		}
		
		this.chatArea_.setText(chatAreaContents);
	}
	
	private void updateTimeOfLastActivity() {
		this.timeOfLastActivity_ = System.currentTimeMillis();
	}
}
