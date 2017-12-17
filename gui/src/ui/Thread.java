package ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.PriorityBlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import backend.Message;
import resources.Strings;

public class Thread extends JPanel implements KeyListener, Comparable<Thread> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6053838067760788383L;
	private String title_;
	private int count_ = 0;
	private JTextArea chatArea_;
	private JTextArea typingArea_;
	private long timeOfLastActivity_; // Time of last activity, in milliseconds
	
	// List of messages to be displayed
	private PriorityBlockingQueue<Message> messages_;
	
	protected Thread() {
		this.title_ = Strings.getDefaultThreadTitle(count_++);
		this.messages_ = new PriorityBlockingQueue<Message>();
		
		// TODO advanced: user list; clone button
		// TODO actually show the title somewhere
		// TODO change border color based on whether user has focused window since new messages arrived. 
		
		this.chatArea_ = new JTextArea(5, 3);
		this.chatArea_.setEditable(false);
		this.chatArea_.setLineWrap(true);
		this.chatArea_.setWrapStyleWord(true);
		JScrollPane chatAreaScrollPane = new JScrollPane(this.chatArea_);
		chatAreaScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
		this.add(chatAreaScrollPane);
		
		this.typingArea_ = new JTextArea(5, 3);
		this.typingArea_.setEditable(true);
		this.typingArea_.setLineWrap(true);
		this.typingArea_.setWrapStyleWord(true);
		this.typingArea_.addKeyListener(this);
		JScrollPane typingAreaScrollPane = new JScrollPane(this.typingArea_);
		typingAreaScrollPane.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.BLUE));
		this.add(typingAreaScrollPane);
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(true);
		this.validate();
		
		this.updateTimeOfLastActivity();
		this.redisplay();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		//  Do nothing
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (e.isShiftDown()) {
				this.typingArea_.append("\n");
			} else {
				if (!this.typingArea_.getText().equals("")) {
					// TODO Get username of this user
					Message message = new Message(this.typingArea_.getText(), "me");
					this.typingArea_.setText("");
					
					this.messages_.add(message);
					
					// TODO send that message on to networking backend as well
					
					// TODO what we should really be doing is sending a message object to something in the backend,
					// and then call the backend for the new list of messages to redisplay in this thread. The thread
					// object here should have an "update" method that just fetches from there. 
					
					this.redisplay();
				}
				
				e.consume();
			}
		}
		
		this.updateTimeOfLastActivity();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Do nothing
	}
	
	@Override
	public int compareTo(Thread thread) {
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
		return this.title_;
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
