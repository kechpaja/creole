package ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import resources.Strings;

public class Thread extends JPanel implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6053838067760788383L;
	private String title_;
	private int count_ = 0;
	private JTextArea chatArea_;
	private JTextArea typingArea_;
	private long timeOfLastActivity_; // Time of last activity, in milliseconds
	
	protected Thread() {
		this.title_ = Strings.getDefaultThreadTitle(count_++);
		
		// TODO advanced: user list; clone button
		// TODO actually show the title somewhere
		
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
					// TODO Don't loc this, change it. 
					this.chatArea_.append("username: " + this.typingArea_.getText() + "\n");
					this.typingArea_.setText("");
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
	
	protected String getTitle() {
		return this.title_;
	}
	
	protected long timeSinceLastActivity() {
		return (System.currentTimeMillis() - this.timeOfLastActivity_);
	}
	
	private void updateTimeOfLastActivity() {
		this.timeOfLastActivity_ = System.currentTimeMillis();
	}
}
